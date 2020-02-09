package miner.kconstellation;

import java.io.IOException;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;

import algo.kconstellation.HexapletConstellation;
import domain.Account;
import domain.Asset;
import domain.proof.Proof;
import domain.proof.primes.HexapletProof;

public class HexapletMiner extends AbstractKConstellationMiner {
	
	public HexapletMiner(Asset asset, Account to) throws NoSuchAlgorithmException, IOException {
		super(HexapletProof.class, asset, new HexapletConstellation(), to);
	}
	
	public Proof createProof(BigInteger[] result, Asset asset) throws NoSuchAlgorithmException, IOException {
		return new HexapletProof(result, asset);
	}
	
	@Override
	public HexapletMiner clone() {
		try {
			return new HexapletMiner(asset, to);
		} catch (NoSuchAlgorithmException | IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		return null;
	}
}
