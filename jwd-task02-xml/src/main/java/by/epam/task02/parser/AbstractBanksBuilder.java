package by.epam.task02.parser;

import java.util.HashSet;
import java.util.Set;

import by.epam.task02.entity.Bank;
import by.epam.task02.exception.BanksXmlException;

public abstract class AbstractBanksBuilder {
	protected Set<Bank> banks;

	public AbstractBanksBuilder() {
		banks = new HashSet<Bank>();
	}

	public AbstractBanksBuilder(Set<Bank> banks) {
		this.banks = banks;
	}

	public Set<Bank> getBanks() {
		return banks;
	}

	public abstract void buildSetBanks(String filename) throws BanksXmlException;
}
