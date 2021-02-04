package by.epam.task02.entity;

import java.time.LocalDateTime;

public class IrrevocableDeposit extends Deposit {

	private LocalDateTime closingData;

	public IrrevocableDeposit() {

	}

	public IrrevocableDeposit(String depositor, String accountID, LocalDateTime openingData, String currency,
			double amount, double profitability, int termDeposit) {
		super(depositor, accountID, openingData, currency, amount, profitability, termDeposit);
	}

	public LocalDateTime getClosingData() {
		return closingData;
	}

	public void setClosingData(LocalDateTime closingData) {
		this.closingData = closingData;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((closingData == null) ? 0 : closingData.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		IrrevocableDeposit other = (IrrevocableDeposit) obj;
		if (closingData == null) {
			if (other.closingData != null)
				return false;
		} else if (!closingData.equals(other.closingData))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(super.toString());
		builder.append("[closingData=");
		builder.append(closingData);
		builder.append("]");
		return builder.toString();
	}

}
