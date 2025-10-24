package yugi;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws SQLException {

		try {
			DBConnection dbConnection = new DBConnection();
			Connection conn = dbConnection.getConnection();

			ScannerDI scannerDI = new ScannerDI();
			Scanner scanner = scannerDI.getScanner();

			System.out.println("******************************* DECK BUILDER *******************************\n");
			
			ArrayList<Command> mainCmds = new ArrayList<>();
			mainCmds.add(new ViewCardsCmd(conn));
			mainCmds.add(new CreateDeckCmd(scanner, conn));
			mainCmds.add(new EditDeckCmd(scanner, conn));
			mainCmds.add(new ViewDecksCmd(conn));
			mainCmds.add(new ViewCardsFromDeckCmd(scanner, conn));

			Menu mainMenu = new Menu(scanner, "Benvenuto nel Deck Builder di Yu-Gi-Oh!\n"
					+ "Mediante questo programma potrai creare Deck e"
					+ "\ngestirne la costruzione a partire da un pool di carte"
					+ "\nScegli una delle seguenti opzioni per iniziare.", mainCmds);
			
			mainMenu.menu();

			System.out.println("\n****************************************************************************\n");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
