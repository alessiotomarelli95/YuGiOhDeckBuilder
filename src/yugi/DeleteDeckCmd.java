package yugi;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class DeleteDeckCmd implements Command {
	
	private final Scanner scanner;
	private final Connection connection;
	private final int deckId;

	public DeleteDeckCmd(Scanner scanner, Connection connection, int deckId) {
		this.scanner = scanner;
		this.connection = connection;
		this.deckId = deckId;
	}

	@Override
	public String getDescription() {
		return "Cancella il Deck.";
	}

	@Override
	public void execute() throws SQLException {
		System.out.print("Sei sicuro di voler eliminare il Deck? (y/n)");
		String delete = scanner.nextLine().trim().toLowerCase();

		String sql = "DELETE FROM decks WHERE id = '" + deckId + "'";

		if (delete.equals("y")) {
			Statement stmt = connection.createStatement();

			stmt.execute(sql);
			System.out.println("Il Deck è stato rimosso con successo!");

		} else if (delete.equals("n")) {
			System.out.println("Il Deck non è stato rimosso.");
		} else {
			System.out.println("Input non valido. Per favore inserisci 'y' o 'n'");
		}
	}

}
