package application.controller;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;

import application.MainCoffee;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import util.view.AlertUtil;

public class RootLayoutController {

	
	@FXML
	public void initialize() {

	}
	
	// File menu
	@FXML
	public void close() {
		MainCoffee.finish();
	}
	
	// Wallet menu
	@FXML
	public void doImportWallet() {
		AlertUtil.notYetImplementedMessage("Import Wallet", "2.0");
	}
	
	@FXML
	public void doBackupWallet() {
		AlertUtil.notYetImplementedMessage("Backup Wallet", "2.0");
	}
	
	@FXML
	public void doWalletRepair() {
		AlertUtil.notYetImplementedMessage("Repair Wallet", "2.0");
	}
	
	@FXML
	public void doEncryptWallet() {
		AlertUtil.notYetImplementedMessage("Encrypt Wallet", "3.0");
	}
	
	@FXML
	public void doChangePassword() {
		AlertUtil.notYetImplementedMessage("Change Wallet Password", "2.0");
	}
	
	@FXML
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
	
	@FXML
	public void doDeleteAccount() {
		AlertUtil.notYetImplementedMessage("Delete Account", "2.0");
	}
	
	@FXML
	public void doMergeAccounts() {
		AlertUtil.notYetImplementedMessage("Merge Accounts", "4.0");
	}
	
	@FXML
	public void doShowAccounts() {
		try {
            // Load overview pane layout from fxml file, using the controller class to get at the correct location
            FXMLLoader loader = new FXMLLoader(AccountOverviewController.class.getResource("../view/AccountOverview.fxml"));
            AnchorPane overview = (AnchorPane) loader.load();
            BorderPane rootLayout = MainCoffee.getRootLayout();
            rootLayout.setLeft(overview);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	// Transactions Menu
	@FXML
	public void doShowTransactions() {
		try {
            // Load overview pane layout from fxml file, using the controller class to get at the correct location
            FXMLLoader loader = new FXMLLoader(TransactionOverviewController.class.getResource("../view/TransactionOverview.fxml"));
            AnchorPane overview = (AnchorPane) loader.load();
            BorderPane rootLayout = MainCoffee.getRootLayout();
            rootLayout.setLeft(overview);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	@FXML
	public void doCreateTransaction() {
		AlertUtil.notYetImplementedMessage("Create Transaction", "2.0");
	}
	
	// Settings
	@FXML
	public void doMinerSettings() {
		AlertUtil.notYetImplementedMessage("Miner settings", "2.0");
	}
	
	@FXML
	public void doMiscellaneousSettings() {
		AlertUtil.notYetImplementedMessage("Miscellaneous Settings", "1.0");
	}
	
	// Mining Menu
	@FXML
	public void doStartMining() {
		AlertUtil.notYetImplementedMessage("Start Mining", "2.0");
	}
	
	@FXML
	public void doStopMining() {
		AlertUtil.notYetImplementedMessage("Stop Mining", "2.0");
	}
	
	@FXML
	public void doMinerPerformance() {
		AlertUtil.notYetImplementedMessage("Miner Performance", "2.0");
	}
	
	// Tools Menu
	@FXML
	public void doDebugConsole() {
		AlertUtil.notYetImplementedMessage("Debug Console", "2.0");
	}
	
	@FXML
	public void doInformation() {
		AlertUtil.notYetImplementedMessage("Information", "2.0");
	}
	
	@FXML
	public void doNetworkMonitor() {
		AlertUtil.notYetImplementedMessage("Network Monitor", "2.0");
	}
	
	@FXML
	public void doPeersList() {
		AlertUtil.notYetImplementedMessage("Peers List", "2.0");
	}
	
	@FXML
	public void doShowChain() {
		try {
			FXMLLoader loader = new FXMLLoader(ShowChainAsDialogController.class.getResource("../view/ShowChainAsDialog.fxml"));
	        AnchorPane page = (AnchorPane) loader.load();
	
	        // Create the dialog Stage.
	        Stage dialogStage = new Stage();
	        dialogStage.setTitle("Contents of the blockchain (excluding genesis block)");
	        dialogStage.initModality(Modality.WINDOW_MODAL);
	        dialogStage.initOwner(MainCoffee.getPrimaryStage());
	        Scene scene = new Scene(page);
	        dialogStage.setScene(scene);
	
	        // Set the stage
	        ShowChainAsDialogController controller = loader.getController();
	        controller.setDialogStage(dialogStage);
	
	        // Show the dialog and wait until the user closes it
	        dialogStage.showAndWait();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// Graphs Menu
	@FXML
	public void doShowProofDistribution() {
		BorderPane rootLayout = MainCoffee.getRootLayout();
        FXMLLoader loader = new FXMLLoader(ProofsPieChartController.class.getResource("../view/ProofsPieChart.fxml"));
        AnchorPane proofsPieChart;
		try {
			proofsPieChart = (AnchorPane) loader.load();
	        rootLayout.setCenter(proofsPieChart);
	        rootLayout.setLeft(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// Help Menu
	@FXML
	public void doManual() {
		AlertUtil.notYetImplementedMessage("Manual", "2.0");
	}
	
	
	@SuppressWarnings("deprecation")
	@FXML
	public void doAbout() {
		try(InputStream inputStream = this.getClass().getResourceAsStream("/data/About.txt")) {
		    String aboutText = IOUtils.toString(inputStream);
		    Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("About");
			alert.setHeaderText("About this application");alert.setContentText(aboutText);
			alert.showAndWait();
		}
		catch (IOException e) {

		}
	}
}
