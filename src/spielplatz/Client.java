package spielplatz;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import spielplatz.hilfsklassen.ChatNachricht;
import spielplatz.hilfsklassen.Nachricht;

public class Client {
	private EndPunkt server;
	private Briefkasten briefkasten;

	private Client(String name) throws RemoteException, NotBoundException, AlreadyBoundException {
		if (name == null) {
			name = this.toString();
		}
		
		briefkasten = new Briefkasten(name, false);
		
		/* handler des servers */
		server = briefkasten.schlageNach("server");
		
		/* dem server mitteilen wer wir sind */
		briefkasten.registriereBei(server);
	}
	
	private void run() throws RemoteException {		
		server.sende(new ChatNachricht("Wie gehts grosser Server?"));
		
		Nachricht n;
		
		while ( ( n = briefkasten.getNächsteNachricht()) != null) {
			System.out.println("Client: " + n);
		}
	}

	public static void main(String[] args) throws RemoteException, NotBoundException, AlreadyBoundException, InterruptedException {
		String name = null;
		if ( args.length > 0) {
			name = args[0];
		}
		
		Client c = new Client(name);			
		c.run();
	}
}
