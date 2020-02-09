package miner.kconstellation;

import java.io.IOException;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;

import algo.kconstellation.PentapletConstellation;
import domain.Account;
import domain.Asset;
import domain.proof.Proof;
import domain.proof.primes.PentapletProof;

public class PentapletMiner extends AbstractKConstellationMiner {
	
	public PentapletMiner(Asset asset, Account to) throws NoSuchAlgorithmException, IOException {
		super(PentapletProof.class, asset, new PentapletConstellation(), to);
	}
	
	public Proof createProof(BigInteger[] result, Asset asset) throws NoSuchAlgorithmException, IOException {
		return new PentapletProof(result, asset);
	}
	
	@Override
	public PentapletMiner clone() {
		try {
			return new PentapletMiner(asset, to);
		} catch (NoSuchAlgorithmException | IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		return null;
	}
}
