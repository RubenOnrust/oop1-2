package application.controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.SortedSet;

import application.MainCoffee;
import domain.Account;
import domain.Asset;
import domain.Chain;
import domain.Wallet;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import util.pattern.IObserver;
import util.pattern.Observable;
import util.view.AlertUtil;

public class AccountOverviewController implements IObserver {
	private Wallet wallet;
	private AccountDetailController controller;
	
	@FXML
	private ObservableList<Account> shownAccounts = FXCollections.observableArrayList();
	
    @FXML
    private TableView<Account> accountTable;
    @FXML
    private TableColumn<Account, String> timestampColumn;
    @FXML
    private TableColumn<Account, String> labelColumn;
    @FXML
    private TableColumn<Account, String> balanceColumn;
    @FXML
    private Label totalBalance;
	
	@FXML
	public void initialize() {
		wallet = Wallet.instance();
		wallet.addObserver(this);
		
		// Show the detail pane
		try {
			Chain.instance(Asset.getDefault()).addObserver(this);
            FXMLLoader loader = new FXMLLoader(AccountDetailController.class.getResource("../view/AccountDetails.fxml"));
            AnchorPane accountDetails = (AnchorPane) loader.load();
            controller = loader.getController();
            BorderPane rootLayout = MainCoffee.getRootLayout();
            rootLayout.setCenter(accountDetails);
        } catch (IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
		showAccountList();
	}
	
	private void showAccountList() {
		// Initialize the mail table with the four columns.
		timestampColumn.setCellValueFactory(cellData -> cellData.getValue().timestampProperty());
		labelColumn.setCellValueFactory(cellData -> cellData.getValue().labelProperty());
		balanceColumn.setCellValueFactory(cellData -> cellData.getValue().balanceProperty());
        
		// Listen for selection changes and show the key details when changed.
        accountTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> controller.showAccountDetails(newValue));

        // Fill the table with account data
		shownAccounts.clear();
		SortedSet<Account> keySet = Collections.synchronizedSortedSet(wallet.getAccounts());
		try {
			shownAccounts.addAll(keySet);
			accountTable.setItems(shownAccounts);
		}
		catch (ConcurrentModificationException e) {
			// Ugly!
		}
		
		// Show total balance
		totalBalance.setText("Total balance: " + Wallet.instance().getTotalBalance().toString() + " " + Asset.getDefault().getTicker()); // Does NOT take multiple coins into account
	}
	
	public void doCreateAccount() {
		try {
			FXMLLoader loader = new FXMLLoader(NewAccountDialogController.class.getResource("../view/NewAccountDialog.fxml"));
	        AnchorPane page = (AnchorPane) loader.load();
	
	        // Create the dialog Stage.
	        Stage dialogStage = new Stage();
	        dialogStage.setTitle("Create a new account");
	        dialogStage.initModality(Modality.WINDOW_MODAL);
	        dialogStage.initOwner(MainCoffee.getPrimaryStage());
	        Scene scene = new Scene(page);
	        dialogStage.setScene(scene);
	
	        // Set the stage
	        NewAccountDialogController controller = loader.getController();
	        controller.setDialogStage(dialogStage);
	
	        // Show the dialog and wait until the user closes it
	        dialogStage.showAndWait();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void doEditAccount() {
		AlertUtil.notYetImplementedMessage("Edit Account", "2.0");
	}
	
	public void doDeleteAccount() {
		AlertUtil.notYetImplementedMessage("Delete Account", "2.0");
	}

	@Override
	public void update(Observable arg0) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				showAccountList();
			}
		});
	}
}

