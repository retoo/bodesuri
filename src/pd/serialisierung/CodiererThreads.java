package pd.serialisierung;

import java.util.HashMap;
import java.util.Map;

import dienste.serialisierung.Codierer;

/**
 * Speichert Threads und die dazu zugewiesenen Codierer.
 */
public class CodiererThreads {
	private static Map<Thread, Codierer> codiererThreads = new HashMap<Thread, Codierer>();

	/**
	 * Registriere einen Thread mit dem dazugehörigen Codierer.
	 *
	 * @param thread Thread, der registriert werden soll
	 * @param codierer Codierer, der für den Thread verwendet werden soll
	 */
	public static void registriere(Thread thread, Codierer codierer) {
		if (codierer == null) {
			throw new RuntimeException("Registrierter Codierer darf nicht null sein");
		}
		codiererThreads.put(thread, codierer);
	}

	/**
	 * @param thread Thread, dessen Codierer gefragt ist
	 * @return Codierer, der zum Thread gehört
	 */
	public static Codierer getCodierer(Thread thread) {
		return codiererThreads.get(thread);
	}
}
