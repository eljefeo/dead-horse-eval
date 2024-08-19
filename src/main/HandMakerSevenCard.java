package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
    private static final List<Long[]> allHands = new ArrayList<>();
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
        long[] allSevenCardHands = EvalTestPlayground.createAllSevenCardHands();
        for(int i=0;i<allSevenCardHands.length;i+=7){

            Long[] aHand = new Long[] {allSevenCardHands[i],allSevenCardHands[i+1],
                    allSevenCardHands[i+2],allSevenCardHands[i+3],allSevenCardHands[i+4], allSevenCardHands[i+5],allSevenCardHands[i+6]};

            long res = DeadHorse7.eval7(aHand) >>> 51;

            System.out.println("cast long to int for hand type, hopefully : " + ((int) res));
            allHandsOrdered.get((int)res).add(aHand);

            allHands.add(aHand);
        }
    }


    public static List<Long[]> makeThisManyRandom7CardHands(int howMany) {
        List<Long[]> hands = new ArrayList<>();
        for(int i = 0; i < howMany; i++){
            hands.add(allHands.get(rand.nextInt(allHands.size())));
        }
        return hands;
    }

    public static void mixUpCards(long[] c) {
        int N = c.length;
        for (int i = 0; i < N; i++) {
            int r = i + (int) (Math.random() * (N - i));
            long t = c[r];
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


    public static String[] getRandomHandFromDescription(String handDescription) throws Exception {

        //int handType;
        //if(handDescription.toUpperCase().equals(util.ROYAL_FLUSH.toUpperCase())){
        //	handType = 9; //maybe can hijack some functions and use 9 as royal flush indicator
        //} else {
        int handType = util.handDescriptionToType(handDescription);
        Long[] cardsLong = getRandomCertainTypeHand(handType);
        //}

        return util7.decimalsToShortCardNames(cardsLong);
    }

    public static Long[] getRandomHand(){
        return allHands.get(rand.nextInt(allHands.size()));
    }
    public static Long[] getRandomHighCardHand(){
        return getRandomCertainTypeHand(0);
    }
    public static Long[] getRandomPairHand(){
        return getRandomCertainTypeHand(1);
    }
    public static Long[] getRandomTwoPairHand(){
        return getRandomCertainTypeHand(2);
    }
    public static Long[] getRandomTripsHand(){
        return getRandomCertainTypeHand(3);
    }
    public static Long[] getRandomStraightHand(){
        return getRandomCertainTypeHand(4);
    }
    public static Long[] getRandomFlushHand(){
        return getRandomCertainTypeHand(5);
    }
    public static Long[] getRandomFullHouseHand(){
        return getRandomCertainTypeHand(6);
    }
    public static Long[] getRandomQuadsHand(){
        return getRandomCertainTypeHand(7);
    }
    public static Long[] getRandomStraightFlushHand(){
        return getRandomCertainTypeHand(8);
    }

    public static Long[] getRandomCertainTypeHand(int type){
        List<Long[]> handList = allHandsOrdered.get(type);
        int ni = rand.nextInt(handList.size());
        //System.out.println("getRandomCertainTypeHand : " + util.handNames[type] + " random hand : " + ni + ", " + highCardHands.size());
        return handList.get(ni);
    }


}
