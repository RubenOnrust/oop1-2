package domain.io.xml;

import domain.io.IAccountStore;

public class AccountStoreFactory {
	private static AccountStoreFactory factory = null;

	
	private AccountStoreFactory() {

	}
	
	public static AccountStoreFactory instance() {
		if (factory == null) {
			factory = new AccountStoreFactory();
		}
		return factory;
	}
	
	public IAccountStore get() {
		return AccountStoreXML.instance();
	}
}
