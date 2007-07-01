package pd.regelsystem;

import java.util.Vector;

import pd.regelsystem.karten.Karte;
import pd.regelsystem.verstoesse.RegelVerstoss;
import pd.spiel.spieler.Spieler;
import pd.zugsystem.Zug;

/**
 * Regel, die andere Regeln beinhalten kann. Ist gültig, wenn eine der
 * enthaltenen Regeln gültig ist.
 */
public class RegelVeroderung extends Regel {
	private Vector<Regel> regeln = new Vector<Regel>();

	/**
	 * Füge eine Regel der Veroderung hinzu.
	 *
	 * @param regel Regel, die hinzugefügt wird
	 */
	public void fuegeHinzu(Regel regel) {
		regeln.add(regel);
	}

	public boolean arbeitetMitWeg() {
		for (Regel regel : regeln) {
			if (regel.arbeitetMitWeg()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Beim Validieren werden alle enthaltenen Regeln durchgegangen und der
	 * erste gültige Zug zurückgegeben.
	 * 
	 * Wenn keine Regel gültig ist, wird der RegelVerstoss mit der höchsten
	 * Spezifität geworfen.
	 */
	public Zug validiere(ZugEingabe zugEingabe) throws RegelVerstoss {
		RegelVerstoss verstoss = null;
		for (Regel regel : regeln) {
			try {
				Zug resultat = regel.validiere(zugEingabe);
				return resultat;
			} catch (RegelVerstoss rv) {
				if (verstoss == null ||
				    rv.getSpezifitaet() > verstoss.getSpezifitaet()) {
					verstoss = rv;
				}
			}
		}
		throw verstoss;
	}

	protected void liefereZugEingaben(Spieler spieler, Karte karte,
	                                  ZugEingabeAbnehmer abnehmer) {
		for (Regel regel : regeln) {
			regel.liefereZugEingaben(spieler, karte, abnehmer);
		}
	}

	public String getBeschreibung() {
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < regeln.size(); ++i) {
			s.append(regeln.get(i).getBeschreibung());
			if (i < regeln.size() - 2) {
				s.append(", ");
			} else if (i == regeln.size() - 2) {
				s.append(" oder ");
			}
		}
		return s.toString();
	}
}
