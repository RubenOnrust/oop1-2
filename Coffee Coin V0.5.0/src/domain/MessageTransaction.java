package domain;

import java.math.BigInteger;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import util.IllegalParameterException;

// Object of this class are Transactions carrying a message for the recipient. They need to follow some extra rules:
// 1. The message can only be a text, with a maximum length of 1024 characters (note: put this in the Settings class.) This is done to avoid overburdening the chain
// 2. The sender pays a fee for sending the message as always. However, the fee and the amount sent are combined and redistributed: 90% for the block creator, 10% for the recipient
// 3. The amount sent plus fee cannot be less than 10.
// 4. The message itself is not part of the Merkle Tree integrity checking; the message should be encrypted with the public key of the recipient
//    As the sender is checked by the chain, a double layer is not necessary.
// 5. Messages can clog the chain. After a week (setting in Settings!) the message text can be set to null by all nodes without consequence.
//
public class MessageTransaction extends Transaction {
	private static final long serialVersionUID = -866277535372019968L;
	private transient String message;

	public MessageTransaction(Asset asset, BigInteger amount, Account from, Account to, BigInteger amountProcessing, String message, String recipient) {
		super(asset, amount, from, to, amountProcessing);
		if ((message.length() > 1024) || (message.length() <4)) {
			throw new IllegalParameterException("Message length should be between 4 and 1024.");
		}
		this.message = encrypt(message, recipient);
		// Set the amounts
		//
		BigInteger TEN = new BigInteger("10");
		BigInteger total = amount.add(amountProcessing);
		if (total.compareTo(TEN) == -1) {
			throw new IllegalParameterException("Sending a message costs at least 10 " + asset.getName() + ".");
		}
		BigInteger compensation = total.divide(TEN);
		BigInteger fee = compensation.multiply(new BigInteger("9"));
		//
		// The rest is evenly divided
		//
		BigInteger rest = (total.subtract(fee)).subtract(compensation);
		compensation = compensation.add(rest.divide(new BigInteger("2")));
		fee = total.subtract(compensation);
		super.amount = compensation;
		super.amountProcessing = fee;
	}
	
	// Encrypt the message using the public key of the recipient.
	// For now, just return the string; encrypting it can be added later.
	//
	private String encrypt(String message, String publicKey) {
		return message;
	}
	
	public String getMessage() {
		return message;
	}
	
	// Return the unencrypted message by passing the private key
	//
	public String getMessage(String privateKey) {
		return message;
	}
	
	public void clearMessage() {
		this.message = null;
	}
	
	public String getTransactionType() {
		return "Message transaction";
	}
	
	// Utility method to greatly simplify showing a transaction in the GUI
	public StringProperty transactionTypeProperty() {
		StringProperty transactionTypeProperty = new SimpleStringProperty();
		transactionTypeProperty.set("M");
		return transactionTypeProperty;
	}
}
