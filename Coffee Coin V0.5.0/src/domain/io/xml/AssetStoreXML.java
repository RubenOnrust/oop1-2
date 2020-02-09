package domain.io.xml;

import domain.Asset;
import domain.io.IAssetStore;

public class AssetStoreXML implements IAssetStore {
	private static IAssetStore store;
	
	public static IAssetStore instance() {
		if (store == null) {
			store = new AssetStoreXML();
		}
		return store;
	}
	
	private AssetStoreXML() {
		
	}

	@Override
	public String get(Asset asset) {
		StringBuilder result = new StringBuilder();
		result.append("<Asset \tticker=\"");
		result.append(asset.getTicker());
		result.append("\"\n\tname=\"");
		result.append(asset.getName());
		result.append("\"\n\tdescription=\"");
		result.append(asset.getDescription());
		result.append("\"/>\n");
		return new String(result);
	}

}
