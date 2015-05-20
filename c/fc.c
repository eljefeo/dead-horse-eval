#include <stdio.h>
#include <stdbool.h>

int main() {

	makeLotsOfRandom5CardHands(1000000);

return 0;


}


int eval(int a, int b, int c, int d, int e) {
	int x=((a^b^c^d^e)&0x1FFF);
	int y=((a|b|c|d|e)&0x1FFF);
	int z=(y^x);
	int v=y&y-1;
				bool strt=0x1F1D100%y==0,flsh=(a&b&c&d&e)!=0;
				return (v=(v&=v-1)==0?2:(v&=v-1)==0?3:(v&=v-1)==0?4:5)==3?z==0
					?0xC000000|(v=((a&b)==(a&0x1FFF)?a:(c&d)==(c&0x1FFF)?c:e)&0x1FFF^y)|v<<13
					:0x8000000|x|z<<13:v==4?0x4000000|x|z<<13
					:v==2?(a+b+c+d+e-x&0x1FFF)==(0x1FFF&(y^x)<<2)
					?0x1C000000|x|z<<13:0x18000000|z|x<<13
					:strt&&flsh?0x20000000|(x==0x100F?15:x)
					:strt?0x10000000|(x==0x100F?15:x):flsh?0x14000000|x:x;
}



void makeLotsOfRandom5CardHands(int howMany){

		  int fiftyTwoCards[52] = {65537,65538,65540,65544,65552,65568,65600,65664,65792,66048,66560,67584,69632,
				  32769,32770,32772,32776,32784,32800,32832,32896,33024,33280,33792,34816,36864,
				  16385,16386,16388,16392,16400,16416,16448,16512,16640,16896,17408,18432,20480,
				  8193,8194,8196,8200,8208,8224,8256,8320,8448,8704,9216,10240,12288};


		  int allc2[52];
		  strncpy(allc2,fiftyTwoCards,52);

		  int allCards[howMany*5];

		  int ran = 0;

		  //x will be used to get the cards from the array and make sure
		  // we do not pick the same card twice
		  int x = 0;
		  int i=0;
		  for(i=0;i<howMany;i++){
			  for(int j=0;j<5;j++){
				  while(x==0){
					  ran = rand()%(51);
					  x = allc2[ran];
				  }

				  allCards[5*i+j] = x;
				  				  allc2[ran]=0;
				  x=0;
			  }

			  strncpy(allc2,fiftyTwoCards,52);
		  }

			int winner = 0;
			for (int i=0;i<howMany;i+=5){
				int temp = eval(allCards[i],allCards[i+1],allCards[i+2],allCards[i+3],allCards[i+4]);
				if (temp > winner){
					winner = temp;
				}
			}

			printf("Winning hand is %d", winner);

	  }
