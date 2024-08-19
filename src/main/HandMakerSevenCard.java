package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class HandMakerSevenCard {

    static final Random rand = new Random();

    private static final List<Long[]> allHighCardHands = new ArrayList<>();
    private static final List<Long[]> allPairHands = new ArrayList<>();
    private static final List<Long[]> allTwoPairHands = new ArrayList<>();
    private static final List<Long[]> allTripHands = new ArrayList<>();
    private static final List<Long[]> allStraightHands = new ArrayList<>();
    private static final List<Long[]> allFlushHands = new ArrayList<>();
    private static final List<Long[]> allFullHouseHands = new ArrayList<>();
    private static final List<Long[]> allQuadHands = new ArrayList<>();
    private static final List<Long[]> allStraightFlushHands = new ArrayList<>();
    private static final List<List<Long[]>> allHandsOrdered = new ArrayList<>();



    //TODO This works in 5 card evaluator because theres only 2.5 million hands. Will not work with 7 card as there is 133 million hands, we will run out of Java heap space...
    private static final List<Long[]> allHands = new ArrayList<>();
    /*static {
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
        long[] allSevenCardHands = EvalTestPlayground.createAllSevenCardHands();
        for(int i=0;i<allSevenCardHands.length;i+=7){

            Long[] aHand = new Long[] {allSevenCardHands[i],allSevenCardHands[i+1],
                    allSevenCardHands[i+2],allSevenCardHands[i+3],allSevenCardHands[i+4], allSevenCardHands[i+5],allSevenCardHands[i+6]};

            long res = DeadHorse7.eval7(aHand) >>> 51;

            //System.out.println("cast long to int for hand type, hopefully : " + ((int) res));
            allHandsOrdered.get((int)res).add(aHand);

            allHands.add(aHand);
        }
    }*/


    /*public static List<Long[]> makeThisManyRandom7CardHands(int howMany) {
        List<Long[]> hands = new ArrayList<>();
        for(int i = 0; i < howMany; i++){
            hands.add(allHands.get(rand.nextInt(allHands.size())));
        }
        return hands;
    }*/

    public static void mixUpCards(long[] c) {
        int N = c.length;
        for (int i = 0; i < N; i++) {
            int r = i + (int) (Math.random() * (N - i));
            long t = c[r];
            c[r] = c[i];
            c[i] = t;
        }
    }

    public static void mixUpCards(Long[] c) {
        int N = c.length;
        for (int i = 0; i < N; i++) {
            int r = i + (int) (Math.random() * (N - i));
            long t = c[r];
            c[r] = c[i];
            c[i] = t;
        }
    }

    public static void mixUpInts(int[] c) {
        int N = c.length;
        for (int i = 0; i < N; i++) {
            int r = i + (int) (Math.random() * (N - i));
            int t = c[r];
            c[r] = c[i];
            c[i] = t;
        }
    }

    public static void sortHighDown(long[] n) {
        long temp = 0;
        int size = n.length - 1;
        for (int i = 0; i < size; i++)
            for (int j = 0; j < (size - i); j++)
                if ((n[j] & 8191) < (n[j + 1] & 8191)) {
                    temp = n[j];
                    n[j] = n[j + 1];
                    n[j + 1] = temp;
                }
    }

    public static List<List<Long[]>> getAllHandsOrdered(){
        return allHandsOrdered;
    }


    public static long[] getRandomHandFromDescription(String handDescription) throws Exception {

        //int handType;
        //if(handDescription.toUpperCase().equals(util.ROYAL_FLUSH.toUpperCase())){
        //	 handType = 9; //maybe can hijack some functions and use 9 as royal flush indicator
        //} else {
        int handType = util.handDescriptionToType(handDescription);
        long[] cardsLong = getRandomThisType7CardHand(handType);
        //}

        return cardsLong; //util7.decimalsToShortCardNames(cardsLong);
    }

    /*public static Long[] getRandomHand(){
        return allHands.get(rand.nextInt(allHands.size()));
    }*/
    public static long[] getRandomHighCardHand(){
        return getRandomThisType7CardHand(0);
    }
    public static long[] getRandomPairHand(){
        return getRandomThisType7CardHand(1);
    }
    public static long[] getRandomTwoPairHand(){
        return getRandomThisType7CardHand(2);
    }
    public static long[] getRandomTripsHand(){
        return getRandomThisType7CardHand(3);
    }
    public static long[] getRandomStraightHand(){
        return getRandomThisType7CardHand(4);
    }
    public static long[] getRandomFlushHand(){
        return getRandomThisType7CardHand(5);
    }
    public static long[] getRandomFullHouseHand(){
        return getRandomThisType7CardHand(6);
    }
    public static long[] getRandomQuadsHand(){
        return getRandomThisType7CardHand(7);
    }
    public static long[] getRandomStraightFlushHand(){
        return getRandomThisType7CardHand(8);
    }


    /*public static Long[] getRandomCertainTypeHand(int type){
        List<Long[]> handList = allHandsOrdered.get(type);
        //int ni = rand.nextInt(handList.size());
        //System.out.println("getRandomCertainTypeHand : " + util.handNames[type] + " random hand : " + ni + ", " + highCardHands.size());

        return new Long[]{0L}; //handList.get(ni);
    }*/



    public static long[] getRandomThisType7CardHand(int whatKind) { // 0 = high card, 1 = pair, 2 = 2pair etc..

        int counter = 0;
        int limit = 10000000;
        int chunks = 1000;
        if (whatKind > util.handNames.length - 1) {
            throw new IllegalArgumentException(whatKind + " is invalid. Please pick a hand from 0 - " + (util.handNames.length - 1));
        }
        while (true) {
            counter++;
            long[] sevenCards = util7.makeThisManyRandom7CardHands(chunks);

            for(int i = 0; i < sevenCards.length; i += 7){
                long res = DeadHorse7.eval7(sevenCards[i], sevenCards[i+1], sevenCards[i+2], sevenCards[i+3], sevenCards[i+4], sevenCards[i+5], sevenCards[i+6]);
                int type = (int) (res >>> 51);
                if (type == whatKind) {
                    System.out.println("Finally got " + util.handNames[type] + " after " + counter + " hands");
                    return new long[]{sevenCards[i], sevenCards[i+1], sevenCards[i+2], sevenCards[i+3], sevenCards[i+4], sevenCards[i+5], sevenCards[i+6]};
                } else if (counter > limit) {
                    System.out.println("Did not get a " + util.handNames[whatKind] + " after " + limit + " hands...stopping the search so it doesnt go on forever");
                    return null;
                }
            }



        }

    }


    //This is not going to be pretty, algorithm to create a pair hand, or high card...
    public static Long[] getRandomPairHand2(){
        List<Long> allCards = Arrays.stream(util7.all52CardsDecimal).boxed().collect(Collectors.toList());
        int cardCount = util.cardChars.length;
        int suitCount = util.suitChars.length;

        Long[] cards = new Long[util7.numOfCardsPerHand];


        int cardInd = rand.nextInt(cardCount); //this 2, 3,4,5,6,7,8,9,T,J,Q,K
        long pairCard = util.cardChars[cardInd];
        System.out.println("Picked first card: " + cardInd);


        int suit1Ind = rand.nextInt(suitCount);
       // long suit1 = util.suitChars[suit1Ind];

        int suit2Ind = suit1Ind;
        while(suit2Ind == suit1Ind){
            suit2Ind = rand.nextInt(suitCount); //keep picking a random suit index until it is different than the first
        }

        //long suit2 = util.suitChars[suit2Ind];

        long card1 = util7.makeDecimalFromIndexes(cardInd, suit1Ind);
        long card2 = util7.makeDecimalFromIndexes(cardInd, suit2Ind);

        allCards.remove(card1);
        allCards.remove(card2);
        cards[0] = card1;
        cards[1] = card2;



        for (int i = 2; i < suitCount; i++){
            //cards[i] = allCards
        }


    return new Long[]{0L};

    }

    public static Long[] getRandomStraightHand2() throws Exception {
        List<Long> allCards = Arrays.stream(util7.all52CardsDecimal).boxed().collect(Collectors.toList());
        int cardCount = util.cardChars.length;
        int suitCount = util.suitChars.length;

        Long[] cards = new Long[util7.numOfCardsPerHand];


        int cardInd = rand.nextInt(cardCount); //this 2, 3,4,5,6,7,8,9,T,J,Q,K
        char[] ourCardChars = new char[util7.numOfCardsPerHand];
        char[] ourSuitChars = new char[util7.numOfCardsPerHand];
        //maybe pick a straight by choosing at random the highest card of the straight, choose from 5 - Ace

        int highestCard = rand.nextInt(10) + 3;//5,6,7,8,9,T,J,Q,K - 10 cards here. We actually want to pick a random number from 3-13, but we just do 0-10 and then add 3 after
        //System.out.println("highest card is " + util.cardLongNames[highestCard] + " hopefully same as :: " + util.cardChars[3]);
        if(highestCard == 3) { //this means highest card is the 5, so A2345, that darn low ace straight, gotta do something special for it
            ourCardChars[0] = util.cardChars[util.cardChars.length-1];
            ourSuitChars[0] = util.suitChars[rand.nextInt(util.suitChars.length)];

            ourCardChars[1] = util.cardChars[0];
            ourSuitChars[1] = util.suitChars[rand.nextInt(util.suitChars.length)];

            ourCardChars[2] = util.cardChars[1];
            ourSuitChars[2] = util.suitChars[rand.nextInt(util.suitChars.length)];

            ourCardChars[3] = util.cardChars[2];
            ourSuitChars[3] = util.suitChars[rand.nextInt(util.suitChars.length)];

            ourCardChars[4] = util.cardChars[3];
            ourSuitChars[4] = util.suitChars[rand.nextInt(util.suitChars.length)];
            //System.out.println("Found Low Ace straight...");
        } else {
            for(int i = 0; i < 5; i++){
                ourCardChars[i] = util.cardChars[highestCard-i];
                ourSuitChars[i] = util.suitChars[rand.nextInt(util.suitChars.length)];
            }
        }

        ourCardChars[5] = util.cardChars[rand.nextInt(util.cardChars.length)];
        ourSuitChars[5] = util.suitChars[rand.nextInt(util.suitChars.length)];

        ourCardChars[6] = util.cardChars[rand.nextInt(util.cardChars.length)];
        ourSuitChars[6] = util.suitChars[rand.nextInt(util.suitChars.length)];

        for(int i = 0; i < ourSuitChars.length; i++){
            System.out.println("Created cards before checking for flushes: " + ourCardChars[i] + ourSuitChars[i]);
        }


        for(int i = 0; i < 5; i++){ //check if our final 2 kicker cards are a duplicate of any of the straight cards, if so pick a random other suit for that card.
            if(ourCardChars[5] == ourCardChars[i] && ourSuitChars[5] == ourSuitChars[i]){
                /*char[] newSuitChoices = new char[util.suitChars.length - 1];
                int counter = 0;
                for(char sc : util.suitChars){
                    if(sc != ourSuitChars[5]){
                        newSuitChoices[counter++] = sc;
                    }
                }*/
                ourSuitChars[5] = pickRandomSuitExcept(ourSuitChars[5]);// newSuitChoices[rand.nextInt(newSuitChoices.length)];
                break;
            }
        }

        for(int i = 0; i < 5; i++){ //check if our final 2 kicker cards are a duplicate of any of the straight cards, if so pick a random other suit for that card.
            if(ourCardChars[6] == ourCardChars[i] && ourSuitChars[6] == ourSuitChars[i]){
                /*char[] newSuitChoices = new char[util.suitChars.length - 1];
                int counter = 0;
                for(char sc : util.suitChars){
                    if(sc != ourSuitChars[6]){
                        newSuitChoices[counter++] = sc;
                    }
                }*/
                ourSuitChars[6] = pickRandomSuitExcept(ourSuitChars[6]);// newSuitChoices[rand.nextInt(newSuitChoices.length)];
                break;
            }
        }

        int[] suitCounts = new int[util.suitChars.length];
        for(int i = 0; i < ourSuitChars.length; i++){

            for(int j = 0; j < suitCounts.length; j++){
                if(ourSuitChars[i] == util.suitChars[j]){
                    suitCounts[j]++;
                }

            }
            //System.out.println("logging cards again: " + ourCardChars[i] + ourSuitChars[i]);
        }



       // char flushSuit = 0;
        //for(int cnt : suitCounts){
        //System.out.println("Suit counts, length: " + suitCounts.length);
        for(int c = 0; c < suitCounts.length; c++){
            int cnt = suitCounts[c];
            //System.out.println("Suit counts : " + util.suitChars[c] + " : " + cnt);
            if(cnt > 4){
                int numOfSuitsToChange = cnt - 4;
               // System.out.println("Need to change this many suits: " + numOfSuitsToChange);
                int[] indexes = new int[ourSuitChars.length];

                for(int i=0; i<indexes.length; i++)
                    indexes[i] = i;

                //System.out.println("Indexes before : " + Arrays.toString(indexes));
                mixUpInts(indexes);

                //System.out.println("Indexes mixed hopefully after : " + Arrays.toString(indexes));
                int numSuitsChanged = 0;
                int ind = 0;
                while(numSuitsChanged < numOfSuitsToChange){
                    System.out.println("doing ind = " + ind);
                    System.out.println("and index = " + indexes[ind] );

                    if(ourSuitChars[indexes[ind]] == util.suitChars[c]){
                        //System.out.println("before changing this suit  " + ourCardChars[indexes[ind]] + ourSuitChars[indexes[ind]]);
                         char newSt = pickRandomSuitExcept(ourSuitChars[indexes[ind]]);
                        System.out.println("Changed " + ourCardChars[indexes[ind]] + ourSuitChars[indexes[ind]] + " to " +  ourCardChars[indexes[ind]] + newSt);

                        boolean dup = false;
                        for(int i = 0; i < ourSuitChars.length; i++){
                            if(i==indexes[ind]) continue;
                            if(newSt == ourSuitChars[i] && ourCardChars[indexes[ind]] == ourCardChars[i]){
                                System.out.println("Found a duplicate: " + ourCardChars[indexes[ind]] +newSt + " : " + ourCardChars[i] + ourSuitChars[i] + " :: at " + i + " ind=" + ind + " index=" +  indexes[ind]);
                                dup = true;
                                break;
                            }
                        }

                        if(dup){

                            ind--;
                        } else {
                            ourSuitChars[indexes[ind]] = newSt;
                            numSuitsChanged++;
                        }
                    }
                    ind++;
                }
            }
        }


        /*System.out.println("After changing EVERYTHING");
        for(int i = 0; i < ourSuitChars.length; i++){
            System.out.println("Card " + i + " : " + ourCardChars[i] + ourSuitChars[i]);
        }*/

        Long[] straightCards = new Long[util7.numOfCardsPerHand];//new Long[]{util7.makeDecimalFromChar()}
        for(int i = 0; i < straightCards.length; i++){
            System.out.println("final list: " + ourCardChars[i] + ourSuitChars[i]);
            straightCards[i] = util7.makeDecimalFromChars(ourCardChars[i], ourSuitChars[i]);
        }

        for(int i = 0; i < straightCards.length-1; i++){
            for(int j = i+1; j < straightCards.length; j++){
                if(straightCards[i] == straightCards[j]){
                    throw new Exception("They are the SAME!!!!!!!!!!!!!!");
                }
            }

        }

        return straightCards;
        //need to check and make sure we dont have a flush...


        //that takes care of the straight. Now to get the other 2 kicker cards





    }

    private static char pickRandomSuitExcept(char suit ){
        char[] newSuitChoices = new char[util.suitChars.length - 1];
        int counter = 0;
        for(char sc : util.suitChars){
            if(sc != suit){
                newSuitChoices[counter++] = sc;
            }
        }
        return newSuitChoices[rand.nextInt(newSuitChoices.length)];

    }


    public static Long[] getRandomQuadsHand2() throws Exception {
        //Pick random card from deck
        // go get the other 3 cards so we have all 4
        // pick any other random card as the kicker

        List<Long> allCards = Arrays.stream(util7.all52CardsDecimal).boxed().collect(Collectors.toList());
        int cardCount = util.cardChars.length;
        int suitCount = util.suitChars.length;

        Long[] cards = new Long[util7.numOfCardsPerHand];


        int cardInd = rand.nextInt(cardCount); //this 2, 3,4,5,6,7,8,9,T,J,Q,K
        System.out.println("Picked first card: " + cardInd);
        //now we go get all 4 suits for that card. Like if we pick 4, go get 4H 4S 4C 4D
        for (int i = 0; i < suitCount; i++){

            long nextCard = util7.makeDecimalFromIndexes(cardInd, i);//now we have all 4 cards for quads
            cards[i] = nextCard;
            allCards.remove(nextCard);

            //System.out.println("quad card: " + i + " : " + nextCard + " : " + util7.convertDecimalToShortCardName(nextCard) + " : " + allCards.size());
        }

        for (int i =  cards.length - suitCount + 1; i < cards.length; i++){ //

            long nextCard = allCards.get(rand.nextInt(allCards.size()));
            cards[i] = nextCard;
            allCards.remove(nextCard);
            //System.out.println("2 quad card: " + i + " : " + nextCard + " : " + allCards.size());
        }

        mixUpCards(cards);
        /*for(long l : cards){
            System.out.println("final quad card: " + l + " :: " + util7.convertDecimalToShortCardName(l));
        }*/

        return cards;// getRandomCertainTypeHand(7);
    }




}
