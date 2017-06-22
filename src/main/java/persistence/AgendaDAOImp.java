package persistence;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import database.DBConnection;
import domain.Agenda;

public class AgendaDAOImp implements AgendaDAO {
	private Connection conn;
	
	public AgendaDAOImp() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		try{
			conn = new DBConnection().getConnection();
		}
		catch(InstantiationException|IllegalAccessException|ClassNotFoundException e){
			conn = null;
		}	
	}
	
	@Override
	public ArrayList<Agenda> getAgendaPunten(Date _starttijd, Date _eindtijd, Boolean alleen_openbaar) throws SQLException {
		ArrayList<Agenda> agendapunten = new ArrayList<Agenda>();
		
		if(conn == null || _starttijd == null || _eindtijd == null || _starttijd.after(_eindtijd)){
			return agendapunten;
		}
		
		try{
			Statement stmt = conn.createStatement();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String openbaar = alleen_openbaar == true ? " AND openbaar = true" : "";
			ResultSet rs = stmt.executeQuery("SELECT * FROM agenda WHERE starttijd BETWEEN '"+ sdf.format(_starttijd) +"' AND '"+ sdf.format(_eindtijd) +"'"+ openbaar +" ORDER BY starttijd ASC");
	
			while(rs.next()){
				agendapunten.add(new Agenda(rs.getInt("id"), rs.getString("evenement"), rs.getBoolean("openbaar"), rs.getTimestamp("starttijd"), rs.getTimestamp("eindtijd")));
			}
			
			return agendapunten;
		}
		catch(SQLException e){
			e.printStackTrace();
			return agendapunten;
		}
	}

	@Override
	public Agenda getAgendaPunt(int _id) throws SQLException {
		Agenda agendapunt = null;
		
		if(conn == null){
			return agendapunt;
		}
		
		try{
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM agenda WHERE id ="+ _id);

			if(rs.next()){
				agendapunt = new Agenda(rs.getInt("id"), rs.getString("evenement"), rs.getBoolean("openbaar"), rs.getTimestamp("starttijd"), rs.getTimestamp("eindtijd"));
			}
			
			return agendapunt;
		}
		catch(SQLException e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void InsertAgendaPunt(Agenda _agendapunt) throws SQLException {
		//Voor de makkelijkheid wordt een agenda object meegegeven om een nieuw agendapunt toe te voegen aan de agendatabel.
		//Het wedstrijd_id is standaard null.
		
		if(conn == null){
			return;
		}
		
		try{
			Statement stmt = conn.createStatement();
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm");
			String starttijd = "date '"+ sdf1.format(_agendapunt.getStarttijd()) +"' + time '"+ sdf2.format(_agendapunt.getStarttijd()) +"'";
			String eindtijd = "date '"+ sdf1.format(_agendapunt.getEindtijd()) +"' + time '"+ sdf2.format(_agendapunt.getEindtijd()) +"'";
			stmt.executeUpdate("INSERT INTO agenda (wedstrijd_id, evenement, openbaar, starttijd, eindtijd)"
			+"VALUES (null,'"+ _agendapunt.getEvenement() +"',"+ _agendapunt.getOpenbaar() +","+ starttijd +","+ eindtijd +")");
		}
		catch(SQLException e){
			e.printStackTrace();
			return;
		}	
	}

	@Override
	public void updateAgendaPunt(int _id, String[] kolommen) throws SQLException {
		// TODO Deze method hoort bij een use case met een lage prioriteit.
	}

	@Override
	public void deleteAgendaPunt(int _id) throws SQLException {
		if(conn == null){
			return;
		}
		
		try{
			Statement stmt = conn.createStatement();
			stmt.executeUpdate("DELETE FROM agenda WHERE id = "+ _id);
		}
		catch(SQLException e){
			e.printStackTrace();
			return;
		}	
	}

}
