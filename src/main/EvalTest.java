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
		  for(int i=0;i<allCards.length;i+=5)
			handCounter[DeadHorseEval.eval(allCards[i],allCards[i+1],allCards[i+2],allCards[i+3],allCards[i+4])]++;
		  
		  
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
			   
			   System.out.println(res+"\n" + ((double)handCounter[j]/totalCounter*100) + "%\n");
		   }
			 
		  System.out.println("Total Count : " + totalCounter);
		  
	  }

	public static void randomizerSpeedTest(int howMany){
		  
		  //number of hands to burn through
		  //pick a nice huge round number to let this sucker get warmed up
		  // int howMany = 10000000;

		  int[] allCards = HandMaker.makeLotsOfRandomHands(howMany);
		  
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

		  int[] allCards = HandMaker.makeLotsOfRandomHands(howMany);
		  
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
	
}
