package ui.spiel.steuerung;

import geteiltes.ClickMouseAdapter;

import java.awt.event.MouseEvent;


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
}
