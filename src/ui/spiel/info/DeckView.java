package ui.spiel.info;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import pd.karten.Acht;
import pd.karten.Ass;
import pd.karten.Drei;
import pd.karten.Fuenf;
import pd.karten.Karte;
import pd.karten.KartenFarbe;
import pd.karten.Sechs;
import pd.karten.Zehn;
import applikation.client.BodesuriClient;
import applikation.events.AufgegebenEvent;

/**
 * JPanel, das DeckView wird zur Darstellung der Karten verwendet.
 * Hier werden die Karten der Spieler verwaltet und dargestellt.
 */
public class DeckView extends JPanel {

	private static final long serialVersionUID = 1L;

	private BodesuriClient automat;

	Vector<Karte> karten = new Vector<Karte>();

	public DeckView(BodesuriClient automat) {
		this.automat = automat;

		TitledBorder titel = new TitledBorder("Karten");
		setBorder(titel);
		setLayout(null);
		setPreferredSize(new Dimension(230, 280));
		setMaximumSize(new Dimension(230, 280));
		setMinimumSize(new Dimension(230, 280));

		karten.add(new Ass(KartenFarbe.Kreuz, 1));
		karten.add(new Drei(KartenFarbe.Karo, 1));
		karten.add(new Fuenf(KartenFarbe.Kreuz, 1));
		karten.add(new Zehn(KartenFarbe.Pik, 1));
		karten.add(new Sechs(KartenFarbe.Herz, 1));
		karten.add(new Acht(KartenFarbe.Karo, 1));

		for (int i = 5; i >= 0; i--) {
			Point p = new Point(20 + i * 20, 30 + i * 20);
			this.add(new KarteView(p, karten.get(i), automat.queue, this));
		}
		
		
		//TODO Noch besser platzieren & schöner machen (kein Automat)
		JButton aussetzen = new JButton("Aussetzen");
		aussetzen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (DeckView.this.automat.aufgabe) {
					DeckView.this.automat.queue.enqueue(new AufgegebenEvent());	
				}
            }
			
		});
		add(aussetzen);
	}
}
