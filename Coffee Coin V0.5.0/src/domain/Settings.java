package domain;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class Settings {
	private static Settings internalObject = null;
	private ClassLoader classLoader;
	public static final int PRIME_CERTAINTY = 11;
	
	private Environment environment;
	private File settingsFile;
	private int logLevel;
	
	private File genesisPath, peersFile, walletFile, baseDirectory, databaseDirectory, minerPath;
	private int configPort, RPCPort, chainListener, chainAnswer, maxPeers;

	private int walletRepairLevel;
	private String walletEncryptionAlgorithm;
	private int encryptionLevel;
	private String walletPassword;
	
	private Account miningTarget;
	private int numberOfMinerThreads;
	private int minerPriority;
	private boolean minerAutoStart, includeSubdirs;
	private File tridecapletFile, dodecapletFile, undecapletFile, decapletFile, nenapletFile, octapletFile, septapletFile, hexapletFile, pentapletFile;
	private BigInteger tridecapletValue, dodecapletValue, undecapletValue, decapletValue, nenapletValue, octapletValue, septapletValue, hexapletValue, pentapletValue;
	private long randomDifficulty;
	private long SHA512Difficulty;
	private int DAGdepth;
	
	public static void main(String[] args) {
		Settings settings = Settings.instance();
		System.out.println(settings);
	}
		
	public static synchronized Settings instance() {
		if (internalObject == null) {
			internalObject = new Settings();
		}
		return internalObject;
	}
	
	private Settings() {
		classLoader = getClass().getClassLoader();
		settingsFile = new File(System.getProperty("user.home") + "/Settings.txt");
		reset();
		loadSettings();
	}
	
	public void reset() {
		// Give default values to all settings attributes
		miningTarget = Account.getAssetBitBucket();
	}
	
	private void loadSettings() {
		List<String> lines = null;
		try {
			lines = FileUtils.readLines(settingsFile, "UTF-8");
		} catch (IOException e) {
			System.out.println("Settings file not found at " + settingsFile + " ; now exiting.");
			System.exit(1);
		}
		
		// Take values and assign to variables
		environment = extractEnvironmentSetting(lines);
		logLevel = extractIntSetting(lines, "LogLevel");
		peersFile = extractFileSetting(lines, "Peers");
		walletFile = extractFileSetting(lines, "Wallet");
		baseDirectory = extractFileSetting(lines, "BaseDirectory");
		databaseDirectory = extractFileSetting(lines, "Database");
		minerPath = extractFileSetting(lines, "MinerPath");
		
		configPort = extractIntSetting(lines, "ConfigPort");
		RPCPort = extractIntSetting(lines, "RPCPort");
		chainListener = extractIntSetting(lines, "ChainListen");
		chainAnswer = extractIntSetting(lines, "ChainAnswer");
		maxPeers = extractIntSetting(lines, "MaxPeers");
		
		walletRepairLevel = extractIntSetting(lines, "RepairLevel");
		walletEncryptionAlgorithm = extractStringSetting(lines, "EncryptionAlgorithm");
		encryptionLevel = extractIntSetting(lines, "EncryptionLevel");
		walletPassword = extractStringSetting(lines, "WalletPassword");
		
		numberOfMinerThreads = extractIntSetting(lines, "Threads");
		minerPriority = extractIntSetting(lines, "Priority");
		minerAutoStart = extractBooleanSetting(lines, "Autostart");
		includeSubdirs = extractBooleanSetting(lines, "IncludeSubdirs");
		
		randomDifficulty = extractLongSetting(lines, "randomDifficulty");
		SHA512Difficulty = extractLongSetting(lines, "SHA512Difficulty");
		DAGdepth = extractIntSetting(lines, "DAGdepth");

		genesisPath = extractFileSetting(lines, "GenesisPath");
		tridecapletFile = extractFileSetting(lines, "tridecapletFile", genesisPath);
		dodecapletFile = extractFileSetting(lines, "dodecapletFile", genesisPath);
		undecapletFile = extractFileSetting(lines, "undecapletFile", genesisPath);
		decapletFile = extractFileSetting(lines, "decapletFile", genesisPath);
		nenapletFile = extractFileSetting(lines, "nenapletFile", genesisPath);
		octapletFile = extractFileSetting(lines, "octapletFile", genesisPath);
		septapletFile = extractFileSetting(lines, "septapletFile", genesisPath);
		hexapletFile = extractFileSetting(lines, "hexapletFile", genesisPath);
		pentapletFile = extractFileSetting(lines, "pentapletFile", genesisPath);

		tridecapletValue = extractBigIntegerSetting(lines, "tridecapletValue");
		dodecapletValue = extractBigIntegerSetting(lines, "dodecapletValue");
		undecapletValue = extractBigIntegerSetting(lines, "undecapletValue");
		decapletValue = extractBigIntegerSetting(lines, "decapletValue");
		nenapletValue = extractBigIntegerSetting(lines, "nenapletValue");
		octapletValue = extractBigIntegerSetting(lines, "octapletValue");
		septapletValue = extractBigIntegerSetting(lines, "septapletValue");
		hexapletValue = extractBigIntegerSetting(lines, "hexapletValue");
		pentapletValue = extractBigIntegerSetting(lines, "pentapletValue");
		
		// Always have the following as last one; it will change the place where the settings file will be written, but also mess up reading!
		settingsFile = extractFileSetting(lines, "Settings");
	}
	
	public void saveSettings() {
		// Save to the settings file.
		try {
			String text = "[Local]";
			FileUtils.writeStringToFile(settingsFile, text+"\n","UTF-8", false);
			text = "Environment: " + environment.toString();
			FileUtils.writeStringToFile(settingsFile, text+"\n","UTF-8", true);
		}
		catch (IOException e) {
			e.printStackTrace();
			System.exit(17);
		}
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Settings [environment=");
		builder.append(environment);
		builder.append(", settingsFile=");
		builder.append(settingsFile);
		builder.append(", logLevel=");
		builder.append(logLevel);
		builder.append(", genesisPath=");
		builder.append(genesisPath);
		builder.append(", peersFile=");
		builder.append(peersFile);
		builder.append(", walletFile=");
		builder.append(walletFile);
		builder.append(", baseDirectory=");
		builder.append(baseDirectory);
		builder.append(", databaseDirectory=");
		builder.append(databaseDirectory);
		builder.append(", minerPath=");
		builder.append(minerPath);
		builder.append(", configPort=");
		builder.append(configPort);
		builder.append(", RPCPort=");
		builder.append(RPCPort);
		builder.append(", chainListener=");
		builder.append(chainListener);
		builder.append(", chainAnswer=");
		builder.append(chainAnswer);
		builder.append(", maxPeers=");
		builder.append(maxPeers);
		builder.append(", walletRepairLevel=");
		builder.append(walletRepairLevel);
		builder.append(", walletEncryptionAlgorithm=");
		builder.append(walletEncryptionAlgorithm);
		builder.append(", encryptionLevel=");
		builder.append(encryptionLevel);
		builder.append(", walletPassword=");
		builder.append(walletPassword);
		builder.append(", miningTarget=");
		builder.append(miningTarget);
		builder.append(", numberOfMinerThreads=");
		builder.append(numberOfMinerThreads);
		builder.append(", minerPriority=");
		builder.append(minerPriority);
		builder.append(", minerAutoStart=");
		builder.append(minerAutoStart);
		builder.append(", includeSubdirs=");
		builder.append(includeSubdirs);
		builder.append(", tridecapletFile=");
		builder.append(tridecapletFile);
		builder.append(", dodecapletFile=");
		builder.append(dodecapletFile);
		builder.append(", undecapletFile=");
		builder.append(undecapletFile);
		builder.append(", decapletFile=");
		builder.append(decapletFile);
		builder.append(", nenapletFile=");
		builder.append(nenapletFile);
		builder.append(", octapletFile=");
		builder.append(octapletFile);
		builder.append(", septapletFile=");
		builder.append(septapletFile);
		builder.append(", hexapletFile=");
		builder.append(hexapletFile);
		builder.append(", pentapletFile=");
		builder.append(pentapletFile);
		builder.append(", tridecapletValue=");
		builder.append(tridecapletValue);
		builder.append(", dodecapletValue=");
		builder.append(dodecapletValue);
		builder.append(", undecapletValue=");
		builder.append(undecapletValue);
		builder.append(", decapletValue=");
		builder.append(decapletValue);
		builder.append(", nenapletValue=");
		builder.append(nenapletValue);
		builder.append(", octapletValue=");
		builder.append(octapletValue);
		builder.append(", septapletValue=");
		builder.append(septapletValue);
		builder.append(", hexapletValue=");
		builder.append(hexapletValue);
		builder.append(", pentapletValue=");
		builder.append(pentapletValue);
		builder.append(", randomDifficulty=");
		builder.append(randomDifficulty);
		builder.append(", SHA512Difficulty=");
		builder.append(SHA512Difficulty);
		builder.append(", DAGdepth=");
		builder.append(DAGdepth);
		builder.append("]");
		return builder.toString();
	}

	private Environment extractEnvironmentSetting(List<String> lines) {
		String text = extractStringSetting(lines, "Environment");
		switch (text) {
			case "DEVELOPMENT": return Environment.DEVELOPMENT;
			case "TEST": return Environment.TEST;
			case "PRODUCTION": return Environment.PRODUCTION;
			default: return Environment.DEVELOPMENT;
		}
	}
	
	private String extractStringSetting(List<String> lines, String attribute) {
		String target = attribute.toUpperCase();
		for (String line: lines) {
			String text = line.toUpperCase();
			if (text.startsWith(target)) {
				text = line.substring(attribute.length()+1, line.length()).trim();
				return text;
			}
		}
		return null;
	}
	
	private File extractFileSetting(List<String> lines, String attribute) {
		String text = extractStringSetting(lines, attribute);
		File file = new File(text);
		
		// If the file does not exist, an empty File object will now have been created
		// Check this; for now, write message, but change to giving a popup to select the correct file
		if (!file.exists()) {
		    if (environment == Environment.DEVELOPMENT) {
		    	System.out.println("File or path not found: " + attribute);
		    	return null;
		    }
		    else {
		    	return null;
		    }
		}
		else {
			return file;
		}
	}
	
	private File extractFileSetting(List<String> lines, String attribute, File path) {
		String text = extractStringSetting(lines, attribute);
		String pathname = path.getAbsolutePath();
		File file = new File(pathname + "\\" + text);
		
		// If the file does not exist, an empty File object will now have been created
		// Check this; for now, write message, but change to giving a popup to select the correct file
		if (!file.exists()) {
			return null;
		}
		else {
			return file;
		}
	}

	private boolean extractBooleanSetting(List<String> lines, String attribute) {
		String text = extractStringSetting(lines, attribute);
		if (text.contentEquals("TRUE") ) {
			return true;
		}
		else {
			return false;
		}
	}
	
	private int extractIntSetting(List<String> lines, String attribute) {
		String text = extractStringSetting(lines, attribute);
		try {
			int result = Integer.valueOf(text);
			return result;
		}
		catch (NumberFormatException e) {
			return -1;
		}
	}
	
	private long extractLongSetting(List<String> lines, String attribute) {
		String text = extractStringSetting(lines, attribute);
		try {
			long result = Long.valueOf(text);
			return result;
		}
		catch (NumberFormatException e) {
			return -1;
		}
	}
	
	private BigInteger extractBigIntegerSetting(List<String> lines, String attribute) {
		String text = extractStringSetting(lines, attribute);
		try {
			BigInteger result = new BigInteger(text);
			return result;
		}
		catch (NumberFormatException e) {
			return BigInteger.ZERO.subtract(BigInteger.ONE);
		}
	}
	
	public Environment getEnvironment() {
		return environment;
	}

	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}

	public File getSettingsFile() {
		return settingsFile;
	}

	public void setSettingsFile(File settingsFile) {
		this.settingsFile = settingsFile;
	}

	public ClassLoader getClassLoader() {
		return classLoader;
	}

	public void setClassLoader(ClassLoader classLoader) {
		this.classLoader = classLoader;
	}

	public int getLogLevel() {
		return logLevel;
	}

	public void setLogLevel(int logLevel) {
		this.logLevel = logLevel;
	}

	public File getGenesisPath() {
		return genesisPath;
	}

	public void setGenesisPath(File genesisPath) {
		this.genesisPath = genesisPath;
	}

	public File getPeersFile() {
		return peersFile;
	}

	public void setPeersFile(File peersFile) {
		this.peersFile = peersFile;
	}

	public File getWalletFile() {
		return walletFile;
	}

	public void setWalletFile(File walletFile) {
		this.walletFile = walletFile;
	}

	public File getBaseDirectory() {
		return baseDirectory;
	}

	public void setBaseDirectory(File baseDirectory) {
		this.baseDirectory = baseDirectory;
	}

	public File getDatabaseDirectory() {
		return databaseDirectory;
	}

	public void setDatabaseDirectory(File databaseDirectory) {
		this.databaseDirectory = databaseDirectory;
	}

	public File getMinerPath() {
		return minerPath;
	}

	public void setMinerPath(File minerPath) {
		this.minerPath = minerPath;
	}

	public int getConfigPort() {
		return configPort;
	}

	public void setConfigPort(int configPort) {
		this.configPort = configPort;
	}

	public int getRPCPort() {
		return RPCPort;
	}

	public void setRPCPort(int rPCPort) {
		RPCPort = rPCPort;
	}

	public int getChainListener() {
		return chainListener;
	}

	public void setChainListener(int chainListener) {
		this.chainListener = chainListener;
	}

	public int getChainAnswer() {
		return chainAnswer;
	}

	public void setChainAnswer(int chainAnswer) {
		this.chainAnswer = chainAnswer;
	}

	public int getMaxPeers() {
		return maxPeers;
	}

	public void setMaxPeers(int maxPeers) {
		this.maxPeers = maxPeers;
	}

	public int getWalletRepairLevel() {
		return walletRepairLevel;
	}

	public void setWalletRepairLevel(int walletRepairLevel) {
		this.walletRepairLevel = walletRepairLevel;
	}

	public String getWalletEncryptionAlgorithm() {
		return walletEncryptionAlgorithm;
	}

	public void setWalletEncryptionAlgorithm(String walletEncryptionAlgorithm) {
		this.walletEncryptionAlgorithm = walletEncryptionAlgorithm;
	}

	public int getEncryptionLevel() {
		return encryptionLevel;
	}

	public void setEncryptionLevel(int encryptionLevel) {
		this.encryptionLevel = encryptionLevel;
	}

	public String getWalletPassword() {
		return walletPassword;
	}

	public void setWalletPassword(String walletPassword) {
		this.walletPassword = walletPassword;
	}

	public int getNumberOfMinerThreads() {
		return numberOfMinerThreads;
	}

	public void setNumberOfMinerThreads(int numberOfMinerThreads) {
		this.numberOfMinerThreads = numberOfMinerThreads;
	}

	public int getMinerPriority() {
		return minerPriority;
	}

	public void setMinerPriority(int minerPriority) {
		this.minerPriority = minerPriority;
	}

	public boolean isMinerAutoStart() {
		return minerAutoStart;
	}

	public void setMinerAutoStart(boolean minerAutoStart) {
		this.minerAutoStart = minerAutoStart;
	}

	public File getTridecapletFile() {
		return tridecapletFile;
	}

	public void setTridecapletFile(File tridecapletFile) {
		this.tridecapletFile = tridecapletFile;
	}

	public File getDodecapletFile() {
		return dodecapletFile;
	}

	public void setDodecapletFile(File dodecapletFile) {
		this.dodecapletFile = dodecapletFile;
	}

	public File getUndecapletFile() {
		return undecapletFile;
	}

	public void setUndecapletFile(File undecapletFile) {
		this.undecapletFile = undecapletFile;
	}

	public File getDecapletFile() {
		return decapletFile;
	}

	public void setDecapletFile(File decapletFile) {
		this.decapletFile = decapletFile;
	}

	public File getNenapletFile() {
		return nenapletFile;
	}

	public void setNenapletFile(File nenapletFile) {
		this.nenapletFile = nenapletFile;
	}

	public File getOctapletFile() {
		return octapletFile;
	}

	public void setOctapletFile(File octapletFile) {
		this.octapletFile = octapletFile;
	}

	public File getSeptapletFile() {
		return septapletFile;
	}

	public void setSeptapletFile(File septapletFile) {
		this.septapletFile = septapletFile;
	}

	public File getHexapletFile() {
		return hexapletFile;
	}

	public void setHexapletFile(File hexapletFile) {
		this.hexapletFile = hexapletFile;
	}

	public File getPentapletFile() {
		return pentapletFile;
	}

	public void setPentapletFile(File pentapletFile) {
		this.pentapletFile = pentapletFile;
	}

	public BigInteger getTridecapletValue() {
		return tridecapletValue;
	}

	public void setTridecapletValue(BigInteger tridecapletValue) {
		this.tridecapletValue = tridecapletValue;
	}

	public BigInteger getDodecapletValue() {
		return dodecapletValue;
	}

	public void setDodecapletValue(BigInteger dodecapletValue) {
		this.dodecapletValue = dodecapletValue;
	}

	public BigInteger getUndecapletValue() {
		return undecapletValue;
	}

	public void setUndecapletValue(BigInteger undecapletValue) {
		this.undecapletValue = undecapletValue;
	}

	public BigInteger getDecapletValue() {
		return decapletValue;
	}

	public void setDecapletValue(BigInteger decapletValue) {
		this.decapletValue = decapletValue;
	}

	public BigInteger getNenapletValue() {
		return nenapletValue;
	}

	public void setNenapletValue(BigInteger nenapletValue) {
		this.nenapletValue = nenapletValue;
	}

	public BigInteger getOctapletValue() {
		return octapletValue;
	}

	public void setOctapletValue(BigInteger octapletValue) {
		this.octapletValue = octapletValue;
	}

	public BigInteger getSeptapletValue() {
		return septapletValue;
	}

	public void setSeptapletValue(BigInteger septapletValue) {
		this.septapletValue = septapletValue;
	}

	public BigInteger getHexapletValue() {
		return hexapletValue;
	}

	public void setHexapletValue(BigInteger hexapletValue) {
		this.hexapletValue = hexapletValue;
	}

	public BigInteger getPentapletValue() {
		return pentapletValue;
	}

	public void setPentapletValue(BigInteger pentapletValue) {
		this.pentapletValue = pentapletValue;
	}

	public long getRandomDifficulty() {
		return randomDifficulty;
	}

	public void setRandomDifficulty(long randomDifficulty) {
		this.randomDifficulty = randomDifficulty;
	}

	public long getSHA512Difficulty() {
		return SHA512Difficulty;
	}

	public void setSHA512Difficulty(long sHA512Difficulty) {
		SHA512Difficulty = sHA512Difficulty;
	}

	public int getDAGdepth() {
		return DAGdepth;
	}

	public void setDAGdepth(int dAGdepth) {
		DAGdepth = dAGdepth;
	}
	
	public Account getMiningTarget() {
		return miningTarget;
	}

	public void setMiningTarget(Account miningTarget) {
		this.miningTarget = miningTarget;
	}

	public boolean isIncludeSubdirs() {
		return includeSubdirs;
	}

	public void setIncludeSubdirs(boolean includeSubdirs) {
		this.includeSubdirs = includeSubdirs;
	}

	public static int getPrimeCertainty() {
		return PRIME_CERTAINTY;
	}

	public enum Environment { DEVELOPMENT, TEST, PRODUCTION

	}
}
