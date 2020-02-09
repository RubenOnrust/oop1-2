package miner.kconstellation;

import java.io.IOException;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;

import algo.kconstellation.DodecapletConstellation;
import domain.Account;
import domain.Asset;
import domain.proof.Proof;
import domain.proof.primes.DodecapletProof;

public class DodecapletMiner extends AbstractKConstellationMiner {
	
	public DodecapletMiner(Asset asset, Account to) throws NoSuchAlgorithmException, IOException {
		super(DodecapletProof.class, asset, new DodecapletConstellation(), to);
	}
	
	public Proof createProof(BigInteger[] result, Asset asset) throws NoSuchAlgorithmException, IOException {
		return new DodecapletProof(result, asset);
	}
	
	@Override
	public DodecapletMiner clone() {
		try {
			return new DodecapletMiner(asset, to);
		} catch (NoSuchAlgorithmException | IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		return null;
	}
}
