package spielplatz;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;

import spielplatz.hilfsklassen.Brief;
import spielplatz.hilfsklassen.Nachricht;

public class Empfaenger implements Runnable{
	Briefkasten nachrichtenQueue;
	private ObjectInputStream inputStream;
	boolean isGeschlossen = false;
	private EndPunkt endpunkt;

	public Empfaenger(EndPunkt endpunkt, Briefkasten briefkasten) throws IOException {
		inputStream = new ObjectInputStream(endpunkt.socket.getInputStream());
		this.endpunkt = endpunkt;
		this.nachrichtenQueue = briefkasten;
	}

	public void run() {
		try {
			Object obj; 
			while ( ( obj = inputStream.readObject()) != null) {
				Nachricht nachricht = (Nachricht) obj;
				Brief brief = new Brief(endpunkt, nachricht);
				
				nachrichtenQueue.einwerfen(brief);
			}			
		} catch (EOFException eof) {
			/* TODO: Nicht sicher ob das so gut */
			isGeschlossen = true;
			return;
		} catch (IOException e) {
			// TODO Sinnvolles Handling bei Fehlern
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Sinnvolles Handling bei Fehlern
			e.printStackTrace();
		}
		
	}
}
