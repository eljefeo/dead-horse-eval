package main;

import java.util.Random;

public class HandMaker {

	  public static int[] makeLotsOfRandomHands(int howMany){
		  
		  //keep a copy of the original array
		  // in case we want to use this method on its own out of this class
		  int[] fiftyTwoCards = new int[]
				  {
					  65537,65538,65540,65544,65552,65568,65600,65664,65792,66048,66560,67584,69632,
					  32769,32770,32772,32776,32784,32800,32832,32896,33024,33280,33792,34816,36864,
					  16385,16386,16388,16392,16400,16416,16448,16512,16640,16896,17408,18432,20480,
					  8193,8194,8196,8200,8208,8224,8256,8320,8448,8704,9216,10240,12288
				  };
		  
		  //make a copy of array to do each hand
		  int[] allc2 = (int[]) fiftyTwoCards.clone();
		  
		  //the total array with all the cards will be a 1-D array
		  //every chunk of 5 cards will be a different random hand
		  int []allCards = new int[howMany*5];
		  
		  //random object
		  Random r = new Random();
		  
		  //random number
		  int ran = 0;
		  
		  //x will be used to get the cards from the array and make sure
		  // we do not pick the same card twice
		  int x = 0;
		  for(int i=0;i<howMany;i++){
			  for(int j=0;j<5;j++){
				  //while x==0: this ensures we dont pick the same exact card again
				  //since each card in deck will be set to zero after we pick it,
				  // x would remain zero if it chose it again, and the loop will continue
				  // while x==0 until it chooses a card we have not picked yet
				  while(x==0){
					  ran = r.nextInt((51 - 1) + 1) +1;
					  x = allc2[ran];
				  }
				  
				  allCards[5*i+j] = x;
				  
				  //set the newly picked card to 0, so we cant choose it a again next time
				  allc2[ran]=0;
				  x=0;
			  }
			  
			  //get a fresh copy of the cards ready for the next random 5 card hand
			  //without all the 0s we just created from choosing cards in the previous deck
			  allc2=(int[]) fiftyTwoCards.clone();
		  }
		  
		  return allCards;	
	  }
}
