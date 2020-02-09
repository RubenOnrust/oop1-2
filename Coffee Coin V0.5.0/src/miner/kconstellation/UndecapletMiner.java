package miner.kconstellation;

import java.io.IOException;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;

import algo.kconstellation.UndecapletConstellation;
import domain.Account;
import domain.Asset;
import domain.proof.Proof;
import domain.proof.primes.UndecapletProof;

public class UndecapletMiner extends AbstractKConstellationMiner {
	
	public UndecapletMiner(Asset asset, Account to) throws NoSuchAlgorithmException, IOException {
		super(UndecapletProof.class, asset, new UndecapletConstellation(), to);
	}
	
	public Proof createProof(BigInteger[] result, Asset asset) throws NoSuchAlgorithmException, IOException {
		return new UndecapletProof(result, asset);
	}
	
	@Override
	public UndecapletMiner clone() {
		try {
			return new UndecapletMiner(asset, to);
		} catch (NoSuchAlgorithmException | IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		return null;
	}
}
