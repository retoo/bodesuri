package spielplatz;

import java.io.IOException;
import java.util.Vector;

import pd.spielerverwaltung.Spieler;
import dienste.netzwerk.Brief;
import dienste.netzwerk.Briefkasten;
import dienste.netzwerk.Daemon;
import dienste.netzwerk.nachrichten.ChatNachricht;
import dienste.netzwerk.nachrichten.NeueVerbindung;
import dienste.netzwerk.nachrichten.SpielStartNachricht;
import dienste.netzwerk.nachrichten.VerbindungWegException;

@Deprecated
public class Server {
	private static final int PORT = 3334;
	
	@Deprecated
	public static void main(String[] args) throws IOException, ClassNotFoundException, VerbindungWegException {		
		Briefkasten serverBriefkasten = new Briefkasten();
		
		Daemon a = new Daemon(PORT, serverBriefkasten);
		
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
