package ui.spiel.brett;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import applikation.client.controller.Steuerung;

public class BrettMouseAdapter extends MouseAdapter {
	private Steuerung steuerung;

	public BrettMouseAdapter(Steuerung steuerung) {
		this.steuerung = steuerung;
	}

	public void mouseClicked(MouseEvent e) {
		steuerung.feldAbwaehlen();
	}
}
