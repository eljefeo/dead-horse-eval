package com.jmedia.poker;

import java.util.List;
import java.util.Map;

import static java.util.Map.entry;
import static com.jmedia.poker.Util7.*;

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



	private static final long[] bitPlaces = new long[] {1L, 8L, 64L, 512L, 4096L, 32768L, 262144L, 2097152L, 16777216L, 134217728L, 1073741824L, 8589934592L, 68719476736L};


	private static long[] returnNumbers = new long[]{
			0L,72057594037927936L, 144115188075855872L, 216172782113783808L, 288230376151711744L,360287970189639680L, 432345564227567616L, 504403158265495552L, 576460752303423488L
	};
	static final Map<Long, Integer> bitMap = Map.ofEntries(
			entry(1L, 0),
			entry(8L, 2),
			entry(64L, 4),
			entry(512L, 6),
			entry(4096L, 8),
			entry(32768L, 10),
			entry(262144L, 12),
			entry(2097152L, 14),
			entry(16777216L, 16),
			entry(134217728L, 18),
			entry(1073741824L, 20),
			entry(8589934592L, 22),
			entry(68719476736L, 24)
	);

	/*
	2 1 -> 0
	3 1000 -> 2 bits to be 10
	4 1000000 -> 4 bits to be 100
	5 1000000000 -> 6 bits to be 1000
	6 1000000000000 -> 8 bits to be 10000
	 */



	public static int eval7Checke() throws Exception {
		String[] someCardCodes = new String[] { "8C", "5C", "6C", "7C", "9C", "4C", "3C" };
		String[] someCardCodes2 = new String[] { "4D", "5C", "6C", "7C", "KC", "4C", "3C" };
		long[] hand = shortCardNamesToDecimals(someCardCodes);
		long[] handm = Util.maskCards(hand, cardMask);
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
		System.out.println("RES : " + res + " : " + resi + " : " + Util.bin51(resi) + " : " + Util.getPwrTwo(resi) );
		System.out.println(Util.bin51(res));
		System.out.println(" : " + Util7.handNames[resi]);
		long[] handc = Util7.getRandomThisType7CardHand(1);
		System.out.println(" got this random hand " + handc);
		eval7(handc);
		System.out.println(" evald this random hand " + handc);
		long ord = orHand(handc);
		long sum = sumHand(handc);
		long ordMasked = ord & cardMask;
		try {
			System.out.println("got : "
					+ Util7.convertDecimalToShortCardName(handc[0]) + ", "
					+ Util7.convertDecimalToShortCardName(handc[1]) + ", "
					+ Util7.convertDecimalToShortCardName(handc[2]) + ", "
					+ Util7.convertDecimalToShortCardName(handc[3]) + ", "
					+ Util7.convertDecimalToShortCardName(handc[4]) + ", "
					+ Util7.convertDecimalToShortCardName(handc[5]) + ", "
					+ Util7.convertDecimalToShortCardName(handc[6]));
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		System.out.println("ord bit counts: " + Util.countBits(ordMasked) + " : " + Util.bin51(ordMasked));


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
		String[] someCardCodes = new String[] { "AS", "5S", "QD", "9C", "KC", "2D", "3S" };
		String[] someCardCodes2 = new String[] { "4D", "5C", "6C", "7C", "KC", "4C", "3C" };
		long[] hand = shortCardNamesToDecimals(someCardCodes);
		long[] handm = Util.maskCards(hand, cardMask);
		for(int i=0; i<hand.length; i++){
			long c = hand[i];

			System.out.println("card " + i + " : " + Util.bin51(c) + " : " + someCardCodes[i]);

		}

		long res = eval7(hand);
		int resi = (int) (res >>> 51);
		System.out.println("RES : " + res + " : " + resi + " : " + Util.bin51(resi) + " : " + Util.getPwrTwo(resi) );
		System.out.println("binn: " + Util.bin51(res));
		System.out.println(" : " + Util.handNames[resi]);
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

	public static long eval7(long[] hand) {/*throws Exception {*/
		/*if(hand.length != 7){
			throw new Exception("Hand must have exactly 7 cards");
		}*/
		return eval7(hand[0],hand[1],hand[2],hand[3],hand[4],hand[5],hand[6]);
	}

	public static long eval7(Long[] hand) {// throws Exception {
		//if(hand.length != 7){
		//	throw new Exception("Hand must have exactly 7 cards");
		//}
		return eval7(hand[0],hand[1],hand[2],hand[3],hand[4],hand[5],hand[6]);
	}

	public static long eval7(PokerHand hand) throws Exception {

		if(hand != null){
			List<String> cards = hand.getCards();
			if(cards != null && cards.size() == numOfCardsPerHand){
				//return eval5(cards.get(0), cards.get(1), cards.get(2), cards.get(3), cards.get(4));
				long[] cardDecimals = shortCardNamesToDecimals(cards);
				return eval7(cardDecimals);
			} else {
				if(cards != null){
					System.out.println(cards);
				}
				throw new IllegalArgumentException("Invalid Cards");
			}
		} else {
			throw new IllegalArgumentException("Cards are null");
		}

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

	public static long eval7beta(long[] cards) {
		return eval7beta(cards[0], cards[1], cards[2], cards[3], cards[4], cards[5], cards[6]);
	}
	/*public static long eval7beta(long a, long b, long c, long d, long e, long f, long g) {
		return 0L;
	}*/
	public static long eval7beta(long a, long b, long c, long d, long e, long f, long g) {

		//TODO Still need to shift the bits upon the return so we have important bits and kicker bits. We are not doing that yet!
		final long[] cds = new long[] { a, b, c, d, e, f, g};
		final double l2 = Math.log(2);
		final long ord = a | b | c | d | e | f | g; //orHand(hand);
		final long sum = a + b + c + d + e + f + g; //sumHand(hand);
		final long suits = sum & suitMask;
		long flushCards = 0;
		//check for flushes and straight flushes
		for (int i = 0; i < fullSuitMasks.length; i++) {
			final long fm = fullSuitMasks[i];
			if ((fm & suits) > almostFlush[i]) {
				int flshCounter = 0;

				for(int j = 0; j < 7; j++){
					if ((cds[j] & fm) != 0){flushCards |= cds[j];flshCounter++;}
				}

				flushCards &= cardMask;
				long straightFlushCards = (flushCards & flushCards >>> 3 & flushCards >>> 6 & flushCards >>> 9 & flushCards >>> 12);
				if (straightFlushCards != 0 ) {//crappy extra check to get rid of extra straight cards:
					//TODO test flushes and straight flushes to make sure everything is in order after a couple small var changes
					final long u = straightFlushCards & straightFlushCards - 1;
					if(u != 0){
						final long uu = u & u - 1; //we only need 5 cards for a straight, if you more than 5 cards to a straight (like a 6 card straight), this will chop off the lowest card
						straightFlushCards = ((uu != 0) ? uu : u) ;/*<< 12;*/ //this also chops off the lowest card in case you have a 7 card straight
					}
					return (576460752303423488L | straightFlushCards);//returns the lowest card in the straight (for 5,6,7,8,9 it will be 5)
				} else if((flushCards & 68719477321L) == 68719477321L){//gotta check the stupid A,2,3,4,5 straight
					//System.out.println("Straight flush 2 : " + util.bin64(straightFlushCards));
					return (576460752303423488L /*| 512*/); //if we dont or anything that means lowest possible strt. a2345
				}

				if(flshCounter == 7) {
					flushCards &= flushCards - 1;
					flushCards &= flushCards - 1;
				} else if(flshCounter == 6) {
					flushCards &= flushCards - 1;
				}
				//System.out.println("Flush only : " + util.bin64(flushCards));
				return 360287970189639680L | flushCards;
			}
		}

		long or = ord & cardMask;
		//System.out.println("Straight or & card mask : " + util.bin64(or));
		long ors = (or & (or >>> 3 & or >>> 6 & or >>> 9 & or >>> 12));
		if (ors != 0) {
			long u = ors;
			if((u &= u-1) != 0){
				long uu=u;
				ors = ((uu &= uu-1) != 0) ? uu : u;
			}
			//System.out.println("Straight 1 ors : "  + util.bin64(ors));
			return 288230376151711744L | ors; //return Straight
		} else if ((or & 68719477321L) == 68719477321L){
			//System.out.println("Straight 2 ors : "  + util.bin64(ors));
			return 288230376151711744L; //return A,2,3,4,5 Straight I think? I forget...
		}

		//check pairs, trips, quads, fullhouse, two pair....
		final long quads = (sum & quadMask) >> 2;
		if (quads != 0) {
			long kickers = or ^ quads;
			long kickers2 = kickers & kickers - 1;
			if(kickers2 != 0){
				kickers = kickers2;
			}
			kickers2 = kickers & kickers - 1;
			if(kickers2 != 0){
				kickers = kickers2;
			}

			//long returnBase = 504403158265495552L;
			//long returnQds = quads << (39 - (bitMap.get(quads)));
			int chck = (int) (Math.log(quads) / l2 );
			int d3 = chck / 3;
			return 504403158265495552L |  quads << (39 + d3 - chck) | kickers; //Quads
			//return 504403158265495552L |  quads << (39 - (bitMap.get(quads))) | kickers; //Quads
		}

		final long pairsAndTrips = (sum & pairMask) >> 1;
		final long trips = sum & pairsAndTrips;
		long pairs = trips ^ pairsAndTrips; // since pairs includes pairs and also trips, this will get rid of trips
		// and only include pairs
		if (trips != 0) {
			final long twoTrips = trips & trips - 1;
			if (twoTrips != 0) {
				int chck = (int) (Math.log(twoTrips) / l2 );
				int d3 = chck / 3;
				return 432345564227567616L | twoTrips << (39 + d3 - chck)  | trips^twoTrips;//fullhouse
				//return 432345564227567616L | twoTrips << (39 - (bitMap.get(twoTrips)))  | trips^twoTrips;//fullhouse
			} else if (pairs != 0){
				final long nextPair = pairs & pairs - 1;
				int chck = (int) (Math.log(trips) / l2 );
				int d3 = chck / 3;
				return 432345564227567616L | trips << (39 + d3 - chck) | (nextPair != 0 ? nextPair : pairs) ;//fullhouse
				//return 432345564227567616L | ( (trips << (39 - (bitMap.get(trips)))) | (nextPair != 0 ? nextPair : pairs)) ;//fullhouse
			} else {
				long kickers = or ^ trips;
				kickers &= kickers - 1;

				int chck = (int) (Math.log(trips) / l2 );
				int d3 = chck / 3;
				//0000001100000000000100000000001000000000001000000000000000000000
				return 0x300000000000000L | trips << (39 + d3 - chck) | kickers & kickers - 1; //3 of a kind / trips
				//return 0x300000000000000L | (trips << (39 - (bitMap.get(trips)))) | kickers & kickers - 1; //3 of a kind / trips
			}
		}
		if (pairs != 0) {
			long nextPair = pairs & pairs - 1;
			if (nextPair != 0) {
				final long finalPair = nextPair & nextPair - 1;

				if(finalPair != 0){
					or ^= nextPair;
					or &= or - 1;
					pairs = finalPair;
					nextPair = finalPair ^ nextPair ;//biggest pair
				} else {
					or ^= pairs;
					or &= or - 1;
					or &= or - 1;
					pairs ^= nextPair;
				}
				//pairs = (pairs << (39 - (bitMap.get(pairs))));
				//nextPair = (nextPair << (39 - (bitMap.get(nextPair))));
				int chck = (int) (Math.log(pairs) / l2 );
				int d3 = chck / 3;

				int chck2 = (int) (Math.log(nextPair) / l2 );
				int d32 = chck / 3;
				return 144115188075855872L | pairs << (39 + d3 - chck)  | nextPair << (39 + d32 - chck2)  | or; //2 pair
				//return 144115188075855872L | (pairs << (39 - (bitMap.get(pairs))))| (nextPair << (39 - (bitMap.get(nextPair))))  | or; //2 pair
			} else {
				or ^= pairs;
				or &= or - 1;
				int chck = (int) (Math.log(pairs) / l2 );
				int d3 = chck / 3;
				return 72057594037927936L | pairs << (39 + d3 - chck) | or & or -1; //1 pair
				//return 72057594037927936L |  (pairs << (39 - (bitMap.get(pairs)))) | or & or -1; //1 pair
			}
		}
		//System.out.println("Just high card hand");
		or &= or - 1;
		or &= or - 1;
		//System.out.println("High cards all kickers : " + util.bin64(or));
		return or; //high card
	}



	public static long eval7(long a, long b, long c, long d, long e, long f, long g) {
		long ord = a | b | c | d | e | f | g; //orHand(hand);
		long sum = a + b + c + d + e + f + g; //sumHand(hand);
		long suits = sum & suitMask;
		long flushCards = 0;
		//check for flushes and straight flushes
		for (int i = 0; i < fullSuitMasks.length; i++) {
			long fm = fullSuitMasks[i];
			if ((fm & suits) > almostFlush[i]) {
				int flshCounter = 0;
				if ((a & fm) != 0){flushCards |= a;flshCounter++;}
				if ((b & fm) != 0){flushCards |= b;flshCounter++;}
				if ((c & fm) != 0){flushCards |= c;flshCounter++;}
				if ((d & fm) != 0){flushCards |= d;flshCounter++;}
				if ((e & fm) != 0){flushCards |= e;flshCounter++;}
				if ((f & fm) != 0){flushCards |= f;flshCounter++;}
				if ((g & fm) != 0){flushCards |= g;flshCounter++;}
				flushCards &= cardMask;
				long straightFlushCards = (flushCards & flushCards >>> 3 & flushCards >>> 6 & flushCards >>> 9 & flushCards >>> 12);
				if (straightFlushCards != 0 ) {//crappy extra check to get rid of extra straight cards:
					long u=straightFlushCards;
					if((u &= u-1) != 0){
						long uu=u;
						straightFlushCards = (((uu &= uu-1) != 0) ? uu : u) ;/*<< 12;*/
					}
					/*if(flshCounter == 7){
						straightFlushCards &= straightFlushCards - 1;
						straightFlushCards &= straightFlushCards - 1;
					}
					if(flshCounter == 6){
						straightFlushCards &= straightFlushCards - 1;
					}*/
					return (18014398509481984L | straightFlushCards);//returns the lowest card in the straight (for 5,6,7,8,9 it will be 5)
				} else if((flushCards & 68719477321L) == 68719477321L){//gotta check the stupid A,2,3,4,5 straight
					return (18014398509481984L /*| 512*/); //if we dont or anything that means lowest possible strt. a2345
				}

				if(flshCounter == 7){
					flushCards &= flushCards - 1;
					flushCards &= flushCards - 1;
				}
				if(flshCounter == 6){
					flushCards &= flushCards - 1;
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
					pairs = nextPair;
				}

				/*if(finalPair != 0){
					long kickers = or ^ nextPair;
					kickers &= kickers -1;
					return 4503599627370496L | nextPair << 1 | (kickers & kickers - 1) ;
				} else {*/
				long kickers = or ^ pairs;
				kickers &= kickers -1;
				return 4503599627370496L | pairs << 1 | (kickers & kickers - 1);
				//}
				//return 4503599627370496L | ((finalPair != 0) ? nextPair : pairs); //this gives us the pair, but need the other 3 kickers
			} else {
				long kickers = or ^ pairs;
				kickers &= kickers -1;
				kickers &= kickers -1;
				return 2251799813685248L |  pairs << 1 | (kickers & kickers - 1);
			}
		}
		or &= or - 1;
		return or &= or - 1;
	}


	//public static long eval7Working140Million(long a, long b, long c, long d, long e, long f, long g) {
	/*public static long eval7(long a, long b, long c, long d, long e, long f, long g) {

		//TODO Still need to shift the bits upon the return so we have important bits and kicker bits. We are not doing that yet!
		long ord = a | b | c | d | e | f | g; //orHand(hand);
		long sum = a + b + c + d + e + f + g; //sumHand(hand);
		long suits = sum & suitMask;
		long flushCards = 0;
		//check for flushes and straight flushes
		for (int i = 0; i < fullSuitMasks.length; i++) {
			long fm = fullSuitMasks[i];
			if ((fm & suits) > almostFlush[i]) {
				int flshCounter = 0;
				if ((a & fm) != 0){flushCards |= a;flshCounter++;}
				if ((b & fm) != 0){flushCards |= b;flshCounter++;}
				if ((c & fm) != 0){flushCards |= c;flshCounter++;}
				if ((d & fm) != 0){flushCards |= d;flshCounter++;}
				if ((e & fm) != 0){flushCards |= e;flshCounter++;}
				if ((f & fm) != 0){flushCards |= f;flshCounter++;}
				if ((g & fm) != 0){flushCards |= g;flshCounter++;}
				flushCards &= cardMask;
				long straightFlushCards = (flushCards & flushCards >>> 3 & flushCards >>> 6 & flushCards >>> 9 & flushCards >>> 12);
				if (straightFlushCards != 0 ) {//crappy extra check to get rid of extra straight cards:
					long u=straightFlushCards;
					if((u &= u-1) != 0){ //we only need 5 cards for a straight, if you more than 5 cards to a straight (like a 6 card straight), this will chop off the lowest card
						long uu=u;
						straightFlushCards = (((uu &= uu-1) != 0) ? uu : u) ;*//*<< 12;*//* //this also chops off the lowest card in case you have a 7 card straight
					}
					*//*if(flshCounter == 7){
						straightFlushCards &= straightFlushCards - 1;
						straightFlushCards &= straightFlushCards - 1;
					}
					if(flshCounter == 6){
						straightFlushCards &= straightFlushCards - 1;
					}*//*
					return (18014398509481984L | straightFlushCards);//returns the lowest card in the straight (for 5,6,7,8,9 it will be 5)
				} else if((flushCards & 68719477321L) == 68719477321L){//gotta check the stupid A,2,3,4,5 straight
					return (18014398509481984L *//*| 512*//*); //if we dont or anything that means lowest possible strt. a2345
				}

				if(flshCounter == 7){
					flushCards &= flushCards - 1;
					flushCards &= flushCards - 1;
				}
				if(flshCounter == 6){
					flushCards &= flushCards - 1;
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
			return 9007199254740992L | ors; //return Straight
		} else if ((or & 68719477321L) == 68719477321L){
			return 9007199254740992L | 512; //return A,2,3,4,5 Straight I think? I forget...
		}

		//check pairs, trips, quads, fullhouse, two pair....
		long quads = (sum & quadMask) >> 2;
		if (quads != 0) {

			long kickers = or ^ quads;
			System.out.println("initial kickers " + util.bin64(kickers) + " : " + kickers);
			long kickers2 = kickers & kickers - 1;
			if(kickers2 != 0){
				System.out.println("doing kickers 1 " + util.bin64(kickers2) + " : " + kickers2);
				kickers = kickers2;
				kickers2 = kickers & kickers - 1;
				if(kickers2 != 0){
					System.out.println("doing kickers 2 " + util.bin64(kickers2) + " : " + kickers2);
					kickers = kickers2;
				}
			}



			System.out.println("quad card:\t" + util.bin64(quads) + " : " + quads);
			System.out.println("Kick card:\t" + util.bin64(kickers) + " : " + kickers);
			return 15762598695796736L | (quads << 1) | kickers;
			//return 15762598695796736L |  (quads >> bitMap.get(quads)*2) << 13 | finalKicker >> (bitMap.get(finalKicker)*2);
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
					pairs = nextPair;
				}

				*//*if(finalPair != 0){
					long kickers = or ^ nextPair;
					kickers &= kickers -1;
					return 4503599627370496L | nextPair << 1 | (kickers & kickers - 1) ;
				} else {*//*
					long kickers = or ^ pairs;
					kickers &= kickers -1;
					return 4503599627370496L | pairs << 1 | (kickers & kickers - 1);
				//}
				//return 4503599627370496L | ((finalPair != 0) ? nextPair : pairs); //this gives us the pair, but need the other 3 kickers
			} else {
				long kickers = or ^ pairs;
				kickers &= kickers -1;
				kickers &= kickers -1;
				return 2251799813685248L |  pairs << 1 | (kickers & kickers - 1);
			}
		}
		or &= or - 1;
		return or &= or - 1; //high card
	}*/

	public static long testeval7() throws Exception {
		String[] someCardCodes = new String[] { "9C", "9H", "3C", "KS", "9S", "JD", "9D" };

		long[] hand = shortCardNamesToDecimals(someCardCodes);

		for (long l : hand) {
			System.out.println("card: " + l + " : " + convertDecimalToLongName(l) + " : " + Util.bin51(l));
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
		System.out.println("suits: " + suits + " : " + Util.bin51(suits));
		for (int i = 0; i < fullSuitMasks.length; i++) {
			//long masked = fullSuitMasks[i] & suits;
			if (/* masked != 0 && */ (fullSuitMasks[i] & suits) > almostFlush[i]) {
				System.out.println("Flush of " + Util.suitLongs[i] + " :: " + i + " ");
				//return suits;


				//need to return the highest 5 cards with this suit I guess
				//well since there is no way to have 2 different flushes on the board...
				//can we just return the highest card of the flush? thats all you need to know who won in case
				//2 people both have a flush
				for(int j=0; j<hand.length; j++){
					if((fullSuitMasks[i] & hand[i]) != 0){
						long returnFlush = 0x14000000 | (hand[i] & suitMask);
						System.out.println("About to return a Flush: " + returnFlush + " :: " + Util.bin64(returnFlush));


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
					System.out.println("STraIght FluSh " + Util.bin51(ord));
					return 8;//since also a flush, its a straight flush.
				}

				else{
					System.out.println("Normal Straight " + Util.bin51(ord));
					return 4; //if no flush, just straight
				}

			}
		}
		if(isFlush){
			System.out.println("yep just returning a Flush: " + " :: " + Util.bin64(ord));
			return 5;
		}


		System.out.println("looking for duplicates : pairs, 2pairs, trips, full houses, quads...");
		long pairs = (sum & pairMask) >> 1;
		long trips = sum & pairs ;
		long onlyPairs = pairs  ^ trips; // since pairs includes pairs and also trips, this will get rid of trips
		// and only include pairs
		long quads = sum & quadMask;

		if (quads != 0) {
			System.out.println("QUADS : " + Util.bin51(quads));
			//return 0x1C000000;
			return 7;
		}
		if (trips != 0) {
			System.out.println("TRIPS : " + Util.bin51(trips));
			//here... what... it could be 1 trips or 2 trips technically..
			long twoTrips = trips & trips-1;
			if(twoTrips != 0 || onlyPairs != 0){
				System.out.println("we have 2 trips or a trip and pair>?.. so fullhouse " + Util.bin51(twoTrips) + " : " + Util.bin51(trips^twoTrips));
				return 6;
			} else{
				System.out.println("Just 1 trips : " + Util.bin51(trips) );
				return 3;
			}

		}
		if (onlyPairs != 0) {
			System.out.println("PAIRS : " + Util.bin51(onlyPairs));
			long nextPair = onlyPairs & onlyPairs - 1;
			if(nextPair != 0){
				System.out.println("Two pair... but we still need to find the top 2 pair... " + Util.bin51(onlyPairs));
				return 2;
			} else {
				System.out.println("single pair " + Util.bin51(onlyPairs));
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
		System.out.println("suits: " + suits + " : " + Util.bin51(suits));
		for (int i = 0; i < fullSuitMasks.length; i++) {
			long masked = fullSuitMasks[i] & suits;
			if (/* masked != 0 && */ masked > almostFlush[i]) {
				System.out.println("Flush of " + Util.suitLongs[i] + " :: " + i + " ");
				return suits;
			}
		}
		return suits;
	}

	public static void tt() throws Exception {

		long[] all52Cards = Util7.makeAll52CardsDecimal();
		/*
		 * for(long l : all52Cards) { System.out.print(" " + l + "L,"); }
		 */
		int cnt = 0;
		for (long l : all52Cards) {
			if (cnt++ > 12) {
				System.out.println();
				cnt = 1;
			}
			System.out.print(convertDecimalToLongName(l) + ", ");
		}

		String[] someCardCodes = new String[] { "2C", "3H", "4H", "5H", "6H", "8S", "9H", "TH", "JD", "QH", "KH",
				"AH" };
		long[] someCards = new long[someCardCodes.length];
		for (int i = 0; i < someCardCodes.length; i++) {
			someCards[i] = shortCardNameToDecimal(someCardCodes[i]);
		}

		for (int i = 0; i < someCards.length; i++) {
			System.out.println("before:\t\t" + Util.bin51(someCards[i]) + " : " + someCards[i]);
			System.out.println("name: " + convertDecimalToLongName(someCards[i]));
			someCards[i] &= cardMask;
			System.out.println("after:\t\t" + Util.bin51(someCards[i]) + " : " + someCards[i]);

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
			System.out.println("QUADS : " + Util.bin51(quads));

		}
		if (onlyPairs != 0) {
			System.out.println("PAIRS : " + Util.bin51(onlyPairs));
		}
		if (trips != 0) {
			System.out.println("TRIPS : " + Util.bin51(trips));
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
		System.out.println("SUM : " + Util.bin51(sumCards));
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
		return shortCardNamesToDecimals(new String[] { "AH", "5S", "7S", "AC", "2C", "TD", "4S" });
	}




}
