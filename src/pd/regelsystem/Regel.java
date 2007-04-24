package pd.regelsystem;

import pd.zugsystem.Zug;

public abstract class Regel {
	public boolean validiere(Zug zug) {
		return false;
	}
}
