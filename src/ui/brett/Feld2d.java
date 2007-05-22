package ui.brett;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;

import applikation.zugentgegennahme.ZugEntgegennahme;
import pd.brett.Feld;

public class Feld2d extends javax.swing.JLabel {
	private static final long serialVersionUID = 1L;
	private Point p;
	private Feld feld;

	static final private ImageIcon bildFeld = new ImageIcon(Feld2d.class.getResource("/ui/brett/feld.png"));

	public Feld2d(Point p, Feld feld) {
		super(bildFeld);
		this.p = p;
		this.feld = feld;

		setBounds((int)p.getX(), (int)p.getY(), bildFeld.getIconWidth(), bildFeld.getIconHeight());
		addMouseListener(new FeldMouseAdapter());
	}
	
	public int getX() {
		return (int) p.getX();
	}
	
	public int getY(){
		return (int) p.getY();
	}
	
	public Feld getFeld() {
		return feld;
	}

	private class FeldMouseAdapter extends MouseAdapter {				
		// FIXME: woher bekomme ich den Spieler, der auf das Feld geklickt hat?
		// TODO: Anhand der Klicks die Regel entscheiden? Müsste doch von Karte aus geschehen
		/**
		 * Anhand der Instanzvariablen wird entschieden, ob gerade der Anfangspunkt oder 
		 * der Endpunkt angeklickt wurde.
		 * 
		 * @param e MouseEvent enthält die angeklickte Komponente
		 */
		public void mouseClicked(MouseEvent e) {
			// TODO: kann hier auch direkt feld Instanzvariable genommen werden?
			//       --> schöner machen
			Feld angeklicktesFeld = ((Feld2d) e.getComponent()).getFeld();
			ZugEntgegennahme.ziehen(angeklicktesFeld);
		}
		
		
	}
}
