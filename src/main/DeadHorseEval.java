package main;

public class DeadHorseEval {
	
	

	  public static int eval(int a, int b, int c, int d, int e){
		  
			int sMask=0x1FFF;
			int xorMasked=(a^b^c^d^e)&sMask;
			int or=(a|b|c|d|e);
			int orMasked = or&sMask;
			int orXorMask = (orMasked^xorMasked);
			int cntO=orMasked;
			int cntX=xorMasked;
			int v=(cntO&=cntO-1)!=0?(cntO&=cntO-1)!=0?(cntO&=cntO-1)!=0?5:4:3:2;
			if((cntX&=cntX-1)!=0)v++;
			if((cntX&=cntX-1)!=0)v++;
			if((cntX&=cntX-1)!=0)v++;
			boolean strt=0x1F1D100%orMasked==0;
			boolean flsh=(a&b&c&d&e)!=0;
			boolean quad=(a+b+c+d+e-xorMasked&sMask)==(sMask&(orMasked^xorMasked)<<2);
			int trips=((a&b)==(a&sMask)?a:(c&d)==(c&sMask)?c:e)&sMask;
			return  
					//pair
					v==7?0x4000000|xorMasked|orXorMask<<13
					//two pair
					:v==4?0x8000000|xorMasked|orXorMask<<13
					//trips
					:v==6?0xC000000|(trips^orMasked)|trips<<13
					//straight flush
					:flsh&&strt?0x20000000|(xorMasked==0x100F?15:xorMasked)
					//flush
					:flsh?0x14000000|xorMasked
					//straight
					:strt?0x10000000|(xorMasked==0x100F?15:xorMasked)
					//quads
					:v==3?quad?0x1C000000|xorMasked|orXorMask<<13
					//full house
					:0x18000000|orXorMask|xorMasked<<13
					//high card
					:xorMasked;

		  }
}
