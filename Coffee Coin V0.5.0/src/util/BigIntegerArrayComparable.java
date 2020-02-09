package util;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Comparator;

public class BigIntegerArrayComparable implements Comparator<BigInteger[]> {

	@Override
	public int compare(BigInteger[] o1, BigInteger[] o2) {
		Arrays.sort(o1);
		Arrays.sort(o2);
		return o1[0].compareTo(o2[0]);
	}
}
