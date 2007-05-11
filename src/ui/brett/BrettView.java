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

import ui.brett.Figur;
import pd.spieler.Spieler;

public class BrettView extends JPanel {

	private static final long serialVersionUID = 1L;

	Vector<Feld2d> felder = new Vector<Feld2d>();

	private JLabel debug;

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
		
		Figur figur1 = new Figur(30, 20, "Figur1");
		this.add(figur1);
//		for (int i = 30; i < 400; i += 70) {
//			Feld2d feld = new Feld2d(i);
//			felder.add(feld);
//			this.add(feld);
//		}
		Spieler sp = new Spieler(1);

//		felder.get(1).setFigur(new Figur(sp));
//		felder.get(2).setFigur(new Figur(sp));
	}

	class FeldMouse extends MouseAdapter {
		
		
		public void mouseClicked(MouseEvent e) {
			Feld2d clicked = (Feld2d) e.getComponent();

//			if (clicked.hasFigur()) {
//				if (selected == null) {
//					selected = clicked;
//				} else {
//					// Fressen
//					selected = null;
//				}
//			} else {
//				if (selected == null) {
//					// nichts
//				} else {
//					clicked.setFigur(selected.getFigur());
//					selected.setFigur(null);
//					selected = null;
//				}
//			}

			debug.setText(clicked.getText() + " / X: " + e.getX() + " / Y: "
			               + e.getY());
		}
	}
}
