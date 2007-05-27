package applikation.server.nachrichten;

import dienste.netzwerk.EndPunkt;
import dienste.netzwerk.Nachricht;

/**
 * Nachricht die meldet, dass die Verbindung zu einem {@link EndPunkt}
 * unerwartet geschlossen wurde.
 * 
 * FIXME: gehört ins Dienste Package
 * 
 */
public class VerbindungGeschlossen extends Nachricht {
	private static final long serialVersionUID = 1L;
}
