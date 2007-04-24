package PD.regelsystem;

import PD.zugsystem.Zug;

public abstract class Regel {
	public boolean validiere(Zug zug) {
		return false;
	}
}
