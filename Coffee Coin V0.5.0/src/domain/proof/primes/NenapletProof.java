package domain.proof.primes;

import java.io.IOException;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;

import domain.Asset;
import domain.Settings;

//
// Objects of this class proof the finding of a nenaplet (a K-prime constellation with a diameter of 9) not known
// by the chain before
//
public class NenapletProof extends KConstellationProof  {
	private static final long serialVersionUID = -8337541918409524143L;

	public NenapletProof(Object[] primes, Asset asset) throws NoSuchAlgorithmException, IOException {
		super(primes, asset);
	}

	@Override
	public boolean isValid() {
		return isValid(9);
	}
	
	protected BigInteger getkConstellationValue() {
		return Settings.instance().getNenapletValue();
	}
}
