package miner.hash;

import java.io.IOException;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import java.util.Random;

import algo.hash.SHA512;
import domain.Account;
import domain.Asset;
import domain.Block;
import domain.Chain;
import domain.RewardTransaction;
import domain.Transaction;
import domain.TransactionPool;
import domain.proof.Proof;
import domain.proof.difficulty.DifficultyAssessorFactory;
import domain.proof.hash.SHA512Proof;
import miner.IMiner;
import miner.MiningResultsQueue;
import util.IllegalParameterException;
import util.MathUtil;
import util.pattern.Observable;

public class SHA512Miner extends Observable implements IMiner, Cloneable {
	private TransactionPool transactionPool;
	private MiningResultsQueue miningResults;
	private Chain chain;
	private Account to;
	private Asset asset;
	private volatile boolean running = false;
	
	public SHA512Miner(Asset asset, Account to) throws NoSuchAlgorithmException, IOException {
		this.to = to;
		transactionPool = TransactionPool.instance();
		miningResults = MiningResultsQueue.instance();
		this.asset = asset;
		chain = Chain.instance(asset);
	}
	
	public void stop() {
		running = false;
	}
	
	private BigInteger hash(BigInteger input, long nonce) {
		byte[] toHash = input.toByteArray();
		byte[] nonceBytes = MathUtil.longtoBytes(nonce);
		byte[] originalBytes = new byte[toHash.length + nonceBytes.length];
		System.arraycopy(toHash, 0, originalBytes, 0, toHash.length);
		System.arraycopy(nonceBytes, 0, originalBytes, toHash.length, nonceBytes.length);
		BigInteger hashed = new BigInteger(SHA512.hash(originalBytes), 16);
		return hashed;
	}

	@Override
	public void run() {
		running = true;
		Random random = new Random();
		long nonce = (long) (random.nextDouble() * Long.MAX_VALUE);
		int difficulty = 20;
		while (running) {
			Proof proof = null;
			BigInteger original = chain.getMerkleRoot();
			
			// Do the hash
			try {
				difficulty = DifficultyAssessorFactory.getAssessor(SHA512Proof.class).getDifficulty(new Date());
			} catch (NoSuchAlgorithmException | IOException e1) {
				e1.printStackTrace();
			}
			BigInteger hashed = original;
			int rounds = MathUtil.binlog(difficulty);
			for (int i=0; i<rounds; i++) {
				hashed = hash(hashed, nonce);
			}	
			
			if (MathUtil.checkTrailingZeroes(hashed, difficulty)) {
				RewardTransaction rewardTransaction;
				try {
					proof = new SHA512Proof(original, nonce, hashed, Asset.getDefault());
					rewardTransaction = new RewardTransaction(proof, to);
					Block block = new Block(chain.getLastBlock(), rewardTransaction);
					List<Transaction> transactions = transactionPool.get();
					for (Transaction t: transactions) {
						block.add(t);
					}
					miningResults.put(block);
				} catch (NoSuchAlgorithmException  | IOException | InterruptedException e) {
					e.printStackTrace();
				}
			}
			nonce = (long) (random.nextDouble() * Long.MAX_VALUE);
		}
	}

	@Override
	public long getExpectedProfit() {
		return 1;
	}
	
	@SuppressWarnings("null")
	@Override
	public SHA512Miner clone() {
		SHA512Miner result = null;
		try {
			result = new SHA512Miner(asset, to);
		} catch (NoSuchAlgorithmException | IOException e) {
			try {
				result.transactionPool = TransactionPool.instance();
				result.miningResults = MiningResultsQueue.instance();
				result.asset = asset;
				result.chain = Chain.instance(asset);
			} catch (NoSuchAlgorithmException | IOException e1) {
				e1.printStackTrace();
			}
			catch(NullPointerException e2) {
				e2.printStackTrace();
				System.exit(1);
			}
		}
		return result;
	}

	//
	// Parameter 1: BigInteger for original
	// Parameter 2: Long for nonce
	// Parameter 3: BigInteger for hash
	//
	@Override
	public Proof createProof(Asset asset, Object... result) throws NoSuchAlgorithmException, IOException {
		if (!(result[0] instanceof BigInteger)) {
			throw new IllegalParameterException("First argument is a BigInteger for original.");
		}
		if (!(result[1] instanceof Long)) {
			throw new IllegalParameterException("Second argument is a Long for nonce.");
		}
		if (!(result[2] instanceof BigInteger)) {
			throw new IllegalParameterException("Second argument is a BigInteger for hashed value.");
		}
		return new SHA512Proof((BigInteger) result[0], (Long) result[1], (BigInteger) result[2], asset);
	}
}