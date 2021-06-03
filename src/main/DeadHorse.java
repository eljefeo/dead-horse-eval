package main;

public class DeadHorse {

	public static int eval5(int a,int b,int c,int d,int e){
		int x=(a^b^c^d^e)&8191,y=(a|b|c|d|e)&8191,z=y^x,v=y&y-1;
		if((v&=v-1)==0)
			return (a+b+c+d+e-x&8191)==(8191&(y^x)<<2) 
			  ?0x1C000000|x|z<<13:0x18000000|z|x<<13; //4 of a kind or full house
		else if((v&=v-1)==0)
			return z!=0?0x8000000|x|z<<13
			:0xC000000|(v=((a&b)==(a&8191)?a:(c&d)==(c&8191)?c:e)&8191^y)|v<<13;
		else if((v&=v-1)==0) return 0x4000000|x|z<<13;
		boolean strt=0x1F1D100%y==0,flsh=(a&b&c&d&e)!=0;
		return strt?(x==4111?15:x)|(flsh?0x20000000:0x10000000):flsh?0x14000000:x;
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

	public static String bin32(int i){
		return String.format("%32s", Integer.toBinaryString(i)).replace(' ', '0');
	}
	public static String bin64(long i){
		return String.format("%64s", Long.toBinaryString(i)).replace(' ', '0');
	}
		  
	
	public static int eval7n(int n0, int n1, int n2, int n3, int n4, int n5, int n6){
		
		int suitmask = 4792320;
		int cardmask = 8191;
		//int u0=n0>>14,u1=n1>>14,u2=n2>>14,u3=n3>>14,u4=n4>>14,u5=n5>>14,u6=n6>>14;
		
		//65536	 32768 	16384 8192
		
		//System.out.println((n0&8191) + " ddd " + Math.log(n0&8191)/0.6931 + " " + Math.log(2));
		
		long al = 0;
/*		al|=(n0&65536)!=0?(n0&=8191)<<3<<((int)(Math.log(n0)/0.6931)):(n0&32768)!=0?(n0&8191)<<2:(n0&16384)!=0?(n0&8191)<<1:n0;
		al|=(n1&65536)!=0?(n1&8191)<<3:(n1&32768)!=0?(n1&8191)<<2:(n1&16384)!=0?(n1&8191)<<1:n1;
		al|=(n2&65536)!=0?(n2&8191)<<3:(n2&32768)!=0?(n2&8191)<<2:(n2&16384)!=0?(n2&8191)<<1:n2;
		al|=(n3&65536)!=0?(n3&8191)<<3:(n3&32768)!=0?(n3&8191)<<2:(n3&16384)!=0?(n3&8191)<<1:n3;
		al|=(n4&65536)!=0?(n4&8191)<<3:(n4&32768)!=0?(n4&8191)<<2:(n4&16384)!=0?(n4&8191)<<1:n4;
		al|=(n5&65536)!=0?(n5&8191)<<3:(n5&32768)!=0?(n5&8191)<<2:(n5&16384)!=0?(n5&8191)<<1:n5;
		al|=(n6&65536)!=0?(n6&8191)<<3:(n6&32768)!=0?(n6&8191)<<2:(n6&16384)!=0?(n6&8191)<<1:n6;*/
		
		/*al|=(n0&65536)!=0?(n0&=8191)<<3<<((int)(Math.log(n0)/0.6931)*3)
				:(n0&32768)!=0?(n0&=8191)<<2<<((int)(Math.log(n0)/0.6931)*3)
				:(n0&=16384)!=0?(n0&8191)<<1<<((int)(Math.log(n0)/0.6931)*3):(n0&8191);*/
/*				8.6

				int log1 =(int) (Math.log(n3&8191)/0.6931)*3;
				System.out.println("log1 " + (n0&8191 )+ " " +log1);
				
				int nnt = (n3&8191) << log1;
				System.out.println("n3 " + (n3&8191) +" << " + log1+ " = " + nnt);
				*/
				
		long nn0=((long)n0&8191) <<
				( (int)(((Math.log(n0&8191)/0.6931)*3) + ((n0&65536)!=0?3 :(n0&32768)!=0?2:(n0&16384)!=0?1: 0)));
		long nn1=((long)n1&8191) <<
				( (int)(((Math.log(n1&8191)/0.6931)*3) + ((n1&65536)!=0?3 :(n1&32768)!=0?2
					:(n1&16384)!=0?1: 0)));
		long nn2=((long)n2&8191) <<
				( (int)(((Math.log(n2&8191)/0.6931)*3) + ((n2&65536)!=0?3 :(n2&32768)!=0?2
					:(n2&16384)!=0?1: 0)));
		long nn3=((long)n3&8191) <<
				( (int)(((Math.log(n3&8191)/0.6931)*3) + ((n3&65536)!=0?3 :(n3&32768)!=0?2
					:(n3&16384)!=0?1: 0)));
		long nn4=((long)n4&8191) <<
				( (int)(((Math.log(n4&8191)/0.6931)*3) + ((n4&65536)!=0?3 :(n4&32768)!=0?2
					:(n4&16384)!=0?1: 0)));
		long nn5=((long)n5&8191) <<
				( (int)(((Math.log(n5&8191)/0.6931)*3) + ((n5&65536)!=0?3 :(n5&32768)!=0?2
					:(n5&16384)!=0?1: 0)));
		long nn6=((long)n6&8191) <<
				( (int)(((Math.log(n6&8191)/0.6931)*3) + ((n6&65536)!=0?3 :(n6&32768)!=0?2
					:(n6&16384)!=0?1: 0)));

	/*			System.out.println("test shift " + bin64(512) + "\ntest shift " + bin64(512<<30)) ;
				
				System.out.println("kjj "+ nn0 + " "+bin64(nn0)+"\ndff "+nn1+ " "+bin64(nn1));
				System.out.println("kjj "+nn2+" "+bin64(nn2)+"\ndff "+nn3+" "+bin64(nn3));
				System.out.println("kjj "+nn4+" "+bin64(nn4)+"\ndff "+nn5+" "+bin64(nn5));
				System.out.println("kjj "+nn6+" "+bin64(nn6));*/
				
				al|=nn0|nn1|nn2|nn3|nn4|nn5|nn6;
				
/*						System.out.println((n0&65536)!=0?(n0&=8191)<<(3+((int)(Math.log(n0)/0.301)*3))
								:(n0&32768)!=0?(n0&=8191)<<(2+((int)(Math.log(n0)/0.301)*3))
								:(n0&=16384)!=0?(n0&8191)<<(1+((int)(Math.log(n0)/0.301)*3)):(n0&8191));

								System.out.println((n1&65536)!=0?(n1&=8191)<<(3+((int)(Math.log(n1)/0.301)*3))
								:(n1&32768)!=0?(n1&=8191)<<(2+((int)(Math.log(n1)/0.301)*3))
								:(n1&=16384)!=0?(n1&8191)<<(1+((int)(Math.log(n1)/0.301)*3)):(n1&8191));

								System.out.println((n2&65536)!=0?(n2&=8191)<<(3+((int)(Math.log(n2)/0.301)*3))
								:(n2&32768)!=0?(n2&=8191)<<(2+((int)(Math.log(n2)/0.301)*3))
								:(n2&=16384)!=0?(n2&8191)<<(1+((int)(Math.log(n2)/0.301)*3)):(n2&8191));

								System.out.println((n3&65536)!=0?(n3&=8191)<<(3+((int)(Math.log(n3)/0.301)*3))
								:(n3&32768)!=0?(n3&=8191)<<(2+((int)(Math.log(n3)/0.301)*3))
								:(n3&=16384)!=0?(n3&8191)<<(1+((int)(Math.log(n3)/0.301)*3)):(n3&8191));

								System.out.println((n4&65536)!=0?(n4&=8191)<<(3+((int)(Math.log(n4)/0.301)*3))
								:(n4&32768)!=0?(n4&=8191)<<(2+((int)(Math.log(n4)/0.301)*3))
								:(n4&=16384)!=0?(n4&8191)<<(1+((int)(Math.log(n4)/0.301)*3)):(n4&8191));

								System.out.println((n5&65536)!=0?(n5&=8191)<<(3+((int)(Math.log(n5)/0.301)*3))
								:(n5&32768)!=0?(n5&=8191)<<(2+((int)(Math.log(n5)/0.301)*3))
								:(n5&=16384)!=0?(n5&8191)<<(1+((int)(Math.log(n5)/0.301)*3)):(n5&8191));

								System.out.println((n6&65536)!=0?(n6&=8191)<<(3+((int)(Math.log(n6)/0.301)*3))
								:(n6&32768)!=0?(n6&=8191)<<(2+((int)(Math.log(n6)/0.301)*3))
								:(n6&=16384)!=0?(n6&8191)<<(1+((int)(Math.log(n6)/0.301)*3)):(n6&8191));

*/








		//System.out.println("hih " + bin64(al));
		//long alt = al%15;
		//System.out.println(al + " " + alt + " " + bin64(alt));
	 	
		
		int 		 u0=(n0&suitmask)
					,u1=(n1&suitmask)
					,u2=(n2&suitmask)
					,u3=(n3&suitmask)
					,u4=(n4&suitmask)
					,u5=(n5&suitmask)
					,u6=(n6&suitmask);
		//int allu = u0>>13|u1>>9|u2>>5|u3>>1|u4<<3|u5<<7|u6<<11;
		int allu = u0+u1+u2+u3+u4+u5+u6;
		
		
		//int u0=n0>>14,u1=n1>>14,u2=n2>>14,u3=n3>>14,u4=n4>>14,u5=n5>>14,u6=n6>>14;
		int o=(n0&=8191)|(n1&=8191)|(n2&=8191)|(n3&=8191)|(n4&=8191)|(n5&=8191)|(n6&=8191);
		int x=n0^n1^n2^n3^n4^n5^n6;
		int z=o^x, w=o&o-1;int v;
		if((w&=w-1)!=0){
			if((w&=w-1)!=0){
				if((w&=w-1)!=0){
					if((w&=w-1)!=0){
						if((w&=w-1)!=0){
							int y = o&o-1;y&=y-1;y&=y-1;y&=y-1;y&=y-1;
							int s1=o&o-1;s1&=s1-1;//highest five
							int s2=(y&y-1)^(o&o-1);//mid five
							int s3=y ^ o;//low five
							z=0;
							int g = 3;
							/*int res = (143165576 ^ allu); res&=res-1;res&=res-1;
							if(res==0)g=65536;*/
							
							//if((res&=res-1)!=0)if((res&=res-1)!=0)if((res&=res-1)!=0)if((res&=res-1)!=0)g=65536;
						/*	res = (71582788 & allu);
							if((res&=res-1)!=0)if((res&=res-1)!=0)if((res&=res-1)!=0)if((res&=res-1)!=0)g=32768;
							res = (35791394 & allu);
							if((res&=res-1)!=0)if((res&=res-1)!=0)if((res&=res-1)!=0)if((res&=res-1)!=0)g=16384;
							res = (17895697 & allu);
							if((res&=res-1)!=0)if((res&=res-1)!=0)if((res&=res-1)!=0)if((res&=res-1)!=0)g=8192;
							*/
		 
							
							
							if(g!=3){
								if(u0==g)z|=n0;if(u1==g)z|=n1;
								if(u2==g)z|=n2;if(u3==g)z|=n3;
								if(u4==g)z|=n4;if(u5==g)z|=n5;
								if(u6==g)z|=n6;
								return   0x1F00%(z&s1)==0?0x20000000|s1
										:0x1F00%(z&s2)==0?0x20000000|s2
										:0x1F00%(z&s3)==0?0x20000000|s3
										:(z&0x100F)==0x100F?0x20000000|15
										:0x14000000|z;//else return flush
							}
							return 	 0x1F00%s1==0 ? 0x10000000|s1//high Straight
									:0x1F00%s2==0 ? 0x10000000|s2//mid Straight
									:0x1F00%s3==0 ? 0x10000000|s3//low Straight
									:(o&0x100F)==0x100F ? 0x10000000|15//Low ace Straight
									: s1;//else return high card, highest 5
						}
						else{
							int y = o&o-1;y&=y-1;y&=y-1;y&=y-1;y&=y-1;
							int s1=o&o-1;//highest five
							int s2=y^o;//mid five
							z=0;
							int g = 3;
							int res = (143165576 & allu); 
							if((res&=res-1)!=0)if((res&=res-1)!=0)if((res&=res-1)!=0)if((res&=res-1)!=0)g=65536;
							res = (71582788 & allu);
							if((res&=res-1)!=0)if((res&=res-1)!=0)if((res&=res-1)!=0)if((res&=res-1)!=0)g=32768;
							res = (35791394 & allu);
							if((res&=res-1)!=0)if((res&=res-1)!=0)if((res&=res-1)!=0)if((res&=res-1)!=0)g=16384;
							res = (17895697 & allu);
							if((res&=res-1)!=0)if((res&=res-1)!=0)if((res&=res-1)!=0)if((res&=res-1)!=0)g=8192;

							
							if(g!=3){
								if(u0==g)z|=n0;if(u1==g)z|=n1;
								if(u2==g)z|=n2;if(u3==g)z|=n3;
								if(u4==g)z|=n4;if(u5==g)z|=n5;
								if(u6==g)z|=n6;
								return   (0x1F00%(z&s1))==0?0x20000000|s1
										:(0x1F00%(z&s2))==0?0x20000000|s2
										:(z&0x100F)==0x100F?0x20000000|15
										:0x14000000|z;//else return flush
							}
							if(0x1F00%s1==0) 		return 0x10000000|s1;//high Straight
							if(0x1F00%s2==0) 		return 0x10000000|s2;//low Straight
							if((o&0x100F)==0x100F) 	return 0x10000000|15;//Low ace Straight
							return 0x4000000|(x&=x-1)&x-1|z<<13;// else return pair, with top 3 kickers
							
						}
					}
					else{
						z=0;
						int g = 3;
						int res = (143165576 & allu); 
						if((res&=res-1)!=0)if((res&=res-1)!=0)if((res&=res-1)!=0)if((res&=res-1)!=0)g=65536;
						res = (71582788 & allu);
						if((res&=res-1)!=0)if((res&=res-1)!=0)if((res&=res-1)!=0)if((res&=res-1)!=0)g=32768;
						res = (35791394 & allu);
						if((res&=res-1)!=0)if((res&=res-1)!=0)if((res&=res-1)!=0)if((res&=res-1)!=0)g=16384;
						res = (17895697 & allu);
						if((res&=res-1)!=0)if((res&=res-1)!=0)if((res&=res-1)!=0)if((res&=res-1)!=0)g=8192;
						
						if(g!=3){
							if(u0==g)z|=n0;if(u1==g)z|=n1;
							if(u2==g)z|=n2;if(u3==g)z|=n3;
							if(u4==g)z|=n4;if(u5==g)z|=n5;
							if(u6==g)z|=n6;
							return   (0x1F00%(z&o))==0 ?0x20000000|o
									:(z&0x100F)==0x100F?0x20000000|15
									:0x14000000|z;//else return flush
						}
						if(0x1F00%o==0) 		return 0x10000000|o;//Straight
						if((o&0x100F)==0x100F) 	return 0x10000000|15;//Low ace Straight
						//else either 2 pair or trips:
						if(x!=o) return 0x8000000|(x&=x-1)&x-1|z<<13;//2 pair without order
						int trip=n0==n1||n0==n2?n0:n1==n2?n2:n3==n4||n3==n5?n3:n4==n5?n4:n6;
						int k = o^trip;
						return 0xC000000|(k&=k-1)&k-1|trip<<13;//trips without order
					}
				}else v=4;
			}else v=3;
		}else v=2;


		w=x;
		v+=(w&=w-1)!=0?(w&=w-1)!=0?3:2:1;
		
		
		if(v==5) return 0x8000000|(x>(z^(z&z-1))?x:(z^(z&z-1)))|(z&z-1)<<13;//three pair without order
		if(v==7)
			if(z*4+x==n0+n1+n2+n3+n4+n5+n6)return 0x1C000000|(x&=x-1)&x-1|z<<13;//normal quads, fullhouse
			else return 0x18000000|z|(n0==n1||n0==n2?n0:n1==n2?n2:n3==n4||n3==n5?n3:n4==n5?n4:n6)<<13;
		if(v==4){
			int l = n0+n1+n2+n3+n4+n5+n6;
			if(z*2+x*3==l)	 return 0x18000000|z|x<<13;//full house 
			if((o=z&z-1)*2+(z^=o)*4+x==l) return 0x1C000000|(o>x?o:x)|z<<13;
			return 0x1C000000|(z>x?z:x)|o<<13;
			//full house, trips and 2 pair, quads with a pair without order
		}
		if(v==6) return 0x18000000|(x&x-1)|x^(x&x-1)<<13;//full house, trips and trips without order
		return 0x1C000000|x|z<<13;//quads and trips without order, only thing left it could be so no if check needed
		
	}
	
	
	
	
	public static int eval7(int n0, int n1, int n2, int n3, int n4, int n5, int n6){
		int u0=n0>>14,u1=n1>>14,u2=n2>>14,u3=n3>>14,u4=n4>>14,u5=n5>>14,u6=n6>>14;
		int o=(n0&=8191)|(n1&=8191)|(n2&=8191)|(n3&=8191)|(n4&=8191)|(n5&=8191)|(n6&=8191);
		int x=n0^n1^n2^n3^n4^n5^n6;
		int z=o^x, w=o&o-1;int v;
		if((w&=w-1)!=0){
			if((w&=w-1)!=0){
				if((w&=w-1)!=0){
					if((w&=w-1)!=0){
						if((w&=w-1)!=0){
							int y = o&o-1;y&=y-1;y&=y-1;y&=y-1;y&=y-1;
							int s1=o&o-1;s1&=s1-1;//highest five
							int s2=(y&y-1)^(o&o-1);//mid five
							int s3=y ^ o;//low five
							z=0;
						/*	
							int aa = 0, bb = 0, cc = 0, dd = 0;
							if(u0==0)aa++;
							else if(u0==1)bb++;
							else if(u0==2)cc++;
							else dd++;
							if(u1==0)aa++;
							else if(u1==1)bb++;
							else if(u1==2)cc++;
							else dd++;
							if(u2==0)aa++;
							else if(u2==1)bb++;
							else if(u2==2)cc++;
							else dd++;
							if(u3==0)aa++;
							else if(u3==1)bb++;
							else if(u3==2)cc++;
							else dd++;
							if(u4==0)aa++;
							else if(u4==1)bb++;
							else if(u4==2)cc++;
							else dd++;
							if(u5==0)aa++;
							else if(u5==1)bb++;
							else if(u5==2)cc++;
							else dd++;
							if(u6==0)aa++;
							else if(u6==1)bb++;
							else if(u6==2)cc++;
							else dd++;
							*/
							//System.out.println(u0 + " " + u1 + " "+ u2 );
							int[]u=new int[5];
							u[u0]++;u[u1]++;u[u2]++;u[u3]++;u[u4]++;u[u5]++;u[u6]++;
							int g=u[0]>4?0:u[1]>4?1:u[2]>4?2:u[4]>4?4:3;
							//int g = aa>4?0:bb>4?1:cc>4?2:dd>4?4:3;
							
							if(g!=3){
								int n=0;
								if(u0==g)z|=n0;else n++;if(u1==g)z|=n1;else n++;
								if(u2==g)z|=n2;else n++;if(u3==g)z|=n3;else n++;
								if(u4==g)z|=n4;else n++;if(u5==g)z|=n5;else n++;
								if(u6==g)z|=n6;else n++;
							//	System.out.println(bin32(z));
								z&=z-1;z&=z-1;
								//System.out.println(bin32(z));
								//if(n==0){z&=z-1;z&=z-1;}else if(n==1)z&=z-1;
								return   0x1F00%(z&s1)==0?0x20000000|s1
										:0x1F00%(z&s2)==0?0x20000000|s2
										:0x1F00%(z&s3)==0?0x20000000|s3
										:(z&0x100F)==0x100F?0x20000000|15
										:0x14000000|z;//else return flush
								
							}
							return 	 0x1F00%s1==0 ? 0x10000000|s1//high Straight
									:0x1F00%s2==0 ? 0x10000000|s2//mid Straight
									:0x1F00%s3==0 ? 0x10000000|s3//low Straight
									:(o&0x100F)==0x100F ? 0x10000000|15//Low ace Straight
									: s1;//else return high card, highest 5
						}
						else{
							int y = o&o-1;y&=y-1;y&=y-1;y&=y-1;y&=y-1;
							int s1=o&o-1;//highest five
							int s2=y^o;//mid five
							z=0;
							int[]u=new int[5];
							u[u0]++;u[u1]++;u[u2]++;u[u3]++;u[u4]++;u[u5]++;u[u6]++;
							int g=u[0]>4?0:u[1]>4?1:u[2]>4?2:u[4]>4?4:3;
							if(g!=3){
								int n=0;
								if(u0==g)z|=n0;if(u1==g)z|=n1;
								if(u2==g)z|=n2;if(u3==g)z|=n3;
								if(u4==g)z|=n4;if(u5==g)z|=n5;
								if(u6==g)z|=n6;
							//	z&=z-1;
								//if(n==0){z&=z-1;z&=z-1;}else if(n==1)z&=z-1;
								return   (0x1F00%(z&s1))==0?0x20000000|s1
										:(0x1F00%(z&s2))==0?0x20000000|s2
										:(z&0x100F)==0x100F?0x20000000|15
										:0x14000000|z;//else return flush
							}
							if(0x1F00%s1==0) 		return 0x10000000|s1;//high Straight
							if(0x1F00%s2==0) 		return 0x10000000|s2;//low Straight
							if((o&0x100F)==0x100F) 	return 0x10000000|15;//Low ace Straight
							return 0x4000000|(x&=x-1)&x-1|z<<13;// else return pair, with top 3 kickers
							
						}
					}
					else{
						z=0;
						int[]u=new int[5];
						u[u0]++;u[u1]++;u[u2]++;u[u3]++;u[u4]++;u[u5]++;u[u6]++;
						int g=u[0]>4?0:u[1]>4?1:u[2]>4?2:u[4]>4?4:3;
						if(g!=3){
							int n=0;
							if(u0==g)z|=n0;if(u1==g)z|=n1;
							if(u2==g)z|=n2;if(u3==g)z|=n3;
							if(u4==g)z|=n4;if(u5==g)z|=n5;
							if(u6==g)z|=n6;
							//if(n==0){z&=z-1;z&=z-1;}else if(n==1)z&=z-1;
							return   (0x1F00%(z&o))==0 ?0x20000000|o
									:(z&0x100F)==0x100F?0x20000000|15
									:0x14000000|z;//else return flush
						}
						if(0x1F00%o==0) 		return 0x10000000|o;//Straight
						if((o&0x100F)==0x100F) 	return 0x10000000|15;//Low ace Straight
						//else either 2 pair or trips:
						if(x!=o) return 0x8000000|(x&=x-1)&x-1|z<<13;//2 pair without order
						int trip=n0==n1||n0==n2?n0:n1==n2?n2:n3==n4||n3==n5?n3:n4==n5?n4:n6;
						int k = o^trip;
						return 0xC000000|(k&=k-1)&k-1|trip<<13;//trips without order
					}
				}else v=4;
			}else v=3;
		}else v=2;


		w=x;
		v+=(w&=w-1)!=0?(w&=w-1)!=0?3:2:1;
		
		
		if(v==5) return 0x8000000|(x>(z^(z&z-1))?x:(z^(z&z-1)))|(z&z-1)<<13;//three pair without order
		if(v==7)
			if(z*4+x==n0+n1+n2+n3+n4+n5+n6)return 0x1C000000|(x&=x-1)&x-1|z<<13;//normal quads, fullhouse
			else return 0x18000000|z|(n0==n1||n0==n2?n0:n1==n2?n2:n3==n4||n3==n5?n3:n4==n5?n4:n6)<<13;
		if(v==4){
			int l = n0+n1+n2+n3+n4+n5+n6;
			if(z*2+x*3==l)	 return 0x18000000|z|x<<13;//full house 
			if((o=z&z-1)*2+(z^=o)*4+x==l) return 0x1C000000|(o>x?o:x)|z<<13;
			return 0x1C000000|(z>x?z:x)|o<<13;
			//full house, trips and 2 pair, quads with a pair without order
		}
		if(v==6) return 0x18000000|(x&x-1)|x^(x&x-1)<<13;//full house, trips and trips without order
		return 0x1C000000|x|z<<13;//quads and trips without order, only thing left it could be so no if check needed
		
	}
	
	

	public static int eval7_backup(int n0, int n1, int n2, int n3, int n4, int n5, int n6){
		int u0=n0>>14,u1=n1>>14,u2=n2>>14,u3=n3>>14,u4=n4>>14,u5=n5>>14,u6=n6>>14;
		int o=(n0&=8191)|(n1&=8191)|(n2&=8191)|(n3&=8191)|(n4&=8191)|(n5&=8191)|(n6&=8191);
		int x=n0^n1^n2^n3^n4^n5^n6;
		int z=o^x, w=o&o-1;int v;
		if((w&=w-1)!=0){
			if((w&=w-1)!=0){
				if((w&=w-1)!=0){
					if((w&=w-1)!=0){
						if((w&=w-1)!=0){
							int y = o&o-1;y&=y-1;y&=y-1;y&=y-1;y&=y-1;
							int s1=o&o-1;s1&=s1-1;//highest five
							int s2=(y&y-1)^(o&o-1);//mid five
							int s3=y ^ o;//low five
							z=0;
							int[]u=new int[5];
							u[u0]++;u[u1]++;u[u2]++;u[u3]++;u[u4]++;u[u5]++;u[u6]++;
							int g=u[0]>4?0:u[1]>4?1:u[2]>4?2:u[4]>4?4:3;
							if(g!=3){
								if(u0==g)z|=n0;if(u1==g)z|=n1;
								if(u2==g)z|=n2;if(u3==g)z|=n3;
								if(u4==g)z|=n4;if(u5==g)z|=n5;
								if(u6==g)z|=n6;
								return   0x1F00%(z&s1)==0?0x20000000|s1
										:0x1F00%(z&s2)==0?0x20000000|s2
										:0x1F00%(z&s3)==0?0x20000000|s3
										:(z&0x100F)==0x100F?0x20000000|15
										:0x14000000|z;//else return flush
							}
							return 	 0x1F00%s1==0 ? 0x10000000|s1//high Straight
									:0x1F00%s2==0 ? 0x10000000|s2//mid Straight
									:0x1F00%s3==0 ? 0x10000000|s3//low Straight
									:(o&0x100F)==0x100F ? 0x10000000|15//Low ace Straight
									: s1;//else return high card, highest 5
						}
						else{
							int y = o&o-1;y&=y-1;y&=y-1;y&=y-1;y&=y-1;
							int s1=o&o-1;//highest five
							int s2=y^o;//mid five
							z=0;
							int[]u=new int[5];
							u[u0]++;u[u1]++;u[u2]++;u[u3]++;u[u4]++;u[u5]++;u[u6]++;
							int g=u[0]>4?0:u[1]>4?1:u[2]>4?2:u[4]>4?4:3;
							if(g!=3){
								if(u0==g)z|=n0;if(u1==g)z|=n1;
								if(u2==g)z|=n2;if(u3==g)z|=n3;
								if(u4==g)z|=n4;if(u5==g)z|=n5;
								if(u6==g)z|=n6;
								return   (0x1F00%(z&s1))==0?0x20000000|s1
										:(0x1F00%(z&s2))==0?0x20000000|s2
										:(z&0x100F)==0x100F?0x20000000|15
										:0x14000000|z;//else return flush
							}
							if(0x1F00%s1==0) 		return 0x10000000|s1;//high Straight
							if(0x1F00%s2==0) 		return 0x10000000|s2;//low Straight
							if((o&0x100F)==0x100F) 	return 0x10000000|15;//Low ace Straight
							return 0x4000000|(x&=x-1)&x-1|z<<13;// else return pair, with top 3 kickers
							
						}
					}
					else{
						z=0;
						int[]u=new int[5];
						u[u0]++;u[u1]++;u[u2]++;u[u3]++;u[u4]++;u[u5]++;u[u6]++;
						int g=u[0]>4?0:u[1]>4?1:u[2]>4?2:u[4]>4?4:3;
						if(g!=3){
							if(u0==g)z|=n0;if(u1==g)z|=n1;
							if(u2==g)z|=n2;if(u3==g)z|=n3;
							if(u4==g)z|=n4;if(u5==g)z|=n5;
							if(u6==g)z|=n6;
							return   (0x1F00%(z&o))==0 ?0x20000000|o
									:(z&0x100F)==0x100F?0x20000000|15
									:0x14000000|z;//else return flush
						}
						if(0x1F00%o==0) 		return 0x10000000|o;//Straight
						if((o&0x100F)==0x100F) 	return 0x10000000|15;//Low ace Straight
						//else either 2 pair or trips:
						if(x!=o) return 0x8000000|(x&=x-1)&x-1|z<<13;//2 pair without order
						int trip=n0==n1||n0==n2?n0:n1==n2?n2:n3==n4||n3==n5?n3:n4==n5?n4:n6;
						int k = o^trip;
						return 0xC000000|(k&=k-1)&k-1|trip<<13;//trips without order
					}
				}else v=4;
			}else v=3;
		}else v=2;


		w=x;
		v+=(w&=w-1)!=0?(w&=w-1)!=0?3:2:1;
		
		
		if(v==5) return 0x8000000|(x>(z^(z&z-1))?x:(z^(z&z-1)))|(z&z-1)<<13;//three pair without order
		if(v==7)
			if(z*4+x==n0+n1+n2+n3+n4+n5+n6)return 0x1C000000|(x&=x-1)&x-1|z<<13;//normal quads, fullhouse
			else return 0x18000000|z|(n0==n1||n0==n2?n0:n1==n2?n2:n3==n4||n3==n5?n3:n4==n5?n4:n6)<<13;
		if(v==4){
			int l = n0+n1+n2+n3+n4+n5+n6;
			if(z*2+x*3==l)	 return 0x18000000|z|x<<13;//full house 
			if((o=z&z-1)*2+(z^=o)*4+x==l) return 0x1C000000|(o>x?o:x)|z<<13;
			return 0x1C000000|(z>x?z:x)|o<<13;
			//full house, trips and 2 pair, quads with a pair without order
		}
		if(v==6) return 0x18000000|(x&x-1)|x^(x&x-1)<<13;//full house, trips and trips without order
		return 0x1C000000|x|z<<13;//quads and trips without order, only thing left it could be so no if check needed
	}
	

	

	
	
	
	
	
}


