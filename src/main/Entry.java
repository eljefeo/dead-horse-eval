package main;

public class Entry {

	public static void main(String[] args) throws java.lang.Exception {
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

		 EvalTestPlayground.testEveryHand5();
		//EvalTestPlayground.testEval5AndNotes();

		 //EvalTestPlayground.testStatisticsManyHand5(10000000);
		//DeadHorse7.findDuplicates();
		///DeadHorse7.findFlushes();
		//DeadHorse7.findStraights();
		//DeadHorse7.testeval7();
		DeadHorse7.eval7Checke();
		util7.testEveryHand7n();
		//util5.humanEncodeEval("2S", "8S", "6S", "5S", "3S" );


		//util5.decode5CardHand(403701764);
		//util5.humanEncodeShortAndDecodeLongHand(new String[] {"8H", "9D", "9C", "9H", "8D"});

		// EvalTestPlayground.testEveryHand7();

		// EvalTestPlayground.testEveryHand7n();

		// EvalTestPlayground.test7();
		// EvalTestPlayground.doSuitTest();

		// EvalTestPlayground.makeNewnums();

		/*
		initial tests for 7 card eval
		PASS - All 23294460 High Card hands are accounted for 17.411919581751437%
		PASS - All 58627800 Pair hands are accounted for 43.822545740704314%
		PASS - All 31433400 Two Pair hands are accounted for 23.495536405695844%
		PASS - All 6461620 3 of a kind hands are accounted for 4.8298697547758875%
		PASS - All 6180020 Straight hands are accounted for 4.619382087140698%
		FAIL - 4052396 out of 4047644 Flush hands received! 3.029046102180999%
		PASS - All 3473184 Full House hands are accounted for 2.5961022706955124%
		PASS - All 224848 4 of a kind hands are accounted for 0.16806722689075632%
		FAIL - 36832 out of 41584 Straight Flush hands received! 0.02753083016455711%
Total Count : 133784560





FAIL - 50220 out of 23294460 High Card hands received! 0.03753796402215622%
FAIL - 140077 out of 58627800 Pair hands received! 0.10470341271070444%
FAIL - 291981 out of 31433400 Two Pair hands received! 0.21824715796800467%
FAIL - 125004 out of 6461620 3 of a kind hands received! 0.09343679121118312%
FAIL - 2696848 out of 6180020 Straight hands received! 2.0158140819837507%
FAIL - 0 out of 4047644 Flush hands received! 0.0%
FAIL - 1233944 out of 3473184 Full House hands received! 0.9223366283822289%
FAIL - 129246486 out of 224848 4 of a kind hands received! 96.60792396372197%
FAIL - 0 out of 41584 Straight Flush hands received! 0.0%
Total Count : 133784560
		 */

	}
}
