package domain;

import java.math.BigInteger;
import java.util.List;
import java.util.SortedSet;

import domain.factory.WalletFactory;
import util.collection.DynamicSortedSet;
import util.pattern.IObserver;
import util.pattern.Observable;

//
// A Wallet is the collection of accounts the user works with. Only those accounts are to be shown in the GUI
// The currentAccount attribute is the account currently used to do a transaction with. It is also in the collection.
// If the balance of the currentAccount is too small for the transaction, a reroute to merging accounts needs to be done.
//
public class Wallet extends Observable implements IObserver  {
	private static Wallet internalObject = null;
	private DynamicSortedSet<Account> accounts;
	private Account currentAccount;
	
	public static synchronized Wallet instance() {
		if (internalObject == null) {
			internalObject = new Wallet();
		}
		return internalObject;
	}
	
	private Wallet() {
		accounts = new DynamicSortedSet<Account>(new DefaultAccountComparator());
		List<Account> temp = WalletFactory.instance().getAllAccounts();
		for (Account account: temp) {
			accounts.add(account);
			account.addObserver(this);
		}
		notifyObservers();
	}

	public Account getCurrentAccount() {
		return currentAccount;
	}

	public void setCurrentAccount(Account currentAccount) {
		this.currentAccount = currentAccount;
	}

	public synchronized SortedSet<Account> getAccounts() {
		return accounts;
	}
	
	public synchronized void addAccount(Account account) {
		accounts.add(account);
		if (accounts.size() == 1) {
			setCurrentAccount(account);
		}
		account.addObserver(this);
		notifyObservers();
	}
	
	public synchronized boolean deleteAccount(Account account) {
		boolean result = accounts.remove(account);
		if (result) {
			if (currentAccount.equals(account)) {
				currentAccount = null;
			}
			account.deleteObserver(this);
		}
		notifyObservers();
		return result;
	}
	
	public Account get(int index) {
		int i = 0;
		for (Account anAccount: accounts) {
			if (index == i) {
				return anAccount;
			}
			i++;
		}
		return null;
	}
	
	// Returns the label associated with a public key if known;
	// returns null if unknown
	//
	public String getLabel(BigInteger publicKey) {
		for (Account account: accounts) {
			if (account.getPublicHash().equals(publicKey)) {
				return account.getLabel();
			}
		}
		return null;
	}
	
	public BigInteger getTotalBalance() {
		BigInteger result = BigInteger.ZERO;
		for (Account account: accounts) {
			result = result.add(account.getBalance());
		}
		return result;
	}

	@Override
	public void update(Observable arg0) {
		notifyObservers();
	}

	public int size() {
		return accounts.size();
	}
}
