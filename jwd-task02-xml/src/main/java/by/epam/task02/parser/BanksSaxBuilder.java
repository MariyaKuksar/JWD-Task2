package by.epam.task02.parser;

import java.io.IOException;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import by.epam.task02.entity.Bank;
import by.epam.task02.exception.BanksXmlException;
import by.epam.task02.handler.BankErrorHandler;
import by.epam.task02.handler.BanksHandler;

public class BanksSaxBuilder extends AbstractBanksBuilder {
	public static Logger logger = LogManager.getLogger();
	private BanksHandler handler = new BanksHandler();
	private XMLReader reader;

	public BanksSaxBuilder() {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		try {
			SAXParser saxParser = factory.newSAXParser();
			reader = saxParser.getXMLReader();
		} catch (ParserConfigurationException | SAXException e) {
			logger.log(Level.ERROR, " parser configuration error");
		}
		reader.setErrorHandler(new BankErrorHandler());
		reader.setContentHandler(handler);
	}

	public BanksSaxBuilder(Set<Bank> banks) {
		super(banks);
		SAXParserFactory factory = SAXParserFactory.newInstance();
		try {
			SAXParser saxParser = factory.newSAXParser();
			reader = saxParser.getXMLReader();
		} catch (ParserConfigurationException | SAXException e) {
			logger.log(Level.ERROR, " parser configuration error");
		}
		reader.setErrorHandler(new BankErrorHandler());
		reader.setContentHandler(handler);
	}

	@Override
	public void buildSetBanks(String filename) throws BanksXmlException {
		try {
			reader.parse(filename);
		} catch (IOException | SAXException e) {
			throw new BanksXmlException("parsing error" + e);
		}
		banks = handler.getBanks();
		logger.log(Level.INFO, "parsing result: " + getBanks());
	}

}
