package util.collection;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.lang3.ArrayUtils;

import domain.Asset;
import util.MathUtil;
import util.WrappedString;
import util.hash.HasherFactory;
import util.hash.IHashable;

public class MerkleTree<T extends IHashable> implements IHashable {
	private static final long serialVersionUID = -5588833069630124506L;
	private BigInteger[][] hashes; // A ragged array to store the hashes in
	private int size = 0; // Number if items in tree
	
	public static void main(String[] args) throws NoSuchAlgorithmException {
		
		// Sample code for building a Merkle Tree
		MerkleTree<WrappedString> tree = new MerkleTree<>(new WrappedString("Testing"));
		tree.add(new WrappedString("Another test"));
		tree.add(new WrappedString("Yet another test"));
		tree.add(new WrappedString("Bilbo"));
		tree.add(new WrappedString("Balings"));
		tree.add(new WrappedString("Apple pie"));
		tree.add(new WrappedString("Meat pie"));
		tree.add(new WrappedString("Humble pie"));
		tree.add(new WrappedString("Humble pies"));
		System.out.println(tree);
	}
	
	// The first element needs special treatment
	public MerkleTree(T element) throws NoSuchAlgorithmException {
		hashes = new BigInteger[2][];
		hashes[0] = new BigInteger[1];
		hashes[1] = new BigInteger[2];
		hashes[1][0] = new BigInteger(element.hash());
		hashes[1][1] = BigInteger.ZERO;
		byte[] needsHashing = ArrayUtils.addAll(hashes[1][0].toByteArray(), hashes[1][1].toByteArray());
		hashes[0][0] = hash(needsHashing);
		size = 1;
	}
	
	public BigInteger getRoot() {
		return hashes[0][0];
	}
	
	// Start has to be a clear power of 2. End+1 has to be a clear power of 2, and larger than start. Returns Merkle hash for (start, end).
	public BigInteger getHash(int start, int end) {
		if (((start % 2) != 0) || (((end+1) % 2) != 0)) {
			throw new IllegalArgumentException("Start and end need to be powers of 2");
		}
		if (start >= end) {
			throw new IllegalArgumentException("Start needs to be smaller than end");
		}
		
		// The layer is determined by the number of items in the hash (being the 2 log of the number of items)
		int layer = hashes.length - MathUtil.binlog(end - start + 1) - 1;
		
		// The index in the layer is determined by the starting number
		int temp = MathUtil.binlog(end - start + 1);
		int temp2 = (int) java.lang.Math.pow(2.0, temp);
		int index = start / temp2;		
		return hashes[layer][index];
	}
	
	public void add(T element) throws NoSuchAlgorithmException {
		// Determine number of layers needed. It is equal to 2 log (#items) + 1 (the 1 being the lead nodes)
		// Number of layers needs to be rounded up: 
		// 1-2 item -> 2 layers
		// 3-4 items -> 3 layers
		// 5-8 items -> 4 layers
		// 9-15 items -> 5 layers
		// So calculate depth. Calculate depth for 1 less item.  If they are the same, add 2 to depth. If different, add 1.
		
		int neededDepth;
		int depthMore = MathUtil.binlog(size+1);
		int depthLess = MathUtil.binlog(size);
		if (depthMore == depthLess) {
			neededDepth = depthLess + 2;
		}
		else {
			neededDepth = depthLess + 1;
		}
		
		// If depth needs to increase, do so
		if (neededDepth > hashes.length) {
			increaseDepth();
		}
		
		// Find the slot to put the value in. This is just the lowest layer of the array, slot #size. 
		// Put in the new value.
		hashes[hashes.length-1][size] = new BigInteger(element.hash());
		
		// Rehash all the layers above
		int index = size;
		for (int i=hashes.length-2; i>-0; i--) {
			// A left slot or a right slot need to be treated somewhat differently
			byte[] needsHashing;
			if ((index % 2) == 1) {
				// Right leaf
				needsHashing = ArrayUtils.addAll(hashes[hashes.length-1][index].toByteArray(), hashes[hashes.length-1][index-1].toByteArray());
			}
			else {
				// Left leaf
				needsHashing = ArrayUtils.addAll(hashes[hashes.length-1][index].toByteArray(), hashes[hashes.length-1][index+1].toByteArray());
			}
			// Use this to hash for the level above. Half the index, rounding down. This is the index in layer above.
			index /=  2;
			hashes[i][index] = hash(needsHashing);
		}
		size++;
	}
	
	// The following method relies on a simple hashing mechanism, which is fine for now
	private BigInteger hash(byte[] input) throws NoSuchAlgorithmException {
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		return new BigInteger(digest.digest(input));
	}
	
	private void increaseDepth() throws NoSuchAlgorithmException {
		BigInteger[][] result = new BigInteger[hashes.length+1][];
		result[0] = new BigInteger[1];
		// Loop over the original tree
		for (int i=0; i<hashes.length; i++) {
			// For each layer, initialize a new layer double its size
			result[i+1] = new BigInteger[hashes[i].length * 2];
			// Loop over the original layer and copy its contents to the new layer's left half
			for (int j=0; j<hashes[i].length; j++) {
				result[i+1][j] = hashes[i][j];
			}
			// Loop over the other half to fill it with zero
			for (int j=hashes[i].length; j<result[i+1].length; j++) {
				result[i+1][j] = BigInteger.ZERO;
			}
		}
		result[0][0] = hash(ArrayUtils.addAll(hashes[1][0].toByteArray(), hashes[1][1].toByteArray()));
		hashes = result;
	}
	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append("Size of tree: " + size + "\n");
		for (int i=0; i<hashes.length; i++) {
			result.append("(");
			for (int j=0; j<hashes[i].length; j++) {
				result.append(hashes[i][j]);
				result.append(",");
			}
			result.deleteCharAt(result.length()-1);
			result.append(")\n");
		}
		return new String(result);
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
		Asset theOther = (Asset) other;
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
