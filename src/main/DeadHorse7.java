package main;

public class DeadHorse7 {

	// static final char[] cardChars = new char[] {'A', 'K', 'Q', 'J', 'T', '9',
	// '8', '7', '6', '5', '4', '3', '2'};
	// static final String[] cardNames = new String[] {"Ace", "King", "Queen",
	// "Jack", "Ten", "Nine", "Eight", "Seven", "Six", "Five", "Four", "Three",
	// "Two"};


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

	//static final long[] straights = new long[]{68719477321L, 4681L, 37448L, 299584L, 2396672L, 19173376L, 153387008L, 1227096064L, 9816768512L, 78534148096L};
	static final long[] straights = new long[]{78534148096L, 9816768512L, 1227096064L, 153387008L, 19173376L, 2396672L, 299584L, 37448L, 4681L, 68719477321L};
	/*
	 * long pairs = sum & pairMask; long trips = sum & (pairs>>1); long onlyPairs =
	 * (pairs>>1) ^ trips; //since pairs includes pairs and also trips, this will
	 * get rid of trips and only include pairs long quads = sum & quadMask;
	 */

	public static int eval7Checke() throws Exception {
		String[] someCardCodes = new String[] { "9C", "7C", "8D", "TD", "3D", "2C", "3C" };

		long[] hand = convertHandHumanShortToDecimal7(someCardCodes);
		int res = eval7(hand[0], hand[1], hand[2], hand[3], hand[4], hand[5], hand[6]);
		System.out.println("REsult: " + util.handNames[res]);
		return res;
	}

	public static int eval7(long a, long b, long c, long d, long e, long f, long g) {

		long ord = a | b | c | d | e | f | g; //orHand(hand);
		long sum = a + b + c + d + e + f + g; //sumHand(hand);
		long flushCards = 0;
		boolean isFlush = false, isStrt = false;
		long suits = sum & suitMask;

		for (int i = 0; i < fullSuitMasks.length; i++) {
			//long masked = fullSuitMasks[i] & suits;
			if (/* masked != 0 && */ (fullSuitMasks[i] & suits) > almostFlush[i]) {


				if((a&fullSuitMasks[i]) != 0) flushCards |= a;
				if((b&fullSuitMasks[i]) != 0) flushCards |= b;
				if((c&fullSuitMasks[i]) != 0) flushCards |= c;
				if((d&fullSuitMasks[i]) != 0) flushCards |= d;
				if((e&fullSuitMasks[i]) != 0) flushCards |= e;
				if((f&fullSuitMasks[i]) != 0) flushCards |= f;
				if((g&fullSuitMasks[i]) != 0) flushCards |= g;
				//000000111000000000000000000000000000000000000000000
				//000000001000000000000000000001000000000000000000000
				//000000000000000000000001001001001001000000000001000
				//000000000000000000000001001001001001000000000000000
				//System.out.println("fullSuitMasks[i] : " + util.bin51(fullSuitMasks[i]));
				//System.out.println("a : " + util.bin51(a));
				//System.out.println("b : " + util.bin51(b));
				//System.out.println("c : " + util.bin51(c));
				//System.out.println("flush cards: " + util.bin51(flushCards));
				flushCards &= cardMask;
				//System.out.println("flush cards: " + util.bin51(flushCards));
				isFlush = true;
				break;
			}
		}

		long straightCards = 0;
		for(long l : straights){
			if((l&ord) == l){

				//return l | 0x10000000;

				if((a&l) != 0) straightCards |= a;
				if((b&l) != 0) straightCards |= b;
				if((c&l) != 0) straightCards |= c;
				if((d&l) != 0) straightCards |= d;
				if((e&l) != 0) straightCards |= e;
				if((f&l) != 0) straightCards |= f;
				if((g&l) != 0) straightCards |= g;

				//System.out.println("straight cards: " + util.bin51(straightCards));
				straightCards &= cardMask;
				//System.out.println("straight cards: " + util.bin51(straightCards));
				if(isFlush ){
					if( (straightCards & flushCards) == straightCards){
					//	System.out.println("straight FLUSHHH cards: " + util.bin51(straightCards));
						//need to check if the same 5 cards for flush are the same 5 cards for straight...
						return 8;//since also a flush, its a straight flush.
					} else {
						//System.out.println("normal flush");
						return 5;
					}

				}

				else{
					return 4; //if no flush, just straight
				}

			}
		}
		if(isFlush){
			return 5;
		}

		long pairs = (sum & pairMask) >> 1;
		long trips = sum & pairs ;
		long onlyPairs = pairs  ^ trips; // since pairs includes pairs and also trips, this will get rid of trips
		// and only include pairs
		long quads = sum & quadMask;

		if (quads != 0) {
			//return 0x1C000000;
			return 7;
		}
		if (trips != 0) {
			long twoTrips = trips & trips-1;
			if(twoTrips != 0 || onlyPairs != 0){
				return 6;
			} else{
				return 3;
			}

		}
		if (onlyPairs != 0) {
			long nextPair = onlyPairs & onlyPairs - 1;
			if(nextPair != 0){
				return 2;
			} else {
				return 1;
			}
		}
		return 0;
	}

	public static long testeval7() throws Exception {
		String[] someCardCodes = new String[] { "9C", "9H", "3C", "KS", "9S", "JD", "9D" };

		long[] hand = convertHandHumanShortToDecimal7(someCardCodes);

		for (long l : hand) {
			System.out.println("card: " + l + " : " + convertDecimalToLongName7(l) + " : " + util.bin51(l));
		}
		long ord = orHand(hand);
		long sum = sumHand(hand);

		/*long straight = findStraights(ord);
		long flush = findFlushes(sum);
		long duplicates = findDuplicates(sum);

		if(straight != 0){
			System.out.println("Straight!");
		}
		if(flush != 0){
			System.out.println("Flush!");
		}
		if(duplicates != 0){
			System.out.println("Duplicates!");
		}
		*/

		//00000000000000000000000000 26 zeros
		/*
		fullhouse: 0x18000000
		Quads: 0x1C000000
		Flush: 0x14000000
		Straight: 0x10000000

		 - but if ace 2 3 4 5... maybe return:
				 int straightCards = 0;
					if (xor == 0x100F) // this is a straight ace,2,3,4,5 = 1000000001111
						straightCards = 0xF; // this is just bits 0001111 (meaning cards 2,3,4,5) ... we omit the ace
					 else
						straightCards = xor; //else just use whatever straight this is..


		Pair: 0x4000000
		trips: 0xC000000
		Two Pair: 0x8000000
		High Card: 0

		straight flush: 0x20000000

		static String[] handNames = {
            "High Card 0", "Pair 1", "Two Pair 2", "3 of a kind 3",
            "Straight 4", "Flush 5", "Full House 6", "4 of a kind 7", "Straight Flush 8"
    };
		 */

		boolean isFlush = false, isStrt = false;
		System.out.println("First checking for a flush ");
		long suits = sum & suitMask;
		System.out.println("suits: " + suits + " : " + util.bin51(suits));
		for (int i = 0; i < fullSuitMasks.length; i++) {
			//long masked = fullSuitMasks[i] & suits;
			if (/* masked != 0 && */ (fullSuitMasks[i] & suits) > almostFlush[i]) {
				System.out.println("Flush of " + util.suitLongs[i] + " :: " + i + " ");
				//return suits;


				//need to return the highest 5 cards with this suit I guess
				//well since there is no way to have 2 different flushes on the board...
				//can we just return the highest card of the flush? thats all you need to know who won in case
				//2 people both have a flush
				for(int j=0; j<hand.length; j++){
					if((fullSuitMasks[i] & hand[i]) != 0){
						long returnFlush = 0x14000000 | (hand[i] & suitMask);
						System.out.println("About to return a Flush: " + returnFlush + " :: " + util.bin64(returnFlush));


						//return 5 ;//| (hand[i] & suitMask); //this 0x14000000 is not in the right spot.... need to recalculate, those are for 5 card..
						//need to determine if returning a long or an int. we can get away with an int, problem is we would have to move
						//the bits from the original card over a bunch to get it in the right spot. Might just be easier
						//to return the long...idk.
					}
				}

				isFlush = true;
				break;
			}
		}
		System.out.println("Checking next for straights");
		for(long l : straights){
			if((l&ord) == l){
				System.out.println("Found a straight: " + l);

				//return l | 0x10000000;

				if(isFlush){
					System.out.println("STraIght FluSh " + util.bin51(ord));
					return 8;//since also a flush, its a straight flush.
				}

				else{
					System.out.println("Normal Straight " + util.bin51(ord));
					return 4; //if no flush, just straight
				}

			}
		}
		if(isFlush){
			System.out.println("yep just returning a Flush: " + " :: " + util.bin64(ord));
			return 5;
		}


		System.out.println("looking for duplicates : pairs, 2pairs, trips, full houses, quads...");
		long pairs = (sum & pairMask) >> 1;
		long trips = sum & pairs ;
		long onlyPairs = pairs  ^ trips; // since pairs includes pairs and also trips, this will get rid of trips
		// and only include pairs
		long quads = sum & quadMask;

		if (quads != 0) {
			System.out.println("QUADS : " + util.bin51(quads));
			//return 0x1C000000;
			return 7;
		}
		if (trips != 0) {
			System.out.println("TRIPS : " + util.bin51(trips));
			//here... what... it could be 1 trips or 2 trips technically..
			long twoTrips = trips & trips-1;
			if(twoTrips != 0 || onlyPairs != 0){
				System.out.println("we have 2 trips or a trip and pair>?.. so fullhouse " + util.bin51(twoTrips) + " : " + util.bin51(trips^twoTrips));
				return 6;
			} else{
				System.out.println("Just 1 trips : " + util.bin51(trips) );
				return 3;
			}

		}
		if (onlyPairs != 0) {
			System.out.println("PAIRS : " + util.bin51(onlyPairs));
			long nextPair = onlyPairs & onlyPairs - 1;
			if(nextPair != 0){
				System.out.println("Two pair... but we still need to find the top 2 pair... " + util.bin51(onlyPairs));
				return 2;
			} else {
				System.out.println("single pair " + util.bin51(onlyPairs));
				return 1;
			}

			//
		}

		System.out.println("HighCard!!!!!");
		return 0;











		/*
		if 3 pair, quads, or two 3 of a kinds then dont bother checking straight, flush

		but if 2 pair or trips, then you could still have a straight or flush
		if
		 */


		//return ord;
	}

	public static long findStraights(long orCards) throws Exception {
		System.out.println("looking for straights...");

		// Here is where it gets a little tricky I think. This may even make me want to
		// redesign the card structure of all the bits...
		// I dont think this bit setup is nice for straights...
		// we surely can do it, but it will take more operations than I had hoped.
		// Theres likely a better way.

		for(long l : straights){
			if((l&orCards) == l){
				System.out.println("Found a straight: " + l);
				return l;
			}
		}
		return 0L;
	}

	public static long findFlushes(long sumCards) throws Exception {
		System.out.println("looking for flushes...");
		/*String[] someCardCodes = new String[] { "2C", "3D", "4D", "5S", "6D", "8D", "9D" };

		long[] hand = convertHandHumanShortToDecimal7(someCardCodes);

		for (long l : hand) {
			System.out
					.println("card: " + l + " : " + convertDecimalToLongName7(l) + " : " + EvalTestPlayground.bin51(l));
		}
*/
		//long suits = sumHand(hand) & suitMask;
		long suits = sumCards & suitMask;
		System.out.println("suits: " + suits + " : " + util.bin51(suits));
		for (int i = 0; i < fullSuitMasks.length; i++) {
			long masked = fullSuitMasks[i] & suits;
			if (/* masked != 0 && */ masked > almostFlush[i]) {
				System.out.println("Flush of " + util.suitLongs[i] + " :: " + i + " ");
				return suits;
			}
		}
		return suits;
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
			System.out.println("before:\t\t" + util.bin51(someCards[i]) + " : " + someCards[i]);
			System.out.println("name: " + convertDecimalToLongName7(someCards[i]));
			someCards[i] &= cardMask;
			System.out.println("after:\t\t" + util.bin51(someCards[i]) + " : " + someCards[i]);

		}
	}

	public static long findDuplicates(long sumCards) throws Exception {
		System.out.println("looking for duplicates : pairs, 2pairs, trips, full houses, quads...");
		long pairs = (sumCards & pairMask) >> 1;
		long trips = sumCards & pairs ;
		long onlyPairs = pairs  ^ trips; // since pairs includes pairs and also trips, this will get rid of trips
		// and only include pairs
		long quads = sumCards & quadMask;

		if (quads != 0) {
			System.out.println("QUADS : " + util.bin51(quads));

		}
		if (onlyPairs != 0) {
			System.out.println("PAIRS : " + util.bin51(onlyPairs));
		}
		if (trips != 0) {
			System.out.println("TRIPS : " + util.bin51(trips));
		}
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
		/*String[] cardsStrings = new String[] { "4H", "AD", "4C", "AC", "4S", "4D", "AS" }; // Quads and trips
		long[] hand = convertHandHumanShortToDecimal7(cardsStrings);
		for (int i = 0; i < hand.length; i++) {

			long aCard = hand[i];
			long aCardm1 = aCard - 1;
			System.out.println(cardsStrings[i] + " : " + aCard);
			System.out.println(EvalTestPlayground.bin51(aCard));
			System.out.println(EvalTestPlayground.bin51(aCardm1));
			System.out.println(EvalTestPlayground.bin51((aCard & aCardm1)));

		}*/
		//long ord = hand[0] | hand[1] | hand[2] | hand[3] | hand[4] | hand[5] | hand[6];
		//long sum = hand[0] + hand[1] + hand[2] + hand[3] + hand[4] + hand[5] + hand[6];

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



		//System.out.println("ORD : " + EvalTestPlayground.bin51(ord));
		System.out.println("SUM : " + util.bin51(sumCards));
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

		return -999L;
	}

	public static long[] makeAll52Cards7Decimal() {

		int cardCount = util.cardChars.length;
		int suitCount = util.suitChars.length;
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

		return util.cardLongs[cardIndex] + util.OF + util.suitLongs[suitIndex];
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

	public static long orHand(long[] cards) {
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

	public static long makeDecimalFromIndexes(int cardIndex, int suitIndex) {
		return (1L << (cardIndex * 3)) | (1L << ((suitIndex + 13) * 3));
	}

	public static String convertDecimalToLongName7(long card) throws Exception {
		return getCardLong(card) + util.OF + getSuitLong(card);
	}

	public static String convertDecimalToShortName7(long card) throws Exception {
		return (getCardChar(card) + "" + getSuitChar(card));
	}

	public static String getSuitLong(long card) throws Exception {
		return util.suitLongs[getSuitIndexDecimal(card)];
	}

	public static String getCardLong(long card) throws Exception {
		return util.cardLongs[getCardIndexDecimal(card)];
	}

	public static char getSuitChar(long card) throws Exception {
		return util.suitChars[getSuitIndexDecimal(card)];
	}

	public static char getCardChar(long card) throws Exception {
		return util.cardChars[getCardIndexDecimal(card)];
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
		for (int i = 0; i < util.suitChars.length; i++) {
			if (suitChar == util.suitChars[i]) {
				return i;
			}
		}
		throw new Exception("Error retreiving suit index for suit char: " + suitChar);
	}

	public static int getCardIndexChar(char cardChar) throws Exception {
		for (int i = 0; i < util.cardChars.length; i++) {
			if (cardChar == util.cardChars[i]) {
				return i;
			}
		}
		throw new Exception("Error retreiving card index for card char: " + cardChar);
	}


}
