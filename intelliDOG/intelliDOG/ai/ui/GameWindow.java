package intelliDOG.ai.ui;

import intelliDOG.ai.framework.Cards;
import intelliDOG.ai.framework.Players;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * A JFrame extension that shows the current game situation
 *
 */
public class GameWindow extends JFrame {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4370316588905654795L;
	
	private JPanel boardPanel;
	private JLabel[] dummyFields;
	private JLabel[] neutralFields;
	private JLabel[] specialFieldsP1;
	private JLabel[] specialFieldsP2;
	private JLabel[] specialFieldsP3;
	private JLabel[] specialFieldsP4;
	
	private JPanel cardPanel;
	private JLabel[][] cardLabels;
	
	private JLabel[] playerLabels;
	
	private long timeout = 0;
	

	public GameWindow(){
		boardPanel = new JPanel(new GridLayout(21,21));
		boardPanel.setBackground(Color.LIGHT_GRAY);
		boardPanel.setPreferredSize(new Dimension(600, 600));
		initiateBoardPanel();
		
		cardPanel = new JPanel(new GridLayout(13,1));
		cardPanel.setPreferredSize(new Dimension(200, 600));
		initiateCardPanel();
		
		//add panels to main panel
		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.add(boardPanel, BorderLayout.CENTER);
		mainPanel.add(cardPanel, BorderLayout.EAST);
		
		this.add(mainPanel);
		
		this.setLocation(new Point(100, 200));
		this.pack();
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}
	
	private void initiateBoardPanel(){
		dummyFields = new JLabel[362];
		for(int i = 0; i < dummyFields.length; i++){
			dummyFields[i] = new JLabel();
			dummyFields[i].setBackground(Color.gray);
			//dummyFields[i].setOpaque(true);
		}
		neutralFields = new JLabel[64];
		for(int i = 0; i < neutralFields.length; i++){
			neutralFields[i] = new JLabel();
			neutralFields[i].setBackground(Color.white);
			neutralFields[i].setBorder(BorderFactory.createLineBorder(Color.gray, 1));
			neutralFields[i].setOpaque(true);
			neutralFields[i].setText(" " + i);
		}
		specialFieldsP1 = new JLabel[5];
		for(int i = 0; i < specialFieldsP1.length; i++){
			specialFieldsP1[i] = new JLabel();
			specialFieldsP1[i].setBackground(Color.white);
			specialFieldsP1[i].setBorder(BorderFactory.createLineBorder(Color.red, 1));
			specialFieldsP1[i].setOpaque(true);
			if(i != 0){ specialFieldsP1[i].setText(" " + (59 + i + 1 * 4)); }
			else{ specialFieldsP1[i].setText(" " + 0); }
		}
		specialFieldsP2 = new JLabel[5];
		for(int i = 0; i < specialFieldsP2.length; i++){
			specialFieldsP2[i] = new JLabel();
			specialFieldsP2[i].setBackground(Color.white);
			specialFieldsP2[i].setBorder(BorderFactory.createLineBorder(Color.green, 1));
			specialFieldsP2[i].setOpaque(true);
			if(i != 0){ specialFieldsP2[i].setText(" " + (59 + i + 2 * 4)); }
			else{ specialFieldsP2[i].setText(" " + 16); }
		}
		specialFieldsP3= new JLabel[5];
		for(int i = 0; i < specialFieldsP3.length; i++){
			specialFieldsP3[i] = new JLabel();
			specialFieldsP3[i].setBackground(Color.white);
			specialFieldsP3[i].setBorder(BorderFactory.createLineBorder(Color.blue, 1));
			specialFieldsP3[i].setOpaque(true);
			if(i != 0){ specialFieldsP3[i].setText(" " + (59 + i + 3 * 4)); }
			else{ specialFieldsP3[i].setText(" " + 32); }
		}
		specialFieldsP4 = new JLabel[5];
		for(int i = 0; i < specialFieldsP4.length; i++){
			specialFieldsP4[i] = new JLabel();
			specialFieldsP4[i].setBackground(Color.white);
			specialFieldsP4[i].setBorder(BorderFactory.createLineBorder(Color.yellow, 1));
			specialFieldsP4[i].setOpaque(true);
			if(i != 0){ specialFieldsP4[i].setText(" " + (59 + i + 4 * 4)); }
			else{ specialFieldsP4[i].setText(" " + 48); }
		}
		
		
		//row 1:
		for(int i = 0; i < 8; i++){
			boardPanel.add(dummyFields[i]);
		}
		boardPanel.add(specialFieldsP2[0]);
		for(int i = 15; i >= 12; i--){
			boardPanel.add(neutralFields[i]);
		}
		for(int i = 8; i < 16; i++){
			boardPanel.add(dummyFields[i]);
		}
		//row 2:
		for(int i = 16; i < 24; i++){
			boardPanel.add(dummyFields[i]);
		}
		boardPanel.add(neutralFields[17]);
		boardPanel.add(specialFieldsP2[1]);
		boardPanel.add(dummyFields[24]);
		boardPanel.add(dummyFields[25]);
		boardPanel.add(neutralFields[11]);
		for(int i = 26; i < 34; i++){
			boardPanel.add(dummyFields[i]);
		}
		//row 3:
		for(int i = 34; i < 42; i++){
			boardPanel.add(dummyFields[i]);
		}
		boardPanel.add(neutralFields[18]);
		boardPanel.add(dummyFields[42]);
		boardPanel.add(specialFieldsP2[2]);
		boardPanel.add(dummyFields[43]);
		boardPanel.add(neutralFields[10]);
		for(int i = 44; i < 52; i++){
			boardPanel.add(dummyFields[i]);
		}
		//row 4:
		for(int i = 52; i < 60; i++){
			boardPanel.add(dummyFields[i]);
		}
		boardPanel.add(neutralFields[19]);
		boardPanel.add(dummyFields[60]);
		boardPanel.add(specialFieldsP2[3]);
		boardPanel.add(dummyFields[61]);
		boardPanel.add(neutralFields[9]);
		for(int i = 62; i < 70; i++){
			boardPanel.add(dummyFields[i]);
		}
		//row 5:
		for(int i = 70; i < 78; i++){
			boardPanel.add(dummyFields[i]);
		}
		boardPanel.add(neutralFields[20]);
		boardPanel.add(dummyFields[78]);
		boardPanel.add(specialFieldsP2[4]);
		boardPanel.add(dummyFields[79]);
		boardPanel.add(neutralFields[8]);
		for(int i = 80; i < 88; i++){
			boardPanel.add(dummyFields[i]);
		}
		//row 6:
		for(int i = 88; i < 95; i++){
			boardPanel.add(dummyFields[i]);
		}
		boardPanel.add(neutralFields[21]);
		for(int i = 95; i < 100; i++){
			boardPanel.add(dummyFields[i]);
		}
		boardPanel.add(neutralFields[7]);
		for(int i = 100; i < 107; i++){
			boardPanel.add(dummyFields[i]);
		}
		//row 7:
		for(int i = 107; i < 113; i++){
			boardPanel.add(dummyFields[i]);
		}
		boardPanel.add(neutralFields[22]);
		for(int i = 113; i < 120; i++){
			boardPanel.add(dummyFields[i]);
		}
		boardPanel.add(neutralFields[6]);
		for(int i = 120; i < 126; i++){
			boardPanel.add(dummyFields[i]);
		}
		//row 8:
		for(int i = 126; i < 131; i++){
			boardPanel.add(dummyFields[i]);
		}
		boardPanel.add(neutralFields[23]);
		for(int i = 131; i < 140; i++){
			boardPanel.add(dummyFields[i]);
		}
		boardPanel.add(neutralFields[5]);
		for(int i = 140; i < 145; i++){
			boardPanel.add(dummyFields[i]);
		}
		//row 9:
		for(int i = 28; i >= 24; i--){
			boardPanel.add(neutralFields[i]);
		}
		for(int i = 145; i < 156; i++){
			boardPanel.add(dummyFields[i]);
		}
		for(int i = 4; i > 0; i--){
			boardPanel.add(neutralFields[i]);
		}
		boardPanel.add(specialFieldsP1[0]);
		//row 10:
		boardPanel.add(neutralFields[29]);
		for(int i = 156; i < 174; i++){
			boardPanel.add(dummyFields[i]);
		}
		boardPanel.add(specialFieldsP1[1]);
		boardPanel.add(neutralFields[63]);
		//row 11:
		boardPanel.add(neutralFields[30]);
		boardPanel.add(dummyFields[174]);
		boardPanel.add(specialFieldsP3[2]);
		boardPanel.add(specialFieldsP3[3]);
		boardPanel.add(specialFieldsP3[4]);
		for(int i = 175; i < 186; i++){
			boardPanel.add(dummyFields[i]);
		}
		boardPanel.add(specialFieldsP1[4]);
		boardPanel.add(specialFieldsP1[3]);
		boardPanel.add(specialFieldsP1[2]);
		boardPanel.add(dummyFields[186]);
		boardPanel.add(neutralFields[62]);
		//row 12:
		boardPanel.add(neutralFields[31]);
		boardPanel.add(specialFieldsP3[1]);
		for(int i = 187; i < 205; i++){
			boardPanel.add(dummyFields[i]);
		}
		boardPanel.add(neutralFields[61]);
		//row 13:
		boardPanel.add(specialFieldsP3[0]);
		for(int i = 33; i < 37; i++){
			boardPanel.add(neutralFields[i]);
		}
		for(int i = 205; i < 216; i++){
			boardPanel.add(dummyFields[i]);
		}
		for(int i = 56; i < 61; i++){
			boardPanel.add(neutralFields[i]);
		}
		//row 14:
		for(int i = 216; i < 221; i++){
			boardPanel.add(dummyFields[i]);
		}
		boardPanel.add(neutralFields[37]);
		for(int i = 221; i < 230; i++){
			boardPanel.add(dummyFields[i]);
		}
		boardPanel.add(neutralFields[55]);
		for(int i = 230; i < 235; i++){
			boardPanel.add(dummyFields[i]);
		}
		//row 15:
		for(int i = 235; i < 241; i++){
			boardPanel.add(dummyFields[i]);
		}
		boardPanel.add(neutralFields[38]);
		for(int i = 241; i < 248; i++){
			boardPanel.add(dummyFields[i]);
		}
		boardPanel.add(neutralFields[54]);
		for(int i = 248; i < 254; i++){
			boardPanel.add(dummyFields[i]);
		}
		//row 16:
		for(int i = 254; i < 261; i++){
			boardPanel.add(dummyFields[i]);
		}
		boardPanel.add(neutralFields[39]);
		for(int i = 261; i < 266; i++){
			boardPanel.add(dummyFields[i]);
		}
		boardPanel.add(neutralFields[53]);
		for(int i = 266; i < 273; i++){
			boardPanel.add(dummyFields[i]);
		}
		//row 17:
		for(int i = 273; i < 281; i++){
			boardPanel.add(dummyFields[i]);
		}
		boardPanel.add(neutralFields[40]);
		boardPanel.add(dummyFields[281]);
		boardPanel.add(specialFieldsP4[4]);
		boardPanel.add(dummyFields[282]);
		boardPanel.add(neutralFields[52]);
		for(int i = 283; i < 291; i++){
			boardPanel.add(dummyFields[i]);
		}
		//row 18:
		for(int i = 291; i < 299; i++){
			boardPanel.add(dummyFields[i]);
		}
		boardPanel.add(neutralFields[41]);
		boardPanel.add(dummyFields[299]);
		boardPanel.add(specialFieldsP4[3]);
		boardPanel.add(dummyFields[300]);
		boardPanel.add(neutralFields[51]);
		for(int i = 301; i < 309; i++){
			boardPanel.add(dummyFields[i]);
		}
		//row 19:
		for(int i = 309; i < 317; i++){
			boardPanel.add(dummyFields[i]);
		}
		boardPanel.add(neutralFields[42]);
		boardPanel.add(dummyFields[317]);
		boardPanel.add(specialFieldsP4[2]);
		boardPanel.add(dummyFields[318]);
		boardPanel.add(neutralFields[50]);
		for(int i = 319; i < 327; i++){
			boardPanel.add(dummyFields[i]);
		}
		//row 20:
		for(int i = 327; i < 335; i++){
			boardPanel.add(dummyFields[i]);
		}
		boardPanel.add(neutralFields[43]);
		boardPanel.add(dummyFields[335]);
		boardPanel.add(dummyFields[336]);
		boardPanel.add(specialFieldsP4[1]);
		boardPanel.add(neutralFields[49]);
		for(int i = 337; i < 345; i++){
			boardPanel.add(dummyFields[i]);
		}
		//row 21:
		for(int i = 345; i < 353; i++){
			boardPanel.add(dummyFields[i]);
		}
		for(int i = 44; i <= 47; i++){
			boardPanel.add(neutralFields[i]);
		}
		boardPanel.add(specialFieldsP4[0]);
		for(int i = 353; i < 361; i++){
			boardPanel.add(dummyFields[i]);
		}
	}
	
	private void initiateCardPanel(){
		playerLabels = new JLabel[4];
		cardLabels = new JLabel[4][6];
		for(int i = 0; i < cardLabels.length; i++){
			cardPanel.add(new JLabel());
			cardPanel.add(playerLabels[i] = new JLabel("Player " + (i + 1) + ": "));
			JPanel playerCards = new JPanel(new GridLayout(1,6));
			for(int j = cardLabels[0].length - 1; j >= 0; j--){
				cardLabels[i][j] = new JLabel();
				playerCards.add(cardLabels[i][j]);
			}
			cardPanel.add(playerCards);
		}
		cardPanel.add(new JLabel());
	}
	
	public void update(byte[] board, int[][] cards, byte playerOnTurn) {
		updateBoard(board);
		updateCards(cards);
		updatePlayerLabels(playerOnTurn);
		this.repaint();
		
		if(this.timeout != 0){
			try {
				Thread.sleep(this.timeout);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void updatePlayerLabels(byte playerOnTurn) {
		for(byte i = 0; i < playerLabels.length; i++){
			playerLabels[i].setText("Player " + (i + 1) + (playerOnTurn - 1 == i ? ": *" :": "));
		}
	}

	private void updateBoard(byte[] board){
		assert board.length == 80;
		
		for(int i = 0; i < board.length; i++){
			//heaven fields
			if(i >= 64){
				int pos = i - 59;
				if(i < 68){ //p1
					pos -= 1 * 4;
					specialFieldsP1[pos].setBackground(board[i] == Players.ANY_SAVE ? Color.red : Color.white);
				}else if(i < 72){ //p2
					pos -= 2 * 4;
					specialFieldsP2[pos].setBackground(board[i] == Players.ANY_SAVE ? Color.green : Color.white);
				}else if(i < 76){ //p3
					pos -= 3 * 4;
					specialFieldsP3[pos].setBackground(board[i] == Players.ANY_SAVE ? Color.blue : Color.white);
				}else{ //p4
					pos -= 4 * 4;
					specialFieldsP4[pos].setBackground(board[i] == Players.ANY_SAVE ? Color.yellow : Color.white);
				}
				continue;
			}
			//home fields
			if(i == 0 || i == 16 || i == 32 || i == 48){
				if(i == 0){
					specialFieldsP1[0].setBackground(board[i] != 0 ? Color.red : Color.white);
				}
				if(i == 16){
					specialFieldsP2[0].setBackground(board[i] != 0 ? Color.green : Color.white);
				}
				if(i == 32){
					specialFieldsP3[0].setBackground(board[i] != 0 ? Color.blue : Color.white);
				}
				if(i == 48){
					specialFieldsP4[0].setBackground(board[i] != 0 ? Color.yellow : Color.white);
				}
				continue;
			}
			//normal fields
			if(board[i] != 0){
				switch(board[i]){
					case Players.P1:
						neutralFields[i].setBackground(Color.red);
						break;
					case Players.P2:
						neutralFields[i].setBackground(Color.green);
						break;
					case Players.P3:
						neutralFields[i].setBackground(Color.blue);
						break;
					case Players.P4:
						neutralFields[i].setBackground(Color.yellow);
						break;
				}
			}else{
				neutralFields[i].setBackground(Color.white);
			}
		}
	}
	
	private void updateCards(int[][] cards) {
		assert cards.length == 4 && cards[0].length == 6;
		for(int i = 0; i < cards.length; i++){
			for(int j = 0; j < cards[0].length; j++){
				setCardForLabel(cards[i][j], cardLabels[i][j]);
			}
		}
	}
	
	private void setCardForLabel(int card, JLabel cardLabel) {
		if(card == -1){
			cardLabel.setText("");
			return;
		}
		if(card == Cards.JOKER){
			cardLabel.setText("J*");
			cardLabel.setForeground(Color.GREEN);
			return;
		}else if(card < 14){
			cardLabel.setText("H: ");
			cardLabel.setForeground(Color.RED);
		}else if(card < 27){
			cardLabel.setText("S: ");
			cardLabel.setForeground(Color.BLACK);
		}else if(card < 40){
			cardLabel.setText("D: ");
			cardLabel.setForeground(Color.RED);
		}else if(card < 53){
			cardLabel.setText("C: ");
			cardLabel.setForeground(Color.BLACK);
		}else{
			assert card < 53;
			return;
		}
		if(card % 13 == 0){
			cardLabel.setText(cardLabel.getText() + "K");
		}else if(card % 13 == 12){
			cardLabel.setText(cardLabel.getText() + "Q");
		}else if(card % 13 == 11){
			cardLabel.setText(cardLabel.getText() + "J");
		}else if(card % 13 == 1){
			cardLabel.setText(cardLabel.getText() + "A");
		}else{
			cardLabel.setText(cardLabel.getText() + (card % 13));
		}
	}

	/**
	 * set the timeout to sleep after each update
	 * @param timeout the timeout to set
	 */
	public void setTimeout(long timeout) {
		this.timeout = timeout;
	}

	public static void main(String[] args){
		new GameWindow();
	}

}
