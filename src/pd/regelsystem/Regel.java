package pd.regelsystem;

import pd.brett.Feld;
import pd.brett.LagerFeld;
import pd.spieler.Spieler;
import pd.zugsystem.Aktion;
import pd.zugsystem.Zug;
import pd.zugsystem.ZugEingabe;

// TODO: Gründe für RegelVerstoss vereinheitlichen.

/**
 * Basisklasse für Regeln. 
 */
public abstract class Regel {
	/**
	 * Validiert eine Zugeingabe. Bei einer gültigen Eingabe wird ein Zug
	 * zurückgegeben und sonst wird eine RegelVerstoss-Exception geworfen.
	 * 
	 * @param zugEingabe Zugeingabe, die validiert werden soll
	 * @return Zug, der dann ausgeführt werden kann
	 * @throws RegelVerstoss Tritt bei Regelwidrigkeit auf und enthält Grund
	 */
	public abstract Zug validiere(ZugEingabe zugEingabe) throws RegelVerstoss;
	
	protected Aktion heimschickAktion(Feld feld, Spieler spieler) {
		LagerFeld lf = spieler.getSpiel().getBrett().getFreiesLagerFeldVon(spieler);
		return new Aktion(feld, lf);
	}
}
