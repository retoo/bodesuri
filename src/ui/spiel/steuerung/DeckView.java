package ui.spiel.steuerung;

import java.awt.Dimension;
import java.awt.Point;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

import javax.swing.JPanel;

import applikation.client.controller.Steuerung;
import applikation.client.pd.Karte;
import applikation.client.pd.Karten;
import dienste.observer.ListChangeEvent;
import dienste.observer.ListChangeEvent.ListChangeType;

/**
 * JPanel, das DeckView wird zur Darstellung der Karten verwendet. Hier werden
 * die Karten der Spieler verwaltet und dargestellt.
 */
public class DeckView extends JPanel implements Observer {
	private Karten karten;
	private Vector<KarteView> karteViews;
	private KarteMouseAdapter karteMouseAdapter;

	public DeckView(Steuerung steuerung, Karten karten) {
		this.karten = karten;

		setLayout(null);
		setOpaque(false);

		Dimension groesse = new Dimension(190, 340);
		setPreferredSize(groesse);
		setMaximumSize(groesse);
		setMinimumSize(groesse);

		KartenAuswahl kartenAuswahl = new KartenAuswahl();
		kartenAuswahl.setVisible(false);
		add(kartenAuswahl);
		
		karteMouseAdapter =	new KarteMouseAdapter(steuerung);

		karteViews = new Vector<KarteView>();
		for (int i = 0; i < 6; ++i) {
			int x = i % 2;
			int y = i / 2;
			Point position = new Point(10 + x*90, 10 + y*110);
			KarteView kv = new KarteView(position, karteMouseAdapter, kartenAuswahl);
			karteViews.add(kv);
			add(kv);
		}

		karten.addObserver(this);
	}

	public void update(Observable o, Object arg) {
		if (arg instanceof ListChangeEvent) {
			ListChangeEvent change = (ListChangeEvent) arg;
			if (change.changeType == ListChangeType.ADDED) {
				for (KarteView kv : karteViews) {
					if (kv.getKarte() == null) {
						kv.setKarte((Karte) change.changedObject);
						break;
					}
				}
			} else if (change.changeType == ListChangeType.REMOVED) {
				for (KarteView kv : karteViews) {
					if (kv.getKarte() == change.changedObject) {
						kv.setKarte(null);
						break;
					}
				}
			} else if (change.changeType == ListChangeType.EVERYTHING) {
				for (KarteView karteView : karteViews) {
					karteView.setKarte(null);
				}
				for (int i = 0; i < karten.size(); ++i) {
					karteViews.get(i).setKarte(karten.get(i));
				}
			}
		}
    }
}
