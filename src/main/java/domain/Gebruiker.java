package domain;

public class Gebruiker {
	private int id;
	private String rol; //1 = Administrator, 2 = Speler
	private String status; //1 = Actief, 2 = Inactief, 3 = Opgezegd
	private String emailadres;
	private String wachtwoord;
	private String voornaam;
	private String tussenvoegsel = "";
	private String achternaam;
	private String woonplaats;
	private String straat;
	private int huisnummer;
	private String postcode;
	
	public Gebruiker(int _id, String _rol, String _status, String _emailadres, String _wachtwoord, String _voornaam,
	String _tussenvoegsel, String _achternaam, String _woonplaats, String _straat, int _huisnummer, String _postcode){
		id = _id;
		rol = _rol;
		status = _status;
		emailadres = _emailadres;
		wachtwoord = _wachtwoord;
		voornaam = _voornaam;
		tussenvoegsel = _tussenvoegsel;
		achternaam = _achternaam;
		woonplaats = _woonplaats;
		straat = _straat;
		huisnummer = _huisnummer;
		postcode = _postcode;
	}
	
	//Het tussenvoegsel hoeft niet te worden meegegeven.
	public Gebruiker(int _id, String _rol, String _status, String _emailadres, String _wachtwoord, String _voornaam,
	String _achternaam, String _woonplaats, String _straat, int _huisnummer, String _postcode){
		id = _id;
		rol = _rol;
		status = _status;
		emailadres = _emailadres;
		wachtwoord = _wachtwoord;
		voornaam = _voornaam;
		achternaam = _achternaam;
		woonplaats = _woonplaats;
		straat = _straat;
		huisnummer = _huisnummer;
		postcode = _postcode;
	}
	
	public int getId(){
		return id;
	}
	
	public void setId(int _id) {
		id = _id;
	}
	
	public String getRol(){
		return rol;
	}
	
	public void setRol(String _rol) {
		rol = _rol;
	}
	
	public String getStatus(){
		return status;
	}
	
	public void setStatus(String _status) {
		status = _status;
	}
	
	public String getEmailadres(){
		return emailadres;
	}
	
	public void setEmailadres(String _emailadres) {
		this.emailadres = _emailadres;
	}
	
	public String getWachtwoord(){
		return wachtwoord;
	}
	
	public void setWachtwoord(String _wachtwoord) {
		wachtwoord = _wachtwoord;
	}
	
	public String getVoornaam(){
		return voornaam;
	}
	
	public void setVoornaam(String _voornaam) {
		voornaam = _voornaam;
	}
	
	public String getTussenvoegsel(){
		return tussenvoegsel;
	}
	
	public void setTussenvoegsel(String _tussenvoegsel) {
		tussenvoegsel = _tussenvoegsel;
	}
	
	public String getAchternaam(){
		return achternaam;
	}
	
	public void setAchternaam(String _achternaam) {
		achternaam = _achternaam;
	}
	
	public String getWoonplaats(){
		return woonplaats;
	}
	
	public void setWoonplaats(String _woonplaats) {
		woonplaats = _woonplaats;
	}
	
	public String getStraat(){
		return straat;
	}
	
	public void setStraat(String _straat) {
		straat = _straat;
	}
	
	public int getHuisnummer(){
		return huisnummer;
	}
	
	public void setHuisnummer(int _huisnummer) {
		huisnummer = _huisnummer;
	}

	public String getPostcode(){
		return postcode;
	}
	
	public void setPostcode(String _postcode) {
		postcode = _postcode;
	}
	
	public String toString(){
		return "id: "+ id +", rol: "+ rol +", status: "+ status +", emailadres: "+ emailadres +", wachtwoord: "+ wachtwoord +", voornaam: "+
		voornaam +", tussenvoegsel: "+ tussenvoegsel +", achternaam: "+ achternaam +", woonplaats: "+ woonplaats +", straat: "+
		straat +", huisnummer: "+ huisnummer +", postcode: "+ postcode;
	}

}
