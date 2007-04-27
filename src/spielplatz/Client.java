package spielplatz;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import spielplatz.hilfsklassen.ChatNachricht;
import spielplatz.hilfsklassen.Nachricht;
import spielplatz.hilfsklassen.Registrierung;
import spielplatz.hilfsklassen.SpielStartNachricht;

public class Client {
	private EndPunkt server;
	private Empfaenger briefkasten;
	private String serverHostname;

	private Client(String serverHostname) throws RemoteException, NotBoundException, AlreadyBoundException {
		this.serverHostname = serverHostname;
	}
	
	private void run() throws RemoteException, IOException, AlreadyBoundException, NotBoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		System.out.print("Spielername: ");
		String spielerName = in.readLine();
		
		briefkasten = new Empfaenger(spielerName, false, serverHostname);
		
		System.out.println("Hole Server");
		/* handler des servers */
		server = briefkasten.schlageNach("server");
		System.out.println("Hab Server");
		
		System.out.println("Sende Server..." );
		System.out.println(server.briefkasten);
		server.sende(new Registrierung(spielerName, briefkasten.stub));
		
		System.out.println("sendung abgeschickt...");
		Nachricht n;
		while ((n = briefkasten.getNaechsteNachricht()) != null) {
			if (n instanceof ChatNachricht) {
				System.out.println("ChatNachricht: " + n);
			} else if (n instanceof SpielStartNachricht) {
				System.out.println("Spiel startet!");
				break;
			}
		}
		
		
		while (true) {
			System.out.println("Client: Lese Eingabe ein...");
			String eingabe = in.readLine();
			System.out.println("Client: Eingelesen: (" + eingabe + ")");
			server.sende(new ChatNachricht(eingabe));
			
			while ((n = briefkasten.getNaechsteNachricht(false)) != null) {
				if (n instanceof ChatNachricht) {
					System.out.println(n);
				} else {
					throw new RuntimeException("Unbekannte Nachricht");
				}
			}
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
