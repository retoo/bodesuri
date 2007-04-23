package spielplatz;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Vector;

import PD.Spielerverwaltung.Spieler;

import spielplatz.hilfsklassen.ChatNachricht;
import spielplatz.hilfsklassen.Nachricht;
import spielplatz.hilfsklassen.Registrierung;

public class Server {
	private Briefkasten briefkasten;


	public Server() throws RemoteException, AlreadyBoundException {
		/* briefkasten für eingehende Nachrichten */
		briefkasten = new Briefkasten("server", true);
	}

	private void run() throws RemoteException, NotBoundException {
		Nachricht n;

		Vector<Spieler> spieler = new Vector<Spieler>();

		/* Spielstart */
		while ( ( n = briefkasten.getNächsteNachricht()) != null) {
			/* Ah ein Nachbar stelllt sich vor, nehmen wir den 
			 * in unser Adressbuch rein 
			 */
			if (n instanceof Registrierung) {
				Registrierung reg = (Registrierung) n;
				EndPunkt client = briefkasten.schlageNach(reg.name);
				
				Spieler neuerSpieler = new Spieler(client, reg.name);
				
				for (Spieler s : spieler) {
					s.endpunkt.sende(new ChatNachricht("Neuer Spieler " + neuerSpieler));
				}
				
				spieler.add(neuerSpieler);
			} else {
				throw new RuntimeException("Unbekannte Nachricht " + n);
			}
			

			
			if (spieler.size() == 2) {
				break;
			}
		}
		
		System.out.println("Spiel komplett, Spieler sind: ");
		
		for (Spieler s : spieler) {
			System.out.println(" - " + s);
			
		}

		System.out.println("out of scope");
	}

	public static void main(String args[]) throws RemoteException, AlreadyBoundException, InterruptedException, NotBoundException  {
		Server server = new Server();
		server.run();
	}
}
