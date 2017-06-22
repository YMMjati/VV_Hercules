package persistence;

import java.sql.SQLException;
import domain.Team;

public interface TeamDAO {
	public Team getTeam(int _id) throws SQLException;
	public Team getTeamByName(String _naam) throws SQLException;
}
