package yugi;

import java.util.Scanner;

public class ScannerDI {
	
	private final Scanner scanner;
	
	public ScannerDI() {
		this.scanner = new Scanner(System.in);
	}
	
	public Scanner getScanner() {
		return scanner;
	}

}
