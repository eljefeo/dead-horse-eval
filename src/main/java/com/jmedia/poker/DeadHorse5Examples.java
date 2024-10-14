package com.jmedia.poker;

import java.util.Arrays;
import java.util.List;

public class DeadHorse5Examples {


    public static void doTestExamples() throws Exception {
        shortToLong(); //This shows how to mock up a hand and get it evaluated into a human-readable description like "2C", "2S", "9C", "7C", "7H" and it shows Two Pair: Sevens and Twos with a Nine kicker
        testEval5Ints();
        //HandMakerFiveCard.testGetRandomTestSomeHand();
        //getRandomHandFromDescription();

        //speedGameOfPoker();
        //speedGameOfPokerRealDeck();
        //getSomeRandomHand();
    }

    private static void speedGameOfPoker() throws Exception {
        //Doesnt actually deal hands from the same deck
        //We will deal N number of random hands, evaluate the hands, and find out which player wins!
        int playerCount = 5;
       // List<Integer[]> hands = new ArrayList<>();
        int[] handResults = new int[playerCount];
        String[] handDescriptions = new String[playerCount];
        System.out.println();
        for (int i=0; i < playerCount; i++){
            Integer[] randomHand = HandMakerFiveCard.getRandomHand();
            int res = DeadHorse.eval5(randomHand);
            handResults[i] = res;
            String handDescription = Util5.decode5CardHand(res, false);
            handDescriptions[i] = handDescription;
            //hands.add(randomHand);
            //System.out.println("Player " + (i+1) + " is dealt a " + util5.convertDecimalsToLongHandDescription(randomHand));
            System.out.println("Player " + (i+1) + " is dealt a " + handDescription);
            for(Integer c : randomHand){
                System.out.println(Util5.decimalToLongCardName(c));
            }
            System.out.println("----------\n");
        }
        System.out.println("Who Wins???");
        int winnerIndex = Util.indexOfLargestIntInArray(handResults);
        System.out.println("\nPlayer " + (winnerIndex+1) + " WINS with a " + handDescriptions[winnerIndex]);

    }

    private static void speedGameOfPokerRealDeck() throws Exception {
        //This will deal cards from a 52 card deck, then show and evaluate each hand, and show who wins. Good for gambling
        System.out.println("dealing hands from the same deck");
        int playerCount = 5;
        List<Integer[]> hands = Util5.dealRandomHandsFromDeck(playerCount);
        int[] handResults = new int[playerCount];
        String[] handDescriptions = new String[playerCount];

        for(int i = 0; i < hands.size(); i++){
            Integer[] hand = hands.get(i);
            int res = DeadHorse.eval5(hand);
            handResults[i] = res;
            String handDescription = Util5.decode5CardHand(res, false);
            handDescriptions[i] = handDescription;
            System.out.println("-----\nPlayer " + (i+1) + " has " + Arrays.toString(Util5.decimalsToShortCardNames(hand)) + " - " + handDescription);
        }

        System.out.println("Who Wins???");
        int winnerIndex = Util.indexOfLargestIntInArray(handResults);
        System.out.println("\nPlayer " + (winnerIndex+1) + " WINS with a " + handDescriptions[winnerIndex]);

    }

    private static void getSomeRandomHand() {
        //gets a totally random hand and evaluates it, good for gambling
        System.out.println("Retrieving a totally random hand...");
        Integer[] cards = HandMakerFiveCard.getRandomHand();
        String result = Util5.decimalsToLongHandDescription(cards); //util5.shortCardsToLongDescription(cards, false);

        System.out.println(result);
    }

    private static void getRandomHandFromDescription() throws Exception {
        //Here you can type in what type of hand you want and it will find a random hand of that type.
        String handType = "full house";
        System.out.println("Retrieving random " + handType + " hand:");
        String[] cards = HandMakerFiveCard.getRandomHandFromDescription(handType);
        System.out.println(Arrays.toString(cards) + " --- " + Util5.shortCardsToLongHandDescription(cards, false));


    }

    //5 card
    public static void shortToLong(){
        //This shows how to mock up a hand and get it evaluated into a human-readable description like "2C", "2S", "9C", "7C", "7H" and it shows Two Pair: Sevens and Twos with a Nine kicker
        System.out.println("Example: shorthand card names to full hand description..." );
        String[] cards = new String[]{
                //"3C", "3S", "7C", "3H", "3S"
                //"2D", "5D", "3D", "4D", "6D",
                "AC","2C","2D","2H","4C"
        };
        System.out.println("Evaluating these cards: " + Arrays.toString(cards));
        String result = Util5.shortCardsToLongHandDescription(cards, true);

        Integer[] decs = Util5.shortCardNamesToDecimals(cards);
        int res = DeadHorse.eval5(decs);
        System.out.println("5card result : " + res + " : " + Util.bin32(res));

        System.out.println(result);
        System.out.println("\n");
    }


    public static void testEval5Ints() throws Exception {
        int a =   20480;
        int b =  16385;
        int c =  8193;
        int d =  32769;
        int e =  16388;
        System.out.println("Short to long cards testEval5Ints : " + a + " : " + Util5.decimalToShortCardName(a));
        System.out.println("Short to long cards testEval5Ints : " + b + " : " + Util5.decimalToShortCardName(b));
        System.out.println("Short to long cards testEval5Ints : " + c + " : " + Util5.decimalToShortCardName(c));
        System.out.println("Short to long cards testEval5Ints : " + d + " : " + Util5.decimalToShortCardName(d));
        System.out.println("Short to long cards testEval5Ints : " + e + " : " + Util5.decimalToShortCardName(e));
        //int res = DeadHorse.eval5(a,b,c,d,e);
        int res = DeadHorse.eval5WithNotes(a,b,c,d,e);
        System.out.println("Eval 5 hopefully no bug : " + res);
        String resString = Util5.decode5CardHand(res, true);
    }

}
