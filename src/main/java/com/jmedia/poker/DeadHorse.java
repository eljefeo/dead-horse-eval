package com.jmedia.poker;

import java.util.List;

public class DeadHorse {

//
    public static int eval5(int a, int b, int c, int d, int e) {
        int m = 0x1FFF;
        int x = (a ^ b ^ c ^ d ^ e) & m, y = (a | b | c | d | e) & m, z = y ^ x, v = y & y - 1;
        if ((v &= v - 1) == 0)
            return (a + b + c + d + e - x & m) == (m & (y ^ x) << 2)
                    ? 0x1C000000 | x | z << 13 : 0x18000000 | z | x << 13; //4 of a kind or full house
        else if ((v &= v - 1) == 0)
            return z != 0 ? 0x8000000 | x | z << 13
                    : 0xC000000 | (v = (((a & b & m) != 0 || (a & c & m) != 0 ? a : (b & c & m) != 0 ? b : e) & m)) ^ y | (v << 13);
        else if ((v &= v - 1) == 0) return 0x4000000 | x | z << 13;
        boolean s = 0x1F1D100 % y == 0, f = (a & b & c & d & e) != 0;
        return s ? (x == 0x100F ? 15 : x) | (f ? 0x20000000 : 0x10000000) : f ? 0x14000000 | x : x;
    }


    public static int eval5(int[] cards) {
        /*if (cards.length != 5) {
            throw new IllegalArgumentException("You must pass in 5 and only 5 numbers");
        }*/
        return eval5(cards[0], cards[1], cards[2], cards[3], cards[4]);
    }

    public static int eval5(Integer[] cards) {
        /*if (cards.length != 5) {
            throw new IllegalArgumentException("You must pass in 5 and only 5 numbers");
        }*/
        return eval5(cards[0], cards[1], cards[2], cards[3], cards[4]);
    }

    public static int eval5(List<Integer> cards) {
        /*if (cards == null || cards.size() != 5) {
            throw new IllegalArgumentException("You must pass in 5 and only 5 numbers");
        }*/
        return eval5(cards.get(0), cards.get(1), cards.get(2), cards.get(3), cards.get(4));
    }

    public static int eval5(PokerHand hand) {

        if(hand != null){
            List<String> cards = hand.getCards();
            if(cards != null && cards.size() == Util5.numOfCardsPerHand){
                //return eval5(cards.get(0), cards.get(1), cards.get(2), cards.get(3), cards.get(4));
                return eval5(Util5.shortCardNamesToDecimals(cards));
            } else {
                if(cards != null){
                    System.out.println(cards);
                }
                throw new IllegalArgumentException("Invalid Cards");
            }
        } else {
            throw new IllegalArgumentException("Cards are null");
        }

    }



    public static int eval5WithNotes(int a, int b, int c, int d, int e) {
        //& (and) is if both bits are 1, then return 1
        //| (or) is if either of the bits are 1, then return 1
        //^ (xor) is if one bit is 1 and the other bit is 0 then return 1
        int first13Bits = 0x1FFF; //in binary this is 00001111111111111 (13 1's). This will be used to separate the card from the suit. if we AND (&) this then we keep the card that is in bits 1-13 and lose the suit, cause the suit is bits 14-17
        int importantBitShift = 13; //we will use this to shift the important card over 13 bits when we return the result;
        int xor = (a ^ b ^ c ^ d ^ e) & first13Bits; //all the cards xor'd together, and then the suits masked. We mask the suits so we just have the card (2 or Jack or 10 or Ace)
        int or = (a | b | c | d | e) & first13Bits; //all the cards or'd together, and then the suits masked. We mask the suits so we just have the card (2 or Jack or 10 or Ace)
        int orXorXor = or ^ xor; //
        int bitCntr = or & or - 1; //this is used to count the bits. We will remove bits one by one, counting as we go so we can know how many bits are set.
        // Different hands have a unique number of bits, there will always be between 2 and 5 unique bits.
        //for example: four of a kind or a fullhouse have 2 unique bits/cards, a straight has 5 unique cards
        //since minimum there will be 2 bits, we start by taking away the lowest bit by doing or & or - 1.
        //we imagine the bit count is 1 at this point...

        bitCntr &= bitCntr - 1; //take away the second bit

        if (bitCntr == 0) { //if we have no bits left, that means we had 2 bits. Only 4of a kind and fullhouse have only 2 bits
            if ((a + b + c + d + e - xor & first13Bits) == (first13Bits & (or ^ xor) << 2)) {
                System.out.println("Returning here 4 of a kind "+ Util.handNames[(0x1C000000 >> 26)]);
                return 0x1C000000 | xor | orXorXor << importantBitShift;
            } else {
                System.out.println("Returning here Full house "+ Util.handNames[(0x18000000 >> 26)]);
                return 0x18000000 | orXorXor | xor << importantBitShift; //4 of a kind or full house
            }
        } else if ((bitCntr &= bitCntr - 1) == 0) { //take away the 3rd bit.. 2 pair or 3 of a kind would have 3 bits like we see here.
            if (orXorXor != 0) {
                System.out.println("Returning here Two Pair "+ Util.handNames[(0x8000000 >> 26)]);
                return 0x8000000 | xor | orXorXor << importantBitShift;
            } else {

                //here we are just looking for the card that has a duplicate, we know there are 3 of some card, and the other 2 cards are unique.
                //we also need to mask the suits, so we are just comparing the card value without the suits to see if they are the same.
                if ((a & b & first13Bits) != 0 || (a & c & first13Bits) != 0) {
                    System.out.println("a=" + a + " b=" + b + " c=" + c + " d=" + d + " e=" + e);
                    System.out.println("returning 3 kind of a : " + a + " : a&b = " + (a&b) + " .. a&c = " + (a&c));
                    bitCntr = a;
                } else if ((b & c & first13Bits) != 0) {
                    System.out.println("returning 3 kind of b : " + b);
                    bitCntr = b;
                } else {
                    System.out.println("returning 3 kind of e : " + e);
                    bitCntr = e;
                }
                bitCntr = bitCntr & first13Bits;// here we get rid of the suits;

                // (bitCntr^or) This will leave us with the 'other' two cards (not the triple card)
                // bitCntr<<13 this is the triple card, so we move this card 13 to the left so it is the important card
                System.out.println("returning here trips " + Util.handNames[(0xC000000 >> 26)]);
                return 0xC000000|(bitCntr^or)|(bitCntr<<13);

                //3 of a kind works like this
                // a==b||a==c ? a : b==c  ? b : e
            /*  Heres the 10 different ways the trip card can be in the 5 cards: (5 choose 3)
                we can see that when a=b or a=c, or b=c this takes care of 7/10 options.
                and the 3 options that are left all have the last card as the trip card
                01110   b=c
                01011   last card
                11010   a=b
                11100   a=b
                11001   a=b
                10110   a=c
                10011   last card
                10101   a=c
                01101   b=c
                00111   last card


             */
            }
        }

        int tempV = bitCntr & bitCntr - 1; //take away a 4th bit.. only a pair could be here if we had only 4 unique bits
        if (tempV == 0) {
            System.out.println("Returning here Pair "+ Util.handNames[(0x4000000 >> 26)]);
            return 0x4000000 | xor | orXorXor << importantBitShift;

        }

        //if we get to this point, that means we had 5 unique bits.
        // So that leaves us with a straight, a flush, straight-flush, or just a high card hand
        boolean straight = 0x1F1D100 % or == 0;
        boolean flush = (a & b & c & d & e) != 0;

        if (straight) {

            //this is to check if the straight is a 2,3,4,5,ACE
            //if this is the case, when we put the ACE in the important bits section
                // it makes it seem like this straight is better than a 6 high straight, for example. When it is not...
            //so what we do to get around that is we just omit the ACE altogether from important bits
            //yes this means the imp bits only have the 2,3,4,5 and is missing the ace in this one single type of hand
            //but we can still infer it later if we wanted to, since we know its a straight, and the bits are 2,3,4,5
            int straightCards = 0;
            if (xor == 0x100F) { // this is a straight ace,2,3,4,5 = 1000000001111
                straightCards = 0xF; // this is just bits 0001111 (meaning cards 2,3,4,5) ... we omit the ace
            } else {
                straightCards = xor; //else just use whatever straight this is..
            }

            int typeOfHand = 0;
            if (flush) {
                System.out.println("Returning here Straight Flush!");
                typeOfHand = 0x20000000; //this means this hand is a straight AND a flush, a STRAIGHT FLUSH!!!
            } else {
                System.out.println("Returning here normal Straight !");
                typeOfHand = 0x10000000; //this just means this hand is a normal straight
            }
            System.out.println("Returning here Straight or straightFlush");
            return straightCards | typeOfHand;
            //straightCards are the 5 (or 4 if ace,2,3,4,5) cards, typeOfHand is whether it's a straight or straight flush

        } else if (flush) {
            System.out.println("Returning here Flush "+ Util.handNames[(0x14000000 >> 26)]);
            return 0x14000000 | xor; //this is just the type of hand for Flush, and xor are just the 5 cards (we could use OR cards here too, same result)
        } else {
            System.out.println("Returning high card hand here " + Util.handNames[0]);
            return xor;
            //this is just High card, the lowest type. So we dont even put a hand type, leave it as 0.
            //and just return the 5 cards as is.
        }
    }

	/*
 	//So ugly its beautiful
public static int eval5(int a, int b, int c, int d, int e){
	int x=(a^b^c^d^e)&0x1FFF,y=(a|b|c|d|e)&0x1FFF,z=(y^x),v=y&y-1;
	boolean strt=0x1F1D100%y==0,flsh=(a&b&c&d&e)!=0;
	return (v=(v&=v-1)==0?2:(v&=v-1)==0?3:(v&=v-1)==0?4:5)==3?z==0
		?0xC000000|(v=((a&b)==(a&0x1FFF)?a:(c&d)==(c&0x1FFF)?c:e)&0x1FFF^y)|v<<13
		:0x8000000|x|z<<13:v==4?0x4000000|x|z<<13
		:v==2?(a+b+c+d+e-x&0x1FFF)==(0x1FFF&(y^x)<<2)
		?0x1C000000|x|z<<13:0x18000000|z|x<<13
		:strt&&flsh?0x20000000|(x==0x100F?15:x)
		:strt?0x10000000|(x==0x100F?15:x):flsh?0x14000000|x:x;
}
 */


}



