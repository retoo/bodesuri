/**
 * @(#) BrettView.java
 */

package ui.brett;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JPanel;

import pd.brett.Feld;
import pd.spieler.Figur;

public class BrettView extends JPanel {

	private static final long serialVersionUID = 1L;

	Map<Feld, Feld2d> felder = new HashMap<Feld, Feld2d>();

	private JLabel debug;

	public BrettView() {
		// Nur vorübergehend, damit man sehen kann wie gross das Panel ist...
		setBackground(new Color(0, 150, 0));

		setLayout(null);
		Dimension groesse = new Dimension(600, 600);
		setPreferredSize(groesse);
		setMaximumSize(groesse);
		setMinimumSize(groesse);
		setAlignmentX(Component.LEFT_ALIGNMENT);

		debug = new JLabel();
		debug.setBounds(20, 570, 200, 30);
		add(debug);

		// Vorübergehend bis Danilo hier alles richtig implementiert.
		Feld feld = null;

		for (int i = 0; i < 20; i++) {
			Feld2d feld2d = new Feld2d(i);
			felder.put(feld, feld2d);
		}

		add(new Figur2d(felder.get(0), this));

		for (Feld2d feld2d : felder.values()) {
			this.add(feld2d);
		}
	}

	public Feld2d getFigur2d(Figur figur) {
		// TODO Auto-generated method stub
		return null;
	}
}
