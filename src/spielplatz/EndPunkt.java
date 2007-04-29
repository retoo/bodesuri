package spielplatz;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import spielplatz.hilfsklassen.Nachricht;

public class EndPunkt {
	Socket socket;
	private Thread empfaengerThread;
	private ObjectOutputStream outputStream;
	private Empfaenger empfaenger;
	public Briefkasten briefkasten;
	
	
	/**
	 * Startet die Kommunkation mit dem übergebenen Socket. Dieser Konstrutkur wird 
	 * vom Server verwendet.
	 * 
	 * TODO: prüfen ob man evtl. zwei Subklassen anstatt zwei Konstruktoren verwenden sollte
	 * 
	 * @param socket zu verwendendender Socket
	 * @param briefkasten Briefkasten in welchem die Nachrichten abgelegt werden können
	 * @throws IOException
	 */
	public EndPunkt(Socket socket, Briefkasten briefkasten) throws IOException {
		this.briefkasten = briefkasten;
		this.socket = socket;
		
		startVerhandlung();
	}
	
	/**
	 * Startet die Kommunikation mit dem übergebenen System (Hostname & Port). Dieser
	 * Konstrutkur wird vom Client verwendet.
	 * 
	 * TODO: prüfen ob man evtl. zwei Subklassen anstatt zwei Konstruktoren verwenden sollte
	 * 
	 * @param hostname Hostname des zu verbindenen Systems
	 * @param port Port des zu verbinden Systems
	 * @throws UnknownHostException 
	 * @throws IOException
	 */
	public EndPunkt(String hostname, int port) throws UnknownHostException, IOException {
		socket = new Socket(hostname, port);
		
		 /* Buffer für eingehende Nachrichten */
		briefkasten = new Briefkasten();
		
		startVerhandlung();
	}
	
	private void startVerhandlung() throws IOException {
		outputStream = new ObjectOutputStream(socket.getOutputStream());
		
		
		/* Bis auf weiteres machen wir das Empfanger der Nachrichten mal in einem seperaetn
		 * Thread. Später kann man das evtl. mit Select etc. besser lösen
		 */
		empfaenger = new Empfaenger(this, briefkasten);
		empfaengerThread = new Thread(empfaenger);
		empfaengerThread.start();
	}
	
	
	/**
	 * Sende die angegebene Nachricht an den Host auf der anderen Seite des Kommunikationkanals.
	 * 
	 * @param nachricht zu übertragende Nachricht
	 */
	public void sende(Nachricht nachricht) {
		try {
			outputStream.writeObject(nachricht);
		} catch (IOException e) {
			// TODO Sinnvolles Handling bei Fehlern
			e.printStackTrace();
		}		
	}

	public void ausschalten() throws IOException {
		socket.shutdownInput();
	}
}
