package digio.coding.task.util;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Class to keep utility services.
 * 
 * @author ivanudakara
 */
public class UtilityService {
	private UtilityService() {
		// to avoid instantiation.
	}
	
	public static List<Map.Entry<String, Integer>> sortMap(Map<String, Integer> unsortMap) {
		List<Map.Entry<String, Integer>> list = new LinkedList<>(unsortMap.entrySet());
		Collections.sort(list, (e1, e2) -> e2.getValue().compareTo(e1.getValue()));
		return list;
	}
}
