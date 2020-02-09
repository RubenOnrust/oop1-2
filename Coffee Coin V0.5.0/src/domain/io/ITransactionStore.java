package domain.io;

import domain.Transaction;

public interface ITransactionStore {
	String get(Transaction transaction); // Returns a string representation of the transaction

}
