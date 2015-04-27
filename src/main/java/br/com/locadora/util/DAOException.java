package br.com.locadora.util;

public class DAOException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8319541822987395987L;

	public DAOException() {
		
	}

	public DAOException(String message) {
		super(message);
	}

	public DAOException(Throwable cause) {
		super(cause);
	}

	public DAOException(String message, Throwable cause) {
		super(message, cause);
	}

	public DAOException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
