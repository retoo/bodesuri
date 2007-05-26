package pd.brett;

import pd.spieler.Figur;
import pd.spieler.Spieler;
import dienste.serialisierung.CodierbaresObjekt;

/**
 * Basisklasse der Felder, die auf dem Spielbrett liegen.
 * 
 * Jedes Feld ist mit seinem Nächsten und Vorherigen verkettet, damit man von
 * einem Feld aus auf alle anderen Felder gelangen kann.
 * 
 * @see Brett
 */
public abstract class Feld extends CodierbaresObjekt {
	protected Feld naechstes;
	protected Feld vorheriges;

	protected Figur figur;
	
	private int nummer;

	/**
	 * Erstellt ein Feld.
	 * 
	 * @param nummer Eindeutige Nummer (wird für Serialisierung verwendet)
	 */
	public Feld(int nummer) {
		super("Feld " + nummer);
		this.nummer = nummer;
	}

	/**
	 * Gibt das Feld zurück, das nach n Schritten kommt.
	 * 
	 * @param n Anzahl Schritte zum Feld
	 * @return Feld
	 */
	public Feld getNtesFeld(int n) {
		Feld f = this;
		while (n-- > 0)
			f = f.naechstes;

		return f;
	}

	/**
	 * Versetze Figur von diesem Feld auf das Zielfeld.
	 * 
	 * @param ziel Zielfeld
	 */
	public void versetzeFigurAuf(Feld ziel) {
		ziel.setFigur(getFigur());
		setFigur(null);
	}

	/**
	 * @return true, wenn keine Figur auf dem Feld ist
	 */
	public boolean istFrei() {
		return getFigur() == null;
	}

	/**
	 * @return true, wenn eine Figur auf dem Feld ist
	 */
	public boolean istBesetzt() {
		return getFigur() != null;
	}

	/**
	 * @param spieler Spieler
	 * @return true, wenn eine Figur des Spielers auf dem Feld ist
	 */
	public boolean istBesetztVon(Spieler spieler) {
		return istBesetzt() && getFigur().getSpieler() == spieler;
	}

	/**
	 * Gibt das nächste Feld zurück, das mit diesem verbunden ist.
	 * 
	 * @return nächstes Feld
	 */
	public Feld getNaechstes() {
		return naechstes;
	}

	/**
	 * Verkettet dieses Feld mit seinem nächsten Feld.
	 * 
	 * @param naechstesFeld Nächstes Feld, mit dem verbunden werden soll
	 */
	public void setNaechstes(Feld naechstesFeld) {
		this.naechstes = naechstesFeld;
	}

	/**
	 * Gibt das vorherige Feld zurück, das mit diesem verbunden ist.
	 * 
	 * @return vorheriges Feld
	 */
	public Feld getVorheriges() {
		return vorheriges;
	}

	/**
	 * Verkettet dieses Feld mit seinem vorherigen Feld.
	 * 
	 * @param vorherigesFeld Vorheriges Feld, mit dem verbunden werden soll
	 */
	public void setVorheriges(Feld vorherigesFeld) {
		this.vorheriges = vorherigesFeld;
	}

	/**
	 * @return Figur, die sich auf diesem Feld befindet
	 */
	public Figur getFigur() {
		return figur;
	}

	/**
	 * @param figur Figur, die neu auf diesem Feld sein soll.
	 */
	public void setFigur(Figur figur) {
		this.figur = figur;
		if (figur != null) {
			figur.wurdeBewegt(this);
		}
	}
	
	/**
	 * Gibt Feld aus, zum Beispiel so: "Feld 47"
	 */
	public String toString() {
		return "Feld " + nummer;
	}

	/**
	 * @return Feldnummer
	 */
	public int getNummer() {
		return nummer;
	}
}
