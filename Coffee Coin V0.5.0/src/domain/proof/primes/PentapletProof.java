package domain.proof.primes;

import java.io.IOException;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;

import domain.Asset;
import domain.Settings;

//
// Objects of this class proof the finding of a hexaplet (a K-prime constellation with a diameter of 6) not known
// by the chain before
//
public class PentapletProof extends KConstellationProof  {
	private static final long serialVersionUID = 2637777073798884785L;

	public PentapletProof(Object[] primes, Asset asset) throws NoSuchAlgorithmException, IOException {
		super(primes, asset);
	}

	@Override
	public boolean isValid() {
		return isValid(5);
	}
	
	protected BigInteger getkConstellationValue() {
		return Settings.instance().getPentapletValue();
	}
}
