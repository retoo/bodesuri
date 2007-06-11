package ui;

import javax.swing.JOptionPane;

import pd.brett.Feld;

import ui.lobby.LobbyView;
import ui.spiel.BodesuriView;
import ui.spiel.brett.FeldMouseAdapter;
import ui.spiel.karten.KarteMouseAdapter;
import ui.verbinden.VerbindenView;
import applikation.client.controller.Controller;

public class GUIController extends Controller {
	private VerbindenView verbindenView;
	private LobbyView lobbyView;
	private BodesuriView spielView;

	private FeldMouseAdapter feldMouseAdapter;
	private KarteMouseAdapter karteMouseAdapter;

	public GUIController(String spielerName) {
		super(spielerName);
	}

	public void zeigeVerbinden() {
		verbindenView = new VerbindenView(this, this.getSpielerName());
		verbindenView.setVisible(true);
	}

	public void zeigeLobby() {
		verbindenView.setVisible(false);
		lobbyView = new LobbyView();
		lobbyView.setVisible(true);
	}

	public void zeigeSpiel() {
		lobbyView.setVisible(false);
		spielView = new BodesuriView(this, this.getSpielerIch().getName());
		spielView.setVisible(true);
	}

	public void zeigeFehlermeldung(String fehlermeldung) {
		JOptionPane.showMessageDialog(null, fehlermeldung);
	}

	public void feldAuswahl(Boolean zustand) {
		feldMouseAdapter.aktiv(zustand);
	}
	
	public void feldAbwaehlen(Feld feld) {
		feldMouseAdapter.setzeFigurAusgewaehltStatus(true, feld);
	}
	
	public void feldWaehlen(Feld feld) {
		feldMouseAdapter.setzeFigurAusgewaehltStatus(false, feld);
	}

	public void kartenAuswahl(Boolean zustand) {
		karteMouseAdapter.aktiv(zustand);
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
}
