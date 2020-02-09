package domain;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;

import util.hash.HasherFactory;
import util.hash.IHashable;

// The reward for creating a new block
// 
public class Reward implements IHashable {
	private static final long serialVersionUID = 155907804475413796L;
	private final Asset asset;
	private final BigInteger amount;
	
	public Reward(Asset asset, BigInteger amount) {
		super();
		this.asset = asset;
		this.amount = amount;
	}

	public Asset getAsset() {
		return asset;
	}

	public BigInteger getAmount() {
		return amount;
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
		Reward theOther = (Reward) other;
		// Checks for null values done
			
		BigInteger hashSelf, hashOther;
		try {
			hashSelf = new BigInteger(this.hash());
			hashOther = new BigInteger(theOther.hash());
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("Oeps. Crypto algorithm unknown.");
		}
		return hashSelf.equals(hashOther);
	}
}
