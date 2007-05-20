package dienste.netzwerk;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;



public class EndPunkt {
	private Socket socket;
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
		System.out.println("Verbinde zu " + hostname + ":" + port);
		
		socket = new Socket(hostname, port);
		
		 /* Buffer für eingehende Nachrichten */
		briefkasten = new Briefkasten();
		
		startVerhandlung();
		
		System.out.println("Verbindungsaufbau erfolgreich");
	}
	
	private void startVerhandlung() throws IOException {
		outputStream = new ObjectOutputStream(socket.getOutputStream());
		
		
		/* Bis auf weiteres machen wir das Empfanger der Nachrichten mal in einem seperaetn
		 * Thread. Später kann man das evtl. mit Select etc. besser lösen
		 */
		empfaenger = new Empfaenger(this, socket, briefkasten);
		empfaengerThread = new Thread(empfaenger);
		empfaengerThread.start();
	}
	
	
	/**
	 * Sende die angegebene Nachricht an den Host auf der anderen Seite des Kommunikationkanals.
	 * 
	 * @param nachricht zu übertragende Nachricht
	 * @throws VerbindungWegException 
	 */
	public void sende(Nachricht nachricht) throws VerbindungWegException {
		try {
			outputStream.writeObject(nachricht);
		} catch (IOException e) {
			throw new VerbindungWegException();
		}	

	}
	
	public void ausschalten() throws IOException {
		socket.shutdownInput();
		socket.shutdownOutput();
	}
	
	public String toString() {
		InetAddress addr = socket.getInetAddress();
		return addr.getHostAddress() + ":" + socket.getPort();
	}
}
