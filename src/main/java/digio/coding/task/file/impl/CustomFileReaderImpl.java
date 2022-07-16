package digio.coding.task.file.impl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import digio.coding.task.file.CustomFileReader;

/**
 * Implementation of {@link CustomFileReader}.
 * 
 * @author ivanudakara
 */
public class CustomFileReaderImpl implements CustomFileReader {
	private static final Logger LOG = Logger.getLogger(CustomFileReaderImpl.class.getName());

	@Override
	public List<String> readLogLines(String filePath) {
		try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
			return reader.lines().collect(Collectors.toList());
		} catch (IOException e) {
			LOG.log(Level.SEVERE, "IOException occured while reading the log file: {0}", filePath);
			return new ArrayList<>();
		}
	}
}
