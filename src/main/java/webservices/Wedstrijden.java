package webservices;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

import java.util.ArrayList;
import java.util.Date;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.*;

import domain.Wedstrijd;
import persistence.WedstrijdDAOImp;
import static java.time.temporal.TemporalAdjusters.*;

import java.security.NoSuchAlgorithmException;
import java.time.temporal.TemporalAdjusters;

@Path("/wedstrijden")
public class Wedstrijden {
	public Wedstrijden() {}
	
	@GET
	@Path("/getwedstrijden/{periode}/{openbaar}")
	@Produces("application/json")
    public String getWedstrijden(@PathParam("periode") String periode, @PathParam("openbaar") String openbaar) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		JsonArrayBuilder jab = Json.createArrayBuilder();
		JsonObjectBuilder job = Json.createObjectBuilder();
		WedstrijdDAOImp wdi = new WedstrijdDAOImp();

		Boolean jaar = periode.equals("jaar");
		SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM (HH:mm)");
		Date starttijd;
		Date eindtijd;
		
		if(jaar == true){ //Alle wedstrijden van het jaar
			LocalDate vandaag = LocalDate.now();
			LocalDate eerstedagvanjaar = vandaag.with(firstDayOfYear());
			LocalDate laatstedagvanjaar = vandaag.with(lastDayOfYear());
			starttijd = java.sql.Date.valueOf(eerstedagvanjaar);
			eindtijd = java.sql.Date.valueOf(laatstedagvanjaar);
		}
		else{ //Alleen recentelijke wedstrijden
			LocalDate begin = LocalDate.now().withDayOfMonth(1);
			LocalDate eind = LocalDate.now().plusMonths(1).with(TemporalAdjusters.lastDayOfMonth());
			starttijd = java.sql.Date.valueOf(begin);
			eindtijd = java.sql.Date.valueOf(eind);	
		}

		Boolean alleen_openbaar = openbaar.equals("prive") ? false : true;

		ArrayList<Wedstrijd> wedstrijden = wdi.getWedstrijden(starttijd, eindtijd, alleen_openbaar);

		for(Wedstrijd x: wedstrijden){
			String toernooi = x.getToernooi() != null ? x.getToernooi().getNaam() : "";
			job.add("id", x.getId());
			job.add("type", x.getType());
			job.add("starttijd", sdf.format(x.getStarttijd()));
			job.add("eindtijd", sdf.format(x.getEindtijd()));
			job.add("aanvaller", x.getAanvaller().getNaam());
			job.add("verdediger", x.getVerdediger().getNaam());
			job.add("eindstand", x.getAanvaller_score() +"-"+ x.getVerdediger_score());
			job.add("toernooi", toernooi);
			jab.add(job);
		}

		JsonArray array = jab.build();
		return array.toString();
    }
	
	@DELETE
	@Path("verwijder/{id}")
	public void verwijderWedstrijd(@PathParam("id") int id) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, NoSuchAlgorithmException {
		WedstrijdDAOImp wdi = new WedstrijdDAOImp();
		wdi.deleteWedstrijd(id);
	}
	
}



