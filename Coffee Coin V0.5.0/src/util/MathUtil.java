package util;

import java.math.BigInteger;

public class MathUtil {
	public static byte[] longtoBytes(long data) {
		 return new byte[]{
			 (byte) ((data >> 56) & 0xff),
			 (byte) ((data >> 48) & 0xff),
			 (byte) ((data >> 40) & 0xff),
			 (byte) ((data >> 32) & 0xff),
			 (byte) ((data >> 24) & 0xff),
			 (byte) ((data >> 16) & 0xff),
			 (byte) ((data >> 8) & 0xff),
			 (byte) ((data >> 0) & 0xff),
		 };
	}
	
	public static boolean checkLeadingZeroes(BigInteger hash, int difficulty) {
		String binary = hash.toString(2);
		StringBuilder sb = new StringBuilder();
		for (int i=0; i<difficulty; i++) {
			sb.append("0");
		}
		String reference = new String(sb);
		if (binary.startsWith(reference)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public static boolean checkTrailingZeroes(BigInteger hash, int difficulty) {
		String binary = hash.toString(2);
		StringBuilder sb = new StringBuilder();
		for (int i=0; i<difficulty; i++) {
			sb.append("0");
		}
		String reference = new String(sb);
		if (binary.endsWith(reference)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public static int numberOfTrailingZeroes(BigInteger hash) {
		String binary = hash.toString(2);
		int result = 0;
		while (binary.endsWith("0")) {
			result++;
			binary = binary.substring(0, binary.length()-1);
		}
		return result;
	}
	
	// Gives the 2 log of an integer; returns 0 for bits=0
	public static int binlog( int bits )  {
		int log = 0;
	    if ((bits & 0xffff0000 ) != 0 ) { 
	    	bits >>>= 16; log = 16;
	    }
	    if (bits >= 25) { 
	    	bits >>>= 8; log += 8; 
	    }
	    if (bits >= 16) {
	    	bits >>>= 4; log += 4;
	    }
	    if (bits >= 4) {
	    	bits >>>= 2; log += 2;
	    }
	    return log + ( bits >>> 1 );
	}
}
