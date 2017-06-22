package domain;

public class Team {
	private int id;
	private String naam;

	public Team(int _id, String _naam){
		id = _id;
		naam = _naam;
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
	
	public String toString(){
		return "id: "+ id +", naam: "+ naam;
	}
	
}
