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
import applikation.client.pd.SteuerungsZustand;

public class SteuerungsButtonView extends JPanel implements Observer {
	Steuerung steuerung;
	Spiel spiel;

	CardLayout layout;
	JButton aufgeben;
	JButton tauschen;

	public SteuerungsButtonView(Steuerung steuerung, Spiel spiel) {
		this.steuerung = steuerung;
		this.spiel = spiel;

		layout = new CardLayout();
		setLayout(layout);
		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		setOpaque(false);

		spiel.addObserver(this);

		aufgeben = new JButton("Aufgeben");
		aufgeben.setOpaque(false);
		aufgeben.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SteuerungsButtonView.this.steuerung.aufgeben();
			}
		});

		tauschen = new JButton("Tauschen");
		tauschen.setOpaque(false);
		tauschen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SteuerungsButtonView.this.steuerung.kartenTauschBestaetigen();
			}
		});

		add(new JLabel(), "");
		add(aufgeben, "aufgeben");
		add(tauschen, "tauschen");
	}

	public void update(Observable o, Object arg) {
		SteuerungsZustand sz = spiel.getSteuerungsZustand();
		if (sz == SteuerungsZustand.AUFGEBEN) {
			layout.show(this, "aufgeben");
		} else if (sz == SteuerungsZustand.TAUSCHEN){
			layout.show(this, "tauschen");
		} else {
			layout.show(this, "");
		}
	}
}
