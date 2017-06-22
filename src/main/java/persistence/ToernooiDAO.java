package persistence;

import java.util.ArrayList;
import java.sql.SQLException;
import domain.Toernooi;

public interface ToernooiDAO {
	public ArrayList<Toernooi> getAllToernooien() throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException;
	public Toernooi getToernooi(int _id) throws SQLException;
	public void InsertToernooi(Toernooi _toernooi) throws SQLException;
	public void updateToernooi(int _id, String[] kolommen) throws SQLException;
	public void deleteToernooi(int _id) throws SQLException;
	public Toernooi getToernooiByName(String _naam) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException;
}
