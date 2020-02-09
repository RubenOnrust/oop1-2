package domain.io.xml;

import domain.Reward;
import domain.io.IRewardStore;

public class RewardStoreXML implements IRewardStore {
	private static IRewardStore store;
	
	public static IRewardStore instance() {
		if (store == null) {
			store = new RewardStoreXML();
		}
		return store;
	}
	
	private RewardStoreXML() {
		
	}

	@Override
	public String get(Reward reward) {
		StringBuilder result = new StringBuilder();
		result.append("<Reward amount=\"");
		result.append(reward.getAmount());
		result.append("\">\n");
		result.append(lineAsset((AssetStoreFactory.instance().get().get(reward.getAsset()))));
		result.append("</Reward>\n");
		return new String(result);
	}
	
	private String lineAsset(String asset) {
		StringBuilder result = new StringBuilder(asset);
		result.insert(0, "\t");
		int index = result.indexOf("name", 0);
		do  {
			result.insert(index, "\t");
			index +=4;
			index = result.indexOf("name", index);
		}
		while (index != -1);
		index = result.indexOf("description", index);
		do  {
			result.insert(index, "\t");
			index +=4;
			index = result.indexOf("description", index);
		}
		while (index != -1);
		return new String(result);
	}	
}