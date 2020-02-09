package domain.io;

import domain.GenesisRewardTransaction;

public interface IGenesisRewardTransactionStore {
	String get(GenesisRewardTransaction genesisTansaction); // Returns a string representation of the transaction

}
