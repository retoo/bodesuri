package ui.spiel.karten;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import applikation.client.controller.Steuerung;
import applikation.client.pd.Spiel;

public class SteuerungsView extends JPanel implements Observer {
	Steuerung steuerung;
	Spiel spiel;

	public SteuerungsView(Steuerung steuerung, Spiel spiel) {
		this.steuerung = steuerung;
		this.spiel = spiel;

		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		setOpaque(false);

		/* TODO: Robin: das war vom ehemaligen steuerung label, ich lass mal hier, vielleicht b
		 * brauchst ud das für die buttons (-reto)
		 */
		spiel.addObserver(this);

		JButton aussetzen = new JButton("Aufgeben");
		aussetzen.setOpaque(false);
		aussetzen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SteuerungsView.this.steuerung.aufgeben();
			}
		});

		aussetzen.setAlignmentX(Component.LEFT_ALIGNMENT);

		add(aussetzen);
		add(Box.createVerticalGlue());
	}

	public void update(Observable o, Object arg) {
		/* TODO: Robin: das war vom ehemaligen steuerung label, ich lass mal hier, vielleicht b
		 * brauchst ud das für die buttons (-reto)
		 */
	}
}
