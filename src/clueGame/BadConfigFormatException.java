// Authors: Matthew McCormick and Zhen Liu

package clueGame;

public class BadConfigFormatException extends Exception {
	public BadConfigFormatException() {
		super("Bad format is detected.");
	
	}
	public BadConfigFormatException(String error) {
		super(error);
	}
}
