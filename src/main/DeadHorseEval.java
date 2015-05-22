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
		

		
		int or = n[0]|n[1]|n[2]|n[3]|n[4]|n[5]|n[6];

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
		
		//straight
		int s1=or&or-1;s1&=s1-1;
		int s2=or&(or-1^(~or>>1));
		int s3=or&(or-1^(~or>>1));
		
	
		if(0x1F1D100%s1==0){
			//System.out.println
		}
		
		
		
		return 0;
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

}


