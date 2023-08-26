package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class HandMaker {

	static int[] allc = new int[] { 65537, 65538, 65540, 65544, 65552, 65568, 65600, 65664, 65792, 66048, 66560, 67584,
			69632, 32769, 32770, 32772, 32776, 32784, 32800, 32832, 32896, 33024, 33280, 33792, 34816, 36864, 16385,
			16386, 16388, 16392, 16400, 16416, 16448, 16512, 16640, 16896, 17408, 18432, 20480, 8193, 8194, 8196, 8200,
			8208, 8224, 8256, 8320, 8448, 8704, 9216, 10240, 12288 };

	public static int[] getRandom5CardHand() { // if used a lot this can be optimized, its likely slow with the toList
												// and boxed etc..

		List<Integer> fiftyTwoCards = new ArrayList<Integer>();
		fiftyTwoCards.addAll(Arrays.stream(allc).boxed().toList());

		int[] fiveCards = new int[5];

		Random r = new Random();
		for (int i = 0; i < 5; i++) {
			int randomIndex = r.nextInt(fiftyTwoCards.size());
			Integer randomElement = fiftyTwoCards.get(randomIndex);
			fiftyTwoCards.remove(randomIndex);
			fiveCards[i] = randomElement;
		}

		return fiveCards;
	}

	public static int[] makeLotsOfRandom5CardHands2(int howMany) {
		int[] allCards = new int[howMany * 5];
		Random r = new Random();
		for (int i = 0; i < howMany; i++) {
			int[] fiveCards = getRandom5CardHand();
			for (int j = 0; j < 5; j++) {
				allCards[5 * i + j] = fiveCards[j];
			}
		}
		return allCards;
	}

	public static int[] makeLotsOfRandom5CardHands(int howMany) {
		// List<Integer> fiftyTwoCards = Arrays.asList(allc.clone())

		List<Integer> fiftyTwoCards = Arrays.stream(allc).boxed().toList();

		// make a copy of array to do each hand
		List<Integer> allc2 = new ArrayList<Integer>(fiftyTwoCards);
		// the total array with all the cards will be a 1-D array
		// every chunk of 5 cards will be a different random hand
		int[] allCards = new int[howMany * 5];

		Random r = new Random();
		for (int i = 0; i < howMany; i++) {
			for (int j = 0; j < 5; j++) {
				int randomIndex = r.nextInt(allc2.size());
				Integer randomElement = allc2.get(randomIndex);
				allc2.remove(randomIndex);
				allCards[5 * i + j] = randomElement;
			}
			allc2 = new ArrayList<Integer>(fiftyTwoCards);

		}

		return allCards;
	}

	public static int[] makeLotsOfRandom5CardHandsIntArr(int howMany) {

		// keep a copy of the original array
		// in case we want to use this method on its own out of this class
		int[] fiftyTwoCards = new int[] { 65537, 65538, 65540, 65544, 65552, 65568, 65600, 65664, 65792, 66048, 66560,
				67584, 69632, 32769, 32770, 32772, 32776, 32784, 32800, 32832, 32896, 33024, 33280, 33792, 34816, 36864,
				16385, 16386, 16388, 16392, 16400, 16416, 16448, 16512, 16640, 16896, 17408, 18432, 20480, 8193, 8194,
				8196, 8200, 8208, 8224, 8256, 8320, 8448, 8704, 9216, 10240, 12288 };

		// make a copy of array to do each hand
		int[] allc2 = (int[]) fiftyTwoCards.clone();

		// the total array with all the cards will be a 1-D array
		// every chunk of 5 cards will be a different random hand
		int[] allCards = new int[howMany * 5];

		// random object
		Random r = new Random();

		// random number
		int ran = 0;

		// x will be used to get the cards from the array and make sure
		// we do not pick the same card twice
		int x = 0;
		for (int i = 0; i < howMany; i++) {
			for (int j = 0; j < 5; j++) {
				// while x==0: this ensures we dont pick the same exact card again
				// since each card in deck will be set to zero after we pick it,
				// x would remain zero if it chose it again, and the loop will continue
				// while x==0 until it chooses a card we have not picked yet
				while (x == 0) {
					ran = r.nextInt(52);
					x = allc2[ran];
				}

				allCards[5 * i + j] = x;

				// set the newly picked card to 0, so we cant choose it a again next time
				allc2[ran] = 0;
				x = 0;
			}

			// get a fresh copy of the cards ready for the next random 5 card hand
			// without all the 0s we just created from choosing cards in the previous deck
			allc2 = (int[]) fiftyTwoCards.clone();
		}

		return allCards;
	}

	public static int[] makeLotsOfRandom7CardHands(int howMany) {

		// keep a copy of the original array
		// in case we want to use this method on its own out of this class
		int[] fiftyTwoCards = new int[] { 65537, 65538, 65540, 65544, 65552, 65568, 65600, 65664, 65792, 66048, 66560,
				67584, 69632, 32769, 32770, 32772, 32776, 32784, 32800, 32832, 32896, 33024, 33280, 33792, 34816, 36864,
				16385, 16386, 16388, 16392, 16400, 16416, 16448, 16512, 16640, 16896, 17408, 18432, 20480, 8193, 8194,
				8196, 8200, 8208, 8224, 8256, 8320, 8448, 8704, 9216, 10240, 12288 };

		// make a copy of array to do each hand
		int[] allc2 = (int[]) fiftyTwoCards.clone();

		// the total array with all the cards will be a 1-D array
		// every chunk of 7 cards will be a different random hand
		int[] allCards = new int[howMany * 7];

		// random object
		Random r = new Random();

		// random number
		int ran = 0;

		// x will be used to get the cards from the array and make sure
		// we do not pick the same card twice
		int x = 0;
		for (int i = 0; i < howMany; i++) {
			for (int j = 0; j < 7; j++) {
				// while x==0: this ensures we dont pick the same exact card again
				// since each card in deck will be set to zero after we pick it,
				// x would remain zero if it chose it again, and the loop will continue
				// while x==0 until it chooses a card we have not picked yet
				while (x == 0) {
					ran = r.nextInt((51 - 1) + 1) + 1;
					x = allc2[ran];
				}

				allCards[7 * i + j] = x;

				// set the newly picked card to 0, so we cant choose it a again next time
				allc2[ran] = 0;
				x = 0;
			}

			// get a fresh copy of the cards ready for the next random 7 card hand
			// without all the 0s we just created from choosing cards in the previous deck
			allc2 = (int[]) fiftyTwoCards.clone();
		}

		return allCards;
	}

	// These next methods are somewhat random hand generators.
	// They do not make completely random hands, but they are good enough for some
	// testing I needed to do...

	// there is an error in this method, it makes some cards as 0, I did it quick
	// and dirty. It needs re-doing
	public static int[] makeHighCardHand() {
		// System.out.println("Making high card hand");
		int[] allc2 = allc.clone();
		Random r = new Random();
		int ra, rb, rc, rd, re;
		int ran = 0, x = 0, a = 0, b = 0, c = 0, d = 0, e = 0;

		while (x == 0) {
			ran = r.nextInt((12 - 0) + 1) + 0;
			x = allc2[ran];
		}
		ra = x;
		allc2[ran] = 0;
		allc2[ran + 13] = 0;
		allc2[ran + 26] = 0;
		x = 0;

		while (x == 0) {
			ran = r.nextInt((12 - 0) + 1) + 0;
			if (ran > 0 && allc2[ran - 1] != 0 & ran < 12 && allc2[ran + 1] != 0)
				x = allc2[ran];

		}
		b = x;
		rb = ran;
		allc2[ran] = 0;
		allc2[ran + 13] = 0;
		allc2[ran + 26] = 0;
		x = 0;

		while (x == 0) {
			ran = r.nextInt((25 - 13) + 1) + 13;
			x = allc2[ran];
		}
		c = x;
		rc = ran;
		allc2[ran] = 0;
		allc2[ran + 13] = 0;
		allc2[ran + 26] = 0;
		x = 0;

		while (x == 0) {
			ran = r.nextInt((38 - 25) + 1) + 25;
			x = allc2[ran];
		}
		d = x;
		rd = ran;
		allc2[ran] = 0;
		allc2[ran + 13] = 0;
		x = 0;

		while (x == 0) {
			ran = r.nextInt((38 - 25) + 1) + 25;
			x = allc2[ran];
		}
		e = x;
		re = ran;

		x = 0;
		if (a == b || a == c || a == d || a == e) {
			while (x == 0) {
				ran = r.nextInt((51 - 1) + 1) + 1;
				x = allc2[ran];
			}
			allc2[ran] = 0;
			a = x;
		}

		x = 0;
		if (b == a || b == c || b == d || b == e) {
			while (x == 0) {
				ran = r.nextInt((51 - 1) + 1) + 1;
				x = allc2[ran];
			}
			allc2[ran] = 0;
			b = x;
		}

		x = 0;
		if (c == b || c == a || c == d || c == e) {
			while (x == 0) {
				ran = r.nextInt((51 - 1) + 1) + 1;
				x = allc2[ran];
			}
			allc2[ran] = 0;
			c = x;
		}
		x = 0;
		if (d == b || d == c || d == a || d == e) {
			while (x == 0) {
				ran = r.nextInt((51 - 1) + 1) + 1;
				x = allc2[ran];
			}
			allc2[ran] = 0;
			d = x;
		}
		x = 0;
		if (e == b || e == c || e == d || e == a) {
			while (x == 0) {
				ran = r.nextInt((51 - 1) + 1) + 1;
				x = allc2[ran];
			}
			allc2[ran] = 0;
			e = x;
		}

		int[] fhigh = { ra, rb, rc, rd, re };
		sortHighDown(fhigh);
		int scounter = 0;
		for (int i = 0; i < fhigh.length - 1; i++) {
			if (fhigh[i] == fhigh[i + 1] + 1) {
				scounter++;
				// System.out.println("corrected straight");
			}

		}

		if (scounter == 4) {
			while ((re = re - 1) != rd && re != rc && re != rb && re != ra) {
				if (re == 0)
					re = rd + 2;
				else
					e = allc2[re];
			}
			;
		}

		// TODO sometimes a number gets set to 0, need to fix
		return new int[] { a, b, c, d, e };

	}

	public static int[] makePairHand() {
		// System.out.println("Making Pair hand");
		int[] allc2 = allc.clone();
		Random r = new Random();
		int ran = 0, x = 0, a = 0, b = 0, c = 0, d = 0, e = 0;

		while (x == 0) {
			ran = r.nextInt((12 - 0) + 1) + 0;
			x = allc2[ran];
		}
		a = x;
		allc2[ran] = 0;
		allc2[ran + 13] = 0;
		allc2[ran + 26] = 0;
		x = 0;

		while (x == 0) {
			ran = r.nextInt((12 - 0) + 1) + 0;
			x = allc2[ran];
		}
		b = x;
		allc2[ran] = 0;
		allc2[ran + 13] = 0;
		allc2[ran + 26] = 0;
		x = 0;

		while (x == 0) {
			ran = r.nextInt((25 - 13) + 1) + 13;
			x = allc2[ran];
		}
		c = x;
		allc2[ran] = 0;
		allc2[ran + 13] = 0;
		allc2[ran + 26] = 0;
		x = 0;

		while (x == 0) {
			ran = r.nextInt((38 - 25) + 1) + 25;
			x = allc2[ran];
		}
		d = x;
		allc2[ran] = 0;
		e = allc2[ran + 13];

		return new int[] { a, b, c, d, e };
	}

	public static int[] makeTwoPairHand() {
		// System.out.println("Making Two Pair hand");
		int[] allc2 = allc.clone();
		Random r = new Random();
		int ran = 0, x = 0, a = 0, b = 0, c = 0, d = 0, e = 0;

		while (x == 0) {
			ran = r.nextInt((12 - 0) + 1) + 0;
			x = allc2[ran];
		}
		a = x;
		allc2[ran] = 0;
		allc2[ran + 13] = 0;
		allc2[ran + 26] = 0;
		allc2[ran + 39] = 0;
		x = 0;

		while (x == 0) {
			ran = r.nextInt((12 - 0) + 1) + 0;
			x = allc2[ran];
		}
		b = x;
		allc2[ran] = 0;
		x = 0;

		e = allc2[ran + 13];
		allc2[ran + 13] = 0;

		while (x == 0) {
			ran = r.nextInt((25 - 13) + 1) + 13;
			x = allc2[ran];
		}
		c = x;

		d = allc2[ran + 13];

		return new int[] { a, b, c, d, e };
	}

	public static int[] makeTripHand() {
		// System.out.println("Making Trip hand");
		int[] allc2 = allc.clone();
		Random r = new Random();
		int ran = 0, x = 0, a = 0, b = 0, c = 0, d = 0, e = 0;

		while (x == 0) {
			ran = r.nextInt((12 - 0) + 1) + 0;
			x = allc2[ran];
		}
		a = x;
		allc2[ran] = 0;
		allc2[ran + 13] = 0;
		allc2[ran + 26] = 0;
		x = 0;

		while (x == 0) {
			ran = r.nextInt((12 - 0) + 1) + 0;
			x = allc2[ran];
		}
		b = x;
		allc2[ran] = 0;
		allc2[ran + 13] = 0;
		allc2[ran + 26] = 0;
		x = 0;

		while (x == 0) {
			ran = r.nextInt((25 - 13) + 1) + 13;
			x = allc2[ran];
		}
		c = x;

		d = allc2[ran + 26];
		e = allc2[ran + 13];

		return new int[] { a, b, c, d, e };
	}

	public static int[] makeStraightHand() {
		// System.out.println("Making Straight hand");
		int[] allc2 = allc.clone();
		Random r = new Random();
		int ran = 0, x = 0, a = 0, b = 0, c = 0, d = 0, e = 0;

		while (x == 0) {
			ran = r.nextInt((8 - 1) + 1) + 1;
			x = allc2[ran];
		}
		a = x;

		b = allc2[ran + 1];
		c = allc2[ran + 15];
		d = allc2[ran + 29];
		e = allc2[ran + 43];

		return new int[] { a, b, c, d, e };
	}

	public static int[] makeFlushHand() {
		// System.out.println("Making Flush hand");
		int[] allc2 = allc.clone();
		Random r = new Random();
		int ran = 0, x = 0, a = 0, b = 0, c = 0, d = 0, e = 0;

		int ranSuit = ran = r.nextInt((4 - 1) + 1) + 1;

		int max = (13 * ranSuit) - 1, min = max - 12;

		while (x == 0) {
			ran = r.nextInt((max - min) + 1) + min;
			x = allc2[ran];
		}
		a = x;
		allc2[ran] = 0;
		x = 0;
		while (x == 0) {
			ran = r.nextInt((max - min) + 1) + min;
			x = allc2[ran];
		}
		b = x;
		allc2[ran] = 0;
		x = 0;
		while (x == 0) {
			ran = r.nextInt((max - min) + 1) + min;
			x = allc2[ran];
		}
		c = x;
		allc2[ran] = 0;
		x = 0;
		while (x == 0) {
			ran = r.nextInt((max - min) + 1) + min;
			x = allc2[ran];
		}
		d = x;
		allc2[ran] = 0;
		x = 0;
		while (x == 0) {
			ran = (r.nextInt((max - min) + 1) + min);
			if (ran > 0 && allc2[ran - 1] != 0 & ran < max && allc2[ran + 1] != 0)
				x = allc2[ran];
		}
		e = x;

		return new int[] { a, b, c, d, e };
	}

	public static int[] makeFullHouseHand() {
		// System.out.println("Making Fullhouse hand");
		int[] allc2 = allc.clone();
		Random r = new Random();
		int ran = 0, x = 0, a = 0, b = 0, c = 0, d = 0, e = 0;

		while (x == 0) {
			ran = r.nextInt((12 - 0) + 1) + 0;
			x = allc2[ran];
		}
		a = x;
		b = allc2[ran + 13];
		allc2[ran] = 0;
		allc2[ran + 13] = 0;
		allc2[ran + 26] = 0;
		x = 0;

		while (x == 0) {
			ran = r.nextInt((25 - 13) + 1) + 13;
			x = allc2[ran];
		}
		c = x;

		d = allc2[ran + 26];
		e = allc2[ran + 13];

		return new int[] { a, b, c, d, e };
	}

	public static int[] makeQuadsHand() {
		// System.out.println("Making Quads hand");
		int[] allc2 = allc.clone();
		Random r = new Random();
		int ran = 0, x = 0, a = 0, b = 0, c = 0, d = 0, e = 0;

		while (x == 0) {
			ran = r.nextInt((12 - 0) + 1) + 0;
			x = allc2[ran];
		}
		b = x;
		x = 0;
		allc2[ran] = 0;
		c = allc2[ran + 39];
		allc2[ran + 39] = 0;
		d = allc2[ran + 26];
		allc2[ran + 26] = 0;
		e = allc2[ran + 13];
		allc2[ran + 13] = 0;

		while (x == 0) {
			ran = r.nextInt((51 - 0) + 1) + 0;
			x = allc2[ran];
		}
		a = x;

		return new int[] { a, b, c, d, e };
	}

	public static int[] makeStraightFlushHand() {
		// System.out.println("Making straightFlush hand");
		int[] allc2 = allc.clone();
		Random r = new Random();
		int ran = 0, x = 0, a = 0, b = 0, c = 0, d = 0, e = 0;

		int ranSuit = ran = r.nextInt((4 - 1) + 1) + 1;

		int max = (13 * ranSuit) - 1 - 4, min = max - 8;

		while (x == 0) {
			ran = r.nextInt((max - min) + 1) + min;
			x = allc2[ran];
		}
		a = x;

		b = allc2[ran + 1];
		c = allc2[ran + 2];
		d = allc2[ran + 3];
		e = allc2[ran + 4];

		return new int[] { a, b, c, d, e };
	}

	public static void mixUpCards(int[] c) {
		int N = c.length;
		for (int i = 0; i < N; i++) {
			int r = i + (int) (Math.random() * (N - i));
			int t = c[r];
			c[r] = c[i];
			c[i] = t;
		}
	}

	public static void sortHighDown(int[] n) {
		int temp = 0;
		int size = n.length - 1;
		for (int i = 0; i < size; i++)
			for (int j = 0; j < (size - i); j++)
				if ((n[j] & 8191) < (n[j + 1] & 8191)) {
					temp = n[j];
					n[j] = n[j + 1];
					n[j + 1] = temp;
				}
	}

}
