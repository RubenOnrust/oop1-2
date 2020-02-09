package miner.kconstellation;

import java.io.IOException;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;

import algo.kconstellation.NenapletConstellation;
import domain.Account;
import domain.Asset;
import domain.proof.Proof;
import domain.proof.primes.NenapletProof;

public class NenapletMiner extends AbstractKConstellationMiner {
	
	public NenapletMiner(Asset asset, Account to) throws NoSuchAlgorithmException, IOException {
		super(NenapletProof.class, asset, new NenapletConstellation(), to);
	}
	
	public Proof createProof(BigInteger[] result, Asset asset) throws NoSuchAlgorithmException, IOException {
		return new NenapletProof(result, asset);
	}
	
	@Override
	public NenapletMiner clone() {
		try {
			return new NenapletMiner(asset, to);
		} catch (NoSuchAlgorithmException | IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		return null;
	}
}
