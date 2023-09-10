package main;

public class util {
    static String[] allCardNames = new String[]{
            "2s", "3s", "4s", "5s", "6s", "7s", "8s", "9s", "10s", "Js", "Qs", "Ks", "As",
            "2h", "3h", "4h", "5h", "6h", "7h", "8h", "9h", "10h", "Jh", "Qh", "Kh", "Ah",
            "2c", "3c", "4c", "5c", "6c", "7c", "8c", "9c", "10c", "Jc", "Qc", "Kc", "Ac",
            "2d", "3d", "4d", "5d", "6d", "7d", "8d", "9d", "10d", "Jd", "Qd", "Kd", "Ad"
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

    protected static String bin13(int i) {
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
}
