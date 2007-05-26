package dienste.serialisierung;

import java.util.HashMap;
import java.util.Map;

/**
 * Codierer, den es pro {@link Spiel} einmal gibt und der die Abbildung von
 * Codes zu Objekten verwaltet.
 * 
 * Jedes codierbare Objekt hat einen eindeutigen Code zugeordnet. Wenn das
 * Objekt serialisiert wird, wird nicht das Objekt selber geschrieben, sondern
 * ein codiertes Objekt (ein Ersatzobjekt) mit einem Code.
 * 
 * Das codierte Objekt wird übermittelt und auf einem anderen Rechner wird es
 * decodiert, indem in der lokalen Tabelle das zu diesem Code gespeicherte
 * Objekt hervorgeholt wird.
 * 
 * Das urprünglich versendete Objekt entspricht dem auf dem Empfänger, da die
 * Tabelle gleich aufgebaut wurde.
 */
public class Codierer {
	private Map<String, CodierbaresObjekt> objekte;
	
	/**
	 * Erstellt einen Codierer.
	 */
	public Codierer() {
		objekte = new HashMap<String, CodierbaresObjekt>();
	}
	
	/**
	 * Speichert einen Code und das zugehörige codierbare Objekt.
	 * 
	 * @param code Eindeutiger Code des Objekts
	 * @param objekt Zugehöriges Objekt, welches codierbar ist
	 */
	public void speichere(String code, CodierbaresObjekt objekt) {
		objekte.put(code, objekt);
	}
	
	/**
	 * Gibt von einem Code das zugehörige Objekt zurück.
	 * 
	 * @param code Code des Objekts
	 * @return Objekt, das mit dem Code gespeicher wurde
	 */
	public CodierbaresObjekt get(String code) {
		return objekte.get(code);
	}
}
