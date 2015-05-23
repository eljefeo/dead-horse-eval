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
	
	public static int eval7(int[]n){
		int[] u = {(n[0]&0x1E000)>>14,(n[1]&0x1E000)>>14,(n[2]&0x1E000)>>14,(n[3]&0x1E000)>>14,(n[4]&0x1E000)>>14,(n[5]&0x1E000)>>14,(n[6]&0x1E000)>>14};
		n[0]&=8191;n[1]&=8191;n[2]&=8191;n[3]&=8191;n[4]&=8191;n[5]&=8191;n[6]&=8191;
		

		int o = n[0]|n[1]|n[2]|n[3]|n[4]|n[5]|n[6];
		int x = n[0]^n[1]^n[2]^n[3]^n[4]^n[5]^n[6];
		int ox =o^x;
		//System.out.println("or " + bin(o));
		//System.out.println("xor " + bin(x));

		sort7b(n,u);
		
/*		System.out.println(n[0] + " : " + u[0]);
		System.out.println(n[1] + " : " + u[1]);
		System.out.println(n[2] + " : " + u[2]);
		System.out.println(n[3] + " : " + u[3]);
		System.out.println(n[4] + " : " + u[4]);
		System.out.println(n[5] + " : " + u[5]);
		System.out.println(n[6] + " : " + u[6]);
		*/
		//flush
			
		
		

		int v=1;
		int or = o&o-1;
		//System.out.println(bin(or));
		v+=(or&=or-1)!=0?(or&=or-1)!=0?(or&=or-1)!=0?(or&=or-1)!=0?(or&=or-1)!=0?6:5:4:3:2:1;


		if(v>4){
			//setup straight check
			int s1=o&o-1;s1&=s1-1;
			int s2=o-n[6];s2-=n[0];
			int s3=o-n[0];
			if(n[1]!=n[0])s3-=n[1];
			else if(n[2]!=n[1])s3-=n[2];
			else if(n[2]!=n[3])s3-=n[3];
			else return 0x18000000|n[4]|n[0]<<13;//accidently found quads, so return it
			
			//setup flush check
			int so=0;
			int[]fn = new int[5];
			fn[u[0]]++;fn[u[1]]++;fn[u[2]]++;fn[u[3]]++;
			fn[u[4]]++;fn[u[5]]++;fn[u[6]]++;
			if(fn[0]>4){//Diamond Flush
					if(u[0]==0)so|=n[0];if(u[1]==0)so|=n[1];
					if(u[2]==0)so|=n[2];if(u[3]==0)so|=n[3];
					if(u[4]==0)so|=n[4];if(u[5]==0)so|=n[5];
					if(u[6]==0)so|=n[6];
				}
				if(fn[1]>4){//Club Flush
					if(u[0]==1)so|=n[0];if(u[1]==1)so|=n[1];
					if(u[2]==1)so|=n[2];if(u[3]==1)so|=n[3];
					if(u[4]==1)so|=n[4];if(u[5]==1)so|=n[5];
					if(u[6]==1)so|=n[6];
				}
				if(fn[2]>4){//Heart Flush
					if(u[0]==2)so|=n[0];if(u[1]==2)so|=n[1];
					if(u[2]==2)so|=n[2];if(u[3]==2)so|=n[3];
					if(u[4]==2)so|=n[4];if(u[5]==2)so|=n[5];
					if(u[6]==2)so|=n[6];
				}
				if(fn[4]>4){//Spade Flush
					if(u[0]==4)so|=n[0];if(u[1]==4)so|=n[1];
					if(u[2]==4)so|=n[2];if(u[3]==4)so|=n[3];
					if(u[4]==4)so|=n[4];if(u[5]==4)so|=n[5];
					if(u[6]==4)so|=n[6];
				}
			
			//check for straight flush
			if(so!=0){
				if((so&s1)==s1) return 0x20000000|s1;
				if((so&s2)==s2) return 0x20000000|s2;
				if((so&s3)==s3) return 0x20000000|s3;
				if((so&0x100F)==0x100F)return 0x20000000|15;
				return 0x14000000|so;//else return flush
			}
			//else check for straights
			if(0x1F1D100%s1==0) 	return 0x10000000|s1;//high Straight
			if(0x1F1D100%s2==0) 	return 0x10000000|s2;//mid Straight
			if(0x1F1D100%s3==0) 	return 0x10000000|s3;//low Straight
			if((o&0x100F)==0x100F)	return 0x10000000|15;//Low ace Straight
		}
		
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
		
		
		int vvv=v+vv;
		
		if(vvv==14){
			//high card
			//return s1;
			return o-n[6]-n[5];
		}
		
		if(vvv==11){
			//pair
			
			int highest3WithoutPaird=x&x-1;highest3WithoutPaird&=highest3WithoutPaird-1;
			return 0x4000000|highest3WithoutPaird|ox<<13;
		}
		
		if(vvv==8){
			// 2 pair
			int highestKicker = n[0]!=n[1]?n[0]:n[2]!=n[3]?n[2]:n[4];
			return 0x8000000|highestKicker|ox<<13;
		}
		
		if(vvv==5){
			//three pair
			//int highestKicker = n[0]!=n[1]?n[0]:n[2]!=n[3]?n[2]:n[4];
			//return 0x8000000|highestKicker|(ox&ox-1)<<13;
			return 0x8000000|(n[0]!=n[1]?n[0]:n[2]!=n[3]?n[2]:n[4])|(ox&ox-1)<<13;
		}
		
		if(vvv==10){
			//trips
			/*if(n[0]==n[1]) return 0xC000000|n[3]|n[4]|n[0]<<13;
			else if (n[2]==n[3]) return 0xC000000|n[0]|(n[1]!=n[2]?n[1]:n[4])|n[2]<<13;
			else return 0xC000000|n[0]|n[1]|n[4]<<13;*/
			return n[0]==n[1] ? 0xC000000|n[3]|n[4]|n[0]<<13
					:n[2]==n[3] ? 0xC000000|n[0]|(n[1]!=n[2]?n[1]:n[4])|n[2]<<13
							:0xC000000|n[0]|n[1]|n[4]<<13;
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

			 if(n[0]==n[1]&&n[0]!=ox) return 0x18000000|ox|n[0]<<13;
			else if (n[2]==n[3]&&n[2]!=ox) return 0x18000000|ox|n[2]<<13;
			else return 0x18000000|ox|n[4]<<13;
		}
		
		if(vvv==4){
			if((n[0]&n[1]&n[2])==n[3]){
				return 0x1C000000|n[4]|n[3]<<13;
			}
			if((n[1]&n[2]&n[4])==n[3] || (n[2]&n[4]&n[5])==n[3] || (n[4]&n[5]&n[6])==n[3]){
				return 0x1C000000|n[0]|n[3]<<13;
			}
			
			 if((n[0]&n[1]&ox)!=0) return 0x18000000|n[0]|n[4]<<13;
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
			
			return 0x1C000000|(n[0]==n[3]?n[6]:n[0])|n[3]<<13;//quads and trips
			
		}
		
		
		
		
		
		return 0;//should never come to this
	}
	
	
	
	public static int eval7b(int n0, int n1, int n2, int n3, int n4, int n5, int n6){
		int u0=(n0&0x1E000)>>14;
		int u1=(n1&0x1E000)>>14;
		int u2=(n2&0x1E000)>>14;
		int u3=(n3&0x1E000)>>14;
		int u4=(n4&0x1E000)>>14;
		int u5=(n5&0x1E000)>>14;
		int u6=(n6&0x1E000)>>14;
		n0&=8191;n1&=8191;n2&=8191;
		n3&=8191;n4&=8191;n5&=8191;
		n6&=8191;
		int o=n0|n1|n2|n3|n4|n5|n6;
		int x=n0^n1^n2^n3^n4^n5^n6;
		int z=0;
		if(n2>n1){z=n2;n2=n1;n1=z;z=u2;u2=u1;u1=z;}
		if(n4>n3){z=n4;n4=n3;n3=z;z=u4;u4=u3;u3=z;}
		if(n6>n5){z=n6;n6=n5;n5=z;z=u6;u6=u5;u5=z;}
		if(n2>n0){z=n2;n2=n0;n0=z;z=u2;u2=u0;u0=z;}
		if(n5>n3){z=n5;n5=n3;n3=z;z=u5;u5=u3;u3=z;}
		if(n6>n4){z=n6;n6=n4;n4=z;z=u6;u6=u4;u4=z;}		
		if(n1>n0){z=n1;n1=n0;n0=z;z=u1;u1=u0;u0=z;}
		if(n5>n4){z=n5;n5=n4;n4=z;z=u5;u5=u4;u4=z;}
		if(n6>n2){z=n6;n6=n2;n2=z;z=u6;u6=u2;u2=z;}
		if(n4>n0){z=n4;n4=n0;n0=z;z=u4;u4=u0;u0=z;}
		if(n5>n1){z=n5;n5=n1;n1=z;z=u5;u5=u1;u1=z;}
		if(n3>n0){z=n3;n3=n0;n0=z;z=u3;u3=u0;u0=z;}
		if(n5>n2){z=n5;n5=n2;n2=z;z=u5;u5=u2;u2=z;}
		if(n3>n1){z=n3;n3=n1;n1=z;z=u3;u3=u1;u1=z;}
		if(n4>n2){z=n4;n4=n2;n2=z;z=u4;u4=u2;u2=z;}
		if(n3>n2){z=n3;n3=n2;n2=z;z=u3;u3=u2;u2=z;}
		z=o^x;
		int v=1;
		int or = o&o-1;
		v+=(or&=or-1)!=0?(or&=or-1)!=0?(or&=or-1)!=0
		?(or&=or-1)!=0?(or&=or-1)!=0?6:5:4:3:2:1;		
		
		/*System.out.println(n0 + " : " + u0);
		System.out.println(n1 + " : " + u1);
		System.out.println(n2 + " : " + u2);
		System.out.println(n3 + " : " + u3);
		System.out.println(n4 + " : " + u4);
		System.out.println(n5 + " : " + u5);
		System.out.println(n6 + " : " + u6);*/

		if(v>4){
		
			/**********************************v == 7***************************/
			if(v==7){
				//straight
				int s1=o&o-1;s1&=s1-1;
				int s2=o-n6;s2-=n0;
				int s3=o-n0;
					 if(n1!=n0)s3-=n1;
				else if(n2!=n1)s3-=n2;
				else if(n2!=n3)s3-=n3;
				else return 0x18000000|n4|n0<<13;//accidently found quads, so return it
		
			//flush
			int so=0;
			int[]fn = new int[5];
			fn[u0]++;fn[u1]++;fn[u2]++;fn[u3]++;
			fn[u4]++;fn[u5]++;fn[u6]++;

			if(fn[0]>4){//Diamond Flush
				if(u0==0)so|=n0;if(u1==0)so|=n1;
				if(u2==0)so|=n2;if(u3==0)so|=n3;
				if(u4==0)so|=n4;if(u5==0)so|=n5;
				if(u6==0)so|=n6;
			}
			if(fn[1]>4){//Club Flush
				if(u0==1)so|=n0;if(u1==1)so|=n1;
				if(u2==1)so|=n2;if(u3==1)so|=n3;
				if(u4==1)so|=n4;if(u5==1)so|=n5;
				if(u6==1)so|=n6;
				
			}
			if(fn[2]>4){//Heart Flush
				if(u0==2)so|=n0;if(u1==2)so|=n1;
				if(u2==2)so|=n2;if(u3==2)so|=n3;
				if(u4==2)so|=n4;if(u5==2)so|=n5;
				if(u6==2)so|=n6;
			}
			if(fn[4]>4){//Spade Flush
				if(u0==4)so|=n0;if(u1==4)so|=n1;
				if(u2==4)so|=n2;if(u3==4)so|=n3;
				if(u4==4)so|=n4;if(u5==4)so|=n5;
				if(u6==4)so|=n6;
			}
			
				//check for straight flush

				if(so!=0){
					return   (0x1F00%(so&s1))==0 ?0x20000000|s1
							:(0x1F00%(so&s2))==0 ?0x20000000|s2
							:(0x1F00%(so&s3))==0 ?0x20000000|s3
							:(so&0x100F)==0x100F?0x20000000|15
							:0x14000000|so;//else return flush
				}
				
				if(0x1F00%s1==0) 	return 0x10000000|s1;//high Straight
				if(0x1F00%s2==0) 	return 0x10000000|s2;//mid Straight
				if(0x1F00%s3==0) 	return 0x10000000|s3;//low Straight
				if((o&0x100F)==0x100F) 	return 0x10000000|15;//Low ace Straight
				
			}
			/***************************************************************************/
			
			
			
			
			
			
			
			
			/****************************v == 6**************************************/
			if(v==6){//straight
				int s1=o&o-1;
				int s2=o-n0;
		
			//flush
			int so=0;
			int[]fn = new int[5];
			fn[u0]++;fn[u1]++;fn[u2]++;fn[u3]++;
			fn[u4]++;fn[u5]++;fn[u6]++;

			if(fn[0]>4){//Diamond Flush
				if(u0==0)so|=n0;if(u1==0)so|=n1;
				if(u2==0)so|=n2;if(u3==0)so|=n3;
				if(u4==0)so|=n4;if(u5==0)so|=n5;
				if(u6==0)so|=n6;
			}
			if(fn[1]>4){//Club Flush
				if(u0==1)so|=n0;if(u1==1)so|=n1;
				if(u2==1)so|=n2;if(u3==1)so|=n3;
				if(u4==1)so|=n4;if(u5==1)so|=n5;
				if(u6==1)so|=n6;
				
			}
			if(fn[2]>4){//Heart Flush
				if(u0==2)so|=n0;if(u1==2)so|=n1;
				if(u2==2)so|=n2;if(u3==2)so|=n3;
				if(u4==2)so|=n4;if(u5==2)so|=n5;
				if(u6==2)so|=n6;
			}
			if(fn[4]>4){//Spade Flush
				if(u0==4)so|=n0;if(u1==4)so|=n1;
				if(u2==4)so|=n2;if(u3==4)so|=n3;
				if(u4==4)so|=n4;if(u5==4)so|=n5;
				if(u6==4)so|=n6;
			}
			
				//check for straight flush

				if(so!=0){
					return   (0x1F00%(so&s1))==0 ?0x20000000|s1
							:(0x1F00%(so&s2))==0 ?0x20000000|s2
							:(so&0x100F)==0x100F?0x20000000|15
							:0x14000000|so;//else return flush
				}
				
				if(0x1F00%s1==0) 	return 0x10000000|s1;//high Straight
				if(0x1F00%s2==0) 	return 0x10000000|s2;//low Straight
				if((o&0x100F)==0x100F) 	return 0x10000000|15;//Low ace Straight
			}
			/***************************************************************************/
			
			
			
			
			/***********************************v == 5**************************/
			if(v==5){
		
			//flush
			int so=0;
			int[]fn = new int[5];
			fn[u0]++;fn[u1]++;fn[u2]++;fn[u3]++;
			fn[u4]++;fn[u5]++;fn[u6]++;

			if(fn[0]>4){//Diamond Flush
				if(u0==0)so|=n0;if(u1==0)so|=n1;
				if(u2==0)so|=n2;if(u3==0)so|=n3;
				if(u4==0)so|=n4;if(u5==0)so|=n5;
				if(u6==0)so|=n6;
			}
			if(fn[1]>4){//Club Flush
				if(u0==1)so|=n0;if(u1==1)so|=n1;
				if(u2==1)so|=n2;if(u3==1)so|=n3;
				if(u4==1)so|=n4;if(u5==1)so|=n5;
				if(u6==1)so|=n6;
				
			}
			if(fn[2]>4){//Heart Flush
				if(u0==2)so|=n0;if(u1==2)so|=n1;
				if(u2==2)so|=n2;if(u3==2)so|=n3;
				if(u4==2)so|=n4;if(u5==2)so|=n5;
				if(u6==2)so|=n6;
			}
			if(fn[4]>4){//Spade Flush
				if(u0==4)so|=n0;if(u1==4)so|=n1;
				if(u2==4)so|=n2;if(u3==4)so|=n3;
				if(u4==4)so|=n4;if(u5==4)so|=n5;
				if(u6==4)so|=n6;
			}
			
				//check for straight flush

				if(so!=0){
					return   (0x1F00%(so&o))==0 ?0x20000000|o
							:(so&0x100F)==0x100F?0x20000000|15
							:0x14000000|so;//else return flush
				}
				
				if(0x1F00%o==0) 	return 0x10000000|o;//Straight
				if((o&0x100F)==0x100F) 	return 0x10000000|15;//Low ace Straight
				
			}
			/***************************************************************************/
			
			
			
			
			
		}
		

		int xx=x;
		v+=(xx&=xx-1)!=0?(xx&=xx-1)!=0?(xx&=xx-1)!=0?(xx&=xx-1)!=0
		?(xx&=xx-1)!=0?(xx&=xx-1)!=0?7:6:5:4:3:2:1;
		
		if(v==14) return o-n6-n5;//high card
		
		if(v==11) return 0x4000000|(x&=x-1)&x-1|z<<13;//pair
		
		if(v==8) return 0x8000000|(n0!=n1?n0:n2!=n3?n2:n4)|z<<13;// 2 pair
		
		if(v==5) return 0x8000000|(n0!=n1?n0:n2!=n3?n2:n4)|(z&z-1)<<13;//three pair
		
		if(v==10) return n0==n1?0xC000000|n3|n4|n0<<13
				  	:n2==n3?0xC000000|n0|(n1!=n2?n1:n4)|n2<<13
				  	:0xC000000|n0|n1|n4<<13;//trips
		
		if(v==7) return (n0&n1&n2)==n3 ? 0x1C000000|n4|n3<<13
					:(n1&n2&n4)==n3||(n2&n4&n5)==n3||(n4&n5&n6)==n3
					?0x1C000000|n0|n3<<13
					:n0==n1&&n0!=z?0x18000000|z|n0<<13
					:n2==n3&&n2!=z? 0x18000000|z|n2<<13
					:0x18000000|z|n4<<13;//full house (trips and 1 pair)
		
		if(v==4) return (n0&n1&n2)==n3?0x1C000000|n4|n3<<13
					:(n1&n2&n4)==n3||(n2&n4&n5)==n3||(n4&n5&n6)==n3
					?0x1C000000|n0|n3<<13
					:(n0&n1&z)!=0?0x18000000|n0|n4<<13
					:0x18000000|n3|n0<<13;//full house, trips and 2 pair, quads with a pair 
		
		if(v==6) return 0x18000000|n5|n1<<13;//full house, trips and trips
		
		if(v==3) return 0x1C000000|(n0==n3?n6:n0)|n3<<13;//quads and trips
		
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


