package pd.regelsystem;

import pd.spieler.Spieler;
import pd.zugsystem.Zug;
import pd.zugsystem.ZugEingabe;

// TODO: Gründe für RegelVerstoss vereinheitlichen.

/**
 * Basisklasse für Regeln. 
 */
public abstract class Regel {
	private String beschreibung;
	
	/**
	 * Validiere eine Zugeingabe. Bei einer gültigen Eingabe wird ein Zug
	 * zurückgegeben und sonst wird eine RegelVerstoss-Exception geworfen.
	 * 
	 * @param zugEingabe Zugeingabe, die validiert werden soll
	 * @return Zug, der dann ausgeführt werden kann
	 * @throws RegelVerstoss Tritt bei Regelwidrigkeit auf und enthält Grund
	 */
	public abstract Zug validiere(ZugEingabe zugEingabe) throws RegelVerstoss;

	/**
	 * Finde heraus, ob der Spieler mit dieser Regel noch ziehen kann, oder ob
	 * er aufgeben muss.
	 * 
	 * @param spieler Spieler, dessen Figuren überprüft werden
	 * @return true, wenn Spieler noch Zugmöglichkeit hat
	 */
	public abstract boolean kannZiehen(Spieler spieler);
	
	/**
	 * @return Beschreibung der Regel
	 */
	public String getBeschreibung() {
		return beschreibung;
	}

	protected void setBeschreibung(String beschreibung) {
		this.beschreibung = beschreibung;
	}
}
