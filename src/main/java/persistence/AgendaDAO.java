package persistence;

import java.util.ArrayList;
import java.util.Date;
import java.sql.SQLException;
import domain.Agenda;

public interface AgendaDAO {
	public ArrayList<Agenda> getAgendaPunten(Date _starttijd, Date _eindtijd, Boolean alleen_openbaar) throws SQLException;
	public Agenda getAgendaPunt(int _id) throws SQLException;
	public void InsertAgendaPunt(Agenda _agendapunt) throws SQLException;
	public void updateAgendaPunt(int _id, String[] kolommen) throws SQLException;
	public void deleteAgendaPunt(int _id) throws SQLException;
}
