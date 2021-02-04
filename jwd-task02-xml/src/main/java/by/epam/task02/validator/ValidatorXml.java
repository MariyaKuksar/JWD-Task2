package by.epam.task02.validator;

import java.io.File;
import java.io.IOException;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;

import by.epam.task02.handler.BankErrorHandler;

public class ValidatorXml {
	public static Logger logger = LogManager.getLogger();

	public boolean validateXml(String filePath, String schemaPath) {
		boolean isValid = true;
		String language = XMLConstants.W3C_XML_SCHEMA_NS_URI;
		SchemaFactory factory = SchemaFactory.newInstance(language);
		try {
			File schemaLocation = new File(schemaPath);
			Schema schema = factory.newSchema(schemaLocation);
			Validator validator = schema.newValidator();
			Source source = new StreamSource(filePath);
			validator.setErrorHandler(new BankErrorHandler());
			validator.validate(source);
			logger.log(Level.INFO, filePath + " is valid");
		} catch (SAXException | IOException | NullPointerException e) {
			logger.log(Level.ERROR, filePath + " is not correct or valid");
			isValid = false;
		}
		return isValid;
	}
}