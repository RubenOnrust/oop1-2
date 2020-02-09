package domain.io.xml;

import domain.io.IProofStore;

public class DodecapletProofStoreXML extends AbstractProofStoreXML  {
	private static IProofStore store;
	
	public static IProofStore instance() {
		if (store == null) {
			store = new DodecapletProofStoreXML();
		}
		return store;
	}
	
	private DodecapletProofStoreXML() {
		super();
	}
	
	public String getConstellationTag() {
		return "Dodecaplet";
	}
}