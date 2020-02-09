package application.controller;

import domain.Account;
import domain.Wallet;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class NewAccountDialogController {
	private boolean okClicked = false;
    private Stage dialogStage;
    
	@FXML
	private TextField label;
	
	@FXML
	private TextArea seed;
	
	@FXML
	private TextArea description;
	
	@FXML
	public void initialize() {
		label.clear();
		description.clear();
	}
	
	@FXML
	public void handleOk() {
		if ((label.getText() != null) && (seed.getText() != null) && (seed.getText().length() > 20)) {
			Account account = new Account(seed.getText(), label.getText(), description.getText());
			Wallet.instance().addAccount(account);
			okClicked = true;
			dialogStage.close();
		}
	}
	
	@FXML
	public void handleCancel() {
		dialogStage.close();
	}
	
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    
    public boolean isOkClicked() {
        return okClicked;
    }
}
