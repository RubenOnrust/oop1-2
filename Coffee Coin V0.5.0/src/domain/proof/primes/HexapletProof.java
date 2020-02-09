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
public class HexapletProof extends KConstellationProof  {
	private static final long serialVersionUID = 2637777073798884785L;

	public HexapletProof(Object[] primes, Asset asset) throws NoSuchAlgorithmException, IOException {
		super(primes, asset);
	}

	@Override
	public boolean isValid() {
		return isValid(6);
	}
	
	protected BigInteger getkConstellationValue() {
		return Settings.instance().getHexapletValue();
	}
}
