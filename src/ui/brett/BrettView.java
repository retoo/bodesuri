/**
 * @(#) BrettView.java
 */

package ui.brett;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class BrettView extends JPanel {

	private static final long serialVersionUID = 1L;

	Vector<Feld2d> felder = new Vector<Feld2d>();

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

		for (int i = 0; i < 20; i++) {
			Feld2d feld = new Feld2d(i);
			felder.add(feld);
		}

		add(new Figur2d(felder.get(0)));
		
		for (Feld2d feld : felder) {
			this.add(feld);
		}
	}
}
