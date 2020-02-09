package domain;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;

import util.hash.HasherFactory;
import util.hash.IHashable;

public class Asset implements IHashable {
	private static final long serialVersionUID = 5406418539870576425L;
	private final String ticker;
	private final String name;
	private final String description;
	
	public Asset(String ticker, String name, String description) {
		super();
		this.ticker = ticker;
		this.name = name;
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public String getTicker() {
		return ticker;
	}

	@Override
	public byte[] hash() throws NoSuchAlgorithmException {
		return HasherFactory.hasher().hash(this);
	}

	@Override
	public int hashCode() {
		final int prime = 991;
		int result = 1;
		result = prime * result + ((ticker == null) ? 0 : ticker.hashCode());
		return result;
	}

	@Override
	public boolean equals(IHashable other) {
		if (this == other)
			return true;
		if (other == null)
			return false;
		if (getClass() != other.getClass())
			return false;
		Asset theOther = (Asset) other;
		if (ticker == null) {
			if (theOther.ticker != null) {
				return false;
			}
		}
		// Checks for null values done
			
		BigInteger hashSelf, hashOther;
		try {
			hashSelf = new BigInteger(this.hash());
			hashOther = new BigInteger(other.hash());
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("Oeps. Crypto algorithm unknown.");
		}
		return hashSelf.equals(hashOther);
	}
	
	public static Asset getDefault() {
		return new Asset("COF", "Coffee Coin","If the world runs on this, everyone would be active.");
	}
}
