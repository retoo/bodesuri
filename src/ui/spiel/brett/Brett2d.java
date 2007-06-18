package ui.spiel.brett;

import javax.swing.JLabel;
import ui.ressourcen.Icons;

public class Brett2d extends JLabel {
	public Brett2d(BrettMouseAdapter mouseAdapter){
		super(Icons.BRETT);
		setBounds(0, 0, Icons.BRETT.getIconWidth(), Icons.BRETT.getIconHeight());
		addMouseListener(mouseAdapter);
	}
}
