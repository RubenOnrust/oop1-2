package domain.io;

import domain.MessageTransaction;

public interface IMessageTransactionStore {
	String get(MessageTransaction transaction); // Returns a string representation of the messagetransaction

}
