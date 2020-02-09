package domain.proof;

import java.io.IOException;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import domain.Asset;
import domain.Reward;
import util.hash.HasherFactory;
import util.hash.IHashable;

//
// A Proof object is the top level of proofs, describing any type of it.
// PrimeProofs are PoE, but this class also describes a SHA256 PoW.
//
public abstract class Proof implements IHashable, Comparable<Proof> {
	private static final long serialVersionUID = 6563024374846194757L;
	private Date timestamp;
	protected Reward reward; // The reward earned for this proof. Is calculated at object instantiation.

	protected Proof() {
		this.timestamp = new Date();
	}
	
	public Reward getReward() {
		return reward;
	}
	
	public void setReward(Reward reward) {
		this.reward = reward;
	}
	
	public Date getTimestamp() {
		return (Date) timestamp.clone();
	}
	
	@Override
	public byte[] hash() throws NoSuchAlgorithmException {
		return HasherFactory.hasher().hash(this);
	}
	
	@Override
	public boolean equals(IHashable other) {
		if (this == other)
			return true;
		if (other == null)
			return false;
		if (getClass() != other.getClass())
			return false;
		BigInteger hashSelf, hashOther;
		try {
			hashSelf = new BigInteger(this.hash());
			hashOther = new BigInteger(other.hash());
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("Oeps. Crypto algorithm unknown.");
		}
		return hashSelf.equals(hashOther);
	}
	
	@Override
	public int compareTo(Proof other) {
		return this.timestamp.compareTo(other.timestamp);
	}

	public abstract boolean equals(Proof other);
	public abstract boolean isValid() throws NoSuchAlgorithmException, IOException;
	public abstract Reward calculateReward(Asset asset) throws NoSuchAlgorithmException, IOException;
	public abstract Object[] getProofValues(); // For K-Constellations a list of prime numbers, for a hashing algorithm the nonce, parameters and resulting value etc
	public abstract String getProofValuesTags(); // An XML representation of the proof values
}
