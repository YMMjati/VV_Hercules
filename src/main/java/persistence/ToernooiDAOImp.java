package persistence;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import database.DBConnection;
import domain.Toernooi;
import persistence.TeamDAOImp;

public class ToernooiDAOImp implements ToernooiDAO {
	private Connection conn;
	
	public ToernooiDAOImp() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		try{
			conn = new DBConnection().getConnection();
		}
		catch(InstantiationException|IllegalAccessException|ClassNotFoundException e){
			conn = null;
		}
	}
	
	@Override
	public ArrayList<Toernooi> getAllToernooien() throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		ArrayList<Toernooi> toernooien = new ArrayList<Toernooi>();
		
		if(conn == null){
			return toernooien;
		}
		
		try{
			Statement stmt = conn.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT * FROM toernooien;");

			while(rs.next()){
				if(rs.getInt("winnaar") == 0){
					toernooien.add(new Toernooi(rs.getInt("id"), rs.getString("naam"), rs.getTimestamp("starttijd"), rs.getTimestamp("eindtijd")));
				}
				else{
					TeamDAOImp tdi = new TeamDAOImp();
					toernooien.add(new Toernooi(rs.getInt("id"), rs.getString("naam"), rs.getTimestamp("starttijd"), rs.getTimestamp("eindtijd"), tdi.getTeam(rs.getInt("winnaar"))));
				}
			}
			
			return toernooien;
		}
		catch(SQLException|InstantiationException|IllegalAccessException|ClassNotFoundException e){
			e.printStackTrace();
			return toernooien;
		}
	}

	@Override
	public Toernooi getToernooi(int _id) throws SQLException {
		// TODO Deze method hoort bij een use case met een lage prioriteit.
		return null;
	}


	@Override
	public Toernooi getToernooiByName(String _naam) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		Toernooi toernooi = null;
			
		if(conn == null){
			return toernooi;
		}
			
		try{
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM toernooien WHERE naam = '"+ _naam +"'");

			if(rs.next()){
				if(rs.getInt("winnaar") == 0){
					toernooi = new Toernooi(rs.getInt("id"), rs.getString("naam"), rs.getTimestamp("starttijd"), rs.getTimestamp("eindtijd"));
				}
				else{
					TeamDAOImp tdi = new TeamDAOImp();
					toernooi = new Toernooi(rs.getInt("id"), rs.getString("naam"), rs.getTimestamp("starttijd"), rs.getTimestamp("eindtijd"), tdi.getTeam(rs.getInt("winnaar")));
				}
			}
				
				return toernooi;
			}
			catch(SQLException|InstantiationException|IllegalAccessException|ClassNotFoundException e){
				e.printStackTrace();
				return null;
			}
		}

	@Override
	public void InsertToernooi(Toernooi _toernooi) throws SQLException {
		if(conn == null){
			return;
		}
		
		try{
			Statement stmt = conn.createStatement();
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm");
			String starttijd = "date '"+ sdf1.format(_toernooi.getStarttijd()) +"' + time '"+ sdf2.format(_toernooi.getStarttijd()) +"'";
			String eindtijd = "date '"+ sdf1.format(_toernooi.getEindtijd()) +"' + time '"+ sdf2.format(_toernooi.getEindtijd()) +"'";
			
			if(_toernooi.getWinnaar() != null){
				stmt.executeUpdate("INSERT INTO toernooien (naam, starttijd, eindtijd, winnaar) VALUES ('"+ _toernooi.getNaam() +"',"+ starttijd +" ,"+ eindtijd +","
				+"(SELECT id FROM teams WHERE id = "+ _toernooi.getWinnaar().getId() +"))");
			}
			else{
				stmt.executeUpdate("INSERT INTO toernooien (naam, starttijd, eindtijd) VALUES ('"+ _toernooi.getNaam() +"',"+ starttijd +" ,"+ eindtijd +")");
			}
		}
		catch(SQLException e){
			e.printStackTrace();
			return;
		}	
	}

	@Override
	public void updateToernooi(int _id, String[] kolommen) throws SQLException {
		// TODO Deze method hoort bij een use case met een lage prioriteit.
	}

	@Override
	public void deleteToernooi(int _id) throws SQLException {
		if(conn == null){
			return;
		}
		
		try{
			Statement stmt = conn.createStatement();
			stmt.executeUpdate("DELETE FROM toernooien WHERE id = "+ _id);
		}
		catch(SQLException e){
			e.printStackTrace();
			return;
		}	
	}

}
