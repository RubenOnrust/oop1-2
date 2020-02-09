package miner.kconstellation;

import java.io.IOException;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;

import algo.kconstellation.DecapletConstellation;
import domain.Account;
import domain.Asset;
import domain.proof.Proof;
import domain.proof.primes.DecapletProof;

public class DecapletMiner extends AbstractKConstellationMiner {
	
	public DecapletMiner(Asset asset, Account to) throws NoSuchAlgorithmException, IOException {
		super(DecapletProof.class, asset, new DecapletConstellation(), to);
	}
	
	public Proof createProof(BigInteger[] result, Asset asset) throws NoSuchAlgorithmException, IOException {
		return new DecapletProof(result, asset);
	}
	
	@Override
	public DecapletMiner clone() {
		try {
			return new DecapletMiner(asset, to);
		} catch (NoSuchAlgorithmException | IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		return null;
	}
}
