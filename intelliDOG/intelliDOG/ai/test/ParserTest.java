package intelliDOG.ai.test;

import java.util.IdentityHashMap;
import java.util.List;

import ch.bodesuri.applikation.client.pd.Karten;
import ch.bodesuri.dienste.observer.ObservableList;
import ch.bodesuri.dienste.serialisierung.Codierer;
import ch.bodesuri.pd.ProblemDomain;
import ch.bodesuri.pd.karten.*;
import ch.bodesuri.pd.regelsystem.Karte;
import ch.bodesuri.pd.spiel.Spiel;
import ch.bodesuri.pd.spiel.brett.*;
import ch.bodesuri.pd.spiel.spieler.Spieler;


import intelliDOG.ai.framework.Cards;
import intelliDOG.ai.framework.Parser;
import junit.framework.TestCase;

/**
 * 
 * @author kathrin
 *
 */
public class ParserTest extends TestCase {

	protected ch.bodesuri.applikation.client.pd.Spiel game; 
	protected Parser parser; 
	protected Spiel spiel;
	protected KartenGeber kartenGeber;
	protected Brett brett;
	protected Codierer codierer;
	protected ProblemDomain problemDomain; 
	protected List<Spieler> spieler; 
	
	protected ObservableList<Karte> cards = new ObservableList<Karte>();

	// Used, to use avoid duplicating code unnecessary
	protected static int count_init_cards; 
	
	protected void setUp() throws Exception  {

		problemDomain = new ProblemDomain();

		spiel       = problemDomain.getSpiel();
		kartenGeber = problemDomain.getKartenGeber();
		codierer    = problemDomain.getCodierer();

		brett = spiel.getBrett();

		for (int i = 0; i < 4; ++i) {
			spiel.fuegeHinzu("Spieler" + i);
		}

		spieler = spiel.getSpieler();
		spieler.get(0).setPartner(spieler.get(2));
		spieler.get(2).setPartner(spieler.get(0));
		spieler.get(1).setPartner(spieler.get(3));
		spieler.get(3).setPartner(spieler.get(1));
	
		game = new ch.bodesuri.applikation.client.pd.Spiel(problemDomain);
	
		parser = new Parser(); 
		
	}

	protected void tearDown() throws Exception {
		
	}

//	public void testConvert() {
//		fail("Not yet implemented");
//	}
	
	/**
	 * Set all players first pawn from initial position to its home field.
	 * Convert board and check that only those four positions are set.
	 */
	public void testconvertBoard_01() {
		
		assertEquals(brett.getAlleFelder().size(), 96); 
		setAllPlayersToHome(); 
		parser.convertBoard(game); 
		
		byte[] board = parser.getBoard();
		
		// Home fields are: 
		// -  0 for player 1
		// - 16 for player 2
		// - 32 for player 3
		// - 48 for player 4
		
		assertEquals(1, board[0]); 
		assertEquals(2, board[16]);
		assertEquals(3, board[32]);
		assertEquals(4, board[48]);
		
		assertFalse(board.length == 0); 
		
		// everything above 48 should be 0 as well as lower fields exept they are (0 mod 16) and <= 48  
		for(int i=0; i<board.length; i++)
			if(i > 48)
				assertEquals(0, board[i]); 
			else if(i%16 != 0)
				assertEquals(0, board[i]); 
			else 
				assertNotSame(0, board[i]); 
	}
	
	
	/**
	 * Move first pawn of every player from his homefield one field forward
	 *
	 */
	public void testconvertBoard_02() 
	{
		int nbr_pawns = 0; 
		
		assertEquals(brett.getAlleFelder().size(), 96); 
		setAllPlayersToHome();
		
		moveAllPlayersOneField(0); 
		parser.convertBoard(game); 
		
		byte[] board = parser.getBoard();
		
		// one move from the homefield means i%16 == 1
		// everything above 49 should be 0 as well as lower fields exept they are (1 mod 16) and <= 49 
		for(int i=0; i<board.length; i++)
		{
			if(i > 49)
				assertEquals(0, board[i]); 
			else if(i%16 != 1)
				assertEquals(0, board[i]); 
			else { 
				assertNotSame(0, board[i]);
				nbr_pawns++; 
			}	
		}
//		 there are only 4 pawn one the board
		assertEquals(nbr_pawns, 4); 
	}
	
	/**
	 *  Move all pawns of every player to his heaven fields
	 *
	 */
	public void testconvertBoard_03() 
	{
		int nbr_pawns = 0; 	
		assertEquals(brett.getAlleFelder().size(), 96); 
		moveAllPawnsToHeaven(); 
		parser.convertBoard(game); 
		byte[] board = parser.getBoard();
		
		for(int i=64; i<=67; i++)
			assertEquals(1, board[i]);
		for(int i=68; i<=71; i++)
			assertEquals(2, board[i]);
		for(int i=72; i<=75; i++)
			assertEquals(3, board[i]);
		for(int i=76; i<=79; i++)
			assertEquals(4, board[i]);
	
	
		for(int i=0; i<board.length; i++)
		{
			if(board[i] != 0) nbr_pawns++; 
		}
		// there are 16 pawn one the board
		assertEquals(16, nbr_pawns);
	}
	

	/**
	 *  Set first player's first pawn to home field and move 2 fields
	 *
	 */
	public void testconvertBoard_04() 
	{
		int nbr_pawns = 0; 	
		assertEquals(brett.getAlleFelder().size(), 96); 
		
		setPlayerToHome(0, 0); 
		move_N_Fields(0, 2, 0); 
		
		parser.convertBoard(game); 
		byte[] board = parser.getBoard();
		
		assertEquals(1, board[2]);
		
		for(int i=0; i<board.length; i++)
			if(board[i] != 0) 
				nbr_pawns++; 
		
		// there is 1 pawn one the board
		assertEquals(1, nbr_pawns);
	}
	
	
	/**
	 *  Set first and second player's first pawn to home field and move 4 fields
	 *
	 */
	public void testconvertBoard_05() 
	{
		int nbr_pawns = 0; 	
		assertEquals(96, brett.getAlleFelder().size()); 
		
		
		setPlayerToHome(0, 0);
		setPlayerToHome(1, 0);
		
		move_N_Fields(0, 4, 0); 
		move_N_Fields(1, 4, 0);
		
		parser.convertBoard(game); 
		byte[] board = parser.getBoard();
		
		assertEquals(1, board[4]);
		assertEquals(2, board[20]);
		
		for(int i=0; i<board.length; i++)
			if(board[i] != 0) 
				nbr_pawns++; 
		
		// there are 2 pawn one the board
		assertEquals(2, nbr_pawns);
	}
	
	/**
	 *  Set all pawn of every player first to his homefield and move: 
	 *  first  pawn move 15 forward
	 *  second pawn move 14 forward
	 *  third  pawn move 13 forward
	 *  fourth pawn move 12 forward
	 *
	 */
	public void testconvertBoard_06() 
	{
		int nbr_pawns = 0; 	
		assertEquals(brett.getAlleFelder().size(), 96); 
		
		// First player
		setPlayerToHome(0, 0);   // pawn: 0
		move_N_Fields(0, 15, 0);
		setPlayerToHome(0, 1);   // pawn: 1
		move_N_Fields(0, 14, 1);
		setPlayerToHome(0, 2);   // pawn: 2 
		move_N_Fields(0, 13, 2);	
		setPlayerToHome(0, 3);   // pawn: 3
		move_N_Fields(0, 12, 3);

		// Second player
		setPlayerToHome(1, 0);   // pawn: 0
		move_N_Fields(1, 15, 0);
		setPlayerToHome(1, 1);   // pawn: 1
		move_N_Fields(1, 14, 1);
		setPlayerToHome(1, 2);   // pawn: 2 
		move_N_Fields(1, 13, 2);	
		setPlayerToHome(1, 3);   // pawn: 3
		move_N_Fields(1, 12, 3);
	
		// Third player
		setPlayerToHome(2, 0);   // pawn: 0
		move_N_Fields(2, 15, 0);
		setPlayerToHome(2, 1);   // pawn: 1
		move_N_Fields(2, 14, 1);
		setPlayerToHome(2, 2);   // pawn: 2 
		move_N_Fields(2, 13, 2);	
		setPlayerToHome(2, 3);   // pawn: 3
		move_N_Fields(2, 12, 3);
		
//		 Fourth player
		setPlayerToHome(3, 0);   // pawn: 0
		move_N_Fields(3, 15, 0);
		setPlayerToHome(3, 1);   // pawn: 1
		move_N_Fields(3, 14, 1);
		setPlayerToHome(3, 2);   // pawn: 2 
		move_N_Fields(3, 13, 2);	
		setPlayerToHome(3, 3);   // pawn: 3
		move_N_Fields(3, 12, 3);
		
		parser.convertBoard(game); 
		byte[] board = parser.getBoard();
		
		assertEquals(1, board[15]);
		assertEquals(1, board[14]);
		assertEquals(1, board[13]);
		assertEquals(1, board[12]);
		
		assertEquals(2, board[31]);
		assertEquals(2, board[30]);
		assertEquals(2, board[29]);
		assertEquals(2, board[28]);
		
		assertEquals(3, board[47]);
		assertEquals(3, board[46]);
		assertEquals(3, board[45]);
		assertEquals(3, board[44]);
		
		assertEquals(4, board[63]);
		assertEquals(4, board[62]);
		assertEquals(4, board[61]);
		assertEquals(4, board[60]);
		
		for(int i=0; i<board.length; i++)
			if(board[i] != 0) 
				nbr_pawns++; 
		
		// there are 2 pawn one the board
		assertEquals(16, nbr_pawns);
	}
	
	
	/**
	 * First test situation: 
	 * Set every first pawn of every player to his homefield
	 *
	 */
	private void setAllPlayersToHome()
	{
		for(Spieler s_xx : spieler)
		{
			BankFeld homefield = s_xx.getSpiel().getBrett().getBankFeldVon(s_xx);
			ch.bodesuri.pd.spiel.brett.Feld fig = s_xx.getFiguren().get(0).getFeld(); 
			fig.versetzeFigurAuf(homefield);
		}
	}
	
	private void setPlayerToHome(int player, int pawn)
	{
		Spieler sp = game.getSpiel().getSpieler().get(player); 
		BankFeld homefield = sp.getSpiel().getBrett().getBankFeldVon(sp);
		ch.bodesuri.pd.spiel.brett.Feld fig = sp.getFiguren().get(pawn).getFeld(); 
		fig.versetzeFigurAuf(homefield);
	}
	
	
	/**
	 * Second test situation: 
	 * Move all player's first pawn one field forward
	 *
	 */
	private void moveAllPlayersOneField(int pawn)
	{
		for(Spieler s_xx : spieler)
		{
			ch.bodesuri.pd.spiel.brett.Feld p = s_xx.getFiguren().get(pawn).getFeld(); 
			p.versetzeFigurAuf(p.getNaechstes());
		}
	}
	
	
	/**
	 * Third test situation: 
	 * Move all player's pawns to heaven fields
	 *
	 */
	private void moveAllPawnsToHeaven()
	{
		int pawns = 4; 
		
		for(Spieler player : spieler)
		{
			for(int j=0; j<pawns; j++) 
			{	
				// get j-th heaven field and j-th pawn
				HimmelFeld heaven_field = player.getSpiel().getBrett().getHimmelFelderVon(player).get(j);
				ch.bodesuri.pd.spiel.brett.Feld fig = player.getFiguren().get(j).getFeld(); 
				fig.versetzeFigurAuf(heaven_field);
			}
		}
	}

	
	/**
	 * Fourth test situation: 
	 * Move players pawn n fields forward. We assume to be at the homefield. 
	 * Otherwise nextField() wouldn't work. 
	 *
	 */
	private void move_N_Fields(int _player, int nbr_fields, int pawn)
	{
		Spieler player = game.getSpiel().getSpieler().get(_player); 
			
	    // move players pawn i-th fields forward
		for(int j=0; j<nbr_fields; j++) 
		{
			ch.bodesuri.pd.spiel.brett.Feld p = player.getFiguren().get(pawn).getFeld();
			p.versetzeFigurAuf(p.getNaechstes()); 
		}
	}


	/**
	 * Test to verify that the cards of a player are converted correctly
	 * Those are the cards: 
	 * Kind:   Spades
	 * Number: 6
	 * Cards:  2 - 7  
	 */
	public void testconvertCards_01()
	{
		
		IdentityHashMap<Karte, ch.bodesuri.applikation.client.pd.Karte> cardMap = initCards(); 
		
		parser.convertCards(cardMap); 
		int[] cards = parser.getCards(); 
		
		sort_cards(cards, 0, cards.length-1); 
		
		assertEquals(cards.length, 6); // array is always six
		assertEquals(cards[0], Cards.SPADES_TWO); 
		assertEquals(cards[1], Cards.SPADES_THREE);
		assertEquals(cards[2], Cards.SPADES_FOUR);
		assertEquals(cards[3], Cards.SPADES_FIVE);
		assertEquals(cards[4], Cards.SPADES_SIX);
		assertEquals(cards[5], Cards.SPADES_SEVEN);
		
		
		for(int i=6; i<cards.length; i++)
			assertNull(cards[i]); // everything else must be null
	}
	
	
	/**
	 * Test to verify that the cards of a player are converted correctly
	 * These are the cards: 
	 * Kind:   Hearts
	 * Number: 6
	 * Cards:  2 - 7  
	 */
	public void testconvertCards_02()
	{
		IdentityHashMap<Karte, ch.bodesuri.applikation.client.pd.Karte> cardMap = initCards(); 
		
		parser.convertCards(cardMap); 
		int[] cards = parser.getCards(); 
		
		sort_cards(cards, 0, cards.length-1); 
		
		assertEquals(cards.length, 6); // array is always six
		assertEquals(cards[0], Cards.HEARTS_TWO); 
		assertEquals(cards[1], Cards.HEARTS_THREE);
		assertEquals(cards[2], Cards.HEARTS_FOUR);
		assertEquals(cards[3], Cards.HEARTS_FIVE);
		assertEquals(cards[4], Cards.HEARTS_SIX);
		assertEquals(cards[5], Cards.HEARTS_SEVEN);
		
		
		for(int i=6; i<cards.length; i++)
			assertNull(cards[i]); // everything else must be null

	}
	
	/**
	 * Test to verify that the cards of a player are converted correctly
	 * Those are the cards: 
	 * Kind:   Spades, Hearts
	 * Number: 5
	 * Cards:  8, 9, 10, J, Q  
	 */
	public void testconvertCards_03()
	{
		IdentityHashMap<Karte, ch.bodesuri.applikation.client.pd.Karte> cardMap = initCards(); 
		
		parser.convertCards(cardMap); 
		int[] cards = parser.getCards(); 
		
		sort_cards(cards, 0, cards.length-1); 
		
		// after sorting are empty (-1) entries at the beginning
		// Because there are only 5 cards set, the first has to be empty
		for(int i=0; i < 1; i++)
			assertEquals(-1, cards[i]);
		
		assertEquals(cards.length, 6); 
		assertEquals(cards[1], Cards.HEARTS_EIGHT); 
		assertEquals(cards[2], Cards.HEARTS_TEN);
		assertEquals(cards[3], Cards.HEARTS_QUEEN);
		assertEquals(cards[4], Cards.SPADES_NINE);
		assertEquals(cards[5], Cards.SPADES_JACK);
		
	}
	
	/**
	 * Initialize the cards for all convertCard() tests
	 * 
	 * @return card HashMap with set of cards
	 */
	private IdentityHashMap<Karte, ch.bodesuri.applikation.client.pd.Karte> initCards()
	{
		Karte two_sp = new Zwei(KartenFarbe.Pik); 
		Karte three_sp = new Drei(KartenFarbe.Pik); 
		Karte four_sp = new Vier(KartenFarbe.Pik); 
		Karte five_sp = new Fuenf(KartenFarbe.Pik); 
		Karte six_sp = new Sechs(KartenFarbe.Pik); 
		Karte seven_sp = new Sieben(KartenFarbe.Pik); 
		Karte eight_sp = new Acht(KartenFarbe.Pik); 
		Karte nine_sp = new Neun(KartenFarbe.Pik); 
		Karte ten_sp = new Zehn(KartenFarbe.Pik); 
		Karte jack_sp = new Bube(KartenFarbe.Pik); 
		Karte queen_sp = new Dame(KartenFarbe.Pik); 
		Karte king_sp = new Koenig(KartenFarbe.Pik); 
		Karte ace_sp = new Ass(KartenFarbe.Pik); 
		
		
		Karte two_h = new Zwei(KartenFarbe.Herz); 
		Karte three_h = new Drei(KartenFarbe.Herz); 
		Karte four_h = new Vier(KartenFarbe.Herz); 
		Karte five_h = new Fuenf(KartenFarbe.Herz); 
		Karte six_h = new Sechs(KartenFarbe.Herz); 
		Karte seven_h = new Sieben(KartenFarbe.Herz); 
		Karte eight_h = new Acht(KartenFarbe.Herz); 
		Karte nine_h = new Neun(KartenFarbe.Herz); 
		Karte ten_h = new Zehn(KartenFarbe.Herz); 
		Karte jack_h = new Bube(KartenFarbe.Herz); 
		Karte queen_h = new Dame(KartenFarbe.Herz); 
		Karte king_h = new Koenig(KartenFarbe.Herz); 
		Karte ace_h = new Ass(KartenFarbe.Herz); 
		
		ch.bodesuri.applikation.client.pd.Karte app_two_sp = new ch.bodesuri.applikation.client.pd.Karte(two_sp); 
		ch.bodesuri.applikation.client.pd.Karte app_three_sp = new ch.bodesuri.applikation.client.pd.Karte(three_sp); 
		ch.bodesuri.applikation.client.pd.Karte app_four_sp = new ch.bodesuri.applikation.client.pd.Karte(four_sp); 
		ch.bodesuri.applikation.client.pd.Karte app_five_sp = new ch.bodesuri.applikation.client.pd.Karte(five_sp); 
		ch.bodesuri.applikation.client.pd.Karte app_six_sp = new ch.bodesuri.applikation.client.pd.Karte(six_sp); 
		ch.bodesuri.applikation.client.pd.Karte app_seven_sp = new ch.bodesuri.applikation.client.pd.Karte(seven_sp); 
		ch.bodesuri.applikation.client.pd.Karte app_eight_sp = new ch.bodesuri.applikation.client.pd.Karte(eight_sp); 
		ch.bodesuri.applikation.client.pd.Karte app_nine_sp = new ch.bodesuri.applikation.client.pd.Karte(nine_sp); 
		ch.bodesuri.applikation.client.pd.Karte app_ten_sp = new ch.bodesuri.applikation.client.pd.Karte(ten_sp); 
		ch.bodesuri.applikation.client.pd.Karte app_jack_sp = new ch.bodesuri.applikation.client.pd.Karte(jack_sp); 
		ch.bodesuri.applikation.client.pd.Karte app_queen_sp = new ch.bodesuri.applikation.client.pd.Karte(queen_sp); 
		ch.bodesuri.applikation.client.pd.Karte app_king_sp = new ch.bodesuri.applikation.client.pd.Karte(king_sp); 
		ch.bodesuri.applikation.client.pd.Karte app_ace_sp = new ch.bodesuri.applikation.client.pd.Karte(ace_sp); 
		
		
		ch.bodesuri.applikation.client.pd.Karte app_two_h = new ch.bodesuri.applikation.client.pd.Karte(two_h); 
		ch.bodesuri.applikation.client.pd.Karte app_three_h = new ch.bodesuri.applikation.client.pd.Karte(three_h); 
		ch.bodesuri.applikation.client.pd.Karte app_four_h = new ch.bodesuri.applikation.client.pd.Karte(four_h); 
		ch.bodesuri.applikation.client.pd.Karte app_five_h = new ch.bodesuri.applikation.client.pd.Karte(five_h); 
		ch.bodesuri.applikation.client.pd.Karte app_six_h = new ch.bodesuri.applikation.client.pd.Karte(six_h); 
		ch.bodesuri.applikation.client.pd.Karte app_seven_h = new ch.bodesuri.applikation.client.pd.Karte(seven_h); 
		ch.bodesuri.applikation.client.pd.Karte app_eight_h = new ch.bodesuri.applikation.client.pd.Karte(eight_h); 
		ch.bodesuri.applikation.client.pd.Karte app_nine_h = new ch.bodesuri.applikation.client.pd.Karte(nine_h); 
		ch.bodesuri.applikation.client.pd.Karte app_ten_h = new ch.bodesuri.applikation.client.pd.Karte(ten_h); 
		ch.bodesuri.applikation.client.pd.Karte app_jack_h = new ch.bodesuri.applikation.client.pd.Karte(jack_h); 
		ch.bodesuri.applikation.client.pd.Karte app_queen_h = new ch.bodesuri.applikation.client.pd.Karte(queen_h); 
		ch.bodesuri.applikation.client.pd.Karte app_king_h = new ch.bodesuri.applikation.client.pd.Karte(king_h); 
		ch.bodesuri.applikation.client.pd.Karte app_ace_h = new ch.bodesuri.applikation.client.pd.Karte(ace_h); 
		
		Karten pd_cards = null; 

		// for convertcards_01
		if(count_init_cards == 0) {
			
			cards.add(two_sp); 
			cards.add(three_sp); 
			cards.add(four_sp); 
			cards.add(five_sp); 
			cards.add(six_sp); 
			cards.add(seven_sp); 
		
			pd_cards = new Karten(cards);
			pd_cards.add(app_two_sp); 
			pd_cards.add(app_three_sp);
			pd_cards.add(app_four_sp); 
			pd_cards.add(app_five_sp);
			pd_cards.add(app_six_sp); 
			pd_cards.add(app_seven_sp);
				
		} else if(count_init_cards == 1) { // convertcards_02
			
			cards.add(two_h); 
			cards.add(three_h); 
			cards.add(four_h); 
			cards.add(five_h); 
			cards.add(six_h); 
			cards.add(seven_h); 

			pd_cards = new Karten(cards);
			pd_cards.add(app_two_h); 
			pd_cards.add(app_three_h);
			pd_cards.add(app_four_h); 
			pd_cards.add(app_five_h);
			pd_cards.add(app_six_h); 
			pd_cards.add(app_seven_h);
			
		} else if(count_init_cards == 2) {	// convertcards_03
			
			cards.add(eight_h); 
			cards.add(nine_sp); 
			cards.add(ten_h); 
			cards.add(jack_sp); 
			cards.add(queen_h); 

			pd_cards = new Karten(cards);
			
			pd_cards.add(app_eight_h); 
			pd_cards.add(app_nine_sp);
			pd_cards.add(app_ten_h); 
			pd_cards.add(app_jack_sp); 
			pd_cards.add(app_queen_h);
			 
		} else
			System.out.println("nothing");
		
		// Not important which player's cards. Always use player 1 to test cards. 
		game.getSpieler().get(0).setKarten(pd_cards); 

		IdentityHashMap<Karte, ch.bodesuri.applikation.client.pd.Karte> cardMap	
				= new IdentityHashMap<Karte, ch.bodesuri.applikation.client.pd.Karte>();

		for (ch.bodesuri.applikation.client.pd.Karte k : game.getSpieler().get(0).getKarten()) {
			cardMap.put(k.getKarte(), k);
		}
		
		// increment counter for next test
		count_init_cards++;
		return cardMap; 
	}
	
	/**
	 * Because conversion of cards is done with a IdentityHashMap, there is no order of the elements.
	 * In order to verify the cards correctly, they are first sorted with quicksort. 
	 *
	 * @param cards cards to be sorted
	 * @param start start index
	 * @param end end index - number of cards
	 */
	private void sort_cards(int[] cards, int start, int end)
	{
		 if(end <= start) return;
		 
	     int x= cards[start];
	     int i = start; 
	     int j = end + 1;
	     
	     for(;;){
	            do i++; while(i<end && cards[i] < x);
	            do j--; while(j>start && cards[j] > x);
	            if(j <= i)   break;
	            int tmp = cards[i];
	            cards[i] = cards[j];
	            cards[j] = tmp;
	     }
	     cards[start] = cards[j];
	     cards[j] = x;
	     sort_cards(cards, start, j-1);
	     sort_cards(cards, j+1, end);
	}
	
	/**
	 * Use Joker as a specific card and verify it is translated 
	 * correctly. 
	 * @throws Exception
	 */
	public void testUnConvertCard() throws Exception
	{
		assertTrue(parser.unconvertCard(53) instanceof Ass);
		assertTrue(parser.unconvertCard(54) instanceof Zwei); 
		assertTrue(parser.unconvertCard(55) instanceof Drei); 
		assertTrue(parser.unconvertCard(56) instanceof Vier); 
		assertTrue(parser.unconvertCard(57) instanceof Fuenf); 
		assertTrue(parser.unconvertCard(58) instanceof Sechs);
		assertTrue(parser.unconvertCard(59) instanceof Sieben); 
		assertTrue(parser.unconvertCard(60) instanceof Acht); 
		assertTrue(parser.unconvertCard(61) instanceof Neun); 
		assertTrue(parser.unconvertCard(62) instanceof Zehn); 
		assertTrue(parser.unconvertCard(63) instanceof Bube); 
		assertTrue(parser.unconvertCard(64) instanceof Dame); 
		assertTrue(parser.unconvertCard(65) instanceof Koenig); 	
	}
	
	public void testUnConvertField() throws Exception
	{
		for(int i =0; i< 3; i++)
		{
			byte player = 1; 
			assertEquals(i*24, parser.unConvertFields(i*16, player));
			assertEquals(5 + i , parser.unConvertFields(64 + i,player));
		}
		
	}
	public void testUnConvertField2() throws Exception
	{
		byte player = 1;
		assertEquals(66, parser.unConvertFields(42, player));
		assertEquals(81, parser.unConvertFields(49, player));
		assertEquals(77, parser.unConvertFields(76, player));
	}
		
	public static void main(String[] args) {
	      junit.textui.TestRunner.run(ParserTest.class);
  }

	
	
}
