package by.epam.task02.exception;

public class BanksXmlException extends Exception {
	private static final long serialVersionUID = 1L;

	public BanksXmlException() {	
	}
	
	public BanksXmlException (String message) {
		super(message);
	}
	
	public BanksXmlException (String message, Throwable cause) {
		super(message, cause);
	}
	
	public BanksXmlException (Throwable cause) {
		super(cause);
	}
}
