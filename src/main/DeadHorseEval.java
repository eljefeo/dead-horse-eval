package main;

public class DeadHorseEval {

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
		
		else if(strt) 	return 0x10000000|(x==4111?15:x);
		
		else if (flsh) 	return 0x14000000|x;
		
		else return x;

	}
	
	public static int eval7(int[]n){
		int[] u = {(n[0]&0x1E000)>>14,(n[1]&0x1E000)>>14,(n[2]&0x1E000)>>14,(n[3]&0x1E000)>>14,(n[4]&0x1E000)>>14,(n[5]&0x1E000)>>14,(n[6]&0x1E000)>>14};
		n[0]&=8191;n[1]&=8191;n[2]&=8191;n[3]&=8191;n[4]&=8191;n[5]&=8191;n[6]&=8191;
		

		boolean quads = false;
		int o = n[0]|n[1]|n[2]|n[3]|n[4]|n[5]|n[6];
		int x = n[0]^n[1]^n[2]^n[3]^n[4]^n[5]^n[6];
		
		System.out.println("or " + bin(o));
		System.out.println("xor " + bin(x));

		sort7b(n,u);
		
		System.out.println(n[0] + " : " + u[0]);
		System.out.println(n[1] + " : " + u[1]);
		System.out.println(n[2] + " : " + u[2]);
		System.out.println(n[3] + " : " + u[3]);
		System.out.println(n[4] + " : " + u[4]);
		System.out.println(n[5] + " : " + u[5]);
		System.out.println(n[6] + " : " + u[6]);
		
		//flush
		int[]fn = new int[5];
		fn[u[0]]++;fn[u[1]]++;fn[u[2]]++;fn[u[3]]++;fn[u[4]]++;fn[u[5]]++;fn[u[6]]++;
		if(fn[0]>4){
			System.out.println("Diamond Flush");
			int so = 0,cnt=0;
			if(u[0]==0){cnt++;so|=n[0];}
			if(u[1]==0){cnt++;so|=n[0];}
			if(u[2]==0){cnt++;so|=n[0];}
			if(u[3]==0){cnt++;so|=n[0];}
			if(u[4]==0){cnt++;so|=n[0];}
			if(u[5]==0&&cnt!=5){cnt++;so|=n[0];}
			if(u[6]==0&&cnt!=5){cnt++;so|=n[0];}
			return 0x14000000|so;
		}
		if(fn[1]>4){
			System.out.println("Club Flush");
			int so = 0,cnt=0;
			if(u[0]==1){cnt++;so|=n[0];}
			if(u[1]==1){cnt++;so|=n[0];}
			if(u[2]==1){cnt++;so|=n[0];}
			if(u[3]==1){cnt++;so|=n[0];}
			if(u[4]==1){cnt++;so|=n[0];}
			if(u[5]==1&&cnt!=5){cnt++;so|=n[0];}
			if(u[6]==1&&cnt!=5){cnt++;so|=n[0];}
			return 0x14000000|so;
			
		}
		if(fn[2]>4){
			System.out.println("Heart Flush");
			int so = 0,cnt=0;
			if(u[0]==2){cnt++;so|=n[0];}
			if(u[1]==2){cnt++;so|=n[0];}
			if(u[2]==2){cnt++;so|=n[0];}
			if(u[3]==2){cnt++;so|=n[0];}
			if(u[4]==2){cnt++;so|=n[0];}
			if(u[5]==2&&cnt!=5){cnt++;so|=n[0];}
			if(u[6]==2&&cnt!=5){cnt++;so|=n[0];}
			return 0x14000000|so;
		}
		if(fn[4]>4){
			System.out.println("Spade Flush");
			int so = 0,cnt=0;
			if(u[0]==4){cnt++;so|=n[0];}
			if(u[1]==4){cnt++;so|=n[0];}
			if(u[2]==4){cnt++;so|=n[0];}
			if(u[3]==4){cnt++;so|=n[0];}
			if(u[4]==4){cnt++;so|=n[0];}
			if(u[5]==4&&cnt!=5){cnt++;so|=n[0];}
			if(u[6]==4&&cnt!=5){cnt++;so|=n[0];}
			return 0x14000000|so;
		}
		
		
		

		int v=1;
		int orr = o&o-1;
		System.out.println(bin(orr));
		v+=(orr&=orr-1)!=0?(orr&=orr-1)!=0?(orr&=orr-1)!=0?(orr&=orr-1)!=0?(orr&=orr-1)!=0?6:5:4:3:2:1;
		System.out.println("or " + bin(o) + ", orr " + bin(orr) + ", v " + v);
		
		
		System.out.println("\n");
		
		int vv=1;
		int xx=x;
		System.out.println(bin(xx));
		if((xx&=xx-1)!=0)
			if((xx&=xx-1)!=0)
				if((xx&=xx-1)!=0)
					if((xx&=xx-1)!=0)
						if((xx&=xx-1)!=0)
							if((xx&=xx-1)!=0)
								vv+=6;
							else vv+=5;
						else vv+=4;
					else vv+=3;
				else vv+=2;
			else vv+=1;
		
		//vv+=(xx&=xx-1)!=0?(xx&=xx-1)!=0?(xx&=xx-1)!=0?(xx&=xx-1)!=0?(xx&=xx-1)!=0?(xx&=xx-1)!=0?6:5:4:3:2:1:0;
		System.out.println("xor " + bin(x) + ", xx " + bin(xx) + ", vv " + vv);
		
		System.out.println("Total = " + (v+vv));
		
		int vvv=v+vv;
		
		
		if(v>4){
			//straight
			int s1=o&o-1;s1&=s1-1;
			int s2=o-n[6];s2-=n[0];
			int s3=o-n[0];
			int mi=0;
			if(n[1]!=n[0])mi=n[1];
			else if(n[2]!=n[1])mi=n[2];
			else if(n[2]!=n[3])mi=n[3];
			else return 0x18000000|n[4]|n[0]<<13;//accidently found quads, so return it
	
			if(0x1F1D100%s1==0){
				System.out.println("high Straight " + bin(s1));
				return 0x10000000|s1;
			}
			if(0x1F1D100%s2==0){
				System.out.println("mid Straight " +bin(s2));
				return 0x10000000|s2;
			}
			if(0x1F1D100%(s3-=mi)==0){
				System.out.println("low Straight " + bin(s3));
				return 0x10000000|s3;
			}
			
			if(n[0]==4096&&(o&15)==15){
				System.out.println("Low ace Straight " );
				return 0x10000000|15;
			}
		}
		
		if(vvv==14){
			//high card
			//return s1;
			return o-n[6]-n[5];
		}
		
		if(vvv==11){
			//pair
			int paired = o^x;
			int highest3WithoutPaird=x&x-1;highest3WithoutPaird&=highest3WithoutPaird-1;
			return 0x4000000|highest3WithoutPaird|paired<<13;
		}
		
		if(vvv==8){
			// 2 pair
			int paired = o^x;
			int highestKicker = n[0]!=n[1]?n[0]:n[2]!=n[3]?n[2]:n[4];
			return 0x8000000|highestKicker|paired<<13;
		}
		
		if(vvv==5){
			//three pair
			int paired = o^x;
			int top2pair=paired&paired-1;
			int highestKicker = n[0]!=n[1]?n[0]:n[2]!=n[3]?n[2]:n[4];
			return 0x8000000|highestKicker|top2pair<<13;
		}
		
		if(vvv==10){
			//trips
		}
		
		if(vvv==7){
			//full house (trips and 1 pair)
			//quads and 3 singles
		}
		
		if(vvv==4){
			//full house, trips and 2 pair
			//quads with a pair 
		}
		
		if(vvv==6){
			//full house, trips and trips
		}
		
		if(vvv==3){
			//quads and trips
		}
		
		
		
		
		
		return 0;//else return 5 highest
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


