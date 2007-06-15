package ui.spiel.brett;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import ui.Steuerung;

public class BrettMouseAdapter extends MouseAdapter {
	private Steuerung controller;

	public BrettMouseAdapter(Steuerung steuerung) {
		this.controller = steuerung;
	}

	public void mouseClicked(MouseEvent e) {
		controller.feldAbwaehlen();
	}
}
