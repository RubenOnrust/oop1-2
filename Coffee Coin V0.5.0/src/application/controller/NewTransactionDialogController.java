package application.controller;

import domain.Wallet;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class NewTransactionDialogController {
	private boolean okClicked = false;
    private Stage dialogStage;
    @SuppressWarnings("unused")
	private Wallet wallet;
    
	@FXML
	private Label asset;
	@FXML
	private TextField amount;
	@FXML
	private TextField from;
	@FXML
	private TextField to;
	@FXML
	private TextField amountProcessing; // The fee given for processing. 
	@FXML
	private TextArea description;
	
	@FXML
	public void initialize() {
		amount.clear();
		from.clear();
		to.clear();
		amountProcessing.clear();
		description.clear();
		wallet = Wallet.instance(); // For giving the user a list of accounts to pick from; also built in to have the system pick one or more
	}
	
	@FXML
	public void handleOk() {
		if ((asset.getText() != null) && (amount.getText() != null) && (to.getText() != null)) {
			// TransactionPool pool = TransactionPool.instance();
			// Transaction transaction = new Transaction(Asset.getDefault(), new BigInteger(new Long(amount).toString()), from, to, new BigInteger(new Long(fee).toString()));
			// pool.queue(transaction);
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
