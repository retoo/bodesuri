package pd.serialisierung;

import java.util.HashMap;
import java.util.Map;

import dienste.serialisierung.Codierer;

public class CodiererThreads {
	private static Map<Thread, Codierer> codiererThreads = new HashMap<Thread, Codierer>();

	public static void registriere(Thread thread, Codierer codierer) {
		if (codierer == null) {
			throw new RuntimeException("Registriertes Spiel darf nicht null sein");
		}
		codiererThreads.put(thread, codierer);
	}

	public static Codierer getCodierer(Thread thread) {
		return codiererThreads.get(thread);
	}
}
