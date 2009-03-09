package intelliDOG.ai.ui;

import intelliDOG.ai.init.Init;
import intelliDOG.ai.utils.AIArena;
import intelliDOG.ai.utils.Statistics;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.DefaultTableModel;

/**
 * A JFrame extension which is used to start games and see some statistics
 *
 */
public class IntelliDOGStarter extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7333794369978190684L;
	
	private JRadioButton rbBodesuri;
	private JRadioButton rbIntelliDOG;
	private JCheckBox cbGUI;
	private JSpinner spTimeout;
	private JSpinner spGames;
	
	private JComboBox[] cobPlayers;
	private JComboBox[] cobEvaluators;
	private JTextField[] tfNamePlayers;
	private String[] botTypes;
	private String[] evalTypes;

	private JButton bStart;
	private JButton bStatistics;
	private JButton bExit;
	
	private JLabel lGameNr;
	private JLabel lAvgWinPercent;
	private JLabel lAvgWinHeight;
	private JLabel lAvgTime;
	private JLabel lAvgNrOfMoves;
	private JLabel lAvgTimePerMove;
	
	private JTable tblStatistics;
	
	
	private IntelliDOGStarter(){
		botTypes = new String[] {"AlphaBeta_V1", "AlphaBeta_V2", "ComplexBot", "N_StepBot", "RandomBot", 
				"SimpleBot", "Star1_Bot","Star2_Bot","Star2Bot_V1", "ThreeStepBot", "TwoStepBot"};
		evalTypes = new String[] {"Standard", "SE", "SE_V2", "SE_V3", "SE_V4", "SE_V5"};
		
		JPanel northPanel = new JPanel(new GridLayout(1,2));
		initNorthPanel(northPanel);
		
		JPanel southPanel = new JPanel();
		initSouthPanel(southPanel);
		
		JPanel eastPanel = new JPanel();
		initEastPanel(eastPanel);
		
		JPanel settingsPanel = new JPanel(new BorderLayout());
		settingsPanel.add(northPanel, BorderLayout.NORTH);
		settingsPanel.add(southPanel, BorderLayout.SOUTH);
		
		JPanel westPanel = new JPanel(new BorderLayout());
		westPanel.setPreferredSize(new Dimension(250, 400));
		JPanel statisticsPanel = new JPanel();
		initStatisticsPanel(statisticsPanel);
		westPanel.add(statisticsPanel, BorderLayout.NORTH);
		initTblStatistics();
		JScrollPane scrollPane = new JScrollPane(tblStatistics);
		tblStatistics.setFillsViewportHeight(true);
		westPanel.add(scrollPane, BorderLayout.CENTER);
		
		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.add(settingsPanel, BorderLayout.CENTER);
		mainPanel.add(eastPanel, BorderLayout.EAST);
		mainPanel.add(westPanel, BorderLayout.WEST);
		
		this.add(mainPanel);
		
		this.setLocation(new Point(200, 200));
		this.pack();
		this.setTitle("intelliDOG");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		Statistics.setIds(this);
	}
	
	private void initTblStatistics() {
		Object[] colNames = new Object[]{"Nr.", "Win", "Res", "Time", "# Moves"};
		DefaultTableModel dtm = new DefaultTableModel(colNames, 0){
			private static final long serialVersionUID = 4748692690199151862L;
			@Override
			public boolean isCellEditable(int rowIndex, int vColIndex) {
				return false;
			}
		};
		tblStatistics = new JTable(dtm);
	}

	private void initStatisticsPanel(JPanel statisticsPanel) {
		statisticsPanel.setLayout(new GridLayout(6,2));
		statisticsPanel.setBackground(Color.PINK);
		statisticsPanel.setPreferredSize(new Dimension(250, 200));
		
		lGameNr = new JLabel();
		lAvgWinPercent = new JLabel();
		lAvgWinHeight = new JLabel();
		lAvgTime = new JLabel();
		lAvgNrOfMoves = new JLabel();
		lAvgTimePerMove = new JLabel();
		
		statisticsPanel.add(new JLabel("Game #: "));
		statisticsPanel.add(lGameNr);
		statisticsPanel.add(new JLabel("Win % (T1/T2): "));
		statisticsPanel.add(lAvgWinPercent);
		statisticsPanel.add(new JLabel("Avg. win height: "));
		statisticsPanel.add(lAvgWinHeight);
		statisticsPanel.add(new JLabel("Avg. time: "));
		statisticsPanel.add(lAvgTime);
		statisticsPanel.add(new JLabel("Avg. # of moves: "));
		statisticsPanel.add(lAvgNrOfMoves);
		statisticsPanel.add(new JLabel("Avg. time/move: "));
		statisticsPanel.add(lAvgTimePerMove);
		
		
	}

	private void initNorthPanel(JPanel northPanel){
		//northPanel.setBackground(Color.BLUE);
		northPanel.setPreferredSize(new Dimension(700, 200));
		
		JPanel northWestPanel = new JPanel(new GridLayout(4,1));
		northWestPanel.setBorder(BorderFactory.createTitledBorder("Framework"));
		ButtonGroup bgFramework = new ButtonGroup();
		rbBodesuri = new JRadioButton("Bodesuri");
		rbIntelliDOG = new JRadioButton("intelliDOG");
		rbIntelliDOG.setSelected(true);
		northWestPanel.add(rbBodesuri);
		northWestPanel.add(rbIntelliDOG);
		bgFramework.add(rbBodesuri);
		bgFramework.add(rbIntelliDOG);
		
		JPanel northEastPanel = new JPanel(new GridLayout(4,2));
		northEastPanel.setBorder(BorderFactory.createTitledBorder("Options"));
		cbGUI = new JCheckBox("GUI");
		spTimeout = new JSpinner(new SpinnerNumberModel(500, 0, 10000, 500));
		spGames = new JSpinner(new SpinnerNumberModel(1, 1, 100000, 1));
		northEastPanel.add(new JLabel());
		northEastPanel.add(cbGUI);
		northEastPanel.add(new JLabel("Timeout: "));
		northEastPanel.add(spTimeout);
		northEastPanel.add(new JLabel("Nr. of Games: "));
		northEastPanel.add(spGames);
		
		northPanel.add(northWestPanel);
		northPanel.add(northEastPanel);
	}
	
	private void initSouthPanel(JPanel southPanel){
		//southPanel.setBackground(Color.RED);
		southPanel.setLayout(new GridLayout(4,1));
		southPanel.setPreferredSize(new Dimension(700, 200));
		southPanel.setBorder(BorderFactory.createTitledBorder("Players: "));
		cobPlayers = new JComboBox[4];
		cobEvaluators = new JComboBox[4];
		tfNamePlayers = new JTextField[4];
		
		for(int i = 0; i < 4; i++){
			JPanel pPi = new JPanel(new GridLayout(1,7));
			cobPlayers[i] = new JComboBox(botTypes);
			cobEvaluators[i] = new JComboBox(evalTypes);
			tfNamePlayers[i] = new JTextField("Bot" + (i + 1), 12);
			pPi.add(new JLabel("Player " + (i + 1) + ": "));
			JPanel temp = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 12));
			temp.add(new JLabel("Type: "));
			pPi.add(temp);
			pPi.add(cobPlayers[i]);
			temp = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 12));
			temp.add(new JLabel("Evaluator: "));
			pPi.add(temp);
			pPi.add(cobEvaluators[i]);
			temp = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 12));
			temp.add(new JLabel("Name: "));
			pPi.add(temp);
			pPi.add(tfNamePlayers[i]);
			southPanel.add(pPi);
		}
	}
	
	private void initEastPanel(JPanel eastPanel){
		//eastPanel.setBackground(Color.GREEN);
		eastPanel.setPreferredSize(new Dimension(120, 400));
		eastPanel.setLayout(new GridLayout(20, 1));
		
		bStatistics = new JButton("Statistics");
		bStatistics.setEnabled(false);
		bStart = new JButton("Start");
		bExit = new JButton("Exit");
		addListeners();
		
		for(int i = 0; i < 14; i++){
			eastPanel.add(new JLabel());
		}
		eastPanel.add(bStatistics);
		eastPanel.add(new JLabel());
		eastPanel.add(bStart);
		eastPanel.add(new JLabel());
		eastPanel.add(bExit);
		eastPanel.add(new JLabel());
	}
	
	private void addListeners(){
		bStart.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				resetStats();
				String[] botClassNames = new String[4];
				String[] botNames = new String[4];
				for(int i = 0; i < 4; i++){
					botClassNames[i] = "intelliDOG.ai.bots." + cobPlayers[i].getSelectedItem().toString();
					botNames [i] = tfNamePlayers[i].getText();
				}
				if(rbIntelliDOG.isSelected()){ //start over intelliDOG
					Init ioa = new Init(getThis());
					ioa.start();
					/*ioa.startGame(cbGUI.isSelected(), (Integer)spTimeout.getValue(), 
							(Integer)spGames.getValue(), botClassNames, botNames);
					ioa.setParams(cbGUI.isSelected(), (Integer)spTimeout.getValue(), 
							(Integer)spGames.getValue(), botClassNames, botNames);*/
				}else{ //start over bodesuri
					AIArena aia = new AIArena(getThis());
					aia.start();
				}
			}});
		
		bExit.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}});
		
		bStatistics.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Statistics.printTimeTable();
				Statistics.clearTime();
			}});
		rbBodesuri.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				for(int i = 0; i < cobEvaluators.length; i++){
					cobEvaluators[i].setEnabled(false);
				}
				spTimeout.setEnabled(false);
			}
		});
		rbIntelliDOG.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				for(int i = 0; i < cobEvaluators.length; i++){
					cobEvaluators[i].setEnabled(true);
				}
				spTimeout.setEnabled(true);
			}
		});
	}
	
	/**
	 * checks if the <class>JCheckBox</class> cbGUI is selected
	 * @return true if cbGUI is selected, false otherwise
	 */
	public boolean isGUISelected(){
		return cbGUI.isSelected();
	}
	
	/**
	 * gets the timeout from the <class>JSpinner</class> spTimeout
	 * @return the value of spTimeout
	 */
	public int getTimeout(){
		return (Integer)spTimeout.getValue();
	}
	
	/**
	 * gets the nr of games form the <class>JSpinner</class> spGames
	 * @return the value of spGames
	 */
	public int getNrOfGames(){
		return (Integer)spGames.getValue();
	}
	
	/**
	 * gets the types of the bots (selected in the <class>JComboBox</class>es)
	 * @return the selected items (with an the prefix for the right package)
	 */
	public String[] getBotClassNames(){
		String[] botClassNames = new String[4];
		for(int i = 0; i < 4; i++){
			botClassNames[i] = "intelliDOG.ai.bots." + cobPlayers[i].getSelectedItem().toString();
		}
		return botClassNames;
	}
	
	/**
	 * gets the names of the bots (specified in the <class>JTextField</class>s)
	 * @return the bot names
	 */
	public String[] getBotNames(){
		String[] botNames = new String[4];
		for(int i = 0; i < 4; i++){
			botNames[i] = tfNamePlayers[i].getText();
		}
		return botNames;
	}
	
	/**
	 * gets the types of the evaluators (selected in the <class>JComboBox</class>es)
	 * @return the selected items (with some name replacements and a prefix
	 * for the right package)
	 */
	public String[] getEvaluators(){
		String[] evaluators = new String[4];
		for(int i = 0; i < 4; i++){
			if(cobEvaluators[i].getSelectedItem().toString().equals("Standard")){
				evaluators[i] = "Standard";
			}else{
				evaluators[i] = "intelliDOG.ai.evaluators." + cobEvaluators[i].getSelectedItem().toString().replace("_", "").replace("SE", "SimpleEvaluator");
			}
		}
		return evaluators;
	}
	
	private IntelliDOGStarter getThis(){
		return this;
	}
	
	/**
	 * method to update the game statistics
	 * @param gameNr the number of the current game
	 * @param winningTeam the team that has won
	 * @param pieceCount1 the pieces in heaven of team one
	 * @param pieceCount2 the pieces in heaven of team two
	 * @param time the duration the game took
	 * @param moves the number of moves that where made in this game
	 */
	public void addGameStat(int gameNr, int winningTeam, int pieceCount1, int pieceCount2, double time, int moves){
		//Object[] colNames = new Object[]{"Nr.", "Win", "Res", "Time", "# Moves"};
		DefaultTableModel dtm = (DefaultTableModel)tblStatistics.getModel();
		dtm.addRow(new Object[] {gameNr, winningTeam, pieceCount1 + "/" + pieceCount2, Math.round(time * 100.) / 100., moves});
		
		lGameNr.setText("" + gameNr);
		double winPerc = 0;
		double resultAvg1 = 0;
		double resultAvg2 = 0;
		double timeAvg = 0;
		int moveAvg = 0;
		double timePMoveAvg = 0;
		
		for(int i = 0; i < dtm.getRowCount(); i++){
			winPerc += (Integer)dtm.getValueAt(i, 1) == 1 ? 1 : 0;
			resultAvg1 += Integer.parseInt(((String)dtm.getValueAt(i, 2)).substring(0, 1));
			resultAvg2 += Integer.parseInt(((String)dtm.getValueAt(i, 2)).substring(2, 3));
			double t = (Double)dtm.getValueAt(i, 3);
			int m = (Integer)dtm.getValueAt(i, 4);
			timeAvg += t;
			moveAvg += m;
			timePMoveAvg += t / m;
		}
		
		winPerc = Math.round((winPerc / gameNr * 100.0) * 100.) / 100.;
		resultAvg1 = Math.round((resultAvg1 / gameNr) * 100.) / 100.;
		resultAvg2 = Math.round((resultAvg2 / gameNr) * 100.) / 100.;
		timeAvg = Math.round((timeAvg / gameNr) * 100.) / 100.;
		moveAvg = moveAvg / gameNr;
		timePMoveAvg = Math.round((timePMoveAvg / gameNr) * 100.) / 100.;
		
		lAvgWinPercent.setText("" + winPerc + "/" + (100 - winPerc) + "%");
		lAvgWinHeight.setText(resultAvg1 + "/" + resultAvg2);
		lAvgTime.setText("" + timeAvg);
		lAvgNrOfMoves.setText("" + moveAvg);
		lAvgTimePerMove.setText("" + timePMoveAvg);
	}
	
	/**
	 * this method will reset all statistics
	 */
	private void resetStats(){
		DefaultTableModel dtm = (DefaultTableModel)tblStatistics.getModel();
		int rowCount = dtm.getRowCount() - 1;
		for(int i = rowCount; i >= 0; i--){
			dtm.removeRow(i);
		}
		lGameNr.setText("");
		lAvgWinPercent.setText("");
		lAvgWinHeight.setText("");
		lAvgTime.setText("");
		lAvgNrOfMoves.setText("");
		lAvgTimePerMove.setText("");
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new IntelliDOGStarter();
	}

}
