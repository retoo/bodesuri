package ui.spiel.steuerung;

import java.awt.event.MouseEvent;

import ui.geteiltes.ClickMouseAdapter;


import applikation.client.controller.Steuerung;

public class KarteMouseAdapter extends ClickMouseAdapter {
	private Steuerung steuerung;

	public KarteMouseAdapter(Steuerung steuerung) {
		this.steuerung = steuerung;
	}

	public void clicked(MouseEvent evt) {
		KarteView karteView = (KarteView) evt.getComponent();
		steuerung.karteAuswaehlen(karteView.getKarte());
	}

	public void mouseClicked(MouseEvent evt) {
		super.mouseClicked(evt);
		if (evt.getClickCount() == 2) {
			steuerung.kartenTauschBestaetigen();
		}
	}
}
