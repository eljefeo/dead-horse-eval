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
			if(u[1]==0){cnt++;so|=n[1];}
			if(u[2]==0){cnt++;so|=n[2];}
			if(u[3]==0){cnt++;so|=n[3];}
			if(u[4]==0){cnt++;so|=n[4];}
			if(u[5]==0&&cnt!=5){cnt++;so|=n[5];}
			if(u[6]==0&&cnt!=5){cnt++;so|=n[6];}
			return 0x14000000|so;
		}
		if(fn[1]>4){
			System.out.println("Club Flush");
			int so = 0,cnt=0;
			if(u[0]==1){cnt++;so|=n[0];}
			if(u[1]==1){cnt++;so|=n[1];}
			if(u[2]==1){cnt++;so|=n[2];}
			if(u[3]==1){cnt++;so|=n[3];}
			if(u[4]==1){cnt++;so|=n[4];}
			if(u[5]==1&&cnt!=5){cnt++;so|=n[5];}
			if(u[6]==1&&cnt!=5){cnt++;so|=n[6];}
			return 0x14000000|so;
			
		}
		if(fn[2]>4){
			System.out.println("Heart Flush");
			int so = 0,cnt=0;
			if(u[0]==2){cnt++;so|=n[0];}
			if(u[1]==2){cnt++;so|=n[1];}
			if(u[2]==2){cnt++;so|=n[2];}
			if(u[3]==2){cnt++;so|=n[3];}
			if(u[4]==2){cnt++;so|=n[4];}
			if(u[5]==2&&cnt!=5){cnt++;so|=n[4];}
			if(u[6]==2&&cnt!=5){cnt++;so|=n[5];}
			return 0x14000000|so;
		}
		if(fn[4]>4){
			System.out.println("Spade Flush");
			int so = 0,cnt=0;
			if(u[0]==4){cnt++;so|=n[0];}
			if(u[1]==4){cnt++;so|=n[1];}
			if(u[2]==4){cnt++;so|=n[2];}
			if(u[3]==4){cnt++;so|=n[3];}
			if(u[4]==4){cnt++;so|=n[4];}
			if(u[5]==4&&cnt!=5){cnt++;so|=n[5];}
			if(u[6]==4&&cnt!=5){cnt++;so|=n[6];}
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
			if(n[0]==n[1]) return 0xC000000|n[3]|n[4]|n[0]<<13;
			else if (n[2]==n[3]) return 0xC000000|n[0]|(n[1]!=n[2]?n[1]:n[4])|n[2]<<13;
			else return 0xC000000|n[0]|n[1]|n[4]<<13;
		}
		
		if(vvv==7){
			//full house (trips and 1 pair)
			//quads and 3 singles
			
			// if quads, n[3] contains quad card
			if((n[0]&n[1]&n[2])==n[3]){
				return 0x1C000000|n[4]|n[3]<<13;
			}
			if((n[1]&n[2]&n[4])==n[3] || (n[2]&n[4]&n[5])==n[3] || (n[4]&n[5]&n[6])==n[3]){
				return 0x1C000000|n[0]|n[3]<<13;
			}

			int pair = o^x;
			 if(n[0]==n[1]&&n[0]!=pair) return 0x18000000|pair|n[0]<<13;
			else if (n[2]==n[3]&&n[2]!=pair) return 0x18000000|pair|n[2]<<13;
			else return 0x18000000|pair|n[4]<<13;
		}
		
		if(vvv==4){
			if((n[0]&n[1]&n[2])==n[3]){
				return 0x1C000000|n[4]|n[3]<<13;
			}
			if((n[1]&n[2]&n[4])==n[3] || (n[2]&n[4]&n[5])==n[3] || (n[4]&n[5]&n[6])==n[3]){
				return 0x1C000000|n[0]|n[3]<<13;
			}
			
			int pair = o^x;
			 if((n[0]&n[1]&pair)!=0) return 0x18000000|n[0]|n[4]<<13;
			 else return 0x18000000|n[3]|n[0]<<13;
			//full house, trips and 2 pair
			//quads with a pair 
		}
		
		if(vvv==6){
			//full house, trips and trips
			//n[1] == higher trip card, n[5] lower trip (pair) card
			return 0x18000000|n[5]|n[1]<<13;
		}
		
		if(vvv==3){
			//quads and trips
			return 0x1C000000|(n[0]==n[3]?n[6]:n[0])|n[3]<<13;
			
		}
		
		
		
		
		
		return 0;//should never come to this
	}
	
	
	
	public static int eval7b(int n0, int n1, int n2, int n3, int n4, int n5, int n6){
		int u0 = (n0&0x1E000)>>14;
		int u1 = (n1&0x1E000)>>14;
		int u2 = (n2&0x1E000)>>14;
		int u3 = (n3&0x1E000)>>14;
		int u4 = (n4&0x1E000)>>14;
		int u5 = (n5&0x1E000)>>14;
		int u6 = (n6&0x1E000)>>14;
		n0&=8191;n1&=8191;n2&=8191;n3&=8191;n4&=8191;n5&=8191;n6&=8191;
		

		boolean quads = false;
		int o = n0|n1|n2|n3|n4|n5|n6;
		int x = n0^n1^n2^n3^n4^n5^n6;
		
	/*	System.out.println("or " + bin(o));
		System.out.println("xor " + bin(x));
		System.out.println(n0 + " : " + u0);
		System.out.println(n1 + " : " + u1);
		System.out.println(n2 + " : " + u2);
		System.out.println(n3 + " : " + u3);
		System.out.println(n4 + " : " + u4);
		System.out.println(n5 + " : " + u5);
		System.out.println(n6 + " : " + u6);*/
		//System.out.println();
		
		int[] all = sort7bb(n0,n1,n2,n3,n4,n5,n6,u0,u1,u2,u3,u4,u5,u6);
		n0=all[0];
		n1=all[1];
		n2=all[2];
		n3=all[3];
		n4=all[4];
		n5=all[5];
		n6=all[6];
		u0=all[7];
		u1=all[8];
		u2=all[9];
		u3=all[10];
		u4=all[11];
		u5=all[12];
		u6=all[13];
		
		/*System.out.println(n0 + " : " + u0);
		System.out.println(n1 + " : " + u1);
		System.out.println(n2 + " : " + u2);
		System.out.println(n3 + " : " + u3);
		System.out.println(n4 + " : " + u4);
		System.out.println(n5 + " : " + u5);
		System.out.println(n6 + " : " + u6);*/
		
		//flush
		int[]fn = new int[5];
		fn[u0]++;fn[u1]++;fn[u2]++;fn[u3]++;fn[u4]++;fn[u5]++;fn[u6]++;
		if(fn[0]>4){
		//	System.out.println("Diamond Flush");
			int so = 0,cnt=0;
			if(u0==0){cnt++;so|=n0;}
			if(u1==0){cnt++;so|=n1;}
			if(u2==0){cnt++;so|=n2;}
			if(u3==0){cnt++;so|=n3;}
			if(u4==0){cnt++;so|=n4;}
			if(u5==0&&cnt!=5){cnt++;so|=n5;}
			if(u6==0&&cnt!=5){cnt++;so|=n6;}
			return 0x14000000|so;
		}
		if(fn[1]>4){
			//System.out.println("Club Flush");
			int so = 0,cnt=0;
			if(u0==1){cnt++;so|=n0;}
			if(u1==1){cnt++;so|=n1;}
			if(u2==1){cnt++;so|=n2;}
			if(u3==1){cnt++;so|=n3;}
			if(u4==1){cnt++;so|=n4;}
			if(u5==1&&cnt!=5){cnt++;so|=n5;}
			if(u6==1&&cnt!=5){cnt++;so|=n6;}
			return 0x14000000|so;
			
		}
		if(fn[2]>4){
			//System.out.println("Heart Flush");
			int so = 0,cnt=0;
			if(u0==2){cnt++;so|=n0;}
			if(u1==2){cnt++;so|=n1;}
			if(u2==2){cnt++;so|=n2;}
			if(u3==2){cnt++;so|=n3;}
			if(u4==2){cnt++;so|=n4;}
			if(u5==2&&cnt!=5){cnt++;so|=n4;}
			if(u6==2&&cnt!=5){cnt++;so|=n5;}
			return 0x14000000|so;
		}
		if(fn[4]>4){
			//System.out.println("Spade Flush");
			int so = 0,cnt=0;
			if(u0==4){cnt++;so|=n0;}
			if(u1==4){cnt++;so|=n1;}
			if(u2==4){cnt++;so|=n2;}
			if(u3==4){cnt++;so|=n3;}
			if(u4==4){cnt++;so|=n4;}
			if(u5==4&&cnt!=5){cnt++;so|=n5;}
			if(u6==4&&cnt!=5){cnt++;so|=n6;}
			return 0x14000000|so;
		}
		
		
		

		int v=1;
		int orr = o&o-1;
		//System.out.println(bin(orr));
		v+=(orr&=orr-1)!=0?(orr&=orr-1)!=0?(orr&=orr-1)!=0?(orr&=orr-1)!=0?(orr&=orr-1)!=0?6:5:4:3:2:1;
		//System.out.println("or " + bin(o) + ", orr " + bin(orr) + ", v " + v);
		
		
		//System.out.println("\n");
		
		int vv=1;
		int xx=x;
		//System.out.println(bin(xx));
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
		//System.out.println("xor " + bin(x) + ", xx " + bin(xx) + ", vv " + vv);
		
		//System.out.println("Total = " + (v+vv));
		
		int vvv=v+vv;
		
		
		if(v>4){
			//straight
			int s1=o&o-1;s1&=s1-1;
			int s2=o-n6;s2-=n0;
			int s3=o-n0;
			int mi=0;
			if(n1!=n0)mi=n1;
			else if(n2!=n1)mi=n2;
			else if(n2!=n3)mi=n3;
			else return 0x18000000|n4|n0<<13;//accidently found quads, so return it
	
			if(0x1F1D100%s1==0){
				//System.out.println("high Straight " + bin(s1));
				return 0x10000000|s1;
			}
			if(0x1F1D100%s2==0){
				//System.out.println("mid Straight " +bin(s2));
				return 0x10000000|s2;
			}
			if(0x1F1D100%(s3-=mi)==0){
				//System.out.println("low Straight " + bin(s3));
				return 0x10000000|s3;
			}
			
			if(n0==4096&&(o&15)==15){
				//System.out.println("Low ace Straight " );
				return 0x10000000|15;
			}
		}
		
		if(vvv==14){
			//high card
			//return s1;
			return o-n6-n5;
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
			int highestKicker = n0!=n1?n0:n2!=n3?n2:n4;
			return 0x8000000|highestKicker|paired<<13;
		}
		
		if(vvv==5){
			//three pair
			int paired = o^x;
			int top2pair=paired&paired-1;
			int highestKicker = n0!=n1?n0:n2!=n3?n2:n4;
			return 0x8000000|highestKicker|top2pair<<13;
		}
		
		if(vvv==10){
			//trips
			if(n0==n1) return 0xC000000|n3|n4|n0<<13;
			else if (n2==n3) return 0xC000000|n0|(n1!=n2?n1:n4)|n2<<13;
			else return 0xC000000|n0|n1|n4<<13;
		}
		
		if(vvv==7){
			//full house (trips and 1 pair)
			//quads and 3 singles
			
			// if quads, n3 contains quad card
			if((n0&n1&n2)==n3){
				return 0x1C000000|n4|n3<<13;
			}
			if((n1&n2&n4)==n3 || (n2&n4&n5)==n3 || (n4&n5&n6)==n3){
				return 0x1C000000|n0|n3<<13;
			}

			int pair = o^x;
			 if(n0==n1&&n0!=pair) return 0x18000000|pair|n0<<13;
			else if (n2==n3&&n2!=pair) return 0x18000000|pair|n2<<13;
			else return 0x18000000|pair|n4<<13;
		}
		
		if(vvv==4){
			if((n0&n1&n2)==n3){
				return 0x1C000000|n4|n3<<13;
			}
			if((n1&n2&n4)==n3 || (n2&n4&n5)==n3 || (n4&n5&n6)==n3){
				return 0x1C000000|n0|n3<<13;
			}
			
			int pair = o^x;
			 if((n0&n1&pair)!=0) return 0x18000000|n0|n4<<13;
			 else return 0x18000000|n3|n0<<13;
			//full house, trips and 2 pair
			//quads with a pair 
		}
		
		if(vvv==6){
			//full house, trips and trips
			//n1 == higher trip card, n5 lower trip (pair) card
			return 0x18000000|n5|n1<<13;
		}
		
		if(vvv==3){
			//quads and trips
			return 0x1C000000|(n0==n3?n6:n0)|n3<<13;
			
		}
		
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


