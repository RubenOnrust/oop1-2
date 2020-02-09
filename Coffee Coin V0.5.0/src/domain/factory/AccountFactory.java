package domain.factory;

import static domain.Settings.Environment.DEVELOPMENT;

import java.util.ArrayList;
import java.util.List;

import domain.Account;
import domain.Settings;

public class AccountFactory {
	private static AccountFactory innerObject = null;
	private List<Account> accounts;
	
	public static AccountFactory instance() {
		if (innerObject == null) {
			innerObject = new AccountFactory();
		}
		return innerObject;
	}
	
	private AccountFactory() {
		accounts = new ArrayList<>();
		if (Settings.instance().getEnvironment() == DEVELOPMENT) {
			accounts = TestDataFactory.getTestAccounts();
		}
	}
	
	public List<Account> getAll() {
		List<Account> result = new ArrayList<>();
		for (Account a: accounts) {
			result.add(a);
		}
		return result;
	}
	
	public Account get(int index) {
		return accounts.get(index);
	}
	
	public int size() {
		return accounts.size();
	}
}
