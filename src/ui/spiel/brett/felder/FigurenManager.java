package ui.spiel.brett.felder;

import java.util.IdentityHashMap;
import pd.spiel.spieler.Figur;

/**
 * Dient zur Vereinigung der Problemdomain Figur und der GUI Figur2d.
 */
public class FigurenManager {
	private IdentityHashMap<Figur, Figur2d> figurMap;

	public FigurenManager() {
		figurMap = new IdentityHashMap<Figur, Figur2d>();
	}

	public Figur2d get(Figur figur) {
		Figur2d figur2d = figurMap.get(figur);

		return figur2d;
	}

	/**
	 * Die Problemdomain Figur und die GUI Figur2d werden zur HashMap
	 * hinzugefügt.
	 * 
	 * @param figur
	 * @param figur2d
	 */
	public void registriere(Figur figur, Figur2d figur2d) {
		figurMap.put(figur, figur2d);
	}
}
