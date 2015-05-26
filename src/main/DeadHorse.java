package main;

public class DeadHorse {

	public static int eval5(int a, int b, int c, int d, int e){
		int x=(a^b^c^d^e)&8191;
		int y=(a|b|c|d|e)&8191;
		int z=(y^x);
		int v=y&y-1;
		v=(v&=v-1)==0?2:(v&=v-1)==0?3:(v&=v-1)==0?4:5;
		 if(v==4) return 0x4000000|x|z<<13;
		 else if(v==3)
			if(z!=0) return 0x8000000|x|z<<13;
			else return 0xC000000|(v=((a&b)==(a&8191)?a:(c&d)==(c&8191)?c:e)&8191^y)|v<<13;
		else if(v==2)
			if((a+b+c+d+e-x&8191)==(8191&(y^x)<<2)) return 0x1C000000|x|z<<13;
			else return 0x18000000|z|x<<13;
		boolean strt=0x1F1D100%y==0;
		boolean flsh=(a&b&c&d&e)!=0;
		return strt&&flsh?0x20000000|(x==4111?15:x)
				:strt?0x10000000|(x==4111?15:x):flsh?0x14000000|x:x;

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


