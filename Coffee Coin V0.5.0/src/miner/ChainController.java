package miner;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.List;

import domain.Asset;
import domain.Block;
import domain.Chain;
import domain.Transaction;
import domain.TransactionPool;
import util.pattern.Observable;

public class ChainController extends Observable {
	private MiningResultsQueue miningResults;
	private Chain chain;
	private volatile boolean running = false;
	private static ChainController chainController;
	
	private ChainController() {
		miningResults = MiningResultsQueue.instance();
		try {
			chain = Chain.instance(Asset.getDefault());
		} catch (NoSuchAlgorithmException | IOException e) {
			e.printStackTrace();
		}
		Thread innerThread = new Thread(new Runnable() {

			@Override
			public void run() {
				doWork();
			}
		});
		running = true;
		innerThread.setDaemon(true);
		innerThread.start();
	}
	
	public static ChainController instance() {
		if (chainController == null) {
			chainController = new ChainController();
		}
		return chainController;
	}
	
	public synchronized void stop() {
		this.running = false;
	}
	
	private void doWork() {
		while (running) {
			try {
				Block block = miningResults.take();
				if (chain.add(block)) {
					notifyObservers();
				}
				else {
					// Reject adding the block. Return transactions to pool
					synchronized(this) {
						TransactionPool pool = TransactionPool.instance();
						List<Transaction> transactions = block.getTransactions();
						Iterator<Transaction> i = transactions.iterator();
						i.next(); // Skipping the RewardTransaction
						while (i.hasNext()) {
							pool.queue(i.next());
						}
					}
				}
			} catch (InterruptedException | NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
		}
	}
}
