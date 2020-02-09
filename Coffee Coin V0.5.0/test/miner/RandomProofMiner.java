package miner;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import java.util.Random;

import domain.Account;
import domain.Asset;
import domain.Block;
import domain.Chain;
import domain.RewardTransaction;
import domain.Transaction;
import domain.TransactionPool;
import domain.proof.Proof;
import miner.IMiner;
import miner.MiningResultsQueue;
import proof.RandomProof;
import util.IllegalParameterException;

public class RandomProofMiner implements IMiner {
	private TransactionPool transactionPool;
	private MiningResultsQueue miningResults;
	private Chain chain;
	private Account to;
	private volatile boolean running = false;
	private Date timestampLastFound;
	private long chance;
	
	public RandomProofMiner(Asset asset, Account to) throws NoSuchAlgorithmException, IOException {
		this.to = to;
		timestampLastFound = new Date();
		transactionPool = TransactionPool.instance();
		miningResults = MiningResultsQueue.instance();
		chain = Chain.instance(asset);
		chance = 2;
	}
	
	public void stop() {
		running = false;
	}

	@Override
	public void run() {
		running = true;
		Random random = new Random(timestampLastFound.getTime());
		while (running) {
			// Roll the die until the required number comes up
			double exp = Math.pow(10, (0-chance));
			double die = random.nextDouble();
			while (die > exp) {
				die = random.nextDouble();
			}
			//
			// Found a number. Create a new proof using the valueFound and current chance
			//
			Proof proof = null;
			try {
				proof = createProof(Asset.getDefault(), die, chance);
			} catch (NoSuchAlgorithmException | IOException e) {
				e.printStackTrace();
			}
			//
			// Adjust chance so the speed will be around 10 seconds a result
			//
			Date now = new Date();
			double elapsed = now.getTime() - timestampLastFound.getTime();
			if (elapsed > 100000) {
				chance--;
			}
			else if (elapsed < 2000) {
				chance++;
			}
			//
			// Create a block, add transactions and offer it to the chain
			//
			RewardTransaction rewardTransaction;
			try {
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
	}

	@Override
	public long getExpectedProfit() {
		return 1;
	}

	// Object 1 is a Double for valueFound
	// Object 2 is a Long for chance
	//
	@Override
	public Proof createProof(Asset asset, Object... result) throws NoSuchAlgorithmException, IOException {
		if (!(result[0] instanceof Double)) {
			throw new IllegalParameterException("First argument is a Double for valueFound.");
		}
		if (!(result[1] instanceof Long)) {
			throw new IllegalParameterException("Second argument is a Long for chance.");
		}
		return new RandomProof((Double) result[0], (Long) result[1], asset);
	}
}