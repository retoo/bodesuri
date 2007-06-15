package ui.spiel.karten;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import applikation.client.controller.Steuerung;

public class KarteMouseAdapter extends MouseAdapter {
	private Steuerung steuerung;
	private KartenAuswahlView kartenAuswahlView;
	private KartenAuswahl kartenAuswahl;

	private boolean aktiv;

	public KarteMouseAdapter(Steuerung steuerung,
	        KartenAuswahlView kartenAuswahlView, KartenAuswahl kartenAuswahl) {
		this.steuerung = steuerung;
		this.kartenAuswahlView = kartenAuswahlView;
		this.kartenAuswahl = kartenAuswahl;
		this.aktiv = false;
	}

	public void mouseClicked(MouseEvent evt) {
		if (aktiv) {
			KarteView karteView = (KarteView) evt.getComponent();
			kartenAuswahl.setPosition(karteView.getPosition());

			kartenAuswahlView.getDeckView().add(kartenAuswahl);
			kartenAuswahlView.getDeckView().updateUI();

			kartenAuswahlView.getKarteGewaehltView()
			                 .zeigeKarte(karteView.getKarte());

			steuerung.karteAuswaehlen(karteView.getKarte());
		}
	}

	protected void aktiv(Boolean aktiv) {
		this.aktiv = aktiv;
		if (!aktiv) {
			kartenAuswahlView.getDeckView().remove(kartenAuswahl);
			kartenAuswahlView.getDeckView().updateUI();

			kartenAuswahlView.getKarteGewaehltView().zeigeKeineKarte();
		}
	}
}
