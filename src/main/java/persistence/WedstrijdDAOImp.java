package persistence;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import database.DBConnection;
import domain.Wedstrijd;
import domain.Team;
import domain.Toernooi;
import domain.Gebruiker;

public class WedstrijdDAOImp implements WedstrijdDAO {
	private Connection conn;
	
	public WedstrijdDAOImp() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		try{
			conn = new DBConnection().getConnection();
		}
		catch(InstantiationException|IllegalAccessException|ClassNotFoundException e){
			conn = null;
		}
	}

	@Override
	public ArrayList<Wedstrijd> getWedstrijden(Date _starttijd, Date _eindtijd, Boolean alleen_openbaar) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		//Vanwege de complexiteit van deze method, worden de deelnemers (nog) niet aan de wedstrijd objecten toegevoegd.	
		ArrayList<Wedstrijd> wedstrijden = new ArrayList<Wedstrijd>();
		
		if(conn == null){
			return wedstrijden;
		}

		try{
			Statement stmt = conn.createStatement();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String openbaar = alleen_openbaar == true ? " AND type_id NOT IN (2)" : "";
			ResultSet rs = stmt.executeQuery("SELECT a.id AS id, a.toernooi_id AS toernooi_id, b.naam AS toernooi_naam, b.starttijd AS toernooi_starttijd, b.eindtijd AS toernooi_eindtijd, b.winnaar AS toernooi_winnaar_id, e.naam AS toernooi_winnaar_naam, a.type_id AS type_id, f.naam AS type_naam, a.aanvaller_id AS aanvaller_id, c.naam AS aanvaller_naam, a.verdediger_id AS verdediger_id, d.naam AS verdediger_naam, a.starttijd AS starttijd, a.eindtijd AS eindtijd, a.aanvaller_score AS aanvaller_score, a.verdediger_score AS verdediger_score FROM wedstrijden a LEFT OUTER JOIN toernooien b ON a.toernooi_id = b.id LEFT OUTER JOIN teams c ON a.aanvaller_id = c.id LEFT OUTER JOIN teams d ON a.verdediger_id = d.id LEFT OUTER JOIN teams e ON b.winnaar = e.id LEFT OUTER JOIN wedstrijdentypes f ON a.type_id = f.id WHERE a.starttijd BETWEEN '"+ sdf.format(_starttijd) +"' AND '"+ sdf.format(_eindtijd) +"'"+ openbaar +" ORDER BY starttijd ASC");
			
			while(rs.next()){
				if(rs.getInt("toernooi_id") == 0){ //De wedstrijd heeft geen toernooi, dus er wordt null als toernooi meegegeven.
					wedstrijden.add(new Wedstrijd(rs.getInt("id"),null,rs.getString("type_naam"),new Team(rs.getInt("aanvaller_id"), rs.getString("aanvaller_naam")),new Team(rs.getInt("verdediger_id"), rs.getString("verdediger_naam")),rs.getTimestamp("starttijd"),rs.getTimestamp("eindtijd"),rs.getInt("aanvaller_score"),rs.getInt("verdediger_score"),new ArrayList<Gebruiker>()));
				}
				else{
					if(rs.getInt("toernooi_winnaar_id") == 0){ //De wedstrijd heeft een toernooi, het toernooi heeft geen winnaar en krijgt dus geen team object.
						wedstrijden.add(new Wedstrijd(rs.getInt("id"),new Toernooi(rs.getInt("toernooi_id"),rs.getString("toernooi_naam"),rs.getTimestamp("toernooi_starttijd"), rs.getTimestamp("toernooi_eindtijd")),rs.getString("type_naam"),new Team(rs.getInt("aanvaller_id"), rs.getString("aanvaller_naam")),new Team(rs.getInt("verdediger_id"), rs.getString("verdediger_naam")),rs.getTimestamp("starttijd"),rs.getTimestamp("eindtijd"),rs.getInt("aanvaller_score"),rs.getInt("verdediger_score"),new ArrayList<Gebruiker>()));
					}
					else{ //De wedstrijd heeft een toernooi met een winnaar, in het toernooi object zelf wordt een team object meegegeven.
						wedstrijden.add(new Wedstrijd(rs.getInt("id"),new Toernooi(rs.getInt("toernooi_id"),rs.getString("toernooi_naam"),rs.getTimestamp("toernooi_starttijd"), rs.getTimestamp("toernooi_eindtijd"), new Team(rs.getInt("toernooi_winnaar_id"), rs.getString("toernooi_winnaar_naam"))),rs.getString("type_naam"),new Team(rs.getInt("aanvaller_id"), rs.getString("aanvaller_naam")),new Team(rs.getInt("verdediger_id"), rs.getString("verdediger_naam")),rs.getTimestamp("starttijd"),rs.getTimestamp("eindtijd"),rs.getInt("aanvaller_score"),rs.getInt("verdediger_score"),new ArrayList<Gebruiker>()));
					}
				}
			}
			
			return wedstrijden;
			
		}
		catch(SQLException e){
			e.printStackTrace();
			return wedstrijden;
		}
	}

	@Override
	public Wedstrijd getWedstrijd(int _id) throws SQLException {
		Wedstrijd wedstrijd = null;
		
		if(conn == null){
			return wedstrijd;
		}
		
		try{
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT a.id AS id, a.toernooi_id AS toernooi_id, b.naam AS toernooi_naam, b.starttijd AS toernooi_starttijd, b.eindtijd AS toernooi_eindtijd, b.winnaar AS toernooi_winnaar_id, e.naam AS toernooi_winnaar_naam, a.type_id AS type_id, f.naam AS type_naam, a.aanvaller_id AS aanvaller_id, c.naam AS aanvaller_naam, a.verdediger_id AS verdediger_id, d.naam AS verdediger_naam, a.starttijd AS starttijd, a.eindtijd AS eindtijd, a.aanvaller_score AS aanvaller_score, a.verdediger_score AS verdediger_score FROM wedstrijden a LEFT OUTER JOIN toernooien b ON a.toernooi_id = b.id LEFT OUTER JOIN teams c ON a.aanvaller_id = c.id LEFT OUTER JOIN teams d ON a.verdediger_id = d.id LEFT OUTER JOIN teams e ON b.winnaar = e.id LEFT OUTER JOIN wedstrijdentypes f ON a.type_id = f.id WHERE a.id = "+ _id);
			
			if(rs.next()){
				if(rs.getInt("toernooi_id") == 0){ //De wedstrijd heeft geen toernooi, dus er wordt null als toernooi meegegeven.
					wedstrijd = new Wedstrijd(rs.getInt("id"),null,rs.getString("type_naam"),new Team(rs.getInt("aanvaller_id"), rs.getString("aanvaller_naam")),new Team(rs.getInt("verdediger_id"), rs.getString("verdediger_naam")),rs.getTimestamp("starttijd"),rs.getTimestamp("eindtijd"),rs.getInt("aanvaller_score"),rs.getInt("verdediger_score"),new ArrayList<Gebruiker>());
				}
				else{
					if(rs.getInt("toernooi_winnaar_id") == 0){ //De wedstrijd heeft een toernooi, het toernooi heeft geen winnaar en krijgt dus geen team object.
						wedstrijd = new Wedstrijd(rs.getInt("id"),new Toernooi(rs.getInt("toernooi_id"),rs.getString("toernooi_naam"),rs.getTimestamp("toernooi_starttijd"), rs.getTimestamp("toernooi_eindtijd")),rs.getString("type_naam"),new Team(rs.getInt("aanvaller_id"), rs.getString("aanvaller_naam")),new Team(rs.getInt("verdediger_id"), rs.getString("verdediger_naam")),rs.getTimestamp("starttijd"),rs.getTimestamp("eindtijd"),rs.getInt("aanvaller_score"),rs.getInt("verdediger_score"),new ArrayList<Gebruiker>());
					}
					else{ //De wedstrijd heeft een toernooi met een winnaar, in het toernooi object zelf wordt een team object meegegeven.
						wedstrijd = new Wedstrijd(rs.getInt("id"),new Toernooi(rs.getInt("toernooi_id"),rs.getString("toernooi_naam"),rs.getTimestamp("toernooi_starttijd"), rs.getTimestamp("toernooi_eindtijd"), new Team(rs.getInt("toernooi_winnaar_id"), rs.getString("toernooi_winnaar_naam"))),rs.getString("type_naam"),new Team(rs.getInt("aanvaller_id"), rs.getString("aanvaller_naam")),new Team(rs.getInt("verdediger_id"), rs.getString("verdediger_naam")),rs.getTimestamp("starttijd"),rs.getTimestamp("eindtijd"),rs.getInt("aanvaller_score"),rs.getInt("verdediger_score"),new ArrayList<Gebruiker>());
					}
				}
			}
			
			return wedstrijd;
		}
		catch(SQLException e){
			e.printStackTrace();
			return wedstrijd;
		}
	}

	@Override
	public void InsertWedstrijd(Wedstrijd _wedstrijd) throws SQLException {
		if(conn == null){
			return;
		}
		
		try{
			Statement stmt = conn.createStatement();
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm");
			String starttijd = "date '"+ sdf1.format(_wedstrijd.getStarttijd()) +"' + time '"+ sdf2.format(_wedstrijd.getStarttijd()) +"'";
			String eindtijd = "date '"+ sdf1.format(_wedstrijd.getEindtijd()) +"' + time '"+ sdf2.format(_wedstrijd.getEindtijd()) +"'";
			String toernooi_id = _wedstrijd.getToernooi() != null ? Integer.toString(_wedstrijd.getToernooi().getId()) : "null";
			stmt.executeUpdate("INSERT INTO wedstrijden (toernooi_id, type_id, aanvaller_id, verdediger_id, starttijd, eindtijd, aanvaller_score, verdediger_score)"
			+ " VALUES("+ toernooi_id +",(SELECT id FROM wedstrijdentypes WHERE naam = '"+ _wedstrijd.getType() +"'),"+ _wedstrijd.getAanvaller().getId() +","+ _wedstrijd.getVerdediger().getId() +","+ starttijd +","+ eindtijd +","+ _wedstrijd.getAanvaller_score() +","+ _wedstrijd.getVerdediger_score() +")");
		}
		catch(SQLException e){
			e.printStackTrace();
			return;
		}
	}

	@Override
	public void updateWedstrijd(Wedstrijd _wedstrijd) throws SQLException {
		// TODO Deze method hoort bij een use case met een lage prioriteit.
		if(conn == null){
			return;
		}
	}

	@Override
	public void deleteWedstrijd(int _id) throws SQLException {
		if(conn == null){
			return;
		}
		
		try{
			Statement stmt = conn.createStatement();
			stmt.executeUpdate("DELETE FROM wedstrijden WHERE id = "+ _id);
		}
		catch(SQLException e){
			e.printStackTrace();
			return;
		}	
	}

}
