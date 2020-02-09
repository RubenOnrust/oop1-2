package util.view;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class AlertUtil {
	
	public static void notYetImplementedMessage(String functionality, String version) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(functionality);
		alert.setHeaderText(functionality + " not yet implemented");alert.setContentText("The functionality " + functionality + " is planned to be implemented in version " + version);
		alert.showAndWait();
	}
	
	public static void notYetImplementedWarning(String functionality, String version) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle(functionality);
		alert.setHeaderText(functionality + " not yet implemented");alert.setContentText("The functionality " + functionality + " is planned to be implemented in version " + version + "\nThis will be a DANGEROUS functionality once finished, as it will irrevocably destroy ALL keys in the application!");
		alert.showAndWait();
	}
}
