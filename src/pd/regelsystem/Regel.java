package pd.regelsystem;

import pd.zugsystem.Zug;
import pd.zugsystem.ZugEingabe;

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
}
