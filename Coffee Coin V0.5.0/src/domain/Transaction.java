package domain;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import util.IllegalParameterException;
import util.hash.HasherFactory;
import util.hash.IHashable;
import util.pattern.Observable;

public class Transaction extends Observable implements IHashable {
	private static final long serialVersionUID = -8122454826614517959L;
	private final Asset asset;
	protected BigInteger amount;
	private final Account from;
	private final Account to;
	protected BigInteger amountProcessing; // The fee given for processing. 
	private final Date datetime;
	private BigInteger ownHash;
	private BigInteger previousHash; // Set when put in a block. Allow this once, after that, alert
									 // observers is it changes, but do not change anything.
									 // The first transaction in a block is a RewardTransaction; only this one can have a null previousHash.
									 // PreviousHash is not set for transactions in the memorypool; they are set once the block
									 // is constructed and filled
	
	public Transaction(Asset asset, BigInteger amount, Account from, Account to, BigInteger amountProcessing) {
		super();
		if (amountProcessing.compareTo(BigInteger.ZERO) < 0) {
			throw new IllegalParameterException("amountProcessing cannot be negative.");
		}
		this.asset = asset;
		this.amount = amount;
		this.from = from;
		this.to = to;
		datetime = new Date();
		this.amountProcessing = amountProcessing;
		ownHash = new BigInteger(this.hash());
		previousHash = null;
		from.withdraw(amount.add(amountProcessing));
		to.deposit(amount);
	}

	public BigInteger getPreviousHash() {
		return previousHash;
	}

	protected void setPreviousHash(BigInteger previousHash) {
		if (this.previousHash == null) {
			this.previousHash = previousHash;
		}
		else {
			notifyObservers();
		}
	}

	public BigInteger getAmountProcessing() {
		return amountProcessing;
	}

	public Asset getAsset() {
		return asset;
	}

	public BigInteger getAmount() {
		return amount;
	}
	
	public void setAmount(BigInteger amount) {
		// Drastic. Correct the balances.
		from.deposit(this.amount);
		to.withdraw(this.amount);
		from.withdraw(amount);
		to.deposit(amount);
		this.amount = amount;
		ownHash = new BigInteger(this.hash());
		notifyObservers();
	}

	public Account getFrom() {
		return from;
	}

	public Account getTo() {
		return to;
	}

	public Date getDatetime() {
		return datetime;
	}

	public BigInteger getOwnHash() {
		return ownHash;
	}

	@Override
	public byte[] hash() {
		try {
			return HasherFactory.hasher().hash(this);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public boolean equals(IHashable other) {
		if (this == other)
			return true;
		if (other == null)
			return false;
		if (getClass() != other.getClass())
			return false;
		Transaction theOther = (Transaction) other;
		// Checks for null values done
			
		BigInteger hashSelf, hashOther;
		hashSelf = new BigInteger(this.hash());
		hashOther = new BigInteger(theOther.hash());
		return hashSelf.equals(hashOther);
	}
	
	public String getTransactionType() {
		return "Regular transaction";
	}
	
	// Utility method to greatly simplify showing a transaction in the GUI
	public StringProperty timestampProperty() {
		StringProperty timestampProperty = new SimpleStringProperty();
		timestampProperty.set(datetime.toString());
		return timestampProperty;
	}
	
	// Utility method to greatly simplify showing a transaction in the GUI
	// Note: for testing purposes, a few accounts are translated to text hard-coded.
	// For version 1, this needs to be changed to using objects of the class Owner.
	//
	public StringProperty fromProperty() {
		StringProperty fromProperty = new SimpleStringProperty();
		// If the account is known in the wallet, show the label instead
		String label = Wallet.instance().getLabel(from.getPublicHash());
		if (label != null) {
			fromProperty.set(label);
		}
		else {
			fromProperty.set(from.getPublicHash().toString(16));
		}
		if (Account.getNewAssetSource().getPublicHash().equals(from.getPublicHash())) {
			fromProperty.set(Account.getNewAssetSource().getLabel());
		}
		return fromProperty;
	}
	
	// Utility method to greatly simplify showing a transaction in the GUI
	// Note: for testing purposes, a few accounts are translated to text hard-coded.
	// For version 1, this needs to be changed to using objects of the class Owner.
	//
	public StringProperty toProperty() {
		StringProperty toProperty = new SimpleStringProperty();
		// If the account is known in the wallet, show the label instead
		String label = Wallet.instance().getLabel(to.getPublicHash());
		if (label != null) {
			toProperty.set(label);
		}
		else {
			toProperty.set(to.getPublicHash().toString(16));
		}
		if (Account.getAssetBitBucket().getPublicHash().equals(to.getPublicHash())) {
			toProperty.set(Account.getAssetBitBucket().getLabel());
		}
		return toProperty;
	}
	
	// Utility method to greatly simplify showing a transaction in the GUI
	public StringProperty transactionTypeProperty() {
		StringProperty transactionTypeProperty = new SimpleStringProperty();
		transactionTypeProperty.set("T");
		return transactionTypeProperty;
	}
	
	// Utility method to greatly simplify showing a transaction in the GUI
	public StringProperty amountProperty() {
		StringProperty amountProperty = new SimpleStringProperty();
		amountProperty.set(amount + " " + asset.getTicker());
		return amountProperty;
	}
}
