package application.controller;

import java.io.IOException;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import domain.Asset;
import domain.Chain;
import domain.GenesisRewardTransaction;
import domain.TransactionPool;
import domain.proof.Proof;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import util.pattern.IObserver;
import util.pattern.Observable;

public class ProofsPieChartController implements IObserver {
	
	private static Chain chain;
	@FXML
	private PieChart pieChartOccurrences;
	@FXML
	private PieChart pieChartAmount;
	@FXML
	private Label totalValue;
	@FXML
	private Label totalBlocks;
	@FXML
	private Label totalGenesisProofs;
	@FXML
	private Label totalTransactionsInChain;
	@FXML
	private Label totalTransactionsInPool;
	
	
	@FXML
	public void initialize() throws NoSuchAlgorithmException, IOException {
		chain = Chain.instance(Asset.getDefault());
		createPieCharts();
		updateCounts();
		chain.addObserver(this);
	}
	
	@SuppressWarnings("deprecation")
	private void updateCounts() {
		totalValue.setText("Total value in chain: " + (chain.getTotalValue().toString()));
		totalBlocks.setText("Total number of blocks: " + new Integer(chain.size()-1).toString());
		int pregenProofs = ((GenesisRewardTransaction) chain.getGenesisBlock().getRewardTransaction()).getProofs().size();
		totalGenesisProofs.setText("Total number of pre-generated proofs: " + (new Integer(pregenProofs).toString()));
		totalTransactionsInChain.setText("Total transactions in chain: " + (new Long(chain.getNumberOfTransactions()).toString()));
		totalTransactionsInPool.setText("Total transactions in pool: " + (new Integer(TransactionPool.instance().size()).toString()));
	}
	
	private void createPieCharts() throws NoSuchAlgorithmException, IOException {
		// Get the chain. Retrieve all proofs from it. Loop over it and count the number of proof types.
		// Store the results in a Map first. When finished, use this to create PieChart.Data objects
		// and create the PieChart itself.
		Map<String, BigInteger> occurrenceMap = new TreeMap<>();
		Map<String, BigInteger> amountMap = new TreeMap<>();
		List<Proof> proofs = chain.getProofs(false);
		for (Proof proof: proofs) {
			String type = proof.getClass().getSimpleName();
			BigInteger occurences = occurrenceMap.get(type);
			BigInteger amounts = amountMap.get(type);
			BigInteger value = (occurences == null) ? BigInteger.ONE : occurences.add(BigInteger.ONE);
			BigInteger amount = (amounts == null) ? proof.getReward().getAmount() : amounts.add(proof.getReward().getAmount());
			occurrenceMap.put(type, value);
			amountMap.put(type, amount);
		}
		ObservableList<PieChart.Data> occurrenceData = FXCollections.observableArrayList();
		Set<String> keySet = occurrenceMap.keySet();
		for (String key:keySet) {
			occurrenceData.add(new PieChart.Data(key, occurrenceMap.get(key).longValue()));
		}
		pieChartOccurrences.setData(occurrenceData);
		pieChartOccurrences.setClockwise(true);
		pieChartOccurrences.setStartAngle(180); 
		
		ObservableList<PieChart.Data> amountData = FXCollections.observableArrayList();
		keySet = amountMap.keySet();
		for (String key:keySet) {
			amountData.add(new PieChart.Data(key, amountMap.get(key).longValue()));
		}
		pieChartAmount.setData(amountData);
		pieChartAmount.setClockwise(true);
		pieChartAmount.setStartAngle(180); 
	}

	@Override
	public void update(Observable arg0) {
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				try {
					createPieCharts();
					updateCounts();
				} catch (NoSuchAlgorithmException | IOException e) {
					e.printStackTrace();
				}
			}
			
		});
	}
}
