package util;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;

import util.hash.HasherFactory;
import util.hash.IHashable;

public class WrappedString implements IHashable {
	private static final long serialVersionUID = -3478141424016627883L;
	private String wrappedString;
	
	public WrappedString(String something) {
		super();
		this.wrappedString = something;
	}

	protected String getWrappedString() {
		return wrappedString;
	}

	protected void setWrappedString(String something) {
		this.wrappedString = something;
	}

	@Override
	public byte[] hash() throws NoSuchAlgorithmException {
		return HasherFactory.hasher().hash(this);
	}

	@Override
	public boolean equals(IHashable other) {
		BigInteger hashSelf, hashOther;
		try {
			hashSelf = new BigInteger(this.hash());
			hashOther = new BigInteger(other.hash());
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("Oeps. Crypto algorithm unknown.");
		}
		return hashSelf.equals(hashOther);
	}
}
