package ui.spiel.karten;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import applikation.client.controller.Steuerung;

public class SteuerungsView extends JPanel {
	Steuerung steuerung;

	public SteuerungsView(Steuerung steuerung) {
		this.steuerung = steuerung;
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setOpaque(false);

		JLabel gespielteKarte = new JLabel();
		// FIXME: Reto steuerung.registriereGespielteKarten(gespielteKarte);
		JButton aussetzen = new JButton("Aufgeben");
		aussetzen.addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	SteuerungsView.this.steuerung.aufgeben();
            }
		});

		add(gespielteKarte);
		add(aussetzen);
	}

}
