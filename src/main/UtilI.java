package main;

public interface UtilI {

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
    // static final String[] suitLongsReversed = new String[]{"Spades", "Hearts", "Clubs", "Diamonds"};

    static final String OF = " of ";
    public Object makeDecimalFromIndexes(int cardIndex, int suitIndex) ;
}
