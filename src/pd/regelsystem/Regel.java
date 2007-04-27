package pd.regelsystem;

import pd.zugsystem.Zug;

public abstract class Regel {
	public abstract Regel validiere(Zug zug);
	
	public abstract void ausfuehren(Zug zug);
}
