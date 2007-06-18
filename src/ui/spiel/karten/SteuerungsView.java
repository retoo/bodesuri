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
import javax.swing.JTextArea;

import pd.zugsystem.ZugEingabe;
import applikation.client.controller.Steuerung;
import applikation.client.pd.Spiel;

public class SteuerungsView extends JPanel implements Observer {
	Steuerung steuerung;
	Spiel spiel;
	JTextArea letzterZugLabel;

	public SteuerungsView(Steuerung steuerung, Spiel spiel) {
		this.steuerung = steuerung;
		this.spiel = spiel;

		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		setOpaque(false);

		spiel.addObserver(this);

		letzterZugLabel = new JTextArea();
		letzterZugLabel.setLineWrap(true);
		letzterZugLabel.setEnabled(false);
		letzterZugLabel.setWrapStyleWord(true);
		letzterZugLabel.setOpaque(false);
		letzterZugLabel.setDisabledTextColor(letzterZugLabel.getForeground());

		JButton aussetzen = new JButton("Aufgeben");
		aussetzen.setOpaque(false);
		aussetzen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SteuerungsView.this.steuerung.aufgeben();
			}
		});

		letzterZugLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
		aussetzen.setAlignmentX(Component.LEFT_ALIGNMENT);

		add(aussetzen);
		add(Box.createVerticalGlue());
		add(letzterZugLabel);
	}

	public void update(Observable o, Object arg) {
		ZugEingabe letzterZug = spiel.getLetzterZug();
		if (letzterZug != null) {
			letzterZugLabel.setText(letzterZug.getKurzBeschreibung());
		} else {
			letzterZugLabel.setText("");
		}
	}
}
