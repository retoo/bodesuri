/**
 * @(#) DeckView.java
 */

package ui.spiel.info;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import pd.karten.Ass;
import pd.karten.KartenFarbe;
import applikation.client.events.KarteGewaehltEvent;
import dienste.automat.EventQueue;

public class DeckView extends JPanel {

    private static final long serialVersionUID = 1L;
	private EventQueue queue;

    public DeckView(EventQueue queue) {
    	this.queue = queue;
    	
		TitledBorder titel = new TitledBorder("Karten");
		// titel.setTitleFont(titel.getTitleFont().deriveFont(Font.BOLD));
		setBorder(titel);
		setLayout(new FlowLayout());
		
		JButton karteSpielen = new JButton("Karte spielen");
		karteSpielen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				KarteGewaehltEvent e = new KarteGewaehltEvent(new Ass(KartenFarbe.Herz, 1));
				
				DeckView.this.queue.enqueue(e);
            }
		});
		this.add(karteSpielen);
    }
}
