package pd.regelsystem;

import java.util.Vector;

import pd.zugsystem.Zug;
import pd.zugsystem.ZugEingabe;

/**
 * Regel, die andere Regeln beinhalten kann. Ist gültig, wenn eine der
 * enthaltenen Regeln gültig ist.
 */
public class RegelVeroderung extends Regel {
	private Vector<Regel> regeln = new Vector<Regel>();
	
	public void fuegeHinzu(Regel regel) {
		regeln.add(regel);
	}

	/**
	 * Beim Validieren werden alle enthaltenen Regeln durchgegangen und der
	 * erste gültige Zug zurückgegeben.
	 * 
	 * Wenn keine Regel gültig ist, wird eine RegelVerstoss geworfen.
	 */
	public Zug validiere(ZugEingabe zugEingabe) throws RegelVerstoss {
		StringBuilder s = new StringBuilder();
		s.append("Keine Regel war gültig");
		for (Regel regel : regeln) {
			try {
				Zug resultat = regel.validiere(zugEingabe);
				return resultat;
			} catch (RegelVerstoss rv) {
				s.append("\n - " + rv.getMessage());
			}
		}
		throw new RegelVerstoss(s.toString());
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
