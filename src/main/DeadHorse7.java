package main;

public class DeadHorse7 {
	
	
	
	public static void t() {
		
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
		 ;
		 That way if you have a few 2's like a 2 of hearts, 2 of spades, and 2 of clubs..
		 	when you add them together it will be 001 (1 two) 010 (2 twos) 011 (3 twos)
		 	
		 	
		 	
		
		 
		 mmmm mnmmmm
		 
		 
		 
		 */
		
		
		
	}
	
	public static void convertHumanToBinary7(String card) {
		//like AH or 5S
		
	}

}
