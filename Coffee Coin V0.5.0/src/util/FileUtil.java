package util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


public class FileUtil {
	private List<String> lines;
	String filename;
	Path file;
	
	public FileUtil(List<String> lines, String filename) throws IOException {
		this.lines = lines;
		this.filename = filename;
		file = Paths.get(filename);
		Files.write(file, lines, StandardCharsets.UTF_8, StandardOpenOption.APPEND);
	}
	
	public FileUtil(String filename) throws IOException {
		this.lines = new LinkedList<String>();
		this.filename = filename;
		file = Paths.get(filename);
		Files.write(file, lines, StandardCharsets.UTF_8, StandardOpenOption.APPEND);
	}
	
	public void writeFile() throws IOException {
		Files.write(file, lines, StandardCharsets.UTF_8, StandardOpenOption.APPEND);
	}

	public void add(String line) throws IOException {
		List<String> lineToAdd = Arrays.asList(line);
		lines.add(line);
		Files.write(file, lineToAdd, StandardCharsets.UTF_8, StandardOpenOption.APPEND);
	}
}
