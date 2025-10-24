package yugi;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class Menu {

	private final Scanner scanner;
	private final String header;
	private final ArrayList<Command> commands;

	public Menu(Scanner scanner, String header, ArrayList<Command> commands) {
		this.scanner = scanner;
		this.header = header;
		this.commands = commands;
	}

	public void menu() throws SQLException {

		System.out.println(header);
		System.out.println();

		boolean running = true;

		while (running) {
			
			int i = 0;
			
			for (i = 0; i < commands.size(); i++) {
				System.out.println((i + 1) + ". " + commands.get(i).getDescription());
			}

			System.out.println((i + 1) + ". Esci\n");

			System.out.print("Inserisci il numero corrispondente alla tua scelta: ");
			int input = scanner.nextInt() - 1;
			scanner.nextLine();

			try {

				if (input == commands.size()) {
					running = false;
				} else if (input >= 0 && input < commands.size()) {
					Command selectedCommand = commands.get(input);
					selectedCommand.execute();
				} else {
					System.out.println("Inserisci un numero valido tra 1 e " + (commands.size() + 1) + ".");
				}

			} catch (NumberFormatException e) {
				System.out.println("Input non valido. Inserisci un numero.");
			}

		}

	}

}
