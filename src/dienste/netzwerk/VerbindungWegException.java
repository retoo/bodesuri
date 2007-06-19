package dienste.netzwerk;

import java.io.IOException;

/**
 * Meldet einen schweren Fehler in einer Netzwerkverbindung.
 */
public class VerbindungWegException extends RuntimeException {
	public VerbindungWegException(IOException e) {
	    super(e);
    }
}
