package main;

import java.util.Arrays;

public class DeadHorse7Examples {


    public static void doTestExamples() throws Exception {

        testBin();

        //shortToLong(); //This shows how to mock up a hand and get it evaluated into a human-readable description like "2C", "2S", "9C", "7C", "7H" and it shows Two Pair: Sevens and Twos with a Nine kicker
        //HandMakerFiveCard.testGetRandomTestSomeHand();
        //getRandomHandFromDescription();



        speedGameOfPoker();
        speedGameOfPokerRealDeck();
        getSomeRandomHand();
        
    }

    private static void testBin() throws Exception {

        String[] cards1 = new String[]{
                //I think there is a problem here, how can we tell if 4ofAKind of 2s with 7 kicker, is less than 3 of a kind with 2 kicker
                //"2C", "4C", "5C", "3C", "6C", "9D", "KS" //0000000001000000000000000000000000000000000000000000000000000001
                //"2C", "2D", "5C", "3C", "6C", "2H", "2S" //0000000000111000000000000000000000000000000000000001000000000010 four 2s with 6 kicker
                //"2C", "2D", "5C", "3C", "7C", "2H", "2S" //0000000000111000000000000000000000000000000000001000000000000010 four 2s with 7 kicker
                "3C", "3D", "5C", "2C", "7C", "3H", "3S"   //0000000000111000000000000000000000000000000000001000000000010000 four 3s with 7 kicker
        };

        String[] cards2 = new String[]{
                //I think there is a problem here, how can we tell if 4ofAKind of 2s with 7 kicker, is less than 3 of a kind with 2 kicker
                //"2C", "4C", "5C", "3C", "6C", "9D", "KS" //0000000001000000000000000000000000000000000000000000000000000001
                //"2C", "2D", "5C", "3C", "6C", "2H", "2S" //0000000000111000000000000000000000000000000000000001000000000010 four 2s with 6 kicker
                "2C", "2D", "5C", "3C", "9C", "2H", "2S" //0000000000111000000000000000000000000000000000001000000000000010 four 2s with 7 kicker
                //"3C", "3D", "5C", "2C", "7C", "3H", "3S"  //0000000000111000000000000000000000000000000000001000000000010000 four 3s with 7 kicker
        };
        System.out.println("Evaluating these cards1: " + Arrays.toString(cards1));
        long result1 = util7.evalShortCards(cards1);
        long res1 = result1 >>> 51;
        System.out.println("long result1 = " + result1 + " : " + res1 + " :: " + util.handNames[(int)res1]);
        System.out.println("bin1 : " + util.bin64(result1));
        System.out.println("\n\n");

        System.out.println("Evaluating these cards2: " + Arrays.toString(cards2));
        long result2 = util7.evalShortCards(cards2);
        long res2 = result2 >>> 51;
        System.out.println("long result2 = " + result2 + " : " + res2 + " :: " + util.handNames[(int)res2]);
        System.out.println("bin : " + util.bin64(result2));

        if(result1 == result2) System.out.println("They are the SAME!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        int winner = result1 > result2 ? 1 : 2;
        System.out.println("\nwinner: " + result1 + " : " + result2 + " ::: Player " + winner);


        long[] quadCards = HandMakerSevenCard.getRandomQuadsHand();

        long res3 = DeadHorse7.eval7(quadCards);
        System.out.println("Manufactured quad hand: " + util.handNames[(int)(res3 >>> 51)]);


        int type = 0;
        for(int i = 0; i < util.handNames.length; i++){
            System.out.println("\nSearching for a " + util.handNames[i] + " type of hand");
            long[] randCards = util7.getRandomThisType7CardHand(i);

            long rRes = DeadHorse7.eval7(randCards);
            //int gotType = rRes >>> 51;
            String[] shrts = util7.decimalsToShortCardNames(randCards);


            System.out.println("Got : " + util.handNames[(int)(rRes >>> 51)] + " : " + shrts[0] + ", " + shrts[1] + ", " + shrts[2] + ", " + shrts[3] + ", " + shrts[4] + ", " + shrts[5] + ", " + shrts[6]);




        }
        System.out.println("making straight hand");
        int max = 1000;
        for(int i = 0; i < max; i++){
            HandMakerSevenCard.getRandomStraightHand2();
        }



    }

    private static void getRandomHandFromDescription() throws Exception {

        //Here you can type in what type of hand you want and it will find a random hand of that type.
        String handType = "full house";
        System.out.println("Retrieving random " + handType + " hand:");
        long[] cardLongs = HandMakerSevenCard.getRandomHandFromDescription(handType);
        String[] cards = util7.decimalsToShortCardNames(cardLongs);
        System.out.println(Arrays.toString(cards) + " --- " + util5.shortCardsToLongHandDescription(cards, false));


    }

    private static void speedGameOfPoker() {
    }

    private static void getSomeRandomHand() {
    }

    private static void speedGameOfPokerRealDeck() {
        
    }

    public static void shortToLong(){
        //This shows how to mock up a hand and get it evaluated into a human-readable description like "2C", "2S", "9C", "7C", "7H" and it shows Two Pair: Sevens and Twos with a Nine kicker
        System.out.println("Example: shorthand card names to full hand description..." );
        String[] cards = new String[]{
                "2C", "4C", "5C", "3C", "6C", "9D", "KS"
        };
        System.out.println("Evaluating these cards: " + Arrays.toString(cards));
        String result = util7.shortCardsToLongHandDescription(cards, false);

        System.out.println(result);
    }
}
