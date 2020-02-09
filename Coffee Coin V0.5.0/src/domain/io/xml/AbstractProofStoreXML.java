package domain.io.xml;

import domain.io.IProofStore;
import domain.proof.Proof;

public abstract class AbstractProofStoreXML implements IProofStore {
	protected AbstractProofStoreXML() {
		
	}
	
	@Override
	public String get(Proof proof) {
		StringBuilder result = new StringBuilder();
		result.append("<" + getConstellationTag() + "Proof\ttimestamp=\"");
		result.append(proof.getTimestamp());
		result.append("\"\n");
		result.append(proof.getProofValuesTags());
		result.append(lineReward(RewardStoreFactory.instance().get().get(proof.getReward())));
		result.append("</" + getConstellationTag() + "Proof>\n");
		return new String(result);
	}
	
	protected String lineReward(String reward) {
		StringBuilder result = new StringBuilder(reward);
		result.insert(0, "\t\t");
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
			result.insert(index, "");
			index +=4;
			index = result.indexOf("ticker", index);
		}
		while (index != -1);
		while (index != -1);
		index = result.indexOf("<Asset", index);
		do  {
			result.insert(index, "\t\t");
			index +=4;
			index = result.indexOf("<Asset", index);
		}
		while (index != -1);
		index = result.indexOf("</Reward>", index);
		do  {
			result.insert(index, "\t\t");
			index +=4;
			index = result.indexOf("</Reward>", index);
		}
		while (index != -1);
		return new String(result);
	}
	
	public abstract String getConstellationTag();
}