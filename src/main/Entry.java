package main;

public class Entry {

	public static void main(String[] args) throws java.lang.Exception {

		initializeHandMaker();
		doTest1();

		// EvalTestPlayground.showRandomizerDiagnostics(1);
		// EvalTestPlayground.randomizerSpeedTest5CardDiagnostics(50);
		// EvalTestPlayground.howLongUntilYouGetThisKindOfHands(2);
		// EvalTestPlayground.handCompareTest(100);

		// EvalTestPlayground.randomizerSpeedTest7Card(10000000);
		// EvalTestPlayground.randomizerSpeedTest7nCard(10000000);

		// EvalTestPlayground.randomizerSpeedTest7Cardr(10000000);

		// EvalTestPlayground.randomizerSpeedTest7Card(30000000);

		 //EvalTestPlayground.randomizerSpeedTest5Card(25000000); // speed average :
		// 25000000 hands in 0.121995199 seconds = 204Mhps
		// EvalTestPlayground.randomizerSpeedTest5Card(130000000); // Did 130000000
		// hands in 0.6159191 seconds 211 million hands a second
		 //System.out.println(EvalTestPlayground.humanDecodeEval("9d", "9c", "9h", "6s","9s"));

		//util5.getRandomThisType5CardHand(4);
		//util7.getRandomThisType7CardHand(4);

		 //EvalTestPlayground.testEveryHand5();
		 //EvalTestPlayground.testEval5AndNotes();

		 //EvalTestPlayground.testStatisticsManyHand5(10000000);
		//DeadHorse7.findDuplicates();
		///DeadHorse7.findFlushes();
		//DeadHorse7.findStraights();
		//DeadHorse7.testeval7();
		//DeadHorse7.eval7Checke();


		//DeadHorse7.eval7Checke2();
		//util7.testEveryHand7n();
		//util5.humanEncodeEval("2S", "8S", "6S", "5S", "3S" );


		//util5.decode5CardHand(403701764);
		//util5.humanEncodeShortAndDecodeLongHand(new String[] {"8H", "9D", "9C", "9H", "8D"});

		// EvalTestPlayground.testEveryHand7();

		// EvalTestPlayground.testEveryHand7n();

		// EvalTestPlayground.test7();
		// EvalTestPlayground.doSuitTest();

		// EvalTestPlayground.makeNewnums();





/*
Did all 133784560 hands in 0.9339746 seconds
143.24218238911422 million hands a second

PASS - All 23294460 High Card hands are accounted for 17.411919581751437%
PASS - All 58627800 Pair hands are accounted for 43.822545740704314%
PASS - All 31433400 Two Pair hands are accounted for 23.495536405695844%
PASS - All 6461620 3 of a kind hands are accounted for 4.8298697547758875%
PASS - All 6180020 Straight hands are accounted for 4.619382087140698%
PASS - All 4047644 Flush hands are accounted for 3.0254941227896555%
PASS - All 3473184 Full House hands are accounted for 2.5961022706955124%
PASS - All 224848 4 of a kind hands are accounted for 0.16806722689075632%
PASS - All 41584 Straight Flush hands are accounted for 0.03108280955590092%
Total Count : 133784560
		 */

	}

	private static void initializeHandMaker(){
		HandMaker.prepAllHands();
	}

	public static void doTest1() throws Exception {

		for(int i = 0; i < util.handNames.length; i++){
			testHandMaker(i);
		}

		/*Integer[] hch = HandMaker.getRandomHighCardHand();
		//System.out.println(hch[0] + " = " + util5.getCardName5(hch[0]), " + hch[1] + ", " + hch[2] + ", " + hch[3] + ", " + hch[4]);
		for (Integer integer : hch) {
			System.out.print(integer + " = " + util5.getCardName5(integer) + ", ");
		}
		System.out.println();
		int resHighCard = util5.humanEncodeEval(util5.getCardName5(hch[0]), util5.getCardName5(hch[1]), util5.getCardName5(hch[2]), util5.getCardName5(hch[3]), util5.getCardName5(hch[4]));
		String handDescription = util5.decode5CardHand(resHighCard);
		System.out.println("high card description: " + handDescription + " :: " + "int res : " + resHighCard);
		//00000100000000000000000000101110
		//00000100000000010001010000000010

		Integer[] pair = HandMaker.getRandomPairHand();
		//System.out.println(hch[0] + " = " + util5.getCardName5(hch[0]), " + hch[1] + ", " + hch[2] + ", " + hch[3] + ", " + hch[4]);
		for (Integer integer : pair) {
			System.out.print(integer + " = " + util5.getCardName5(integer) + ", ");
		}
		System.out.println("\n\n");
		int pairRes = util5.humanEncodeEval(util5.getCardName5(pair[0]), util5.getCardName5(pair[1]), util5.getCardName5(pair[2]), util5.getCardName5(pair[3]), util5.getCardName5(pair[4]));
		String handDescription1 = util5.decode5CardHand(pairRes);
		System.out.println("pair description: " + handDescription1 + " :: " + "int res : " + pairRes);
*/
	}

	public static void testHandMaker(int type) throws Exception {
		Integer[] cards = new Integer[0];
		switch (type){
			case 0:
				cards = HandMaker.getRandomHighCardHand();
				break;
			case 1:
				cards = HandMaker.getRandomPairHand();
				break;
			case 2:
				cards = HandMaker.getRandomTwoPairHand();
				break;
			case 3:
				cards = HandMaker.getRandomTripsHand();
				break;
			case 4:
				cards = HandMaker.getRandomStraightHand();
				break;
			case 5:
				cards = HandMaker.getRandomFlushHand();
				break;
			case 6:
				cards = HandMaker.getRandomFullHouseHand();
				break;
			case 7:
				cards = HandMaker.getRandomQuadsHand();
				break;
			case 8:
				cards = HandMaker.getRandomStraightFlushHand();
				break;
		}



		//System.out.println(hch[0] + " = " + util5.getCardName5(hch[0]), " + hch[1] + ", " + hch[2] + ", " + hch[3] + ", " + hch[4]);
		for (Integer integer : cards) {
			System.out.print(integer + " = " + util5.getCardName5(integer) + ", ");
		}
		System.out.println("\n\n");
		int cardsRes = util5.humanEncodeEval(util5.getCardName5(cards[0]), util5.getCardName5(cards[1]), util5.getCardName5(cards[2]), util5.getCardName5(cards[3]), util5.getCardName5(cards[4]));
		String handDescription1 = util5.decode5CardHand(cardsRes);
		System.out.println("pair description: " + handDescription1 + " :: " + "int res : " + cardsRes);
	}
}


