package domain;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;

import domain.proof.Proof;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class RewardTransaction extends Transaction {
	private static final long serialVersionUID = -6979740971973703197L;
	private final Proof proof;

	public RewardTransaction(Proof proof, Account to) throws NoSuchAlgorithmException {
		super(proof.getReward().getAsset(), proof.getReward().getAmount(), Account.getNewAssetSource(), to, BigInteger.ZERO); // Calculate amountProcessing to be the sum of all these in the block
		this.proof = proof;
	}

	public Proof getProof() {
		return proof;
	}
	
	public String getTransactionType() {
		return "Rewards for new block";
	}
	
	// Utility method to greatly simplify showing a transaction in the GUI
	public StringProperty transactionTypeProperty() {
		StringProperty transactionTypeProperty = new SimpleStringProperty();
		transactionTypeProperty.set("R");
		return transactionTypeProperty;
	}
}
