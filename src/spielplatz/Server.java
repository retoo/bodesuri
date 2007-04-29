package spielplatz;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Vector;

import pd.spielerverwaltung.Spieler;
import spielplatz.hilfsklassen.Brief;
import spielplatz.hilfsklassen.ChatNachricht;
import spielplatz.hilfsklassen.NeueVerbindung;
import spielplatz.hilfsklassen.SpielStartNachricht;

public class Server {
	private static final int PORT = 3334;

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		
		ServerSocket sock = new ServerSocket(PORT);
		
		Briefkasten serverBriefkasten = new Briefkasten();
		Akzeptierer a = new Akzeptierer(sock, serverBriefkasten);
		
		Thread t = new Thread(a);
		t.start();
		
		
		Brief brief;

		Vector<Spieler> spieler = new Vector<Spieler>();

		/* Spielstart */
		while ( ( brief = serverBriefkasten.getBrief()) != null) {
			/* Ah ein Nachbar stelllt sich vor, nehmen wir den 
			 * in unser Adressbuch rein 
			 */
			if (brief.nachricht instanceof NeueVerbindung) {
				Spieler neuerSpieler = new Spieler(brief.absender, brief.absender.toString());
				
				spieler.add(neuerSpieler);
				
				for (Spieler s : spieler) {
					s.endpunkt.sende(new ChatNachricht("Neuer Spieler " + neuerSpieler));
				}
				
			} else {
				throw new RuntimeException("Unbekannte Nachricht " + brief);
			}
			
			if (spieler.size() == 2) {
				break;
			}
		}
		
		System.out.println("Spiel komplett, Spieler sind: ");
		
		for (Spieler s : spieler) {
			System.out.println(" - " + s);
		}
		
		for (Spieler s : spieler) {
			s.endpunkt.sende(new SpielStartNachricht());
		}
		
		while ( ( brief = serverBriefkasten.getBrief()) != null) {
			if (brief.nachricht instanceof ChatNachricht) {
				System.out.println("Chat: " + brief);
				
				for (Spieler s : spieler) {
					System.out.println("Sende Nachricht: (" + brief + ") an (" + s + ")");
					s.endpunkt.sende(brief.nachricht);
				}
			} else {
				throw new RuntimeException("Unbekannte Nachricht " + brief);
			}
		}
	}
}
