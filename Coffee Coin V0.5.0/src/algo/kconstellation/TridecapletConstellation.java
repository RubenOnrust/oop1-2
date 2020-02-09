package algo.kconstellation;

import static domain.Settings.Environment.DEVELOPMENT;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import domain.Settings;

public class TridecapletConstellation extends AbstractKConstellationPrimer {
	
	@Override
	public BigInteger[] getNextKPrime(BigInteger minimum) throws IOException {
		BigInteger[] result = new BigInteger[13];
		BigInteger currentPrime = new BigInteger(minimum.toByteArray()); // Simulating a clone
		while (true) {
			currentPrime = currentPrime.nextProbablePrime(); // Now I have the next prime to test for nenaplaetness
			
			// (0  6  12  16  18  22  28  30  36  40  42  46  48)
			result[0] = currentPrime;
			result[1] = currentPrime.add(SIX);
			result[2] = currentPrime.add(TWELVE);
			result[3] = currentPrime.add(SIXTEEN);
			result[4] = currentPrime.add(EIGHTEEN);	
			result[5] = currentPrime.add(TWENTYTWO);
			result[6] = currentPrime.add(TWENTYEIGHT);	
			result[7] = currentPrime.add(THIRTY);
			result[8] = currentPrime.add(THIRTYSIX);	
			result[9] = currentPrime.add(FOURTY);
			result[10] = currentPrime.add(FOURTYTWO);	
			result[11] = currentPrime.add(FOURTYSIX);
			result[12] = currentPrime.add(FOURTYEIGHT);	

			if (isKPrime(result)) {
				if (addPrime(result)) {
					if (Settings.instance().getEnvironment() == DEVELOPMENT) {
						createCSVFile(result);
					}
					return result;
				}
			}
			
			// (0  4  6  10  16  18  24  28  30  34  40  46  48)
			result[1] = currentPrime.add(FOUR);
			result[2] = currentPrime.add(SIX);
			result[3] = currentPrime.add(TEN);
			result[4] = currentPrime.add(SIXTEEN);	
			result[5] = currentPrime.add(EIGHTEEN);
			result[6] = currentPrime.add(TWENTYFOUR);	
			result[7] = currentPrime.add(TWENTYEIGHT);
			result[8] = currentPrime.add(THIRTY);	
			result[9] = currentPrime.add(THIRTYFOUR);
			result[10] = currentPrime.add(FOURTY);	
			result[11] = currentPrime.add(FOURTYSIX);
			result[12] = currentPrime.add(FOURTYEIGHT);	
			
			if (isKPrime(result)) {
				if (addPrime(result)) {
					if (Settings.instance().getEnvironment() == DEVELOPMENT) {
						createCSVFile(result);
					}
					return result;
				}
			}

			// (0  4  6  10  16  18  24  28  30  34  36  46  48)
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
			result[11] = currentPrime.add(FOURTYSIX);
			result[12] = currentPrime.add(FOURTYEIGHT);	
			
			if (isKPrime(result)) {
				if (addPrime(result)) {
					if (Settings.instance().getEnvironment() == DEVELOPMENT) {
						createCSVFile(result);
					}
					return result;
				}
			}
			
			// (0  2  6  8  12  18  20  26  30  32  36  42  48)
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
			result[11] = currentPrime.add(FOURTYTWO);
			result[12] = currentPrime.add(FOURTYEIGHT);	
			
			if (isKPrime(result)) {
				if (addPrime(result)) {
					if (Settings.instance().getEnvironment() == DEVELOPMENT) {
						createCSVFile(result);
					}
					return result;
				}
			}
			
			// (0  2  8  14  18  20  24  30  32  38  42  44  48)
			result[1] = currentPrime.add(TWO);
			result[2] = currentPrime.add(EIGHT);
			result[3] = currentPrime.add(FOURTEEN);
			result[4] = currentPrime.add(EIGHTEEN);	
			result[5] = currentPrime.add(TWENTY);
			result[6] = currentPrime.add(TWENTYFOUR);	
			result[7] = currentPrime.add(THIRTY);
			result[8] = currentPrime.add(THIRTYTWO);	
			result[9] = currentPrime.add(THIRTYEIGHT);
			result[10] = currentPrime.add(FOURTYTWO);	
			result[11] = currentPrime.add(FOURTYFOUR);
			result[12] = currentPrime.add(FOURTYEIGHT);	
			
			if (isKPrime(result)) {
				if (addPrime(result)) {
					if (Settings.instance().getEnvironment() == DEVELOPMENT) {
						createCSVFile(result);
					}
					return result;
				}
			}
						
			// (0  2  12  14  18  20  24  30  32  38  42  44  48)
			result[1] = currentPrime.add(TWO);
			result[2] = currentPrime.add(TWELVE);
			result[3] = currentPrime.add(FOURTEEN);
			result[4] = currentPrime.add(EIGHTEEN);	
			result[5] = currentPrime.add(TWENTY);
			result[6] = currentPrime.add(TWENTYFOUR);	
			result[7] = currentPrime.add(THIRTY);
			result[8] = currentPrime.add(THIRTYTWO);	
			result[9] = currentPrime.add(THIRTYEIGHT);
			result[10] = currentPrime.add(FOURTYTWO);	
			result[11] = currentPrime.add(FOURTYFOUR);
			result[12] = currentPrime.add(FOURTYEIGHT);	
			
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
	    FileWriter out = new FileWriter(settings.getTridecapletFile(), true);
	    try (CSVPrinter printer = new CSVPrinter(out, CSVFormat.DEFAULT)) {
	            printer.printRecord(primes[0], primes[1], primes[2], primes[3], primes[4], primes[5], primes[6], primes[7], primes[8], primes[9], primes[10], primes[11], primes[12]);
	    }
	}
}
