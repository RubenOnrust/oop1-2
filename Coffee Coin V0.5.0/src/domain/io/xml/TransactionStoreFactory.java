package domain.io.xml;

import domain.io.ITransactionStore;

public class TransactionStoreFactory {
	private static TransactionStoreFactory factory = null;

	
	private TransactionStoreFactory() {

	}
	
	public static TransactionStoreFactory instance() {
		if (factory == null) {
			factory = new TransactionStoreFactory();
		}
		return factory;
	}
	
	public ITransactionStore get() {
		return TransactionStoreXML.instance();
	}
}
