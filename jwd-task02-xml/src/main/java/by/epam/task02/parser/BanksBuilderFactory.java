package by.epam.task02.parser;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.task02.exception.BanksXmlException;

public class BanksBuilderFactory {
	public static Logger logger = LogManager.getLogger();

	private BanksBuilderFactory() {

	}

	public static AbstractBanksBuilder createBanksBuilder(String typeParser) throws BanksXmlException {
		AbstractBanksBuilder builder = null;
		switch (typeParser.toUpperCase()) {
		case "DOM":
			builder = new BanksDomBuilder();
			break;
		case "STAX":
			builder = new BanksStaxBuilder();
			break;
		case "SAX":
			builder = new BanksSaxBuilder();
			break;
		default:
			throw new BanksXmlException("parser " + typeParser + "is not found");
		}
		logger.log(Level.INFO, builder.getClass().getName() + " will be used");
		return builder;
	}
}
