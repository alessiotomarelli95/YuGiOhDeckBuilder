package yugi;

import java.sql.SQLException;

public interface Command {
	
	public String getDescription();
	public void execute() throws SQLException;

}
