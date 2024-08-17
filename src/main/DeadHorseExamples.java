package main;

import java.util.Arrays;
import java.util.List;

public class DeadHorseExamples {


    public static void doTestExamples() throws Exception {
        //shortToLong();
        //HandMakerFiveCard.testGetRandomTestSomeHand();
        //getRandomHandFromDescription();

        //speedGameOfPoker();
        speedGameOfPokerRealDeck();
        getSomeRandomHand();
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
            String handDescription = util5.decode5CardHand(res, false);
            handDescriptions[i] = handDescription;
            //hands.add(randomHand);
            //System.out.println("Player " + (i+1) + " is dealt a " + util5.convertDecimalsToLongHandDescription(randomHand));
            System.out.println("Player " + (i+1) + " is dealt a " + handDescription);
            for(Integer c : randomHand){
                System.out.println(util5.decimalToLongCardName(c));
            }
            System.out.println("----------\n");
        }
        System.out.println("Who Wins???");
        int winnerIndex = util.indexOfLargestIntInArray(handResults);
        System.out.println("\nPlayer " + (winnerIndex+1) + " WINS with a " + handDescriptions[winnerIndex]);

    }

    private static void speedGameOfPokerRealDeck() throws Exception {
        //This will deal cards from a 52 card deck, then show and evaluate each hand, and show who wins. Good for gambling
        System.out.println("dealing hands from the same deck");
        int playerCount = 10;
        List<Integer[]> hands = util5.dealRandomHandsFromDeck(playerCount);
        int[] handResults = new int[playerCount];
        String[] handDescriptions = new String[playerCount];

        for(int i = 0; i < hands.size(); i++){
            Integer[] hand = hands.get(i);
            int res = DeadHorse.eval5(hand);
            handResults[i] = res;
            String handDescription = util5.decode5CardHand(res, false);
            handDescriptions[i] = handDescription;
            System.out.println("-----\nPlayer " + (i+1) + " has " + Arrays.toString(util5.decimalsToShortCardNames(hand)) + " - " + handDescription);
        }

        System.out.println("Who Wins???");
        int winnerIndex = util.indexOfLargestIntInArray(handResults);
        System.out.println("\nPlayer " + (winnerIndex+1) + " WINS with a " + handDescriptions[winnerIndex]);

    }

    private static void getSomeRandomHand() {
        //gets a totally random hand and evaluates it, good for gambling
        System.out.println("Retrieving a totally random hand...");
        Integer[] cards = HandMakerFiveCard.getRandomHand();
        String result = util5.decimalsToLongHandDescription(cards); //util5.shortCardsToLongDescription(cards, false);

        System.out.println(result);
    }

    private static void getRandomHandFromDescription() throws Exception {
        //Here you can type in what type of hand you want and it will find a random hand of that type
        String handType = " flush";
        System.out.println("Retrieving random " + handType + " hand:");
        String[] cards = HandMakerFiveCard.getRandomHandFromDescription(handType);
        System.out.println(Arrays.toString(cards) + " --- " + util5.shortCardsToLongHandDescription(cards, false));


    }

    //5 card
    public static void shortToLong(){
        //This shows how to mock up a hand and get it evaluated into a human-readable description
        System.out.println("Example: shorthand card names to full hand description..." );
        String[] cards = new String[]{
                "2C", "2S", "9C", "7C", "7H"
        };
        System.out.println("Evaluating these cards: " + Arrays.toString(cards));
        String result = util5.shortCardsToLongHandDescription(cards, false);

        System.out.println(result);
    }

}
