package digio.coding.task;

import static digio.coding.task.util.Const.REGEX_IP;
import static digio.coding.task.util.Const.REGEX_URL;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import digio.coding.task.file.CustomFileReader;
import digio.coding.task.util.Const;
import digio.coding.task.util.UtilityService;

/**
 * Solution implementation.
 * 
 * @author ivanudakara
 */
public class Solution {
	private static final Logger LOG = Logger.getLogger(Solution.class.getName());

	static {
		try (InputStream in = UtilityService.class.getClassLoader().getResourceAsStream("application.properties")) {
			Const.prop.load(in);
		} catch (IOException e) {
			LOG.log(Level.SEVERE, "Error while reading application.properties");
		}
	}

	private final CustomFileReader fileReader;
	private Map<String, Integer> ipAddressMap = new HashMap<>();
	private Map<String, Integer> urlMap = new HashMap<>();

	public Solution(CustomFileReader fileReader) {
		this.fileReader = fileReader;
	}

	public void printResult() {
		List<String> logLines = fileReader.readLogLines(Const.prop.getProperty("log.file.path"));

		if (logLines.isEmpty()) {
			LOG.log(Level.SEVERE, "No lines found in the log file!");
			return;
		} else {
			extractData(logLines);
		}
		LOG.log(Level.INFO, "Number of unique IP addresses: {0}", ipAddressMap.size());
		LOG.log(Level.INFO, "The top 3 most visited URLs: {0}",
				getTopResults(urlMap, Integer.parseInt(Const.prop.getProperty("top.results.amount"))));
		LOG.log(Level.INFO, "The top 3 most active IP addresses: {0}",
				getTopResults(ipAddressMap, Integer.parseInt(Const.prop.getProperty("top.results.amount"))));
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
		LOG.log(Level.FINER, "End of Loop");
	}

	private void extractIpInfo(Matcher ipMatcher) {
		String ipAddr;
		if (ipMatcher.find()) {
			ipAddr = ipMatcher.group(0).trim();
			LOG.log(Level.FINER, "{0}", ipAddr);
			if (ipAddressMap.containsKey(ipAddr)) {
				ipAddressMap.put(ipAddr, ipAddressMap.get(ipAddr) + 1);
			} else {
				ipAddressMap.put(ipAddr, 1);
			}
		} else {
			LOG.log(Level.SEVERE, "No matching value found for IP address!");
		}
	}

	private void extractUrlInfo(Matcher urlMatcher) {
		String url;
		if (urlMatcher.find()) {
			url = urlMatcher.group(1).trim();
			LOG.log(Level.FINER, "{0}", url);
			if (urlMap.containsKey(url)) {
				urlMap.put(url, urlMap.get(url) + 1);
			} else {
				urlMap.put(url, 1);
			}
		} else {
			LOG.log(Level.SEVERE, "No matching value found for URL address!");
		}
	}

	private String getTopResults(Map<String, Integer> map, int numOfResults) {
		List<Map.Entry<String, Integer>> sortedMap = UtilityService.sortMap(map);
		StringBuilder places = new StringBuilder();
		if(numOfResults > map.size()) {
			LOG.log(Level.SEVERE, "No enough matching values found!");
			return "";
		}

		for (int i = 0; i < numOfResults; i++) {
			places.append(" ").append(i + 1).append(") ").append(sortedMap.get(i).getKey());
		}
		LOG.log(Level.FINE, "Places str: {0}", places);
		return places.toString();
	}
}
