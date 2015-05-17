package main;

public class DeadHorseEval {

	  public static int eval(int a, int b, int c, int d, int e){
			int sMask=0x1FFF;
			int xorMasked=(a^b^c^d^e)&sMask;
			int or=(a|b|c|d|e);
			int orMasked = or&sMask;
			int cntO=orMasked;
			int cntX=xorMasked;
			int v=(cntO&=cntO-1)!=0?(cntO&=cntO-1)!=0?(cntO&=cntO-1)!=0?5:4:3:2;
			if((cntX&=cntX-1)!=0)v++;
			if((cntX&=cntX-1)!=0)v++;
			if((cntX&=cntX-1)!=0)v++;
			boolean strt=0x1F1D100%orMasked==0;
			boolean flsh = (a&b&c&d&e)!=0;
			boolean quad=(a+b+c+d+e-xorMasked&sMask)==(sMask&(orMasked^xorMasked)<<2);
			return v==7?1:v==4?2:v==6?3:flsh&&strt?8:flsh?5:strt?4:v==3?quad?7:6:0;
			
		  }
}
