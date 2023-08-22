package main;

import java.util.Arrays;

public class DeadHorse7 {
	
	static long singleCardMask = 78536544841L;
	//long singleOrPairOrTrip = 471219269046L;
	static long pairMask = 157073089682L;
	//long tripMask = trips are combo of single card mask and pair mask
	static long quadMask = 314146179364L;
	static long suitMask = 2251250057871360L;
	static long cardMask = 549755813887L;
	/*
	long pairs = sum & pairMask;
	long trips = sum & (pairs>>1);
	long onlyPairs = (pairs>>1) ^ trips; //since pairs includes pairs and also trips, this will get rid of trips and only include pairs
	long quads = sum & quadMask;
	 */
	

	public static void tt() {
		long[] someCards = new long[] {convertHumanToBinary7("2H"),convertHumanToBinary7("3H"),convertHumanToBinary7("4H"),
				convertHumanToBinary7("5H"),convertHumanToBinary7("6H"),convertHumanToBinary7("7H"),convertHumanToBinary7("8H"),
				convertHumanToBinary7("9H"),convertHumanToBinary7("TH"),convertHumanToBinary7("JH"),convertHumanToBinary7("QH"),
				convertHumanToBinary7("KH"),convertHumanToBinary7("AH")
		};
		
		
		for(int i=0; i<someCards.length; i++) {
			//System.out.println("before:\t\t" + EvalTestPlayground.bin51(randomCards[i]));
			someCards[i] &= 549755813887L;
			System.out.println("after:\t\t" + EvalTestPlayground.bin51(someCards[i]) + " : " + someCards[i]);
		}
	}
	
	
	public static void t() {
		tt();
		makeAll52Cards7bin();
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
		char[] acceptableCards = new char[] {'A', 'K', 'Q', 'J', 'T', '9', '8', '7', '6', '5', '4', '3', '2'};
		char[] acceptableSuits = new char[] {'S', 'H', 'C', 'D'};
		int cardCount = 13;
		int suitCount = 4;
		long[] cards = new long[cardCount * suitCount];
		for(int i = 0; i < cardCount; i++) {
			for(int j = 0; j < suitCount; j++) {
				//(1L << ((card-2)*3)) | (1L << (suit*3));
				cards[i*j] = (1L << (13 + j) * 3) | (1L << (i*3));
				System.out.println("Card " + i + " " + j + " : " + cards[i*j] + " :: \n" + EvalTestPlayground.bin51(cards[i*j]));
			}
		}
		return cards;
	}
	
	
	//This function is not optimized, just here to make things easier. If actually needed in some performance situation, we should find faster ways to do this
	public static long convertHumanToBinary7(String cardString) {
		//like AH or 5S
		char[] acceptableCards = new char[] {'A', 'K', 'Q', 'J', 'T', '9', '8', '7', '6', '5', '4', '3', '2'};
		char[] acceptableSuits = new char[] {'S', 'H', 'C', 'D'};
		char ca = cardString.charAt(0);
		char cb = cardString.charAt(1);
		if(cardString.length() != 2 || new String(acceptableCards).indexOf(ca) == -1 || new String(acceptableSuits).indexOf(cb) == -1) {
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
		
	}

}
