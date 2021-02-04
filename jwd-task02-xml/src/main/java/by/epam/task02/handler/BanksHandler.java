package by.epam.task02.handler;

import java.time.LocalDateTime;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import by.epam.task02.entity.Bank;
import by.epam.task02.entity.Deposit;
import by.epam.task02.entity.IrrevocableDeposit;
import by.epam.task02.entity.RevocableDeposit;

public class BanksHandler extends DefaultHandler {
	public static Logger logger = LogManager.getLogger();
	private Set<Bank> banks;
	private Bank currentBank;
	private Deposit currentDeposit;
	private BanksXmlTag currentXmlTag;
	private EnumSet<BanksXmlTag> withText;
	private static final String ELEMENT_BANK = "bank";
	private static final String ELEMENT_REVOCABLE_DEPOSIT = "revocable-deposit";
	private static final String ELEMENT_IRREVOCABLE_DEPOSIT = "irrevocable-deposit";
	public final static String DEFAULT_ATTRIBUTE_COUNTRY = "Belarus";
	public final static String HYPHEN = "-";
	public final static String UNDERSCORE = "_";

	public BanksHandler() {
		banks = new HashSet<Bank>();
		withText = EnumSet.range(BanksXmlTag.DEPOSITOR, BanksXmlTag.CLOSING_DATA);
	}

	public Set<Bank> getBanks() {
		return banks;
	}

	public void startElement(String uri, String localName, String qName, Attributes attrs) {
		switch (qName) {
		case ELEMENT_BANK:
			currentBank = buildBank(attrs);
			break;
		case ELEMENT_REVOCABLE_DEPOSIT:
			currentDeposit = new RevocableDeposit();
			break;
		case ELEMENT_IRREVOCABLE_DEPOSIT:
			currentDeposit = new IrrevocableDeposit();
		default:
			BanksXmlTag temp = BanksXmlTag.valueOf(qName.replace(HYPHEN, UNDERSCORE).toUpperCase());
			if (withText.contains(temp)) {
				currentXmlTag = temp;
			}
		}
	}

	public void endElement(String uri, String localName, String qName) {
		if (ELEMENT_BANK.equals(qName)) {
			banks.add(currentBank);
		} else if (ELEMENT_IRREVOCABLE_DEPOSIT.equals(qName) || ELEMENT_REVOCABLE_DEPOSIT.equals(qName)) {
			currentBank.addDeposit(currentDeposit);
		}
	}

	public void characters(char[] ch, int start, int length) {
		String data = new String(ch, start, length).strip();
		if (currentXmlTag != null) {
			switch (currentXmlTag) {
			case DEPOSITOR:
				currentDeposit.setDepositor(data);
				break;
			case ACCOUNT_ID:
				currentDeposit.setAccountID(data);
				break;
			case OPENING_DATA:
				LocalDateTime openingData = LocalDateTime.parse(data);
				currentDeposit.setOpeningData(openingData);
				break;
			case CURRENCY:
				currentDeposit.setCurrency(data);
				break;
			case AMOUNT:
				Double amount = Double.parseDouble(data);
				currentDeposit.setAmount(amount);
				break;
			case PROFITABILITY:
				Double profitability = Double.parseDouble(data);
				currentDeposit.setProfitability(profitability);
				break;
			case TERM_DEPOSIT:
				Integer termDeposit = Integer.parseInt(data);
				currentDeposit.setTermDeposit(termDeposit);
				break;
			case CLOSING_DATA:
				IrrevocableDeposit irrevocableDeposit = (IrrevocableDeposit) currentDeposit;
				LocalDateTime closingData = LocalDateTime.parse(data);
				irrevocableDeposit.setClosingData(closingData);
				break;
			default:
				logger.log(Level.ERROR, "enum constant not present");
			}
		}
		currentXmlTag = null;
	}

	private Bank buildBank(Attributes attrs) {
		Bank bank = new Bank();
		if (attrs.getLength()== 1) {
			bank.setName(attrs.getValue(0));
			bank.setCountry(DEFAULT_ATTRIBUTE_COUNTRY);
		} else {
			int numberAttribute = 0;
			while (numberAttribute < attrs.getLength()) {
				switch (attrs.getLocalName(numberAttribute)) {
				case "country":
					bank.setCountry(attrs.getValue(numberAttribute));
					break;
				case "name":
					bank.setName(attrs.getValue(numberAttribute));
					break;
				}
				numberAttribute++;
			}
		}
		return bank;
	}
}
