package pd.regelsystem;

import java.util.List;

import pd.karten.Karte;
import pd.regelsystem.verstoesse.RegelVerstoss;
import pd.spieler.Spieler;
import pd.zugsystem.Zug;
import pd.zugsystem.ZugEingabe;
import pd.zugsystem.ZugEingabeAbnehmer;
import pd.zugsystem.ZugEingabeHoffender;
import pd.zugsystem.ZugEingabeSammler;

/**
 * Basisklasse für Regeln.
 */
public abstract class Regel {
	private String beschreibung;

	public boolean arbeitetMitWeg() {
		return false;
	}

	/**
	 * Validiere eine Zugeingabe. Bei einer gültigen Eingabe wird ein Zug
	 * zurückgegeben und sonst wird eine RegelVerstoss-Exception geworfen.
	 *
	 * @param zugEingabe Zugeingabe, die validiert werden soll
	 * @return Zug, der dann ausgeführt werden kann
	 * @throws RegelVerstoss Tritt bei Regelwidrigkeit auf und enthält Grund
	 */
	public abstract Zug validiere(ZugEingabe zugEingabe) throws RegelVerstoss;

	/**
	 * Finde heraus, ob der Spieler mit dieser Regel noch ziehen kann, oder ob
	 * er aufgeben muss.
	 *
	 * @param spieler Spieler, dessen Figuren überprüft werden
	 * @return true, wenn Spieler noch Zugmöglichkeit hat
	 */
	public boolean istZugMoeglich(Spieler spieler) {
		ZugEingabeHoffender hoffender = new ZugEingabeHoffender();
		liefereZugEingaben(spieler, null, hoffender);
		return hoffender.hatBekommen();
	}

	/**
	 * Gib alle ZugEingaben zurück, mit denen der Spieler noch ziehen könnte.
	 * 
	 * @param spieler Spieler, dessen Figuren überprüft werden
	 * @param karte Karte, die den ZugEingaben zugewiesen werden soll
	 * @return Liste von ZugEingaben
	 */
	public List<ZugEingabe> getMoeglicheZuege(Spieler spieler, Karte karte) {
		ZugEingabeSammler sammler = new ZugEingabeSammler();
		liefereZugEingaben(spieler, karte, sammler);
		return sammler.getZugEingaben();
	}

	protected abstract void liefereZugEingaben(Spieler spieler, Karte karte,
	                                           ZugEingabeAbnehmer abnehmer);

	/**
	 * @return Beschreibung der Regel
	 */
	public String getBeschreibung() {
		return beschreibung;
	}

	protected void setBeschreibung(String beschreibung) {
		this.beschreibung = beschreibung;
	}
}
