package roteirizador.ui;

import java.util.Scanner;

public class ScannerInput {
	
	private static ScannerInput instance;
	
	private Scanner scanner;
	
	private ScannerInput() {
		scanner = new Scanner(System.in);
	};
	
	public static ScannerInput getInstance() {
		if (instance == null) {
			instance = new ScannerInput();
		}
		return instance;
	}

	public int readInt() {
		return scanner.nextInt();
	}

	public String readString() {
		return scanner.next();
	}

	public double readDouble() {
		return scanner.nextDouble();
	}

}
