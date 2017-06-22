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
import domain.Agenda;
import persistence.AgendaDAOImp;

import static java.time.temporal.TemporalAdjusters.*;

import java.security.NoSuchAlgorithmException;
import java.time.temporal.TemporalAdjusters;

@Path("/evenementen")
public class Evenementen {
	public Evenementen() {}
	
	@GET
	@Path("/getevenementen/{periode}/{openbaar}")
	@Produces("application/json")
	public String getEvenementen(@PathParam("periode") String periode, @PathParam("openbaar") String openbaar) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		JsonArrayBuilder jab = Json.createArrayBuilder();
		JsonObjectBuilder job = Json.createObjectBuilder();
		AgendaDAOImp adi = new AgendaDAOImp();

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

		ArrayList<Agenda> agendapunten = adi.getAgendaPunten(starttijd, eindtijd, alleen_openbaar);

		for(Agenda x: agendapunten){
			job.add("id", x.getId());
			job.add("naam", x.getEvenement());
			job.add("starttijd", sdf.format(x.getStarttijd()));
			job.add("eindtijd", sdf.format(x.getEindtijd()));
			jab.add(job);
		}

		JsonArray array = jab.build();
		return array.toString();
	}
	
	@DELETE
	@Path("verwijder/{id}")
	public void verwijderEvenement(@PathParam("id") int id) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, NoSuchAlgorithmException {
		AgendaDAOImp adi = new AgendaDAOImp();
		adi.deleteAgendaPunt(id);
	}
	
}
