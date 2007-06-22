package pd.zugsystem;

import java.util.Vector;

import pd.brett.Feld;

public class Weg extends Vector<Feld> {
	private boolean vorwaerts;

	public Weg(boolean vorwaerts) {
		this.vorwaerts = vorwaerts;
	}

	public boolean istRueckwaerts() {
		return !vorwaerts;
	}

	public int getWegLaenge() {
		return size() - 1;
	}
}
