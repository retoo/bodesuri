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

		Vector<EndPunkt> clients = new Vector<EndPunkt>();

		while ( ( n = briefkasten.getNächsteNachricht()) != null) {
			/* Ah ein Nachbar stelllt sich vor, nehmen wir den 
			 * in unser Adressbuch rein 
			 */
			if (n instanceof Registrierung) {
				Registrierung reg = (Registrierung) n;
				EndPunkt client = briefkasten.schlageNach(reg.name);

				System.out.println("ah.. guten tag herr " + client);

				client.sendeNachricht(new ChatNachricht("Hallo kleiner " + client));
				clients.add(client);
			} else {
				System.out.println("Bekam ne Nachricht" + n);

				for (EndPunkt client : clients) {
					client.sendeNachricht(n);
				}				
			}
		}

		System.out.println("out of scope");
	}

	public static void main(String args[]) throws RemoteException, AlreadyBoundException, InterruptedException, NotBoundException  {
		Server server = new Server();
		server.run();
	}
}
