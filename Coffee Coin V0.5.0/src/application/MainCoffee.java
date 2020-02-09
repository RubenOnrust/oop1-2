package application;
	
import static domain.Settings.Environment.DEVELOPMENT;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import application.controller.RootLayoutController;
import domain.Asset;
import domain.Chain;
import domain.Settings;
import domain.TransactionPool;
import domain.Wallet;
import domain.factory.TransactionGenerator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import miner.ChainController;
import miner.MinerPool;
import miner.MiningResultsQueue;


public class MainCoffee extends Application {
	private static Stage primaryStage;
    private static BorderPane rootLayout;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException, NoSuchAlgorithmException {
        MainCoffee.primaryStage = primaryStage;
        MainCoffee.primaryStage.setTitle("Coffee Coin V0.5");
        
        showSplashScreen();
        initializeSingletons();
    	initializeRunners();
    	
    	Wallet wallet = Wallet.instance();
    	if ((wallet.size() != 0) &&  (Settings.instance().getEnvironment() == DEVELOPMENT)) {
           	Settings.instance().setMiningTarget(wallet.get(wallet.size()-1));
    	}
    	else {
    		Settings.instance().setMiningTarget(null);
    	}
    	
        if (Settings.instance().getEnvironment() == DEVELOPMENT) {
        	MinerPool.instance(Settings.instance().getMiningTarget()).startAll();
        	new TransactionGenerator();
        }
        
        initRootLayout();
        primaryStage.show();
    }
    
    // Create instances of all Singletons; this speeds up other interactions.
    private void initializeSingletons() throws NoSuchAlgorithmException, IOException {
    	Settings.instance();
    	TransactionPool.instance();
    	MiningResultsQueue.instance();
    	Chain.instance(Asset.getDefault());
    	ChainController.instance();
    	Wallet.instance();
    	MinerPool.instance(Settings.instance().getMiningTarget());
    }

    private void initRootLayout() {
        try {
            // Load root layout from fxml file, using the controller class to get at the correct location
            FXMLLoader loader = new FXMLLoader(RootLayoutController.class.getResource("../view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void showSplashScreen() {

    }

    public static BorderPane getRootLayout() {
		return rootLayout;
	}

	public static Stage getPrimaryStage() {
        return primaryStage;
    }
    
    private void initializeRunners() {
    	
    }

    public static void finish() {
    	try {
			MinerPool.instance(Settings.instance().getMiningTarget()).stopAll();
		} catch (NoSuchAlgorithmException | IOException e) {
			e.printStackTrace();
		}
		System.exit(0);
    }
}
