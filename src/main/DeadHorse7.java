package main;

public class DeadHorse7 {
	
	//static final char[] cardChars = new char[] {'A', 'K', 'Q', 'J', 'T', '9', '8', '7', '6', '5', '4', '3', '2'};
	//static final String[] cardNames = new String[] {"Ace", "King", "Queen", "Jack", "Ten", "Nine", "Eight", "Seven", "Six", "Five", "Four", "Three", "Two"};
	static final String charPlacement = "SSSHHHCCCDDDAAAKKKQQQJJJTTT999888777666555444333222";
	static final char[] cardChars = new char[] {'2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'A'};
	static final String[] cardNames = new String[] {"Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Jack", "Queen", "King", "Ace"};
	
	static final char[] suitChars = new char[] {'S', 'H', 'C', 'D'};
	static final String[] suitNames = new String[] {"Spades", "Hearts", "Clubs", "Diamonds"};
	
	static final long singleCardMask = 78536544841L;
	//long singleOrPairOrTrip = 471219269046L;
	static final long pairMask = 157073089682L;
	//long tripMask = trips are combo of single card mask and pair mask
	static final long quadMask = 314146179364L;
	static final long suitMask = 2251250057871360L;
	static final long cardMask = 549755813887L;

	static final long spadeMask = 281474976710656L;
	static final long heartMask = 35184372088832L;
	static final long clubMask = 4398046511104L;
	static final long diamondMask = 549755813888L;
	static final long[] suitLongs = new long[] {spadeMask, heartMask, clubMask, diamondMask };
	static final long[] cardLongs = new long[] {1L, 8L, 64L, 512L, 4096L, 32768L, 262144L, 2097152L, 16777216L, 134217728L, 1073741824L, 8589934592L, 68719476736L };
	/*
	long pairs = sum & pairMask;
	long trips = sum & (pairs>>1);
	long onlyPairs = (pairs>>1) ^ trips; //since pairs includes pairs and also trips, this will get rid of trips and only include pairs
	long quads = sum & quadMask;
	 */
	

	public static void tt() {
		
		
		String[] someCardCodes = new String[] {"2C", "3H", "4H", "5H", "6H", "8S", "9H", "TH", "JD", "QH", "KH","AH"};
		long[] someCards = new long[someCardCodes.length];
		for(int i=0; i<someCardCodes.length; i++) {
			someCards[i] = convertHumanToBinary7(someCardCodes[i]);
		}
		
		for(int i=0; i<someCards.length; i++) {
			System.out.println("before:\t\t" + EvalTestPlayground.bin51(someCards[i]) + " : " + someCards[i]);
			System.out.println("name: " + convertBinaryToHuman7(someCards[i]));
			someCards[i] &= cardMask;
			System.out.println("after:\t\t" + EvalTestPlayground.bin51(someCards[i]) + " : " + someCards[i]);
			
		}
	}
	
	
	public static void t() {
		tt();
		long[] all52Cards = makeAll52Cards7bin();
		for(long l : all52Cards) {
			System.out.print(" " + l + "L,");
		}
		for(long l : all52Cards) {
			System.out.println(convertBinaryToHuman7(l));
		}
		//String[] cardsStrings = new String [] {"AH", "5S", "7S", "AC", "2C", "TD", "4S"}; //pair
		//String[] cardsStrings = new String [] {"AH", "5S", "4C", "AC", "2C", "4D", "4S"}; //pair and trips
		//String[] cardsStrings = new String [] {"AH", "5S", "4C", "AC", "2C", "4D", "6S"}; //2 pair
		//String[] cardsStrings = new String [] {"AH", "5S", "4C", "AC", "2C", "4D", "2S"}; //3 pair
		//String[] cardsStrings = new String [] {"AH", "5S", "4C", "AC", "4S", "4D", "AS"}; //2 trips
		//String[] cardsStrings = new String [] {"4H", "5S", "4C", "AC", "4S", "4D", "9S"}; //Quads and pair
		//String[] cardsStrings = new String [] {"4H", "5S", "4C", "AC", "4S", "4D", "AS"}; //Quads and pair
		String[] cardsStrings = new String [] {"4H", "AD", "4C", "AC", "4S", "4D", "AS"}; //Quads and trips
		long[] hand = convertHumanHandToBinary7(cardsStrings);
		for(int i=0; i<hand.length; i++) {
			System.out.println(cardsStrings[i] + " : " + hand[i] + " :\t\t\t" + EvalTestPlayground.bin51(hand[i]));
		}
		long ord = hand[0] | hand[1] | hand[2] | hand[3] | hand[4] | hand[5] | hand[6];
		long sum = hand[0] + hand[1] + hand[2] + hand[3] + hand[4] + hand[5] + hand[6];
		
		//String testCard = "AH";
		//long converted = convertHumanToBinary7(testCard);
		//System.out.println("card : " + testCard + " : " + converted + " : " + EvalTestPlayground.bin51(converted));
		

		
		//long twoH = 35184372088833L;
		//long twS = 281474976710657L;
		//String twoHString = EvalTestPlayground.bin51(twoH);
		//String twoSString = EvalTestPlayground.bin51(twS);
		//System.out.println("two of hearts: " + twoHString);
		//System.out.println("two of spades: " + twoSString);
		//System.out.println("8 plus 1: " + (8 + 1));
		//System.out.println("8 OR 1: " + (8 | 1));
		//System.out.println("2h plus 2s: " + (twoH + twS));
		//System.out.println("2h OR 2s:   " + (twoH | twS));
		
		
		
		long pairs = sum & pairMask;
		long trips = sum & (pairs>>1);
		long onlyPairs = (pairs>>1) ^ trips; //since pairs includes pairs and also trips, this will get rid of trips and only include pairs
		long quads = sum & quadMask;
		
		if(onlyPairs > 0) {
			System.out.println("PAIRS : " + EvalTestPlayground.bin51(onlyPairs));
		}
		if(trips > 0) {
			System.out.println("TRIPS : " + EvalTestPlayground.bin51(trips));
		}
		if(quads > 0) {
			System.out.println("QUADS : " + EvalTestPlayground.bin51(quads));
		}
		System.out.println("ORD : " + EvalTestPlayground.bin51(ord));
		System.out.println("SUM : " + EvalTestPlayground.bin51(sum));
		//System.out.println("sum == ord : " + (sum == ord));
		///System.out.println("PAIRS : " + EvalTestPlayground.bin51(pairs));
		//System.out.println("TRIPS : " + EvalTestPlayground.bin51(trips));
		//System.out.println("ONLY PAIRS : " + EvalTestPlayground.bin51(onlyPairs));
		//System.out.println("QUADS : " + EvalTestPlayground.bin51(quads));
		//we can find trips by getting the pairs, shift right 1, then & the original sum. only trips will be left... I think
		
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
		return convertHumanHandToBinary7(new String [] {"AH", "5S", "7S", "AC", "2C", "TD", "4S"});
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
	
	public static long[] makeAll52Cards7bin() {
		
		int cardCount = 13;
		int suitCount = 4;
		long[] cards = new long[cardCount * suitCount];
		for(int j = 0; j < suitCount; j++) {
		for(int i = 0; i < cardCount; i++) {
			
				//(1L << ((card-2)*3)) | (1L << (suit*3));
				cards[(j*cardCount + i)] = (1L << (13 + j) * 3) | (1L << (i*3));
				System.out.println("Card " + i + " " + j + " * " + (j*cardCount + i) +" : " + cards[(j*cardCount + i)] + " :: \n" + EvalTestPlayground.bin51(cards[i*j]));
			}
		}
		return cards;
	}
	
	
	//This function is not optimized, just here to make things easier. If actually needed in some performance situation, we should find faster ways to do this
	public static long convertHumanToBinary7(String cardString) {
		//like AH or 5S
		//char[] acceptableCards = new char[] {'A', 'K', 'Q', 'J', 'T', '9', '8', '7', '6', '5', '4', '3', '2'};
		//char[] acceptableSuits = new char[] {'S', 'H', 'C', 'D'};
		char ca = cardString.charAt(0);
		char cb = cardString.charAt(1);
		if(cardString.length() != 2 || new String(cardChars).indexOf(ca) == -1 || new String(suitChars).indexOf(cb) == -1) {
			throw new IllegalArgumentException("Card must be 2 chars long and must be in format AH, 6D, TS, JC, 2H etc... :  " + cardString);
		}
		
		//card
		int card = 0, suit = 0;
		if(ca == "A".charAt(0)) {
			card = 14;
		} else if(ca == "K".charAt(0)) {
			card = 13;
		} else if(ca == "Q".charAt(0)) {
			card = 12;
		} else if(ca == "J".charAt(0)) {
			card = 11;
		} else if(ca == "T".charAt(0)) {
			card = 10;
		} else {
			card = Integer.parseInt(ca + "");
		}
		
		// suit
		if(cb == "S".charAt(0)) {
			suit = 16;
		} else if(cb == "H".charAt(0)) {
			suit = 15;
		} else if(cb == "C".charAt(0)) {
			suit = 14;
		} else if(cb == "D".charAt(0)) {
			suit = 13;
		}
		return (1L << ((card-2)*3)) | (1L << (suit*3));
	}
	
	public static String convertBinaryToHuman7(long card) {
		int suit = 0;
		String suitName = "";
		String cardName = "";
		for(int i = 0; i < suitLongs.length; i++) {
			if((card & suitLongs[i]) > 0 ) {
				//System.out.println(suitLongs[i] + " " + suitNames[i] + " " + card);
				suit = i;
				suitName = suitNames[i];
				break;
			}
		}
		
		long c = singleCardMask & card;
		for(int i=0; i<cardLongs.length; i++) {
			if((card & cardLongs[i]) > 0) {
				cardName = cardNames[i];
			}
		}
		String ret = cardName + " of " + suitName;
		//System.out.println(ret + " : " + card + " : " + EvalTestPlayground.bin51(card));
		return ret;
		/*if((card & spadeMask) == spadeMask ) {
			System.out.println("Spade : " + card);
			suit = 1;
		} else if((card & heartMask) == heartMask ) {
			System.out.println("Heart : " + card);
			suit = 2;
		} else if((card & clubMask) == clubMask ) {
			System.out.println("Club : " + card);
			suit = 3;
		} else if((card & diamondMask) == diamondMask ) {
			System.out.println("Diamond : " + card);
			suit = 4;
		}*/
		
	}

}
