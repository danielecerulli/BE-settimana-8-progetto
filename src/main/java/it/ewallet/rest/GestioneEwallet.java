package it.ewallet.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import it.ewallet.pojo.ContoCorrente;
import it.ewallet.pojo.Movimento;

@Path("/conto")
public class GestioneEwallet {
	
	private static List<ContoCorrente> conti = new ArrayList<ContoCorrente>();
	private static List<Movimento> movimenti = new ArrayList<Movimento>();
	
	@GET
	@Path("/vediconti")
	@Produces(MediaType.APPLICATION_JSON)
	public List<ContoCorrente> retrieveConti() {
		
		return conti;
	}
	
	@GET
	@Path("/vedimovimenti")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Movimento> retrieveMovimenti() {
		
		return movimenti;
	}
	
	@DELETE
	@Path("/cancellaconto/{iban}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response cancellaConto(@PathParam("iban")int iban) {	
		for (ContoCorrente cont : conti) {
			if (cont.getIban() == iban) {
				conti.remove(cont);
				break;
			}			
		}		
		return Response.status(200).entity("Eliminazione ContoCorrente avvenuta con successo").build();
	}
	
	@DELETE
	@Path("/cancellamovimento/{ibanM}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response cancellaMovimento(@PathParam("dataM")int ibanM) {
		for (Movimento mov : movimenti ) {
			if (mov.getIbanM() == (ibanM)) {
				movimenti.remove(mov);
				break;
			}
		}
		return Response.status(200).entity("Eliminazione Movimento avvenuta con successo").build();
	}
	
	@POST
	@Path("/apriconto")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response apriConto(ContoCorrente c) {
		conti.add(c);
		return Response.status(200).entity("Apertura Conto avvenuta con successo").build();
	}
	
	@PUT
	@Path("/aggiornaconto")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response aggiornaConto(ContoCorrente c) {
		
		for (ContoCorrente cont : conti) {
			if (cont.getIban() == c.getIban()) {
				int index = conti.lastIndexOf(cont);
				conti.set(index, c);
			}
		}	
		return Response.status(200).entity("Aggiornamento Conto avvenuto con successo").build();		
	}
	
	@GET
	@Path("/cercaconto/{iban}")
	@Produces(MediaType.APPLICATION_JSON)
	public ContoCorrente retrieveByIban(@PathParam("iban")int iban) {
		ContoCorrente c = null;
		for (ContoCorrente cont : conti) {
			if (cont.getIban() == iban) {
				c = cont;
			}
		
		}	
		return c;
	}
	
	@PUT
	@Path("/versa/{importo}/{iban}/{dataM}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response versa(@PathParam("iban")int iban, @PathParam("dataM")String dataM, @PathParam("importo")double importo) {
		for (ContoCorrente c : conti ) {
			if (c.getIban() == iban) {
				double nSaldo = c.getSaldo() + importo;
				c.setSaldo(nSaldo);
				Movimento m = new Movimento();
				m.setIbanM(iban);
				m.setData(dataM);
				m.setTipo("versamento");
				m.setImporto(importo);
				movimenti.add(m);
			}
		
		}
		return Response.status(200).entity("Versamento avvenuto con successo").build();
	}
	
	@PUT
	@Path("/preleva/{importo}/{iban}/{dataM}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response preleva(@PathParam("iban")int iban, @PathParam("dataM")String dataM, @PathParam("importo")double importo) {
		for (ContoCorrente c : conti ) {
			if (c.getIban() == iban) {
				double nSaldo = c.getSaldo() - importo;
				c.setSaldo(nSaldo);
				Movimento m = new Movimento();
				m.setIbanM(iban);
				m.setData(dataM);
				m.setTipo("prelievo");
				m.setImporto(importo);
				movimenti.add(m);
			}
		
		}
		return Response.status(200).entity("Versamento avvenuto con successo").build();
	}
	
}
