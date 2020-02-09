package domain.io.xml;

import domain.io.IMessageTransactionStore;

public class MessageTransactionStoreFactory {
	private static MessageTransactionStoreFactory factory = null;

	
	private MessageTransactionStoreFactory() {

	}
	
	public static MessageTransactionStoreFactory instance() {
		if (factory == null) {
			factory = new MessageTransactionStoreFactory();
		}
		return factory;
	}
	
	public IMessageTransactionStore get() {
		return MessageTransactionStoreXML.instance();
	}
}
