package proof;

import java.io.IOException;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;

import domain.Asset;
import domain.Reward;
import domain.proof.Proof;
import util.IllegalParameterException;

//
// This proof is just to test whether wildly different proof types can still be in the same architecture
// The proof is found by rolling random numbers until the chance asked has been met
// It is of course valid from then on
// NO VALUE IN PRODUCTION, NONE AT ALL
//

public class RandomProof extends Proof {
	private static final long serialVersionUID = -699851557651269570L;
	private double valueFound;
	private long chance; // Given as a 1-in-chance value, so 100 means 1% chance

	public RandomProof(double valueFound, long chance, Asset asset) throws NoSuchAlgorithmException, IOException {
		super();
		this.valueFound = valueFound;
		this.chance = chance;
		if (!isValid()) {
			throw new IllegalParameterException("Input is not a valid Random Proof.");
		}
		super.reward = calculateReward(asset);
	}

	@Override
	public boolean equals(Proof o) {
		if (o instanceof RandomProof) {
			return false;
		}
		else {
			return false;
		}
	}

	// Object 1 is a Double for valueFound
	// Object 2 is a Long for chance
	//
	@Override
	public boolean isValid() {
		double toRoll = Math.pow(10, (0 - chance));
		if (toRoll >= valueFound) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public Reward calculateReward(Asset asset) throws NoSuchAlgorithmException, IOException {
		return new Reward(Asset.getDefault(), BigInteger.ONE);
	}

	@Override
	public Object[] getProofValues() {
		Object[] result = new Object[2];
		result[0] = valueFound;
		result[1] = chance;
		return result;
	}
	
	@Override
	public String getProofValuesTags() {
		StringBuilder result = new StringBuilder("\t\tvalue=\"");
		result.append(String.format("%.12f",valueFound));
		result.append(("\"\n\t\tchance=\""));
		result.append(chance);
		result.append("\">\n");
		return new String(result);
	}
}
