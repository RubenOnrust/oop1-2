package miner;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.apache.commons.io.FileUtils;
import com.sun.scenario.Settings;

public class MinerCollection {
	private static MinerCollection instance;
	    
	private MinerCollection(){}
	    
	public static synchronized MinerCollection getInstance(){
		if(instance == null){
			instance = new MinerCollection();
		}
		return instance;
	}
	public void getMiners() {
		List<File> minerFiles = new ArrayList<File>();
		final String[] SUFFIX = {"class"};  // use the suffix to filter
		File minerPath = Settings.getInstance().getMinerPath();
		Collection<File> files = FileUtils.listFiles(minerPath, SUFFIX, true);
		for (File file: files) {
			if ((file.getName().endsWith("Miner.class")) && 
		(!file.getName().startsWith("I")) && 
		(!file.getName().contains("Abstract"))) {
			    	minerFiles.add(file);
			}
		}

	}
	
	public static void main(String[] args) {
		MinerCollection a = new MinerCollection();
		a.getMiners();
		
	}

}
