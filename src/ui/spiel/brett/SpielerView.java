package ui.spiel.brett;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import pd.spieler.Spieler;
import applikation.client.Controller;



/**
 * JPanel, dient zur auflistung der einzelnen Spieler.
 */

public class SpielerView extends JPanel {
	public SpielerView(Controller controller) {
		TitledBorder titel = new TitledBorder("Spieler");
		// titel.setTitleFont(titel.getTitleFont().deriveFont(Font.BOLD));
		setBorder(titel);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setOpaque(false);

//		System.out.println(FigurenFarbe.values());
//		Enumeration<FigurenFarbe> iter = (Enumeration<FigurenFarbe>) FigurenFarbe.BLAU.iterator();
//		System.out.println(iter.hasMoreElements());
		
		JLabel spielerName;
		for (Spieler spieler : controller.getSpiel().getSpieler()) {
			spielerName = new JLabel(spieler.getName());
			//spielerName.setForeground(FigurenFarbe.farben.get(1).getColor());
			this.add(spielerName);
		}
	}
}
