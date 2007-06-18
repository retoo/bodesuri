package ui.spiel.karten;

import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import applikation.client.controller.Steuerung;
import applikation.client.pd.Karte;

public class KarteGewaehltView extends JPanel {
	private JLabel name;
	private JTextArea beschreibung;

	public KarteGewaehltView(Steuerung steuerung) {
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		setOpaque(false);

		name = new JLabel();
		name.setOpaque(false);
		Font nameFont = name.getFont().deriveFont(Font.BOLD, 16);
		name.setFont(nameFont);

		beschreibung = new JTextArea();
		beschreibung.setOpaque(false);
		beschreibung.setLineWrap(true);
		beschreibung.setEnabled(false);
		beschreibung.setWrapStyleWord(true);
		beschreibung.setDisabledTextColor(beschreibung.getForeground());
		beschreibung.setRows(2);

		name.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		beschreibung.setAlignmentX(JTextArea.LEFT_ALIGNMENT);
		
		add(name);
		add(beschreibung);
	}

	public void zeigeKarte(Karte karte) {
		name.setText(karte.toString());
		beschreibung.setText(karte.getRegelBeschreibung());
	}

	public void zeigeKeineKarte() {
		// Leerzeichen sind ein Hack, damit die Grösse richtig berechnet wird.
		name.setText(" ");
		beschreibung.setText(" ");
	}
}
