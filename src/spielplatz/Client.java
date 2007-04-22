package spielplatz;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import spielplatz.hilfsklassen.ChatNachricht;
import spielplatz.hilfsklassen.Nachricht;

public class Client {
	private EndPunkt server;
	private Briefkasten briefkasten;

	private Client() throws RemoteException, NotBoundException, AlreadyBoundException {
		
		briefkasten = new Briefkasten(this.toString(), false);
		/* handler des servers */
		server = briefkasten.schlageNach("server");
		
		briefkasten.registriereBei(server);
	}
	
	private void run() throws RemoteException {		
		server.sendeNachricht(new ChatNachricht("hallo du"));
		
		Nachricht n;
		
		while ( ( n = briefkasten.getNächsteNachricht()) != null) {
			System.out.println("Client: Bekam Nachricht: " + n);			
		}
	}

	public static void main(String[] args) throws RemoteException, NotBoundException, AlreadyBoundException, InterruptedException {
		Client c = new Client();
		
		c.run();
	}
}
