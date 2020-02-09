package domain;

import java.util.LinkedList;
import java.util.List;

//Place where pending transactions are stored
//When a miner thread finds a new result, it can take the transactions
//form this object to fill the block with
public class TransactionPool {
	private static TransactionPool innerObject = null;
	private final List<Transaction> transactions;
	
	public static TransactionPool instance() {
		if (innerObject == null) {
			innerObject = new TransactionPool();
		}
		return innerObject;
	}
	
	private TransactionPool() {
		transactions = new LinkedList<>();
	}
	
	public synchronized boolean queue(Transaction t) {
		return transactions.add(t);
	}
	
	public synchronized List<Transaction> get() {
		List<Transaction> result = peek();
		transactions.clear();
		return result;
	}
	
	public synchronized List<Transaction> peek() {
		List<Transaction> result = new LinkedList<>();
		for (Transaction t: transactions) {
			result.add(t);
		}
		return result;
	}
	
	public int size() {
		return transactions.size();
	}
}
