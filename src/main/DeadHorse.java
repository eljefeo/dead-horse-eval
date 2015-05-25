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
		 if(strt&&flsh) return 0x20000000|(x==4111?15:x);
		
		else if(strt) return 0x10000000|(x==4111?15:x);
		
		else if (flsh) return 0x14000000|x;
		
		else return x;

	}
	
	
	
	public static int eval7(int n0, int n1, int n2, int n3, int n4, int n5, int n6){
		int u0=(n0&0x1E000)>>14;
		int u1=(n1&0x1E000)>>14;
		int u2=(n2&0x1E000)>>14;
		int u3=(n3&0x1E000)>>14;
		int u4=(n4&0x1E000)>>14;
		int u5=(n5&0x1E000)>>14;
		int u6=(n6&0x1E000)>>14;
		int o=(n0&=8191)|(n1&=8191)|(n2&=8191)|(n3&=8191)|(n4&=8191)|(n5&=8191)|(n6&=8191);
		int x=n0^n1^n2^n3^n4^n5^n6, z=o^x, v=1, w=o&o-1;
		v+=(w&=w-1)!=0?(w&=w-1)!=0?(w&=w-1)!=0?(w&=w-1)!=0?(w&=w-1)!=0?6:5:4:3:2:1;		


		if(v==7){
			int y = o&o-1;y&=y-1;y&=y-1;y&=y-1;y&=y-1;
			
			//straight
			int s1=o&o-1;s1&=s1-1;//highest five
			int s2=(y&y-1)^(o&o-1);//mid five
			int s3=y ^ o;//low five
			//flush
			int so=0;
			int[]u=new int[5];
			u[u0]++;u[u1]++;u[u2]++;u[u3]++;u[u4]++;u[u5]++;u[u6]++;
			int g=u[0]>4?0:u[1]>4?1:u[2]>4?2:u[4]>4?4:3;
			if(g!=3){
				if(u0==g)so|=n0;if(u1==g)so|=n1;
				if(u2==g)so|=n2;if(u3==g)so|=n3;
				if(u4==g)so|=n4;if(u5==g)so|=n5;
				if(u6==g)so|=n6;
				return   (0x1F00%(so&s1))==0?0x20000000|s1
						:(0x1F00%(so&s2))==0?0x20000000|s2
						:(0x1F00%(so&s3))==0?0x20000000|s3
						:(so&0x100F)==0x100F?0x20000000|15
						:0x14000000|so;//else return flush
			}
			if(0x1F00%s1==0) 		return 0x10000000|s1;//high Straight
			if(0x1F00%s2==0) 		return 0x10000000|s2;//mid Straight
			if(0x1F00%s3==0) 		return 0x10000000|s3;//low Straight
			if((o&0x100F)==0x100F) 	return 0x10000000|15;//Low ace Straight
			
		}
		
		if(v==6){
			int y = o&o-1;y&=y-1;y&=y-1;y&=y-1;y&=y-1;
			
			//straight
			int s1=o&o-1;//highest five
			int s2=y^o;//mid five
			//flush
			int so=0;
			int[]u = new int[5];
			u[u0]++;u[u1]++;u[u2]++;u[u3]++;u[u4]++;u[u5]++;u[u6]++;
			int g=u[0]>4?0:u[1]>4?1:u[2]>4?2:u[4]>4?4:3;
			if(g!=3){
				if(u0==g)so|=n0;if(u1==g)so|=n1;
				if(u2==g)so|=n2;if(u3==g)so|=n3;
				if(u4==g)so|=n4;if(u5==g)so|=n5;
				if(u6==g)so|=n6;
				return   (0x1F00%(so&s1))==0?0x20000000|s1
						:(0x1F00%(so&s2))==0?0x20000000|s2
						:(so&0x100F)==0x100F?0x20000000|15
						:0x14000000|so;//else return flush
			}
			if(0x1F00%s1==0) 	return 0x10000000|s1;//high Straight
			if(0x1F00%s2==0) 	return 0x10000000|s2;//low Straight
			if((o&0x100F)==0x100F) 	return 0x10000000|15;//Low ace Straight
		}

		if(v==5){
			//flush
			int so=0;
			int[]u = new int[5];
			u[u0]++;u[u1]++;u[u2]++;u[u3]++;u[u4]++;u[u5]++;u[u6]++;
			int g=u[0]>4?0:u[1]>4?1:u[2]>4?2:u[4]>4?4:3;
			if(g!=3){
				if(u0==g)so|=n0;if(u1==g)so|=n1;
				if(u2==g)so|=n2;if(u3==g)so|=n3;
				if(u4==g)so|=n4;if(u5==g)so|=n5;
				if(u6==g)so|=n6;
				return   (0x1F00%(so&o))==0 ?0x20000000|o
						:(so&0x100F)==0x100F?0x20000000|15
						:0x14000000|so;//else return flush
			}
			if(0x1F00%o==0) 		return 0x10000000|o;//Straight
			if((o&0x100F)==0x100F) 	return 0x10000000|15;//Low ace Straight
		}
		
		
		int xx=x;
		v+=(xx&=xx-1)!=0?(xx&=xx-1)!=0?(xx&=xx-1)!=0?(xx&=xx-1)!=0
		?(xx&=xx-1)!=0?(xx&=xx-1)!=0?7:6:5:4:3:2:1;
		
		if(v==14) return (o&=o-1)&o-1;//high card without order
		if(v==11) return 0x4000000|(x&=x-1)&x-1|z<<13;//pair without order
		if(v==8)  return 0x8000000|(x&=x-1)&x-1|z<<13;//2 pair without order
		if(v==5)  return 0x8000000|(x>(z^(z&z-1))?x:(z^(z&z-1)))|(z&z-1)<<13;//three pair without order
		

		if(v==10){
			//trips without order
			int trip=n0==n1||n0==n2?n0:n1==n2?n2:n3==n4||n3==n5?n3:n4==n5?n4:n6;
			int k = o^trip;
			return 0xC000000|(k&=k-1)&k-1|trip<<13;
		}
		
		if(v==7){ 
			//quads
			int l = n0+n1+n2+n3+n4+n5+n6;
			if(z*4+x==l)return 0x1C000000|(x&=x-1)&x-1|z<<13;
			//fullhouse
			
			int j=x;
			int c1=(j&=j-1)&j-1;//highest bit of the 3
			int c2=j^j&j-1;//mid bit
			int c3=j^x;//low bit
			if(c1*3+z*2+c2+c3==l)return 0x18000000|z|c1<<13;
			if(c2*3+z*2+c1+c3==l)return 0x18000000|z|c2<<13;
			return 0x18000000|z|c3<<13;
			//normal quads, normal full house: without order
			
			}
		
		if(v==4){
			int l = n0+n1+n2+n3+n4+n5+n6;
			if(z*2+x*3==l) return 0x18000000|z|x<<13;//full house 
			int p1= z&z-1,p2=z^p1;
			return p1*2+p2*4+x==l?0x1C000000|(p1>x?p1:x)|p2<<13:0x1C000000|(p2>x?p2:x)|p1<<13;
			//full house, trips and 2 pair, quads with a pair without order
	}
		if(v==6) return 0x18000000|(x&x-1)|x^(x&x-1)<<13;//full house, trips and trips without order
		if(v==3) return 0x1C000000|x|z<<13;//quads and trips without order
		
		return 0;//should never come to this
	}
	
	
	
	public static void sort7(int n[]){
		int t=0;
		if(n[2]>n[1]){t=n[2];n[2]=n[1];n[1]=t;}if(n[4]>n[3]){t=n[4];n[4]=n[3];n[3]=t;}
		if(n[6]>n[5]){t=n[6];n[6]=n[5];n[5]=t;}if(n[2]>n[0]){t=n[2];n[2]=n[0];n[0]=t;}
		if(n[5]>n[3]){t=n[5];n[5]=n[3];n[3]=t;}if(n[6]>n[4]){t=n[6];n[6]=n[4];n[4]=t;}		
		if(n[1]>n[0]){t=n[1];n[1]=n[0];n[0]=t;}if(n[5]>n[4]){t=n[5];n[5]=n[4];n[4]=t;}
		if(n[6]>n[2]){t=n[6];n[6]=n[2];n[2]=t;}if(n[4]>n[0]){t=n[4];n[4]=n[0];n[0]=t;}
		if(n[5]>n[1]){t=n[5];n[5]=n[1];n[1]=t;}if(n[3]>n[0]){t=n[3];n[3]=n[0];n[0]=t;}
		if(n[5]>n[2]){t=n[5];n[5]=n[2];n[2]=t;}if(n[3]>n[1]){t=n[3];n[3]=n[1];n[1]=t;}
		if(n[4]>n[2]){t=n[4];n[4]=n[2];n[2]=t;}if(n[3]>n[2]){t=n[3];n[3]=n[2];n[2]=t;}
	}
	
	public static void sort7b(int n[], int[]u){
		int t=0;
		if(n[2]>n[1]){t=n[2];n[2]=n[1];n[1]=t;t=u[2];u[2]=u[1];u[1]=t;}
		if(n[4]>n[3]){t=n[4];n[4]=n[3];n[3]=t;t=u[4];u[4]=u[3];u[3]=t;}
		if(n[6]>n[5]){t=n[6];n[6]=n[5];n[5]=t;t=u[6];u[6]=u[5];u[5]=t;}
		if(n[2]>n[0]){t=n[2];n[2]=n[0];n[0]=t;t=u[2];u[2]=u[0];u[0]=t;}
		if(n[5]>n[3]){t=n[5];n[5]=n[3];n[3]=t;t=u[5];u[5]=u[3];u[3]=t;}
		if(n[6]>n[4]){t=n[6];n[6]=n[4];n[4]=t;t=u[6];u[6]=u[4];u[4]=t;}		
		if(n[1]>n[0]){t=n[1];n[1]=n[0];n[0]=t;t=u[1];u[1]=u[0];u[0]=t;}
		if(n[5]>n[4]){t=n[5];n[5]=n[4];n[4]=t;t=u[5];u[5]=u[4];u[4]=t;}
		if(n[6]>n[2]){t=n[6];n[6]=n[2];n[2]=t;t=u[6];u[6]=u[2];u[2]=t;}
		if(n[4]>n[0]){t=n[4];n[4]=n[0];n[0]=t;t=u[4];u[4]=u[0];u[0]=t;}
		if(n[5]>n[1]){t=n[5];n[5]=n[1];n[1]=t;t=u[5];u[5]=u[1];u[1]=t;}
		if(n[3]>n[0]){t=n[3];n[3]=n[0];n[0]=t;t=u[3];u[3]=u[0];u[0]=t;}
		if(n[5]>n[2]){t=n[5];n[5]=n[2];n[2]=t;t=u[5];u[5]=u[2];u[2]=t;}
		if(n[3]>n[1]){t=n[3];n[3]=n[1];n[1]=t;t=u[3];u[3]=u[1];u[1]=t;}
		if(n[4]>n[2]){t=n[4];n[4]=n[2];n[2]=t;t=u[4];u[4]=u[2];u[2]=t;}
		if(n[3]>n[2]){t=n[3];n[3]=n[2];n[2]=t;t=u[3];u[3]=u[2];u[2]=t;}
	}
	
	public static int[] sort7bb(int n0, int n1, int n2, int n3, int n4, int n5, int n6, int u0, int u1, int u2, int u3, int u4, int u5, int u6 ){
		int t=0;
		if(n2>n1){t=n2;n2=n1;n1=t;t=u2;u2=u1;u1=t;}
		if(n4>n3){t=n4;n4=n3;n3=t;t=u4;u4=u3;u3=t;}
		if(n6>n5){t=n6;n6=n5;n5=t;t=u6;u6=u5;u5=t;}
		if(n2>n0){t=n2;n2=n0;n0=t;t=u2;u2=u0;u0=t;}
		if(n5>n3){t=n5;n5=n3;n3=t;t=u5;u5=u3;u3=t;}
		if(n6>n4){t=n6;n6=n4;n4=t;t=u6;u6=u4;u4=t;}		
		if(n1>n0){t=n1;n1=n0;n0=t;t=u1;u1=u0;u0=t;}
		if(n5>n4){t=n5;n5=n4;n4=t;t=u5;u5=u4;u4=t;}
		if(n6>n2){t=n6;n6=n2;n2=t;t=u6;u6=u2;u2=t;}
		if(n4>n0){t=n4;n4=n0;n0=t;t=u4;u4=u0;u0=t;}
		if(n5>n1){t=n5;n5=n1;n1=t;t=u5;u5=u1;u1=t;}
		if(n3>n0){t=n3;n3=n0;n0=t;t=u3;u3=u0;u0=t;}
		if(n5>n2){t=n5;n5=n2;n2=t;t=u5;u5=u2;u2=t;}
		if(n3>n1){t=n3;n3=n1;n1=t;t=u3;u3=u1;u1=t;}
		if(n4>n2){t=n4;n4=n2;n2=t;t=u4;u4=u2;u2=t;}
		if(n3>n2){t=n3;n3=n2;n2=t;t=u3;u3=u2;u2=t;}
		return new int[]{n0,n1,n2,n3,n4,n5,n6,u0,u1,u2,u3,u4,u5,u6};
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
	public static String bin(int i){
		return String.format("%17s", Integer.toBinaryString(i)).replace(' ', '0');
	}
}


