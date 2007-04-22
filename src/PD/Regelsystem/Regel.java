package PD.Regelsystem;

import PD.Zugsystem.Zug;

public abstract class Regel {
	public boolean validiere(Zug zug) {
		return false;
	}
}
