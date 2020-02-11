package miner;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.apache.commons.io.FileUtils;
import domain.Settings;
import miner.*;

public class MinerCollection {
	private static MinerCollection instance;
	private List<String> miners;
	    
	private MinerCollection(){}
	    
	public static synchronized MinerCollection getInstance(){
		if(instance == null){
			instance = new MinerCollection();
		}
		return instance;
	}

	public List<String> getMiners() {
		return miners;
	}

	public void setMiners(List<String> miners) {
		this.miners = miners;
	}
	
	public void makeMinerList() {
		List<File> minerFiles = new ArrayList<File>();
		final String[] SUFFIX = {"class"};  // use the suffix to filter
		File minerPath = Settings.instance().getMinerPath();
		Collection<File> files = FileUtils.listFiles(minerPath, SUFFIX, true);
		for (File file: files) {
			if ((file.getName().endsWith("Miner.class")) && 
		(!file.getName().startsWith("I")) && 
		(!file.getName().contains("Abstract"))) {
			    	minerFiles.add(file);
			}
		}
		minerFiles.get(1).getParent();
		List<String> listOfMiners = new ArrayList<String>();
		for (int i = 0; i < minerFiles.size(); i++) {
			String b = minerFiles.get(i).getName();
			listOfMiners.add(b);
		}
		this.miners = listOfMiners;
	}
	public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
		List<File> minerFiles = new ArrayList<File>();
		final String[] SUFFIX = {"class"};  // use the suffix to filter
		File minerPath = Settings.instance().getMinerPath();
		Collection<File> files = FileUtils.listFiles(minerPath, SUFFIX, true);
		for (File file: files) {
			if ((file.getName().endsWith("Miner.class")) && 
		(!file.getName().startsWith("I")) && 
		(!file.getName().contains("Abstract"))) {
			    	minerFiles.add(file);
			}
		}
		minerFiles.get(1).getParent();
		List<String> listOfMiners = new ArrayList<String>();
		for (int i = 0; i < minerFiles.size(); i++) {
			String b = minerFiles.get(i).getName();
			listOfMiners.add(b);
		}
		ClassLoader classLoader = MinerCollection.class.getClassLoader();
		Class aClass = classLoader.loadClass("miner.*");
		System.out.println();
	}
}
