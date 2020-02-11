package miner;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import domain.Account;
import domain.Settings;
import miner.factory.MinerFactory;
import util.pattern.Observable;

public class MinerPool extends Observable {
	private List<IMiner> miners;
	private ExecutorService pool;
	private static MinerPool innerObject = null;
	
	public static MinerPool instance(Account to) throws NoSuchAlgorithmException, IOException {
		if (innerObject == null) {
			innerObject = new MinerPool(to);
		}
		return innerObject;
	}
	
	private MinerPool(Account to) throws NoSuchAlgorithmException, IOException {
		miners = MinerFactory.getMiners(to);
		pool = Executors.newFixedThreadPool(Settings.instance().getNumberOfMinerThreads());
	}
	
	public void start(int index) {
		pool.submit(miners.get(index));
		notifyObservers();
	}
	
	public void stop(int index) {
		miners.get(index).stop();
		notifyObservers();
	}
	
	public void startAll() {
		for (int i=0; i<miners.size(); i++) {
			start(i);
		}
		notifyAll();
	}
	
	public void stopAll() {
		pool.shutdown();
		for (int i=0; i<miners.size(); i++) {
			stop(i);
		}
		try {
			pool.awaitTermination(60, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		pool = Executors.newFixedThreadPool(Settings.instance().getNumberOfMinerThreads());
		notifyAll();
	}
}
