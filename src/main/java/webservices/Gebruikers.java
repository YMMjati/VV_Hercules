package webservices;

//import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.*;
import domain.Gebruiker;
import persistence.GebruikerDAOImp;

@Path("/gebruikers")
public class Gebruikers {
	public Gebruikers() {}
	
	@POST
	@Path("/inloggen")
	@Produces("application/json")
	public String Inloggen(@FormParam("email") String email, @FormParam("wachtwoord") String wachtwoord) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, NoSuchAlgorithmException{
		JsonArrayBuilder jab = Json.createArrayBuilder();
		JsonObjectBuilder job = Json.createObjectBuilder();
		GebruikerDAOImp gdi = new GebruikerDAOImp();
		
		Gebruiker gebruiker = gdi.verifyGebruiker(email, wachtwoord);

		if(gebruiker == null){
			job.add("id", "null");
			job.add("rol", "null");
			jab.add(job);
		}
		else{
			job.add("id", gebruiker.getId());
			job.add("rol", gebruiker.getRol());
			jab.add(job);
		}
	
		JsonArray array = jab.build();
		return array.toString();
	}
	
	@GET
	@Path("/account/{id}")
	@Produces("application/json")
	public String Accountgegevens(@PathParam("id") int id) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		JsonArrayBuilder jab = Json.createArrayBuilder();
		JsonObjectBuilder job = Json.createObjectBuilder();
		GebruikerDAOImp gdi = new GebruikerDAOImp();
		Gebruiker gebruiker = gdi.getGebruiker(id);

		if(gebruiker == null){
			job.add("voornaam", "");
			job.add("tussenvoegsel", "");
			job.add("achternaam", "");
			job.add("emailadres", "");
			job.add("woonplaats", "");
			job.add("straatnaam", "");
			job.add("huisnummer", "");
			job.add("postcode", "");
			jab.add(job);
		}
		else{
			job.add("voornaam", gebruiker.getVoornaam());
			job.add("tussenvoegsel", gebruiker.getTussenvoegsel());
			job.add("achternaam", gebruiker.getAchternaam());
			job.add("emailadres", gebruiker.getEmailadres());
			job.add("woonplaats", gebruiker.getWoonplaats());
			job.add("straatnaam", gebruiker.getStraat());
			job.add("huisnummer", gebruiker.getHuisnummer());
			job.add("postcode", gebruiker.getPostcode());
			jab.add(job);
		}

		JsonArray array = jab.build();
		return array.toString();
	}
	
	@PUT
	@Path("/account/wijzig_wachtwoord/{id}/{wachtwoord}")
	public void wijzigWachtwoord(@PathParam("id") int id, @PathParam("wachtwoord") String wachtwoord) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, NoSuchAlgorithmException {
		GebruikerDAOImp gdi = new GebruikerDAOImp();
		
		/*
		//Deze code dient om het wachtwoord te hashen zodat het vergeleken kan worden met een gehashed wachtwoord in de database.
		String salt = "ZH%5RMhqwEX25^cF";
		wachtwoord = wachtwoord + salt;
		MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
		messageDigest.update(wachtwoord.getBytes());
		String hashedwachtwoord = new String(messageDigest.digest());
		*/
		
		Gebruiker gebruiker = new Gebruiker(id, null, null, null, wachtwoord, null, null, null, null, 0, null);
		gdi.updateGebruiker(gebruiker);
	}
	
	@PUT
	@Path("/account/opzeggen/{id}")
	public void wijzigWachtwoord(@PathParam("id") int id) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, NoSuchAlgorithmException {
		GebruikerDAOImp gdi = new GebruikerDAOImp();
		Gebruiker gebruiker = new Gebruiker(id, null, "Opgezegd", null, null, null, null, null, null, 0, null);
		gdi.updateGebruiker(gebruiker);
	}
	
	@PUT
	@Path("/account/wijzigen/{id}/{emailadres}/{woonplaats}/{straatnaam}/{huisnummer}/{postcode}")
	public void wijzigGegevens(@PathParam("id") int id, @PathParam("emailadres") String emailadres, @PathParam("woonplaats") String woonplaats, @PathParam("straatnaam") String straatnaam, @PathParam("huisnummer") int huisnummer, @PathParam("postcode") String postcode) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, NoSuchAlgorithmException {
		GebruikerDAOImp gdi = new GebruikerDAOImp();
		Gebruiker gebruiker = new Gebruiker(id, null, null, emailadres, null, null, null, null, woonplaats, straatnaam, huisnummer, postcode);
		gdi.updateGebruiker(gebruiker);
	}

}
