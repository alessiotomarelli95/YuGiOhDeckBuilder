package yugi;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class AddCardCmd implements Command {

	private final Scanner scanner;
	private final Connection connection;
	private final int deckId;

	public AddCardCmd(Scanner scanner, Connection connection, int deckId) {
		this.scanner = scanner;
		this.connection = connection;
		this.deckId = deckId;
	}

	@Override
	public String getDescription() {
		return "Aggiungi una Carta.";
	}

	@Override
	public void execute() throws SQLException {
		
		String cardCode;

		while (true) {

			System.out.print("Inserisci il codice della Carta da inserire:");
			cardCode = scanner.nextLine();

			if (!cardCode.matches("^\\d{8}$")) {
				System.out.println("Il codice non è valido. Inserisci esattamente 8 cifre numeriche (0-9).");
			} else {
				break;
			}
		}

		int insertQuant;

		while (true) {

			System.out.print("Quante copie vuoi inserire? ");
			insertQuant = scanner.nextInt();
			scanner.nextLine();

			if (insertQuant < 1 || insertQuant > 3) {
				System.out.println("La quantità non è valida. Inserisci esattamente 1 cifra (1-3).");
			} else {
				break;
			}
		}

		int maxQuant = 3;

		String checkSql = "SELECT quantità FROM composizione_deck WHERE id_deck = ? AND codice_carta = ?;";

		try (PreparedStatement checkPstmt = connection.prepareStatement(checkSql)) {
			checkPstmt.setInt(1, deckId);
			checkPstmt.setString(2, cardCode);
			ResultSet rs = checkPstmt.executeQuery();

			if (rs.next()) {
				int currentQuant = rs.getInt("quantità");
				int newTotal = currentQuant + insertQuant;

				if (newTotal > maxQuant) {
					int addable = maxQuant - currentQuant;
					System.out.println("Errore! Hai già " + currentQuant + " copie di questa carta nel Deck. "
							+ "Puoi aggiungere massimo " + addable + " copia/e");
				} else {
					String updateSql = "UPDATE composizione_deck SET quantità = ? WHERE id_deck = ? AND codice_carta = ?;";

					try (PreparedStatement updatePstmt = connection.prepareStatement(updateSql)) {
						updatePstmt.setInt(1, newTotal);
						updatePstmt.setInt(2, deckId);
						updatePstmt.setString(3, cardCode);
						updatePstmt.executeUpdate();

						System.out.println("Carta aggiunta: al momento hai nel Deck " + newTotal
								+ " copia/e della carta selezionata.");
					}
				}
			} else {
				if (insertQuant > maxQuant) {
					System.out.println("Errore! La quantità massima di copie consentita per ciascuna carta è 3.");
				} else {
					String insertSql = "INSERT INTO composizione_deck (id_deck, codice_carta, quantità) "
							+ "VALUES (?, ?, ?)";

					try (PreparedStatement insertPstmt = connection.prepareStatement(insertSql)) {
						insertPstmt.setInt(1, deckId);
						insertPstmt.setString(2, cardCode);
						insertPstmt.setInt(3, insertQuant);
						insertPstmt.execute();

						System.out.println("La carta è stata inserita con successo!\n");
					}
				}
			}
		}
	}
}
