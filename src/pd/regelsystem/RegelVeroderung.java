package pd.regelsystem;

import java.util.Vector;

import pd.karten.Karte;
import pd.spieler.Spieler;
import pd.zugsystem.Zug;
import pd.zugsystem.ZugEingabe;
import pd.zugsystem.ZugEingabeAbnehmer;

/**
 * Regel, die andere Regeln beinhalten kann. Ist gültig, wenn eine der
 * enthaltenen Regeln gültig ist.
 */
public class RegelVeroderung extends Regel {
	private Vector<Regel> regeln = new Vector<Regel>();

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
	 * Wenn keine Regel gültig ist, wird eine RegelVerstoss geworfen.
	 */
	public Zug validiere(ZugEingabe zugEingabe) throws RegelVerstoss {
		StringBuilder s = new StringBuilder();
		s.append("Keine Regel war gültig:");
		Vector<String> verstoesse = new Vector<String>();
		for (Regel regel : regeln) {
			try {
				Zug resultat = regel.validiere(zugEingabe);
				return resultat;
			} catch (RegelVerstoss rv) {
				String message = rv.getMessage();
				if (!verstoesse.contains(message)) {
					verstoesse.add(message);
				}
			}
		}
		for (String verstoss : verstoesse) {
			s.append("\n - " + verstoss);
		}
		throw new RegelVerstoss(s.toString());
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
