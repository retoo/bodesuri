package pd;

import java.util.HashMap;
import java.util.Map;


public class SpielThreads {
	private static Map<Thread, Spiel> spiele = new HashMap<Thread, Spiel>();
	
	public static void registriere(Thread thread, Spiel spiel) {
		spiele.put(thread, spiel);
	}
	
	public static Spiel getSpiel(Thread thread) {
		return spiele.get(thread);
	}
}
