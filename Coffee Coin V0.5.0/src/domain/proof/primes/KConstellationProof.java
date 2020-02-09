package domain.proof.primes;

import static domain.Settings.PRIME_CERTAINTY;

import java.io.IOException;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import domain.Asset;
import domain.Chain;
import domain.Reward;
import domain.Settings;
import domain.proof.Proof;
import util.BigIntegerArrayComparable;
import util.IllegalParameterException;

public abstract class KConstellationProof extends Proof {
	private static final long serialVersionUID = 805994052584093233L;
	private BigInteger[] primes;
	
	protected KConstellationProof(Object[] primes, Asset asset) throws NoSuchAlgorithmException, IOException {
		super();
		this.primes = new BigInteger[primes.length];
		
		// Check whether all objects are either String or BigInteger. If
		// of correct type, fill array slot.
		for (int i=0; i<primes.length; i++) {
			if (primes[i] instanceof String) {
				this.primes[i] = new BigInteger((String)primes[i]);
			}
			else if (primes[i] instanceof BigInteger) {
				this.primes[i] = (BigInteger) primes[i];
			}
			else {
				throw new IllegalParameterException("Parameters can only be of type String or of type BigInteger; parameter in slot " + i + " is not.");
			}
		}
		// Check whether it is a valid constellation. Checking whether it is unique
		// cannot be done here!
		if (!isValid()) {
			throw new IllegalParameterException("Input is not a valid K-constellation.");
		}
		setReward(new Reward(asset, BigInteger.ZERO));
	}
	
	@Override
	public boolean equals(Proof o) {
		if (o instanceof KConstellationProof) {
			KConstellationProof other = (KConstellationProof) o;
			return this.primes[0].equals(other.primes[0]);
		}
		else {
			return false;
		}
	}
	
	// Retrieve all already known K-constellations from the chain, so isUnique() and calculateReward() can use it
	protected List<BigInteger[]> getKnownKConstellations(Asset asset) throws NoSuchAlgorithmException, IOException {
		Chain chain = Chain.instance(asset);
		List<Proof> proofs = chain.getProofs(this.getClass(), true);
		List<BigInteger[]> knownKConstellations = new LinkedList<BigInteger[]>();
		for (Proof p: proofs) {
			KConstellationProof proof = (KConstellationProof) p;
			BigInteger[] constellation = proof.getPrimes();
			
			// Put the first one in the correct place
			if (knownKConstellations.size() == 0) {
				knownKConstellations.add(constellation);
				return knownKConstellations;
			}
			
			// Determine where the item belongs
			int index = 0;
			for (BigInteger[] big: knownKConstellations) {
				if (big[0].compareTo(constellation[0]) < 0) {
					index++;
				}
			}
			knownKConstellations.add(index, constellation);
		}
		return knownKConstellations;
	}
	
	@Override
	public Reward calculateReward(Asset asset) throws NoSuchAlgorithmException, IOException  {
		// Get the highest K-constellation of the give diameter in the system, not counting 
		// this one. Divide that constellation by this one, giving a higher number when 
		// finding a lower one (encouraging filling in the gaps instead of
		// aiming for a very high one.) Multiply by some constant
		
		Chain chain = Chain.instance(asset);
		List<Proof> proofs = chain.getProofs(this.getClass(), true);
		
		// Get the highest set of primes. 
		BigInteger highest = BigInteger.ZERO;
		Date datetimeHighest = new Date();
		for (Proof p: proofs) {
			KConstellationProof proof = (KConstellationProof) p;
			BigInteger[] primes = proof.getPrimes();
			if (primes[0].compareTo(highest) > 0) {
				datetimeHighest = proof.getTimestamp();
				highest = primes[0];
			}
		}
		
		// Divide the highest one found by the current one. Divide by 300.000 (5 minutes) and multiply by the number
		// of milliseconds since the same prooftype has been found.
		// Do the square root to dampen the effects of waiting to publish
		//
		long timefactor = (new Date().getTime() - datetimeHighest.getTime());
		timefactor = (timefactor == 0) ? 0 : (long) Math.sqrt(timefactor);
		Long primitiveResult = (((highest.multiply(this.getkConstellationValue()).divide(this.getPrimes()[0])).longValue()) * timefactor) / Settings.instance().getRandomDifficulty();
		primitiveResult = (primitiveResult < 1) ? 1 : primitiveResult;
		BigInteger result = new BigInteger(primitiveResult.toString());
		return new Reward(asset, result);
	}
	
	@Override
	public int compareTo(Proof other) {
		if (other instanceof KConstellationProof) {
			KConstellationProof arg0 = (KConstellationProof) other;
			BigIntegerArrayComparable c = new BigIntegerArrayComparable();
			return c.compare(this.getPrimes(), arg0.getPrimes());
		}
		else {
			return super.compareTo(other);
		}

	}
	
	@Override
	public boolean equals(Object other) {
		if (other == null) {
			return false;
		}
		if (!other.getClass().equals(this.getClass())) {
			return false;
		}
		KConstellationProof o = (KConstellationProof) other;
		BigInteger[] self = this.getPrimes();
		BigInteger[] diff = o.getPrimes();
		Arrays.sort(self);
		Arrays.sort(diff);
		return self.equals(other);
	}
	
	public boolean isValid(int size) {
		// Check size of the internal array
		if ((this.primes == null) || (this.primes.length != size)) {
			return false;
		}
		
		// Check whether all entries are prime
		for (int i=0; i<this.primes.length; i++) {
			if (!this.primes[i].isProbablePrime(PRIME_CERTAINTY)) {
				return false;
			}
		}
		return true;
	}
	
	public BigInteger[] getPrimes() {
		BigInteger[] result = new BigInteger[this.primes.length];
		for (int i=0; i<result.length; i++) {
			result[i] = new BigInteger(primes[i].toByteArray()); // Simulating a clone on BigInteger
		}
		return result;
	}
	
	@Override
	public Object[] getProofValues() {
		return getPrimes();
	}
	
	@Override
	public String getProofValuesTags() {
		StringBuilder result = new StringBuilder("\t\tprimes=\"");
		for (BigInteger prime: primes) {
			result.append(prime.toString());
			result.append(",");
		}
		result.deleteCharAt(result.length()-1);
		result.append("\">\n");
		return new String(result);
	}
	
	public abstract boolean isValid();
	protected abstract BigInteger getkConstellationValue();
}
