package yugi;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class EditDeckCmd implements Command {
	
	private final Scanner scanner;
	private final Connection connection;

	public EditDeckCmd(Scanner scanner, Connection connection) {
		this.scanner = scanner;
		this.connection = connection;
	}

	@Override
	public String getDescription() {
		return "Modifica un Deck esistente.";
	}

	@Override
	public void execute() throws SQLException {
		System.out.println();
		Integer deckId = null;
		DeckIDfromName getID = new DeckIDfromName(scanner, connection);
		deckId = getID.deckIDfromName();
		
	    if (deckId != null) {
            System.out.println();
            
            ArrayList<Command> subCmds = new ArrayList<>();
            subCmds.add(new AddCardCmd(scanner, connection, deckId));
            subCmds.add(new RemoveCardCmd(scanner, connection, deckId));
            subCmds.add(new DeleteDeckCmd(scanner, connection, deckId));
            
            Menu subMenu = new Menu(scanner, "Cosa vuoi fare?", subCmds);
            subMenu.menu();
	    } else {
	        System.out.println("Impossibile accedere al submenu: Deck non trovato.");
	    }
	}

}
