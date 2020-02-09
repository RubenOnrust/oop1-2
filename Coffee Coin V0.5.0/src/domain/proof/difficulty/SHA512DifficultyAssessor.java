package domain.proof.difficulty;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import domain.Asset;
import domain.Chain;
import domain.Settings;
import domain.proof.Proof;
import domain.proof.hash.SHA512Proof;

public class SHA512DifficultyAssessor implements IDifficultyAssessor {

	@Override
	public int getDifficulty(Date moment) throws NoSuchAlgorithmException, IOException {
		Chain chain = Chain.instance(Asset.getDefault());
		List<Proof> proofs = chain.getProofs(SHA512Proof.class, false);
		if (proofs.size() < 2) {
			return 16;
		}
		
		Collections.sort(proofs);
		int result = (Integer) proofs.get(proofs.size()-1).getProofValues()[3]; // Starting point
		
		// Get elapsed time between the last two found
		long last = proofs.get(proofs.size()-1).getTimestamp().getTime();;
		long previous = proofs.get(proofs.size()-2).getTimestamp().getTime();
		long elapsed = last - previous;
		long target = Settings.instance().getSHA512Difficulty();
		if (elapsed < target) {
			result++;
		}
		else {
			result--;
		}
		return result;
	}
}
