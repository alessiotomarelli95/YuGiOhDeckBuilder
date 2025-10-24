package yugi;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ViewCardsCmd implements Command {

	private final Connection connection;

	public ViewCardsCmd(Connection connection) {
		this.connection = connection;
	}

	@Override
	public String getDescription() {
		return "Visualizza tutte le carte disponibili.";
	}

	@Override
	public void execute() throws SQLException {
		
		try (Statement stmt = connection.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT codice, nome, tipo FROM carte")) {

			System.out.println();
			System.out.printf("%-10s %-35s %-15s%n", "CODICE", "NOME", "TIPO");

			System.out.println("----------------------------------------------------------------------------");

			while (rs.next()) {
				String code = rs.getString("codice");
				String name = rs.getString("nome");
				String type = rs.getString("tipo");

				System.out.printf("%-10s %-35s %-15s%n", code, name, type);
			}
			System.out.println();

		} catch (SQLException e) {
			System.out.println("Errore nella query del Database:");
			e.printStackTrace();
		}
	}

}
