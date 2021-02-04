package by.epam.task02.entity;

import java.time.LocalDateTime;

public class RevocableDeposit extends Deposit {

	public RevocableDeposit() {

	}

	public RevocableDeposit(String depositor, String accountID, LocalDateTime openingData, String currency,
			double amount, double profitability, int termDeposit) {
		super(depositor, accountID, openingData, currency, amount, profitability, termDeposit);
	}

}
