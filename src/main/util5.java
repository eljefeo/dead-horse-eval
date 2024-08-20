package main;


import java.util.*;

public class util5 extends util{

    static int total5CardHandCount = 2598960;
    static final Random rand = new Random();

    static final int numOfCardsPerHand = 5;

    static final int maxNumPlayers = 10;

    static int[] handFrequency = {
            1302540, 1098240, 123552, 54912, 10200, 5108, 3744, 624, 40
    };

    static final int[] all52CardsDecimal = new int[]{
            65537, 65538, 65540, 65544, 65552, 65568, 65600, 65664, 65792, 66048, 66560, 67584, 69632,
            32769, 32770, 32772, 32776, 32784, 32800, 32832, 32896, 33024, 33280, 33792, 34816, 36864,
            16385, 16386, 16388, 16392, 16400, 16416, 16448, 16512, 16640, 16896, 17408, 18432, 20480,
            8193, 8194, 8196, 8200, 8208, 8224, 8256, 8320, 8448, 8704, 9216, 10240, 12288
    };

    private static final Map<String, Integer> cardMap = new HashMap<>();
    static {
        for (int i = 0; i < all52CardsDecimal.length; i++){
            cardMap.put(allCardNames[i], all52CardsDecimal[i]);
        }
    }

    static final int spadeMask = 65536; //10000000000000000 17 digits
    static final int heartMask = 32768; //1000000000000000 16 digits
    static final int clubMask = 16384; //100000000000000 15 digits
    static final int diamondMask = 8192; //10000000000000 14 digits
    static final int[] suitDecimals = new int[] { diamondMask, clubMask, heartMask, spadeMask };
    static final int[] cardDecimals = new int[] { 1, 2, 4, 8, 16, 32, 64, 128, 256,
            512, 1024, 2048, 4096 };


    /*private static final Map<String, Integer> cardMap = Map.ofEntries(
            entry("2S", 65537),
            entry("3S", 65538),
            entry("4S", 65540),
            entry("5S", 65544),
            entry("6S", 65552),
            entry("7S", 65568),
            entry("8S", 65600),
            entry("9S", 65664),
            entry("TS", 65792),
            entry("JS", 66048),
            entry("QS", 66560),
            entry("KS", 67584),
            entry("AS", 69632),
            entry("2H", 32769),
            entry("3H", 32770),
            entry("4H", 32772),
            entry("5H", 32776),
            entry("6H", 32784),
            entry("7H", 32800),
            entry("8H", 32832),
            entry("9H", 32896),
            entry("TH", 33024),
            entry("JH", 33280),
            entry("QH", 33792),
            entry("KH", 34816),
            entry("AH", 36864),
            entry("2C", 16385),
            entry("3C", 16386),
            entry("4C", 16388),
            entry("5C", 16392),
            entry("6C", 16400),
            entry("7C", 16416),
            entry("8C", 16448),
            entry("9C", 16512),
            entry("TC", 16640),
            entry("JC", 16896),
            entry("QC", 17408),
            entry("KC", 18432),
            entry("AC", 20480),
            entry("2D", 8193),
            entry("3D", 8194),
            entry("4D", 8196),
            entry("5D", 8200),
            entry("6D", 8208),
            entry("7D", 8224),
            entry("8D", 8256),
            entry("9D", 8320),
            entry("TD", 8448),
            entry("JD", 8704),
            entry("QD", 9216),
            entry("KD", 10240),
            entry("AD", 12288)
    );*/

    static int kickerBitCount = 13;
    static int importantBitCount = 13;
    static int handTypeBitCount = 4;
    static int rightShiftForHandType = 26;
    static int rightShiftForimportantCards = 13;

    static int leaveHandTypeMask = 2080374784; //1111100000000000000000000000000 //didnt use the 32nd bit on the right
    static int leaveImportantBitMask = 67100672; //0000011111111111110000000000000
    static int leaveKickerBitMask = 8191; //0000000000000000001111111111111


    public static void makeAllDecimalNumsFromScratch() throws Exception {

        for(int i = 0; i < suitDecimals.length; i++){
            for(int j = 0; j < cardDecimals.length; j++){
                int newNum = makeDecimalFromIndexes(j, i);
                System.out.println("new num : " + newNum + " : " + decimalToShortCardName(newNum));
            }
        }

    }

    public static int[] makeAll52CardsDecimal() {

        int cardCount = cardDecimals.length;
        int suitCount = suitDecimals.length;
        int[] cards = new int[cardCount * suitCount];
        for (int i = 0; i < suitCount; i++) {
            for (int j = 0; j < cardCount; j++) {
                int index = (i * cardCount + j);
                cards[index] = makeDecimalFromIndexes(j, i);
            }
        }
        return cards;
    }

    public static Integer[] getRandomThisType5CardHandRandomly(int whatKind) { // 0 = high card, 1 = pair, 2 = 2pair etc..

        int counter = 0;
        int limit = 100000000;
        int batchSize = 10000;//make this many hands at a time, go through them all looking for the desired outcome (instead of making 1 hand at a time, slower)
        if (whatKind > handNames.length - 1) {
            throw new IllegalArgumentException(whatKind + " is invalid. Please pick a hand from 0 - " + (handNames.length - 1));
        }
        while (true) {

            List<Integer[]> allCards = HandMakerFiveCard.makeThisManyRandom5CardHands(10000);
            for(Integer[] hand : allCards){
                counter++;
               // int[] fiveCards = new allCards[i*5]
                int res = DeadHorse.eval5(hand[0], hand[1], hand[2], hand[3], hand[4]);
                //System.out.println("Cards: " + allCards[i*5] + ", " + allCards[i*5+1] + ", " + allCards[i*5+2] + ", " + allCards[i*5+3] + ", " + allCards[i*5+4]);
                res >>= 26;
                if (res == whatKind) {
                    System.out.println("Finally got " + handNames[res] + " after " + counter + " hands");
                    return hand;
                } else if (counter > limit) {
                    System.out.println("Did not get a " + handNames[whatKind] + " after " + limit + " hands...stopping the search so it doesnt go on forever. something doesnt seem right, it shouldnt take this long");
                    return new Integer[]{};
                }
            }

        }

    }

   /* public static int getRandomThisType5CardHand(int whatKind) { // 0 = high card, 1 = pair, 2 = 2pair etc..

        int counter = 0;
        int limit = 10000000;
        if (whatKind > handNames.length - 1) {
            throw new IllegalArgumentException(whatKind + " is invalid. Please pick a hand from 0 - " + (handNames.length - 1));
        }
        while (true) {
            counter++;
            int[] fiveCards = HandMaker.makeThisManyRandom5CardHands();
            int res = DeadHorse.eval5(fiveCards);
            int type = res >>> 26;
            if (type == whatKind) {
                System.out.println("Finally got " + handNames[type] + " after " + counter + " hands");
                return res;
            } else if (counter > limit) {
                System.out.println("Did not get a " + handNames[whatKind] + " after " + limit + " hands...stopping the search so it doesnt go on forever");
                return -1;
            }
        }

    }*/

    public static String getCardName5(int j) throws Exception {
        for(int i = 0; i< all52CardsDecimal.length; i++){
            if(all52CardsDecimal[i]==j){
                return allCardNames[i];
            }
        }
        throw new Exception("error getting name " + j);
    }


    // can send user readable strings into here like 'AS or JC or 9H or TD (ten of diamonds)'..
//capital letter for face cards and UPPER case letter for suit
    public  static int evalShortCards(String as, String bs, String cs, String ds, String es){

        //convert strings like 7H to numbers that the eval recognizes

        //return DeadHorse.eval5WithNotes(a, b, c, d, e);
        int res = DeadHorse.eval5(cardMap.get(as), cardMap.get(bs), cardMap.get(cs), cardMap.get(ds), cardMap.get(es));
        //System.out.println("humanEncodeEval3 : " + res + " : " + util.bin32(res));
        return res;
    }

    public  static int humanEncodeEvalByHand(String as, String bs, String cs, String ds, String es){

        //convert string "5H" to numbers that the eval recognizes
        char ac=as.charAt(0),bc=bs.charAt(0),cc=cs.charAt(0),dc=ds.charAt(0),ec=es.charAt(0);


        /*int a=((ac=='A'?1<<12:ac=='K'?1<<11:ac=='Q'?1<<10:ac=='J'?1<<9:ac=='T'?1<<8:1<<(ac-50))
                |((ac=as.charAt(1))=='S'?0x10000:ac=='H'?0x8000:ac=='C'?0x4000:0x2000));

        int b=((bc=='A'?1<<12:bc=='K'?1<<11:bc=='Q'?1<<10:bc=='J'?1<<9:bc=='T'?1<<8:1<<(bc-50))
                |((bc=bs.charAt(1))=='S'?0x10000:bc=='H'?0x8000:bc=='C'?0x4000:0x2000));

        int c=((cc=='A'?1<<12:cc=='K'?1<<11:cc=='Q'?1<<10:cc=='J'?1<<9:cc=='T'?1<<8:1<<(cc-50))
                |((cc=cs.charAt(1))=='S'?0x10000:cc=='H'?0x8000:cc=='C'?0x4000:0x2000));

        int d=((dc=='A'?1<<12:dc=='K'?1<<11:dc=='Q'?1<<10:dc=='J'?1<<9:dc=='T'?1<<8:1<<(dc-50))
                |((dc=ds.charAt(1))=='S'?0x10000:dc=='H'?0x8000:dc=='C'?0x4000:0x2000));

        int e=((ec=='A'?1<<12:ec=='K'?1<<11:ec=='Q'?1<<10:ec=='J'?1<<9:ec=='T'?1<<8:1<<(ec-50))
                |((ec=es.charAt(1))=='S'?0x10000:ec=='H'?0x8000:ec=='C'?0x4000:0x2000));*/
        int a = encodeShortByHand(as);
        int b = encodeShortByHand(bs);
        int c = encodeShortByHand(cs);
        int d = encodeShortByHand(ds);
        int e = encodeShortByHand(es);

        System.out.println("humanEncodeEval1: " + a + ", " + b + ", " + c + ", " + d + ", " + e);
        System.out.println("humanEncodeEval2: " + util.bin32(a) + ", " + util.bin32(b) + ", " + util.bin32(c) + ", " + util.bin32(d) + ", " + util.bin32(e));

        //return DeadHorse.eval5WithNotes(a, b, c, d, e);
        int res = DeadHorse.eval5(a, b, c, d, e);
        System.out.println("humanEncodeEval3 : " + res + " : " + util.bin32(res));
        return res;
    }

    private static int encodeShortByHand(String as) {
        char cardChar =as.charAt(0), suiteChar = as.charAt(1);

        int cardBitShift = switch (cardChar){
            case 'A' -> 12;
            case 'K' -> 11;
            case 'Q' -> 10;
            case 'J' -> 9;
            case 'T' -> 8;
            default -> cardChar - 50;
        };
        int cardBit = 1 << cardBitShift;

        int suiteBit = switch (suiteChar){
            case 'S' -> 0x10000;
            case 'H' -> 0x8000;
            case 'C' -> 0x4000;
            case 'D' -> 0x2000;
            default -> throw new Error("Invalid Suite Char : " + suiteChar);
        };
        return cardBit | suiteBit;



    }

    //give it human readable string cards, it will spit back a human readable hand type(pair, full house etc..)
    /*public static String humanEncodeFullHandEval(String as, String bs, String cs, String ds, String es){
        int res= shortCardsToLongDescription(as,bs,cs,ds,es);
        return "unique hand value = " + res + "\n"+ as + ", " + bs + ", " +cs+", " +ds+", "+es+"\n= "+ handNames[res>>26];
    }*/
    public static String decode5CardHand(int hand, boolean showDetails){
        int handType = hand >> rightShiftForHandType;

        if(showDetails)
            System.out.println("decode 5card hand type int : " + handType);

        int importantBits = (hand & leaveImportantBitMask) >> rightShiftForimportantCards;
        int kickerBits = hand & leaveKickerBitMask;

        if(showDetails) {
            System.out.println("This hand: " + hand + " :: " + util.bin32(hand) + " :: " + handType + " : " + handNames[handType]);
            System.out.println("important bits: " + importantBits + " :: " + util.bin13(importantBits));
            System.out.println("kicker bits: " + kickerBits + " :: " + util.bin13(kickerBits));
        }

        String longHandName = handNames[handType];
        List<String> importantStrings = parseHandBits(importantBits);

        if(showDetails) {
            for (String importS : importantStrings) {
                System.out.println("Important Card: " + importS);
            }
        }

        List<String> kickerStrings = parseHandBits(kickerBits);

        if(showDetails) {
            for (String kickS : kickerStrings) {
                System.out.println("Kicker Card: " + kickS);
            }
        }

        String[] cardStrings = new String[5];
        for(int i = 0; i < importantStrings.size(); i++){
            cardStrings[i] = importantStrings.get(i);
        }
        for(int i = 0; i < kickerStrings.size(); i++){
            cardStrings[i + importantStrings.size()] = kickerStrings.get(i);
        }

        if(showDetails) {
            for (String s : cardStrings) {
                System.out.println("Card String going in: " + s);
            }
        }
        String handDescription = getLongHandName(handType, cardStrings);

        if(showDetails) {
            System.out.println(handDescription);
        }
        return handDescription;
    }


    private static List<String> parseHandBits(int impKickBts) {
        List<String> strngs = new ArrayList<String>();
        int impKickCount = 12;
        while(impKickBts > 0){
            int someOne = (int) Math.pow(2,impKickCount);
            int maybe1 = impKickBts & someOne;
            if(maybe1 != 0){
                strngs.add(util.cardLongNames[impKickCount]);
                impKickBts ^= maybe1;
            }
            impKickCount--;
        }
        return strngs;
    }


    public static int longCardNameToDecimal(String cardString){

    //TODO
        // return cardMap.get(cardString);
        System.out.println("Finish developing me util5.convertHumanLongNameToDecimal");
        return 0;
    }

    public static String longCardNameToShortCardName(String cardString){
        //return cardMap.get(cardString);
        //TODO
        System.out.println("Finish developing me util5.convertHumanLongNameToShortName");
        return "";
    }

    public static int shortCardNameToDecimal(String cardString){
        return cardMap.get(cardString);
    }

    public static Integer[] shortCardNamesToDecimals(String[] cardString){
        //TODO
        System.out.println("Finish developing me util5.convertHumanShortNamesToDecimals");

        //return cardMap.get(cardString);


        return new Integer[]{cardMap.get(cardString[0]), cardMap.get(cardString[1]), cardMap.get(cardString[2]), cardMap.get(cardString[3]), cardMap.get(cardString[4])};
    }



    public static int shortCardNameToDecimalByHand(String cardString) throws Exception {
        // like AH or 5S
        if (cardString.length() != 2) {
            throw new IllegalArgumentException("Card must be 2 chars long:  " + cardString);
        }

        int cardIndex = getCardIndexChar(cardString.charAt(0));
        int suitIndex = getSuitIndexChar(cardString.charAt(1));

        return makeDecimalFromIndexes(cardIndex, suitIndex);
    }


    public static char getCardChar(int card) throws Exception {
        return util.cardChars[getCardIndexDecimal(card)];
    }

    public static int getCardIndexDecimal(int card) throws Exception {
        for (int i = 0; i < cardDecimals.length; i++) {
            if ((card & cardDecimals[i]) != 0) {
                return i;
            }
        }
        throw new Exception("Error retrieving card index for card: " + card);
    }

    public static char getSuitChar(int card) throws Exception {
        return util.suitChars[getSuitIndexDecimal(card)];
    }

    public static int getSuitIndexDecimal(int card) throws Exception {
        for (int i = 0; i < suitDecimals.length; i++) {
            if ((card & suitDecimals[i]) != 0) {
                return i;
            }
        }
        throw new Exception("Error retreiving suit index for card: " + card);
    }

    public static int makeDecimalFromIndexes(int cardIndex, int suitIndex) {
        return (1 << cardIndex) | (1 << (13 + suitIndex ) ); //(1L << (cardIndex * 3)) | (1L << ((suitIndex + 13) * 3));
    }

    public static String decimalToShortCardName(int card) throws Exception {
        return (getCardChar(card) + "" + getSuitChar(card));
    }

    public static String[] decimalsToShortCardNames(Integer[] cards) throws Exception {
        String[] cardShorts = new String[cards.length];
        for(int i = 0; i < cards.length; i++){
            cardShorts[i] = decimalToShortCardName(cards[i]);// getCardChar(card) + "" + getSuitChar(card);
        }
        return cardShorts;
    }

    public static String decimalToLongCardName(int card) throws Exception {
        return getCardLong(card) + util.OF + getSuitLong(card);
    }

    public static String getCardLong(int card) throws Exception {
        return util.cardLongNames[getCardIndexDecimal(card)];
    }

    public static String getSuitLong(int card) throws Exception {
        return util.suitLongs[getSuitIndexDecimal(card)];
    }

    public static String decimalsToLongHandDescription(Integer[] cards) {
        int res = DeadHorse.eval5(cards);
        return decode5CardHand(res, false);
    }

    public static String shortCardsToLongHandDescription(String[] someHand, boolean showDetails) {
        //String[] someHand = new String[] {"9D", "3H", "6H", "5H", "4H"};

        //System.out.println(someHand[0] + " " + someHand[1] + " " + someHand[2] + " " + someHand[3] + " " + someHand[4]);
        int res = evalShortCards(someHand[0], someHand[1], someHand[2], someHand[3], someHand[4]);
        return decode5CardHand(res, showDetails);
    }


    public static List<Integer[]> dealRandomHandsFromDeck(int playerCount) {

        if(playerCount > maxNumPlayers){
            throw new IllegalArgumentException("Cannot play a game of 5 card poker with 1 deck of 52 cards with more than " + maxNumPlayers + " players! You asked for " + playerCount);
        }

        System.out.println("Dealing " + playerCount + " hands from the deck");
        List<Integer> allCards = new ArrayList<>(Arrays.stream(all52CardsDecimal).boxed().toList());
        List<Integer[]> playerHands = new ArrayList<>();
        for (int i = 0; i < playerCount; i++){
            Integer[] cards = new Integer[numOfCardsPerHand];

            for(int j = 0; j < cards.length; j++){
                int cardIndex  = rand.nextInt(allCards.size());
                cards[j] = allCards.get(cardIndex);
                allCards.remove(cardIndex);
            }
            playerHands.add(cards);

        }

        return playerHands;

    }
}
