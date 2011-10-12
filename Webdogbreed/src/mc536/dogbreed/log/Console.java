package mc536.dogbreed.log;

/**
 * Helper class to log messages.
 */
public class Console {
	
	/**
	 * Constructor.
	 * Prevents class instantiation.
	 */
	protected Console() { }
	
	/**
	 * Writes an exception to the console.
	 * 
	 * @param e Exception to be written.
	 */
	public static void log(Exception e) {
		System.out.println(e.getMessage());
		e.printStackTrace(System.out);
	}
	
	/**
	 * Writes a message to the console.
	 * 
	 * @param message Message to be written.
	 */
	public static void log(String message) {
		System.out.println(message);
	}

}
