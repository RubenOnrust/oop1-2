package domain.io.xml;

import domain.io.IProofStore;

public class OctapletProofStoreXML extends AbstractProofStoreXML  {
	private static IProofStore store;
	
	public static IProofStore instance() {
		if (store == null) {
			store = new OctapletProofStoreXML();
		}
		return store;
	}
	
	private OctapletProofStoreXML() {
		super();
	}
	
	public String getConstellationTag() {
		return "Octaplet";
	}
}