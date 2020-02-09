package domain.cleaner;

import java.security.NoSuchAlgorithmException;

import domain.Account;
import domain.proof.Proof;

public class CompactingRewardTransaction extends OrderingRewardTransaction {
	private static final long serialVersionUID = -1963105849571048004L;

	public CompactingRewardTransaction(Proof proof, Account to) throws NoSuchAlgorithmException {
		super(proof, to);
		// TODO Auto-generated constructor stub
	}

}
