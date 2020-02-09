package domain.cleaner;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import domain.Block;

public class CompacterBlock extends OrderingBlock {
	private static final long serialVersionUID = 5734184176502166541L;

	// Needs special RewardTransaction
	public CompacterBlock(Block previousBlock, CompactingRewardTransaction t) throws NoSuchAlgorithmException, IOException {
		super(previousBlock, t);
		// TODO Auto-generated constructor stub
	}

}
