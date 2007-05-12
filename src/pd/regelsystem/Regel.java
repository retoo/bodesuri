package pd.regelsystem;

import pd.zugsystem.ZugEingabe;

public abstract class Regel {
	public abstract Regel validiere(ZugEingabe zug);
	
	public abstract void ausfuehren(ZugEingabe zug);
}
