package miner.kconstellation;

import java.io.IOException;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;

import domain.Asset;
import domain.proof.Proof;
import miner.IMiner;

public interface IPrimeProofMiner extends IMiner {
	BigInteger getMinimum();
	Proof createProof(BigInteger[] result, Asset asset) throws NoSuchAlgorithmException, IOException;
}
