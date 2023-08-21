package main;

public class DeadHorse7 {
	
	
	
	public static void t() {
		
		String testCard = "AH";
		long converted = convertHumanToBinary7(testCard);
		System.out.println("card : " + testCard + " : " + converted + " : " + EvalTestPlayground.bin51(converted));
		
		long singleCardMask = 78536544841L;
		//long singleOrPairOrTrip = 471219269046L;
		long pairMask = 157073089682L;
		//long tripMask = trips are combo of single card mask and pair mask
		long quadMask = 314146179364L;
		long suitMask = 2251250057871360L;
		long cardMask = 549755813887L;
		
		long twoH = 35184372088833L;
		long twS = 281474976710657L;
		String twoHString = EvalTestPlayground.bin51(twoH);
		String twoSString = EvalTestPlayground.bin51(twS);
		System.out.println("two of hearts: " + twoHString);
		System.out.println("two of spades: " + twoSString);
		System.out.println("8 plus 1: " + (8 + 1));
		System.out.println("8 OR 1: " + (8 | 1));
		System.out.println("2h plus 2s: " + (twoH + twS));
		System.out.println("2h OR 2s:   " + (twoH | twS));
		//001001001001001001001001001001001001001001001001001 - mask for all single cards and single suit:  321685687669321
		//001001001001001001001001001001001001001 - mask for all single cards 78536544841
		//110110110110110110110110110110110110110 - mask for all cards that have pair or trips: 471219269046 -- may not need this
		//010010010010010010010010010010010010010 - mask for all cards that have pair: 157073089682
		//100100100100100100100100100100100100100 - mask for quads - 314146179364
		 //suit mask: 111111111111000000000000000000000000000000000000000 (decimal 2251250057871360) hopefully I got them all correct here
		 //111111111111111111111111111111111111111 - card mask 549755813887

		
		/*
		 for cards we can use 3 bits each
		 001 - this means one of this card
		 010 - two of this card (like we have a pair)
		 011 - three of a kind
		 100 - quads
		 
		 all cards get 3 bits so 13 cards * 3 bits = 39 bits.
		 need to use a long, 64bit
		 39 for cards, another 12 (3*4 suits) for suits
		 so 51 bits
		 
		 that way if we had a pair of twos, when we add it will just 001 -> 010
		 and we can check every group of 3 cards to tell which ones there were multiples of
		 
		 
		 all cards look like this
		 001001001001001001001001001001001001001001001001001
		 S  H  C  D  A  K  Q  J 10  9  8  7  6  5  4  3  2
		 
		 two of hearts is 000001000000000000000000000000000000000000000000001 (decimal 35184372088833)
		 two of spades is 001000000000000000000000000000000000000000000000001  281474976710657
		 				  
		 //7 of hearts  ? 000000000001000000000000000000000001000000000000000
		 ;
		 That way if you have a few 2's like a 2 of hearts, 2 of spades, and 2 of clubs..
		 	when you add them together it will be 001 (1 two) 010 (2 twos) 011 (3 twos)
		 	
		 	
		 	
		
		 
		 mmmm mnmmmm
		 
		 
		 
		 */
		
		
		
	}
	
	public static long[] getSomeHand() {
		return convertHumanHandToBinary7(new String [] {"AH", "5S", "7S", "AC", "2C", "10D"});
	}
	
	public static long[] convertHumanHandToBinary7(String[] cards) {
		if(cards.length != 7) {
			throw new IllegalArgumentException("There must be 7 cards");
		}
		long[] ret = new long[cards.length];
		for(int i = 0; i < cards.length; i++) {
			ret[i] = convertHumanToBinary7(cards[i]);
		}
		return ret;
	}
	
	public static long convertHumanToBinary7(String card) {
		//like AH or 5S
		if(card.length() != 2) {
			throw new IllegalArgumentException("Card must be 2 chars long");
		}
		
		char nn = '9';
		System.out.println("char 9 : " + nn);
		
		
		
		char ca = card.charAt(0);
		char cb = card.charAt(1);
		int a = 13, k = 12, q = 11, j = 10;
		long ret = 0;
		
		//card
		if(ca == "A".charAt(0)) {
			ret = (1L << (12*3));
			System.out.println("first char A : " + EvalTestPlayground.bin51(ret));
		} else if(ca == "K".charAt(0)) {
			ret = 1L << (11*3);
			System.out.println("first char K : " + EvalTestPlayground.bin51(ret));
		} else if(ca == "Q".charAt(0)) {
			ret = 1L << (10*3);
			System.out.println("first char Q : " + EvalTestPlayground.bin51(ret));
		} else if(ca == "J".charAt(0)) {
			ret = 1L << (9*3);
			System.out.println("first char J : " + EvalTestPlayground.bin51(ret));
		} else {
			int p = Integer.parseInt(ca + "");
			ret = 1L << ((p-2)*3);
		}
		
		// suit
		if(cb == "S".charAt(0)) {
			ret |= 1L << (16*3);
			System.out.println("second char S : " + EvalTestPlayground.bin51(ret));
		} else if(cb == "D".charAt(0)) {
			ret |= 1L << (15*3);
			System.out.println("second char D : " + EvalTestPlayground.bin51(ret));
		} else if(cb == "C".charAt(0)) {
			ret |= 1L << (14*3);
			System.out.println("second char C : " + EvalTestPlayground.bin51(ret));
		} else if(cb == "H".charAt(0)) {
			ret |= 1L << (13*3);
			System.out.println("second char H : " + EvalTestPlayground.bin51(ret));
		}
		
		return ret;
	}

}
