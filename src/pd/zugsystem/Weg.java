package pd.zugsystem;

import java.util.Vector;

import pd.brett.Feld;

public class Weg extends Vector<Feld> {
	private boolean rueckwaerts;
	
	public Weg() {
		this(false);
	}
	
	public Weg(boolean rueckwaerts) {
		this.rueckwaerts = rueckwaerts;
	}
	
	public boolean istRueckwaerts() {
		return rueckwaerts;
	}
}
