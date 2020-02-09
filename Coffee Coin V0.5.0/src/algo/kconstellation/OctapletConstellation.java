package algo.kconstellation;

import static domain.Settings.Environment.DEVELOPMENT;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import domain.Settings;

public class OctapletConstellation extends AbstractKConstellationPrimer {
	
	@Override
	public BigInteger[] getNextKPrime(BigInteger minimum) throws IOException {
		BigInteger[] result = new BigInteger[8];
		BigInteger currentPrime = new BigInteger(minimum.toByteArray()); // Simulating a clone
		while (true) {
			currentPrime = currentPrime.nextProbablePrime(); // Now I have the next prime to test for octets

			// (0, 2, 6, 8, 12, 18, 20, 26)
			result[0] = currentPrime;
			result[1] = currentPrime.add(TWO);
			result[2] = currentPrime.add(SIX);	
			result[3] = currentPrime.add(new BigInteger("8"));
			result[4] = currentPrime.add(new BigInteger("12"));
			result[5] = currentPrime.add(new BigInteger("18"));
			result[6] = currentPrime.add(new BigInteger("20"));
			result[7] = currentPrime.add(new BigInteger("26"));
			if (isKPrime(result)) {
				if (addPrime(result)) {
					if (Settings.instance().getEnvironment() == DEVELOPMENT) {
						createCSVFile(result);
					}
					return result;
				}
			}
			
			// (0, 2, 6, 12, 14, 20, 24, 26)
			result[1] = currentPrime.add(TWO);	
			result[2] = currentPrime.add(SIX);	
			result[3] = currentPrime.add(new BigInteger("12"));
			result[4] = currentPrime.add(new BigInteger("14"));	
			result[5] = currentPrime.add(new BigInteger("20"));	
			result[6] = currentPrime.add(new BigInteger("24"));
			result[7] = currentPrime.add(new BigInteger("26"));
			if (isKPrime(result)) {
				if (addPrime(result)) {
					if (Settings.instance().getEnvironment() == DEVELOPMENT) {
						createCSVFile(result);
					}
					return result;
				}
			}
			
			// (0, 6, 8, 14, 18, 20, 24, 26)
			result[1] = currentPrime.add(SIX);	
			result[2] = currentPrime.add(new BigInteger("8"));	
			result[3] = currentPrime.add(new BigInteger("14"));
			result[4] = currentPrime.add(new BigInteger("18"));	
			result[5] = currentPrime.add(new BigInteger("20"));	
			result[6] = currentPrime.add(new BigInteger("24"));
			result[7] = currentPrime.add(new BigInteger("26"));
			if (isKPrime(result)) {
				if (addPrime(result)) {
					createCSVFile(result);
					return result;
				}
			}
		}
	}

	private void createCSVFile(BigInteger[] primes) throws IOException {
		Settings settings = Settings.instance();
	    FileWriter out = new FileWriter(settings.getOctapletFile(), true);
	    try (CSVPrinter printer = new CSVPrinter(out, CSVFormat.DEFAULT)) {
	            printer.printRecord(primes[0], primes[1], primes[2], primes[3], primes[4], primes[5], primes[6], primes[7]);
	    }
	}
}
