package main;

public class EvalTest {
	
	static String[] handNames = 
		{
			"High Card", "Pair", "Two Pair", "3 of a kind", 
			"Straight", "Flush", "Full House", "4 of a kind", "Straight Flush"
		};
	
	
	static int[] frequency=
		{
			1302540,1098240,123552,54912,10200,5108,3744,624,40
		};
	

	  //there is a speed test method below
	  //but in case you want the good ol fashion 'create every single possible hand'
	  //here you go
	  public static void testEveryHand(){
		  
		  //copy of the deck to make this method more portable
		  int[] fiftyTwo = new int[]
				  {
					  65537,65538,65540,65544,65552,65568,65600,65664,65792,66048,66560,67584,69632,
					  32769,32770,32772,32776,32784,32800,32832,32896,33024,33280,33792,34816,36864,
					  16385,16386,16388,16392,16400,16416,16448,16512,16640,16896,17408,18432,20480,
					  8193,8194,8196,8200,8208,8224,8256,8320,8448,8704,9216,10240,12288
				  };
		  
		  int totalHands = 2598960;
		  int[] allCards = new int[totalHands*5];
		  int totalCounter=0;
		  
		  //this is how we create every possible 5 card hand
		  for(int i=0;i<fiftyTwo.length-1;i++)
			  for(int j=i+1;j<fiftyTwo.length;j++)
				  for(int k=j+1;k<fiftyTwo.length;k++)
					  for(int l=k+1;l<fiftyTwo.length;l++)
						  for(int m=l+1;m<fiftyTwo.length;m++){
							  allCards[totalCounter*5]	=fiftyTwo[i];
							  allCards[totalCounter*5+1]=fiftyTwo[j];
							  allCards[totalCounter*5+2]=fiftyTwo[k];
							  allCards[totalCounter*5+3]=fiftyTwo[l];
							  allCards[totalCounter*5+4]=fiftyTwo[m];
							  totalCounter++;
						  }
		  
		  //get start time
		  long startT = System.nanoTime();
		  //go through every hand, 5 cards at a time
		  for(int i=0;i<allCards.length;i+=5)
			 	DeadHorseEval.eval(allCards[i],allCards[i+1],allCards[i+2],allCards[i+3],allCards[i+4]);
		  //get end time
		  long endT = System.nanoTime();
		  
		  // Time = (end time - start time ) divided by a billion : because it is in nano seconds
		  double time = (double) (endT - startT)/1000000000;
		  System.out.println("Did all " + totalHands + " hands in " + time +" seconds");
		  //hands per second in millions 
		  System.out.println((totalHands/time/1000000) + " million hands a second\n");
		
		  //this is here to count how many of each type of hand come up.
		  //it will re-eval each hand so we do not corrupt the timing with this extra process
		  //this is here to show the accuracy of making/testing each hand possible
		  //Since there is a known number of each type of 5 card hand with 52 cards :
		  //High Card 		1302540
		  //Pair 			1098240
		  //Two Pair 		123552
		  //Trips 			54912
		  //Straight 		10200
		  //Flush 			5108
		  //Full House 		3744
		  //Quads 			624
		  //Straight Flush	40
		  //Total			2598960
		  int[] handCounter = new int[9];
		  for(int i=0;i<allCards.length;i+=5){
			
			  int res = DeadHorseEval.eval(allCards[i],allCards[i+1],allCards[i+2],allCards[i+3],allCards[i+4]);
			  res&=0x3C000000;
			  handCounter[res>>=26]++;
		  
		  }
		  //this is to go through and compare the number of each type of hand we created
		  //to the number of each type of hand we expect
		   for(int j=0;j<handCounter.length;j++){
			   //check if expected == actual
			   boolean checked = handCounter[j]==frequency[j];
			   String res = checked
					   //if all hands accounted for, good news
					   ?"All "+frequency[j]+" "+handNames[j]+" hands are accounted for" 
							   
						//if the counts dont match, show how many failed
					   : (handCounter[j]-frequency[j])+" of "+frequency[j]
							   +" "+handNames[j]+" hands failed!"; 
			   
			   System.out.println(res+" " + ((double)handCounter[j]/totalCounter*100) + "%");
		   }
			 
		  System.out.println("Total Count : " + totalCounter);
		  
	  }

	public static void randomizerSpeedTest5CardDiagnostics(int howMany){
		  
		  //number of hands to burn through
		  //pick a nice huge round number to let this sucker get warmed up
		  // int howMany = 10000000;

		  int[] allCards = HandMaker.makeLotsOfRandom5CardHands(howMany);
		  
		  //get start time
		 // long startT = System.nanoTime();
		  
		  //let er rip, go through every hand, 5 cards at a time
		
		  for(int i=0;i<allCards.length;i+=5){
			 int res = DeadHorseEval.eval(allCards[i],allCards[i+1],allCards[i+2],allCards[i+3],allCards[i+4]);
			 res&=0x3C000000;
			  System.out.println("\n"+handNames[res>>26]);
		  }
		  //get end time
		 // long endT = System.nanoTime();
		  
		 
		  
		  
		    
		  // Time is (end time - start time ) divided by a billion : because it is in nano seconds
		  //double time = (double) (endT - startT)/1000000000;
		  
		  //System.out.println("Did " + howMany + " hands in " + time +" seconds");
		  //hands per second in millions 
		  //System.out.println((int)(howMany/time/1000000) + " million hands a second"); 
	  }
	
	
	public static void randomizerSpeedTest5Card(int howMany){
		  
		  //number of hands to burn through
		  //pick a nice huge round number to let this sucker get warmed up
		  // int howMany = 10000000;

		  int[] allCards = HandMaker.makeLotsOfRandom5CardHands(howMany);
		  
		  //get start time
		  long startT = System.nanoTime();
		  
		  //let er rip, go through every hand, 5 cards at a time
		  for(int i=0;i<allCards.length;i+=5)
			  DeadHorseEval.eval(allCards[i],allCards[i+1],allCards[i+2],allCards[i+3],allCards[i+4]);
		  
		  //get end time
		  long endT = System.nanoTime();
		  
		  
		    
		  // Time is (end time - start time ) divided by a billion : because it is in nano seconds
		  double time = (double) (endT - startT)/1000000000;
		  
		  System.out.println("Did " + howMany + " hands in " + time +" seconds");
		  //hands per second in millions 
		  System.out.println((int)(howMany/time/1000000) + " million hands a second"); 
	  }
	
	  public static void showRandomizerDiagnostics(int howMany){
		  //how many hands do you want to test? choose a few million to be sure..
		 // int howMany = 10000000;

		  int[] allCards = HandMaker.makeLotsOfRandom5CardHands(howMany);
		  
		  //create an array of size 9. 0-8 for each type of hand
		  //this will hold the count of each type of hand that gets created/evaluated
		  int[] handCounter = new int[9];

		  //evaluate every hand, 5 cards at a time
		  for(int i=0;i<allCards.length;i+=5)
			  handCounter[DeadHorseEval.eval(allCards[i],allCards[i+1],allCards[i+2],allCards[i+3],allCards[i+4])]++;
		
		  //this will show the frequence and percentages of each type of hand 
		  //these frequencies can be compared to the actual frequency percentages 
		  //of known poker hands. This just shows the accuracy of the speed test
		  //ths shows the speed test is testing random hands at the same handType/Frequency
		  //you would expect if you tested all possible hands
		  for(int j=0;j<handCounter.length;j++)
				 System.out.println(handNames[j] +" : " + handCounter[j] + " : " + ((double)handCounter[j]/howMany*100) + "%");

	  }
	

public static void randomizerSpeedTest7Card(int howMany){
		  
		  //number of hands to burn through
		  //pick a nice huge round number to let this sucker get warmed up
		  // int howMany = 10000000;

		  int[] allCards = HandMaker.makeLotsOfRandom7CardHands(howMany);
		  //get start time
		  int curWinner=0;
		  long startT = System.nanoTime();
		  
		 
		  //let er rip, go through every hand, 7 cards at a time
		  for(int v=0;v<allCards.length/7;v++)
			  for (int i = 0; i < 7 - 1; i++) 
				  for (int j = i + 1; j < 7; j++) 
					  for (int k = j + 1; k < 7; k++) 
						  for (int l = k + 1; l < 7; l++) 
							  for (int m = l + 1; m < 7; m++) {
								  int current =DeadHorseEval.eval
										(
										  allCards[v*7+i],
										  allCards[v*7+j],
										  allCards[v*7+k],
										  allCards[v*7+l],
										  allCards[v*7+m]
										);
								  if(current>curWinner)curWinner=current;
								}
		  //get end time
		  long endT = System.nanoTime();
		  System.out.println("winning hand  : " + curWinner);
		  
		  // Time is (end time - start time ) divided by a billion : because it is in nano seconds
		  double time = (double) (endT - startT)/1000000000;
		  
		  System.out.println("Did " + howMany + " hands in " + time +" seconds");
		  //hands per second in millions 
		  System.out.println((int)(howMany/time/1000000) + " million hands a second"); 
	  }


public static void handCompareTest(int howMany){
	
	for(int i=0;i<howMany;i++){
	int[] highCard = HandMaker.makeHighCardHand();
	int[] pair = HandMaker.makePairHand();
	int[] twoPair = HandMaker.makeTwoPairHand();
	int[] trips = HandMaker.makeTripHand();
	int[] straight = HandMaker.makeStraightHand();
	int[] flush = HandMaker.makeFlushHand();
	int[] fullhouse = HandMaker.makeFullHouseHand();
	int[] quads = HandMaker.makeQuadsHand();
	int[] straightFlush = HandMaker.makeStraightFlushHand();
	
	int hc = DeadHorseEval.eval(highCard[0], highCard[1], highCard[2], highCard[3], highCard[4]);
	int p = DeadHorseEval.eval(pair[0], pair[1], pair[2], pair[3], pair[4]);
	int tp = DeadHorseEval.eval(twoPair[0], twoPair[1], twoPair[2], twoPair[3], twoPair[4]);
	int t = DeadHorseEval.eval(trips[0], trips[1], trips[2], trips[3], trips[4]);
	int s = DeadHorseEval.eval(straight[0], straight[1], straight[2], straight[3], straight[4]);
	int f = DeadHorseEval.eval(flush[0], flush[1], flush[2], flush[3], flush[4]);
	int fh = DeadHorseEval.eval(fullhouse[0], fullhouse[1], fullhouse[2], fullhouse[3], fullhouse[4]);
	int q = DeadHorseEval.eval(quads[0], quads[1], quads[2], quads[3], quads[4]);
	int sf = DeadHorseEval.eval(straightFlush[0], straightFlush[1], straightFlush[2], straightFlush[3], straightFlush[4]);
	boolean check = hc<p && p<tp && tp<t && t<s && s<f && f<fh && fh<q && q<sf;
	if(!check){
		System.out.println("Failed! " + hc+","+p+","+tp+","+t+","+s+","+f+","+fh+","+q+","+sf);
	
		System.out.println("hc " + bin(highCard[0])+ ","+bin(highCard[1])+","+ bin(highCard[2])+","+ bin(highCard[3])+","+ bin(highCard[4]));
	 
	}
}
	
/*	
	System.out.println("High Card \t" + hc);
	System.out.println("Pair \t\t" + p);
	System.out.println("Two Pair \t" + tp);
	System.out.println("Trips \t\t" + t);
	System.out.println("Straight \t" + s);
	System.out.println("Flush \t\t" + f);
	System.out.println("Full House \t" + fh);
	System.out.println("Quads \t\t" + q);
	System.out.println("Straight Flush \t"  + sf);*/
}


public static String bin(int i){
	return String.format("%17s", Integer.toBinaryString(i)).replace(' ', '0');
}
	  
}
