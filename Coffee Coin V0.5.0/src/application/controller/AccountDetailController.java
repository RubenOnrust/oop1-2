package application.controller;

import domain.Account;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class AccountDetailController {
	@FXML
	private TextField label;
	
	@FXML
	private TextField timestamp;
	
	@FXML
	private TextField publicKey;
	
	@FXML
	private TextField privateKey;
	
	@FXML
	private TextField balance;
	
	@FXML
	private TextArea description;
	
	@FXML
	public void initialize() {
		timestamp.clear();
		publicKey.clear();
		privateKey.clear();
		balance.clear();
		label.clear();
		description.clear();
	}
	
	public void showAccountDetails(Account account) {
		if (account != null) {
			timestamp.setText(account.getTimestamp().toString());
			publicKey.setText(account.getPublicHash().toString());
			privateKey.setText(account.getPrivateHash().toString());
			balance.setText(account.getBalance().toString());
			label.setText(account.getLabel().toString());
			description.setText(account.getDescription().toString());
		}
	}
}
