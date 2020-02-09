package miner.kconstellation;

import java.io.IOException;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;

import algo.kconstellation.SeptapletConstellation;
import domain.Account;
import domain.Asset;
import domain.proof.Proof;
import domain.proof.primes.SeptapletProof;

public class SeptapletMiner extends AbstractKConstellationMiner {
	
	public SeptapletMiner(Asset asset, Account to) throws NoSuchAlgorithmException, IOException {
		super(SeptapletProof.class, asset, new SeptapletConstellation(), to);
	}
	
	public Proof createProof(BigInteger[] result, Asset asset) throws NoSuchAlgorithmException, IOException {
		return new SeptapletProof(result, asset);
	}
	
	@Override
	public SeptapletMiner clone() {
		try {
			return new SeptapletMiner(asset, to);
		} catch (NoSuchAlgorithmException | IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		return null;
	}
}
