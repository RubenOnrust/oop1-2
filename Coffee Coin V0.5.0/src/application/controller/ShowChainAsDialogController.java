package application.controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.List;

import domain.Asset;
import domain.Chain;
import domain.io.IChainStore;
import domain.io.xml.ChainStoreFactory;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class ShowChainAsDialogController {
    private Stage dialogStage;
    
    @FXML
	private TextArea plainText;
	
	@FXML
	public void initialize() {
		plainText.clear();
		IChainStore chainStore = ChainStoreFactory.instance().get();
		List<String> xmlList;
		try {
			Chain chain = Chain.instance(Asset.getDefault());
			xmlList = chainStore.get(chain);
			Iterator<String> i = xmlList.iterator();
			StringBuilder result = new StringBuilder();
			while (i.hasNext()) {
				result.append(i.next());
			}
			plainText.setText(result.toString());
		} catch (NoSuchAlgorithmException | IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void handleOk() {
			dialogStage.close();
	}
	
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
}
