package domain.factory;

import static domain.Settings.Environment.DEVELOPMENT;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import domain.Account;
import domain.Settings;

public class WalletFactory {
	private static WalletFactory innerObject = null;
	private final List<Account> walletAccounts;
	
	public static WalletFactory instance() {
		if (innerObject == null) {
			innerObject = new WalletFactory();
		}
		return innerObject;
	}
	
	private WalletFactory() {
		walletAccounts = new ArrayList<Account>();
		if (Settings.instance().getEnvironment() == DEVELOPMENT) {
			List<Account> testAccounts = TestDataFactory.getTestAccounts();
			// The first few test accounts are reserved for miners not in the wallet
			Iterator<Account> i = testAccounts.iterator();
			for (int j=0; j<2; j++) {
				if (i.hasNext()) {
					i.next();
				}
			}
			// The other accounts go in the wallet
			while (i.hasNext()) {
				Account account = i.next();
				walletAccounts.add(account);
			}
		}
		else {
			// Load accounts from wallet file
		}
	}
	
	public List<Account> getAllAccounts() {
		return walletAccounts;
	}
}
