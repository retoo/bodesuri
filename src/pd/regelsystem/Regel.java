package pd.regelsystem;

import pd.zugsystem.Zug;
import pd.zugsystem.ZugEingabe;

public abstract class Regel {
	public abstract Zug validiere(ZugEingabe zugEingabe) throws RegelVerstoss;
}
