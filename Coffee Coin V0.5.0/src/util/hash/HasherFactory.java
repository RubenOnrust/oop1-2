package util.hash;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import util.SerializeConverter;

public class HasherFactory {
	private static IHasher hasher = null;
	
	private HasherFactory() {
		// Blocking creation of objects of this class; only use static methods
	}
	
	public static IHasher hasher () {
		if (hasher == null) {
			hasher = new IHasher() {

				@Override
				public byte[] hash(Serializable input) {
					MessageDigest digest = null;
					try {
						digest = MessageDigest.getInstance("SHA-256");
					} catch (NoSuchAlgorithmException e) {
						throw new RuntimeException(e);
					}
					return digest.digest(SerializeConverter.objectToByteArray(input));
				}
			};
		}
		return hasher;
	}
}
