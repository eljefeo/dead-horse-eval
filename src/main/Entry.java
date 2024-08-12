package main;

public class Entry {

	public static void main(String[] args) throws java.lang.Exception {

		initializeHandMaker();
		//doTest1();
		doTest2();


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
		//EvalTestPlayground.randomizerSpeedTest5Card(100000000); // Did 130000000
		// hands in 0.6159191 seconds 211 million hands a second
		 //System.out.println(EvalTestPlayground.humanDecodeEval("9D", "9C", "9H", "6S","9S"));

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
		util5.humanEncodeShortAndDecodeLongHand(new String[] {"8H", "9H", "TH", "7H", "JH"});

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

		for(int i = 0; i < util.handNames.length; i++) {
			testHandMaker(i);
		}
	}

	private static void doTest2() throws Exception {

		System.out.println("making all 52 cards for 5 card poker:");
		util5.makeAllDecimalNumsFromScratch();
		System.out.println();

		System.out.println("making all 52 cards for 7 card poker:");
		util7.makeAllDecimalNumsFromScratch();
		System.out.println();

		String test1 = "8D";
		int humanShortDecimal5 = util5.convertHumanShortNameToDecimal(test1);
		System.out.println("conver human short to decimal 5: " + test1 + " : " + humanShortDecimal5);
		System.out.println("human short binary : " + util.bin32(humanShortDecimal5));
		System.out.println("converting decimal back to short name 5: " + util5.convertDecimalToShortName(humanShortDecimal5));

		long humanShortDecimal7 = util7.convertHumanShortNameToDecimal(test1);
		System.out.println("conver human short to decimal 7: " + test1 + " : " + humanShortDecimal7);
		System.out.println("human short binary : " + util.bin64(humanShortDecimal7));
		System.out.println("converting decimal back to short name 7: " + util7.convertDecimalToShortName(humanShortDecimal7));


		int[] allCardNums = util5.allCardNums;
		String[] allCardNames = util.allCardNames;
		String[] hand = new String[]{"7H", "4S", "2D", "4D", "KC"};
		if(allCardNames.length != allCardNums.length){
			throw new Error("Card numbers and names are not the same length!");

		}

		System.out.println("doing card names and nums: ");
		for(String s : hand){
			System.out.println(s + " " + util5.cardMap.get(s) );//+ " : " + util5.de);
		}

		//for (int i = 0; i < allCardNums.length; i++) {
		//	System.out.println(allCardNames[i] + " : " + allCardNums[i]);
		//}

	}

	public static void testHandMaker(int type) throws Exception {
		Integer[] cards = switch (type) {
            case 0 -> HandMaker.getRandomHighCardHand();
            case 1 -> HandMaker.getRandomPairHand();
            case 2 -> HandMaker.getRandomTwoPairHand();
            case 3 -> HandMaker.getRandomTripsHand();
            case 4 -> HandMaker.getRandomStraightHand();
            case 5 -> HandMaker.getRandomFlushHand();
            case 6 -> HandMaker.getRandomFullHouseHand();
            case 7 -> HandMaker.getRandomQuadsHand();
            case 8 -> HandMaker.getRandomStraightFlushHand();
            default -> throw new Error("Please enter a correct hand type from 0-" + util.handNames.length);
        };


        //System.out.println(hch[0] + " = " + util5.getCardName5(hch[0]), " + hch[1] + ", " + hch[2] + ", " + hch[3] + ", " + hch[4]);
		for (Integer integer : cards) {
			System.out.print(integer + " = " + util5.getCardName5(integer) + ", ");
		}
		System.out.println("\n\n");
		int cardsRes = util5.humanEncodeEval(util5.getCardName5(cards[0]), util5.getCardName5(cards[1]), util5.getCardName5(cards[2]), util5.getCardName5(cards[3]), util5.getCardName5(cards[4]));
		String handDescription1 = util5.decode5CardHand(cardsRes);
		System.out.println("Description: " + handDescription1 + " :: " + "int res : " + cardsRes);
	}
}


