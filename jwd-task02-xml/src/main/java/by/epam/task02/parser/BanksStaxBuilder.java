package by.epam.task02.parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Set;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.task02.entity.Bank;
import by.epam.task02.entity.Deposit;
import by.epam.task02.entity.IrrevocableDeposit;
import by.epam.task02.entity.RevocableDeposit;
import by.epam.task02.exception.BanksXmlException;
import by.epam.task02.handler.BanksXmlTag;

public class BanksStaxBuilder extends AbstractBanksBuilder {
	public static Logger logger = LogManager.getLogger();
	private XMLInputFactory inputFactory;
	public final static String ATTRIBUTE_NAME = "name";
	public final static String ATTRIBUTE_COUNTRY = "country";
	public final static String DEFAULT_ATTRIBUTE_COUNTRY = "Belarus";
	public final static String HYPHEN = "-";
	public final static String UNDERSCORE = "_";

	public BanksStaxBuilder() {
		inputFactory = XMLInputFactory.newInstance();
	}

	public BanksStaxBuilder(Set<Bank> banks) {
		super(banks);
		inputFactory = XMLInputFactory.newInstance();
	}

	@Override
	public void buildSetBanks(String filename) throws BanksXmlException {
		XMLStreamReader reader;
		String name;
		try (FileInputStream inputStream = new FileInputStream(new File(filename))) {
			reader = inputFactory.createXMLStreamReader(inputStream);
			while (reader.hasNext()) {
				int type = reader.next();
				if (type == XMLStreamConstants.START_ELEMENT) {
					name = reader.getLocalName();
					if (name.equals(BanksXmlTag.BANK.getValue())) {
						Bank bank = buildBank(reader);
						banks.add(bank);
					}
				}
			}
			logger.log(Level.INFO, "parsing result: " + getBanks());
		} catch (XMLStreamException | FileNotFoundException e) {
			throw new BanksXmlException("parsing error" + e);
		} catch (IOException e) {
			throw new BanksXmlException(filename + "is not found" + e);
		}
	}

	private Bank buildBank(XMLStreamReader reader) throws XMLStreamException {
		Bank bank = new Bank();
		bank.setName(reader.getAttributeValue(null, ATTRIBUTE_NAME));
		bank.setCountry(reader.getAttributeValue(null, ATTRIBUTE_COUNTRY).isEmpty() ? DEFAULT_ATTRIBUTE_COUNTRY
				: reader.getAttributeValue(null, ATTRIBUTE_COUNTRY));
		String name;
		while (reader.hasNext()) {
			int type = reader.next();
			switch (type) {
			case XMLStreamConstants.START_ELEMENT:
				name = reader.getLocalName();
				if (name.equals(BanksXmlTag.REVOCABLE_DEPOSIT.getValue())
						|| name.equals(BanksXmlTag.IRREVOCABLE_DEPOSIT.getValue())) {
					Deposit deposit = buildDeposit(reader, name);
					bank.addDeposit(deposit);
				}
				break;
			case XMLStreamConstants.END_ELEMENT:
				name = reader.getLocalName();
				if (name.equals(BanksXmlTag.BANK.getValue())) {
					return bank;
				}
				break;
			}
		}
		throw new XMLStreamException("Unknown element in tag <bank>");
	}

	private Deposit buildDeposit(XMLStreamReader reader, String tagName) throws XMLStreamException {
		Deposit deposit = null;
		switch (tagName) {
		case "revocable-deposit":
			deposit = new RevocableDeposit();
			break;
		case "irrevocable-deposit":
			deposit = new IrrevocableDeposit();
			break;
		}
		int type;
		String name;
		while (reader.hasNext()) {
			type = reader.next();
			switch (type) {
			case XMLStreamConstants.START_ELEMENT:
				name = reader.getLocalName();
				switch (BanksXmlTag.valueOf(name.replace(HYPHEN, UNDERSCORE).toUpperCase())) {
				case DEPOSITOR:
					deposit.setDepositor(getXMLText(reader));
					break;
				case ACCOUNT_ID:
					deposit.setAccountID(getXMLText(reader));
					break;
				case OPENING_DATA:
					LocalDateTime openingData = LocalDateTime.parse(getXMLText(reader));
					deposit.setOpeningData(openingData);
					break;
				case CURRENCY:
					deposit.setCurrency(getXMLText(reader));
					break;
				case AMOUNT:
					Double amount = Double.parseDouble(getXMLText(reader));
					deposit.setAmount(amount);
					break;
				case PROFITABILITY:
					Double profitability = Double.parseDouble(getXMLText(reader));
					deposit.setProfitability(profitability);
					break;
				case TERM_DEPOSIT:
					Integer termDeposit = Integer.parseInt(getXMLText(reader));
					deposit.setTermDeposit(termDeposit);
					break;
				case CLOSING_DATA:
					IrrevocableDeposit irrevocableDeposit = (IrrevocableDeposit) deposit;
					LocalDateTime closingData = LocalDateTime.parse(getXMLText(reader));
					irrevocableDeposit.setClosingData(closingData);
					break;
				}
				break;
			case XMLStreamConstants.END_ELEMENT:
				name = reader.getLocalName();
				if (name.equals(BanksXmlTag.IRREVOCABLE_DEPOSIT.getValue())
						|| name.equals(BanksXmlTag.REVOCABLE_DEPOSIT.getValue())) {
					return deposit;
				}
				break;
			}
		}
		throw new XMLStreamException("Unknown element in tag " + tagName);
	}

	private String getXMLText(XMLStreamReader reader) throws XMLStreamException {
		String text = null;
		if (reader.hasNext()) {
			reader.next();
			text = reader.getText();
		}
		return text;
	}
}
