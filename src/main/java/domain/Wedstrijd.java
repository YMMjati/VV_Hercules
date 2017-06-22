package domain;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDateTime;

public class Wedstrijd {
	private int id;
	private Toernooi toernooi; //Het toernooi attribuut is verplicht.
	private String type; //1 = Wedstrijd, 2 = Oefenwedstrijd, 3 = Toernooiwedstrijd
	private Team aanvaller;
	private Team verdediger;
	private Date starttijd;
	private Date eindtijd;
	private int aanvaller_score = 0;
	private int verdediger_score = 0;
	private List<Gebruiker> deelnemers = new ArrayList<Gebruiker>();
	
	public Wedstrijd(int _id, Toernooi _toernooi, String _type, Team _aanvaller, Team _verdediger, Date _starttijd, Date _eindtijd,
	int _aanvaller_score, int _verdediger_score, ArrayList<Gebruiker> _deelnemers){
		id = _id; toernooi = _toernooi; type = _type;
		aanvaller = _aanvaller; verdediger = _verdediger; starttijd = _starttijd;
		eindtijd = _eindtijd; aanvaller_score = _aanvaller_score; verdediger_score = _verdediger_score;
		deelnemers = _deelnemers;
	}
	
	//De aanvaller_score en verdediger_score worden hier niet meegegeven.
	public Wedstrijd(int _id, Toernooi _toernooi, String _type, Team _aanvaller, Team _verdediger, Date _starttijd, Date _eindtijd, ArrayList<Gebruiker> _deelnemers){
		id = _id; toernooi = _toernooi; type = _type;
		aanvaller = _aanvaller; verdediger = _verdediger; starttijd = _starttijd;
		eindtijd = _eindtijd; deelnemers = _deelnemers;
	}
	
	//Hier wordt alles meegegeven. De starttijd en eindtijd hebben het datatype LocalDateTime.
	public Wedstrijd(int _id, Toernooi _toernooi, String _type, Team _aanvaller, Team _verdediger, LocalDateTime _starttijd, LocalDateTime _eindtijd,
	int _aanvaller_score, int _verdediger_score, ArrayList<Gebruiker> _deelnemers){
		id = _id; toernooi = _toernooi; type = _type; aanvaller = _aanvaller;
		verdediger = _verdediger; starttijd = java.sql.Timestamp.valueOf(_starttijd); eindtijd = java.sql.Timestamp.valueOf(_eindtijd);
		aanvaller_score = _aanvaller_score; verdediger_score = _verdediger_score; deelnemers = _deelnemers;
	}
	
	//De aanvaller_score en verdediger_score worden hier niet meegegeven. De starttijd en eindtijd hebben het datatype LocalDateTime.
	public Wedstrijd(int _id, String _type, Team _aanvaller, Team _verdediger, LocalDateTime _starttijd, LocalDateTime _eindtijd, ArrayList<Gebruiker> _deelnemers){
		id = _id; type = _type; aanvaller = _aanvaller; verdediger = _verdediger;
		starttijd = java.sql.Timestamp.valueOf(_starttijd); eindtijd = java.sql.Timestamp.valueOf(_eindtijd); deelnemers = _deelnemers;
	}
	
	public int getId(){
		return id;
	}
	
	public void setId(int _id) {
		id = _id;
	}
	
	public Toernooi getToernooi(){
		return toernooi;
	}
	
	public void setToernooi(Toernooi _toernooi) {
		toernooi = _toernooi;
	}
	
	public String getType(){
		return type;
	}
	
	public void setType(String _type) {
		type = _type;
	}
	
	public Team getAanvaller(){
		return aanvaller;
	}
	
	public void setAanvaller(Team _aanvaller) {
		aanvaller = _aanvaller;
	}
	
	public Team getVerdediger(){
		return verdediger;
	}
	
	public void setVerdediger(Team _verdediger) {
		verdediger = _verdediger;
	}
	
	public Date getStarttijd(){
		return starttijd;
	}
	
	public void setStarttijd(Date _starttijd) {
		starttijd = _starttijd;
	}
	
	public Date getEindtijd(){
		return eindtijd;
	}
	
	public void setEindtijd(Date _eindtijd) {
		eindtijd = _eindtijd;
	}
	
	public int getAanvaller_score(){
		return aanvaller_score;
	}
	
	public void setAanvaller_score(int _aanvaller_score) {
		aanvaller_score = _aanvaller_score;
	}
	
	public int getVerdediger_score(){
		return verdediger_score;
	}
	
	public void setVerdediger_score(int _verdediger_score) {
		verdediger_score = _verdediger_score;
	}
	
	public List<Gebruiker> getDeelnemers(){
		return deelnemers;
	}
	
	public void setDeelnemers(List<Gebruiker> _deelnemers) {
		deelnemers = _deelnemers;
	}
	
	public String toString(){
		String toernooi_string = toernooi == null ? "geen" : toernooi.getNaam();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		String deelnemers_string = ""; 
		
		for (Gebruiker x : deelnemers) {
			deelnemers_string += "("+ x.getId() +") "+ x.getVoornaam() +" "+ x.getTussenvoegsel() +" "+ x.getAchternaam() +", ";
		}
		
		deelnemers_string = deelnemers_string.length() >= 2 ? deelnemers_string.substring(0, deelnemers_string.length() - 2) : deelnemers_string;
		
		return "id: "+ id +", toernooi: "+ toernooi_string +", type: "+ type +", aanvaller: "+ aanvaller.getNaam() +", verdediger: "+
		verdediger.getNaam() +", starttijd: "+ sdf.format(starttijd) +", eindtijd: "+ sdf.format(eindtijd) +" aanvaller_score: "+
		aanvaller_score +", verdediger_score: "+ verdediger_score +", deelnemers: "+ deelnemers_string;
	}
	
}
