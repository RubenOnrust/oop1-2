package miner.kconstellation;

import java.io.IOException;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;

import algo.kconstellation.TridecapletConstellation;
import domain.Account;
import domain.Asset;
import domain.proof.Proof;
import domain.proof.primes.TridecapletProof;

public class TridecapletMiner extends AbstractKConstellationMiner {
	
	public TridecapletMiner(Asset asset, Account to) throws NoSuchAlgorithmException, IOException {
		super(TridecapletProof.class, asset, new TridecapletConstellation(), to);
	}
	
	public Proof createProof(BigInteger[] result, Asset asset) throws NoSuchAlgorithmException, IOException {
		return new TridecapletProof(result, asset);
	}
	
	@Override
	public TridecapletMiner clone() {
		try {
			return new TridecapletMiner(asset, to);
		} catch (NoSuchAlgorithmException | IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		return null;
	}
}
