package domain;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

public class Toernooi {
	private int id;
	private String naam;
	private Date starttijd;
	private Date eindtijd;
	private Team winnaar; //Het winnaar attribuut is optioneel.

	public Toernooi(int _id, String _naam, Date _starttijd, Date _eindtijd, Team _winnaar){
		id = _id; naam = _naam; starttijd = _starttijd;
		eindtijd = _eindtijd; winnaar = _winnaar;
	}
	
	//De winnaar wordt hier niet meegegeven.
	public Toernooi(int _id, String _naam, Date _starttijd, Date _eindtijd){
		id = _id; naam = _naam;
		starttijd = _starttijd; eindtijd = _eindtijd;
	}
	
	//Alles wordt hier meegegeven, maar de starttijd en eindtijd hebben het datatype LocalDateTimeTime.
	public Toernooi(int _id, String _naam, LocalDateTime _starttijd, LocalDateTime _eindtijd, Team _winnaar){
		id = _id; naam = _naam; starttijd = java.sql.Timestamp.valueOf(_starttijd);
		eindtijd = java.sql.Timestamp.valueOf(_eindtijd); winnaar = _winnaar;
	}
	
	//De winnaar wordt hier niet meegegeven, en de starttijd en eindtijd hebben het datatype LocalDateTimeTime.
	public Toernooi(int _id, String _naam, LocalDateTime _starttijd, LocalDateTime _eindtijd){
		id = _id; naam = _naam; starttijd = java.sql.Timestamp.valueOf(_starttijd);
		eindtijd = java.sql.Timestamp.valueOf(_eindtijd);
	}
	
	public int getId() {
		return id;
	}

	public void setId(int _id) {
		id = _id;
	}

	public String getNaam() {
		return naam;
	}

	public void setNaam(String _naam) {
		naam = _naam;
	}

	public Date getStarttijd() {
		return starttijd;
	}

	public void setStarttijd(Date _starttijd) {
		starttijd = _starttijd;
	}

	public Date getEindtijd() {
		return eindtijd;
	}

	public void setEindtijd(Date _eindtijd) {
		eindtijd = _eindtijd;
	}

	public Team getWinnaar() {
		return winnaar;
	}

	public void setWinnaar(Team _winnaar) {
		winnaar = _winnaar;
	}
	
	public String toString(){
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		String winnaar_string = winnaar == null ? "geen" : winnaar.getNaam();	
		return "id: "+ id +", naam: "+ naam +", starttijd: "+ sdf.format(starttijd) +", eindtijd: "+ sdf.format(eindtijd) +", winnaar: "+ winnaar_string;
	}
	
}
