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

    public static int[] getRandomThisType5CardHand(int whatKind) { // 0 = high card, 1 = pair, 2 = 2pair etc..

        int counter = 0;
        int limit = 100000000;
        int batchSize = 10000;//make this many hands at a time, go through them all looking for the desired outcome (instead of making 1 hand at a time, slower)
        if (whatKind > handNames.length - 1) {
            throw new IllegalArgumentException(whatKind + " is invalid. Please pick a hand from 0 - " + (handNames.length - 1));
        }
        while (true) {

            int[] allCards = HandMaker.makeThisManyRandom5CardHands(10000);
            for(int i=0; i < batchSize; i+=5){
                counter++;
               // int[] fiveCards = new allCards[i*5]
                int res = DeadHorse.eval5(allCards[i*5], allCards[i*5+1], allCards[i*5+2], allCards[i*5+3], allCards[i*5+4]);
                //System.out.println("Cards: " + allCards[i*5] + ", " + allCards[i*5+1] + ", " + allCards[i*5+2] + ", " + allCards[i*5+3] + ", " + allCards[i*5+4]);
                res >>= 26;
                if (res == whatKind) {
                    System.out.println("Finally got " + handNames[res] + " after " + counter + " hands");
                    return new int[]{allCards[i*5], allCards[i*5+1], allCards[i*5+2], allCards[i*5+3], allCards[i*5+4]};
                } else if (counter > limit) {
                    System.out.println("Did not get a " + handNames[whatKind] + " after " + limit + " hands...stopping the search so it doesnt go on forever. something doesnt seem right, it shouldnt take this long");
                    return new int[]{};
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


        return DeadHorse.eval5WithNotes(a, b, c, d, e);
    }


    //give it human readable string cards, it will spit back a human readable hand type(pair, full house etc..)
    public static String humanEncodeFullHandEval(String as, String bs, String cs, String ds, String es){
        int res= humanEncodeEval(as,bs,cs,ds,es);
        return "unique hand value = " + res + "\n"+ as + ", " + bs + ", " +cs+", " +ds+", "+es+"\n= "+ handNames[res>>26];
    }

    public static String decode5CardHand(int hand){
        int handType = hand >> rightShiftForHandType;
        int importantBits = (hand & leaveImportantBitMask) >> rightShiftForimportantCards;
        int kickerBits = hand & leaveKickerBitMask;
        System.out.println("This hand: " + hand + " :: " + util.bin32(hand) + " :: " + handType + " : " + handNames[handType]);
        System.out.println("important bits: " + importantBits + " :: " + util.bin13(importantBits));
        System.out.println("kicker bits: " + kickerBits + " :: " + util.bin13(kickerBits));

        String longHandName = handNames[handType];
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
        String handDescription = getLongHandName(handType, cardStrings);

        System.out.println(handDescription);

        return handDescription;
    }



    public static String humanEncodeShortAndDecodeLongHand(String[] someHand) throws Exception {
        //String[] someHand = new String[] {"9D", "3H", "6H", "5H", "4H"};
        System.out.println(someHand[0] + " " + someHand[1] + " " + someHand[2] + " " + someHand[3] + " " + someHand[4]);
        int res = humanEncodeEval(someHand[0], someHand[1], someHand[2], someHand[3], someHand[4]);
        return decode5CardHand(res);
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
            case 0:
                return printLongHighCard(cards);
            case 1:
                return printLongPair(cards);
            case 2:
                return printLongTwoPair(cards);
            case 3:
                return printLongTrips(cards);
            case 4:
                return printLongStraight(cards);
            case 5:
                return printLongFlush(cards);
            case 6:
                return printLongFullHouse(cards);
            case 7:
                return printLongQuads(cards);
            case 8:
                return printLongStraightFlush(cards);

            default:
                return "ERROROORRRRORR trying to show long fun description of hand";
        }

    }


    public static String printLongHighCard(String[] cards){
        return handNames[0] + " : " + cards[0] + " " + cards[1] + " " + cards[2] + " " + cards[3] + " " + cards[4];
    }

    public static String printLongPair(String[] cards){
        return handNames[1] + OF + cards[0] + "s" + " with kickers " + cards[1] + ", " + cards[2] + ", " + cards[3];
    }

    public static String printLongTwoPair(String[] cards){
        return handNames[2] + ": " + cards[0] + "s and " + cards[1] + "s with a " + cards[2] + " kicker";
    }

    public static String printLongTrips(String[] cards){
        return handNames[3] + OF + cards[0] + "s with a " + cards[1] + " and " + cards[2] + " kicker";
    }

    public static String printLongStraight(String[] cards){
        //There is a scenario where ACE,2,3,4,5 happens. In that scenario we dont put the ACE card in the bits in the result
        // that way it wont appear higher than a 2,3,4,5,6. So here we check if that ACE is missing, and we add the word in...
        if(cards[4] == null)
            cards[4] = "Ace";
        return handNames[4] + " : " + cards[4] + " " + cards[3] + " " + cards[2] + " " + cards[1] + " " + cards[0];
    }

    public static String printLongFlush(String[] cards){
        return cards[0] + " High " + handNames[5];
    }
    public static String printLongFullHouse(String[] cards){
        return handNames[6] + " : " + cards[0] + "s full of " + cards[1] + "s";
    }

    public static String printLongQuads(String[] cards){
        return handNames[7] + OF + cards[0] + "s";
    }

    public static String printLongStraightFlush(String[] cards){
        //There is a scenario where ACE,2,3,4,5 happens. In that scenario we dont put the ACE card in the bits in the result
        // that way it wont appear higher than a 2,3,4,5,6. So here we check if that ACE is missing, and we add the word in...
        if(cards[4] == null)
            cards[4] = "Ace";
        return handNames[8] + " : " + cards[4] + " " + cards[3] + " " + cards[2] + " " + cards[1] + " " + cards[0];
    }

}
