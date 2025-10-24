package yugi;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class DeckIDfromName {

	private final Scanner scanner;
	private final Connection connection;

	public DeckIDfromName(Scanner scanner, Connection connection) {
		this.scanner = scanner;
		this.connection = connection;
	}

	public Integer deckIDfromName() throws SQLException {

		System.out.print("Inserisci il nome del Deck: ");
		String deckName = scanner.nextLine();

		if (!deckName.matches("^[a-zA-Z_][a-zA-Z0-9_ ]*$")) {
			System.out.println("Il nome inserito non è valido.");
			return null;
		}

		String sql = "SELECT id FROM decks WHERE nome = ?";

		PreparedStatement pstmt = connection.prepareStatement(sql);

		pstmt.setString(1, deckName);
		ResultSet rs = pstmt.executeQuery();

		if (rs.next()) {
			int deckId = rs.getInt("id");
			return deckId;
			// System.out.println("ID del Deck '" + deckName + "' è: " + deckId);
		} else {
			System.out.println("Nessun deck trovato con il nome '" + deckName + "'.");
			return null;
		}
	}
}
