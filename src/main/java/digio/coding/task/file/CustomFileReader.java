/**
 * 
 */
package digio.coding.task.file;

import java.util.List;

/**
 * For file reading.
 * 
 * @author ivanudakara
 */
public interface CustomFileReader {
	/**
	 * Reads the lines from a given file and returns as a list.
	 * 
	 * @return a {@code List<String>}
	 */
	public List<String> readLogLines(String filePath);
}
