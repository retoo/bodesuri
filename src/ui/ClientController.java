package ui;

import applikation.client.BodesuriClient;
import applikation.client.zustaende.AmZug;
import applikation.client.zustaende.KarteWaehlen;
import applikation.client.zustaende.Lobby;
import applikation.client.zustaende.LobbyStart;
import applikation.client.zustaende.NichtAmZug;
import applikation.client.zustaende.ProgrammStart;
import applikation.client.zustaende.SchwererFehler;
import applikation.client.zustaende.SpielStart;
import applikation.client.zustaende.VerbindungErfassen;
import applikation.client.zustaende.VerbindungSteht;
import applikation.client.zustaende.Ziehen;
import dienste.automat.Automat;
import dienste.automat.EventQueue;
import dienste.automat.zustaende.Zustand;
import dienste.netzwerk.VerbindungWegException;
import java.util.Observable;
import java.util.Observer;

public class ClientController implements Observer {
	public ClientController() {
		
	}
	
	public static void main(String[] args) throws VerbindungWegException {
		Automat automat = new BodesuriClient( new EventQueue() );
		// ClientController controller = new ClientController();

		try {
			automat.run();
		} catch (Exception e) {
			/* Applikation stoppen wenn ein Fehler auftritt */
			e.printStackTrace();
			System.out.println("Client: Exception in run()");
			System.exit(99);
		}
	}

	public void update(Observable o, Object arg) {
		Automat automat = (BodesuriClient) o;
		Zustand aktuellerZustand = automat.getAktuellerZustand();
		
		// TODO: Wäre schön, wenn das mit Polymorphismus ginge
		if (aktuellerZustand instanceof AmZug) {
			handle( (AmZug)aktuellerZustand );
		} else if (aktuellerZustand instanceof KarteWaehlen) {
			handle( (KarteWaehlen)aktuellerZustand );
		} else if (aktuellerZustand instanceof Lobby) {
			handle( (Lobby)aktuellerZustand );
		} else if (aktuellerZustand instanceof LobbyStart) {
			handle( (LobbyStart)aktuellerZustand );
		} else if (aktuellerZustand instanceof NichtAmZug) {
			handle( (NichtAmZug)aktuellerZustand );
		} else if (aktuellerZustand instanceof ProgrammStart) {
			handle( (ProgrammStart)aktuellerZustand );
		} else if (aktuellerZustand instanceof SchwererFehler) {
			handle( (SchwererFehler)aktuellerZustand );
		} else if (aktuellerZustand instanceof SpielStart) {
			handle( (SpielStart)aktuellerZustand );
		} else if (aktuellerZustand instanceof VerbindungErfassen) {
			handle( (VerbindungErfassen)aktuellerZustand );
		} else if (aktuellerZustand instanceof VerbindungSteht) {
			handle( (VerbindungSteht)aktuellerZustand );
		} else if (aktuellerZustand instanceof Ziehen) {
			handle( (Ziehen)aktuellerZustand );
		} 
		
	}
		
	public void handle(AmZug aktuellerZustand) {
		
	}
	
	public void handle(KarteWaehlen aktuellerZustand) {
		
	}
	
	public void handle(Lobby aktuellerZustand) {
		
	}
	
	public void handle(LobbyStart aktuellerZustand) {
		
	}
	
	public void handle(NichtAmZug aktuellerZustand) {
		
	}
	
	public void handle(ProgrammStart aktuellerZustand) {
		
	}
	
	public void handle(SchwererFehler aktuellerZustand) {
		
	}
	
	public void handle(SpielStart aktuellerZustand) {
		
	}
	
	public void handle(VerbindungErfassen aktuellerZustand) {
		
	}
	
	public void handle(VerbindungSteht aktuellerZustand) {
		
	}
	
	public void handle(Ziehen aktuellerZustand) {
		
	}
}
