package pd.brett;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import pd.Spiel;
import pd.spieler.Figur;
import pd.spieler.Spieler;

/**
 * Spielbrett, das es pro Spiel gibt und alle Felder beinhaltet.
 * 
 * Die Felder bilden eine verkettete Liste.
 * 
 * @see Feld
 */
public class Brett {
	private Map<Spieler, BankFeld> bankFelder;
	private Map<Spieler, Vector<LagerFeld>> lagerFelder;
	private Map<Spieler, Vector<HimmelFeld>> himmelFelder;
	private Vector<Feld> alleFelder;
	
	private Spiel spiel;
	private int feldNummer;
	
	/**
	 * Erstellt ein Brett.
	 * 
	 * @param spiel Spiel, zu dem Brett gehört
	 */
	public Brett(Spiel spiel) {
		this.spiel = spiel;
		erstelleFelder();
	}

	/*
	 * Feldnummerierung ist folgendermassen:
	 * - Bankfeld 0
	 * - Lagerfelder von 1-4
	 * - Himmelfelder von 5-8
	 * - Normale Felder von 9-23
	 * Also 24 Felder pro "Ecke" von Spieler.
	 */
	private void erstelleFelder() {
		alleFelder   = new Vector<Feld>();
		bankFelder   = new HashMap<Spieler, BankFeld>();
		lagerFelder  = new HashMap<Spieler, Vector<LagerFeld>>();
		himmelFelder = new HashMap<Spieler, Vector<HimmelFeld>>();
		
		feldNummer = 0;
		Vector<Feld> felderInRing = new Vector<Feld>();
		
		for (Spieler sp : spiel.getSpieler()) {
			BankFeld bf = new BankFeld(feldNummer++, sp);
			bankFelder.put(sp, bf);
			alleFelder.add(bf);
			
			erstelleLager(sp, bf);
			erstelleHimmel(sp, bf);
			
			felderInRing.add(bf);
			for (int i = 0; i < 15; ++i) {
				NormalesFeld nf = new NormalesFeld(feldNummer++);
				felderInRing.add(nf);
				alleFelder.add(nf);
			}
		}
		
		verkette(felderInRing, true);
	}

	private void erstelleLager(Spieler sp, BankFeld bf) {
	    Vector<LagerFeld> lager = new Vector<LagerFeld>();
	    for (int i = 0; i < 4; ++i) {
	    	LagerFeld lf = new LagerFeld(feldNummer++, sp);
	    	Figur figur = sp.getFiguren().get(i);
	    	lf.setFigur(figur);
	    	figur.versetzeAuf(lf);
	    	lf.setGeschuetzt(true);
	    	lf.setNaechstes(bf);
	    	lager.add(lf);
	    }
	    lagerFelder.put(sp, lager);
	    alleFelder.addAll(lager);
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
		alleFelder.addAll(himmel);
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
	
	/**
	 * Bankfeld eines Spielers herausfinden.
	 * 
	 * @param spieler Spieler
	 * @return Bankfeld des Spielers
	 */
	public BankFeld getBankFeldVon(Spieler spieler) {
		return bankFelder.get(spieler);
	}

	/**
	 * Lagerfelder eines Spielers herausfinden.
	 * 
	 * @param spieler Spieler
	 * @return Lagerfelder des Spielers
	 */
	public List<LagerFeld> getLagerFelderVon(Spieler spieler) {
	    return lagerFelder.get(spieler);
    }
	
	/**
	 * Himmelfelder eines Spielers herausfinden.
	 * 
	 * @param spieler Spieler
	 * @return Himmelfelder des Spielers
	 */
	public List<HimmelFeld> getHimmelFelderVon(Spieler spieler) {
		return himmelFelder.get(spieler);
	}
	
	/**
	 * Erstes freies Lagerfeld eines Spielers herausfinden.
	 * 
	 * @param spieler Spieler
	 * @return Erstes freies Lagerfeld
	 */
	public LagerFeld getFreiesLagerFeldVon(Spieler spieler) {
		for (LagerFeld feld : lagerFelder.get(spieler)) {
			if (!feld.istBesetzt()) {
				return feld;
			}
		}
		throw new RuntimeException("getFreiesLagerFeldVon wurde aufgerufen, " +
				"aber es sind keine freien Lagerfelder mehr vorhanden.");
	}
	
	/**
	 * Gibt alle Felder, die auf dem Brett sind, zurück.
	 * 
	 * @return Eine Liste aller Felder
	 */
	public List<Feld> getAlleFelder() {
		return alleFelder;
	}
}
