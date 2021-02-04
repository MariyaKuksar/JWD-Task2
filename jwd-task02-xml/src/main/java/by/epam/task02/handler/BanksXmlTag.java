package by.epam.task02.handler;

public enum BanksXmlTag {
	BANKS("banks"), BANK("bank"), REVOCABLE_DEPOSIT("revocable-deposit"), IRREVOCABLE_DEPOSIT("irrevocable-deposit"),
	DEPOSITOR("depositor"), ACCOUNT_ID("account-id"), OPENING_DATA("opening-data"), CURRENCY("currency"),
	AMOUNT("amount"), PROFITABILITY("profitability"), TERM_DEPOSIT("term-deposit"), CLOSING_DATA("closing-data");

	private String value;

	BanksXmlTag(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
