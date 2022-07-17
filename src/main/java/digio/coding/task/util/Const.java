package digio.coding.task.util;

/**
 * Utility class to keep constant values.
 * 
 * @author ivanudakara
 */
public class Const {
	private Const() {
		// To avoid instantiation.
	}

	public static final String REGEX_IP = "([0-9]{0,3}\\.[0-9]{0,3}\\.[0-9]{0,3}\\.[0-9]{0,3})(\\s)";
	public static final String REGEX_URL = "\\s\\\"[A-Z]+\\s([(http\\:)\\/a-z0-9-_\\.]*)\\s";
}
