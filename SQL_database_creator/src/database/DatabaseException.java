package database;

public class DatabaseException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public DatabaseException() {
		super("Unknown exception");
	}
	
	public DatabaseException(String message) {
		super(message);
	}
}
