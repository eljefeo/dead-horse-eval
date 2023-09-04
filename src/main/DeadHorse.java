package main;

public class DeadHorse {


    public static int eval5(int a, int b, int c, int d, int e) {
        int f = 0x1FFF;
        int x = (a ^ b ^ c ^ d ^ e) & f, y = (a | b | c | d | e) & f, z = y ^ x, v = y & y - 1;
        if ((v &= v - 1) == 0)
            return (a + b + c + d + e - x & f) == (f & (y ^ x) << 2)
                    ? 0x1C000000 | x | z << 13 : 0x18000000 | z | x << 13; //4 of a kind or full house
        else if ((v &= v - 1) == 0)
            return z != 0 ? 0x8000000 | x | z << 13
                    : 0xC000000 | (v = ((a & b) == (a & f) ? a : (c & d) == (c & f) ? c : e) & f ^ y) | v << 13;
        else if ((v &= v - 1) == 0) return 0x4000000 | x | z << 13;
        boolean strt = 0x1F1D100 % y == 0, flsh = (a & b & c & d & e) != 0;
        return strt ? (x == 4111 ? 15 : x) | (flsh ? 0x20000000 : 0x10000000) : flsh ? 0x14000000 : x;
    }

    public static int eval5original(int a, int b, int c, int d, int e) {
        int x = (a ^ b ^ c ^ d ^ e) & 8191, y = (a | b | c | d | e) & 8191, z = y ^ x, v = y & y - 1;
        if ((v &= v - 1) == 0)
            return (a + b + c + d + e - x & 8191) == (8191 & (y ^ x) << 2)
                    ? 0x1C000000 | x | z << 13 : 0x18000000 | z | x << 13; //4 of a kind or full house
        else if ((v &= v - 1) == 0)
            return z != 0 ? 0x8000000 | x | z << 13
                    : 0xC000000 | (v = ((a & b) == (a & 8191) ? a : (c & d) == (c & 8191) ? c : e) & 8191 ^ y) | v << 13;
        else if ((v &= v - 1) == 0) return 0x4000000 | x | z << 13;
        boolean strt = 0x1F1D100 % y == 0, flsh = (a & b & c & d & e) != 0;
        return strt ? (x == 4111 ? 15 : x) | (flsh ? 0x20000000 : 0x10000000) : flsh ? 0x14000000 : x;
    }

    public static int eval5(int[] cards) {
        if (cards.length != 5) {
            throw new IllegalArgumentException("You must pass in 5 numbers and only 5 numbers");
        }
        return eval5(cards[0], cards[1], cards[2], cards[3], cards[4]);
    }

    public static int eval5WithNotes(int a, int b, int c, int d, int e) {
        //& (and) is if both bits are 1, then return 1
        //| (or) is if either of the bits are 1, then return 1
        //^ (xor) is if one bit is 1 and the other bit is 0 then return 1
        int first13Bits = 0x1FFF; //in binary this is 00001111111111111 (13 1's). This will be used to separate the card from the suit. if we OR this then we keep the card that is in bits 1-13 and lose the suit, cause the suit is bits 14-17
        int importantBitShift = 13; //we will use this to shift the important card over 13 bits when we return the result;
        int x = (a ^ b ^ c ^ d ^ e) & first13Bits; //all the cards xor'd together, and then the suits masked. We mask the suits so we just have the card (2 or Jack or 10 or Ace)
        int y = (a | b | c | d | e) & first13Bits; //all the cards or'd together, and then the suits masked. We mask the suits so we just have the card (2 or Jack or 10 or Ace)
        int z = y ^ x; //
        int v = y & y - 1; //this is used to count the bits. We will remove bits one by one, counting as we go so we can know how many bits are set. Different hands have a unique number of bits

        v &= v - 1;

        if (v == 0) {
            if ((a + b + c + d + e - x & first13Bits) == (first13Bits & (y ^ x) << 2)) {
                return 0x1C000000 | x | z << importantBitShift;
            } else {
                return 0x18000000 | z | x << importantBitShift; //4 of a kind or full house
            }
        } else if ((v &= v - 1) == 0) {
            if (z != 0) {
                return 0x8000000 | x | z << importantBitShift;
            } else {

                if ((a & b) == (a & first13Bits)) {
                    v = a;
                } else if ((c & d) == (c & first13Bits)) {
                    v = c;
                } else {
                    v = e;
                }
                v = v & first13Bits ^ y;

                return 0xC000000 | v | v << importantBitShift;
            }
        }

        int tempV = v & v - 1;
        if (tempV == 0) {
            return 0x4000000 | x | z << importantBitShift;
        }

        boolean strt = 0x1F1D100 % y == 0;
        boolean flsh = (a & b & c & d & e) != 0;

        if (strt) {

            int hh = 0;
            if (x == 0x100F) {
                hh = 0xF;
            } else {
                hh = x;
            }

            int hhh = 0;
            if (flsh) {
                hhh = 0x20000000;
            } else {
                hhh = 0x10000000;
            }

            return hh | hhh;

        } else if (flsh) {
            return 0x14000000;
        } else {
            return x;
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



