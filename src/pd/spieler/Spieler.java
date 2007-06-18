package pd.spieler;

import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import pd.Spiel;
import pd.karten.Karte;
import pd.regelsystem.Regel;
import pd.serialisierung.BodesuriCodierbaresObjekt;
import pd.zugsystem.ZugEingabe;
import dienste.observer.ObservableList;

/**
 * Spieler, der bei einem {@link Spiel} mitspielt, und 4 {@link Figur Figuren}
 * hat.
 */
public class Spieler extends BodesuriCodierbaresObjekt {
	private int nummer;
	private String name;
	private SpielerFarbe farbe;
	private Spiel spiel;
	private Vector<Figur> figuren = new Vector<Figur>();
	private ObservableList<Karte> karten = new ObservableList<Karte>();

	/**
	 * Erstellt einen Spieler.
	 *
	 * @param nummer
	 *            Eindeutige Spielernummer
	 * @param spiel
	 *            Spiel, bei dem der Spieler mitspielt
	 * @param farbe
	 */
	public Spieler(int nummer, Spiel spiel, SpielerFarbe farbe) {
		super("Spieler " + nummer);
		this.nummer = nummer;
		this.spiel = spiel;
		this.farbe = farbe;
		for (int i = 0; i < 4; ++i) {
			figuren.add(new Figur(this));
		}
	}

	/**
	 * Sieht zum Beispiel so aus: "Zoë"
	 */
	public String toString() {
		return getName();
	}

	/**
	 * @return Name des Spielers
	 */
	public String getName() {
		return name;
	}

	/**
     * @return die farbe
     */
    public SpielerFarbe getFarbe() {
    	return farbe;
    }

	/**
	 * @param name
	 *            Name des Spielers
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return Figuren, mit denen der Spieler spielt
	 */
	public List<Figur> getFiguren() {
		return figuren;
	}

	/**
	 * @return Spielernummer
	 */
	public int getNummer() {
		return nummer;
	}

	/**
	 * @return Spiel, bei dem der Spieler mitspielt
	 */
	public Spiel getSpiel() {
		return spiel;
	}

	public ObservableList<Karte> getKarten() {
		return karten;
	}

	/**
	 * @return true, wenn der Spieler mit seinen Karten ziehen kann
	 */
	public boolean kannZiehen() {
		for (Karte karte : getKarten()) {
			if (karte.getRegel() != null && karte.getRegel().kannZiehen(this)) {
				return true;
			}
		}
		return false;
	}

	public boolean istFertig() {
		for (Figur figur : figuren) {
			if (!figur.getFeld().istHimmel()) {
				return false;
			}
		}

		return true;
	}

	public List<ZugEingabe> getMoeglicheZuege() {
		List<ZugEingabe> moeglich = new LinkedList<ZugEingabe>();

		for (Karte karte : getKarten()) {
			Regel regel = karte.getRegel();
			if (regel != null) {
				regel.moeglicheZuege(this, karte, moeglich);
			}
		}

		return moeglich;
    }
}
