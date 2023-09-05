package main;


import java.util.ArrayList;
import java.util.List;

public class util5 extends util {

    static int total5CardHandCount = 2598960;


    static int[] handFrequency5 = {
            1302540, 1098240, 123552, 54912, 10200, 5108, 3744, 624, 40
    };

    static int[] allCardNums = new int[]{
            65537, 65538, 65540, 65544, 65552, 65568, 65600, 65664, 65792, 66048, 66560, 67584, 69632,
            32769, 32770, 32772, 32776, 32784, 32800, 32832, 32896, 33024, 33280, 33792, 34816, 36864,
            16385, 16386, 16388, 16392, 16400, 16416, 16448, 16512, 16640, 16896, 17408, 18432, 20480,
            8193, 8194, 8196, 8200, 8208, 8224, 8256, 8320, 8448, 8704, 9216, 10240, 12288
    };

    static int kickerBitCount = 13;
    static int importantBitCount = 13;
    static int handTypeBitCount = 4;
    static int rightShiftForHandType = 26;
    static int rightShiftForimportantCards = 13;

    static int leaveHandTypeMask = 2080374784; //1111100000000000000000000000000 //didnt use the 32nd bit on the right
    static int leaveImportantBitMask = 67100672; //0000011111111111110000000000000
    static int leaveKickerBitMask = 8191; //0000000000000000001111111111111


    public static void makeNewnums() {
        for (int i : allCardNums) {
            int ne = 0;
            int suit = i & 122880;
            if (suit == 65536) ne = (suit << 6) | (i & 8191);
            else if (suit == 32768) ne = (suit << 4) | (i & 8191);
            else if (suit == 16384) ne = (suit << 2) | (i & 8191);
            else ne = i;
            System.out.println(ne);
        }
    }

    public static void howLongUntilYouGetThisKindOf5CardHand(int whatKind) { // 0 = high card, 1 = pair, 2 = 2pair etc..

        int counter = 0;
        int limit = 10000000;
        if (whatKind > handNames.length - 1) {
            throw new IllegalArgumentException(whatKind + " is invalid. Please pick a hand from 0 - " + (handNames.length - 1));
        }
        while (true) {
            counter++;
            int[] fiveCards = HandMaker.getRandom5CardHand();
            int res = DeadHorse.eval5(fiveCards);
            res >>= 26;
            if (res == whatKind) {
                System.out.println("Finally got " + handNames[res] + " after " + counter + " hands");
                return;
            } else if (counter > limit) {
                System.out.println("Did not get a " + handNames[whatKind] + " after " + limit + " hands...stopping the search so it doesnt go on forever");
            }
        }

    }

    public static int make5CardHandsUntilThisType(int whatKind) { // 0 = high card, 1 = pair, 2 = 2pair etc..

        int counter = 0;
        int limit = 10000000;
        if (whatKind > handNames.length - 1) {
            throw new IllegalArgumentException(whatKind + " is invalid. Please pick a hand from 0 - " + (handNames.length - 1));
        }
        while (true) {
            counter++;
            int[] fiveCards = HandMaker.getRandom5CardHand();
            int res = DeadHorse.eval5(fiveCards);
            int type = res >> 26;
            if (type == whatKind) {
                System.out.println("Finally got " + handNames[type] + " after " + counter + " hands");
                return res;
            } else if (counter > limit) {
                System.out.println("Did not get a " + handNames[whatKind] + " after " + limit + " hands...stopping the search so it doesnt go on forever");
                return -1;
            }
        }

    }

    public static String getCardName5(int j) throws Exception {
        for(int i=0;i<allCardNums.length;i++){
            if(allCardNums[i]==j){
                return allCardNames[i];
            }
        }
        throw new Exception("error getting name " + j);
    }


    // can send user readable strings into here like 'AS or JC or 9H or TD (ten of diamonds)'..
//capital letter for face cards and UPPER case letter for suit
    public  static int humanEncodeEval(String as, String bs, String cs, String ds, String es){

        //convert string to numbers that the eval recognizes
        char ac=as.charAt(0),bc=bs.charAt(0),cc=cs.charAt(0),dc=ds.charAt(0),ec=es.charAt(0);


        int a=((ac=='A'?1<<12:ac=='K'?1<<11:ac=='Q'?1<<10:ac=='J'?1<<9:ac=='T'?1<<8:1<<(ac-50))
                |((ac=as.charAt(1))=='S'?0x10000:ac=='H'?0x8000:ac=='C'?0x4000:0x2000));

        int b=((bc=='A'?1<<12:bc=='K'?1<<11:bc=='Q'?1<<10:bc=='J'?1<<9:bc=='T'?1<<8:1<<(bc-50))
                |((bc=bs.charAt(1))=='S'?0x10000:bc=='H'?0x8000:bc=='C'?0x4000:0x2000));

        int c=((cc=='A'?1<<12:cc=='K'?1<<11:cc=='Q'?1<<10:cc=='J'?1<<9:cc=='T'?1<<8:1<<(cc-50))
                |((cc=cs.charAt(1))=='S'?0x10000:cc=='H'?0x8000:cc=='C'?0x4000:0x2000));

        int d=((dc=='A'?1<<12:dc=='K'?1<<11:dc=='Q'?1<<10:dc=='J'?1<<9:dc=='T'?1<<8:1<<(dc-50))
                |((dc=ds.charAt(1))=='S'?0x10000:dc=='H'?0x8000:dc=='C'?0x4000:0x2000));

        int e=((ec=='A'?1<<12:ec=='K'?1<<11:ec=='Q'?1<<10:ec=='J'?1<<9:ec=='T'?1<<8:1<<(ec-50))
                |((ec=es.charAt(1))=='S'?0x10000:ec=='H'?0x8000:ec=='C'?0x4000:0x2000));


        return DeadHorse.eval5(a, b, c, d, e);
    }


    //give it human readable string cards, it will spit back a human readable hand type(pair, full house etc..)
    public static String humanEncodeFullHandEval(String as, String bs, String cs, String ds, String es){
        int res= humanEncodeEval(as,bs,cs,ds,es);
        return "unique hand value = " + res + "\n"+ as + ", " + bs + ", " +cs+", " +ds+", "+es+"\n= "+ handNames[res>>26];
    }

    public static String decode5CardHand(int hand) throws Exception {
        //int[] allCards = HandMaker.makeLotsOfRandom5CardHands(1);
        //for(int c : allCards){
        //    System.out.print(getCardName5(c) + ", ");
        //}
        //int res = DeadHorse.eval5(allCards);
        //int res = make5CardHandsUntilThisType(2);
        String[] someHand = new String[] {"JS", "7C", "7H", "7S", "KD"};
        int res = humanEncodeEval(someHand[0], someHand[1], someHand[2], someHand[3], someHand[4]);
        System.out.println(someHand[0] + " " + someHand[1] + " " + someHand[2] + " " + someHand[3] + " " + someHand[4]);
        int handType = res >> rightShiftForHandType;
        int importantBits = (res & leaveImportantBitMask) >> rightShiftForimportantCards;
        int kickerBits = res & leaveKickerBitMask;
        System.out.println("This hand: " + res + " :: " + util.bin32(res) + " :: " + handNames[handType]);
        System.out.println("important bits: " + importantBits + " :: " + util.bin13(importantBits));
        System.out.println("kicker bits: " + kickerBits + " :: " + util.bin13(kickerBits));

        String longHandName = handNames[handType];
       //String[] longImportantName
        List<String> importantStrings = parseHandBits(importantBits);

        for(String importS : importantStrings){
            System.out.println("Important Card: " + importS);
        }

        List<String> kickerStrings = parseHandBits(kickerBits);
        for(String kickS : kickerStrings){
            System.out.println("Kicker Card: " + kickS);
        }

        String[] cardStrings = new String[5];
        for(int i = 0; i < importantStrings.size(); i++){
            cardStrings[i] = importantStrings.get(i);
        }
        for(int i = 0; i < kickerStrings.size(); i++){
            cardStrings[i + importantStrings.size()] = kickerStrings.get(i);
        }
        for(String s : cardStrings){
            System.out.println("Card String going in: " + s);
        }
        System.out.println(getLongHandName(handType, cardStrings));


        return "s";
    }

    private static List<String> parseHandBits(int impKickBts) {
        List<String> strngs = new ArrayList<String>();
        int impKickCount = 12;
        while(impKickBts > 0){
            int someOne = (int) Math.pow(2,impKickCount);
            int maybe1 = impKickBts & someOne;
            if(maybe1 != 0){
                strngs.add(util.cardLongs[impKickCount]);
                impKickBts ^= maybe1;
            }
            impKickCount--;
        }
        return strngs;
    }

    public static String getLongHandName(int type, String[] cards){ //probably want this String array to be int array, for now just passing the card strings like Five, or Five of Hearts or whatever
        //probably want to just pass the integer itself and have the next function figure it out, like pass the int for 2H and just have this one figure it out

        switch (type){
            case 1:
                return printLongPair(cards);
            case 2:
                return printLongTwoPair(cards);
            case 3:
                return printLongTrips(cards);

            default:
                return "ERROROORRRRORR";
        }

    }

    public static String printLongPair(String[] cards){
        String pairExplanation = handNames[1] + OF + cards[0] + "'s" + " with kickers " + cards[1] + ", " + cards[2] + ", " + cards[3];
        return pairExplanation;

    }

    public static String printLongTwoPair(String[] cards){
        String pairExplanation = handNames[2] + ": " + cards[0] + "'s and " + cards[1] + "'s with a " + cards[2] + " kicker";
        return pairExplanation;
    }

    public static String printLongTrips(String[] cards){
        String pairExplanation = handNames[3] + OF + cards[0] + "'s with a " + cards[1] + " and " + cards[2] + " kicker";
        return pairExplanation;
    }


}
