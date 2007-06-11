package ui.spiel.brett;

import javax.swing.JLabel;

import ui.ressourcen.Icons;

public class FilzView extends JLabel{
	
	public FilzView(){
		super(Icons.FILZ);
		setBounds(0, 0, Icons.FILZ.getIconWidth(), Icons.FILZ.getIconHeight());
	}
}
