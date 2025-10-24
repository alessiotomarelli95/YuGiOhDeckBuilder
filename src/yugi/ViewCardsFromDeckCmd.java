package yugi;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class ViewCardsFromDeckCmd implements Command {

	private final Scanner scanner;
	private final Connection connection;

	public ViewCardsFromDeckCmd(Scanner scanner, Connection connection) {
		this.scanner = scanner;
		this.connection = connection;
	}

	@Override
	public String getDescription() {
		return "Viusalizza tutte le carte di un singolo Deck.";
	}

	@Override
	public void execute() throws SQLException {
		System.out.println();
		Integer deckId = null;
		DeckIDfromName getID = new DeckIDfromName(scanner, connection);
		deckId = getID.deckIDfromName();
		
		try (Statement stmt = connection.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT\r\n" + "  decks.nome,\r\n" + "  carte.nome,\r\n"
						+ "  carte.tipo,\r\n" + "  composizione_deck.quantità\r\n" + "FROM composizione_deck\r\n"
						+ "LEFT JOIN decks\r\n" + "ON id_deck = decks.id\r\n" + "LEFT JOIN carte\r\n"
						+ "ON codice_carta = carte.codice\r\n" + "WHERE decks.id = " + deckId + ";")) {

			System.out.println();
			System.out.printf("%-15s %-35s %-15s %-10s%n", "DECK", "NOME", "TIPO", "QUANTITÀ");

			System.out.println("----------------------------------------------------------------------------");

			while (rs.next()) {
				String deck = rs.getString("decks.nome");
				String name = rs.getString("carte.nome");
				String type = rs.getString("carte.tipo");
				String quantity = rs.getString("composizione_deck.quantità");

				System.out.printf("%-15s %-35s %-15s %-10s%n", deck, name, type, quantity);
			}
			System.out.println();

		} catch (SQLException e) {
			System.out.println("Errore nella query del Database:");
			e.printStackTrace();
		}
	}

}
