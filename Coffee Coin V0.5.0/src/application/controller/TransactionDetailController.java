package application.controller;

import domain.MessageTransaction;
import domain.Transaction;
import domain.Wallet;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class TransactionDetailController {
	@FXML
	private TextField transactionType;
	
	@FXML
	private TextField timestamp;
	
	@FXML
	private TextField from;
	
	@FXML
	private TextField to;
	
	@FXML
	private TextField amount; // Includes currency
	
	@FXML
	private TextArea messageText;
	
	@FXML
	public void initialize() {
		transactionType.clear();
		timestamp.clear();
		from.clear();
		to.clear();
		amount.clear();
		messageText.clear();
	}
	
	public void showTransactionDetails(Transaction transaction) {
		if (transaction != null) {
			transactionType.setText(transaction.getTransactionType().toString());
			timestamp.setText(transaction.getDatetime().toString());
			
			String label = Wallet.instance().getLabel(transaction.getFrom().getPublicHash());
			if (label != null) {
				from.setText(label);
			}
			else {
				from.setText(transaction.getFrom().getPublicHash().toString(16));
			}
			
			label = Wallet.instance().getLabel(transaction.getTo().getPublicHash());
			if (label != null) {
				to.setText(label);
			}
			else {
				to.setText(transaction.getTo().getPublicHash().toString(16));
			}
			amount.setText(transaction.getAmount().toString() + " " + transaction.getAsset().getTicker());
			
			// Show message if applicable
			if (transaction instanceof MessageTransaction) {
				MessageTransaction mt = (MessageTransaction) transaction;
				messageText.setText(mt.getMessage());
			}
			else {
				messageText.clear();
			}
		}
	}
}
