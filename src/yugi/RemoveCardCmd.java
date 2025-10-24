package yugi;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class RemoveCardCmd implements Command {

	private final Scanner scanner;
	private final Connection connection;
	private final int deckId;

	public RemoveCardCmd(Scanner scanner, Connection connection, int deckId) {
		this.scanner = scanner;
		this.connection = connection;
		this.deckId = deckId;
	}

	@Override
	public String getDescription() {
		return "Rimuovi una carta";
	}

	@Override
	public void execute() throws SQLException {
		System.out.print("Inserisci il codice della Carta da rimuovere:");
		String cardCode = scanner.nextLine();

		if (!cardCode.matches("^\\d{8}$")) {
			System.out.println("Il codice non è valido. Inserisci esattamente 8 cifre numeriche (0-9).");
			return;
		}

		System.out.print("Quante copie vuoi rimuovere? ");
		String cardQuant = scanner.nextLine();

		if (!cardQuant.matches("^[123]$")) {
			System.out.println("La quantità non è valida. Inserisci esattamente 1 cifra (1-3).");
			return;
		}

		int quantityToRemove = Integer.parseInt(cardQuant);

		String updateSql = "UPDATE composizione_deck\r\n" + "SET quantità = GREATEST(quantità - ?, 0)\r\n"
				+ "WHERE id_deck = ? AND codice_carta = ?\r\n";

		try (PreparedStatement pstmt = connection.prepareStatement(updateSql)) {
			pstmt.setInt(1, quantityToRemove);
			pstmt.setInt(2, deckId);
			pstmt.setString(3, cardCode);
			int rowsUpdated = pstmt.executeUpdate();

			if (rowsUpdated == 0) {
				System.out.println("La carta non esiste nel deck.");
				return;
			}
		}

		String selectSql = "SELECT quantità FROM composizione_deck\r\n" + "WHERE id_deck = ? AND codice_carta = ?\r\n";

		try (PreparedStatement pstmt = connection.prepareStatement(selectSql)) {
			pstmt.setInt(1, deckId);
			pstmt.setString(2, cardCode);

			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					int newQuant = rs.getInt("quantità");
					System.out.println("Numero di copie rimanenti: " + newQuant + "\n");

					if (newQuant == 0) {

						String deleteSql = "DELETE FROM composizione_deck\r\n"
								+ "WHERE id_deck = ? AND codice_carta = ?\r\n";

						try (PreparedStatement delStmt = connection.prepareStatement(deleteSql)) {
							delStmt.setInt(1, deckId);
							delStmt.setString(2, cardCode);
							delStmt.executeUpdate();
							System.out.println("La carta è stata rimossa completamente dal deck.\n");
						}
					}
				}
			}
		}
	}
}
