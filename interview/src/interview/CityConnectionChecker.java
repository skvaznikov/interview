package interview;

import java.io.Console;

/**
	 * (C) 2019
	 * @author eskvaznikov
	 *
	 * 
	 */

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author eskvaznikov Check the connection between 2 cities based on the list
 *         from the input file.
 */
public class CityConnectionChecker {
	private static final String CUSTOMER_MESSAGE = "Please inpit fromCity, toCity and path to mapFile";
	private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private static List<String> cityList = new ArrayList<>();
	private static String fromCity = null;
	private static String toCity = null;
	private static BlockingQueue<String> connectedNodes = new LinkedBlockingQueue<>();
	private static Set<String> checked = new HashSet<>();
	private static String result = "";

	public static void main(String args[]) {

		LOGGER.setLevel(Level.SEVERE);

		String fileName = null;

//   In case we will set the name not from command line - why?
//		try {
//			fileName = readProperty("file.name");
//		} catch (IOException e1) {
//			LOGGER.severe("Can't read property from file");
//		}

		if (args.length == 3) {
			fromCity = args[0].replaceAll("\"", "");
			toCity = args[1].replaceAll("\"", "");
			if (fromCity.equals(toCity)) {
				System.out.println("CONNECTED");
				return;
			}
			fileName = args[2].replaceAll("\"", "");
		} else {
			LOGGER.severe("Wrong argument list");
			Console c = System.console();
			if (c == null) {
				System.err.println(CUSTOMER_MESSAGE);
				return;
			} else {
				c.writer().print(CUSTOMER_MESSAGE);
			}

		}
		try (Stream<String> stream = Files.lines(Paths.get(fileName))) {

			stream.forEach(line -> {
				cityList.add(line);
			});

		} catch (IOException e) {
			LOGGER.severe("Can't read the input file");
		}
		findConnection(fromCity, toCity);
		System.out.println(result);

	}

	/**
	 * Recursive function to determine whether 2 cities connected or not.
	 * 
	 * @param startCity start city
	 * @param endCity   end city
	 */
	private static void findConnection(String startCity, String endCity) {

		List<String> listOfFromPairs = cityList.stream().filter(pair -> pair.contains(startCity))
				.collect(Collectors.toList());

		if (listOfFromPairs.isEmpty())
			result = "Can't determine connection. You city(s) isn't in a list";

		listOfFromPairs.stream().forEach(str -> {
			StringTokenizer tok = new StringTokenizer(str, ",");
			String name = tok.nextToken();
			if (!connectedNodes.contains(name) && !checked.contains(name) && !name.equals(fromCity)) {
				if (name.equals(startCity)) {

					connectedNodes.add(tok.nextToken().trim());
				} else {
					connectedNodes.add(name);
				}
			}
		});
		if (connectedNodes.contains(endCity)) {
			result = "CONNECTED";
		} else {
			try {
				if (connectedNodes.isEmpty()) {
					result = "NOT CONNECTED";
				} else {
					String str = connectedNodes.take();
					checked.add(str);
					findConnection(str, endCity);
				}
			} catch (InterruptedException e) {
				LOGGER.severe(e.getMessage());
				Thread.currentThread().interrupt();
			}
		}

	}

	// Read property in case we need path dynamically
//	private static String readProperty(String propertyName) throws IOException {
//		try (InputStream input = new FileInputStream("config.properties")) {
//			Properties prop = new Properties();
//			prop.load(input);
//			return prop.getProperty(propertyName);
//		}
//	}

}
