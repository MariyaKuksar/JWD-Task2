package by.epam.task02.handler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class BankErrorHandler implements ErrorHandler {
	public static Logger logger = LogManager.getLogger();

	@Override
	public void warning(SAXParseException e) throws SAXException {
		logger.warn(getLineColumnNumber(e) + "-" + e.getMessage());
		throw new SAXException();
	}

	@Override
	public void error(SAXParseException e) throws SAXException {
		logger.error(getLineColumnNumber(e) + "-" + e.getMessage());
		throw new SAXException();
	}

	@Override
	public void fatalError(SAXParseException e) throws SAXException {
		logger.fatal(getLineColumnNumber(e) + " - " + e.getMessage());
		throw new SAXException();
	}

	private String getLineColumnNumber(SAXParseException e) {
		return e.getLineNumber() + " : " + e.getColumnNumber();
	}

}
