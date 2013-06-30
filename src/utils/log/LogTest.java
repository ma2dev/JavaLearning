package utils.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogTest {

	private static Logger log = LoggerFactory.getLogger(LogTest.class.getName());

	public static void main(String[] args) {

		String s = null;

		log.info("Starting my application...");

		if (args != null && args.length > 0) {
			s = args[0];
		}

		String k = null;
		try {

			log.info("Parsing integer: {}", s);

			s = null;
			Integer.parseInt(s);

			log.info("success: parse integer");

		} catch (NumberFormatException nfe) {
			log.error("Exception caught [{}]: ", k, nfe);
		}
	}
}