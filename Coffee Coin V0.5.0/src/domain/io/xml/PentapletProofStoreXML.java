package domain.io.xml;

import domain.io.IProofStore;

public class PentapletProofStoreXML extends AbstractProofStoreXML  {
	private static IProofStore store;
	
	public static IProofStore instance() {
		if (store == null) {
			store = new PentapletProofStoreXML();
		}
		return store;
	}
	
	private PentapletProofStoreXML() {
		super();
	}
	
	public String getConstellationTag() {
		return "Pentaplet";
	}
}