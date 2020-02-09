package domain.proof.primes;

import java.io.IOException;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;

import domain.Asset;
import domain.Settings;

//
// Objects of this class proof the finding of a septaplet (a K-prime constellation with a diameter of 7) not known
// by the chain before
//
public class SeptapletProof extends KConstellationProof  {
	private static final long serialVersionUID = -2838130289618406305L;

	public SeptapletProof(Object[] primes, Asset asset) throws NoSuchAlgorithmException, IOException {
		super(primes, asset);
	}

	@Override
	public boolean isValid() {
		return isValid(7);
	}
	
	protected BigInteger getkConstellationValue() {
		return Settings.instance().getSeptapletValue();
	}
}
