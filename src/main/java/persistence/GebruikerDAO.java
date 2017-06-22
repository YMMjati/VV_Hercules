package persistence;

import java.util.ArrayList;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import domain.Gebruiker;

public interface GebruikerDAO {
	public ArrayList<Gebruiker> getAllGebruikers() throws SQLException;
	public Gebruiker getGebruiker(int _id) throws SQLException;
	public void insertGebruiker(Gebruiker _gebruiker) throws SQLException;
	public void updateGebruiker(Gebruiker _gebruiker) throws SQLException;
	public void deleteGebruiker(int _id) throws SQLException;
	public Gebruiker verifyGebruiker(String _emailadres, String _wachtwoord) throws SQLException, NoSuchAlgorithmException;
}
