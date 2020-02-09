package domain.io;

import domain.RewardTransaction;

public interface IRewardTransactionStore {
	String get(RewardTransaction transaction); // Returns a string representation of the transaction

}
