package ui;

import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import pd.Spiel;
import pd.brett.Feld;
import pd.spieler.Spieler;
import ui.lobby.LobbyView;
import ui.spiel.BodesuriView;
import ui.spiel.brett.felder.FeldMouseAdapter;
import ui.spiel.karten.KarteMouseAdapter;
import ui.verbinden.VerbindenView;
import applikation.client.controller.Controller;

public class GUIController extends Controller {
	private VerbindenView verbindenView;
	private LobbyView lobbyView;
	private BodesuriView spielView;

	private FeldMouseAdapter feldMouseAdapter;
	private KarteMouseAdapter karteMouseAdapter;
	private JLabel hinweisFeld;

	public void zeigeVerbinden(String spielerName) {
		verbindenView = new VerbindenView(this, spielerName);
		verbindenView.setVisible(true);
	}

	public void zeigeLobby() {
		verbindenView.setVisible(false);
		lobbyView = new LobbyView();
		lobbyView.setVisible(true);
	}

	public void zeigeSpiel(Spiel spiel, Spieler spielerIch, Map<Spieler, applikation.client.Spieler> spielers) {
		lobbyView.setVisible(false);
		spielView = new BodesuriView(this, spiel, spielerIch, spielers);
		spielView.setVisible(true);
	}

	public void zeigeFehlermeldung(String fehlermeldung) {
		JOptionPane.showMessageDialog(null, fehlermeldung);
	}
	
	public void zeigeHinweis(String hinweis) {
		if (hinweisFeld != null) {
			hinweisFeld.setText(hinweis);
		}
	}

	public void aktiviereFeld(Boolean zustand) {
		if (feldMouseAdapter != null) {
			feldMouseAdapter.aktiv(zustand);
		}
	}
	
	public void zeigeFeldauswahl(Feld feld, boolean status) {
		if (feldMouseAdapter != null) {
			feldMouseAdapter.setzeFigurAusgewaehltStatus(status, feld);
		}
	}

	public void aktiviereKarte(Boolean zustand) {
		if (karteMouseAdapter != null) {
			karteMouseAdapter.aktiv(zustand);
		}
	}

	/**
	 * Einen {@link FeldMouseAdapter} beim Controller registrieren. Dieser wird
	 * vom Automaten über feldAuswahl() je nach Zustand (de-)aktiviert.
	 *
	 * @param feldMouseAdapter
	 */
	public void registriereFeldMouseAdapter(FeldMouseAdapter feldMouseAdapter) {
		this.feldMouseAdapter = feldMouseAdapter;
	}

	/**
	 * Einen {@link KarteMouseAdapter} beim Controller registrieren. Dieser wird
	 * vom Automaten über kartenAuswahl() je nach Zustand (de-)aktiviert.
	 *
	 * @param karteMouseAdapter
	 */
	public void registriereKarteMouseAdapter(KarteMouseAdapter karteMouseAdapter) {
		this.karteMouseAdapter = karteMouseAdapter;
	}
	
	public void registriereHinweisFeld(JLabel hinweisFeld){
		this.hinweisFeld = hinweisFeld;
	}
}
