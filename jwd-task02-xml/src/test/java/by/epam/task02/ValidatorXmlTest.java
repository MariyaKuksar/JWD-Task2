package by.epam.task02;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import by.epam.task02.exception.BanksXmlException;
import by.epam.task02.validator.ValidatorXml;

public class ValidatorXmlTest {

	ValidatorXml validator;

	@BeforeClass
	public void setUp() {
		validator = new ValidatorXml();
	}

	@Test
	public void validateXmlTest01() throws BanksXmlException {
		String filePath = "resources/data/banks.xml";
		String schemaPath = "resources/data/banks.xsd";
		Assert.assertTrue(validator.validateXml(filePath, schemaPath));
	}

	@Test
	public void validateXmlTest02() throws BanksXmlException {
		String filePath = "resources/data/forTest.xml";
		String schemaPath = "resources/data/banks.xsd";
		Assert.assertFalse(validator.validateXml(filePath, schemaPath));
	}

	@Test
	public void validateXmlTest03() throws BanksXmlException {
		String filePath = null;
		String schemaPath = null;
		Assert.assertFalse(validator.validateXml(filePath, schemaPath));
	}

	@AfterClass
	public void tierDown() {
		validator = null;
	}
}
