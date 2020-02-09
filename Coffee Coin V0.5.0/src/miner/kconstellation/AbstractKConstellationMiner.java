package miner.kconstellation;

import static domain.Settings.Environment.DEVELOPMENT;

import java.io.IOException;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import algo.kconstellation.AbstractKConstellationPrimer;
import domain.Account;
import domain.Asset;
import domain.Block;
import domain.Chain;
import domain.RewardTransaction;
import domain.Settings;
import domain.Transaction;
import domain.TransactionPool;
import domain.proof.Proof;
import domain.proof.primes.KConstellationProof;
import miner.MiningResultsQueue;
import util.BigIntegerArrayComparable;
import util.IllegalParameterException;
import util.pattern.Observable;

public abstract class AbstractKConstellationMiner extends Observable implements IPrimeProofMiner, Cloneable {
	private BigInteger minimum;
	private final long expectedProfit;
	private TransactionPool transactionPool;
	private MiningResultsQueue miningResults;
	private Chain chain;
	private AbstractKConstellationPrimer primer;
	protected Account to;
	protected Asset asset;
	private volatile boolean running = false;
	
	protected AbstractKConstellationMiner(Class<?> type, Asset asset, AbstractKConstellationPrimer primer, Account to) throws NoSuchAlgorithmException, IOException {
		this.primer = primer;
		this.to = to;
		this.asset = asset;
		List<Proof> proofs = Chain.instance(asset).getProofs(type, true);
		SortedSet<BigInteger[]> kConstellationPlets = new TreeSet<>(new BigIntegerArrayComparable());
		for (Proof por: proofs) {
			KConstellationProof kcon = (KConstellationProof) por;
			kConstellationPlets.add(kcon.getPrimes());
		}
		BigInteger[][] proofOfReward = new BigInteger[2][];
		Date[] dates = new Date[2];
		BigInteger[] rewards = new BigInteger[2];
		
		proofOfReward[0] = kConstellationPlets.last();
		kConstellationPlets.remove(proofOfReward[0]);
		proofOfReward[1] = kConstellationPlets.last();
		dates[0] = new Date(0);
		dates[1] = new Date(0);
		for (Proof proof: proofs) {
			Date thisDate = proof.getTimestamp();
			if (thisDate.after(dates[0] )) {
				dates[1] = dates[0] ;
				rewards[1] = rewards[0];
				dates[0]  = thisDate;
				rewards[1] = proof.getReward().getAmount();
			}
		}
		long elapsed = dates[0].getTime() - dates[1].getTime();
		minimum = proofOfReward[0][0];
		expectedProfit = (minimum.longValue() / elapsed) * 1000;
		transactionPool = TransactionPool.instance();
		miningResults = MiningResultsQueue.instance();
		chain = Chain.instance(asset);
	}

	public BigInteger getMinimum() {
		return minimum;
	}

	public long getExpectedProfit() {
		return expectedProfit;
	}
	
	public void stop() {
		running = false;
	}

	@Override
	public void run() {
		running = true;
		while (running) {
			try {
				BigInteger[] result = primer.getNextKPrime(minimum);
				Proof proof = createProof(result, Asset.getDefault());
				RewardTransaction rewardTransaction = new RewardTransaction(proof, to);
				Block block = new Block(chain.getLastBlock(), rewardTransaction);
				List<Transaction> transactions = transactionPool.get();
				for (Transaction t: transactions) {
					block.add(t);
				}
				miningResults.put(block);
				minimum = result[0].add(new BigInteger("2"));
			} catch (IOException | NoSuchAlgorithmException | InterruptedException e) {
				e.printStackTrace();
			}
			if (Settings.instance().getEnvironment() == DEVELOPMENT) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public Proof createProof(Asset asset, Object... result) throws NoSuchAlgorithmException, IOException {
		if ((result == null) || (result.length == 0) || (!(result[0] instanceof BigInteger))) {
			throw new IllegalParameterException("Creating a K-Constellation proof requires an array of BigIntegers");
		}
		else {
			BigInteger[] b = (BigInteger[]) result;
			return createProof(b, asset);
		}
	}
	
	public abstract Proof createProof(BigInteger[] result, Asset asset) throws NoSuchAlgorithmException, IOException;
}
