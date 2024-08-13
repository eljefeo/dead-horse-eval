package main;

public abstract class util {
    static String[] allCardNames = new String[]{
            //"2s", "3s", "4s", "5s", "6s", "7s", "8s", "9s", "10s", "Js", "Qs", "Ks", "As",
            "2S", "3S", "4S", "5S", "6S", "7S", "8S", "9S", "TS", "JS", "QS", "KS", "AS",
            //"2h", "3h", "4h", "5h", "6h", "7h", "8h", "9h", "10h", "Jh", "Qh", "Kh", "Ah",
            "2H", "3H", "4H", "5H", "6H", "7H", "8H", "9H", "TH", "JH", "QH", "KH", "AH",
            //"2c", "3c", "4c", "5c", "6c", "7c", "8c", "9c", "10c", "Jc", "Qc", "Kc", "Ac",
            "2C", "3C", "4C", "5C", "6C", "7C", "8C", "9C", "TC", "JC", "QC", "KC", "AC",
            //"2d", "3d", "4d", "5d", "6d", "7d", "8d", "9d", "10d", "Jd", "Qd", "Kd", "Ad"
            "2D", "3D", "4D", "5D", "6D", "7D", "8D", "9D", "TD", "JD", "QD", "KD", "AD"
    };

    static String[] handNames = {
            "High Card", "Pair", "Two Pair", "3 of a kind",
            "Straight", "Flush", "Full House", "4 of a kind", "Straight Flush"
    };
    static final char[] cardChars = new char[]{'2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'A'};
    static final String[] cardLongs = new String[]{"Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine",
            "Ten", "Jack", "Queen", "King", "Ace"};

    static final char[] suitChars = new char[]{'D', 'C', 'H', 'S'};
    static final String[] suitLongs = new String[]{"Diamonds", "Clubs", "Hearts", "Spades"};
    //static final String[] suitLongsReversed = new String[]{"Spades", "Hearts", "Clubs", "Diamonds"};

    static final String OF = " of ";

//////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static String bin(int i) {
        return String.format("%17s", Integer.toBinaryString(i)).replace(' ', '0');
    }

    public static String bin13(int i) {
        return String.format("%13s", Integer.toBinaryString(i)).replace(' ', '0');
    }

    public static String bin32(int i) {
        return String.format("%32s", Integer.toBinaryString(i)).replace(' ', '0');
    }

    public static String bin51(long l) {
        return String.format("%51s", Long.toBinaryString(l)).replace(' ', '0');
    }

    public static String bin64(long l) {
        return String.format("%64s", Long.toBinaryString(l)).replace(' ', '0');
    }



    public static int countBits(int i){
        int counter = 0;
        while(i != 0){
            i &= i-1;
            counter++;
        }
        return counter;
    }

    public static int countBits(long i){
        int counter = 0;
        while(i != 0){
            i &= i-1;
            counter++;
        }
        return counter;
    }

    public static long[] maskCards(long[] hand, long mask){
        long[] masked = new long[hand.length];
        for(int i=0; i<hand.length; i++){
            masked[i] = hand[i] & mask;
        }
        return masked;
    }

    public static int[] maskCards(int[] hand, int mask){
        int[] masked = new int[hand.length];
        for(int i=0; i<hand.length; i++){
            masked[i] = hand[i] & mask;
        }
        return masked;
    }

    public static int getPwrTwo(long n){
        double m = Math.log(n);
        double t = Math.log(2);
        return (int) ( m/t);
        //System.out.println("m: " + m + " t: " + t + " d: " + d);
    }

    public static String convertHumanShortNameToLongName(String cardString) throws Exception {
        //convert AH to Ace of Hearts or 5S to Five of Spades

        if (cardString.length() != 2) {
            throw new IllegalArgumentException("Card must be 2 chars long:  " + cardString);
        }

        int cardIndex = getCardIndexChar(cardString.charAt(0));
        int suitIndex = getSuitIndexChar(cardString.charAt(1));

        return util.cardLongs[cardIndex] + util.OF + util.suitLongs[suitIndex];
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


    public static String getLongHandName(int type, String[] cards){ //probably want this String array to be int array, for now just passing the card strings like Five, or Five of Hearts or whatever
        //probably want to just pass the integer itself and have the next function figure it out, like pass the int for 2H and just have this one figure it out

        return switch (type) {
            case 0 -> printLongHighCard(cards);
            case 1 -> printLongPair(cards);
            case 2 -> printLongTwoPair(cards);
            case 3 -> printLongTrips(cards);
            case 4 -> printLongStraight(cards);
            case 5 -> printLongFlush(cards);
            case 6 -> printLongFullHouse(cards);
            case 7 -> printLongQuads(cards);
            case 8 -> printLongStraightFlush(cards);
            default -> "ERROROORRRRORR trying to show long fun description of hand";
        };

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

    //abstract public Object makeDecimalFromIndexes(int cardIndex, int suitIndex) ;
}
