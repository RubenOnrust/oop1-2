package domain;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.SortedSet;

import domain.proof.Proof;

public class GenesisRewardTransaction extends RewardTransaction {
	private static final long serialVersionUID = -6979740971973703197L;
	private final SortedSet<Proof> proofs;

	public GenesisRewardTransaction(SortedSet<Proof> proofs) throws NoSuchAlgorithmException {
		super(proofs.first(), Account.getAssetBitBucket());
		this.proofs = Collections.synchronizedSortedSet(proofs);
		
		// Determine total amount
		BigInteger amount = BigInteger.ZERO;
		if ((proofs != null) && (!proofs.isEmpty())) {
			for (Proof proofOfReward: proofs) {
				amount.add(proofOfReward.getReward().getAmount());
			}
		}
		this.amount = amount;
	}
	
	public List<Proof> getProofs() {
		List<Proof> result = new LinkedList<>();
		for (Proof proof: proofs) {
			result.add(proof);
		}
		return result;
	}
}
