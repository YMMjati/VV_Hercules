package persistence;

import java.util.ArrayList;
import java.util.Date;
import java.sql.SQLException;
import domain.Wedstrijd;

public interface WedstrijdDAO {
	public ArrayList<Wedstrijd> getWedstrijden(Date _starttijd, Date _eindtijd, Boolean alleen_openbaar) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException;
	public Wedstrijd getWedstrijd(int _id) throws SQLException;
	public void InsertWedstrijd(Wedstrijd _wedstrijd) throws SQLException;
	public void updateWedstrijd(Wedstrijd _wedstrijd) throws SQLException;
	public void deleteWedstrijd(int _id) throws SQLException;
}
