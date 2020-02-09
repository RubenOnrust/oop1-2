package util.hash;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;

public interface IHashable extends Serializable {
	byte[] hash() throws NoSuchAlgorithmException; // Returns a hashed version of Serializable.toByteArray();
	boolean equals(IHashable other); // Overriding the default equals. Implementation should check hashcode;
									// if those are not equal, objects are not equal
}
