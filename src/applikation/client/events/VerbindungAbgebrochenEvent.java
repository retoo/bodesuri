package applikation.client.events;

import dienste.eventqueue.Event;

/**
 * Event der von
 * {@link applikation.client.pd.Spiel#sende(dienste.netzwerk.Nachricht)}
 * ausgelöst wird wenn die Verbindung abgebrochen ist.
 */
public class VerbindungAbgebrochenEvent extends Event {
}
