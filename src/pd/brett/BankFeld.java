package pd.brett;

import pd.spieler.Spieler;

public class BankFeld extends SpielerFeld {
	private static final long serialVersionUID = 1L;
	
	private HimmelFeld himmelFeld;

	public BankFeld(int nummer, Spieler spieler) {
		super(nummer, spieler);
	}
	
	public HimmelFeld getHimmel() {
		return himmelFeld;
	}
	
	public void setHimmel(HimmelFeld himmelFeld) {
		this.himmelFeld = himmelFeld;
	}
}
