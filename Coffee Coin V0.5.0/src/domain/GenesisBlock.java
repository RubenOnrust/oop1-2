package domain;

import java.security.NoSuchAlgorithmException;
import java.util.SortedSet;

import domain.proof.Proof;

public class GenesisBlock extends Block {
	private static final long serialVersionUID = 6803755515790085121L;

	public GenesisBlock(SortedSet<Proof> proofs) throws NoSuchAlgorithmException {
		super(new GenesisRewardTransaction(proofs));
	}
}
