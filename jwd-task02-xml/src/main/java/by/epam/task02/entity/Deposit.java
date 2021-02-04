package by.epam.task02.entity;

import java.time.LocalDateTime;

public abstract class Deposit {

	private String depositor;
	private String accountID;
	private LocalDateTime openingData;
	private String currency;
	private double amount;
	private double profitability;
	private int termDeposit;
	
	public Deposit() {
		
	}
	
	public Deposit(String depositor, String accountID, LocalDateTime openingData, String currency, double amount,
			double profitability, int termDeposit) {
		this.depositor = depositor;
		this.accountID = accountID;
		this.openingData = openingData;
		this.currency = currency;
		this.amount = amount;
		this.profitability = profitability;
		this.termDeposit = termDeposit;
	}

	public String getDepositor() {
		return depositor;
	}

	public void setDepositor(String depositor) {
		this.depositor = depositor;
	}

	public String getAccountID() {
		return accountID;
	}

	public void setAccountID(String accountID) {
		this.accountID = accountID;
	}

	public LocalDateTime getOpeningData() {
		return openingData;
	}

	public void setOpeningData(LocalDateTime openingData) {
		this.openingData = openingData;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public double getProfitability() {
		return profitability;
	}

	public void setProfitability(double profitability) {
		this.profitability = profitability;
	}

	public int getTermDeposit() {
		return termDeposit;
	}

	public void setTermDeposit(int termDeposit) {
		this.termDeposit = termDeposit;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accountID == null) ? 0 : accountID.hashCode());
		long temp;
		temp = Double.doubleToLongBits(amount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((currency == null) ? 0 : currency.hashCode());
		result = prime * result + ((depositor == null) ? 0 : depositor.hashCode());
		result = prime * result + ((openingData == null) ? 0 : openingData.hashCode());
		temp = Double.doubleToLongBits(profitability);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + termDeposit;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Deposit other = (Deposit) obj;
		if (accountID == null) {
			if (other.accountID != null)
				return false;
		} else if (!accountID.equals(other.accountID))
			return false;
		if (Double.doubleToLongBits(amount) != Double.doubleToLongBits(other.amount))
			return false;
		if (currency == null) {
			if (other.currency != null)
				return false;
		} else if (!currency.equals(other.currency))
			return false;
		if (depositor == null) {
			if (other.depositor != null)
				return false;
		} else if (!depositor.equals(other.depositor))
			return false;
		if (openingData == null) {
			if (other.openingData != null)
				return false;
		} else if (!openingData.equals(other.openingData))
			return false;
		if (Double.doubleToLongBits(profitability) != Double.doubleToLongBits(other.profitability))
			return false;
		if (termDeposit != other.termDeposit)
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(getClass().getName());
		builder.append(" [depositor=");
		builder.append(depositor);
		builder.append(", accountID=");
		builder.append(accountID);
		builder.append(", openingData=");
		builder.append(openingData);
		builder.append(", currency=");
		builder.append(currency);
		builder.append(", amount=");
		builder.append(amount);
		builder.append(", profitability=");
		builder.append(profitability);
		builder.append(", termDeposit=");
		builder.append(termDeposit);
		builder.append("]");
		return builder.toString();
	}
}
