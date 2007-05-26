package dienste.automat.zustaende;

import dienste.automat.Event;
import dienste.automat.UnbekannterEventException;

public abstract class AktiverZustand extends Zustand {
	public Zustand handle(Event event) {
		throw new UnbekannterEventException("Zustand " + this + " hat keinen Übergang für den Event " + event.getClass());
    }
}
