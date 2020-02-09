package algo.kconstellation;

import static domain.Settings.Environment.DEVELOPMENT;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import domain.Settings;

public class NenapletConstellation extends AbstractKConstellationPrimer {
	
	@Override
	public BigInteger[] getNextKPrime(BigInteger minimum) throws IOException {
		BigInteger[] result = new BigInteger[9];
		BigInteger currentPrime = new BigInteger(minimum.toByteArray()); // Simulating a clone
		while (true) {
			currentPrime = currentPrime.nextProbablePrime(); // Now I have the next prime to test for nenaplaetness
			
			// (0, 2, 6, 8, 12, 18, 20, 26, 30)
			result[0] = currentPrime;
			result[1] = currentPrime.add(TWO);
			result[2] = currentPrime.add(SIX);	
			result[3] = currentPrime.add(new BigInteger("8"));
			result[4] = currentPrime.add(new BigInteger("12"));
			result[5] = currentPrime.add(new BigInteger("18"));
			result[6] = currentPrime.add(new BigInteger("20"));
			result[7] = currentPrime.add(new BigInteger("26"));
			result[8] = currentPrime.add(new BigInteger("30"));
			if (isKPrime(result)) {
				if (addPrime(result)) {
					if (Settings.instance().getEnvironment() == DEVELOPMENT) {
						createCSVFile(result);
					}
					return result;
				}
			}
			
			// (0, 4, 6, 10, 16, 18, 24, 28, 30)
			result[1] = currentPrime.add(FOUR);	
			result[2] = currentPrime.add(SIX);	
			result[3] = currentPrime.add(new BigInteger("10"));
			result[4] = currentPrime.add(new BigInteger("16"));	
			result[5] = currentPrime.add(new BigInteger("18"));	
			result[6] = currentPrime.add(new BigInteger("24"));
			result[7] = currentPrime.add(new BigInteger("28"));
			result[8] = currentPrime.add(new BigInteger("30"));
			if (isKPrime(result)) {
				if (addPrime(result)) {
					if (Settings.instance().getEnvironment() == DEVELOPMENT) {
						createCSVFile(result);
					}
					return result;
				}
			}
			
			// (0, 2, 6, 12, 14, 20, 24, 26, 30)
			result[1] = currentPrime.add(TWO);	
			result[2] = currentPrime.add(SIX);	
			result[3] = currentPrime.add(new BigInteger("12"));
			result[4] = currentPrime.add(new BigInteger("14"));	
			result[5] = currentPrime.add(new BigInteger("20"));	
			result[6] = currentPrime.add(new BigInteger("24"));
			result[7] = currentPrime.add(new BigInteger("26"));
			result[8] = currentPrime.add(new BigInteger("30"));
			if (isKPrime(result)) {
				if (addPrime(result)) {
					if (Settings.instance().getEnvironment() == DEVELOPMENT) {
						createCSVFile(result);
					}
					return result;
				}
			}
			
			// (0, 4, 10, 12, 18, 22, 24, 28, 30)
			result[1] = currentPrime.add(FOUR);	
			result[2] = currentPrime.add(new BigInteger("10"));	
			result[3] = currentPrime.add(new BigInteger("12"));
			result[4] = currentPrime.add(new BigInteger("18"));	
			result[5] = currentPrime.add(new BigInteger("22"));	
			result[6] = currentPrime.add(new BigInteger("24"));
			result[7] = currentPrime.add(new BigInteger("28"));
			result[8] = currentPrime.add(new BigInteger("30"));
			if (isKPrime(result)) {
				if (addPrime(result)) {
					if (Settings.instance().getEnvironment() == DEVELOPMENT) {
						createCSVFile(result);
					}
					return result;
				}
			}
		}
	}
	
	private void createCSVFile(BigInteger[] primes) throws IOException {
		Settings settings = Settings.instance();
	    FileWriter out = new FileWriter(settings.getNenapletFile(), true);
	    try (CSVPrinter printer = new CSVPrinter(out, CSVFormat.DEFAULT)) {
	            printer.printRecord(primes[0], primes[1], primes[2], primes[3], primes[4], primes[5], primes[6], primes[7], primes[8]);
	    }
	}
}
