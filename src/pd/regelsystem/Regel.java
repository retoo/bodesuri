package pd.regelsystem;

import java.util.List;

import pd.karten.Karte;
import pd.spieler.Spieler;
import pd.zugsystem.Zug;
import pd.zugsystem.ZugEingabe;
import pd.zugsystem.ZugEingabeAbnehmer;
import pd.zugsystem.ZugEingabeHoffender;
import pd.zugsystem.ZugEingabeSammler;

// TODO: Robin: Gründe für RegelVerstoss vereinheitlichen. --Robin

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
	public boolean kannZiehen(Spieler spieler) {
		ZugEingabeHoffender hoffender = new ZugEingabeHoffender();
		liefereZugEingaben(spieler, null, hoffender);
		return hoffender.hatBekommen();
	}

	public List<ZugEingabe> moeglicheZuege(Spieler spieler, Karte karte) {
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
