package pd.brett;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import pd.Spiel;
import pd.spieler.Spieler;

public class Brett {
	private Map<Spieler, BankFeld> bankFelder;
	private Map<Spieler, Vector<LagerFeld>> lagerFelder;
	// private Map<Spieler, Vector<HimmelFeld>> himmelFelder;
	
	private Spiel spiel;
	
	public Brett(Spiel spiel) {
		this.spiel = spiel;
		erstelleFelder();
	}

	/* TODO: Schritt für Schritt durchdenken */
	private void erstelleFelder() {
		bankFelder   = new HashMap<Spieler, BankFeld>();
		lagerFelder  = new HashMap<Spieler, Vector<LagerFeld>>();
		
		int nummer = 0;
		
		Vector<Feld> felderInRing = new Vector<Feld>();
		
		for (Spieler sp : spiel.getSpieler()) {
			BankFeld bf = new BankFeld(nummer++, sp);
			bankFelder.put(sp, bf);
			felderInRing.add(bf);
			
			Vector<LagerFeld> lager = new Vector<LagerFeld>();
			for (int i = 0; i < 4; ++i) {
				LagerFeld lf = new LagerFeld(nummer++, sp);
				lf.setFigur(sp.getFiguren().get(i));
				lf.setNaechstes(bf);
				lager.add(lf);
			}
			lagerFelder.put(sp, lager);
			
			for (int i = 0; i < 15; ++i) {
				NormalesFeld nf = new NormalesFeld(nummer++);
				felderInRing.add(nf);
			}
		}
		
		for (int i = 0; i < felderInRing.size(); ++i) {
			int i2 = (i + 1) % felderInRing.size();
			Feld f1 = felderInRing.get(i);
			Feld f2 = felderInRing.get(i2);
			f1.setNaechstes(f2);
			f2.setVorheriges(f1);
		}
	}
	
	public BankFeld getBankFeldVon(Spieler spieler) {
		return bankFelder.get(spieler);
	}
}
