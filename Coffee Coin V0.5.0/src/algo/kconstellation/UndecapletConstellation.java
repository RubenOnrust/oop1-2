package algo.kconstellation;

import static domain.Settings.Environment.*;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import domain.Settings;

public class UndecapletConstellation extends AbstractKConstellationPrimer {
	
	@Override
	public BigInteger[] getNextKPrime(BigInteger minimum) throws IOException {
		BigInteger[] result = new BigInteger[11];
		BigInteger currentPrime = new BigInteger(minimum.toByteArray()); // Simulating a clone
		while (true) {
			currentPrime = currentPrime.nextProbablePrime(); // Now I have the next prime to test for undecapletsness
			
			// (0  4  6  10  16  18  24  28  30  34  36)
			result[0] = currentPrime;
			result[1] = currentPrime.add(FOUR);
			result[2] = currentPrime.add(SIX);	
			result[3] = currentPrime.add(TEN);
			result[4] = currentPrime.add(SIXTEEN);
			result[5] = currentPrime.add(EIGHTEEN);
			result[6] = currentPrime.add(TWENTYFOUR);
			result[7] = currentPrime.add(TWENTYEIGHT);
			result[8] = currentPrime.add(THIRTY);
			result[9] = currentPrime.add(THIRTYFOUR);
			result[10] = currentPrime.add(THIRTYSIX);
			if (isKPrime(result)) {
				if (addPrime(result)) {
					if (Settings.instance().getEnvironment() == DEVELOPMENT) {
						createCSVFile(result);
					}
					return result;
				}
			}
			
			// (0  2  6  8  12  18  20  26  30  32  36)
			result[1] = currentPrime.add(TWO);
			result[2] = currentPrime.add(SIX);	
			result[3] = currentPrime.add(EIGHT);
			result[4] = currentPrime.add(TWELVE);
			result[5] = currentPrime.add(EIGHTEEN);
			result[6] = currentPrime.add(TWENTY);
			result[7] = currentPrime.add(TWENTYSIX);
			result[8] = currentPrime.add(THIRTY);
			result[9] = currentPrime.add(THIRTYTWO);
			result[10] = currentPrime.add(THIRTYSIX);
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
	    FileWriter out = new FileWriter(settings.getUndecapletFile(), true);
	    try (CSVPrinter printer = new CSVPrinter(out, CSVFormat.DEFAULT)) {
	            printer.printRecord(primes[0], primes[1], primes[2], primes[3], primes[4], primes[5], primes[6], primes[7], primes[8], primes[9], primes[10]);
	    }
	}
}
