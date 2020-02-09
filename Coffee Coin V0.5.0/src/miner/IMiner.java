package miner;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import domain.Asset;
import domain.proof.Proof;

public interface IMiner extends Runnable {
	void stop();
	long getExpectedProfit();
	Proof createProof(Asset asset, Object... result) throws NoSuchAlgorithmException, IOException;
}
