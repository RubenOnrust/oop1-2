package domain.proof;

import static domain.Settings.Environment.*;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.util.SortedSet;
import java.util.TreeSet;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import domain.Asset;
import domain.Settings;
import domain.proof.primes.DecapletProof;
import domain.proof.primes.DodecapletProof;
import domain.proof.primes.HexapletProof;
import domain.proof.primes.NenapletProof;
import domain.proof.primes.OctapletProof;
import domain.proof.primes.PentapletProof;
import domain.proof.primes.SeptapletProof;
import domain.proof.primes.TridecapletProof;
import domain.proof.primes.UndecapletProof;
import util.BigIntegerArrayComparable;

public class ProofsInitializer {
	private static ProofsInitializer factory = null;
	private SortedSet<Proof> proofs;
	private SortedSet<BigInteger[]> pentaplets, hexaplets, septaplets, octaplets, nenaplets, decaplets, undecaplets, dodecaplets, tridecaplets;
	
	private ProofsInitializer(Asset asset) throws IOException, NoSuchAlgorithmException {
		proofs = new TreeSet<Proof>();
		pentaplets = new TreeSet<>(new BigIntegerArrayComparable());
		hexaplets = new TreeSet<>(new BigIntegerArrayComparable());
		septaplets = new TreeSet<>(new BigIntegerArrayComparable());
		octaplets = new TreeSet<>(new BigIntegerArrayComparable());
		nenaplets = new TreeSet<>(new BigIntegerArrayComparable());
		decaplets = new TreeSet<>(new BigIntegerArrayComparable());
		undecaplets = new TreeSet<>(new BigIntegerArrayComparable());
		dodecaplets = new TreeSet<>(new BigIntegerArrayComparable());
		tridecaplets = new TreeSet<>(new BigIntegerArrayComparable());
		
		read(asset);
		if (Settings.instance().getEnvironment() == TEST) {
			write();
		}
	}
	
	public SortedSet<Proof> getProofs() {
		SortedSet<Proof> result = new TreeSet<>();
		for (Proof proof: proofs) {
			result.add(proof);
		}
		return result;
	}
	
	public SortedSet<Proof> getProofs(Class<?> type) {
		SortedSet<Proof> result = new TreeSet<>();
		for (Proof proof: proofs) {
			if (proof.getClass().equals(type)) {
				result.add(proof);
			}
		}
		return result;
	}

	public static ProofsInitializer instance(Asset asset) throws IOException, NoSuchAlgorithmException {
		if (factory == null) {
			factory = new ProofsInitializer(asset);
		}
		return factory;
	}
	
	private void readTridecaplets(Asset asset) throws IOException, NoSuchAlgorithmException {
		Settings settings = Settings.instance();
	    Reader in = new FileReader(settings.getTridecapletFile());
	    Iterable<CSVRecord> records = CSVFormat.DEFAULT
	      .withFirstRecordAsHeader()
	      .parse(in);
	    String[] tridecapletString = new String[13];
	    for (CSVRecord record : records) {
	    	tridecapletString[0] = record.get(0);
	    	tridecapletString[1] = record.get(1);
	    	tridecapletString[2] = record.get(2);
	    	tridecapletString[3] = record.get(3);
	    	tridecapletString[4] = record.get(4);
	    	tridecapletString[5] = record.get(5);
	    	tridecapletString[6] = record.get(6);
	    	tridecapletString[7] = record.get(7);
	    	tridecapletString[8] = record.get(8);
	    	tridecapletString[9] = record.get(9);
	    	tridecapletString[10] = record.get(10);
	    	tridecapletString[11] = record.get(11);
	    	tridecapletString[12] = record.get(12);
	    	TridecapletProof proof = new TridecapletProof(tridecapletString, asset);
	    	proofs.add(proof);
	    	tridecaplets.add(proof.getPrimes());
	    }
	   
	}
	
	private void readDodecaplets(Asset asset) throws IOException, NoSuchAlgorithmException {
		Settings settings = Settings.instance();
	    Reader in = new FileReader(settings.getDodecapletFile());
	    Iterable<CSVRecord> records = CSVFormat.DEFAULT
	      .withFirstRecordAsHeader()
	      .parse(in);
	    String[] dodecapletString = new String[12];
	    for (CSVRecord record : records) {
	    	dodecapletString[0] = record.get(0);
	    	dodecapletString[1] = record.get(1);
	    	dodecapletString[2] = record.get(2);
	    	dodecapletString[3] = record.get(3);
	    	dodecapletString[4] = record.get(4);
	    	dodecapletString[5] = record.get(5);
	    	dodecapletString[6] = record.get(6);
	    	dodecapletString[7] = record.get(7);
	    	dodecapletString[8] = record.get(8);
	    	dodecapletString[9] = record.get(9);
	    	dodecapletString[10] = record.get(10);
	    	dodecapletString[11] = record.get(11);
	    	DodecapletProof proof = new DodecapletProof(dodecapletString, asset);
	    	proofs.add(proof);
	    	dodecaplets.add(proof.getPrimes());
	    }
	}
	
	private void readUndecaplets(Asset asset) throws IOException, NoSuchAlgorithmException {
		Settings settings = Settings.instance();
	    Reader in = new FileReader(settings.getUndecapletFile());
	    Iterable<CSVRecord> records = CSVFormat.DEFAULT
	      .withFirstRecordAsHeader()
	      .parse(in);
	    String[] undecapletString = new String[11];
	    for (CSVRecord record : records) {
	    	undecapletString[0] = record.get(0);
	    	undecapletString[1] = record.get(1);
	    	undecapletString[2] = record.get(2);
	    	undecapletString[3] = record.get(3);
	    	undecapletString[4] = record.get(4);
	    	undecapletString[5] = record.get(5);
	    	undecapletString[6] = record.get(6);
	    	undecapletString[7] = record.get(7);
	    	undecapletString[8] = record.get(8);
	    	undecapletString[9] = record.get(9);
	    	undecapletString[10] = record.get(10);
	    	UndecapletProof proof = new UndecapletProof(undecapletString, asset);
	    	proofs.add(proof);
	    	undecaplets.add(proof.getPrimes());
	    }
	}
	
	private void readDecaplets(Asset asset) throws IOException, NoSuchAlgorithmException {
		Settings settings = Settings.instance();
	    Reader in = new FileReader(settings.getDecapletFile());
	    Iterable<CSVRecord> records = CSVFormat.DEFAULT
	      .withFirstRecordAsHeader()
	      .parse(in);
	    String[] decapletString = new String[10];
	    for (CSVRecord record : records) {
	    	decapletString[0] = record.get(0);
	    	decapletString[1] = record.get(1);
	    	decapletString[2] = record.get(2);
	    	decapletString[3] = record.get(3);
	    	decapletString[4] = record.get(4);
	    	decapletString[5] = record.get(5);
	    	decapletString[6] = record.get(6);
	    	decapletString[7] = record.get(7);
	    	decapletString[8] = record.get(8);
	    	decapletString[9] = record.get(9);
	    	DecapletProof proof = new DecapletProof(decapletString, asset);
	    	proofs.add(proof);
	    	decaplets.add(proof.getPrimes());
	    }
	}
	
	private void readNenaplets(Asset asset) throws IOException, NoSuchAlgorithmException {
		Settings settings = Settings.instance();
	    Reader in = new FileReader(settings.getNenapletFile());
	    Iterable<CSVRecord> records = CSVFormat.DEFAULT
	      .withFirstRecordAsHeader()
	      .parse(in);
	    String[] nenapletString = new String[9];
	    for (CSVRecord record : records) {
	    	nenapletString[0] = record.get(0);
	    	nenapletString[1] = record.get(1);
	    	nenapletString[2] = record.get(2);
	    	nenapletString[3] = record.get(3);
	    	nenapletString[4] = record.get(4);
	    	nenapletString[5] = record.get(5);
	    	nenapletString[6] = record.get(6);
	    	nenapletString[7] = record.get(7);
	    	nenapletString[8] = record.get(8);
	    	NenapletProof proof = new NenapletProof(nenapletString, asset);
	    	proofs.add(proof);
	    	nenaplets.add(proof.getPrimes());
	    }
	 
	}
	
	private void readOctaplets(Asset asset) throws IOException, NoSuchAlgorithmException {
		Settings settings = Settings.instance();
	    Reader in = new FileReader(settings.getOctapletFile());
	    Iterable<CSVRecord> records = CSVFormat.DEFAULT
	      .withFirstRecordAsHeader()
	      .parse(in);
	    String[] octapletString = new String[8];
	    for (CSVRecord record : records) {
	    	octapletString[0] = record.get(0);
	    	octapletString[1] = record.get(1);
	    	octapletString[2] = record.get(2);
	    	octapletString[3] = record.get(3);
	    	octapletString[4] = record.get(4);
	    	octapletString[5] = record.get(5);
	    	octapletString[6] = record.get(6);
	    	octapletString[7] = record.get(7);
	    	OctapletProof proof = new OctapletProof(octapletString, asset);
	    	proofs.add(proof);
	    	octaplets.add(proof.getPrimes());
	    }
	}
	    
    private void readSeptaplets(Asset asset) throws IOException, NoSuchAlgorithmException {
		Settings settings = Settings.instance();
	    Reader in = new FileReader(settings.getSeptapletFile());
	    Iterable<CSVRecord> records = CSVFormat.DEFAULT
	      .withFirstRecordAsHeader()
	      .parse(in);
	    String[] septapletsString = new String[7];
	    for (CSVRecord record : records) {
	    	septapletsString[0] = record.get(0);
	    	septapletsString[1] = record.get(1);
	    	septapletsString[2] = record.get(2);
	    	septapletsString[3] = record.get(3);
	    	septapletsString[4] = record.get(4);
	    	septapletsString[5] = record.get(5);
	    	septapletsString[6] = record.get(6);
	    	SeptapletProof proof = new SeptapletProof(septapletsString, asset);
	    	proofs.add(proof);
	    	septaplets.add(proof.getPrimes());
	    }
    }
	    
    private void readHexaplets(Asset asset) throws IOException, NoSuchAlgorithmException {
		Settings settings = Settings.instance();
	    Reader in = new FileReader(settings.getHexapletFile());
	    Iterable<CSVRecord> records = CSVFormat.DEFAULT
	      .withFirstRecordAsHeader()
	      .parse(in);
	    String[] hexapletString = new String[6];
	    for (CSVRecord record : records) {
	    	hexapletString[0] = record.get(0);
	    	hexapletString[1] = record.get(1);
	    	hexapletString[2] = record.get(2);
	    	hexapletString[3] = record.get(3);
	    	hexapletString[4] = record.get(4);
	    	hexapletString[5] = record.get(5);
	    	HexapletProof proof = new HexapletProof(hexapletString, asset);
	    	proofs.add(proof);
	    	hexaplets.add(proof.getPrimes());
	    }
	}
    
	private void readPentaplets(Asset asset) throws IOException, NoSuchAlgorithmException {
		Settings settings = Settings.instance();
	    Reader in = new FileReader(settings.getPentapletFile());
	    Iterable<CSVRecord> records = CSVFormat.DEFAULT
	      .withFirstRecordAsHeader()
	      .parse(in);
	    String[] pentapletString = new String[5];
	    for (CSVRecord record : records) {
	    	pentapletString[0] = record.get(0);
	    	pentapletString[1] = record.get(1);
	    	pentapletString[2] = record.get(2);
	    	pentapletString[3] = record.get(3);
	    	pentapletString[4] = record.get(4);
	    	PentapletProof proof = new PentapletProof(pentapletString, asset);
	    	proofs.add(proof);
	    	pentaplets.add(proof.getPrimes());
	    }
    }
	
	private void read(Asset asset) throws NoSuchAlgorithmException, IOException {
		readTridecaplets(asset);
		readDodecaplets(asset);
		readUndecaplets(asset);
		readDecaplets(asset);
		readNenaplets(asset);
		readOctaplets(asset);
		readSeptaplets(asset);
		readHexaplets(asset);
		readPentaplets(asset);
	}
	
	public void write() {
		backupTridecapletFile();
		backupDodecapletFile();
		backupUndecapletFile();
		backupDecapletFile();
		backupNenapletFile();
		backupOctapletFile();
		backupSeptapletFile();
		backupHexapletFile();
		backupPentapletFile();
	}
	
	private void backupTridecapletFile() {
		 Thread t = new Thread (new Runnable() {
				@Override
				public void run() {
					String filename = Settings.instance().getTridecapletFile() + ".bak";
				    FileWriter out = null;
					for (BigInteger[] primes: tridecaplets) {
						try {
							out = new FileWriter(filename, true);
						} catch (IOException e) {
							e.printStackTrace();
						}
					    try (CSVPrinter printer = new CSVPrinter(out, CSVFormat.DEFAULT)) {
					            printer.printRecord(primes[0], primes[1], primes[2], primes[3], primes[4], primes[5], primes[6], primes[7], primes[8], primes[9], primes[10], primes[11], primes[12]);
					    } catch (IOException e) {
							e.printStackTrace();
						}
					}
					tridecaplets = null; // To free up memory
				}
	    	});
	    	t.start();
	}
	
	private void backupDodecapletFile() {
		Thread t = new Thread (new Runnable() {
			@Override
			public void run() {
				String filename = Settings.instance().getDodecapletFile() + ".bak";
			    FileWriter out = null;
				for (BigInteger[] primes: dodecaplets) {
					try {
						out = new FileWriter(filename, true);
					} catch (IOException e) {
						e.printStackTrace();
					}
				    try (CSVPrinter printer = new CSVPrinter(out, CSVFormat.DEFAULT)) {
				            printer.printRecord(primes[0], primes[1], primes[2], primes[3], primes[4], primes[5], primes[6], primes[7], primes[8], primes[9], primes[10], primes[11]);
				    } catch (IOException e) {
						e.printStackTrace();
					}
				}
				dodecaplets = null; // To free up memory
			}
    	});
    	t.start();
	}
	
	private void backupUndecapletFile() {
		 Thread t = new Thread (new Runnable() {
				@Override
				public void run() {
					String filename = Settings.instance().getUndecapletFile() + ".bak";
				    FileWriter out = null;
					for (BigInteger[] primes: undecaplets) {
						try {
							out = new FileWriter(filename, true);
						} catch (IOException e) {
							e.printStackTrace();
						}
					    try (CSVPrinter printer = new CSVPrinter(out, CSVFormat.DEFAULT)) {
					            printer.printRecord(primes[0], primes[1], primes[2], primes[3], primes[4], primes[5], primes[6], primes[7], primes[8], primes[9], primes[10]);
					    } catch (IOException e) {
							e.printStackTrace();
						}
					}
					undecaplets = null; // To free up memory
				}
	    	});
	    	t.start();
	}
	
	private void backupDecapletFile() {
		Thread t = new Thread (new Runnable() {
			@Override
			public void run() {
				String filename = Settings.instance().getDecapletFile() + ".bak";
			    FileWriter out = null;
				for (BigInteger[] primes: decaplets) {
					try {
						out = new FileWriter(filename, true);
					} catch (IOException e) {
						e.printStackTrace();
					}
				    try (CSVPrinter printer = new CSVPrinter(out, CSVFormat.DEFAULT)) {
				            printer.printRecord(primes[0], primes[1], primes[2], primes[3], primes[4], primes[5], primes[6], primes[7], primes[8],primes[9]);
				    } catch (IOException e) {
						e.printStackTrace();
					}
				}
				decaplets = null; // To free up memory
			}
    	});
    	t.start();
	}
	
	private void backupNenapletFile() {
		Thread t = new Thread (new Runnable() {
			@Override
			public void run() {
				String filename = Settings.instance().getNenapletFile() + ".bak";
			    FileWriter out = null;
				for (BigInteger[] primes: nenaplets) {
					try {
						out = new FileWriter(filename, true);
					} catch (IOException e) {
						e.printStackTrace();
					}
				    try (CSVPrinter printer = new CSVPrinter(out, CSVFormat.DEFAULT)) {
				            printer.printRecord(primes[0], primes[1], primes[2], primes[3], primes[4], primes[5], primes[6], primes[7], primes[8]);
				    } catch (IOException e) {
						e.printStackTrace();
					}
				}
				nenaplets = null; // To free up memory
			}
    	});
    	t.start();
	}
	
	private void backupOctapletFile() {
		 Thread t = new Thread (new Runnable() {
				@Override
				public void run() {
					String filename = Settings.instance().getOctapletFile() + ".bak";
				    FileWriter out = null;
					for (BigInteger[] primes: octaplets) {
						try {
							out = new FileWriter(filename, true);
						} catch (IOException e) {
							e.printStackTrace();
						}
					    try (CSVPrinter printer = new CSVPrinter(out, CSVFormat.DEFAULT)) {
				            printer.printRecord(primes[0], primes[1], primes[2], primes[3], primes[4], primes[5], primes[6], primes[7]);
					    } catch (IOException e) {
							e.printStackTrace();
						}
					}
					octaplets = null; // To free up memory
				}
	    	});
	    	t.start();
	}
	
	private void backupSeptapletFile() {
		Thread t = new Thread (new Runnable() {
			@Override
			public void run() {
				String filename = Settings.instance().getSeptapletFile() + ".bak";
			    FileWriter out = null;
				for (BigInteger[] primes: septaplets) {
					try {
						out = new FileWriter(filename, true);
					} catch (IOException e) {
						e.printStackTrace();
					}
				    try (CSVPrinter printer = new CSVPrinter(out, CSVFormat.DEFAULT)) {
			            printer.printRecord(primes[0], primes[1], primes[2], primes[3], primes[4], primes[5], primes[6]);
				    } catch (IOException e) {
						e.printStackTrace();
					}
				}
				septaplets = null; // To free up memory
			}
    	});
    	t.start();
	}
	
	private void backupHexapletFile() {
		Thread t = new Thread (new Runnable() {
			@Override
			public void run() {
				String filename = Settings.instance().getHexapletFile() + ".bak";
			    FileWriter out = null;
				for (BigInteger[] primes: hexaplets) {
					try {
						out = new FileWriter(filename, true);
					} catch (IOException e) {
						e.printStackTrace();
					}
				    try (CSVPrinter printer = new CSVPrinter(out, CSVFormat.DEFAULT)) {
			            printer.printRecord(primes[0], primes[1], primes[2], primes[3], primes[4], primes[5]);
				    } catch (IOException e) {
						e.printStackTrace();
					}
				}
				hexaplets = null; // To free up memory
			}
    	});
    	t.start();
	}
	
	private void backupPentapletFile() {
    	Thread t = new Thread (new Runnable() {
			@Override
			public void run() {
				String filename = Settings.instance().getPentapletFile() + ".bak";
			    FileWriter out = null;
				for (BigInteger[] primes: pentaplets) {
					try {
						out = new FileWriter(filename, true);
					} catch (IOException e) {
						e.printStackTrace();
					}
				    try (CSVPrinter printer = new CSVPrinter(out, CSVFormat.DEFAULT)) {
				            printer.printRecord(primes[0], primes[1], primes[2], primes[3], primes[4]);
				    } catch (IOException e) {
						e.printStackTrace();
					}
				}
				pentaplets = null; // To free up memory
			}
    	});
    	t.start();
	}
}
