package pd.regelsystem;

import java.io.Serializable;

import pd.zugsystem.Zug;

public abstract class Regel implements Serializable {
	public abstract Regel validiere(Zug zug);
	
	public abstract void ausfuehren(Zug zug);
}
