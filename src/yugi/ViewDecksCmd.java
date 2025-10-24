package yugi;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ViewDecksCmd implements Command {

	private final Connection connection;

	public ViewDecksCmd(Connection connection) {
		this.connection = connection;
	}

	@Override
	public String getDescription() {
		return "Visualizza una lista di tutti i Deck creati.";
	}

	@Override
	public void execute() throws SQLException {
		try (Statement stmt = connection.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT nome FROM decks")) {

			System.out.println();
			System.out.printf("LISTA DEI DECK CREATI\n");

			System.out.println("----------------------------------------------------------------------------");

			while (rs.next()) {
				String name = rs.getString("nome");

				System.out.printf("- " + name);
				System.out.println();
			}
			System.out.println();

		} catch (SQLException e) {
			System.out.println("Errore nella query del Database:");
			e.printStackTrace();
		}
	}

}
