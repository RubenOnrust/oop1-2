package algo.kconstellation;

import static domain.Settings.Environment.DEVELOPMENT;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import domain.Settings;

public class HexapletConstellation extends AbstractKConstellationPrimer {
	
	@Override
	public BigInteger[] getNextKPrime(BigInteger minimum) throws IOException {
		BigInteger[] result = new BigInteger[6];
		BigInteger currentPrime = new BigInteger(minimum.toByteArray()); // Simulating a clone
		while (true) {
			currentPrime = currentPrime.nextProbablePrime(); // Now I have the next prime to test for hexaplets
			
			// (0, 4, 6, 10, 12, 16)
			result[0] = currentPrime;
			result[1] = currentPrime.add(FOUR);
			result[2] = currentPrime.add(SIX);	
			result[3] = currentPrime.add(new BigInteger("10"));
			result[4] = currentPrime.add(new BigInteger("12"));
			result[5] = currentPrime.add(new BigInteger("16"));
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
	
	@SuppressWarnings("unused")
	private void createCSVFile(BigInteger[] primes) throws IOException {
		Settings settings = Settings.instance();
	    FileWriter out = new FileWriter(settings.getHexapletFile(), true);
	    try (CSVPrinter printer = new CSVPrinter(out, CSVFormat.DEFAULT)) {
	            printer.printRecord(primes[0], primes[1], primes[2], primes[3], primes[4], primes[5]);
	    }
	}
}
