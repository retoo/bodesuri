package dienste.netzwerk;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import dienste.netzwerk.nachrichten.Nachricht;
import dienste.netzwerk.nachrichten.VerbindungGeschlossen;


public class Empfaenger implements Runnable{
	Briefkasten nachrichtenQueue;
	private ObjectInputStream inputStream;
	private EndPunkt endpunkt;

	public Empfaenger(EndPunkt endpunkt, Socket socket, Briefkasten briefkasten) throws IOException {
		inputStream = new ObjectInputStream(socket.getInputStream());
		this.endpunkt = endpunkt;
		this.nachrichtenQueue = briefkasten;
	}

	public void run() {
		try {
		
			while (true) {
				Object obj = inputStream.readObject();
				
				if (obj == null) 
					throw new RuntimeException("FIXME FIXME");
				
				Nachricht nachricht = (Nachricht) obj;
				Brief brief = new Brief(endpunkt, nachricht);
				
				nachrichtenQueue.einwerfen(brief);
			}			
		} catch (EOFException eof) {
			/* TODO: Nicht sicher ob das so gut */
			
			Brief brief = new Brief(endpunkt, new VerbindungGeschlossen());
			nachrichtenQueue.einwerfen(brief);
			return;
		} catch (IOException e) {
			/* mir ist nicht ganz bekannt wann in welchen legitimen Fällen die Exception 
			 * auftauchen kann... falls dieser Fehler auftaucht (z.B. bei 'normalen' Verbindungs
			 * Problemen) dann muss das Handling dem vom EOFError angepasst werden (siehe oben).
			 */
			System.out.println("IOException im Empfänger (Endpunkt: " + endpunkt + ")");
			e.printStackTrace();
			System.exit(99);
			
		} catch (ClassNotFoundException e) {
			/* der Client kannte die Klasse nicht die übertragen wurde, fehler schwerler */
			System.out.println("ClassNotFoundException im Empfänger (Endpunkt: " + endpunkt + ")");
			e.printStackTrace();
			System.exit(99);
		}
		
	}
}
