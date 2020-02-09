package domain.io.xml;

import domain.io.IRewardStore;

public class RewardStoreFactory {
	private static RewardStoreFactory factory = null;

	
	private RewardStoreFactory() {

	}
	
	public static RewardStoreFactory instance() {
		if (factory == null) {
			factory = new RewardStoreFactory();
		}
		return factory;
	}
	
	public IRewardStore get() {
		return RewardStoreXML.instance();
	}
}
