package application.controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.SortedSet;

import application.MainCoffee;
import domain.Account;
import domain.Asset;
import domain.Chain;
import domain.Transaction;
import domain.Wallet;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import util.pattern.IObserver;
import util.pattern.Observable;
import util.view.AlertUtil;

public class TransactionOverviewController implements IObserver {
	private Wallet wallet;
	private TransactionDetailController controller;
	
	@FXML
	private ObservableList<Transaction> shownTransactions = FXCollections.observableArrayList();
	
    @FXML
    private TableView<Transaction> transactionTable;
    @FXML
    private TableColumn<Transaction, String> transactionTypeColumn; // Change to graphical representation later on. For now a one-character symbol.
    @FXML
    private TableColumn<Transaction, String> timestampColumn;
    @FXML
    private TableColumn<Transaction, String> fromColumn;
    @FXML
    private TableColumn<Transaction, String> toColumn;
    @FXML
    private TableColumn<Transaction, String> amountColumn; // Includes asset type
    @FXML
    private Label totalBalance;
	
	@FXML
	public void initialize() {
		wallet = Wallet.instance();
		wallet.addObserver(this);
		
		// Show the detail pane
		try {
			Chain.instance(Asset.getDefault()).addObserver(this);
            FXMLLoader loader = new FXMLLoader(TransactionDetailController.class.getResource("../view/TransactionDetails.fxml"));
            AnchorPane transactionDetails = (AnchorPane) loader.load();
            controller = loader.getController();
            BorderPane rootLayout = MainCoffee.getRootLayout();
            rootLayout.setCenter(transactionDetails);
        } catch (IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
		
		showTransactionList();
	}
	
	private void showTransactionList() {
		// Initialize the mail table with the five columns.
		transactionTypeColumn.setCellValueFactory(cellData -> cellData.getValue().transactionTypeProperty());
		timestampColumn.setCellValueFactory(cellData -> cellData.getValue().timestampProperty());
		fromColumn.setCellValueFactory(cellData -> cellData.getValue().fromProperty());
		toColumn.setCellValueFactory(cellData -> cellData.getValue().toProperty());
		amountColumn.setCellValueFactory(cellData -> cellData.getValue().amountProperty());
		
		// Listen for selection changes and show the key details when changed.
        transactionTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> controller.showTransactionDetails(newValue));
		
		// Fill the table with transaction data
		shownTransactions.clear();
		
		// First retrieve the transactions
		SortedSet<Account> accounts = wallet.getAccounts();
		Collection<Transaction> transactions;
		try {
			transactions = Chain.instance(Asset.getDefault()).getTransactions(accounts);
			shownTransactions.addAll(transactions);
			transactionTable.setItems(shownTransactions);
			
		} catch (NoSuchAlgorithmException | IOException | ConcurrentModificationException e) {
			// Ugly!
		}
		
		// Show total balance
		totalBalance.setText("Total balance: " + Wallet.instance().getTotalBalance().toString() + " " + Asset.getDefault().getTicker()); // Does NOT take multiple coins into account
	}
	
	@FXML
	public void doCreateTransaction() {
		AlertUtil.notYetImplementedMessage("Create Transaction", "1.0");
	}

	@Override
	public void update(Observable arg0) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				showTransactionList();
			}
		});
	}
}

