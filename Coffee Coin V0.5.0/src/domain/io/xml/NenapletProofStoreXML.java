package domain.io.xml;

import domain.io.IProofStore;

public class NenapletProofStoreXML extends AbstractProofStoreXML  {
	private static IProofStore store;
	
	public static IProofStore instance() {
		if (store == null) {
			store = new NenapletProofStoreXML();
		}
		return store;
	}
	
	private NenapletProofStoreXML() {
		super();
	}
	
	public String getConstellationTag() {
		return "Nenaplet";
	}
}