package persistence;

import database.DBConnection;

//import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
import domain.Gebruiker;

public class GebruikerDAOImp implements GebruikerDAO {
	private Connection conn;
	
	public GebruikerDAOImp() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		try{
			conn = new DBConnection().getConnection();
		}
		catch(InstantiationException|IllegalAccessException|ClassNotFoundException e){
			conn = null;
		}	
	}
	
	@Override
	public ArrayList<Gebruiker> getAllGebruikers() throws SQLException{
		ArrayList<Gebruiker> gebruikers = new ArrayList<Gebruiker>();
		
		if(conn == null){
			return gebruikers;
		}
		
		try{
			Statement stmt = conn.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT a.id AS id, b.naam AS rol, c.naam AS status, a.emailadres AS emailadres, a.wachtwoord AS wachtwoord, a.voornaam AS voornaam, a.tussenvoegsel AS tussenvoegsel, a.achternaam, a.woonplaats, a.straat, a.huisnummer, a.postcode"
			+" FROM gebruikers a JOIN gebruikersrollen b ON a.rol_id = b.id JOIN statussen c ON a.status_id = c.id");

			while(rs.next()){
				String tussenvoegsel = rs.getString("tussenvoegsel") == null ? "" : rs.getString("tussenvoegsel");
				gebruikers.add(new Gebruiker(rs.getInt("id"), rs.getString("rol"), rs.getString("status"), rs.getString("emailadres"), rs.getString("wachtwoord"),
				rs.getString("voornaam"), tussenvoegsel, rs.getString("achternaam"), rs.getString("woonplaats"), rs.getString("straat"),
				rs.getInt("huisnummer"), rs.getString("postcode")));
			}
		}
		catch(SQLException e){
			e.printStackTrace();
			return gebruikers;
		}

		return gebruikers;
	}

	@Override
	public Gebruiker getGebruiker(int _id) throws SQLException {
		Gebruiker gebruiker = null;
		
		if(conn == null){
			return gebruiker;
		}
		
		try{
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT a.id AS id, b.naam AS rol, c.naam AS status, a.emailadres AS emailadres, a.wachtwoord AS wachtwoord, a.voornaam AS voornaam, a.tussenvoegsel AS tussenvoegsel, a.achternaam, a.woonplaats, a.straat, a.huisnummer, a.postcode"
			+" FROM gebruikers a JOIN gebruikersrollen b ON a.rol_id = b.id JOIN statussen c ON a.status_id = c.id WHERE a.id = "+ _id);
	
			if(rs.next()){
				String tussenvoegsel = rs.getString("tussenvoegsel") == null ? "" : rs.getString("tussenvoegsel");
				gebruiker = new Gebruiker(rs.getInt("id"), rs.getString("rol"), rs.getString("status"), rs.getString("emailadres"), rs.getString("wachtwoord"), rs.getString("voornaam"),
				tussenvoegsel, rs.getString("achternaam"), rs.getString("woonplaats"), rs.getString("straat"), rs.getInt("huisnummer"), rs.getString("postcode"));
			}
			
			return gebruiker;
		}
		catch(SQLException e){
			e.printStackTrace();
			return gebruiker;
		}
			
	}

	@Override
	public void insertGebruiker(Gebruiker _gebruiker) throws SQLException {
		//Voor de makkelijkheid wordt een gebruiker object meegegeven om een nieuwe gebruiker toe te voegen aan de gebruikerstabel.
		
		if(conn == null){
			return;
		}
		
		try{
			String tussenvoegsel = _gebruiker.getTussenvoegsel() == null || _gebruiker.getTussenvoegsel() == "" ? "null" : "'"+ _gebruiker.getTussenvoegsel() +"'";
			Statement stmt = conn.createStatement();
			stmt.executeUpdate("INSERT INTO gebruikers (rol_id, status_id, emailadres, wachtwoord, voornaam, tussenvoegsel, achternaam, woonplaats, straat, huisnummer, postcode) VALUES"
			+"((SELECT id FROM gebruikersrollen WHERE naam = '"+ _gebruiker.getRol() +"'), (SELECT id FROM statussen WHERE naam = '"+ _gebruiker.getStatus() +"'), '"+ _gebruiker.getEmailadres() +"', '"+ _gebruiker.getWachtwoord() +"',"
			+ "'"+ _gebruiker.getVoornaam() +"', "+ tussenvoegsel +", '"+ _gebruiker.getAchternaam() +"', '"+ _gebruiker.getWoonplaats() +"', '"+ _gebruiker.getStraat() +"', "+ _gebruiker.getHuisnummer() +", '"+ _gebruiker.getPostcode() +"');");
		}
		catch(SQLException e){
			e.printStackTrace();
			return;
		}	
	}

	@Override
	public void updateGebruiker(Gebruiker _gebruiker) throws SQLException {
		//Deze method gebruikt een gebruiker object om te bepalen welke kolommen geüpdatet moeten worden.
		//Alle attributen die null of 0 bevatten (afhankelijk van datatype), worden genegeerd.
		//Als het tussenvoegsel de string "null" heeft, wordt de tussenvoegsel kolom naar null geüpdatet.
		
		if(conn == null){
			return;
		}
		
		try{	
			String updatekolommen = "";
			
			if(_gebruiker.getRol() != null){updatekolommen += "rol_id = (SELECT id FROM gebruikersrollen WHERE naam = '"+ _gebruiker.getRol() +"'), ";}
			if(_gebruiker.getStatus() != null){updatekolommen += "status_id = (SELECT id FROM statussen WHERE naam = '"+ _gebruiker.getStatus() +"'), ";}
			if(_gebruiker.getEmailadres() != null){updatekolommen += "emailadres = '"+ _gebruiker.getEmailadres() +"', ";}
			if(_gebruiker.getWachtwoord() != null){updatekolommen += "wachtwoord = '"+ _gebruiker.getWachtwoord() +"', ";}
			if(_gebruiker.getVoornaam() != null){updatekolommen += "voornaam = '"+ _gebruiker.getVoornaam() +"', ";}
			if(_gebruiker.getTussenvoegsel() == "null"){updatekolommen += "tussenvoegsel = null, ";}
			else if(_gebruiker.getTussenvoegsel() != null && _gebruiker.getTussenvoegsel() != "null" && _gebruiker.getTussenvoegsel() != ""){updatekolommen += "tussenvoegsel = '"+ _gebruiker.getTussenvoegsel() +"', ";}
			if(_gebruiker.getAchternaam() != null){updatekolommen += "achternaam = '"+ _gebruiker.getAchternaam() +"', ";}
			if(_gebruiker.getWoonplaats() != null){updatekolommen += "woonplaats = '"+ _gebruiker.getWoonplaats() +"', ";}
			if(_gebruiker.getStraat() != null){updatekolommen += "straat = '"+ _gebruiker.getStraat() +"', ";}
			if(_gebruiker.getHuisnummer() != 0){updatekolommen += "huisnummer = "+ _gebruiker.getHuisnummer() +", ";}
			if(_gebruiker.getPostcode() != null){ //Als de postcode niet de laatste kolom is, wordt de komma aan het eind weggehaald.
				updatekolommen += "postcode = '"+ _gebruiker.getPostcode() +"'";
			}
			else{
				updatekolommen = updatekolommen.substring(0, updatekolommen.length() - 2);
			}
			
			Statement stmt = conn.createStatement();
		
			stmt.executeUpdate("UPDATE gebruikers SET "+ updatekolommen +" WHERE id = "+ _gebruiker.getId());
		}
		catch(SQLException e){
			e.printStackTrace();
			return;
		}	
	}

	@Override
	public void deleteGebruiker(int _id) throws SQLException {
		if(conn == null){
			return;
		}
		
		try{
			Statement stmt = conn.createStatement();
			stmt.executeUpdate("DELETE FROM gebruikers WHERE id = "+ _id);
		}
		catch(SQLException e){
			e.printStackTrace();
			return;
		}	
	}
	
	public Gebruiker verifyGebruiker(String _emailadres, String _wachtwoord) throws SQLException, NoSuchAlgorithmException{
		//Deze method gaat na of de inloggegevens van een gebruiker kloppen.
		
		Gebruiker gebruiker = null;

		if(conn == null){
			return null;
		}
		
		try{	
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT a.id AS id, b.naam AS rol, c.naam AS status, a.emailadres AS emailadres, a.wachtwoord AS wachtwoord, a.voornaam AS voornaam, a.tussenvoegsel AS tussenvoegsel, a.achternaam, a.woonplaats, a.straat, a.huisnummer, a.postcode"
			+" FROM gebruikers a JOIN gebruikersrollen b ON a.rol_id = b.id JOIN statussen c ON a.status_id = c.id WHERE a.emailadres = '"+ _emailadres +"' AND a.status_id = 1");

			if(rs.next()){
				String tussenvoegsel = rs.getString("tussenvoegsel") == null ? "" : rs.getString("tussenvoegsel");
				gebruiker = new Gebruiker(rs.getInt("id"), rs.getString("rol"), rs.getString("status"), rs.getString("emailadres"), rs.getString("wachtwoord"), rs.getString("voornaam"),
				tussenvoegsel, rs.getString("achternaam"), rs.getString("woonplaats"), rs.getString("straat"), rs.getInt("huisnummer"), rs.getString("postcode"));
			}
			else{
				return null;
			}
			
			/*
			// Deze code dient voor een (toekomstige) functionaliteit die een gehashed ingevoerd wachtwoord vergelijkt met een gehashed wachtwoord in de database.
			String salt = "ZH%5RMhqwEX25^cF";
			String wachtwoord = _wachtwoord + salt;
			MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
			messageDigest.update(wachtwoord.getBytes());
			String hashedwachtwoord = new String(messageDigest.digest());
			*/
			
			if(_wachtwoord.equals(gebruiker.getWachtwoord())){
				return gebruiker;
			}
			else{
				return null;
			}
		}
		catch(SQLException e){
			e.printStackTrace();
			return null;
		}		
	}

}

