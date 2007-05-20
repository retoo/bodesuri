package pd.brett;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import pd.Spiel;
import pd.spieler.Spieler;

public class Brett {
	private Map<Spieler, BankFeld> bankFelder;
	private Map<Spieler, Vector<LagerFeld>> lagerFelder;
	private Map<Spieler, Vector<HimmelFeld>> himmelFelder;
	
	private Spiel spiel;
	
	public Brett(Spiel spiel) {
		this.spiel = spiel;
		erstelleFelder();
	}

	/* TODO: Schritt für Schritt durchdenken */
	private void erstelleFelder() {
		bankFelder   = new HashMap<Spieler, BankFeld>();
		lagerFelder  = new HashMap<Spieler, Vector<LagerFeld>>();
		himmelFelder = new HashMap<Spieler, Vector<HimmelFeld>>();
		
		int nummer = 0;
		
		Vector<Feld> felderInRing = new Vector<Feld>();
		
		for (Spieler sp : spiel.getSpieler()) {
			BankFeld bf = new BankFeld(nummer++, sp);
			bankFelder.put(sp, bf);
			felderInRing.add(bf);
			
			Vector<HimmelFeld> himmel = new Vector<HimmelFeld>();
			for (int i = 0; i < 4; ++i) {
				HimmelFeld hf = new HimmelFeld(nummer++, sp);
				bf.setHimmel(hf);
				hf.setVorheriges(bf);
				himmel.add(hf);
			}
			for (int i = 0; i < himmel.size() - 1; ++i) {
				HimmelFeld f1 = himmel.get(i);
				HimmelFeld f2 = himmel.get(i + 1);
				f1.setNaechstes(f2);
				f2.setVorheriges(f1);
			}
			himmelFelder.put(sp, himmel);
			
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

	public List<LagerFeld> getLagerFelderVon(Spieler spieler) {
	    return lagerFelder.get(spieler);
    }
	
	public LagerFeld getFreiesLagerFeldVon(Spieler spieler) {
		for (LagerFeld feld : lagerFelder.get(spieler)) {
			if (!feld.istBesetzt()) {
				return feld;
			}
		}
		throw new RuntimeException("getFreiesLagerFeldVon wurde aufgerufen, " +
				"aber es sind keine freien Lagerfelder mehr vorhanden.");
	}
}
