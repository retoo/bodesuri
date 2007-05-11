/**
 * @(#) BrettView.java
 */

package ui.brett;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JPanel;

import pd.spieler.Figur;
import pd.spieler.Spieler;

public class BrettView extends JPanel {

	private static final long serialVersionUID = 1L;

	Vector<Feld> felder = new Vector<Feld>();

	private JLabel debug;
	private FeldMouse listener = new FeldMouse();


	public BrettView() {
		// Nur vorübergehend, damit man sehen kann wie gross das Panel ist...
		setBackground(new Color(0, 150, 0));

		setLayout(null);
		Dimension grösse = new Dimension(600, 600);
		setPreferredSize(grösse);
		setMaximumSize(grösse);
		setMinimumSize(grösse);
		setAlignmentX(Component.LEFT_ALIGNMENT);

		debug = new JLabel();
		debug.setBounds(20, 570, 200, 30);
		add(debug);

		for (int i = 30; i < 400; i += 70) {
			Feld feld = new Feld(listener, i, 20, "Feld" + i);
			felder.add(feld);
			this.add(feld);
		}
		Spieler sp = new Spieler(1);
		felder.get(1).setFigur(new Figur(sp));
		felder.get(2).setFigur(new Figur(sp));
	}

	class FeldMouse extends MouseAdapter {
		private Feld selected;
		
		public void mouseClicked(MouseEvent e) {
			Feld clicked = (Feld) e.getComponent();

			if (clicked.hasFigur()) {
				if (selected == null) {
					selected = clicked;
				} else {
					// Fressen
					selected = null;
				}
			} else {
				if (selected == null) {
					// nichts
				} else {
					clicked.setFigur(selected.getFigur());
					selected.setFigur(null);
					selected = null;
				}
			}

			debug.setText(clicked.getText() + " / X: " + e.getX() + " / Y: "
			               + e.getY());
		}
	}
}
