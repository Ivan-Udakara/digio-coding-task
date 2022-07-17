package digio.coding.task;

import static digio.coding.task.util.Const.REGEX_IP;
import static digio.coding.task.util.Const.REGEX_URL;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import digio.coding.task.file.CustomFileReader;
import digio.coding.task.util.UtilityService;

/**
 * Solution implementation.
 * 
 * @author ivanudakara
 */
public class Solution {
	private static final Logger LOG = Logger.getLogger(Solution.class.getName());
	// read file
	// iterate line by line
	// fill 2 maps
		// 1. IP, count
		// 2. URL, count
	// return
		// number of unique IPs -> map1.size
		// top 3 most visited URLs -> map2 entries with highest count
		// top 3 most active IPs -> map1 entries with highest count
	
	private final CustomFileReader fileReader;
	private Map<String, Integer> ipAddressMap = new HashMap<>();
	private Map<String, Integer> urlMap = new HashMap<>();

	private static final String FILE_PATH = "/Volumes/Programming/codes/interview-related/digio-coding-task/programming-task-example-data.log";
	
	public Solution(CustomFileReader fileReader) {
		this.fileReader = fileReader;
	}

	public void printResult() {
		List<String> logLines = fileReader.readLogLines(FILE_PATH);

		if (logLines.isEmpty()) {
			LOG.log(Level.SEVERE, "No lines found in the log file!");
		} else {
			extractData(logLines);
		}
		LOG.log(Level.INFO, "Number of unique IP addresses: {0}", ipAddressMap.size());
		LOG.log(Level.INFO, "The top 3 most visited URLs: 1) {0}, 2) {1}, 3) {2}", getTopResults(urlMap, 3));
		LOG.log(Level.INFO, "The top 3 most active IP addresses: 1) {0}, 2) {1}, 3) {2}", getTopResults(ipAddressMap, 3));
	}

	private void extractData(List<String> logLines) {
		Pattern ipPattern = Pattern.compile(REGEX_IP);
		Matcher ipMatcher;
		Pattern urlPattern = Pattern.compile(REGEX_URL);
		Matcher urlMatcher;
		
		for (String line : logLines) {
			ipMatcher = ipPattern.matcher(line);
			urlMatcher = urlPattern.matcher(line);
			
			extractIpInfo(ipMatcher);
			extractUrlInfo(urlMatcher);
		}
		LOG.log(Level.INFO, "End of Loop");
	}
	
	private void extractIpInfo(Matcher ipMatcher) {
		String ipAddr;
		if (ipMatcher.find()) {
			ipAddr = ipMatcher.group(0).trim();
			LOG.log(Level.INFO, "{0}", ipAddr);
			if(ipAddressMap.containsKey(ipAddr)) {
				ipAddressMap.put(ipAddr, ipAddressMap.get(ipAddr) + 1);
			} else {
				ipAddressMap.put(ipAddr, 1);
			}
		}
	}
	
	private void extractUrlInfo(Matcher urlMatcher) {
		String url;
		if (urlMatcher.find()) {
			url = urlMatcher.group(1).trim();
			LOG.log(Level.INFO, "{0}", url);
			if(urlMap.containsKey(url)) {
				urlMap.put(url, urlMap.get(url) + 1);
			} else {
				urlMap.put(url, 1);
			}
		}
	}
	
	private Object[] getTopResults(Map<String, Integer> map, int numOfResults) {
		Object[] result = new Object[numOfResults];
		List<Map.Entry<String, Integer>> sortedMap = UtilityService.sortMap(map);

		for (int i = 0; i < numOfResults; i++) {
			result[i] = sortedMap.get(i).getKey();
		}
		return result;
	}
}
