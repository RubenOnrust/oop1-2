package domain.proof;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import domain.Asset;
import domain.Block;
import domain.Chain;
import domain.Settings;
import domain.Transaction;
import util.collection.Tree;
import util.pattern.IObserver;
import util.pattern.Observable;

// Objects of this class represent DAG-like structures as used in algorithms such as scrypt and dagger
//
// In order to build the structure, the following algorithm is used:
// 1. Retrieve the last block from the chain as starting point
// 2. Do a SHA256 hash on the merkle root of the block. The result is called xroot and used as 
//    the parent in the tree, so for the first block the root. This xroot value is given to a
//    recursion together with the parent (null for root):
// 
// 3. The element given is added to the tree
// 4. Now to calculate the two children:
//    4a. The xroot is hashed again and yroot is calculated as this hash mod the total number of blocks
//        This gives a block number. The block is retrieved and the merkle root hashed. The result is 
//        done mod the number of transactions in the block - which will always be at least 1, the 
//        reward transaction. That transaction is retrieved, the hash is hashed again to give the 
//        value for child 1
//    4b.xroot is hashed again, the second child is calculated the same way as child 1 from here
// 5. Both children are passed to the method again, now with element as the parent
// 6. The stop condition is when the required number of elements have been added to the tree, 
//    as defined in the settings file
//
// Filling the DAG needs to block all access
// Whenever the chain updates - usually by accepting a block - the structure needs to be recalculated

public class DirectedAcyclicGraph implements IObserver {
	private Tree<BigInteger> tree;
	private int treeDepth;
	private MessageDigest digest;
	
	public DirectedAcyclicGraph() throws NoSuchAlgorithmException, IOException {
		// 1.
		Chain chain = Chain.instance(Asset.getDefault());
		chain.addObserver(this);
		buildTree();
	}
	
	private synchronized void buildTree() throws NoSuchAlgorithmException, IOException {
		Chain chain = Chain.instance(Asset.getDefault());
		Block lastBlock = chain.getLastBlock();
		BigInteger merkleRoot = lastBlock.getMerkleRoot();

		// 2. 
		this.digest = MessageDigest.getInstance("SHA-256");
		byte[] xhashed = digest.digest(merkleRoot.toByteArray());
		BigInteger xroot = new BigInteger(xhashed);

		// 3.
		this.treeDepth = Settings.instance().getDAGdepth();
		tree = new Tree<BigInteger>(xroot, (int) Math.pow(2, this.treeDepth));
		
		// 4.
		BigInteger child1 = calculateChild(xhashed);
		BigInteger child2 = calculateChild(digest.digest(xhashed));
		addElementAndChildren(xroot, child1, 1);
		addElementAndChildren(xroot, child2, 1);
	}
	
	private void addElementAndChildren(BigInteger parent, BigInteger element, int level) throws NoSuchAlgorithmException, IOException {
		//3.
		tree.addElement(parent, element);
		
		// 6. Test for stop condition
		if (level < this.treeDepth) {
			// 4. Get children
			BigInteger child1 = calculateChild(element.toByteArray());
			BigInteger child2 = calculateChild(digest.digest(element.toByteArray()));
			
			// Add those children
			addElementAndChildren(element, child1, level+1);
			addElementAndChildren(element, child2, level+1);
		}
	}
	
	// 4a. and 4b.
	private BigInteger calculateChild(byte[] parent) throws NoSuchAlgorithmException, IOException {
		Chain chain = Chain.instance(Asset.getDefault());
		byte[] yhashed = digest.digest(parent);
		BigInteger numberOfBlocks = chain.getNumberOfBlocks();
		int indexChild = new BigInteger(yhashed).mod(numberOfBlocks).intValue();
		Block child1 = chain.getBlock(indexChild);
		BigInteger merkleRoot = child1.getMerkleRoot();
		byte[] child1hashed = digest.digest(merkleRoot.toByteArray());
		BigInteger transactionIndex = new BigInteger(child1hashed).mod(new BigInteger(Integer.toString(child1.size())));
		Transaction t = child1.getTransaction(transactionIndex.intValue());
		return new BigInteger(digest.digest(t.getOwnHash().toByteArray()));
	}
	
	public BigInteger get(int index) {
		return tree.get(index);
	}

	@Override
	public synchronized void update(Observable observable) {
		try {
			buildTree();
		} catch (NoSuchAlgorithmException | IOException e) {
			e.printStackTrace();
		}
	}
}
