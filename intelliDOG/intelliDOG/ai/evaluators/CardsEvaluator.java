package intelliDOG.ai.evaluators;

import intelliDOG.ai.framework.Rules;

public class CardsEvaluator implements Evaluator {

	@Override
	public int evaluate(byte[] actualState, byte[] targetState, byte player) {
		throw new UnsupportedOperationException("not supported");
	}

	@Override
	public int evaluate(byte[] actualState, byte[] targetState, byte player,
			int[] card) {
		
		int aceWeight  = 10;  //  weight if all pieces on the field
		int aceWeight2 = 15;  // weight if some pieces are outside the field
		
		int kingWeight  = 10;  //  weight if all pieces on the field
		int kingWeight2 = 15;  // weight if some pieces are outside the field
		
		int fourWeight  = 2;  // weight if all pieces are on the field (can only be used to go backwards)
		int fourWeight2 = 1;  // FIXME: better split? 
		int fourWeight3 = 20; // weight if it COULD be used to go directly into heaven: 
							  // f.e. if at least one piece is outside the board and 
							  // there is an Ace to go out.
		
		int sevenWeight  = 5; // seven can be used to order pieces in heaven	 
		int sevenWeight2 = 10; // FIXME: put that functionality into CardsEvaluator
							  // seven can be used to capture other pawns 
		
		// count number of my pieces in order to detect if there are some 
		// off the field. That has effect on the evaluation of Ace. 
		int pieces[] = new int[4];
		byte heavenPieces[] = new byte[4]; 
		
		heavenPieces = Rules.getInstance().getPiecesInHeavenOfPlayer(actualState, player); 
		
		try {
			pieces = Rules.getInstance().getPiecesInGameForPlayer(actualState, player);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		int sum = 0; 
		boolean hasAce  = false; 
		boolean hasKing = false; 
		
		for(int i=0; i<card.length; i++)
		{
			int cur_card = card[i] % 13; 
			
			switch(cur_card)
			{
			case 1: // ACE
				if(pieces.length < 4) {
					sum += aceWeight2;
					hasAce = true; 
				}else
					sum += aceWeight; 
				break; 
				
			case 4: // Four
				if(pieces.length == 4)
					sum += fourWeight; 
				else if(pieces.length < 4 && (hasAce || hasKing) )
					sum += fourWeight3; 
				else 
					sum += fourWeight2; 
				break; 
				
			case 7: // Seven
					if( (heavenPieces[3] != 0) || (heavenPieces[2] != 0) || (heavenPieces[1] != 0))
						sum += sevenWeight; 
					else 
						sum += sevenWeight2; 
					break; 
					
			case 11: // Jack
					break; 
					
			case 0:  // King
				if(pieces.length < 4) {
					sum += kingWeight2;
					hasKing = true; 
				}else
					sum += kingWeight; 
				break; 
				
			case 5: case 6: case 8: case 9: case 10: case 12: 
				if(card[i] == 100)
					sum += 50; 
				else
					sum += 3; 
				break; 
			
			// for everything else
			default: 
			}
		}
		return sum;
	}

	@Override
	public int evaluate(byte[] targetState, byte player) {
		throw new UnsupportedOperationException("not supported");
	}

	@Override
	public int evaluate(byte[] targetState, byte player, int[] card, float fading) {
		
		
		int aceWeight  = 10;  //  weight if all pieces on the field
		int aceWeight2 = 15;  // weight if some pieces are outside the field
		
		int kingWeight  = 10;  //  weight if all pieces on the field
		int kingWeight2 = 15;  // weight if some pieces are outside the field
		
		int fourWeight  = 2;  // weight if all pieces are on the field (can only be used to go backwards)
		int fourWeight2 = 1;  // FIXME: better split? 
		int fourWeight3 = 20; // weight if it COULD be used to go directly into heaven: 
							  // f.e. if at least one piece is outside the board and 
							  // there is an Ace to go out.
		
		int sevenWeight  = 5; // seven can be used to order pieces in heaven	 
		int sevenWeight2 = 10; // FIXME: put that functionality into CardsEvaluator
							  // seven can be used to capture other pawns
		
		// count number of my pieces in order to detect if there are some 
		// off the field. That has effect on the evaluation of Ace. 
		int pieces[] = new int[4];
		byte heavenPieces[] = new byte[4]; 
		
		heavenPieces = Rules.getInstance().getPiecesInHeavenOfPlayer(targetState, player); 
		
		try {
			pieces = Rules.getInstance().getPiecesInGameForPlayer(targetState, player);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		int sum = 0; 
		boolean hasAce  = false; 
		boolean hasKing = false; 
		
		for(int i=0; i<card.length; i++)
		{
			int cur_card = card[i] % 13; 
			
			switch(cur_card)
			{
			case 1: // ACE
				if(pieces.length < 4) {
					sum += aceWeight2;
					hasAce = true; 
				}else
					sum += aceWeight; 
				break; 
				
			case 4: // Four
				if(pieces.length == 4)
					sum += fourWeight; 
				else if(pieces.length < 4 && (hasAce || hasKing) )
					sum += fourWeight3; 
				else 
					sum += fourWeight2; 
				break; 
				
			case 7: // Seven
					if( (heavenPieces[3] != 0) || (heavenPieces[2] != 0) || (heavenPieces[1] != 0))
						sum += sevenWeight; 
					else 
						sum += sevenWeight2; 
					break; 
					
			case 11: // Jack
					break; 
					
			case 0:  // King
				if(pieces.length < 4) {
					sum += kingWeight2;
					hasKing = true; 
				}else
					sum += kingWeight; 
				break; 
				
			case 5: case 6: case 8: case 9: case 10: case 12: 
				if(card[i] == 100)
					sum += 50; 
				else
					sum += 3; 
				break; 
			
			// for everything else
			default: 
			}
		}
		return sum;
	}

}
