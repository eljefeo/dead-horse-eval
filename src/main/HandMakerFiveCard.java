package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class HandMakerFiveCard {

	/*static int[] allc = new int[] { 65537, 65538, 65540, 65544, 65552, 65568, 65600, 65664, 65792, 66048, 66560, 67584,
			69632, 32769, 32770, 32772, 32776, 32784, 32800, 32832, 32896, 33024, 33280, 33792, 34816, 36864, 16385,
			16386, 16388, 16392, 16400, 16416, 16448, 16512, 16640, 16896, 17408, 18432, 20480, 8193, 8194, 8196, 8200,
			8208, 8224, 8256, 8320, 8448, 8704, 9216, 10240, 12288 };*/

	static final Random rand = new Random();

	private static final List<Integer[]> allHighCardHands = new ArrayList<>();
	private static final List<Integer[]> allPairHands = new ArrayList<>();
	private static final List<Integer[]> allTwoPairHands = new ArrayList<>();
	private static final List<Integer[]> allTripHands = new ArrayList<>();
	private static final List<Integer[]> allStraightHands = new ArrayList<>();
	private static final List<Integer[]> allFlushHands = new ArrayList<>();
	private static final List<Integer[]> allFullHouseHands = new ArrayList<>();
	private static final List<Integer[]> allQuadHands = new ArrayList<>();
	private static final List<Integer[]> allStraightFlushHands = new ArrayList<>();
	private static final List<List<Integer[]>> allHandsOrdered = new ArrayList<>();
	private static final List<Integer[]> allHands = new ArrayList<>();
	static {
		allHandsOrdered.add(allHighCardHands);
		allHandsOrdered.add(allPairHands);
		allHandsOrdered.add(allTwoPairHands);
		allHandsOrdered.add(allTripHands);
		allHandsOrdered.add(allStraightHands);
		allHandsOrdered.add(allFlushHands);
		allHandsOrdered.add(allFullHouseHands);
		allHandsOrdered.add(allQuadHands);
		allHandsOrdered.add(allStraightFlushHands);

		//This will create and evaluate every possible 5 card hand. Then add the hand to allHandsOrdered in the correct spot, and add it to allHands
		int[] allFiveCardHands = EvalTestPlayground.createAllFiveCardHands();
		for(int i=0;i<allFiveCardHands.length;i+=5){

			Integer[] aHand = new Integer[] {allFiveCardHands[i],allFiveCardHands[i+1],
					allFiveCardHands[i+2],allFiveCardHands[i+3],allFiveCardHands[i+4]};

			int res = DeadHorse.eval5(aHand)>>26;

			allHandsOrdered.get(res).add(aHand);

			allHands.add(aHand);
		}
	}


	/*public static int[] getRandom5CardHand() { // if used a lot this can be optimized, its likely slow with the toList
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
	}*/

	/*public static int[] makeLotsOfRandom5CardHands2(int howMany) {
		int[] allCards = new int[howMany * 5];
		Random r = new Random();
		for (int i = 0; i < howMany; i++) {
			int[] fiveCards = getRandom5CardHand();
			for (int j = 0; j < 5; j++) {
				allCards[5 * i + j] = fiveCards[j];
			}
		}
		return allCards;
	}*/

	public static int[] makeLotsOfRandom5CardHandsSlow(int howMany) { //this is so dang slow. But another way to do it I suppose...
		// List<Integer> fiftyTwoCards = Arrays.asList(allc.clone())

		List<Integer> fiftyTwoCards = Arrays.stream(util5.all52CardsDecimal).boxed().toList();

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

	public static List<Integer[]> makeThisManyRandom5CardHands(int howMany) {
		List<Integer[]> hands = new ArrayList<>();
		for(int i = 0; i < howMany; i++){
			hands.add(allHands.get(rand.nextInt(allHands.size())));
		}
		return hands;
	}

	public static int[] makeThisManyRandom5CardHandsOld(int howMany) {

		// keep a copy of the original array
		// in case we want to use this method on its own out of this class
		/*int[] fiftyTwoCards = new int[] { 65537, 65538, 65540, 65544, 65552, 65568, 65600, 65664, 65792, 66048, 66560,
				67584, 69632, 32769, 32770, 32772, 32776, 32784, 32800, 32832, 32896, 33024, 33280, 33792, 34816, 36864,
				16385, 16386, 16388, 16392, 16400, 16416, 16448, 16512, 16640, 16896, 17408, 18432, 20480, 8193, 8194,
				8196, 8200, 8208, 8224, 8256, 8320, 8448, 8704, 9216, 10240, 12288 };*/
		int[] fiftyTwoCards = util5.all52CardsDecimal;

		// make a copy of array to do each hand
		int[] allc2 = fiftyTwoCards.clone();

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
					ran = r.nextInt(52 );
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



	/*public static long[] getRandom7CardHandSlowDontUse() { // if used a lot this can be optimized, its likely slow with the toList
		// and boxed etc..

		List<Long> fiftyTwoCards = new ArrayList<Long>();
		fiftyTwoCards.addAll(Arrays.stream(util7.all52Cards7).boxed().toList());

		long[] sevenCards = new long[7];

		Random r = new Random();
		for (int i = 0; i < 7; i++) {
			int randomIndex = r.nextInt(fiftyTwoCards.size());
			Long randomElement = fiftyTwoCards.get(randomIndex);
			fiftyTwoCards.remove(randomIndex);
			sevenCards[i] = randomElement;
		}

		return sevenCards;
	}*/


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

	/*public static void prepAllHands() { //done in static block now

		int[] allFiveCardHands = EvalTestPlayground.createAllFiveCardHands();
		for(int i=0;i<allFiveCardHands.length;i+=5){
			int res = DeadHorse.eval5(allFiveCardHands[i],allFiveCardHands[i+1],
					allFiveCardHands[i+2],allFiveCardHands[i+3],allFiveCardHands[i+4])>>26;
			allHandsOrdered.get(res).add(new Integer[] {allFiveCardHands[i],allFiveCardHands[i+1],
					allFiveCardHands[i+2],allFiveCardHands[i+3],allFiveCardHands[i+4]});
		}


	}*/

	public static List<List<Integer[]>> getAllHandsOrdered(){
		return allHandsOrdered;
	}



	public static String[] getRandomHandFromDescription(String handDescription) throws Exception {

		//int handType;
		//if(handDescription.toUpperCase().equals(util.ROYAL_FLUSH.toUpperCase())){
		//	handType = 9; //maybe can hijack some functions and use 9 as royal flush indicator
		//} else {
			int handType = util.handDescriptionToType(handDescription);
			Integer[] cardsInt = getRandomCertainTypeHand(handType);
		//}

		return util5.decimalsToShortCardNames(cardsInt);
	}



	public static Integer[] getRandomHand(){

		return allHands.get(rand.nextInt(allHands.size()));
	}
	public static Integer[] getRandomHighCardHand(){
		return getRandomCertainTypeHand(0);
	}
	public static Integer[] getRandomPairHand(){
		return getRandomCertainTypeHand(1);
	}
	public static Integer[] getRandomTwoPairHand(){
		return getRandomCertainTypeHand(2);
	}
	public static Integer[] getRandomTripsHand(){
		return getRandomCertainTypeHand(3);
	}
	public static Integer[] getRandomStraightHand(){
		return getRandomCertainTypeHand(4);
	}
	public static Integer[] getRandomFlushHand(){
		return getRandomCertainTypeHand(5);
	}
	public static Integer[] getRandomFullHouseHand(){
		return getRandomCertainTypeHand(6);
	}
	public static Integer[] getRandomQuadsHand(){
		return getRandomCertainTypeHand(7);
	}
	public static Integer[] getRandomStraightFlushHand(){
		return getRandomCertainTypeHand(8);
	}

	public static Integer[] getRandomCertainTypeHand(int type){
		List<Integer[]> handList = allHandsOrdered.get(type);
		int ni = rand.nextInt(handList.size());
		// System.out.println("getRandomCertainTypeHand : " + util.handNames[type] + " random hand : " + ni + ", " + highCardHands.size());
		return handList.get(ni);
	}




	//Just testing if this could work, make sure references are good in the list of lists
	public static void testGetRandomTestSomeHand() throws Exception {
		Integer[] cards = allQuadHands.get(0);
		String[] cardsSt = util5.decimalsToShortCardNames(cards);
		System.out.println("Random hand : " + Arrays.toString(cardsSt));
	}

}
