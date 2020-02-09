package util.hash;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;

public interface IHasher {
	byte[] hash(Serializable input) throws NoSuchAlgorithmException; // Returns a hashed version of the input
}
