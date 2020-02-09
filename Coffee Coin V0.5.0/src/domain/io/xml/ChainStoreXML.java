package domain.io.xml;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import domain.Block;
import domain.Chain;
import domain.io.IChainStore;

public class ChainStoreXML implements IChainStore {
	private static IChainStore store;
	
	public static IChainStore instance() {
		if (store == null) {
			store = new ChainStoreXML();
		}
		return store;
	}
	
	private ChainStoreXML() {
		
	}

	@Override
	public List<String> get(Chain chain) {
		List<String> output = new ArrayList<>();
		// First output the basic data from the chain: hash, timestamp, asset
		StringBuilder result = new StringBuilder();
		result.append("<Chain\thash=\"");
		result.append(chain.getMerkleRoot().toString(16));
		result.append("\"\n\ttimestamp=\"");
		result.append(chain.getTimestamp());
		result.append("\">\n");
		result.append(lineAsset(AssetStoreFactory.instance().get().get(chain.getAsset())));
		output.add(new String(result));
		
		// Create a String per block in the chain. Do not include the Genesis block, it is way too large for that.
		List<Block> blocks = chain.getBlocks();
		Iterator<Block> i = blocks.iterator();
		while (i.hasNext()) {
			Block thisBlock = i.next();
			output.add(new String(BlockStoreFactory.instance().get().get(thisBlock)));
		}
		output.add(new String(("</Chain>\n")));
		return output;
	}
	
	private String lineAsset(String asset) {
		StringBuilder result = new StringBuilder(asset);
		result.insert(0, "\t");
		int index = result.indexOf("name", 0);
		do  {
			result.insert(index, "\t\t");
			index +=4;
			index = result.indexOf("name", index);
		}
		while (index != -1);
		index = result.indexOf("description", index);
		do  {
			result.insert(index, "\t\t");
			index +=4;
			index = result.indexOf("description", index);
		}
		while (index != -1);
		index = result.indexOf("ticker", index);
		do  {
			//result.insert(index, "\t");
			index +=4;
			index = result.indexOf("ticker", index);
		}
		while (index != -1);
		return new String(result);
	}
	
	@SuppressWarnings("unused")
	private String lineGenesisBlock(String block) {
		StringBuilder result = new StringBuilder(block);
		int index = result.indexOf("<" + XMLUtil.getProofTag(block), 0);

		while (index != -1) {
			index = result.indexOf("timestamp=", index-6);
			result.deleteCharAt(index-1);
			index = result.indexOf("primes", index);
			index = result.indexOf("\t", index-10);
			result.deleteCharAt(index);
			index = result.indexOf("<Reward", index);
			index = result.indexOf("\t", index-10);
			result.deleteCharAt(index);
			index = result.indexOf("amount", index);
			index = result.indexOf("\t", index-6);
			result.deleteCharAt(index);
			index = result.indexOf("</Reward", index);
			index = result.indexOf("\t", index-10);
			result.deleteCharAt(index);
			index = result.indexOf("<" + XMLUtil.getProofTag(new String(result)), index);
		}
		return new String(result);
	}
}
