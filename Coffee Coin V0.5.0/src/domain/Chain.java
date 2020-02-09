package domain;

import java.io.IOException;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;

import domain.proof.Proof;
import domain.proof.ProofsInitializer;
import util.collection.MerkleTree;
import util.hash.HasherFactory;
import util.hash.IHashable;
import util.pattern.Observable;

public class Chain extends Observable implements IHashable {
	private static final long serialVersionUID = -6160696307145980453L;
	private static Chain chain = null;
	private final List<Block> blocks; 
	private MerkleTree<Block> merkleTree;
	private final Asset asset;
	private final Date timestamp;
	private BigInteger totalValue = BigInteger.ZERO;
	private long numberOfTransactions = 0;
	private BigInteger numberOfBlocks;
	
	private Chain(Asset asset) throws NoSuchAlgorithmException, IOException {
		super();
		this.asset = asset;
		SortedSet<Proof> proofs = ProofsInitializer.instance(asset).getProofs();
		Block genesis = new GenesisBlock(proofs);
		blocks = Collections.synchronizedList(new ArrayList<>());
		blocks.add(genesis);
		merkleTree = new MerkleTree<>(genesis);
		this.timestamp = new Date();
		this.numberOfBlocks = BigInteger.ZERO;
	}
	
	public static Chain instance(Asset asset) throws NoSuchAlgorithmException, IOException {
		if (chain == null) {
			chain = new Chain(asset);
		}
		return chain;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public List<Block> getBlocks() {
		return new LinkedList<Block>(blocks);
	}

	public BigInteger getMerkleRoot() {
		return new BigInteger(merkleTree.getRoot().toByteArray());
	}

	public Asset getAsset() {
		return asset;
	}
	
	@Override
	public synchronized byte[] hash() throws NoSuchAlgorithmException {
		return HasherFactory.hasher().hash(this);
	}

	@Override
	public synchronized boolean equals(IHashable other) {
		if (this == other)
			return true;
		if (other == null)
			return false;
		if (getClass() != other.getClass())
			return false;
		Chain theOther = (Chain) other;
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
	
	public synchronized boolean add(Block b) throws NoSuchAlgorithmException {
		// First check whether the proposed proof is unique (new)
		
		Proof proof = b.getRewardTransaction().getProof();
		if (!isUnique(proof)) {
			return false;
		}
		Block last = blocks.get(blocks.size()-1);
		if (blocks.add(b)) {
			// Block has been approved; update balance of the block creator and all transaction creators because of the fees
			// Keep in mind the transaction sender has already been charged the processing fee, it only needs to 
			// be determined who receives this
			//
			List<Transaction> transactions = b.getTransactions();
			Iterator<Transaction> i = transactions.iterator();
			Account to = i.next().getTo();
			while (i.hasNext()) {
				Transaction t = i.next();
				to.deposit(t.getAmountProcessing());
			}
			
			// Create merkle hash
			b.setHashPrevious(last.getMerkleRoot());
			merkleTree.add(b);
			totalValue.add(b.getRewardTransaction().getProof().getReward().getAmount());
			numberOfTransactions += b.size()-1;
			numberOfBlocks = numberOfBlocks.add(BigInteger.ONE);
			notifyObservers();
			return true;
		}
		else {
			return false;
		}
	}
	
	public BigInteger getNumberOfBlocks() {
		return new BigInteger(numberOfBlocks.toString());
	}
	
	public synchronized boolean isEmpty() {
		return blocks.isEmpty();
	}
	
	public synchronized boolean contains(Block t) {
		return blocks.contains(t);
	}
	
	public synchronized int size() {
		return blocks.size();
	}
	
	public Iterator<Block> iterator() {
		return (Iterator<Block>) blocks.iterator();
	}
	
	// Does not include the reward transactions!
	public long getNumberOfTransactions() {
		return numberOfTransactions;
	}
	
	// The first block can have multiple proofs. The parameter says whether these should be included (true) or not (false).
	public synchronized List<Proof> getProofs(boolean includingGenesis) {
		List<Proof> result = new LinkedList<>();
		Iterator<Block> i = blocks.iterator();
		GenesisRewardTransaction genBlock = (GenesisRewardTransaction) i.next().getRewardTransaction();
		if (includingGenesis) {
			List<Proof> genProofs = genBlock.getProofs();
			for (Proof proof: genProofs) {
				result.add(proof);
			}
		}

		// Add the rest of the proofs
		while (i.hasNext()) {
			Block block = i.next();
			result.add(block.getRewardTransaction().getProof());
		}
		return result;
	}
	
	// The first block can have multiple proofs. The second parameter says whether these should be included (true) or not (false).
	public synchronized List<Proof> getProofs(Proof type, Boolean includingGenesis) {
		return getProofs(type.getClass(), includingGenesis);
	}
	
	// The first block can have multiple proofs. The second parameter says whether these should be included (true) or not (false).
	public synchronized List<Proof> getProofs(Class<?> type, Boolean includingGenesis) {
		List<Proof> temp = getProofs(includingGenesis);
		List<Proof> result = new LinkedList<>();
		for (Proof proof: temp) {
			if (proof.getClass().equals(type)) {
				result.add(proof);
			}
		}
		return result;
	}
	
	// The following method will give ALL transactions in the chain, excluding the stuff in the Genesis block
	public synchronized Set<Transaction> getTransactions() {
		Set<Transaction> result = new HashSet<>();
		Iterator<Block> i = blocks.iterator();
		i.next(); // To skip the Genesis block
		
		// Add the rest of the proofs
		while (i.hasNext()) {
			Block block = i.next();
			List<Transaction> transactions = block.getTransactions();
			result.addAll(transactions);
		}
		return result;
	}
	
	// Give the transactions of a specified account
	public synchronized Set<Transaction> getTransactions(Account account) {
		Set<Transaction> allTransactions = getTransactions();
		Set<Transaction> result = new HashSet<>();
		Iterator<Transaction> i = allTransactions.iterator();
		while (i.hasNext()) {
			Transaction t = i.next();
			if (t.getTo().equals(account) || (t.getFrom().equals(account))) {
				result.add(t);
			}
		}
		return result;
	}
	
	// Give the transactions of a specified account
	public Set<Transaction> getTransactions(Collection<Account> accounts) {
		Set<Transaction> allTransactions = getTransactions();
		Set<Transaction> result = new HashSet<>();
		Iterator<Transaction> i = allTransactions.iterator();
		while (i.hasNext()) {
			Transaction t = i.next();
			for (Account account: accounts) {
				if (t.getTo().equals(account) || (t.getFrom().equals(account))) {
					result.add(t);
					break;
				}
			}
		}
		return result;
	}
	
	public synchronized boolean isUnique(Proof proof) {
		List<Proof> proofs = getProofs(proof.getClass(), true);
		for (Proof p: proofs) {
//			if (p.getPrimes()[0].equals(proof.getPrimes()[0])) {
			if (p.equals(proof)) {
				return false;
			}
		}
		return true;
	}
	
	public synchronized Block getLastBlock() {
		return blocks.get(blocks.size() - 1);
	}
	
	public Block getBlock(int index) {
		return blocks.get(index);
	}
	
	public BigInteger getTotalValue() {
		return totalValue;
	}
	
	public synchronized GenesisBlock getGenesisBlock() {
		return (GenesisBlock) blocks.get(0);
	}
}

