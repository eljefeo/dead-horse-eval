package main;

public class DeadHorse7 {

	// static final char[] cardChars = new char[] {'A', 'K', 'Q', 'J', 'T', '9',
	// '8', '7', '6', '5', '4', '3', '2'};
	// static final String[] cardNames = new String[] {"Ace", "King", "Queen",
	// "Jack", "Ten", "Nine", "Eight", "Seven", "Six", "Five", "Four", "Three",
	// "Two"};

	// These are the bits and what they mean, everything gets 3 bits.
	// Starting from the rightmost bits, 222 means the 2 cards go there
	// if we are adding up how many of each of these cards there are, like if
	// someone has 4-of-a-kind of 2(s)
	// then we need to count to 4, to do that we need 3 bits (100 - this is 4 in
	// binary)
	// same thing for the suits, if there are 7 cards total and all are 1 suit, then
	// we need to count to 7 (111 - this is 7 in binary)
	// so 3 bits each is enough to count whatever we want.
	static final String charPlacement = "SSSHHHCCCDDDAAAKKKQQQJJJTTT999888777666555444333222";

	// cards are all 2 through Ace, diamonds clubs then hearts then spades (we may
	// change the order, Im already mixing up orders everywhere of cards and suits)
	static final long[] all52Cards2 = new long[] { 549755813889L, 549755813896L, 549755813952L, 549755814400L,
			549755817984L, 549755846656L, 549756076032L, 549757911040L, 549772591104L, 549890031616L, 550829555712L,
			558345748480L, 618475290624L, 4398046511105L, 4398046511112L, 4398046511168L, 4398046511616L,
			4398046515200L, 4398046543872L, 4398046773248L, 4398048608256L, 4398063288320L, 4398180728832L,
			4399120252928L, 4406636445696L, 4466765987840L, 35184372088833L, 35184372088840L, 35184372088896L,
			35184372089344L, 35184372092928L, 35184372121600L, 35184372350976L, 35184374185984L, 35184388866048L,
			35184506306560L, 35185445830656L, 35192962023424L, 35253091565568L, 281474976710657L, 281474976710664L,
			281474976710720L, 281474976711168L, 281474976714752L, 281474976743424L, 281474976972800L, 281474978807808L,
			281474993487872L, 281475110928384L, 281476050452480L, 281483566645248L, 281543696187392L };
	// static final long[] all52Cards2 = makeAll52Cards7Decimal();

	static final char[] cardChars = new char[] { '2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'A' };
	static final String[] cardLongs = new String[] { "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine",
			"Ten", "Jack", "Queen", "King", "Ace" };

	static final char[] suitChars = new char[] { 'D', 'C', 'H', 'S' };
	static final String[] suitLongs = new String[] { "Diamonds", "Clubs", "Hearts", "Spades" };
	static final String[] suitLongsReversed = new String[] { "Spades", "Hearts", "Clubs", "Diamonds" };

	static final String OF = " of ";
	static final long singleCardMask = 78536544841L;
	// long singleOrPairOrTrip = 471219269046L;
	static final long pairMask = 157073089682L;
	// long tripMask = trips are combo of single card mask and pair mask
	static final long quadMask = 314146179364L;
	static final long suitMask = 2251250057871360L;
	static final long cardMask = 549755813887L;

	static final long allSpadeBits = 1970324836974592L;
	static final long allHeartsBits = 246290604621824L;
	static final long allClubsBits = 30786325577728L;
	static final long allDiamondBits = 3848290697216L;

	static final long spadeMask = 281474976710656L;
	static final long heartMask = 35184372088832L;
	static final long clubMask = 4398046511104L;
	static final long diamondMask = 549755813888L;
	static final long fourSpades = 1125899906842624L;
	static final long fourHearts = 140737488355328L;
	static final long fourClubs = 17592186044416L;
	static final long fourDiamonds = 2199023255552L;
	// static final long[] suitMasks = {allSpadeBits, allHeartsBits, allClubsBits,
	// allDiamondBits};
	// static final long[] almostFlush = {fourSpades, fourHearts, fourClubs,
	// fourDiamonds}; //this is the decimal of 4 of each suit, like 1 spade is 001,
	// this is 4 spades or 100
	static final long[] almostFlush = { fourDiamonds, fourClubs, fourHearts, fourSpades }; // this is the decimal of 4
																							// of each suit, like 1
																							// spade is 001, this is 4
																							// spades or 100

	static final long[] fullSuitMasks = { allDiamondBits, allClubsBits, allHeartsBits, allSpadeBits };// this has all 3
																										// bits set for
																										// each suit,
																										// like 1 spade
																										// is 001, this
																										// is 111

	static final long[] suitDecimals = new long[] { diamondMask, clubMask, heartMask, spadeMask };
	static final long[] cardDecimals = new long[] { 1L, 8L, 64L, 512L, 4096L, 32768L, 262144L, 2097152L, 16777216L,
			134217728L, 1073741824L, 8589934592L, 68719476736L };

	/*
	 * long pairs = sum & pairMask; long trips = sum & (pairs>>1); long onlyPairs =
	 * (pairs>>1) ^ trips; //since pairs includes pairs and also trips, this will
	 * get rid of trips and only include pairs long quads = sum & quadMask;
	 */

	public static void findStraights() {

		// Here is where it gets a little tricky I think. This may even make me want to
		// redesign the card structure of all the bits...
		// I dont think this bit setup is nice for straights...
		// we surely can do it, but it will take more operations than I had hoped.
		// Theres likely a better way.
	}

	public static void findFlushes() throws Exception {
		String[] someCardCodes = new String[] { "2C", "3D", "4D", "5S", "6D", "8D", "9D" };

		long[] hand = convertHandHumanShortToDecimal7(someCardCodes);

		for (long l : hand) {
			System.out
					.println("card: " + l + " : " + convertDecimalToLongName7(l) + " : " + EvalTestPlayground.bin51(l));
		}

		long suits = sumHand(hand) & suitMask;
		System.out.println("suits: " + suits + " : " + EvalTestPlayground.bin51(suits));
		for (int i = 0; i < fullSuitMasks.length; i++) {
			long masked = fullSuitMasks[i] & suits;
			if (/* masked != 0 && */ masked > almostFlush[i]) {
				System.out.println("Flush of " + suitLongs[i] + " :: " + i + " ");
				return;
			}
		}
	}

	public static void tt() throws Exception {

		long[] all52Cards = makeAll52Cards7Decimal();
		/*
		 * for(long l : all52Cards) { System.out.print(" " + l + "L,"); }
		 */
		int cnt = 0;
		for (long l : all52Cards) {
			if (cnt++ > 12) {
				System.out.println();
				cnt = 1;
			}
			System.out.print(convertDecimalToLongName7(l) + ", ");
		}

		String[] someCardCodes = new String[] { "2C", "3H", "4H", "5H", "6H", "8S", "9H", "TH", "JD", "QH", "KH",
				"AH" };
		long[] someCards = new long[someCardCodes.length];
		for (int i = 0; i < someCardCodes.length; i++) {
			someCards[i] = convertHumanShortNameToDecimal7(someCardCodes[i]);
		}

		for (int i = 0; i < someCards.length; i++) {
			System.out.println("before:\t\t" + EvalTestPlayground.bin51(someCards[i]) + " : " + someCards[i]);
			System.out.println("name: " + convertDecimalToLongName7(someCards[i]));
			someCards[i] &= cardMask;
			System.out.println("after:\t\t" + EvalTestPlayground.bin51(someCards[i]) + " : " + someCards[i]);

		}
	}

	public static void findDuplicates() throws Exception {
		// tt();

		// String[] cardsStrings = new String [] {"AH", "5S", "7S", "AC", "2C", "TD",
		// "4S"}; //pair
		// String[] cardsStrings = new String [] {"AH", "5S", "4C", "AC", "2C", "4D",
		// "4S"}; //pair and trips
		// String[] cardsStrings = new String [] {"AH", "5S", "4C", "AC", "2C", "4D",
		// "6S"}; //2 pair
		// String[] cardsStrings = new String [] {"AH", "5S", "4C", "AC", "2C", "4D",
		// "2S"}; //3 pair
		// String[] cardsStrings = new String [] {"AH", "5S", "4C", "AC", "4S", "4D",
		// "AS"}; //2 trips
		// String[] cardsStrings = new String [] {"4H", "5S", "4C", "AC", "4S", "4D",
		// "9S"}; //Quads and pair
		// String[] cardsStrings = new String [] {"4H", "5S", "4C", "AC", "4S", "4D",
		// "AS"}; //Quads and pair
		String[] cardsStrings = new String[] { "4H", "AD", "4C", "AC", "4S", "4D", "AS" }; // Quads and trips
		long[] hand = convertHandHumanShortToDecimal7(cardsStrings);
		for (int i = 0; i < hand.length; i++) {

			long aCard = hand[i];
			long aCardm1 = aCard - 1;
			System.out.println(cardsStrings[i] + " : " + aCard);
			System.out.println(EvalTestPlayground.bin51(aCard));
			System.out.println(EvalTestPlayground.bin51(aCardm1));
			System.out.println(EvalTestPlayground.bin51((aCard & aCardm1)));

		}
		long ord = hand[0] | hand[1] | hand[2] | hand[3] | hand[4] | hand[5] | hand[6];
		long sum = hand[0] + hand[1] + hand[2] + hand[3] + hand[4] + hand[5] + hand[6];

		// String testCard = "AH";
		// long converted = convertHumanToBinary7(testCard);
		// System.out.println("card : " + testCard + " : " + converted + " : " +
		// EvalTestPlayground.bin51(converted));

		// long twoH = 35184372088833L;
		// long twS = 281474976710657L;
		// String twoHString = EvalTestPlayground.bin51(twoH);
		// String twoSString = EvalTestPlayground.bin51(twS);
		// System.out.println("two of hearts: " + twoHString);
		// System.out.println("two of spades: " + twoSString);
		// System.out.println("8 plus 1: " + (8 + 1));
		// System.out.println("8 OR 1: " + (8 | 1));
		// System.out.println("2h plus 2s: " + (twoH + twS));
		// System.out.println("2h OR 2s: " + (twoH | twS));

		long pairs = sum & pairMask;
		long trips = sum & (pairs >> 1);
		long onlyPairs = (pairs >> 1) ^ trips; // since pairs includes pairs and also trips, this will get rid of trips
												// and only include pairs
		long quads = sum & quadMask;

		if (onlyPairs != 0) {
			System.out.println("PAIRS : " + EvalTestPlayground.bin51(onlyPairs));
		}
		if (trips != 0) {
			System.out.println("TRIPS : " + EvalTestPlayground.bin51(trips));
		}
		if (quads != 0) {
			System.out.println("QUADS : " + EvalTestPlayground.bin51(quads));
		}
		System.out.println("ORD : " + EvalTestPlayground.bin51(ord));
		System.out.println("SUM : " + EvalTestPlayground.bin51(sum));
		// System.out.println("sum == ord : " + (sum == ord));
		/// System.out.println("PAIRS : " + EvalTestPlayground.bin51(pairs));
		// System.out.println("TRIPS : " + EvalTestPlayground.bin51(trips));
		// System.out.println("ONLY PAIRS : " + EvalTestPlayground.bin51(onlyPairs));
		// System.out.println("QUADS : " + EvalTestPlayground.bin51(quads));
		// we can find trips by getting the pairs, shift right 1, then & the original
		// sum. only trips will be left... I think

		// 001001001001001001001001001001001001001001001001001 - mask for all single
		// cards and single suit: 321685687669321
		// 001001001001001001001001001001001001001 - mask for all single cards
		// 78536544841
		// 110110110110110110110110110110110110110 - mask for all cards that have pair
		// or trips: 471219269046 -- may not need this
		// 010010010010010010010010010010010010010 - mask for all cards that have pair:
		// 157073089682
		// 100100100100100100100100100100100100100 - mask for quads - 314146179364
		// suit mask: 111111111111000000000000000000000000000000000000000 (decimal
		// 2251250057871360) hopefully I got them all correct here
		// 111111111111111111111111111111111111111 - card mask 549755813887

		/*
		 * for cards we can use 3 bits each 001 - this means one of this card 010 - two
		 * of this card (like we have a pair) 011 - three of a kind 100 - quads
		 * 
		 * all cards get 3 bits so 13 cards * 3 bits = 39 bits. need to use a long,
		 * 64bit 39 for cards, another 12 (3*4 suits) for suits so 51 bits
		 * 
		 * that way if we had a pair of twos, when we add it will just 001 -> 010 and we
		 * can check every group of 3 cards to tell which ones there were multiples of
		 * 
		 * 
		 * all cards look like this 001001001001001001001001001001001001001001001001001
		 * S H C D A K Q J 10 9 8 7 6 5 4 3 2
		 * 
		 * two of hearts is 000001000000000000000000000000000000000000000000001 (decimal
		 * 35184372088833) two of spades is
		 * 001000000000000000000000000000000000000000000000001 281474976710657
		 * 
		 * //7 of hearts ? 000000000001000000000000000000000001000000000000000 ; That
		 * way if you have a few 2's like a 2 of hearts, 2 of spades, and 2 of clubs..
		 * when you add them together it will be 001 (1 two) 010 (2 twos) 011 (3 twos)
		 * 
		 * 
		 * 
		 * 
		 * 
		 * mmmm mnmmmm
		 * 
		 * 
		 * 
		 */

	}

	public static long[] makeAll52Cards7Decimal() throws Exception {

		int cardCount = cardChars.length;
		int suitCount = suitChars.length;
		long[] cards = new long[cardCount * suitCount];
		for (int i = 0; i < suitCount; i++) {
			for (int j = 0; j < cardCount; j++) {
				int index = (i * cardCount + j);
				cards[index] = makeDecimalFromIndexes(j, i);// (1L << (13 + i) * 3) | (1L << (j * 3));

				// return (1L << (cardIndex * 3)) | (1L << ((suitIndex + 13) * 3));
				// System.out.println(EvalTestPlayground.bin51(cards[index]) + " Card " + j + "
				// " + i + " * " + (index) +" : " + cards[index]);
			}
		}
		return cards;
	}

	public static long[] getSomeHand() throws Exception {
		return convertHandHumanShortToDecimal7(new String[] { "AH", "5S", "7S", "AC", "2C", "TD", "4S" });
	}

	public static String convertHumanShortNameToLongName7(String cardString) throws Exception {
		// like AH or 5S

		if (cardString.length() != 2) {
			throw new IllegalArgumentException("Card must be 2 chars long:  " + cardString);
		}

		int cardIndex = getCardIndexChar(cardString.charAt(0));
		int suitIndex = getSuitIndexChar(cardString.charAt(1));

		return cardLongs[cardIndex] + OF + suitLongs[suitIndex];
	}

	// This method will take a String like "5S" or "JC" (five of clubs or Jack of
	// Spades) and turn it into the decimal equivalent for that card
	// This function is not optimized, just here to make things easier. If actually
	// needed in some performance situation, we should find faster ways to do this
	public static long convertHumanShortNameToDecimal7(String cardString) throws Exception {
		// like AH or 5S
		if (cardString.length() != 2) {
			throw new IllegalArgumentException("Card must be 2 chars long:  " + cardString);
		}

		int cardIndex = getCardIndexChar(cardString.charAt(0));
		int suitIndex = getSuitIndexChar(cardString.charAt(1));

		return makeDecimalFromIndexes(cardIndex, suitIndex);
	}

	public static long[] convertHandHumanShortToDecimal7(String[] cards) throws Exception {
		if (cards.length != 7) {
			throw new IllegalArgumentException("There must be 7 cards");
		}
		long[] ret = new long[cards.length];
		for (int i = 0; i < cards.length; i++) {
			ret[i] = convertHumanShortNameToDecimal7(cards[i]);
		}
		return ret;
	}

	public long orHand(long[] cards) {
		if (cards.length != 7) {
			throw new IllegalArgumentException("There must be 7 cards");
		}
		return cards[0] | cards[1] | cards[2] | cards[3] | cards[4] | cards[5] | cards[6];
	}

	public static long sumHand(long[] cards) {
		if (cards.length != 7) {
			throw new IllegalArgumentException("There must be 7 cards");
		}
		return cards[0] + cards[1] + cards[2] + cards[3] + cards[4] + cards[5] + cards[6];
	}

	public static long makeDecimalFromIndexes(int cardIndex, int suitIndex) throws Exception {
		return (1L << (cardIndex * 3)) | (1L << ((suitIndex + 13) * 3));
	}

	public static String convertDecimalToLongName7(long card) throws Exception {
		return getCardLong(card) + OF + getSuitLong(card);
	}

	public static String convertDecimalToShortName7(long card) throws Exception {
		return (getCardChar(card) + "" + getSuitChar(card));
	}

	public static String getSuitLong(long card) throws Exception {
		return suitLongs[getSuitIndexDecimal(card)];
	}

	public static String getCardLong(long card) throws Exception {
		return cardLongs[getCardIndexDecimal(card)];
	}

	public static char getSuitChar(long card) throws Exception {
		return suitChars[getSuitIndexDecimal(card)];
	}

	public static char getCardChar(long card) throws Exception {
		return cardChars[getCardIndexDecimal(card)];
	}

	public static int getSuitIndexDecimal(long card) throws Exception {
		for (int i = 0; i < suitDecimals.length; i++) {
			if ((card & suitDecimals[i]) != 0) {
				return i;
			}
		}
		throw new Exception("Error retreiving suit index for card: " + card);
	}

	public static int getCardIndexDecimal(long card) throws Exception {
		for (int i = 0; i < cardDecimals.length; i++) {
			if ((card & cardDecimals[i]) != 0) {
				return i;
			}
		}
		throw new Exception("Error retreiving card index for card: " + card);
	}

	public static int getSuitIndexChar(char suitChar) throws Exception {
		for (int i = 0; i < suitChars.length; i++) {
			if (suitChar == suitChars[i]) {
				return i;
			}
		}
		throw new Exception("Error retreiving suit index for suit char: " + suitChar);
	}

	public static int getCardIndexChar(char cardChar) throws Exception {
		for (int i = 0; i < cardChars.length; i++) {
			if (cardChar == cardChars[i]) {
				return i;
			}
		}
		throw new Exception("Error retreiving card index for card char: " + cardChar);
	}

}
