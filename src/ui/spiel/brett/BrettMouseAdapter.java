package ui.spiel.brett;

import geteiltes.ClickMouseAdapter;

import java.awt.event.MouseEvent;


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
