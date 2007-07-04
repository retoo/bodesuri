/*
 * Copyright (C) 2007  Danilo Couto, Philippe Eberli,
 *                     Pascal Hobus, Reto Schüttel, Robin Stocker
 *
 * This file is part of Bodesuri.
 *
 * Bodesuri is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 2 as
 * published by the Free Software Foundation.
 *
 * Bodesuri is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Bodesuri; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */


package ui.spiel.brett.felder;


import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.util.Observable;
import java.util.Observer;

import javax.swing.Icon;

import pd.spiel.spieler.Figur;
import pd.spiel.spieler.SpielerFarbe;
import ui.geteiltes.BLabel;
import ui.ressourcen.Icons;
import applikation.client.pd.Feld;

/**
 * JLabel, ist die Oberklase aller Felder. Von dieser Klasse leiten alle
 * weiteren Typen von Feldern ab.
 */
public abstract class Feld2d extends BLabel implements Observer {
    private Point position;
    private final Feld feld;
    protected Icon icon;
    private FigurenManager figurenManager;
    private BLabel hover;
    private BLabel ausgewaehlt;
    private BLabel geist;
    private BLabel wegNormal;
    private Figur aktuelleFigur;

    public Feld2d(Icon icon, Feld2dKonfiguration konfig) {
        super(icon, konfig.position);
        this.icon = icon;
        this.position = konfig.position;
        this.feld = konfig.feld;
        this.hover = konfig.hover;

        this.figurenManager = konfig.figurenManager;
        this.ausgewaehlt = new BLabel(Icons.FELD_AUSWAHL);
        this.ausgewaehlt.setVisible(false);
        this.add(ausgewaehlt);

        this.geist = new BLabel(Icons.getSpielerGeist(konfig.farbeIch), -1, -1);
        this.geist.zentriereAuf(this.getRelativerMittelpunkt());
        this.geist.setVisible(false);
        this.add(geist);

        this.wegNormal = new BLabel(Icons.getSpielerHoverIcon(konfig.farbeIch));
        this.wegNormal.setVisible(false);
        add(wegNormal);

        feld.addObserver(this);

        addMouseListener(konfig.mouseAdapter);

        /* Das Feld ein erstes mal zeichnen*/
        zentriereAuf(position);

        positioniereFigur(false);
    }

    public Feld getFeld() {
        return feld;
    }

    public void update(Observable o, Object arg) {
        positioniereFigur(true);

        /* prüfen wir ob hovern */
        if (feld.istHover()) {

            hover.setVisible(true);
            hover.zentriereAuf(this.position);
        } else {
            hover.setVisible(false);
        }

        ausgewaehlt.setVisible(feld.istAusgewaehlt());
        wegNormal.setVisible(feld.istWeg());
        geist.setVisible(feld.istGeist());
    }

    /** 
     * Die Figur wird auf die Position eines bestimmten Feldes positioniert.
     * 
     * @param animation true, die Figur wird anniemiert verschoben.
     */
    private void positioniereFigur(boolean animation) {
        Figur figur = feld.getFigur();

        /* Prüfen ob Feld mit einer Figur bestückt weden muss */
        if (feld.istBesetzt()) {
            if (aktuelleFigur != figur) {

                /* Figur drauf stellen */
                Figur2d figur2d = figurenManager.get(figur);

                /*
                 * wenn nur ein oder zwei Spieler mitspielen können einige
                 * figuren null sein. Dann zeichnen wir einfach nichts.
                 */
                if (figur2d != null) {
                    if (animation)
                        figur2d.bewegeNach(position);
                    else
                        figur2d.zentriereAuf(position);

                    aktuelleFigur = figur;
                }
            }
        } else {
        	aktuelleFigur = null;
        }
    }

    /**
     * Eigenschaften welches ein Feld2d annimmt werden in dieser Klasse gesetzt.
     */
    public static class Feld2dKonfiguration {
        public final Point position;
        public final Feld feld;
        public final MouseAdapter mouseAdapter;
        public final BLabel hover;
        public final SpielerFarbe farbeIch;
        public final FigurenManager figurenManager;

        public Feld2dKonfiguration(Point position, Feld feld,
        		MouseAdapter mouseAdapter, BLabel hover,
                SpielerFarbe farbeIch, FigurenManager figurenManager) {
            this.position = position;
            this.feld = feld;
            this.mouseAdapter = mouseAdapter;
            this.hover = hover;
            this.farbeIch = farbeIch;
            this.figurenManager = figurenManager;
        }
    }
}
