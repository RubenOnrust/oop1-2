package miner.kconstellation;

import java.io.IOException;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;

import algo.kconstellation.OctapletConstellation;
import domain.Account;
import domain.Asset;
import domain.proof.Proof;
import domain.proof.primes.OctapletProof;

public class OctapletMiner extends AbstractKConstellationMiner {
	
	public OctapletMiner(Asset asset, Account to) throws NoSuchAlgorithmException, IOException {
		super(OctapletProof.class, asset, new OctapletConstellation(), to);
	}
	
	public Proof createProof(BigInteger[] result, Asset asset) throws NoSuchAlgorithmException, IOException {
		return new OctapletProof(result, asset);
	}
	
	@Override
	public OctapletMiner clone() {
		try {
			return new OctapletMiner(asset, to);
		} catch (NoSuchAlgorithmException | IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		return null;
	}
}
