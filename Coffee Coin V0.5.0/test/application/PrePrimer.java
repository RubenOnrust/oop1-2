package application;

import java.io.IOException;
import java.math.BigInteger;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import static domain.Settings.Environment.*;
import algo.kconstellation.AbstractKConstellationPrimer;
import algo.kconstellation.HexapletConstellation;
import algo.kconstellation.NenapletConstellation;
import algo.kconstellation.OctapletConstellation;
import algo.kconstellation.PentapletConstellation;
import algo.kconstellation.SeptapletConstellation;
import domain.Settings;

public class PrePrimer implements Runnable {
	private BigInteger minimum;
	private BigInteger maximum;
	private AbstractKConstellationPrimer primer;
	private static int amount = 0;
	private static List<Thread> draadjes;
	
	public PrePrimer(BigInteger minimum, BigInteger maximum, AbstractKConstellationPrimer primer) {
		super();
		this.minimum = minimum;
		this.maximum = maximum;
		this.primer = primer;
	}

	public static void main(String[] args) throws InterruptedException {
		draadjes = new ArrayList<>(10);
		
		// Start a variation of preminers
		startThread(new PentapletConstellation(), new BigInteger("23000000000"),new BigInteger("30000000000"));
		startThread(new PentapletConstellation(), new BigInteger("30000000000"),new BigInteger("40000000000"));
		startThread(new PentapletConstellation(), new BigInteger("40000000000"),new BigInteger("50000000000"));
		startThread(new PentapletConstellation(), new BigInteger("50000000000"),new BigInteger("60000000000"));
		
		startThread(new HexapletConstellation(),  new BigInteger("2534808037"), new BigInteger("4500000000"));
		startThread(new HexapletConstellation(),  new BigInteger("4500000000"), new BigInteger("6000000000"));
		
		startThread(new SeptapletConstellation(), new BigInteger("3335288639"), new BigInteger("6000000000"));
		startThread(new OctapletConstellation(),  new BigInteger("2966003057"), new BigInteger("6000000000"));
		startThread(new NenapletConstellation(),  new BigInteger("1645175087"), new BigInteger("3000000000"));
		
		// Wait for the preminers to finish their tasks
		for (Thread draadje: draadjes) {
			System.out.println("Waiting for thread " + draadje.getId() + " to finish.");
			draadje.join();
			System.out.println("Thread " + draadje.getId() + " finished.");
		}
	}
	
	private static void startThread(AbstractKConstellationPrimer primerToUse, BigInteger min, BigInteger max) {
		Runnable primer = new PrePrimer(min, max, primerToUse);
		Thread draadje = new Thread(primer);
		draadjes.add(draadje);
		draadje.start();
	}
	
	@Override
	public void run() {
		try {
			primer.getNextKPrime(new BigInteger(minimum.toString()));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		while (maximum.compareTo(primer.getHighestKPrime()[0]) > 0) {
			primer.getNextKPrime();
			if (Settings.instance().getEnvironment() == DEVELOPMENT) {
				System.out.println(LocalTime.now() + ": found " + primer.getHighestKPrime()[0] + "; " + (++amount) + " found.");
			}
		}
	}
}
