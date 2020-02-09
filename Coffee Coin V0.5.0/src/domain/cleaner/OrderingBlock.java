package domain.cleaner;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import domain.Block;

public class OrderingBlock extends Block {
	private static final long serialVersionUID = -411805809823152884L;

	public OrderingBlock(Block previousBlock, OrderingRewardTransaction t) throws NoSuchAlgorithmException, IOException {
		super(previousBlock, t);
		// TODO Auto-generated constructor stub
	}

}
