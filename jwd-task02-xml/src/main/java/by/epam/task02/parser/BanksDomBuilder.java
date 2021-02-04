package by.epam.task02.parser;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import by.epam.task02.entity.Bank;
import by.epam.task02.entity.Deposit;
import by.epam.task02.entity.IrrevocableDeposit;
import by.epam.task02.entity.RevocableDeposit;
import by.epam.task02.exception.BanksXmlException;
import by.epam.task02.handler.BanksXmlTag;

public class BanksDomBuilder extends AbstractBanksBuilder {
	public static Logger logger = LogManager.getLogger();
	private DocumentBuilder docBuilder;
	public final static String ATTRIBUTE_NAME = "name";
	public final static String ATTRIBUTE_COUNTRY = "country";
	public final static String DEFAULT_ATTRIBUTE_COUNTRY = "Belarus";

	public BanksDomBuilder() {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			docBuilder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			logger.log(Level.ERROR, " parser configuration error");
		}
	}

	public BanksDomBuilder(Set<Bank> banks) {
		super(banks);
	}

	@Override
	public void buildSetBanks(String filename) throws BanksXmlException {
		Document doc;
		try {
			doc = docBuilder.parse(filename);
			Element root = doc.getDocumentElement();
			NodeList banksList = root.getElementsByTagName(BanksXmlTag.BANK.getValue());
			for (int i = 0; i < banksList.getLength(); i++) {
				Element bankElement = (Element) banksList.item(i);
				Bank bank = buildBank(bankElement);
				banks.add(bank);
			}
			logger.log(Level.INFO, "parsing result: " + getBanks());
		} catch (IOException | SAXException e) {
			throw new BanksXmlException("parsing error" + e);
		}
	}

	private Bank buildBank(Element bankElement) {
		Bank bank = new Bank();
		bank.setName(bankElement.getAttribute(ATTRIBUTE_NAME));
		bank.setCountry(bankElement.getAttribute(ATTRIBUTE_COUNTRY).isEmpty() ? DEFAULT_ATTRIBUTE_COUNTRY
				: bankElement.getAttribute(ATTRIBUTE_COUNTRY));
		NodeList revocableDepositsList = bankElement.getElementsByTagName(BanksXmlTag.REVOCABLE_DEPOSIT.getValue());
		for (int i = 0; i < revocableDepositsList.getLength(); i++) {
			Element revocableDepositElement = (Element) revocableDepositsList.item(i);
			Deposit revocableDeposit = buildDeposit(revocableDepositElement, BanksXmlTag.REVOCABLE_DEPOSIT.getValue());
			bank.addDeposit(revocableDeposit);
		}
		NodeList irrevocableDepositList = bankElement.getElementsByTagName(BanksXmlTag.IRREVOCABLE_DEPOSIT.getValue());
		for (int i = 0; i < irrevocableDepositList.getLength(); i++) {
			Element irrevocableDepositElement = (Element) irrevocableDepositList.item(i);
			Deposit irrevocableDeposit = buildDeposit(irrevocableDepositElement,
					BanksXmlTag.IRREVOCABLE_DEPOSIT.getValue());
			bank.addDeposit(irrevocableDeposit);
		}
		return bank;
	}

	private Deposit buildDeposit(Element depositElement, String tagName) {
		Deposit deposit = null;
		switch (tagName) {
		case "revocable-deposit":
			deposit = new RevocableDeposit();
			break;
		case "irrevocable-deposit":
			deposit = new IrrevocableDeposit();
			break;
		}
		deposit.setDepositor(getElementTextContent(depositElement, BanksXmlTag.DEPOSITOR.getValue()));
		deposit.setAccountID(getElementTextContent(depositElement, BanksXmlTag.ACCOUNT_ID.getValue()));
		LocalDateTime openingData = LocalDateTime
				.parse(getElementTextContent(depositElement, BanksXmlTag.OPENING_DATA.getValue()));
		deposit.setOpeningData(openingData);
		deposit.setCurrency(getElementTextContent(depositElement, BanksXmlTag.CURRENCY.getValue()));
		Double amount = Double.parseDouble(getElementTextContent(depositElement, BanksXmlTag.AMOUNT.getValue()));
		deposit.setAmount(amount);
		Double profitability = Double
				.parseDouble(getElementTextContent(depositElement, BanksXmlTag.PROFITABILITY.getValue()));
		deposit.setProfitability(profitability);
		Integer termDeposit = Integer
				.parseInt(getElementTextContent(depositElement, BanksXmlTag.TERM_DEPOSIT.getValue()));
		deposit.setTermDeposit(termDeposit);
		if (deposit instanceof IrrevocableDeposit) {
			IrrevocableDeposit irrevocableDeposit = (IrrevocableDeposit) deposit;
			LocalDateTime closingData = LocalDateTime
					.parse(getElementTextContent(depositElement, BanksXmlTag.CLOSING_DATA.getValue()));
			irrevocableDeposit.setClosingData(closingData);
		}
		return deposit;
	}

	private static String getElementTextContent(Element element, String elementName) {
		NodeList nList = element.getElementsByTagName(elementName);
		Node node = nList.item(0);
		String text = node.getTextContent();
		return text;
	}
}
