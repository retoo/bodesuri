package applikation.client.zugautomat.zustaende;

import pd.regelsystem.RegelVerstoss;
import pd.regelsystem.WegLaengeVerstoss;
import pd.zugsystem.Bewegung;
import applikation.client.events.ZugErfasstEvent;
import dienste.automat.zustaende.EndZustand;
import dienste.automat.zustaende.PassiverZustand;
import dienste.automat.zustaende.Zustand;

public class ZugValidieren extends ClientZugZustand implements PassiverZustand {

	public Class<? extends Zustand> handle() {
		Bewegung bewegung = new Bewegung(spielDaten.start.getFeld(),
		                                 spielDaten.ziel.getFeld());
		ZugErfasstEvent erfassterZug = new ZugErfasstEvent(
		                                                   spielDaten.spiel.spielerIch,
		                                                   spielDaten.karte,
		                                                   bewegung);
		try {
			erfassterZug.toZugEingabe().validiere();
		} catch (WegLaengeVerstoss e) {
			if (e.getIstLaenge() < 7) {
				return StartWaehlen.class;
			}
		} catch (RegelVerstoss e) {
			controller.zeigeFehlermeldung("Ungültiger Zug: "
			                              + e.getMessage());
			return ZielWaehlen.class;
		}

		spielDaten.spiel.queue.enqueue(erfassterZug);

		brettZuruecksetzen();
		spielDaten.spiel.spielerIch.getKarten().setAktiv(false);

		return EndZustand.class;
		
//		ZugErfasstEvent erfassterZug = null;
//		if (spielDaten.karte.getKarte() instanceof Sieben) {
//			int weglaenge = spielDaten.getWeglaenge();
//			weglaenge += spielDaten.move.getWegLaenge();
//			if (weglaenge == 7) {
//				spielDaten.bewegungen.add(spielDaten.move);
//				erfassterZug = new ZugErfasstEvent(spielDaten.spiel.spielerIch,
//				                                                   spielDaten.karte,
//				                                                   spielDaten.getBewegungen());
//				try {
//					erfassterZug.toZugEingabe().validiere();
//				} catch (RegelVerstoss e) {
//					controller.zeigeFehlermeldung("Ungültiger Zug: "
//					                              + e.getMessage());
//					spielDaten.bewegungen.remove(spielDaten.bewegungen.size()-1);
//					spielDaten.ziel.setAusgewaehlt(false);
//					spielDaten.ziel = null;
//					return ZielWaehlen.class;
//				}
//			} else if (weglaenge < 7) {
//				spielDaten.bewegungen.add(spielDaten.move);
//				return StartWaehlen.class;
//			} else if (weglaenge > 7) {
//				spielDaten.bewegungen.remove(spielDaten.bewegungen.size()-1);
//				spielDaten.ziel.setAusgewaehlt(false);
//				spielDaten.ziel = null;
//				return ZielWaehlen.class;
//			}
//		} else {
//			erfassterZug = new ZugErfasstEvent(spielDaten.spiel.spielerIch,
//			                                                   spielDaten.karte,
//			                                                   spielDaten.move.getBewegung());
//			try {
//				erfassterZug.toZugEingabe().validiere();
//			} catch (RegelVerstoss e) {
//				controller.zeigeFehlermeldung("Ungültiger Zug: "
//				                              + e.getMessage());
//				spielDaten.ziel.setAusgewaehlt(false);
//				spielDaten.ziel = null;
//				return ZielWaehlen.class;
//			}
//
//		}
//		spielDaten.spiel.queue.enqueue(erfassterZug);
//		
//		spielDaten.brettZuruecksetzen();
//		spielDaten.spiel.spielerIch.getKarten().remove(erfassterZug.getKarte());
//		spielDaten.spiel.spielerIch.getKarten().setAktiv(false);
		
//		if (spielDaten.karte.getKarte() instanceof Sieben) {
//			int weglaenge = spielDaten.getWeglaenge();
//			if (weglaenge < 7) {
//				spielDaten.start.versetzeFigurAuf(spielDaten.ziel);
//				return StartWaehlen.class;
//			} else if (weglaenge > 7) {
//				controller.zeigeFehlermeldung("Ungültiger Zug: Zug muss über "
//				                              + "7 und nicht " + weglaenge
//				                              + " Felder gehen.");
//				spielDaten.ziel.setAusgewaehlt(false);
//				spielDaten.ziel = null;
//				spielDaten.bewegungen.remove(spielDaten.bewegungen.size() - 1);
//				return ZielWaehlen.class;
//			} else {
//				spielDaten.start.versetzeFigurAuf(spielDaten.ziel);
//				spielDaten.siebenRueckgaengig();
//
//			}
//		}
//		ZugErfasstEvent erfassterZug = new ZugErfasstEvent(spielDaten.spiel.spielerIch,
//		                                                   spielDaten.karte,
//		                                                   spielDaten.getBewegungen());
//		try {
//			erfassterZug.toZugEingabe().validiere();
//		} catch (RegelVerstoss e) {
//			controller.zeigeFehlermeldung("Ungültiger Zug: " + e.getMessage());
//			if (erfassterZug.getKarte().getKarte() instanceof Sieben) {
//				spielDaten.siebenRueckgaengig();
//				spielDaten.brettZuruecksetzen();
//				return KarteWaehlen.class;
//			} else {
//				spielDaten.ziel.setAusgewaehlt(false);
//				spielDaten.ziel = null;
//				spielDaten.bewegungen.remove(spielDaten.bewegungen.size() - 1);
//				return ZielWaehlen.class;
//			}
//		}
//
//		spielDaten.spiel.queue.enqueue(erfassterZug);
//
//		spielDaten.brettZuruecksetzen();
//		spielDaten.spiel.spielerIch.getKarten().remove(erfassterZug.getKarte());
//		spielDaten.spiel.spielerIch.getKarten().setAktiv(false);
//
	}

}
