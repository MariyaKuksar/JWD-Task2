package by.epam.task02.entity;

import java.util.ArrayList;
import java.util.List;

public class Bank {

	private String name;
	private String country;
	private List<Deposit> deposits = new ArrayList<Deposit>();

	public Bank() {

	}

	public Bank(String name, String country, List<Deposit> deposits) {
		this.name = name;
		this.country = country;
		this.deposits = deposits;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public List<Deposit> getDeposits() {
		return deposits;
	}

	public void setDeposits(List<Deposit> deposits) {
		this.deposits = deposits;
	}

	public void addDeposit(Deposit deposit) {
		deposits.add(deposit);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((country == null) ? 0 : country.hashCode());
		result = prime * result + ((deposits == null) ? 0 : deposits.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Bank other = (Bank) obj;
		if (country == null) {
			if (other.country != null)
				return false;
		} else if (!country.equals(other.country))
			return false;
		if (deposits == null) {
			if (other.deposits != null)
				return false;
		} else if (!deposits.equals(other.deposits))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("Bank [name=");
	      sb.append(name).append(", country=").append(country);
	      sb.append(", deposits=").append(deposits).append("]");
	      return sb.toString();
	}

	
}
