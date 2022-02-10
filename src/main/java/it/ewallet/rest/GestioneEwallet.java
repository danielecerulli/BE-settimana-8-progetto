package it.ewallet.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
		return Response.status(200).entity("Eliminazione ContoCorrente avvenuta con successo/ IBAN eliminato: " + iban).build();
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
		return Response.status(200).entity("Apertura Conto avvenuta con successo Conto: " + c.getIban() + " Intestatario: " + c.getIntestatario() + " Data apertura: " + c.getDataApertura()).build();
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
		double nS = 0;
		ContoCorrente cc = null;
		for (ContoCorrente c : conti ) {
			if (c.getIban() == iban) {
				double nSaldo = c.getSaldo() + importo;
				nS = nSaldo;
				c.setSaldo(nSaldo);
				Movimento m = new Movimento();
				m.setIbanM(iban);
				m.setData(dataM);
				m.setTipo("versamento");
				m.setImporto(importo);
				movimenti.add(m);
				cc = c;
			}
			
		}
		 
		return Response.status(200).entity("Versamento avvenuto con successo | importo versato: " + importo + " iban Conto: " + iban + " intestatario: " + cc.getIntestatario() + " nuovo saldo: " + nS + " Data: " + dataM).build();
	}
	
	@PUT
	@Path("/preleva/{importo}/{iban}/{dataM}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response preleva(@PathParam("iban")int iban, @PathParam("dataM")String dataM, @PathParam("importo")double importo) {		
		double nS = 0;
		ContoCorrente cc = null;
		for (ContoCorrente c : conti ) {
			if (c.getIban() == iban) {
				double nSaldo = c.getSaldo() - importo;
				nS = nSaldo;
				c.setSaldo(nSaldo);
				Movimento m = new Movimento();
				m.setIbanM(iban);
				m.setData(dataM);
				m.setTipo("prelievo");
				m.setImporto(importo);
				movimenti.add(m);
				cc = c;
			}
		
		}
		return Response.status(200).entity("Prelievo avvenuto con successo | importo prelevato: " + importo + " iban Conto: " + iban + " intestatario: " + cc.getIntestatario() + " nuovo saldo: " + nS + " Data: " + dataM).build();
	}
	
	@GET
	@Path("/trovamovimentiiban/{ibanM}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Movimento> trovaMovimentiByIban(@PathParam("ibanM")int ibanM) {
		//ArrayList<Movimento> movimentiTmp = new ArrayList<Movimento>();
		// THIS IS OLDSTYLE
		/*for (Movimento m : movimenti) {
			if ( m.getIbanM() == ibanM ) {
				movimentiTmp.add(m);
			}
		
		}*/
		//LAMBDA STYLE
		/*movimenti.forEach( (m) -> {
			if ( m.getIbanM() == ibanM ) {
				movimentiTmp.add(m);
			}
		});
		System.out.println("Versione Lambda .forEach()");*/
		
		//LAMBDA CON STREAM e .filter (LA MORTE SUA) 		 											//prendo array movimenti lo fa diventare uno stream, utilizziamo il filter
		return movimenti.stream().filter( m -> m.getIbanM() == ibanM ).collect(Collectors.toList());	// per discriminare gli elementi che ci interessano (predicato o funzione lambda)
																										//utilizziamo collect(Collectors.toList) per aggiungere gli elementi trovati in una lista
	}
	
	public static void stampaListaMovimenti(Movimento m) {	
			System.out.println("tipo mov: " + m.getTipo());
		
	}
	
	@POST
	@Path("/inviocontimassivo")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response invioCcMassivo(List<ContoCorrente> listaC) {				
		conti.addAll(listaC);
		List<Double> saldiConti = restituisciSaldiContiCorrente();
		double saldoTot = saldiConti.stream().reduce( 0.0, (sommaParziale, saldoCorrente) -> sommaParziale + saldoCorrente ); //reduce: parametroIniziale, (sommaParz, elementoCorr) restituisce un solo risultato
		System.out.println("Saldo Tot conti: " + saldoTot );
		saldiConti.stream().forEach( saldo -> System.out.println("Saldo: " + saldo) ); //forEach
		return Response.status(200).entity("Invio Conti avvenuta con successo!").build();
	
	}
	@POST
	@Path("/inviomovimentimassivo")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response invioMovMassivo(List<Movimento> listaM) {
		//movimenti.forEach(GestioneEwallet::stampaListaMovimenti);
		movimenti.forEach(System.out::println);
		movimenti.addAll(listaM);
		return Response.status(200).entity("Invio Movimenti avvenuta con successo!").build();
	}
	
	public List<Double> restituisciSaldiContiCorrente() { 								// il map prende nello stream di ingresso elementi di un certo tipo
		return conti.stream().map( c -> c.getSaldo() ).collect(Collectors.toList());
																							// estrae un attributo per poi effettuare operazioni su esso/essi oppure restituire quell'unico attributo
	}
	
}
