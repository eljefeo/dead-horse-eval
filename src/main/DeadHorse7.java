package main;

import static main.util7.*;

public class DeadHorse7 {

	// static final char[] cardChars = new char[] {'A', 'K', 'Q', 'J', 'T', '9',
	// '8', '7', '6', '5', '4', '3', '2'};
	// static final String[] cardNames = new String[] {"Ace", "King", "Queen",
	// "Jack", "Ten", "Nine", "Eight", "Seven", "Six", "Five", "Four", "Three",
	// "Two"};



	/*
	 * long pairs = sum & pairMask; long trips = sum & (pairs>>1); long onlyPairs =
	 * (pairs>>1) ^ trips; //since pairs includes pairs and also trips, this will
	 * get rid of trips and only include pairs long quads = sum & quadMask;
	 */




	public static int getPwrTwo(long n){
		double m = Math.log(n);
		double t = Math.log(2);
		return (int) ( m/t);
		//System.out.println("m: " + m + " t: " + t + " d: " + d);
	}

	public static int eval7Checke() throws Exception {
		String[] someCardCodes = new String[] { "8C", "5C", "6C", "7C", "9C", "4C", "3C" };
		String[] someCardCodes2 = new String[] { "4D", "5C", "6C", "7C", "KC", "4C", "3C" };
		long[] hand = convertHandHumanShortToDecimal7(someCardCodes);
		long[] handm = util.maskCards(hand, cardMask);
		/*for(int i=0; i<hand.length; i++){
			long c = hand[i];
			long cm = handm[i];
			long b = 63 - Long.numberOfLeadingZeros(cm);
			String s = someCardCodes[i];
			System.out.println(s + " :\t" + c + " :\t" + util.bin51(c));
			System.out.println(s + " :\t" + cm + " :\t" + util.bin51(cm) + " :\t" + b + " : " + (b/3));
			//System.out.println(Math.pow(2,b));
			System.out.println();
		}*/

		long res = eval7(hand);
		int resi = (int) (res >>> 51);
		System.out.println("RES : " + res + " : " + resi + " : " + util.bin51(resi) + " : " + DeadHorse7.getPwrTwo(resi) );
		System.out.println(util.bin51(res));
		System.out.println(" : " + util7.handNames[resi]);
		long[] handc = util7.getRandomThisType7CardHand(1);
		System.out.println(" go this random hand " + handc);
		eval7(handc);
		System.out.println(" evald this random hand " + handc);
		long ord = orHand(handc);
		long sum = sumHand(handc);
		long ordMasked = ord & cardMask;
		try {
			System.out.println("got : "
					+ util7.convertDecimalToShortName7(handc[0]) + ", "
					+ util7.convertDecimalToShortName7(handc[1]) + ", "
					+ util7.convertDecimalToShortName7(handc[2]) + ", "
					+ util7.convertDecimalToShortName7(handc[3]) + ", "
					+ util7.convertDecimalToShortName7(handc[4]) + ", "
					+ util7.convertDecimalToShortName7(handc[5]) + ", "
					+ util7.convertDecimalToShortName7(handc[6]));
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		System.out.println("ord bit counts: " + util.countBits(ordMasked) + " : " + util.bin51(ordMasked));


		int nn = 256;
		//long ee =
		double m = Math.log(nn);
		double t = Math.log(2);
		double d = m/t;
		System.out.println("m: " + m + " t: " + t + " d: " + d + " ::: " + getPwrTwo(nn));


		/*for(int i=0; i<4; i++){
			System.out.println();
			for(int j=0; j < 13; j++){
				int ind = 13*i + j;
				long c = all52Cards7[ind];
				System.out.println(ind + " : " + c + " :\t" + util.bin51(c));
			}
		}*/

		return 0;
	}

	public static int eval7Checke2() throws Exception {
		String[] someCardCodes = new String[] { "8C", "8S", "6S", "7C", "8D", "8H", "4D" };
		String[] someCardCodes2 = new String[] { "4D", "5C", "6C", "7C", "KC", "4C", "3C" };
		long[] hand = convertHandHumanShortToDecimal7(someCardCodes);
		long[] handm = util.maskCards(hand, cardMask);
		for(int i=0; i<hand.length; i++){
			long c = hand[i];


			System.out.println("card " + i + " : " + util.bin51(c) + " : " + someCardCodes[i]);

		}

		long res = eval7(hand);
		int resi = (int) (res >>> 51);
		System.out.println("RES : " + res + " : " + resi + " : " + util.bin51(resi) + " : " + DeadHorse7.getPwrTwo(resi) );
		System.out.println("binn: " + util.bin51(res));
		System.out.println(" : " + util7.handNames[resi]);
		System.out.println("Checke 2");
		return 0;
	}
/*
number of bits possible in each type of hand
High card - 7
Pair - 6
Two pair - 4,5
Trips - 5
Straight - 5,6,7
Flush - 5,6,7
Full house - 3,4
Quads - 2,3,4
Straight flush - 5,6,7

 */

	public static long eval7(long[] hand) throws Exception {
		if(hand.length != 7){
			throw new Exception("Hand must have exactly 7 cards");
		}
		return eval7(hand[0],hand[1],hand[2],hand[3],hand[4],hand[5],hand[6]);
	}

	public static long checkFlush(long a, long b, long c, long d, long e, long f, long g, long suits ) {
		long flushCards = 0;
		for (int i = 0; i < fullSuitMasks.length; i++) {
			//long masked = fullSuitMasks[i] & suits;
			long fm = fullSuitMasks[i];
			if (/* masked != 0 && */ (fm & suits) > almostFlush[i]) {
				if ((a & fm) != 0) flushCards |= a;
				if ((b & fm) != 0) flushCards |= b;
				if ((c & fm) != 0) flushCards |= c;
				if ((d & fm) != 0) flushCards |= d;
				if ((e & fm) != 0) flushCards |= e;
				if ((f & fm) != 0) flushCards |= f;
				if ((g & fm) != 0) flushCards |= g;
				flushCards &= cardMask;
				long ff = (flushCards & flushCards >>> 3 & flushCards >>> 6 & flushCards >>> 9 & flushCards >>> 12);
				if (ff != 0 ) { //gotta check the stupid A,2,3,4,5 straight
					//crappy extra check to get rid of extra straight cards:
					long u=ff;
					if((u &= u-1) != 0){
						//ff = u;
						long uu=u;
						if((uu &= uu-1) != 0) {
							ff = uu;
						} else {
							ff = u;
						}
					}
					ff <<= 12;

					//return 8; //return straight flush
					//System.out.println("binnn : " + util.bin51(flushCards) + " : " + util.bin51(ff));
					//System.out.println("::33 " + util.bin51(18014398509481984L | ff) + " : " + (util.bin51(((18014398509481984L | ff) >>> 51))) + " :: " + ((18014398509481984L | ff) >>> 51));
					//return ((8 << 51) | flushCards);
					return (18014398509481984L | ff);
				} else if((flushCards & 68719477321L) == 68719477321L){

					return (18014398509481984L | 512); //512 is just the 5 high straight flush
				}

				long u=flushCards;
				if((u &= u-1) != 0){

					long uu=u;
					if((uu &= uu-1) != 0) {
						flushCards = uu;
					} else {
						flushCards = u;
					}
				}
				//return 5; //return flush
				return 11258999068426240L | flushCards;
			}
		}
		return 0;
	}
	public static long checkStraight(long a, long b, long c, long d, long e, long f, long g, long ord) {
		long straightCards = 0;
		long or = ord & cardMask;
		long ors = (or & (or >>> 3 & or >>> 6 & or >>> 9 & or >>> 12));
		if (ors != 0) {
			long u = ors;
			if((u &= u-1) != 0){

				long uu=u;
				if((uu &= uu-1) != 0) {
					ors = uu;
				} else {
					ors = u;
				}
			}
			return 9007199254740992L | ors;
			//return 4;
		} else if ((or & 68719477321L) == 68719477321L){
			return 9007199254740992L | 512;
		}
		return 0;
	}

	public static long eval7beta(long a, long b, long c, long d, long e, long f, long g) {
		//int aa  = getPwrTwo(a&cardMask), bb  = getPwrTwo(b&cardMask), cc  = getPwrTwo(c&cardMask), dd  = getPwrTwo(d&cardMask),
		//		ee  = getPwrTwo(e&cardMask), ff  = getPwrTwo(f&cardMask), gg  = getPwrTwo(g&cardMask);
		long ord = a | b | c | d | e | f | g; //orHand(hand);

		long sum = a + b + c + d + e + f + g; //sumHand(hand);
		long tord = ord & cardMask;
		//System.out.println(util.countBits(tord) + " this many Starting bits");
		long bitCounter = (tord & (tord - 1)); //remove 1 bit
		//System.out.println(util.countBits(bitCounter) + " this many bt bits");
		bitCounter &= bitCounter - 1; //remove 2nd bit
		long flushCards = 0;
		boolean isFlush = false, isStrt = false;
		long suits = sum & suitMask;
		long quads = sum & quadMask;
		long pairs = (sum & pairMask) >> 1;
		long trips = sum & pairs;
		long onlyPairs = pairs ^ trips; // since pairs includes pairs and also trips, this will get rid of trips
		boolean isTrips = false;

		if((bitCounter &= bitCounter - 1) == 0 || (bitCounter &= bitCounter - 1) == 0){//2 or 3 or 4 bits. this could be fullhouse or quads or twoPair
			/*if(util.countBits(tord) >  4){
				System.out.println("ok got here with 4 as baaddddd:");
				return 0;
			}*/
			//System.out.println(util.countBits(bitCounter) + " this should be 2 bits or 3 or 4 " + util.countBits(tord));
			if (quads != 0)
				return 15762598695796736L;

			if (trips != 0) { //checking for fullhouse
				isTrips = true;
				long twoTrips = trips & trips - 1;
				if (twoTrips != 0 || onlyPairs != 0) {
					return 13510798882111488L;//return 6; fullhouse
				}
			}

			long multPairs = onlyPairs & onlyPairs - 1; //this would be a second pair
			if((multPairs =  multPairs - 1) != 0){ //this actually means there were 3 pairs (only need top 2 pairs)
				return 4503599627370496L | (multPairs<<1);//return 2pair and oring the top 2 pair, still need to add the highest card as a kicker though...
			}
		}
		if((bitCounter &= bitCounter - 1) == 0 ){ //this is 5 bits - 2pair, trips, straight, flush, straight flush... damn ...



			//check for flushes and straight flushes
			long flsh = checkFlush(a,b,c,d,e,f,g,suits);
			if(flsh != 0) return flsh;

			//check for straights
			long strs = checkStraight(a,b,c,d,e,f,g,ord);
			if(strs != 0) return strs;


			if (onlyPairs != 0) { //2pair check first, since if we have 5 bits, and there is a pair, then that means the last card is another unique card
				// and we would actually have more than 5 bits if the hand was a flush, straight, or straight flush.
				//meaning if we get 5 bits and have a pair, theres no way it would be flush, straight, or straight flush so dont bother checking them first
				long nextPair = onlyPairs & onlyPairs - 1;
				if (nextPair != 0) {
					long finalPair = nextPair & nextPair - 1;
					//return nextPair | (finalPair != 0) ? finalPair :  ;
					if(finalPair != 0){
						return 4503599627370496L | nextPair;
					}
					//return 2;
					return 4503599627370496L | onlyPairs;
				}
			}
			if (trips != 0) {
				long twoTrips = trips & trips - 1;
				if (twoTrips != 0 || onlyPairs != 0) {
					//return 6;
					return 13510798882111488L;
				} else {
					//return 3;
					return 6755399441055744L;
				}
			}
		}  else if((bitCounter &= bitCounter - 1) == 0 ){ //this is 6 bits - pair, straight, flush, straight flush...

			//check for flushes and straight flushes
			long flsh = checkFlush(a,b,c,d,e,f,g,suits);
			if(flsh != 0) return flsh;

			//check for straights
			long strs = checkStraight(a,b,c,d,e,f,g,ord);
			if(strs != 0)
				return strs;

			if (onlyPairs != 0) {
				/*long nextPair = onlyPairs & onlyPairs - 1;
				if (nextPair != 0) {
					long finalPair = nextPair & nextPair - 1;
					//return nextPair | (finalPair != 0) ? finalPair :  ;
					if(finalPair != 0){
						return 4503599627370496L;
					}
					//return 2;
					return 4503599627370496L;
				} else {*/
				//return 1;//<<(40);
				return 2251799813685248L;
				//}
			}

		} else if((bitCounter &= bitCounter - 1) == 0 ) { //this is 7 bits - highcard, straight, flush, straight flush...
			/*if(util.countBits(tord) ==  7){
				System.out.println("ok got here with 7as expected:");
				return 0;
			}*/
			//000000000000001001000001001000000000001000000001001
			//000000000000001001000001001000000000001000000001001
			//System.out.println("7 bitsssss " + util.bin51(tord));
			//check for flushes and straight flushes
			long flsh = checkFlush(a,b,c,d,e,f,g,suits);
			if(flsh != 0) return flsh;

			//check for straights
			long strs = checkStraight(a,b,c,d,e,f,g,ord);
			if(strs != 0)
				return strs;
			//return 0;



		} else if((bitCounter &= bitCounter - 1) == 0){
			System.out.println("8 bits? " + util.bin51(tord));
		} else {
			System.out.println("how the hell did we even get here " + util.bin51(tord));
		}




		long flsh = checkFlush(a,b,c,d,e,f,g,suits);
		if(flsh != 0) return flsh;

		//check for straights
		long strs = checkStraight(a,b,c,d,e,f,g,ord);
		if(strs != 0)
			return strs;

		/*for(int i=0; i<straights.length; i++){
			long l = straights[i];
			if((l&ord) == l){
				return 4;
			}
		}*/


		//check pairs, trips, quads, fullhouse, two pair....


		// and only include pairs
		if (quads != 0) {
			//return 0x1C000000;
			//return 7;
			return 15762598695796736L;
		}
		if (trips != 0) {
			long twoTrips = trips & trips - 1;
			if (twoTrips != 0 || onlyPairs != 0) {
				//return 6;
				return 13510798882111488L;
			} else {
				//return 3;
				return 6755399441055744L;
			}
		}
		if (onlyPairs != 0) {
			long nextPair = onlyPairs & onlyPairs - 1;
			if (nextPair != 0) {
				long finalPair = nextPair & nextPair - 1;
				//return nextPair | (finalPair != 0) ? finalPair :  ;
				if(finalPair != 0){
					return 4503599627370496L;
				}
				//return 2;
				return 4503599627370496L;
			} else {
				//return 1;//<<(40);
				return 2251799813685248L;
			}
		}
		//return bitCounter;
		return 0;
	}

	//public static long eval7Working140Million(long a, long b, long c, long d, long e, long f, long g) {
	public static long eval7(long a, long b, long c, long d, long e, long f, long g) {
		//int aa  = getPwrTwo(a&cardMask), bb  = getPwrTwo(b&cardMask), cc  = getPwrTwo(c&cardMask), dd  = getPwrTwo(d&cardMask),
		//		ee  = getPwrTwo(e&cardMask), ff  = getPwrTwo(f&cardMask), gg  = getPwrTwo(g&cardMask);
		long ord = a | b | c | d | e | f | g; //orHand(hand);
		long sum = a + b + c + d + e + f + g; //sumHand(hand);
		long suits = sum & suitMask, flushCards = 0;
		//check for flushes and straight flushes
		for (int i = 0; i < fullSuitMasks.length; i++) {
			long fm = fullSuitMasks[i];
			if ((fm & suits) > almostFlush[i]) {
				if ((a & fm) != 0) flushCards |= a;
				if ((b & fm) != 0) flushCards |= b;
				if ((c & fm) != 0) flushCards |= c;
				if ((d & fm) != 0) flushCards |= d;
				if ((e & fm) != 0) flushCards |= e;
				if ((f & fm) != 0) flushCards |= f;
				if ((g & fm) != 0) flushCards |= g;
				flushCards &= cardMask;
				long straightFlushCards = (flushCards & flushCards >>> 3 & flushCards >>> 6 & flushCards >>> 9 & flushCards >>> 12);
				if (straightFlushCards != 0 ) {//crappy extra check to get rid of extra straight cards:
					long u=straightFlushCards;
					if((u &= u-1) != 0){
						long uu=u;
						straightFlushCards = (((uu &= uu-1) != 0) ? uu : u) /*<< 12*/;
					}
					return (18014398509481984L | straightFlushCards);//returns the lowest card in the straight (for 5,6,7,8,9 it will be 5)
				} else if((flushCards & 68719477321L) == 68719477321L){//gotta check the stupid A,2,3,4,5 straight
					return (18014398509481984L /*| 512*/); //if we dont or anything that means lowest possible strt. a2345
				}

				long u = flushCards;
				if((u &= u-1) != 0){
					long uu=u;
					flushCards = ((uu &= uu-1) != 0) ? uu : u;
				}
				return 11258999068426240L | flushCards;
			}
		}

		long or = ord & cardMask;
		long ors = (or & (or >>> 3 & or >>> 6 & or >>> 9 & or >>> 12));
		if (ors != 0) {
			long u = ors;
			if((u &= u-1) != 0){
				long uu=u;
				ors = ((uu &= uu-1) != 0) ? uu : u;
			}
			return 9007199254740992L | ors;
		} else if ((or & 68719477321L) == 68719477321L){
			return 9007199254740992L | 512;
		}

		//check pairs, trips, quads, fullhouse, two pair....
		long quads = (sum & quadMask) >> 2;
		if (quads != 0) {
			long kickers = or ^ quads;
			kickers &= kickers - 1;
			return 15762598695796736L | (quads << 1) | (kickers & kickers - 1);
		}

		long pairsAndTrips = (sum & pairMask) >> 1;
		long trips = sum & pairsAndTrips;
		long pairs = trips ^ pairsAndTrips; // since pairs includes pairs and also trips, this will get rid of trips
		// and only include pairs
		if (trips != 0) {
			long twoTrips = trips & trips - 1;
			if (twoTrips != 0) {
				return 13510798882111488L | ((twoTrips << 1) | trips^twoTrips);//fullhouse
			} else if (pairs != 0){
				long nextPair = pairs & pairs - 1;
				return 13510798882111488L | ((trips << 1) | (nextPair != 0 ? nextPair : pairs) );//fullhouse
			} else {
				long kickers = or ^ trips;
				kickers &= kickers - 1;
				return 6755399441055744L | trips << 1 | (kickers & kickers - 1);
			}
		}
		if (pairs != 0) {
			long nextPair = pairs & pairs - 1;
			if (nextPair != 0) {
				long finalPair = nextPair & nextPair - 1;

				if(finalPair != 0){
					long kickers = or ^ nextPair;
					kickers &= kickers -1;
					return 4503599627370496L | nextPair << 1 | (kickers & kickers - 1) ;
				} else {
					long kickers = or ^ pairs;
					kickers &= kickers -1;
					return 4503599627370496L | pairs << 1 | (kickers & kickers - 1);
				}
				//return 4503599627370496L | ((finalPair != 0) ? nextPair : pairs); //this gives us the pair, but need the other 3 kickers
			} else {
				long kickers = or ^ pairs;
				kickers &= kickers -1;
				kickers &= kickers -1;
				return 2251799813685248L |  pairs << 1 | (kickers & kickers - 1);
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



	public static long[] getSomeHand() throws Exception {
		return convertHandHumanShortToDecimal7(new String[] { "AH", "5S", "7S", "AC", "2C", "TD", "4S" });
	}




}
