package ui.spiel.brett;


import java.awt.event.MouseEvent;

import ui.geteiltes.ClickMouseAdapter;


import applikation.client.controller.Steuerung;

public class BrettMouseAdapter extends ClickMouseAdapter {
	private Steuerung steuerung;

	public BrettMouseAdapter(Steuerung steuerung) {
		this.steuerung = steuerung;
	}

	public void clicked(MouseEvent e) {
		steuerung.feldAbwaehlen();
	}
}
