package spielplatz;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import spielplatz.hilfsklassen.Nachricht;
import spielplatz.hilfsklassen.Registrierung;

public class Client {
	private EndPunkt server;
	private Briefkasten briefkasten;

	private Client(String name) throws RemoteException, NotBoundException, AlreadyBoundException {
	}
	
	private void run() throws RemoteException, IOException, AlreadyBoundException, NotBoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		System.out.print("Spielername: ");
		String spielerName = in.readLine();
		
		briefkasten = new Briefkasten(spielerName, false);
		
		/* handler des servers */
		server = briefkasten.schlageNach("server");
		
		server.sende(new Registrierung(spielerName));
		
		Nachricht n;
		while ((n = briefkasten.getNächsteNachricht()) != null) {
			System.out.println("Client: " + n);
		}
	}

	public static void main(String[] args) throws RemoteException, NotBoundException, AlreadyBoundException, InterruptedException, IOException {
		String name = null;
		if ( args.length > 0) {
			name = args[0];
		}
		
		Client c = new Client(name);			
		c.run();
	}
}
