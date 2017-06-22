package persistence;

import java.sql.*;
import database.DBConnection;
import domain.Team;

public class TeamDAOImp implements TeamDAO {
	private Connection conn;
	
	public TeamDAOImp() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		try{
			conn = new DBConnection().getConnection();
		}
		catch(InstantiationException|IllegalAccessException|ClassNotFoundException e){
			conn = null;
		}	
	}
	
	@Override
	public Team getTeam(int _id) throws SQLException {
		Team team = null;
		
		if(conn == null){
			return team;
		}
		
		try{
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM teams WHERE id = "+ _id);

			if(rs.next()){
				team = new Team(rs.getInt("id"), rs.getString("naam"));
			}
			
			return team;
		}
		catch(SQLException e){
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public Team getTeamByName(String _naam) throws SQLException {
		Team team = null;
		
		if(conn == null){
			return team;
		}
		
		try{
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM teams WHERE naam = '"+ _naam +"'");

			if(rs.next()){
				team = new Team(rs.getInt("id"), rs.getString("naam"));
			}
			
			return team;
		}
		catch(SQLException e){
			e.printStackTrace();
			return null;
		}
	}

}
