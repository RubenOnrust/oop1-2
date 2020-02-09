package domain;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import util.IllegalParameterException;
import util.WrappedString;
import util.hash.HasherFactory;
import util.hash.IHashable;
import util.pattern.Observable;

public class Account extends Observable implements IHashable, Comparable<Account>  {
	private static final long serialVersionUID = -9052557659004817333L;
	private final BigInteger publicHash;
	private final BigInteger privateHash;
	private transient BigInteger balance;
	private final Date timestamp; // timestamp creation
	private transient String label;
	private transient String description;
	private boolean unlimitedFunds = false; // Used to pay newly mined coins from
	//
	// In standard bitcoin-like implementations, Accounts do not carry a balance; a balance follows from transactions to and from the account
	// Disadvantage of that seems to be that in order to check a transaction, the whole chain needs to be checked.
	// I take a middle road here; an Account does have a balance, but is is not stored on the chain, only built
	// in memory when constructing the chain. The consequence is that only one asset type is possible.
	
	// While accounts are hashable, they do not need to be stored in full in the main chain. Only the public hash will be stored!
	
	public Account(BigInteger publicHash, BigInteger privateHash, String label, String description) {
		super();
		this.publicHash = publicHash;
		this.privateHash = privateHash;
		this.balance = BigInteger.ZERO;
		this. unlimitedFunds = false;
		this.timestamp = new Date();
		this.label = label;
		this.description = description;
	}
	
	public Account(String seed, boolean unlimitedFunds, String label, String description) {
		WrappedString wrapped = new WrappedString(seed);
		WrappedString reversed = new WrappedString(new String(new StringBuilder(seed).reverse()));
		try {
			this.publicHash = new BigInteger(HasherFactory.hasher().hash(wrapped)).abs();
			this.privateHash = new BigInteger(HasherFactory.hasher().hash(reversed)).abs();
			this.balance = BigInteger.ZERO; 
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
		this.unlimitedFunds = unlimitedFunds;
		this.timestamp = new Date();
		this.label = label;
		this.description = description;
	}
	
	// Bogus implementation, works for setting up software architecture
	public Account(String seed, String label, String description) {
		this.timestamp = new Date();
		this.label = label;
		this.description = description;
		WrappedString wrapped = new WrappedString(seed);
		WrappedString reversed = new WrappedString(new String(new StringBuilder(seed).reverse()));
		try {
			this.publicHash = new BigInteger(HasherFactory.hasher().hash(wrapped)).abs();
			this.privateHash = new BigInteger(HasherFactory.hasher().hash(reversed)).abs();
			this.balance = BigInteger.ZERO; 
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static final Account getNewAssetSource() {
		return new Account("The goose laying golden eggs. By some considered to be the ECB or the FED.", true, "Asset creator", null);
	}
	
	public static final Account getAssetBitBucket() {
		return new Account("Place to send assets to when they need to be gone. As in poof. Kind of system bank.", false, "Asset bitbucket",null);
	}

	public BigInteger getPublicHash() {
		return publicHash;
	}

	public BigInteger getPrivateHash() {
		return privateHash;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	@Override
	public byte[] hash() throws NoSuchAlgorithmException {
		return HasherFactory.hasher().hash(this);
	}
	
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	@Override
	public boolean equals(IHashable other) {
		if (this == other)
			return true;
		if (other == null)
			return false;
		if (getClass() != other.getClass())
			return false;
		Account theOther = (Account) other;
		// Checks for null values done
			
		BigInteger hashSelf, hashOther;
		try {
			hashSelf = new BigInteger(this.hash());
			hashOther = new BigInteger(theOther.hash());
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("Oeps. Crypto algorithm unknown.");
		}
		return hashSelf.equals(hashOther);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((privateHash == null) ? 0 : privateHash.hashCode());
		result = prime * result + ((publicHash == null) ? 0 : publicHash.hashCode());
		result = prime * result + ((timestamp == null) ? 0 : timestamp.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		if (privateHash == null) {
			if (other.privateHash != null)
				return false;
		} else if (!privateHash.equals(other.privateHash))
			return false;
		if (publicHash == null) {
			if (other.publicHash != null)
				return false;
		} else if (!publicHash.equals(other.publicHash))
			return false;
		if (timestamp == null) {
			if (other.timestamp != null)
				return false;
		} else if (!timestamp.equals(other.timestamp))
			return false;
		return true;
	}

	public void deposit(BigInteger amount) {
		if (amount.compareTo(BigInteger.ZERO) < 0) {
			throw new IllegalParameterException("One cannot deposit a negative amount.");
		}
		this.balance = this.balance.add(amount);
		notifyObservers();
	}
	
	public void withdraw(BigInteger amount) {
		if (amount.compareTo(BigInteger.ZERO) < 0) {
			throw new IllegalParameterException("One cannot withdraw a negative amount.");
		}
		if ((amount.compareTo(balance) > 0) && (!unlimitedFunds)) {
			throw new IllegalParameterException("Insufficient funds; balance is " + balance + ", amount is " + amount);
		}
		this.balance = this.balance.subtract(amount);
		notifyObservers();
	}
	
	public BigInteger getBalance() {
		return balance;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public int compareTo(Account other) {
		return this.timestamp.compareTo(other.timestamp);
	}
	
	// Utility method to greatly simplify showing an account in the GUI
	public StringProperty timestampProperty() {
		StringProperty timestampProperty = new SimpleStringProperty();
		timestampProperty.set(timestamp.toString());
		return timestampProperty;
	}
	
	// Utility method to greatly simplify showing an account in the GUI
	public StringProperty publicKeyProperty() {
		StringProperty publicKeyProperty = new SimpleStringProperty();
		publicKeyProperty.set(publicHash.toString(16));
		return publicKeyProperty;
	}
	
	// Utility method to greatly simplify showing an account in the GUI
	public StringProperty privateKeyProperty() {
		StringProperty privateKeyProperty = new SimpleStringProperty();
		privateKeyProperty.set(privateHash.toString(16));
		return privateKeyProperty;
	}
	
	// Utility method to greatly simplify showing an account in the GUI
	public StringProperty balanceProperty() {
		StringProperty balanceProperty = new SimpleStringProperty();
		balanceProperty.set(balance.toString());
		return balanceProperty;
	}
	
	// Utility method to greatly simplify showing an account in the GUI
	public StringProperty labelProperty() {
		StringProperty labelProperty = new SimpleStringProperty();
		labelProperty.set(label);
		return labelProperty;
	}
	
	// Utility method to greatly simplify showing an account in the GUI
	public StringProperty descriptionProperty() {
		StringProperty descriptionProperty = new SimpleStringProperty();
		descriptionProperty.set(description);
		return descriptionProperty;
	}
}
