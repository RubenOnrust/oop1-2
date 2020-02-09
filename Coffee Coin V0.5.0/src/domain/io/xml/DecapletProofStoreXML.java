package domain.io.xml;

import domain.io.IProofStore;

public class DecapletProofStoreXML extends AbstractProofStoreXML  {
	private static IProofStore store;
	
	public static IProofStore instance() {
		if (store == null) {
			store = new DecapletProofStoreXML();
		}
		return store;
	}
	
	private DecapletProofStoreXML() {
		super();
	}
	
	public String getConstellationTag() {
		return "Decaplet";
	}
}