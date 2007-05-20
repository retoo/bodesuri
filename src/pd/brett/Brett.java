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
	private int feldNummer;
	
	public Brett(Spiel spiel) {
		this.spiel = spiel;
		erstelleFelder();
	}

	/* TODO: Schritt für Schritt durchdenken */
	private void erstelleFelder() {
		bankFelder   = new HashMap<Spieler, BankFeld>();
		lagerFelder  = new HashMap<Spieler, Vector<LagerFeld>>();
		himmelFelder = new HashMap<Spieler, Vector<HimmelFeld>>();
		
		feldNummer = 0;
		Vector<Feld> felderInRing = new Vector<Feld>();
		
		for (Spieler sp : spiel.getSpieler()) {
			BankFeld bf = new BankFeld(feldNummer++, sp);
			bankFelder.put(sp, bf);
			
			erstelleLager(sp, bf);
			erstelleHimmel(sp, bf);
			
			felderInRing.add(bf);
			for (int i = 0; i < 15; ++i) {
				felderInRing.add(new NormalesFeld(feldNummer++));
			}
		}
		
		verkette(felderInRing, true);
	}

	private void erstelleLager(Spieler sp, BankFeld bf) {
	    Vector<LagerFeld> lager = new Vector<LagerFeld>();
	    for (int i = 0; i < 4; ++i) {
	    	LagerFeld lf = new LagerFeld(feldNummer++, sp);
	    	lf.setFigur(sp.getFiguren().get(i));
	    	lf.setNaechstes(bf);
	    	lager.add(lf);
	    }
	    lagerFelder.put(sp, lager);
    }

	private void erstelleHimmel(Spieler sp, BankFeld bf) {
		Vector<HimmelFeld> himmel = new Vector<HimmelFeld>();
		for (int i = 0; i < 4; ++i) {
			HimmelFeld hf = new HimmelFeld(feldNummer++, sp);
			himmel.add(hf);
		}
		bf.setHimmel(himmel.get(0));
		himmel.get(0).setVorheriges(bf);
		verkette(himmel, false);
		himmelFelder.put(sp, himmel);
	}
	
	private void verkette(List<? extends Feld> felder, boolean ringsum) {
		int anzahl = ringsum ? felder.size() : felder.size() - 1;
		for (int i = 0; i < anzahl; ++i) {
			int i2 = (i + 1) % felder.size();
			Feld f1 = felder.get(i);
			Feld f2 = felder.get(i2);
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
	
	public List<HimmelFeld> getHimmelFelderVon(Spieler spieler) {
		return himmelFelder.get(spieler);
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
