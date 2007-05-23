package dienste.automat;

import java.util.IdentityHashMap;
import java.util.Map;

public class Automat {
	private Zustand start;
	private EventQuelle eventQuelle;
	private Map<Class<? extends Zustand>, Zustand> zustaende;

	public Automat() {
		zustaende = new IdentityHashMap<Class<? extends Zustand>, Zustand>();
	}

	protected void registriere(Zustand zustand) {
		zustand.setAutomat(this);
		zustaende.put(zustand.getClass(), zustand);
	}

	protected void setStart(Class<? extends Zustand> klasse) {
		start = getZustand(klasse);
	}

	protected void setEventQuelle(EventQuelle quelle) {
		eventQuelle = quelle;
	}

	public Zustand getZustand(Class<? extends Zustand> klasse) {
		Zustand zustand = zustaende.get(klasse);
		
		if (zustand == null) 
			throw new RuntimeException("Nichtregistierer Zustand " + klasse);
		
		return zustand;
	}

	public void run() {
		Zustand aktuellerZustand = start;

		while (true) {
			Zustand neuerZustand;
			
			System.out.println("Uebergang nach: " + aktuellerZustand);
			
			aktuellerZustand.init();
		
			if (aktuellerZustand instanceof AktiverZustand) {
				AktiverZustand zustand = (AktiverZustand) aktuellerZustand;
				
				Event event = eventQuelle.getEvent();
				
				neuerZustand = zustand.handle(event);
			} else {
				PassiverZustand zustand = (PassiverZustand) aktuellerZustand;
				neuerZustand = zustand.getNaechstenZustand();
			}
			aktuellerZustand = neuerZustand;
		}			
	}
}
