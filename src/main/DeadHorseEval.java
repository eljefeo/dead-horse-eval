package main;

public class DeadHorseEval {
	
	public static int eval(int a, int b, int c, int d, int e){
		
		int xor=(a^b^c^d^e)&0x1FFF, or=(a|b|c|d|e)&0x1FFF, orXxor=(or^xor);
		boolean strt=0x1F1D100%or==0, flsh=(a&b&c&d&e)!=0;
		if(strt&&flsh)return 0x20000000|(xor==0x100F?15:xor);
		else if(strt)return 0x10000000|(xor==0x100F?15:xor);
		else if(flsh)return 0x14000000|xor;
		int v=or&or-1;v=(v&=v-1)==0?2:(v&=v-1)==0?3:(v&=v-1)==0?4:5;
		if(v==3)
			if(orXxor==0){
				v=((a&b)==(a&0x1FFF)?a:(c&d)==(c&0x1FFF)?c:e)&0x1FFF;
				return 0xC000000|(v^or)|v<<13;
			}
			else return 0x8000000|xor|orXxor<<13;
		else return
			  v==4?0x4000000|xor|orXxor<<13
			: v==2?(a+b+c+d+e-xor&0x1FFF)==(0x1FFF&(or^xor)<<2)
			?0x1C000000|xor|orXxor<<13:0x18000000|orXxor|xor<<13
			:xor;
		  }
	

}
