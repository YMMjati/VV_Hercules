package domain;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

public class Agenda {
	private int id;
	private String evenement;
	private boolean openbaar;
	private Date starttijd;
	private Date eindtijd;
	
	public Agenda(int _id, String _evenement, boolean _openbaar, Date _starttijd, Date _eindtijd){
		id = _id;
		evenement = _evenement;
		openbaar = _openbaar;
		starttijd = _starttijd;
		eindtijd = _eindtijd;
	}
	
	//Deze tweede constructor accepteert LocalDateTime i.p.v. Date als datatype voor starttijd en eindtijd.
	public Agenda(int _id, String _evenement, boolean _openbaar, LocalDateTime _starttijd, LocalDateTime _eindtijd){
		id = _id;
		evenement = _evenement;
		openbaar = _openbaar;
		starttijd = java.sql.Timestamp.valueOf(_starttijd);
		eindtijd = java.sql.Timestamp.valueOf(_eindtijd);
	}

	public int getId() {
		return id;
	}

	public void setId(int _id) {
		id = _id;
	}

	public String getEvenement() {
		return evenement;
	}

	public void setEvenement(String _evenement) {
		evenement = _evenement;
	}

	public boolean getOpenbaar() {
		return openbaar;
	}

	public void setOpenbaar(boolean _openbaar) {
		openbaar = _openbaar;
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
	
	public String toString(){
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		return "id: "+ id +", evenement: "+ evenement +", boolean: "+ openbaar +", starttijd: "+ sdf.format(starttijd) +", eindtijd: "+ sdf.format(eindtijd);
	}
	
}
