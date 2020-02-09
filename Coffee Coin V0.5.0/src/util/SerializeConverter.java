package util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class SerializeConverter {
	
	public static byte[] objectToByteArray(Serializable input) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream oos;
		try {
			oos = new ObjectOutputStream(bos);
				oos.writeObject(input);
				oos.flush();
		} 
		catch (IOException e) {
			e.printStackTrace();
			throw new IllegalStateException(e);
		}
		return bos.toByteArray();
	}
}
