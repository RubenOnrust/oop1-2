package domain.io.xml;

import domain.io.IProofStore;

public class TridecapletProofStoreXML extends AbstractProofStoreXML  {
	private static IProofStore store;
	
	public static IProofStore instance() {
		if (store == null) {
			store = new TridecapletProofStoreXML();
		}
		return store;
	}
	
	private TridecapletProofStoreXML() {
		super();
	}
	
	public String getConstellationTag() {
		return "Tridecaplet";
	}
}