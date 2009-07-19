package intelliDOG.ai.framework;

import intelliDOG.ai.utils.DebugMsg;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import ch.bodesuri.applikation.client.events.ZugErfasstEvent;
import ch.bodesuri.applikation.client.pd.Feld;
import ch.bodesuri.pd.karten.Acht;
import ch.bodesuri.pd.karten.Ass;
import ch.bodesuri.pd.karten.Bube;
import ch.bodesuri.pd.karten.Dame;
import ch.bodesuri.pd.karten.Drei;
import ch.bodesuri.pd.karten.Fuenf;
import ch.bodesuri.pd.karten.Joker;
import ch.bodesuri.pd.karten.KartenFarbe;
import ch.bodesuri.pd.karten.Koenig;
import ch.bodesuri.pd.karten.Neun;
import ch.bodesuri.pd.karten.Sechs;
import ch.bodesuri.pd.karten.Sieben;
import ch.bodesuri.pd.karten.Vier;
import ch.bodesuri.pd.karten.Zehn;
import ch.bodesuri.pd.karten.Zwei;
import ch.bodesuri.pd.regelsystem.Karte;
import ch.bodesuri.pd.zugsystem.Bewegung;




/**
 * 
 * This class converts the input and all necessary information from Bodesuri 
 * to our representation of the game. 
 * TODO eventuell die 100 if/else in ein key/value struktur umwandeln. 
 */

public class Parser {

	private static int ORIG_HOMEFIELD_P1 = 0; 
	private static int ORIG_HOMEFIELD_P2 = 24;
	private static int ORIG_HOMEFIELD_P3 = 48;
	private static int ORIG_HOMEFIELD_P4 = 72;
	

	private ch.bodesuri.applikation.client.pd.Spiel actualGame;
	
	private byte[] board = new byte [80];
	private byte[] players = new byte[4]; 
	private int[] cards = new int[6]; 
	private byte playerOnTurn = 0; 
	private byte myPlayer = 0; 
	private int nrOfIteration = 0; 
	//pretty mean hack to overcome the bodesuris object style..
	private ch.bodesuri.applikation.client.pd.Karte[] myCards = new ch.bodesuri.applikation.client.pd.Karte[101];
	
	List<Feld> orig_fields = null; 
	
	private DebugMsg msg = DebugMsg.getInstance(); 
	
	public Parser() 
	{
		players[0] = Players.P1; // Nr. 1
		players[1] = Players.P2; // Nr. 2
		players[2] = Players.P3; // Nr. 3
		players[3] = Players.P4; // Nr. 4
	}
	
	public void convert(ch.bodesuri.applikation.client.pd.Spiel game, Map<Karte, ch.bodesuri.applikation.client.pd.Karte> cardMap, BotBoard botBoard, InformationGatherer ig)
	{
		playerOnTurn = (byte)players[game.aktuellerSpieler.getSpieler().getNummer()]; 
		myPlayer = 	    (byte)players[game.spielerIch.getSpieler().getNummer()];  
		nrOfIteration = game.aktuellerSpieler.getKarten().size(); 
		msg.debug(this, "Name: " + game.aktuellerSpieler.getName() + ", bodesuri Nr. : " + game.aktuellerSpieler.getSpieler().getNummer());  
		msg.debug(this, "playerOnTurn: " + playerOnTurn + ", myPlayer: " + myPlayer + ", nrOfIteration: "+ nrOfIteration); 
		actualGame = game; 
		
		convertBoard(game); 
		convertCards(cardMap); 
		
		botBoard.setBoard(board);
		ig.setCardsForPlayer(cards, playerOnTurn);
		ig.setMyPlayer(myPlayer);
	}

	
	/**
	 * convert every field on the board from bodesuri to our board array 
	 * @param game the game to be converted
	 */
	public void convertBoard(ch.bodesuri.applikation.client.pd.Spiel game)
	{
		orig_fields = game.getBrett().getAlleFelder(); 
		int boardIndex = 0; 

		/* First player's home field */
		
		if(orig_fields.get(ORIG_HOMEFIELD_P1).istBesetzt())
		{
			msg.debug(this, orig_fields.get(ORIG_HOMEFIELD_P1).getFigur().getSpieler().toString() + ", " +  
					orig_fields.get(ORIG_HOMEFIELD_P1).getFigur().getSpieler().getFarbe().toString()); 	
			if(game.getSpiel().getBrett().getAlleFelder().get(ORIG_HOMEFIELD_P1).istGeschuetzt())
			{
				board[boardIndex++] = Players.ANY_SAVE;
			}
			else
			{
				int nbr = getPlayersNbr(ORIG_HOMEFIELD_P1);
				board[boardIndex++] = players[nbr];
			}
		} else
			    board[boardIndex++] = 0; 
		
		

		/*  Field 9 to 23 is normal */ 

		for(int i=9; i<=23; i++, boardIndex++)
		{
			msg.debug(this, orig_fields.get(i).getFeld().toString());
			if(orig_fields.get(i).istBesetzt())
			{
				msg.debug(this, orig_fields.get(i).getFigur().getSpieler().toString() + ", " +  
						orig_fields.get(i).getFigur().getSpieler().getFarbe().toString());

				int nbr = getPlayersNbr(i); 
				board[boardIndex] = players[nbr]; 
			} else
				board[boardIndex] = 0; 

		}

		/* boardIndex has to be 16 (Homefield of player 2) */ 
		if(orig_fields.get(ORIG_HOMEFIELD_P2).istBesetzt())
		{
			msg.debug(this, orig_fields.get(ORIG_HOMEFIELD_P2).getFigur().getSpieler().toString() + ", " +  
					orig_fields.get(ORIG_HOMEFIELD_P2).getFigur().getSpieler().getFarbe().toString()); 	
			if(game.getSpiel().getBrett().getAlleFelder().get(ORIG_HOMEFIELD_P2).istGeschuetzt())
			{
				board[boardIndex++] = Players.ANY_SAVE;
			}
			else
			{
				int nbr = getPlayersNbr(ORIG_HOMEFIELD_P2); 
				board[boardIndex++] = players[nbr];
			}
		} else
			board[boardIndex++] = 0; 


		/*  Field 33 to 47 is normal */ 

		for(int i=33; i<=47; i++, boardIndex++)
		{
			msg.debug(this, orig_fields.get(i).getFeld().toString());
			if(orig_fields.get(i).istBesetzt())
			{
				msg.debug(this, orig_fields.get(i).getFigur().getSpieler().toString() + ", " +  
						orig_fields.get(i).getFigur().getSpieler().getFarbe().toString());
				int nbr = getPlayersNbr(i); 

				board[boardIndex] = players[nbr]; 
			} else
				board[boardIndex] = 0; 

		}


		/* boardIndex has to be 32 (Homefield of player 3) */ 
		if(orig_fields.get(ORIG_HOMEFIELD_P3).istBesetzt())
		{
			msg.debug(this, orig_fields.get(ORIG_HOMEFIELD_P3).getFigur().getSpieler().toString() + ", " +  
					orig_fields.get(ORIG_HOMEFIELD_P3).getFigur().getSpieler().getFarbe().toString()); 	
			if(game.getSpiel().getBrett().getAlleFelder().get(ORIG_HOMEFIELD_P3).istGeschuetzt())
			{
				board[boardIndex++] = Players.ANY_SAVE;
			}
			else
			{
				int nbr = getPlayersNbr(ORIG_HOMEFIELD_P3); 
				board[boardIndex++] = players[nbr];
			}
		} else
			board[boardIndex++] = 0; 


		/*  Field 57 to 71 is normal */ 

		for(int i=57; i<=71; i++, boardIndex++)
		{
			msg.debug(this, orig_fields.get(i).getFeld().toString());
			if(orig_fields.get(i).istBesetzt())
			{
				msg.debug(this, orig_fields.get(i).getFigur().getSpieler().toString() + ", " +  
						orig_fields.get(i).getFigur().getSpieler().getFarbe().toString());

				int nbr = getPlayersNbr(i); 

				board[boardIndex] = players[nbr]; 
			} else
				board[boardIndex] = 0; 

		}

		/* boardIndex has to be 48 (Homefield of player 4) */ 
		if(orig_fields.get(ORIG_HOMEFIELD_P4).istBesetzt())
		{
			msg.debug(this, orig_fields.get(ORIG_HOMEFIELD_P4).getFigur().getSpieler().toString() + ", " +  
					orig_fields.get(ORIG_HOMEFIELD_P4).getFigur().getSpieler().getFarbe().toString()); 	
			if(game.getSpiel().getBrett().getAlleFelder().get(ORIG_HOMEFIELD_P4).istGeschuetzt())
			{
				board[boardIndex++] = Players.ANY_SAVE;
			}
			else
			{
				int nbr = getPlayersNbr(ORIG_HOMEFIELD_P4); 
				board[boardIndex++] = players[nbr];
			}
		} else
			board[boardIndex++] = 0; 


		for(int i=81; i<=95; i++, boardIndex++)
		{
			msg.debug(this, orig_fields.get(i).getFeld().toString());
			if(orig_fields.get(i).istBesetzt())
			{
				msg.debug(this, orig_fields.get(i).getFigur().getSpieler().toString() + ", " +  
						orig_fields.get(i).getFigur().getSpieler().getFarbe().toString());

				int nbr = getPlayersNbr(i); 

				board[boardIndex] = players[nbr]; 
			} else
				board[boardIndex] = 0; 

		}

		/*
		 *  Set heaven fields for all players 
		 *
		 * First player's heaven
		 * field numbers 5 - 8
		 * 
		 * boardIndex has to start at 64
		 */
		for(int i=5; i<=8; i++, boardIndex++)
		{
			msg.debug(this, orig_fields.get(i).getFeld().toString());
			if(orig_fields.get(i).istBesetzt())
			{		
				msg.debug(this, orig_fields.get(i).getFigur().getSpieler().toString() + ", " +  
					orig_fields.get(i).getFigur().getSpieler().getFarbe().toString()); 	
				if(game.getSpiel().getBrett().getAlleFelder().get(i).istGeschuetzt())
				{
					board[boardIndex] = Players.ANY_SAVE;
				}
				else
				{
					int nbr = getPlayersNbr(i); 	
					board[boardIndex] = players[nbr]; 
				}
			}
		}

		/*
		 * Second player's heaven
		 * field numbers 29 - 32
		 * 
		 * boardIndex has to start at 68
		 */
		for(int i=29; i<=32; i++, boardIndex++)
		{
			msg.debug(this, orig_fields.get(i).getFeld().toString());
			if(orig_fields.get(i).istBesetzt())
			{
				msg.debug(this, orig_fields.get(i).getFigur().getSpieler().toString() + ", " +  
						orig_fields.get(i).getFigur().getSpieler().getFarbe().toString()); 	
				if(game.getSpiel().getBrett().getAlleFelder().get(i).istGeschuetzt())
				{
					board[boardIndex] = Players.ANY_SAVE;
				}
				else
				{
					int nbr = getPlayersNbr(i); 	
					board[boardIndex] = players[nbr]; 
				}
			}
		}

		/*
		 * Third player's heaven
		 * field numbers 53 - 56
		 * 
		 * boardIndex has to start at 72
		 */
		for(int i=53; i<=56; i++, boardIndex++)
		{
			msg.debug(this, orig_fields.get(i).getFeld().toString());
			if(orig_fields.get(i).istBesetzt())
			{
				msg.debug(this, orig_fields.get(i).getFigur().getSpieler().toString() + ", " +  
						orig_fields.get(i).getFigur().getSpieler().getFarbe().toString());
				if(game.getSpiel().getBrett().getAlleFelder().get(i).istGeschuetzt())
				{
					board[boardIndex] = Players.ANY_SAVE; 
				}
				else
				{
					int nbr = getPlayersNbr(i); 	
					board[boardIndex] = players[nbr]; 
				}
			}
		}

		/*
		 * Fourth player's heaven
		 * field numbers 77 - 80
		 * 
		 * boardIndex has to start at 76
		 */
		for(int i=77; i<=80; i++, boardIndex++)
		{
			msg.debug(this, orig_fields.get(i).getFeld().toString());
			if(orig_fields.get(i).istBesetzt())
			{
				msg.debug(this, orig_fields.get(i).getFigur().getSpieler().toString() + ", " +  
						orig_fields.get(i).getFigur().getSpieler().getFarbe().toString());	
				if(game.getSpiel().getBrett().getAlleFelder().get(i).istGeschuetzt())
				{
					board[boardIndex] = Players.ANY_SAVE; 
				}
				else
				{
					int nbr = getPlayersNbr(i); 	
					board[boardIndex] = players[nbr]; 
				}
			}
		}
		/* boardIndex has to be 80 */ 
		assert boardIndex == 80; 

    } // end convertBoard		
	
	
	/**
	 * Converts all cards from the Map into an array
	 * 
	 * @param cardMap
	 */
	public void convertCards(Map<Karte, ch.bodesuri.applikation.client.pd.Karte> cardMap)
	{
		int cardIndex = 0;
		Iterator<ch.bodesuri.applikation.client.pd.Karte> it = cardMap.values().iterator(); 
		int countCards = 0; 
		
		while(it.hasNext())
		{
			countCards++; 
			ch.bodesuri.applikation.client.pd.Karte k = it.next(); 

			if(k.getKarte() instanceof Joker) {
				cards[cardIndex++] = Cards.JOKER;
				//insert the joker into our mapping array
				myCards[100] = k;
				
			} else if(k.getKarte().getFarbe().equals("Herz")) {
				cardIndex = hearts(k, cardIndex); 
				
			} else if(k.getKarte().getFarbe().equals("Karo")) { 
				cardIndex = diamonds(k, cardIndex); 
				
			} else if(k.getKarte().getFarbe().equals("Kreuz")) {
				cardIndex = clubs(k, cardIndex); 
				
			} else if(k.getKarte().getFarbe().equals("Pik")) {
				cardIndex = spades(k, cardIndex); 			
			}

			msg.debug(this, k.getKarte().getClass().toString() + ", " + k.getKarte().getFarbe().toString()); 
		}
		// Fill rest of array with -1 
		if(countCards != 6)
		{
			for(int i = countCards; i<cards.length; i++)
				cards[i] = -1; 
		}
	}
	
	private int hearts(ch.bodesuri.applikation.client.pd.Karte card, int cardIndex)
	{
		//because the bodesuri uses the standard equals method to check if 2 cards are the same, we have to adapt..
		//in this case, we store the object information in a mapping array to recover it easily. 
		if (card.getKarte() instanceof Ass) { cards[cardIndex++] = Cards.HEARTS_ACE; myCards[Cards.HEARTS_ACE] = card;  }
		else if (card.getKarte() instanceof Zwei) { cards[cardIndex++] = Cards.HEARTS_TWO;  myCards[Cards.HEARTS_TWO] = card;  }
		else if (card.getKarte() instanceof Drei) { cards[cardIndex++] = Cards.HEARTS_THREE;  myCards[Cards.HEARTS_THREE] = card; }
		else if (card.getKarte() instanceof Vier) { cards[cardIndex++] = Cards.HEARTS_FOUR;  myCards[Cards.HEARTS_FOUR] = card; }
		else if (card.getKarte() instanceof Fuenf) { cards[cardIndex++] = Cards.HEARTS_FIVE;  myCards[Cards.HEARTS_FIVE] = card; }
		else if (card.getKarte() instanceof Sechs) { cards[cardIndex++] = Cards.HEARTS_SIX;  myCards[Cards.HEARTS_SIX] = card; }
		else if (card.getKarte() instanceof Sieben) { cards[cardIndex++] = Cards.HEARTS_SEVEN;  myCards[Cards.HEARTS_SEVEN] = card; }
		else if (card.getKarte() instanceof Acht) { cards[cardIndex++] = Cards.HEARTS_EIGHT;  myCards[Cards.HEARTS_EIGHT] = card; }
		else if (card.getKarte() instanceof Neun) { cards[cardIndex++] = Cards.HEARTS_NINE;  myCards[Cards.HEARTS_NINE] = card; }
		else if (card.getKarte() instanceof Zehn) { cards[cardIndex++] = Cards.HEARTS_TEN;  myCards[Cards.HEARTS_TEN] = card; }
		else if (card.getKarte() instanceof Bube) { cards[cardIndex++] = Cards.HEARTS_JACK;  myCards[Cards.HEARTS_JACK] = card; }
		else if (card.getKarte() instanceof Dame) { cards[cardIndex++] = Cards.HEARTS_QUEEN;  myCards[Cards.HEARTS_QUEEN] = card; }
		else if (card.getKarte() instanceof Koenig) { cards[cardIndex++] = Cards.HEARTS_KING;  myCards[Cards.HEARTS_KING] = card; }
		return cardIndex;
	}
	
	private int diamonds(ch.bodesuri.applikation.client.pd.Karte card, int cardIndex)
	{
		if (card.getKarte() instanceof Ass) { cards[cardIndex++] = Cards.DIAMONDS_ACE;  myCards[Cards.DIAMONDS_ACE] = card; }
		else if (card.getKarte() instanceof Zwei) { cards[cardIndex++] = Cards.DIAMONDS_TWO;  myCards[Cards.DIAMONDS_TWO] = card; }
		else if (card.getKarte() instanceof Drei) { cards[cardIndex++] = Cards.DIAMONDS_THREE;  myCards[Cards.DIAMONDS_THREE] = card; }
		else if (card.getKarte() instanceof Vier) { cards[cardIndex++] = Cards.DIAMONDS_FOUR;  myCards[Cards.DIAMONDS_FOUR] = card; }
		else if (card.getKarte() instanceof Fuenf) { cards[cardIndex++] = Cards.DIAMONDS_FIVE;  myCards[Cards.DIAMONDS_FIVE] = card; }
		else if (card.getKarte() instanceof Sechs) { cards[cardIndex++] = Cards.DIAMONDS_SIX;  myCards[Cards.DIAMONDS_SIX] = card; }
		else if (card.getKarte() instanceof Sieben) { cards[cardIndex++] = Cards.DIAMONDS_SEVEN;  myCards[Cards.DIAMONDS_SEVEN] = card; }
		else if (card.getKarte() instanceof Acht) { cards[cardIndex++] = Cards.DIAMONDS_EIGHT;  myCards[Cards.DIAMONDS_EIGHT] = card; }
		else if (card.getKarte() instanceof Neun) { cards[cardIndex++] = Cards.DIAMONDS_NINE;  myCards[Cards.DIAMONDS_NINE] = card; }
		else if (card.getKarte() instanceof Zehn) { cards[cardIndex++] = Cards.DIAMONDS_TEN;  myCards[Cards.DIAMONDS_TEN] = card; }
		else if (card.getKarte() instanceof Bube) { cards[cardIndex++] = Cards.DIAMONDS_JACK;  myCards[Cards.DIAMONDS_JACK] = card; }
		else if (card.getKarte() instanceof Dame) { cards[cardIndex++] = Cards.DIAMONDS_QUEEN;  myCards[Cards.DIAMONDS_QUEEN] = card; }
		else if (card.getKarte() instanceof Koenig) { cards[cardIndex++] = Cards.DIAMONDS_KING;  myCards[Cards.DIAMONDS_KING] = card; }
		return cardIndex;
	}
	
	private int clubs(ch.bodesuri.applikation.client.pd.Karte card, int cardIndex)
	{
		if (card.getKarte() instanceof Ass) { cards[cardIndex++] = Cards.CLUBS_ACE;  myCards[Cards.CLUBS_ACE] = card;  }
		else if (card.getKarte() instanceof Zwei) { cards[cardIndex++] = Cards.CLUBS_TWO; myCards[Cards.CLUBS_TWO] = card;  }
		else if (card.getKarte() instanceof Drei) { cards[cardIndex++] = Cards.CLUBS_THREE; myCards[Cards.CLUBS_THREE] = card;  }
		else if (card.getKarte() instanceof Vier) { cards[cardIndex++] = Cards.CLUBS_FOUR; myCards[Cards.CLUBS_FOUR] = card;  }
		else if (card.getKarte() instanceof Fuenf) { cards[cardIndex++] = Cards.CLUBS_FIVE; myCards[Cards.CLUBS_FIVE] = card;  }
		else if (card.getKarte() instanceof Sechs) { cards[cardIndex++] = Cards.CLUBS_SIX; myCards[Cards.CLUBS_SIX] = card;  }
		else if (card.getKarte() instanceof Sieben) { cards[cardIndex++] = Cards.CLUBS_SEVEN; myCards[Cards.CLUBS_SEVEN] = card;  }
		else if (card.getKarte() instanceof Acht) { cards[cardIndex++] = Cards.CLUBS_EIGHT; myCards[Cards.CLUBS_EIGHT] = card;  }
		else if (card.getKarte() instanceof Neun) { cards[cardIndex++] = Cards.CLUBS_NINE; myCards[Cards.CLUBS_NINE] = card;  }
		else if (card.getKarte() instanceof Zehn) { cards[cardIndex++] = Cards.CLUBS_TEN; myCards[Cards.CLUBS_TEN] = card;  }
		else if (card.getKarte() instanceof Bube) { cards[cardIndex++] = Cards.CLUBS_JACK; myCards[Cards.CLUBS_JACK] = card;  }
		else if (card.getKarte() instanceof Dame) { cards[cardIndex++] = Cards.CLUBS_QUEEN; myCards[Cards.CLUBS_QUEEN] = card;  }
		else if (card.getKarte() instanceof Koenig) { cards[cardIndex++] = Cards.CLUBS_KING; myCards[Cards.CLUBS_KING] = card;  }
		return cardIndex;
	}
	
	private int spades(ch.bodesuri.applikation.client.pd.Karte card, int cardIndex)
	{
		if (card.getKarte() instanceof Ass) { cards[cardIndex++] = Cards.SPADES_ACE; myCards[Cards.SPADES_ACE] = card;  }
		else if (card.getKarte() instanceof Zwei) { cards[cardIndex++] = Cards.SPADES_TWO; myCards[Cards.SPADES_TWO] = card;  }
		else if (card.getKarte() instanceof Drei) { cards[cardIndex++] = Cards.SPADES_THREE; myCards[Cards.SPADES_THREE] = card;  }
		else if (card.getKarte() instanceof Vier) { cards[cardIndex++] = Cards.SPADES_FOUR; myCards[Cards.SPADES_FOUR] = card;  }
		else if (card.getKarte() instanceof Fuenf) { cards[cardIndex++] = Cards.SPADES_FIVE; myCards[Cards.SPADES_FIVE] = card;  }
		else if (card.getKarte() instanceof Sechs) { cards[cardIndex++] = Cards.SPADES_SIX; myCards[Cards.SPADES_SIX] = card;  }
		else if (card.getKarte() instanceof Sieben) { cards[cardIndex++] = Cards.SPADES_SEVEN; myCards[Cards.SPADES_SEVEN] = card;  }
		else if (card.getKarte() instanceof Acht) { cards[cardIndex++] = Cards.SPADES_EIGHT; myCards[Cards.SPADES_EIGHT] = card;  }
		else if (card.getKarte() instanceof Neun) { cards[cardIndex++] = Cards.SPADES_NINE; myCards[Cards.SPADES_NINE] = card;  }
		else if (card.getKarte() instanceof Zehn) { cards[cardIndex++] = Cards.SPADES_TEN; myCards[Cards.SPADES_TEN] = card;  }
		else if (card.getKarte() instanceof Bube) { cards[cardIndex++] = Cards.SPADES_JACK; myCards[Cards.SPADES_JACK] = card;  }
		else if (card.getKarte() instanceof Dame) { cards[cardIndex++] = Cards.SPADES_QUEEN; myCards[Cards.SPADES_QUEEN] = card;  }
		else if (card.getKarte() instanceof Koenig) { cards[cardIndex++] = Cards.SPADES_KING; myCards[Cards.SPADES_KING] = card;  }
		return cardIndex; 
	}
	
	/**
	 * This function returns the number of the player who is on 
	 * the field identified by field_index 
	 * 
	 */
	private int getPlayersNbr(int field_index)
	{
		/* match number of player in array */ 
		int nbr = orig_fields.get(field_index).getFigur().getSpieler().getNummer(); 
		msg.debug(this, "Field number: " + field_index + " Check for players number: " + nbr);
		return nbr; 
	}
	
	/**
	 * Helper method for JUnit tests
	 */
	public byte[] getBoard() 
	{
		return board; 
	}
	
	/**
	 * Helper method for JUnit tests
	 * 
	 */
	public int[] getCards()
	{
		return cards; 
	}
	/**
	 * converts a chosen card back into the data structure of the bodesuri project
	 * @param card the card which will be played
	 * @return a card
	 */
	public ch.bodesuri.applikation.client.pd.Karte convertCardBack(int card)
	{
		ch.bodesuri.applikation.client.pd.Karte chosenOne = new ch.bodesuri.applikation.client.pd.Karte(unconvertCard(card));
		chosenOne.setAusgewaehlt(true);
		return chosenOne;
	}
	
	/**
	 * Convert the card from our data representation into their objects
	 * but only for concrete cards. 
	 * @param card the input integer
	 * @return an object from the bodesuri project. 
	 */
	public Karte unconvertCard(int card)
	{
		assert card <= 100;
		
		if(card == Cards.JOKER_AS_ACE)
		{ return new Ass(KartenFarbe.Herz); }
		else if (card == Cards.JOKER_AS_TWO) 
		{return new Zwei(KartenFarbe.Herz); }
		else if (card == Cards.JOKER_AS_THREE) 
		{ return new Drei(KartenFarbe.Herz); }
		else if (card == Cards.JOKER_AS_FOUR)
		{ return new Vier(KartenFarbe.Herz); }
		else if (card == Cards.JOKER_AS_FIVE)
		{ return new Fuenf(KartenFarbe.Herz); }
		else if (card == Cards.JOKER_AS_SIX)
		{ return new Sechs(KartenFarbe.Herz); }
		else if (card == Cards.JOKER_AS_SEVEN) 
		{ return new Sieben(KartenFarbe.Herz); }
		else if (card == Cards.JOKER_AS_EIGHT)
		{ return new Acht(KartenFarbe.Herz); }
		else if (card == Cards.JOKER_AS_NINE) 
		{ return new Neun(KartenFarbe.Herz); }
		else if (card == Cards.JOKER_AS_TEN) 
		{ return new Zehn(KartenFarbe.Herz); }
		else if (card == Cards.JOKER_AS_JACK) 
		{ return new Bube(KartenFarbe.Herz); }
		else if (card == Cards.JOKER_AS_QUEEN)
		{ return new Dame(KartenFarbe.Herz); }
		else
			return new Koenig(KartenFarbe.Herz); 
	}
	
	/**
	 * converts all the fields from our representation into theirs. We can't
	 * spawn new fields because they are linked to each other. 
	 * @param place start/target position
	 * @param player the player which the pawn belongs to
	 * @return a number which represents their field
	 */
	public int unConvertFields(int place, byte player)
	{
		//move a new pawn on the bankfield
		if(place == -1)
		{
			//convert into the bodesuri represantation
			int temp = ((player - 1) * 24) + 1;
			//get the first occoupied field and return it
			for(int i = 0; i < 4; i++)
			{
				ch.bodesuri.pd.spiel.brett.Feld field = actualGame.getSpiel().getBrett().getAlleFelder().get(temp + i);
				if(field.istBesetzt())
				{
					return (temp + i); 
				}
			}
			//if no field is occupied, draw with the allied player
			byte newPlayer = (byte) ((player + 6)%4);
			if(newPlayer == 0)
			{
				newPlayer = 4;
			}
			//do the same, as above
			temp = ((newPlayer -1) * 24) + 1;
			for(int i = 0; i < 4; i++)
			{
				ch.bodesuri.pd.spiel.brett.Feld field = actualGame.getSpiel().getBrett().getAlleFelder().get(temp + i);
				if(field.istBesetzt())
				{
					return (temp + i); 
				}
			}
		}
		//all our "normal" fields are from 0 to 63, so, seperate them. 
		assert place >= 0; 
		if(place<64)
		{
			//split the board in 4 pieces with an offset
			int quarter = place / 16;
			int quarterOffset = place % 16;
			//if the offset is 0, the field is a startplace which on their board
			//is a multiple of 24
			if(quarterOffset == 0)
			{
				return (quarter * 24);
			}
			//else, convert the normal fields
			else
			{
				int result = (quarter * 24);
				result = result + 8;
				result = result + quarterOffset;
				return result;
			}
		}
		else
		{
			//split our heavenfields into 4 pieces
			int temp = place -64;
			int quarter = temp / 4;
			int quarterOffset = temp % 4;
			//these are the heavenfields for player 1
			if(quarter == 0)
			{
				return(5 + quarterOffset);
			}
			//... for player 2
			if(quarter == 1)
			{
				return(29 + quarterOffset);
			}
			//.. for player 3
			if(quarter == 2)
			{
				return(53 + quarterOffset);
			}
			//.. finally those for player 4
			else
			{
				return(77 + quarterOffset); 
			}
		}
		
	}

	/**
	 * converts our representation into their object-based view
	 * @param card the played card
     * @param move the moves (from/to pairs) 
     * @param player the player on turn
	 * @return return the zugerfasstevent 
	 */
	public ZugErfasstEvent convertBack(int card, byte[] move, byte player)
	{
		//check if the input is valid
		assert card < 66;
		assert (move.length %2) !=1;
		int size = move.length / 2;
		
		int[] startPosResult = new int[size];
		int[] targetPosResult = new int[size];
		
		//do some numbercrunching to convert our fieldnumber into theirs
		for(int i = 0; i< size; i ++)
		{
			startPosResult[i] = unConvertFields(move[2*i], player);
			msg.debug(this, "our fieldnr: " + startPosResult[i]+ " their fieldnr: "+ move[2*i]);
			targetPosResult[i] = unConvertFields(move[(2*i)+1], player);
			msg.debug(this, "our fieldnr: " + targetPosResult[i]+ " their fieldnr: "+ move[2*i+1]);
		}
		
		ch.bodesuri.applikation.client.pd.Karte cardResult; 
		//convert the played card when it is a joker
		if(card>52 && card != 100)
			 cardResult = convertCardBack(card);
		else
			cardResult = myCards[card];
		msg.debug(this, "our cardnr is: " + card + "their card is: "+ cardResult.getClass());
		//convert the fieldnumber on their brett into real objects
		List<Bewegung> moves = new Vector<Bewegung>();
		for(int i = 0; i< startPosResult.length; i++)
		{
			ch.bodesuri.pd.spiel.brett.Feld startFeld = actualGame.getSpiel().getBrett().getAlleFelder().get(startPosResult[i]);
			ch.bodesuri.pd.spiel.brett.Feld endFeld = actualGame.getSpiel().getBrett().getAlleFelder().get(targetPosResult[i]);
			Bewegung bew = new Bewegung(startFeld, endFeld);
			moves.add(bew);
		}
		
		ZugErfasstEvent zee = null; 
		if(card < Cards.JOKER_AS_ACE) //if no joker is played
		{
			zee = new ZugErfasstEvent(actualGame.aktuellerSpieler,cardResult, cardResult, moves); 
		}
		else //if a joker was played, convert the joker also
		{
			msg.debug(this, "joker was played, concrete card is: " + cardResult.getClass());
			ch.bodesuri.applikation.client.pd.Karte cardJoker = myCards[100];
			zee = new ZugErfasstEvent(actualGame.aktuellerSpieler,cardJoker, cardResult, moves); 
		}
		return zee;
	}
}
