// Authors: Matthew McCormick and Zhen Liu

package clueGame;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class BadConfigFormatException extends Exception {
	public BadConfigFormatException() {
		super("Bad format is detected.");
	
	}
	public BadConfigFormatException(String error) {
		super(error);
		File file = new File("logfile.txt");
	    try {
			PrintWriter pw = new PrintWriter(file);
			pw.println("Bad format error");
			pw.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		}
	}
	
}
