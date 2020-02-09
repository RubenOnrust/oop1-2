package domain.io.xml;

import java.math.BigInteger;

import domain.Account;
import domain.io.IAccountStore;

public class AccountStoreXML implements IAccountStore {
	private static IAccountStore store;
	
	public static IAccountStore instance() {
		if (store == null) {
			store = new AccountStoreXML();
		}
		return store;
	}
	
	private AccountStoreXML() {
		
	}

	@Override
	public String get(Account account) {
		BigInteger publicKey = account.getPublicHash();
		String key = publicKey.toString(16);
		StringBuilder result = new StringBuilder();
		result.append("<Account \tpublicKey=\"");
		result.append(key);
		result.append("\"\n\t\ttimestamp=\"");
		result.append(account.getTimestamp());
		result.append("\"\n\t\tbalance=\"");
		result.append(account.getBalance());
		result.append("\" />\n");
		return new String(result);
	}

}
