package domain.cleaner;

import java.security.NoSuchAlgorithmException;

import domain.Account;
import domain.RewardTransaction;
import domain.proof.Proof;

public class OrderingRewardTransaction extends RewardTransaction {
	private static final long serialVersionUID = 4368585251826167934L;

	public OrderingRewardTransaction(Proof proof, Account to) throws NoSuchAlgorithmException {
		super(proof, to);
		// TODO Auto-generated constructor stub
	}

}
