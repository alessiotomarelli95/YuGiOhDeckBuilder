package yugi;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class CreateDeckCmd implements Command {

	private final Scanner scanner;
	private final Connection connection;

	public CreateDeckCmd(Scanner scanner, Connection connection) {
		this.scanner = scanner;
		this.connection = connection;
	}

	@Override
	public String getDescription() {
		return "Crea un Deck.";
	}

	@Override
	public void execute() throws SQLException {
		
		System.out.print("Inserisci il nome del Deck che vuoi creare:");
		String deckName = scanner.nextLine();

		if (!deckName.matches("^[a-zA-Z_][a-zA-Z0-9_ ]*$")) {
			System.out.println("Il nome inserito non è valido.");
			return;
		}

		String sql = "INSERT INTO decks (nome)" + "\nVALUES ('" + deckName + "')";

		Statement stmt = connection.createStatement();

		stmt.execute(sql);
		System.out.println("Il Deck " + deckName + " è stato generato con successo!\n");
	}

}
