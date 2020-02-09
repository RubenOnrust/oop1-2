package algo.kconstellation;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import domain.Settings;

public class PentapletConstellation extends AbstractKConstellationPrimer {
	
	@Override
	public BigInteger[] getNextKPrime(BigInteger minimum) throws IOException {
		BigInteger[] result = new BigInteger[5];
		BigInteger currentPrime = new BigInteger(minimum.toByteArray()); // Simulating a clone
		while (true) {
			currentPrime = currentPrime.nextProbablePrime(); // Now I have the next prime to test for pentapletness
			
			// (0, 2, 6, 8, 12)
			
			result[0] = currentPrime;
			result[1] = currentPrime.add(TWO);
			result[2] = currentPrime.add(SIX);	
			result[3] = currentPrime.add(EIGHT);
			result[4] = currentPrime.add(TWELVE);
			if (isKPrime(result)) {
				if (addPrime(result)) {
					writeCSVFile(result);
					return result;
				}
			}
			
			// (0, 4, 6, 10, 12)
			
			result[1] = currentPrime.add(FOUR);	
			result[2] = currentPrime.add(SIX);	
			result[3] = currentPrime.add(TEN);
			result[4] = currentPrime.add(TWELVE);	
			if (isKPrime(result)) {
				if (addPrime(result)) {
					writeCSVFile(result);
					return result;
				}
			}
		}
	}
	
	private void writeCSVFile(BigInteger[] primes) throws IOException {
		Settings settings = Settings.instance();
	    FileWriter out = new FileWriter(settings.getPentapletFile(), true);
	    try (CSVPrinter printer = new CSVPrinter(out, CSVFormat.DEFAULT)) {
	            printer.printRecord(primes[0], primes[1], primes[2], primes[3], primes[4]);
	    }
	}
}