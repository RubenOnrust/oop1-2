package domain.proof.hash;

import java.io.IOException;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

import algo.hash.SHA512;
import domain.Asset;
import domain.Chain;
import domain.Reward;
import domain.proof.IllegalProofException;
import domain.proof.Proof;
import domain.proof.difficulty.DifficultyAssessorFactory;
import util.MathUtil;

public class SHA512Proof extends Proof {
	private static final long serialVersionUID = 8739293724010814755L;
	private BigInteger original; // Excluding the nonce
	private long nonce;
	private BigInteger hash;
	private int difficulty; // The number of zero bits required for the proof to be valid
	
	// The difficulty is not given as a parameter to the constructor
	// Instead it needs to be calculated from all data available
	// This could turn out to be rather difficult, especially since we need to 
	// prevent attacks such as first dumping a lot of resources in it, then waiting
	// for the difficulty to drop very low, then getting a large number of results again
	//
	// Mind, this is an approach differing from Bitcoin, as we do not store the current difficulty
	// in the blocks (which would limit the block to one or perhaps more PoW algorithms
	// specified in advance
	//
	// Change the parameter difficulty to a Date giving at which time the difficulty needs to 
	// be determined. Have the constructor get a calculator object from a factory; that 
	// factory will provide objects for determining difficulty for many PoW algorithms
	//
	public SHA512Proof(BigInteger original, long nonce, BigInteger hash, Asset asset, Date moment) throws NoSuchAlgorithmException, IOException {
		super();
		this.original = original;
		this.nonce = nonce;
		this.hash = hash;
		this.difficulty = DifficultyAssessorFactory.getAssessor(this.getClass()).getDifficulty(moment);
		if (!isValid()) {
			throw new IllegalProofException("Hash incorrect or number of leading zero bits too low");
		}
		super.reward = calculateReward(asset);
	}
	
	public SHA512Proof(BigInteger original, long nonce, BigInteger hash, Asset asset) throws NoSuchAlgorithmException, IOException {
		this(original, nonce, hash, asset, new Date());
	}

	@Override
	public boolean equals(Proof o) {
		if (o instanceof SHA512Proof) {
			SHA512Proof other = (SHA512Proof) o;
			return this.hash.equals(other.hash);
		}
		else {
			return false;
		}
	}

	//
	// This proof is valid if the original indeed hashes to the hash and the number of leading zero bits is at least equal to difficulty
	// The number of hashing rounds will be the 2log of the difficulty, rounded down.
	// Added: also check whether if this proof is not yet in the chain, it used the current Merkle root value
	// (if it is in the chain, the chain will take care of integrity.)
	//
	@Override
	public boolean isValid() throws NoSuchAlgorithmException, IOException {
		BigInteger toHash = original;
		Chain chain = Chain.instance(Asset.getDefault());
		List<Proof> existingProofs = chain.getProofs(SHA512Proof.class,  false);
		if (!existingProofs.contains(this)) {
			toHash = chain.getMerkleRoot();
		}
		int rounds = MathUtil.binlog(difficulty);
		for (int i=0; i<rounds; i++) {
			toHash = hash(toHash, nonce);
		}	
		if (!toHash.equals(hash)) {
			return false;
		}
		return MathUtil.checkTrailingZeroes(toHash, difficulty);
	}
	
	private BigInteger hash(BigInteger input, long nonce) {
		byte[] toHash = input.toByteArray();
		byte[] nonceBytes = MathUtil.longtoBytes(nonce);
		byte[] originalBytes = new byte[toHash.length + nonceBytes.length];
		System.arraycopy(toHash, 0, originalBytes, 0, toHash.length);
		System.arraycopy(nonceBytes, 0, originalBytes, toHash.length, nonceBytes.length);
		BigInteger hashed = new BigInteger(SHA512.hash(originalBytes), 16);
		return hashed;
	}

	@Override
	public Reward calculateReward(Asset asset) throws NoSuchAlgorithmException, IOException {
		return new Reward(Asset.getDefault(), BigInteger.ONE);
	}

	@Override
	public Object[] getProofValues() {
		Object[] result = new Object[4];
		result[0] = original;
		result[1] = nonce;
		result[2] = hash;
		result[3] = difficulty;
		return result;
	}

	@Override
	public String getProofValuesTags() {
		StringBuilder result = new StringBuilder("\t\toriginal=\"");
		result.append(original.toString(16));
		result.append(("\"\n\t\tnonce=\""));
		result.append(nonce);
		result.append(("\"\n\t\t\thash=\""));
		result.append(hash.toString(16));
		result.append(("\"\n\t\tdifficulty=\""));
		result.append(difficulty);
		result.append(("\"\n\t\ttrailingZeroes=\""));
		result.append(MathUtil.numberOfTrailingZeroes(hash));
		result.append("\">\n");
		return new String(result);
	}

}
