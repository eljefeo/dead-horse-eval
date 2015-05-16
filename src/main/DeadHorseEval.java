package main;

public class DeadHorseEval {

	  public static int eval(int u0, int u1,int u2,int u3, int u4){
			int j=0x1FFF;
			int e=(u0^u1^u2^u3^u4)&j;
			int f=(u0|u1|u2|u3|u4);
			int r = f&j;
			int n=r;
			int a=e;
			int v=(n&=n-1)!=0?(n&=n-1)!=0?(n&=n-1)!=0?5:4:3:2;
			if((a&=a-1)!=0)v++;
			if((a&=a-1)!=0)v++;
			if((a&=a-1)!=0)v++;
			boolean s=0x1F1D100%r==0;
			boolean h=0x10000%(f&~j)==0;
			boolean q=(u0+u1+u2+u3+u4-e&j)==(j&(r^e)<<2);
			return v==7?1:v==4?2:v==6?3:h&&s?8:h?5:s?4:v==3?q?7:6:0;
		  }
}
