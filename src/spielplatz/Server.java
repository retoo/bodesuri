package spielplatz;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Vector;

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

		Vector<EndPunkt> spieler = new Vector<EndPunkt>();

		/* Spielstart */
		while ( ( n = briefkasten.getNächsteNachricht()) != null) {
			/* Ah ein Nachbar stelllt sich vor, nehmen wir den 
			 * in unser Adressbuch rein 
			 */
			if (n instanceof Registrierung) {
				Registrierung reg = (Registrierung) n;
				EndPunkt client = briefkasten.schlageNach(reg.name);
				
				spieler.add(client);
			} else {
				throw new RuntimeException("Unbekannte Nachricht " + n);
			}
			
			for (EndPunkt s : spieler) {
				s.sende(new ChatNachricht("Neuer Spieler" + s));
			}
			
			if (spieler.size() == 2) {
				break;
			}
		}
		
		System.out.println("Spiel komplett, Spieler sind: ");
		
		for (EndPunkt s : spieler) {
			System.out.println(" - " + s);
			
		}

		System.out.println("out of scope");
	}

	public static void main(String args[]) throws RemoteException, AlreadyBoundException, InterruptedException, NotBoundException  {
		Server server = new Server();
		server.run();
	}
}
