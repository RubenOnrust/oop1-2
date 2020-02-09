package domain.io.xml;

public class XMLUtil {
	public static String getProofTag(String xml) {
		int indexEnd = xml.indexOf("Proof");
		int indexStart1 = xml.indexOf("<", indexEnd-20);
		int indexStart2 = xml.indexOf("</", indexEnd);
		
		if (indexStart2 == -1) {
			return xml.substring(indexStart1+1, indexEnd+5);
		}
		else if (indexStart1 < indexStart2) {
			return xml.substring(indexStart1+1, indexEnd+5);
		}
		else {
			return xml.substring(indexStart2+2, indexEnd+5);
		}
	}
}
