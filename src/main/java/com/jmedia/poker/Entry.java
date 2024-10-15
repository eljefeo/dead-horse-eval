package com.jmedia.poker;

public class Entry {

	public static void main(String[] args) throws java.lang.Exception {



		//initializeHandMaker();
		//doTest1();
		//doTest2();

		//testCardConversion();
		DeadHorse5Examples.doTestExamples();
		//DeadHorse7Examples.doTestExamples();
		//util7.testEveryHand7n();
		//EvalTestPlayground.randomizerSpeedTest7Card(10000000);

		//util5.decode5CardHand(403701764);
		//util5.humanEncodeShortAndDecodeLongHand(new String[] {"2H", "9H", "TC", "7H", "JS"});
		//util5.humanEncodeShortAndDecodeLongHand(new String[] {"8H", "8S", "TH", "TD", "8C"});
		//makeAllCards();


		// EvalTestPlayground.showRandomizerDiagnostics(1);
		// EvalTestPlayground.randomizerSpeedTest5CardDiagnostics(50);
		// EvalTestPlayground.howLongUntilYouGetThisKindOfHands(2);
		 //EvalTestPlayground.handCompareTest(1000000);

		//EvalTestPlayground.randomizerSpeedTest7Card(10000000);
		// EvalTestPlayground.randomizerSpeedTest7nCard(10000000);

		// EvalTestPlayground.randomizerSpeedTest7Cardr(10000000);

		// EvalTestPlayground.randomizerSpeedTest7Card(30000000);

		 //EvalTestPlayground.randomizerSpeedTest5Card(50000000); // speed average :
		//EvalTestPlayground.randomizerSpeedTest5Card_2(50000000); // speed average :
		// Did 50000000 hands in 0.2413853 seconds = 207 million hands a second
		//EvalTestPlayground.randomizerSpeedTest5Card(100000000); //
		// Did 100000000 hands in 2.2256084 seconds = 44 million hands a second
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



	//private static void initializeHandMaker(){
	//	//HandMakerFiveCard.prepAllHands();
	//}

	public static void doTest1() throws Exception {

		for(int i = 0; i < Util.handNames.length; i++) {
			testHandMaker(i);
		}
	}

	private static void makeAllCards() throws Exception {
		System.out.println("making all 52 cards for 5 card poker: ");
		Util5.makeAllDecimalNumsFromScratch();
		System.out.println();

		System.out.println("making all 52 cards for 7 card poker:");
		Util7.makeAllDecimalNumsFromScratch();
		System.out.println();
	}

	private static void testCardConversion() throws Exception {
		String test1 = "5H";
		//int humanShortDecimal5 = util5.convertHumanShortNameToDecimal(test1);
		int humanShortDecimal5 = Util5.shortCardNameToDecimal(test1);
		System.out.println("convert human short to decimal 5: " + test1 + " : " + humanShortDecimal5);
		System.out.println("human short binary : " + Util.bin32(humanShortDecimal5));
		System.out.println("Decimal to long name : " + Util5.decimalToLongCardName(humanShortDecimal5));
		System.out.println("converting decimal back to short name 5: " + Util5.decimalToShortCardName(humanShortDecimal5));

		long humanShortDecimal7 = Util7.shortCardNameToDecimal(test1);
		System.out.println("conver human short to decimal 7: " + test1 + " : " + humanShortDecimal7);
		System.out.println("human short binary : " + Util.bin64(humanShortDecimal7));
		System.out.println("Decimal to long name : " + Util7.convertHumanShortNameToLongName(test1));
		System.out.println("converting decimal back to short name 7: " + Util7.convertDecimalToShortCardName(humanShortDecimal7));

	}

	private static void doTest2() throws Exception {





		int[] allCardNums = Util5.all52CardsDecimal;
		String[] allCardNames = Util.allCardNames;
		String[] hand = new String[]{"7H", "4S", "2D", "5D", "KC"};
		if(allCardNames.length != allCardNums.length){
			throw new Error("Card numbers and names are not the same length!");

		}

		System.out.println("doing card names and nums: ");
		for(String s : hand){
			System.out.println(s + " " + Util5.shortCardNameToDecimal(s));//util5.cardMap.get(s) );//+ " : " + util5.de);
		}

		//for (int i = 0; i < allCardNums.length; i++) {
		//	System.out.println(allCardNames[i] + " : " + allCardNums[i]);
		//}

	}

	public static void testHandMaker(int type) throws Exception {
		Integer[] cards = switch (type) {
            case 0 -> HandMakerFiveCard.getRandomHighCardHand();
            case 1 -> HandMakerFiveCard.getRandomPairHand();
            case 2 -> HandMakerFiveCard.getRandomTwoPairHand();
            case 3 -> HandMakerFiveCard.getRandomTripsHand();
            case 4 -> HandMakerFiveCard.getRandomStraightHand();
            case 5 -> HandMakerFiveCard.getRandomFlushHand();
            case 6 -> HandMakerFiveCard.getRandomFullHouseHand();
            case 7 -> HandMakerFiveCard.getRandomQuadsHand();
            case 8 -> HandMakerFiveCard.getRandomStraightFlushHand();
            default -> throw new Error("Please enter a correct hand type from 0-" + Util.handNames.length);
        };


        //System.out.println(hch[0] + " = " + util5.getCardName5(hch[0]), " + hch[1] + ", " + hch[2] + ", " + hch[3] + ", " + hch[4]);
		for (Integer integer : cards) {
			System.out.print(integer + " = " + Util5.getCardName5(integer) + ", ");
		}
		System.out.println("\n\n");
		int cardsRes = Util5.evalShortCards(Util5.getCardName5(cards[0]), Util5.getCardName5(cards[1]), Util5.getCardName5(cards[2]), Util5.getCardName5(cards[3]), Util5.getCardName5(cards[4]));
		String handDescription1 = Util5.decode5CardHand(cardsRes, true);
		System.out.println("Description: " + handDescription1 + " :: " + "int res : " + cardsRes);
	}
}


