package domain;

import java.io.IOException;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import util.collection.MerkleTree;
import util.collection.StableOrderSet;
import util.hash.HasherFactory;
import util.hash.IHashable;
import util.pattern.IObserver;
import util.pattern.Observable;

// Rules:
// Transactions stay in order
// Block needs to be made with the hash of the previous block; having this set to zero is only
// allowed for the Genesis block. 
//
public class Block extends Observable implements IHashable, IObserver {
	private static final long serialVersionUID = 8926701374998217452L;
	protected List<Transaction> transactions; 
	private final Date timestamp;
	private BigInteger hashPrevious; 		  // The root of the Merkle Tree of the previous block
	protected MerkleTree<Transaction> merkleTree;
	
	public Block(Block previousBlock, RewardTransaction t) throws NoSuchAlgorithmException, IOException { 
		super();
		if (previousBlock != null) {
			this.hashPrevious = previousBlock.getMerkleRoot();
		}
		else {
			throw new IllegalStateException("Cannot add a block without a previous one, unless it would be the genesis block.");
		}
		this.timestamp = new Date();
		this.transactions = Collections.synchronizedList(new StableOrderSet<>());
		BigInteger amount = t.getProof().calculateReward(t.getAsset()).getAmount();
		t.getProof().setReward(new Reward(t.getAsset(), amount));
		t.setAmount(amount);
		this.transactions.add(t);
		this.merkleTree = new MerkleTree<>(t);
		t.addObserver(this);
	}
	
	// The one necessary when creating a genesis block
	public Block(GenesisRewardTransaction t) throws NoSuchAlgorithmException { 
		super();
		timestamp = new Date();
		hashPrevious = null;
		transactions = new StableOrderSet<>();
		transactions.add(t);
		merkleTree = new MerkleTree<>(t);
	}
	
	// Add a transaction to the block
	//
	public synchronized boolean add(Transaction t) throws NoSuchAlgorithmException {
		Transaction last = transactions.get(transactions.size()-1);
		BigInteger previousHash = last.getOwnHash();
		t.setPreviousHash(previousHash);
		if (transactions.add(t)) {
			merkleTree.add(t);
			t.addObserver(this);
			return true;
		}
		else {
			return false;
		}
	}
	
	public synchronized boolean isEmpty() {
		return transactions.isEmpty();
	}
	
	public synchronized boolean contains(Transaction t) {
		return transactions.contains(t);
	}
	
	public synchronized int size() {
		return transactions.size();
	}
	
	public Iterator<Transaction> iterator() {
		return (Iterator<Transaction>) transactions.iterator();
	}
	
	@Override
	public synchronized byte[] hash() throws NoSuchAlgorithmException {
		return HasherFactory.hasher().hash(this);
	}
	
	public synchronized BigInteger getMerkleRoot() {
		return new BigInteger(merkleTree.getRoot().toByteArray());
	}

	public synchronized List<Transaction> getTransactions() {
		return new LinkedList<Transaction>(transactions);
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public BigInteger getHashPrevious() {
		return hashPrevious;
	}
	
	public void setHashPrevious(BigInteger hashPrevious) {
		this.hashPrevious = hashPrevious;
	}

	@Override
	public boolean equals(IHashable other) {
		if (this == other)
			return true;
		if (other == null)
			return false;
		if (getClass() != other.getClass())
			return false;
		Block theOther = (Block) other;
		// Checks for null values done
			
		BigInteger hashSelf, hashOther;
		try {
			hashSelf = new BigInteger(this.hash());
			hashOther = new BigInteger(theOther.hash());
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("Oeps. Crypto algorithm unknown.");
		}
		return hashSelf.equals(hashOther);
	}
	
	public RewardTransaction getRewardTransaction() {
		return (RewardTransaction) transactions.get(0);
	}
	
	public void recalculateMerkleTree() {
		Iterator<Transaction> i = transactions.iterator();
		try {
			merkleTree = new MerkleTree<Transaction>(i.next());
			while (i.hasNext()) {
				merkleTree.add(i.next());
			}
		} catch (NoSuchAlgorithmException e) {

		}
	}
	
	// Removes a transaction from the block and recalculates the Merkle Tree
	public synchronized boolean removeTransaction(Transaction t) {
		boolean result = transactions.remove(t);
		if (result) {
			recalculateMerkleTree();
			notifyObservers();
		}
		return result;
	}

	@Override
	public void update(Observable o) {
		notifyObservers();
	}

	public Transaction getTransaction(int index) {
		return transactions.get(index);
	}
}
