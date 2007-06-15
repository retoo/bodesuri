package ui.spiel.karten;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import applikation.client.controller.Steuerung;
import applikation.client.pd.Karte;

public class KarteGewaehltView extends JPanel {
	private JLabel name;
	private JTextArea beschreibung;

	public KarteGewaehltView(Steuerung steuerung) {
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		setOpaque(false);

		name = new JLabel();
		Font nameFont = name.getFont().deriveFont(Font.BOLD, 16);
		name.setFont(nameFont);

		beschreibung = new JTextArea();
		beschreibung.setLineWrap(true);
		beschreibung.setEnabled(false);
		beschreibung.setWrapStyleWord(true);
		beschreibung.setOpaque(false);
		beschreibung.setDisabledTextColor(beschreibung.getForeground());

		add(name, BorderLayout.NORTH);
		add(beschreibung, BorderLayout.CENTER);
	}

	public void zeigeKarte(Karte karte) {
		name.setText(karte.toString());
		beschreibung.setText(karte.getRegelBeschreibung());
	}

	public void zeigeKeineKarte() {
		name.setText("");
		beschreibung.setText("");
	}
}
