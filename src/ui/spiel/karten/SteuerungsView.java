package ui.spiel.karten;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import applikation.client.controller.Steuerung;
import applikation.client.pd.Spiel;

public class SteuerungsView extends JPanel implements Observer {
	CardLayout layout;
	Steuerung steuerung;
	Spiel spiel;

	JButton aufgeben;
	JButton tauschen;

	public SteuerungsView(Steuerung steuerung, Spiel spiel) {
		this.steuerung = steuerung;
		this.spiel = spiel;

		layout = new CardLayout();
		setLayout(layout);
		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		setOpaque(false);

		/* TODO: Robin: das war vom ehemaligen steuerung label, ich lass mal hier, vielleicht b
		 * brauchst ud das für die buttons (-reto)
		 */
		spiel.addObserver(this);

		aufgeben = new JButton("Aufgeben");
		aufgeben.setOpaque(false);
		aufgeben.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SteuerungsView.this.steuerung.aufgeben();
			}
		});

		tauschen = new JButton("Tauschen");
		tauschen.setOpaque(false);
		tauschen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO
			}
		});

		add(aufgeben, "aufgeben");
		add(tauschen, "tauschen");
		add(new JLabel(), "");
	}

	public void update(Observable o, Object arg) {
		/* TODO: Robin: das war vom ehemaligen steuerung label, ich lass mal hier, vielleicht b
		 * brauchst ud das für die buttons (-reto)
		 */
	}
}
