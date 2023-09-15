package main;

import java.util.Random;

public class util7 extends util {

    final static int total7CardHandCount = 133784560;
    final static int[] handFrequency7 = {
            23294460, 58627800, 31433400, 6461620, 6180020, 4047644, 3473184, 224848, 41584
    };
    final static long highCardRet = 0;
    final static long pairRet = 2251799813685248L;
    final static long twoPairRet = 4503599627370496L;
    final static long tripsRet = 6755399441055744L;
    final static long straightRet = 9007199254740992L;
    final static long flushRet = 11258999068426240L;
    final static long fullhouseRet = 13510798882111488L;
    final static long quadsRet = 15762598695796736L;
    final static long straightFlushRet = 18014398509481984L;


    // cards are all 2 through Ace, diamonds clubs then hearts then spades (we may
    // change the order, Im already mixing up orders everywhere of cards and suits)
    final static long[] all52Cards7 = new long[]{549755813889L, 549755813896L, 549755813952L, 549755814400L,
            549755817984L, 549755846656L, 549756076032L, 549757911040L, 549772591104L, 549890031616L, 550829555712L,
            558345748480L, 618475290624L, 4398046511105L, 4398046511112L, 4398046511168L, 4398046511616L,
            4398046515200L, 4398046543872L, 4398046773248L, 4398048608256L, 4398063288320L, 4398180728832L,
            4399120252928L, 4406636445696L, 4466765987840L, 35184372088833L, 35184372088840L, 35184372088896L,
            35184372089344L, 35184372092928L, 35184372121600L, 35184372350976L, 35184374185984L, 35184388866048L,
            35184506306560L, 35185445830656L, 35192962023424L, 35253091565568L, 281474976710657L, 281474976710664L,
            281474976710720L, 281474976711168L, 281474976714752L, 281474976743424L, 281474976972800L, 281474978807808L,
            281474993487872L, 281475110928384L, 281476050452480L, 281483566645248L, 281543696187392L};
    /*
    Daimonds:
2 : 549755813889 :	000000000001000000000000000000000000000000000000001
3 : 549755813896 :	000000000001000000000000000000000000000000000001000
4 : 549755813952 :	000000000001000000000000000000000000000000001000000
5 : 549755814400 :	000000000001000000000000000000000000000001000000000
6 : 549755817984 :	000000000001000000000000000000000000001000000000000
7 : 549755846656 :	000000000001000000000000000000000001000000000000000
8 : 549756076032 :	000000000001000000000000000000001000000000000000000
9 : 549757911040 :	000000000001000000000000000001000000000000000000000
T : 549772591104 :	000000000001000000000000001000000000000000000000000
J : 549890031616 :	000000000001000000000001000000000000000000000000000
Q : 550829555712 :	000000000001000000001000000000000000000000000000000
K : 558345748480 :	000000000001000001000000000000000000000000000000000
A : 618475290624 :	000000000001001000000000000000000000000000000000000

Clubs:
2 : 4398046511105 :	000000001000000000000000000000000000000000000000001
3 : 4398046511112 :	000000001000000000000000000000000000000000000001000
4 : 4398046511168 :	000000001000000000000000000000000000000000001000000
5 : 4398046511616 :	000000001000000000000000000000000000000001000000000
6 : 4398046515200 :	000000001000000000000000000000000000001000000000000
7 : 4398046543872 :	000000001000000000000000000000000001000000000000000
8 : 4398046773248 :	000000001000000000000000000000001000000000000000000
9 : 4398048608256 :	000000001000000000000000000001000000000000000000000
T : 4398063288320 :	000000001000000000000000001000000000000000000000000
J : 4398180728832 :	000000001000000000000001000000000000000000000000000
Q : 4399120252928 :	000000001000000000001000000000000000000000000000000
K : 4406636445696 :	000000001000000001000000000000000000000000000000000
A : 4466765987840 :	000000001000001000000000000000000000000000000000000

Hearts:
2 : 35184372088833 :000001000000000000000000000000000000000000000000001
3 : 35184372088840 :000001000000000000000000000000000000000000000001000
4 : 35184372088896 :000001000000000000000000000000000000000000001000000
5 : 35184372089344 :000001000000000000000000000000000000000001000000000
6 : 35184372092928 :000001000000000000000000000000000000001000000000000
7 : 35184372121600 :000001000000000000000000000000000001000000000000000
8 : 35184372350976 :000001000000000000000000000000001000000000000000000
9 : 35184374185984 :000001000000000000000000000001000000000000000000000
T : 35184388866048 :000001000000000000000000001000000000000000000000000
J : 35184506306560 :000001000000000000000001000000000000000000000000000
Q : 35185445830656 :000001000000000000001000000000000000000000000000000
K : 35192962023424 :000001000000000001000000000000000000000000000000000
A : 35253091565568 :000001000000001000000000000000000000000000000000000

Spades:
2 : 281474976710657 :001000000000000000000000000000000000000000000000001
3 : 281474976710664 :001000000000000000000000000000000000000000000001000
4 : 281474976710720 :001000000000000000000000000000000000000000001000000
5 : 281474976711168 :001000000000000000000000000000000000000001000000000
6 : 281474976714752 :001000000000000000000000000000000000001000000000000
7 : 281474976743424 :001000000000000000000000000000000001000000000000000
8 : 281474976972800 :001000000000000000000000000000001000000000000000000
9 : 281474978807808 :001000000000000000000000000001000000000000000000000
T : 281474993487872 :001000000000000000000000001000000000000000000000000
J : 281475110928384 :001000000000000000000001000000000000000000000000000
Q : 281476050452480 :001000000000000000001000000000000000000000000000000
K : 281483566645248 :001000000000000001000000000000000000000000000000000
A : 281543696187392 :001000000000001000000000000000000000000000000000000
     */


    static final long singleCardMask = 78536544841L; //1001001001001001001001001001001001001 37 digits
    // long singleOrPairOrTrip = 471219269046L;
    static final long pairMask = 157073089682L; //10010010010010010010010010010010010010 38 digits
    // long tripMask = trips are combo of single card mask and pair mask
    static final long quadMask = 314146179364L; //100100100100100100100100100100100100100 39 digits
    static final long suitMask = 2251250057871360L; //111111111111000000000000000000000000000000000000000 51 digits
    static final long cardMask = 549755813887L; //111111111111111111111111111111111111111 39 digits

    static final long allSpadeBits = 1970324836974592L; //111000000000000000000000000000000000000000000000000 51 digits
    static final long allHeartsBits = 246290604621824L; //111000000000000000000000000000000000000000000000 48 digits
    static final long allClubsBits = 30786325577728L; //111000000000000000000000000000000000000000000 45 digits
    static final long allDiamondBits = 3848290697216L; //111000000000000000000000000000000000000000 42 digits

    static final long spadeMask = 281474976710656L; //1000000000000000000000000000000000000000000000000 49 digits
    static final long heartMask = 35184372088832L; //1000000000000000000000000000000000000000000000 46 digits
    static final long clubMask = 4398046511104L; //1000000000000000000000000000000000000000000 43 digits
    static final long diamondMask = 549755813888L; //1000000000000000000000000000000000000000 40 digits
    static final long fourSpades = 1125899906842624L; //100000000000000000000000000000000000000000000000000 51 digits
    static final long fourHearts = 140737488355328L; //100000000000000000000000000000000000000000000000 48 digits
    static final long fourClubs = 17592186044416L; //100000000000000000000000000000000000000000000 45 digits
    static final long fourDiamonds = 2199023255552L; //100000000000000000000000000000000000000000 42 digits
    // static final long[] suitMasks = {allSpadeBits, allHeartsBits, allClubsBits,
    // allDiamondBits};
    // static final long[] almostFlush = {fourSpades, fourHearts, fourClubs,
    // fourDiamonds}; //this is the decimal of 4 of each suit, like 1 spade is 001,
    // this is 4 spades or 100
    static final long[] almostFlush = { fourDiamonds, fourClubs, fourHearts, fourSpades }; // this is the decimal of 4
    // of each suit, like 1
    // spade is 001, this is 4
    // spades or 100

    static final long[] fullSuitMasks = { allDiamondBits, allClubsBits, allHeartsBits, allSpadeBits };// this has all 3
    // bits set for
    // each suit,
    // like 1 spade
    // is 001, this
    // is 111

    static final long[] suitDecimals = new long[] { diamondMask, clubMask, heartMask, spadeMask };
    static final long[] cardDecimals = new long[] { 1L, 8L, 64L, 512L, 4096L, 32768L, 262144L, 2097152L, 16777216L,
            134217728L, 1073741824L, 8589934592L, 68719476736L };

    //static final long[] straights = new long[]{68719477321L, 4681L, 37448L, 299584L, 2396672L, 19173376L, 153387008L, 1227096064L, 9816768512L, 78534148096L};
    static final long[] straights = new long[]{78534148096L, 9816768512L, 1227096064L, 153387008L, 19173376L, 2396672L, 299584L, 37448L, 4681L, 68719477321L};
    // static final long[] all52Cards2 = makeAll52Cards7Decimal();



    // These are the bits and what they mean, everything gets 3 bits.
    // Starting from the rightmost bits, 222 means the 2 cards go there
    // if we are adding up how many of each of these cards there are, like if
    // someone has 4-of-a-kind of 2(s)
    // then we need to count to 4, to do that we need 3 bits (100 - this is 4 in
    // binary)
    // same thing for the suits, if there are 7 cards total and all are 1 suit, then
    // we need to count to 7 (111 - this is 7 in binary)
    // so 3 bits each is enough to count whatever we want.
    static final String charPlacement = "SSSHHHCCCDDDAAAKKKQQQJJJTTT999888777666555444333222";






    public static void testEveryHand7n() throws Exception {
        System.out.println("testing every hand 7 cards");
        int totalHands = 133784560;

        long[] allCards = EvalTestPlayground.createAllSevenCardHands();
        int totalCounter= allCards.length/7;

        //get start time
        long startT = System.nanoTime();
        //go through every hand, 5 cards at a time
        for(int i=0;i<allCards.length;i+=7)
            DeadHorse7.eval7(allCards[i],allCards[i+1],allCards[i+2],allCards[i+3],allCards[i+4],allCards[i+5],allCards[i+6]);
        //get end time
        long endT = System.nanoTime();

        // Time = (end time - start time ) divided by a billion : because it is in nano seconds
        double time = (double) (endT - startT)/1000000000;
        System.out.println("Did all " + totalHands + " hands in " + time +" seconds");
        //hands per second in millions
        Double currentTotal = totalHands/time/1000000;
        //allHandPossibilities += currentTotal;
        System.out.println(currentTotal + " million hands a second\n");

        //this is here to count how many of each type of hand come up.
        //it will re-eval each hand so we do not corrupt the timing with this extra process
        //this is here to show the accuracy of making/testing each hand possible
        //Since there is a known number of each type of 7 card hand with 52 cards
        //High Card 		23294460
        //Pair 			58627800
        //Two Pair 		31433400
        //Trips 			6461620
        //Straight 		6180020
        //Flush 			4047644
        //Full House 		3473184
        //Quads 			224848
        //Straight Flush	41584
        //Total			133,784,560

        //this is to go through and compare the number of each type of hand we created
        //to the number of each type of hand we expect
        int[] handCounter = new int[9];
        for(int i=0;i<allCards.length;i+=7){

            long res = DeadHorse7.eval7(allCards[i],allCards[i+1],allCards[i+2],allCards[i+3],allCards[i+4],allCards[i+5],allCards[i+6]);
            int resi = (int) (res >>> 51);

            handCounter[resi]++;
        }
        for(int j=0;j<handCounter.length;j++){
            //check if expected == actual
            boolean checked = handCounter[j]==handFrequency7[j];
            String res = checked
                    //if all hands accounted for, good news
                    ?"PASS - All "+handFrequency7[j]+" "+handNames[j]+" hands are accounted for"

                    //if the counts dont match, show how many failed
                    : "FAIL - " +handCounter[j]+" out of "+handFrequency7[j]
                    +" "+handNames[j]+" hands received!";

            System.out.println(res+" " + ((double)handCounter[j]/totalCounter*100) + "%");
        }

        System.out.println("Total Count : " + totalCounter);

    }

    public static long[] makeAll52Cards7Decimal() {

        int cardCount = util.cardChars.length;
        int suitCount = util.suitChars.length;
        long[] cards = new long[cardCount * suitCount];
        for (int i = 0; i < suitCount; i++) {
            for (int j = 0; j < cardCount; j++) {
                int index = (i * cardCount + j);
                cards[index] = makeDecimalFromIndexes(j, i);// (1L << (13 + i) * 3) | (1L << (j * 3));
                // return (1L << (cardIndex * 3)) | (1L << ((suitIndex + 13) * 3));
                // System.out.println(EvalTestPlayground.bin51(cards[index]) + " Card " + j + "
                // " + i + " * " + (index) +" : " + cards[index]);
            }
        }
        return cards;
    }

    public static long[] getRandomThisType7CardHand(int whatKind) throws Exception { // 0 = high card, 1 = pair, 2 = 2pair etc..


        System.out.println("looking for this type of hand: " + whatKind + " : " + util.handNames[whatKind]);

        int counter = 0;
        int limit = 20000000;
        int batchSize = 10000;//make this many hands at a time, go through them all looking for the desired outcome (instead of making 1 hand at a time, slower)
        if (whatKind > handNames.length - 1 || whatKind < 0) {
            throw new IllegalArgumentException(whatKind + " is invalid. Please pick a hand from 0 - " + (handNames.length - 1));
        }
        while (true) {

            long[] allCards = util7.makeThisManyRandom7CardHands(10000);
            for(int i=0; i < batchSize-7; i+=7){
                if(counter % 1000000 == 0){
                    System.out.println("counter is now : " + counter);
                }
                counter++;
                //int res = DeadHorse7.eval7(allCards[i*7], allCards[i*7+1], allCards[i*7+2], allCards[i*7+3], allCards[i*7+4], allCards[i*7+5], allCards[i*7+6]);
                long res = DeadHorse7.eval7(allCards[i*7], allCards[i*7+1], allCards[i*7+2], allCards[i*7+3], allCards[i*7+4], allCards[i*7+5], allCards[i*7+6]);
                int resi = (int) (res >>> 51);
                //System.out.println("resi: " + resi + " :: " + util.bin51(res) + " : " + res);
                //System.out.println("Cards: " + allCards[i*5] + ", " + allCards[i*5+1] + ", " + allCards[i*5+2] + ", " + allCards[i*5+3] + ", " + allCards[i*5+4]);
                //res >>= 26;
                //if(counter > 10) return null;
                if (resi == whatKind) {
                    System.out.println("Finally got " + handNames[resi] + " after " + counter + " hands");
                    return new long[]{allCards[i*7], allCards[i*7+1], allCards[i*7+2], allCards[i*7+3], allCards[i*7+4], allCards[i*7+5], allCards[i*7+6]};
                } else if (counter > limit) {
                    System.out.println("Did not get a " + handNames[whatKind] + " after " + limit + " hands...stopping the search so it doesnt go on forever. something doesnt seem right, it shouldnt take this long");
                    return new long[]{};
                } /*else {
                    System.out.println("got : " + handNames[resi] + " : " + whatKind + " : "  + resi + " : "
                            + util7.convertDecimalToShortName7(allCards[i*7]) + ", "
                            + util7.convertDecimalToShortName7(allCards[i*7+1]) + ", "
                            + util7.convertDecimalToShortName7(allCards[i*7+2]) + ", "
                            + util7.convertDecimalToShortName7(allCards[i*7+3]) + ", "
                            + util7.convertDecimalToShortName7(allCards[i*7+4]) + ", "
                            + util7.convertDecimalToShortName7(allCards[i*7+5]) + ", "
                            + util7.convertDecimalToShortName7(allCards[i*7+6]));
                }*/
            }

        }

    }

    public static long[] makeThisManyRandom7CardHands(int howMany) {

        long[] fiftyTwoCards = util7.all52Cards7;

        // make a copy of array to do each hand
        long[] allc2 = fiftyTwoCards.clone();

        // the total array with all the cards will be a 1-D array
        // every chunk of 7 cards will be a different random hand
        long[] allCards = new long[howMany * 7];

        // random object
        Random r = new Random();

        // random number
        int ran = 0;

        // x will be used to get the cards from the array and make sure
        // we do not pick the same card twice
        long x = 0;
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
            allc2 = fiftyTwoCards.clone();
        }

        return allCards;
    }

    public static String convertHumanShortNameToLongName7(String cardString) throws Exception {
        // like AH or 5S

        if (cardString.length() != 2) {
            throw new IllegalArgumentException("Card must be 2 chars long:  " + cardString);
        }

        int cardIndex = getCardIndexChar(cardString.charAt(0));
        int suitIndex = getSuitIndexChar(cardString.charAt(1));

        return util.cardLongs[cardIndex] + util.OF + util.suitLongs[suitIndex];
    }

    // This method will take a String like "5S" or "JC" (five of clubs or Jack of
    // Spades) and turn it into the decimal equivalent for that card
    // This function is not optimized, just here to make things easier. If actually
    // needed in some performance situation, we should find faster ways to do this
    public static long convertHumanShortNameToDecimal7(String cardString) throws Exception {
        // like AH or 5S
        if (cardString.length() != 2) {
            throw new IllegalArgumentException("Card must be 2 chars long:  " + cardString);
        }

        int cardIndex = getCardIndexChar(cardString.charAt(0));
        int suitIndex = getSuitIndexChar(cardString.charAt(1));

        return makeDecimalFromIndexes(cardIndex, suitIndex);
    }

    public static long[] convertHandHumanShortToDecimal7(String[] cards) throws Exception {
        if (cards.length != 7) {
            throw new IllegalArgumentException("There must be 7 cards");
        }
        long[] ret = new long[cards.length];
        for (int i = 0; i < cards.length; i++) {
            ret[i] = convertHumanShortNameToDecimal7(cards[i]);
        }
        return ret;
    }

    public static long orHand(long[] cards) {
        if (cards.length != 7) {
            throw new IllegalArgumentException("There must be 7 cards");
        }
        return cards[0] | cards[1] | cards[2] | cards[3] | cards[4] | cards[5] | cards[6];
    }

    public static long sumHand(long[] cards) {
        if (cards.length != 7) {
            throw new IllegalArgumentException("There must be 7 cards");
        }
        return cards[0] + cards[1] + cards[2] + cards[3] + cards[4] + cards[5] + cards[6];
    }

    public static long makeDecimalFromIndexes(int cardIndex, int suitIndex) {
        return (1L << (cardIndex * 3)) | (1L << ((suitIndex + 13) * 3));
    }

    public static String convertDecimalToLongName7(long card) throws Exception {
        return getCardLong(card) + util.OF + getSuitLong(card);
    }

    public static String convertDecimalToShortName7(long card) throws Exception {
        return (getCardChar(card) + "" + getSuitChar(card));
    }

    public static String getSuitLong(long card) throws Exception {
        return util.suitLongs[getSuitIndexDecimal(card)];
    }

    public static String getCardLong(long card) throws Exception {
        return util.cardLongs[getCardIndexDecimal(card)];
    }

    public static char getSuitChar(long card) throws Exception {
        return util.suitChars[getSuitIndexDecimal(card)];
    }

    public static char getCardChar(long card) throws Exception {
        return util.cardChars[getCardIndexDecimal(card)];
    }

    public static int getSuitIndexDecimal(long card) throws Exception {
        for (int i = 0; i < suitDecimals.length; i++) {
            if ((card & suitDecimals[i]) != 0) {
                return i;
            }
        }
        throw new Exception("Error retreiving suit index for card: " + card);
    }

    public static int getCardIndexDecimal(long card) throws Exception {
        for (int i = 0; i < cardDecimals.length; i++) {
            if ((card & cardDecimals[i]) != 0) {
                return i;
            }
        }
        throw new Exception("Error retreiving card index for card: " + card);
    }

    public static int getSuitIndexChar(char suitChar) throws Exception {
        for (int i = 0; i < util.suitChars.length; i++) {
            if (suitChar == util.suitChars[i]) {
                return i;
            }
        }
        throw new Exception("Error retreiving suit index for suit char: " + suitChar);
    }

    public static int getCardIndexChar(char cardChar) throws Exception {
        for (int i = 0; i < util.cardChars.length; i++) {
            if (cardChar == util.cardChars[i]) {
                return i;
            }
        }
        throw new Exception("Error retreiving card index for card char: " + cardChar);
    }

}
