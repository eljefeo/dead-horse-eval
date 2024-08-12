package main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

import static main.util.handNames;
import static main.util5.*;
import static main.util7.handFrequency;

public class EvalTestPlayground {
	
	static File[] files={
		new File("allHC7.txt"),
		new File("allP7.txt"), 
		new File("allTP7.txt"),
		new File("allT7.txt"),
		new File("allS7.txt"),
		new File("allF7.txt"),
		new File("allFH7.txt"),
		new File("allQ7.txt"),
		new File("allSF7.txt")
	};







	public static long[] createAllSevenCardHands(){

		long[] allCards = new long[util7.total7CardHandCount*7];
		int totalCounter=0;

		long[] cards52_7 = util7.all52CardsDecimal;//DeadHorse7.makeAll52Cards7Decimal();

//668.922.800

		for(int i=0;i<cards52_7.length-1;i++)
			for(int j=i+1;j<cards52_7.length;j++)
				for(int k=j+1;k<cards52_7.length;k++)
					for(int l=k+1;l<cards52_7.length;l++)
						for(int m=l+1;m<cards52_7.length;m++)
							for(int n=m+1;n<cards52_7.length;n++)
								for(int o=n+1;o<cards52_7.length;o++) {
									allCards[totalCounter * 7] = cards52_7[i];
									allCards[totalCounter * 7 + 1] = cards52_7[j];
									allCards[totalCounter * 7 + 2] = cards52_7[k];
									allCards[totalCounter * 7 + 3] = cards52_7[l];
									allCards[totalCounter * 7 + 4] = cards52_7[m];
									allCards[totalCounter * 7 + 5] = cards52_7[n];
									allCards[totalCounter * 7 + 6] = cards52_7[o];
									totalCounter++;
								}
		System.out.println("Created " + totalCounter + " hands");
		return allCards;
	}
	


	  public static int[] createAllFiveCardHands(){
		  
		  int[] allCards = new int[total5CardHandCount*5];
		  int totalCounter=0;
		  
		  //this is how we create every possible 5 card hand
		  for(int i = 0; i< all52CardsDecimal.length-1; i++)
			  for(int j = i+1; j< all52CardsDecimal.length; j++)
				  for(int k = j+1; k< all52CardsDecimal.length; k++)
					  for(int l = k+1; l< all52CardsDecimal.length; l++)
						  for(int m = l+1; m< all52CardsDecimal.length; m++){
							  allCards[totalCounter*5]	= all52CardsDecimal[i];
							  allCards[totalCounter*5+1]= all52CardsDecimal[j];
							  allCards[totalCounter*5+2]= all52CardsDecimal[k];
							  allCards[totalCounter*5+3]= all52CardsDecimal[l];
							  allCards[totalCounter*5+4]= all52CardsDecimal[m];
							  totalCounter++;
						  }
		 return allCards;
	  }
	  
	  //there is a speed test method below
	  //but in case you want the good ol fashion 'create every single possible hand'
	  //here you go

public static void testStatisticsOfEachHand(int howManyToRun){ // 0 = high card, 1 = pair, 2 = 2pair etc..
	  /*
	   * Goal with this is to run a whole bunch of hands and then check how often we get a type of hand vs expected 
	   * so theres 2598960 different hands in a 5 card poker game/deck
	   * 1302540 ways to make a high card hand 
	   * 1098240 ways to make a pair
	   * 123552 ways to make a two-pair
	   * 54912 ways to make a 3 of a kind
	   * 10200 ways to make a straight
	   * 5108 ways to make a flush
	   * 3744 ways to make a full house
	   * 624 ways to make a 4 of a kind
	   * 40 ways to make a straight flush
	   * 
	   */
	
	//please write code here now to do magic math stats stuff...
	/*
	int counter = 0;
	while(true) {
		counter++;
		int[] fiveCards = HandMaker.getRandom5CardHand();
		int res = DeadHorse.eval5(fiveCards);
		res>>=26;
		if(res > whatKind) {
		  System.out.println("Finally got " + handNames[res] + " after " + counter +" hands");
		  return;
		}
	}
	*/
}
	  public static void testStatisticsManyHand5(int howManyHands){

		  String thisManyString = NumberFormat.getNumberInstance(Locale.US).format(howManyHands);
		  System.out.println("Doing this many hands: " + thisManyString);
		  int[] allCards = HandMakerFiveCard.makeThisManyRandom5CardHands(howManyHands);
		  int totalHandsNow = allCards.length / 5; 
		  int[] handCounter = new int[9];
		  double[] frequencyPerc = new double[handFrequency.length];
		  
		 
		  
		  for(int i=0;i<allCards.length;i+=5){
			  int res = DeadHorse.eval5(allCards[i],allCards[i+1],
					  allCards[i+2],allCards[i+3],allCards[i+4])>>26;
			  handCounter[res]++;
		  }
		  
		  System.out.println("\nNow our hands:");
		  for(int i = 0; i< handFrequency.length; i++) {
			  //frequencyPerc[i] = handFrequency5[i]/(double)totalHands5;total5CardHandCount
			  frequencyPerc[i] = handFrequency[i] / (double)total5CardHandCount;
			  System.out.println("hand frequency of " + handNames[i] + " : (had this many in this test: " + handCounter[i] + ")");
			  System.out.println("expected:\t" + (frequencyPerc[i]*100) + "%");
			  System.out.println("Actual:  \t" + ((double)( handCounter[i]/(double)totalHandsNow)*100) + "%");
			  System.out.println();
		  }
		  
		  
		  //System.out.println(res+" " + ((double)handCounter[j]/totalCounter*100) + "%");
	  }
	  
	  public static void testEveryHand5(){
		  
		  //copy of the deck to make this method more portable

		  double allHandPossibilities = 0.0;
		 int totalHands = 2598960;
		 /*int[] allCards = new int[totalHands*5];
		  int totalCounter=0;
		  
		  //this is how we create every possible 5 card hand
		  for(int i=0;i<allCardNums.length-1;i++)
			  for(int j=i+1;j<allCardNums.length;j++)
				  for(int k=j+1;k<allCardNums.length;k++)
					  for(int l=k+1;l<allCardNums.length;l++)
						  for(int m=l+1;m<allCardNums.length;m++){
							  allCards[totalCounter*5]	=allCardNums[i];
							  allCards[totalCounter*5+1]=allCardNums[j];
							  allCards[totalCounter*5+2]=allCardNums[k];
							  allCards[totalCounter*5+3]=allCardNums[l];
							  allCards[totalCounter*5+4]=allCardNums[m];
							  totalCounter++;
						  }*/
		  int[] allCards = createAllFiveCardHands();
		  int totalCounter= allCards.length/5;
		  
		  //get start time
		  long startT = System.nanoTime();
		  //go through every hand, 5 cards at a time
		  for(int i=0;i<allCards.length;i+=5)
			 	DeadHorse.eval5(allCards[i],allCards[i+1],allCards[i+2],allCards[i+3],allCards[i+4]);
		  //get end time
		  long endT = System.nanoTime();
		  
		  // Time = (end time - start time ) divided by a billion : because it is in nano seconds
		  double time = (double) (endT - startT)/1000000000;
		  System.out.println("Did all " + totalHands + " hands in " + time +" seconds");
		  //hands per second in millions 
		  Double currentTotal = totalHands/time/1000000;
		  //allHandPossibilities += currentTotal;
		  System.out.println(currentTotal + " million hands a second\n");
		  //System.out.println("Running Total : " + allHandPossibilities + " percent complete");
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
			
			  int res = DeadHorse.eval5(allCards[i],allCards[i+1],
					  allCards[i+2],allCards[i+3],allCards[i+4])>>26;
			  handCounter[res]++;
		  
		  }
		  //this is to go through and compare the number of each type of hand we created
		  //to the number of each type of hand we expect
		   for(int j=0;j<handCounter.length;j++){
			   //check if expected == actual
			   boolean checked = handCounter[j]== handFrequency[j];
			   String res = checked
					   //if all hands accounted for, good news
					   ?"All "+ handFrequency[j]+" "+handNames[j]+" hands are accounted for"
							   
						//if the counts dont match, show how many failed
					   : (handCounter[j]- handFrequency[j])+" of "+ handFrequency[j]
							   +" "+handNames[j]+" hands failed!"; 
			   
			   System.out.println(res+" " + ((double)handCounter[j]/totalCounter*100) + "%");
		   }
			 
		  System.out.println("Total Count : " + totalCounter);
		  
	  }

	  
	  public static void testEveryHand7(){

		  //copy of the deck to make this method more portable
		  ArrayList[] lists = {
				  new ArrayList<String>(),
				  new ArrayList<String>(),
				  new ArrayList<String>(),
				  new ArrayList<String>(),
				  new ArrayList<String>(),
				  new ArrayList<String>(),
				  new ArrayList<String>(),
				  new ArrayList<String>(),
				  new ArrayList<String>()
		  };
		   int totalHands = 133784560;
		 // int[] allCards = new int[totalHands*7];
		  int totalCounter=0;
		  int[] handCounter = new int[9];
		  //this is how we create all 133,784,560 7 card hands
		  for(int i = 0; i< all52CardsDecimal.length-1; i++)
			  for(int j = i+1; j< all52CardsDecimal.length; j++)
				  for(int k = j+1; k< all52CardsDecimal.length; k++)
					  for(int l = k+1; l< all52CardsDecimal.length; l++)
						  for(int m = l+1; m< all52CardsDecimal.length; m++)
							  for(int n = m+1; n< all52CardsDecimal.length; n++)
								  for(int o = n+1; o< all52CardsDecimal.length; o++){
									  
									  int res = oldBad7Code.eval7
											  (
											  	all52CardsDecimal[i],
											  	all52CardsDecimal[j],
											  	all52CardsDecimal[k],
											  	all52CardsDecimal[l],
											  	all52CardsDecimal[m],
											  	all52CardsDecimal[n],
											  	all52CardsDecimal[o]
											  )>>26;
											  	
									  
									
													  
								 /* String hand=	allCardNums[i]+","+
										  		allCardNums[j]+","+
												allCardNums[k]+","+
												allCardNums[l]+","+
												allCardNums[m]+","+
												allCardNums[n]+","+
												allCardNums[o];
								  */
								 /* lists[res].add(hand);
								  
								  if(lists[res].size()==1000000){
									  atfl(files[res],lists[res]);
									  lists[res].clear();
								  }*/
								  	
								  	  handCounter[res]++;
									  totalCounter++;
						  }
		 /* for(int i = 0;i<lists.length;i++)
			  if(lists[i].size()>0)
				atfl(files[i],lists[i]);*/

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
		  
		  //this is to go through and compare the number of each type of hand we created
		  //to the number of each type of hand we expect

		   for(int j=0;j<handCounter.length;j++){
			   //check if expected == actual
			   boolean checked = handCounter[j]== util7.handFrequency[j];
			   String res = checked
					   //if all hands accounted for, good news
					   ?"PASS - All "+ util7.handFrequency[j]+" "+handNames[j]+" hands are accounted for"
							   
						//if the counts dont match, show how many failed
					   : "FAIL - " +handCounter[j]+" out of "+ util7.handFrequency[j]
							   +" "+handNames[j]+" hands received!"; 
			   
			   System.out.println(res+" " + ((double)handCounter[j]/totalCounter*100) + "%");
		   }
			 
		  System.out.println("Total Count : " + totalCounter);
		  
	  }
	  
	  

	  


	  
	public static void randomizerSpeedTest5CardDiagnostics(int howMany){
		  
		  int[] allCards = HandMakerFiveCard.makeThisManyRandom5CardHands(howMany);
		  
		  for(int i=0;i<allCards.length;i+=5){
			int res = DeadHorse.eval5(allCards[i],allCards[i+1],allCards[i+2],allCards[i+3],allCards[i+4]);
			res>>=26;
		  	System.out.println(res+", "+handNames[res]);
		  }
	  }
	
	
	public static void randomizerSpeedTest5Card(int howMany){
		  
		  //number of hands to burn through
		  //pick a nice huge round number to let this sucker get warmed up
		  // int howMany = 10000000;
		System.out.println("Making " + howMany + " hands...");
		int[] allCards = HandMakerFiveCard.makeThisManyRandom5CardHands(howMany);
		  System.out.println("...Done making " + howMany + " hands");
		  System.out.println("...Evaluating " + howMany + " hands");
		  long startT = System.nanoTime();
		  
		  //let er rip, go through every hand, 5 cards at a time
		  for(int i=0;i<allCards.length;i+=5)
			  DeadHorse.eval5(allCards[i],allCards[i+1],allCards[i+2],allCards[i+3],allCards[i+4]);
		  long endT = System.nanoTime();
		  double time = (double) (endT - startT )/1000000000;
		  
		  System.out.println("Did " + howMany + " hands in " + time +" seconds");
		  System.out.println((int)(howMany/time/1000000) + " million hands a second"); 
	  }
	
	  public static void showRandomizerDiagnostics(int howMany){
		  //how many hands do you want to test? choose a few million to be sure..
		 // int howMany = 10000000;

		  int[] allCards = HandMakerFiveCard.makeThisManyRandom5CardHands(howMany);
		  
		  //create an array of size 9. 0-8 for each type of hand
		  //this will hold the count of each type of hand that gets created/evaluated
		  int[] handCounter = new int[9];

		  //evaluate every hand, 5 cards at a time
		  for(int i=0;i<allCards.length;i+=5)
			  handCounter[DeadHorse.eval5(allCards[i],allCards[i+1],allCards[i+2],allCards[i+3],allCards[i+4])]++;
		
		  //this will show the frequency and percentages of each type of hand 
		  //these frequencies can be compared to the actual frequency percentages 
		  //of known poker hands. This just shows the accuracy of the speed test
		  //ths shows the speed test is testing random hands at the same handType/Frequency
		  //you would expect if you tested all possible hands
		  for(int j=0;j<handCounter.length;j++)
				 System.out.println(handNames[j] +" : " + handCounter[j] + " : " + ((double)handCounter[j]/howMany*100) + "%");

	  }

public static void handCompareTest(int howMany) throws Exception {
	//Failed! 167772194,67117224,134299776,218138628,268437440,335544320,406848512,470810640,536872896
	for(int i=0;i<howMany;i++){
		
		System.out.println("On #" + i);
		
	int[] highCard = HandMakerFiveCard.makeHighCardHand();
	int[] pair = HandMakerFiveCard.makePairHand();
	int[] twoPair = HandMakerFiveCard.makeTwoPairHand();
	int[] trips = HandMakerFiveCard.makeTripHand();
	int[] straight = HandMakerFiveCard.makeStraightHand();
	int[] flush = HandMakerFiveCard.makeFlushHand();
	int[] fullhouse = HandMakerFiveCard.makeFullHouseHand();
	int[] quads = HandMakerFiveCard.makeQuadsHand();
	int[] straightFlush = HandMakerFiveCard.makeStraightFlushHand();
	
	int hc = DeadHorse.eval5(highCard[0], highCard[1], highCard[2], highCard[3], highCard[4]);
	int p = DeadHorse.eval5(pair[0], pair[1], pair[2], pair[3], pair[4]);
	int tp = DeadHorse.eval5(twoPair[0], twoPair[1], twoPair[2], twoPair[3], twoPair[4]);
	int t = DeadHorse.eval5(trips[0], trips[1], trips[2], trips[3], trips[4]);
	int s = DeadHorse.eval5(straight[0], straight[1], straight[2], straight[3], straight[4]);
	int f = DeadHorse.eval5(flush[0], flush[1], flush[2], flush[3], flush[4]);
	int fh = DeadHorse.eval5(fullhouse[0], fullhouse[1], fullhouse[2], fullhouse[3], fullhouse[4]);
	int q = DeadHorse.eval5(quads[0], quads[1], quads[2], quads[3], quads[4]);
	int sf = DeadHorse.eval5(straightFlush[0], straightFlush[1], straightFlush[2], straightFlush[3], straightFlush[4]);
	boolean check = hc<p && p<tp && tp<t && t<s && s<f && f<fh && fh<q && q<sf;
	
	
	if(hc<p && hc<tp && hc<t && hc<s && hc<f && hc<fh && hc<q && hc<sf){
		//System.out.println("High card pass");
	} else{
		if(highCard[0] != 0)
			System.out.println("High card failed " + getCardName5(highCard[0]) + " " + getCardName5(highCard[1]) + " " + getCardName5(highCard[2]) + " " + getCardName5(highCard[3]) + " " + getCardName5(highCard[4]) );
	}
	if(p<tp && p<t && p<s && p<f && p<fh && p<q && p<sf){
		//System.out.println("Pair pass");
	} else{
		System.out.println("Pair failed " + getCardName5(pair[0]) + " " + getCardName5(pair[1]) + " " + getCardName5(pair[2]) + " " + getCardName5(pair[3]) + " " + getCardName5(pair[4]) );
	}
	if(tp<t && tp<s && tp<f && tp<fh && tp<q && tp<sf){
		//System.out.println("TwoPair pass");
	} else{
		System.out.println("TwoPair failed " + getCardName5(twoPair[0]) + " " + getCardName5(twoPair[1]) + " " + getCardName5(twoPair[2]) + " " + getCardName5(twoPair[3]) + " " + getCardName5(twoPair[4]) );
	}
	if(t<s && t<f && t<fh && t<q && t<sf){
		//System.out.println("Trips pass");
	} else{
		System.out.println("Trips failed " + getCardName5(trips[0]) + " " + getCardName5(trips[1]) + " " + getCardName5(trips[2]) + " " + getCardName5(trips[3]) + " " + getCardName5(trips[4]) );
	}
	if(s<f && s<fh && s<q && s<sf){
		//System.out.println("Straight pass");
	} else{
		System.out.println("Straight failed " + getCardName5(straight[0]) + " " + getCardName5(straight[1]) + " " + getCardName5(straight[2]) + " " + getCardName5(straight[3]) + " " + getCardName5(straight[4]) );
	}
	if(f<fh && f<q && f<sf){
		//System.out.println("Flush pass");
	} else{
		System.out.println("Flush failed " + getCardName5(flush[0]) + " " + getCardName5(flush[1]) + " " + getCardName5(flush[2]) + " " + getCardName5(flush[3]) + " " + getCardName5(flush[4]) );
	}
	if(fh<q && fh<sf){
		//System.out.println("FullHouse pass");
	} else{
		System.out.println("FullHouse failed " + getCardName5(fullhouse[0]) + " " + getCardName5(fullhouse[1]) + " " + getCardName5(fullhouse[2]) + " " + getCardName5(fullhouse[3]) + " " + getCardName5(fullhouse[4]) );
	}

	
	
	
	
	
	
	
	
	
	
	if(!check){
		System.out.println("Failed! " + hc+","+p+","+tp+","+t+","+s+","+f+","+fh+","+q+","+sf);
	
		//System.out.println("hc " + bin(highCard[0])+ ","+bin(highCard[1])+","+ bin(highCard[2])+","+ bin(highCard[3])+","+ bin(highCard[4]));
	 
	}
	else{
	//	System.out.println("PASS");
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

// can send user readable strings into here like 'As or Jc or 9h'..
//capital letter for face cards and lower case letter for suit --- this method is old, should delete?
/*public  static int humanEncodeEvalLowerNoTen(String as, String bs, String cs, String ds, String es){

	//convert string to numbers that the eval recognizes
	char ac=as.charAt(0),bc=bs.charAt(0),cc=cs.charAt(0),dc=ds.charAt(0),ec=es.charAt(0);
	int a=((ac=='A'?1<<12:ac=='K'?1<<11:ac=='Q'?1<<10:ac=='J'?1<<9:1<<(ac-50))
	|((ac=as.charAt(1))=='s'?0x10000:ac=='h'?0x8000:ac=='c'?0x4000:0x2000));
	
	int b=((bc=='A'?1<<12:bc=='K'?1<<11:bc=='Q'?1<<10:bc=='J'?1<<9:1<<(bc-50))		
	|((bc=bs.charAt(1))=='s'?0x10000:bc=='h'?0x8000:bc=='c'?0x4000:0x2000));
	
	int c=((cc=='A'?1<<12:cc=='K'?1<<11:cc=='Q'?1<<10:cc=='J'?1<<9:1<<(cc-50))
	|((cc=cs.charAt(1))=='s'?0x10000:cc=='h'?0x8000:cc=='c'?0x4000:0x2000));
	
	int d=((dc=='A'?1<<12:dc=='K'?1<<11:dc=='Q'?1<<10:dc=='J'?1<<9:1<<(dc-50))
	|((dc=ds.charAt(1))=='s'?0x10000:dc=='h'?0x8000:dc=='c'?0x4000:0x2000));
	
	int e=((ec=='A'?1<<12:ec=='K'?1<<11:ec=='Q'?1<<10:ec=='J'?1<<9:1<<(ec-50))
	|((ec=es.charAt(1))=='s'?0x10000:ec=='h'?0x8000:ec=='c'?0x4000:0x2000));

	return DeadHorse.eval5(a, b, c, d, e);
}*/


public static void randomizerSpeedTest7Card(int howMany){
	  
	  //number of hands to burn through
	  //pick a nice huge round number to let this sucker get warmed up
	  // int howMany = 10000000;
	
	

	  long[] ac = util7.makeThisManyRandom7CardHands(howMany);
	  //get start time
	  long startTf = System.nanoTime();
	  
	  //these dang for loops take a while
	  for(int v=0;v<ac.length/7;) v++;
	  //get end time
	  long endTf = System.nanoTime();
	  long startT = System.nanoTime();
	  
	  for(int v=0;v<ac.length/7;v++)
		  DeadHorse7.eval7(ac[(v*7)],ac[(v*7)+1],ac[(v*7)+2],ac[(v*7)+3],ac[(v*7)+4],ac[(v*7)+5],ac[(v*7)+6]);


	  //get end time
	  long endT = System.nanoTime();
	  
	  // Time is (end time - start time  ) divided by a billion : because it is in nano seconds
	  double time = (double) (endT - startT -(endTf - startTf))/1000000000;
	  
	  System.out.println("Did " + howMany + " hands in " + time +" seconds");
	  //hands per second in millions 
	  System.out.println((int)(howMany/time/1000000) + " million hands a second"); 
}

public static void randomizerSpeedTest7nCard(int howMany){
	  
	  //number of hands to burn through
	  //pick a nice huge round number to let this sucker get warmed up
	  // int howMany = 10000000;
	
	

	  long[] ac = util7.makeThisManyRandom7CardHands(howMany);
	  //get start time
	  long startTf = System.nanoTime();
	  
	  //these dang for loops take a while
	  for(int v=0;v<ac.length/7;) v++;
	  //get end time
	  long endTf = System.nanoTime();
	  long startT = System.nanoTime();
	  
	  for(int v=0;v<ac.length/7;v++)
		  DeadHorse7.eval7(ac[(v*7)],ac[(v*7)+1],ac[(v*7)+2],ac[(v*7)+3],ac[(v*7)+4],ac[(v*7)+5],ac[(v*7)+6]);


	  //get end time
	  long endT = System.nanoTime();
	  
	  // Time is (end time - start time  ) divided by a billion : because it is in nano seconds
	  double time = (double) (endT - startT -(endTf - startTf))/1000000000;
	  
	  System.out.println("Did " + howMany + " hands in " + time +" seconds");
	  //hands per second in millions 
	  System.out.println((int)(howMany/time/1000000) + " million hands a second"); 
}








public static void atf(File f, String s){
	if(!f.exists())try{f.createNewFile();}catch(IOException e1){e1.printStackTrace();}
	
	BufferedWriter bw=null;
	try {
		bw = new BufferedWriter(new FileWriter(f, true));
		bw.write(s+"\n");
	} catch (IOException e) {e.printStackTrace();}
	finally{if(bw!=null){try{bw.close();}catch(Exception ee){}}}
}

public static void atfl(File f, ArrayList<String>l){
	if(!f.exists())try{f.createNewFile();}catch(IOException e1){e1.printStackTrace();}
	
	BufferedWriter bw=null;
	try {
		bw = new BufferedWriter(new FileWriter(f, true));
		for(String s : l)
		bw.write(s+"\n");
	} catch (IOException e) {e.printStackTrace();}
	finally{if(bw!=null){try{bw.close();}catch(Exception ee){}}}
}

public static void doSuitTest(){
	
	
	//int[] addsuits = new int[]{8192>>13,65536>>13,1048576>>13,33554432>>13};
	int[] addsuits = new int[]{20480,49152,458752,7864320};
System.out.println(addsuits[0] + " " + addsuits[1] + " " + addsuits[2] + " " + addsuits[3]);
	
	int[] suits = new int[]{65536,65536,65536,65536,65536,65536,65536,65536,65536,65536,65536,65536,65536,32768,32768,32768,32768,32768,32768,32768,32768,32768,32768,32768,32768,32768,16384,16384,16384,16384,16384,16384,16384,16384,16384,16384,16384,16384,16384,8192,8192,8192,8192,8192,8192,8192,8192,8192,8192,8192,8192,8192}; 
	int mask = 122880;
	int allS =143165576;
	int allH = 71582788;
	int allC = 35791394;
 	int allD = 17895697;
 	int sone = 134217728;
 	int hone = 67108864;
 	int cone  =33554432;
 	int done = 16777216;
 	int[] test = new int[29];
 	
 	int count = 0;
 	
 /*	
 	int allT = allS;
		int tone = sone;
 	
 	for(int i = 0; i < 7-1; i ++){
 		for(int j = i+1; j < 7;j++){
 			//System.out.println( i + " " + j);
 			//System.out.println(bin32(sone>>(i*4)|sone>>(j*4)));
 			

 			int num = allT^(tone>>(i*4)|tone>>(j*4));
 			System.out.println(num);
 			test[count++] = num;
 		}
 	}
 	for(int i=0;i<7;i++){
 		test[i+21]=allT^(tone>>(i*4));
 	}
 	test[28] = allT;
*/
 	
 	
/*	for(int i : test){
 		System.out.println("mod S " + allS + " "+ i + " = " + ((allS^i)));
 		
 	}
 	
 	for(int i : test){
 		System.out.println("mod H " + allH + " "+ i + " = " + ((allH^i)));
 	}
 	
	for(int i : test){
 		System.out.println("mod C " + allC + " "+ i + " = " + ((allC^i)));
 	}
	
	for(int i : test){
 		System.out.println("mod D " + allD + " "+ i + " = " + ((allD^i)));
 	}
	*/
	ArrayList<Integer> totals = new ArrayList<Integer>();
 	
	for(int i=0;i<addsuits.length;i++)
		  for(int j=0;j<addsuits.length;j++)
			  for(int k=0;k<addsuits.length;k++)
				  for(int l=0;l<addsuits.length;l++)
					  for(int m=0;m<addsuits.length;m++)
						  for(int n=0;n<addsuits.length;n++)
							  for(int o=0;o<addsuits.length;o++)
				  {
								  
								  int aa = 0, bb= 0 , cc = 0, dd = 0;
								  if(addsuits[i]==addsuits[0])aa++;
								  else if(addsuits[i]==addsuits[1])bb++;
								  else if(addsuits[i]==addsuits[2])cc++;
								  else if(addsuits[i]==addsuits[3])dd++;
								  else System.out.println("uh oh...");

								  if(addsuits[j]==addsuits[0])aa++;
								  else if(addsuits[j]==addsuits[1])bb++;
								  else if(addsuits[j]==addsuits[2])cc++;
								  else if(addsuits[j]==addsuits[3])dd++;
								  else System.out.println("uh oh...");

								  if(addsuits[k]==addsuits[0])aa++;
								  else if(addsuits[k]==addsuits[1])bb++;
								  else if(addsuits[k]==addsuits[2])cc++;
								  else if(addsuits[k]==addsuits[3])dd++;
								  else System.out.println("uh oh...");

								if(addsuits[l]==addsuits[0])aa++;
								  else if(addsuits[l]==addsuits[1])bb++;
								  else if(addsuits[l]==addsuits[2])cc++;
								  else if(addsuits[l]==addsuits[3])dd++;
								  else System.out.println("uh oh...");

								if(addsuits[m]==addsuits[0])aa++;
								  else if(addsuits[m]==addsuits[1])bb++;
								  else if(addsuits[m]==addsuits[2])cc++;
								  else if(addsuits[m]==addsuits[3])dd++;
								  else System.out.println("uh oh...");

								if(addsuits[n]==addsuits[0])aa++;
								  else if(addsuits[n]==addsuits[1])bb++;
								  else if(addsuits[n]==addsuits[2])cc++;
								  else if(addsuits[n]==addsuits[3])dd++;
								  else System.out.println("uh oh...");

								if(addsuits[o]==addsuits[0])aa++;
								  else if(addsuits[o]==addsuits[1])bb++;
								  else if(addsuits[o]==addsuits[2])cc++;
								  else if(addsuits[o]==addsuits[3])dd++;
								  else System.out.println("uh oh...");
								  
								
								int total= addsuits[i] + addsuits[j] + addsuits[k]+ addsuits[l]
										  + addsuits[m] + addsuits[n]  + addsuits[o];
								
								if(aa > 4 || bb > 4 || cc > 4 || dd > 4)
								//if(aa <= 4 && bb <= 4 && cc <= 4 && dd <= 4)
								//if((total-addsuits[0]*5)%7==2 || (total-addsuits[1]*5)%7==2 || (total-addsuits[2]*5)%7==2 || (total-addsuits[3]*5)%7==2)
									
								{
									  
									  //if(total/7 >0 && total/7 < 100)
									  {
										  if(!totals.contains(total)){
											  totals.add(total);
										  
											  
											  int cou = 0;
											  int v = total;
											  
											  while(v!=0){v&=v-1;  cou++;}
										  System.out.println("********** Bit count = " +  cou);
										  System.out.println(total + " " + (total-40960));
										  System.out.println(addsuits[i] + " " + addsuits[j] + " " + addsuits[k]+ " " + addsuits[l]
												  + " " + addsuits[m] + " " + addsuits[n]  + " " + addsuits[o]);
									  
										  }
									  }
								}
								
// 20971520   2621440  327680 40910
								  
								 //lowest highest 1 flush =7, 1029
								
								//lowest highest 8 flush = 82, large = 4176
								
								//lowest highest cc small = 1282, large = 5376
								
								// lowest highes dd  10242, large = 14336
								  
				  }
	
	int counte =0;
	int small = totals.get(0), large = 0;
	Collections.sort(totals);
	for(Integer i : totals){
		//System.out.println("mod7 " + i%12);
		if(i < small)small = i;
		if(i > large)large= i;
		counte++;
		int s1 = i - addsuits[0]*5;
		int s2 = i - addsuits[1]*5;
		int s3 = i - addsuits[2]*5;
		int s4 = i - addsuits[3]*5;
int modd = 116;
		//System.out.println((s1%modd) + ", " + (s2%modd) +", " + (s3%modd) + ", " + (s4%modd) + " " + i%modd);
	}
	//System.out.println(counte + " small = " + small + ", large = " + large);
	
	
	for(int i : addsuits)
		for(int j : addsuits){
			//System.out.println((i+j)%5);
		}
	
	/*
	 for(int i=0;i<allCardNums.length-1;i++)
		  for(int j=i+1;j<allCardNums.length;j++)
			  for(int k=j+1;k<allCardNums.length;k++)
				  for(int l=k+1;l<allCardNums.length;l++)
					  for(int m=l+1;m<allCardNums.length;m++)
						  for(int n=m+1;n<allCardNums.length;n++)
							  for(int o=n+1;o<allCardNums.length;o++){
								  int res = 
										  (
												  	(allCardNums[i]&mask)>>13|
												  	(allCardNums[j]&mask)>>9|
												  	(allCardNums[k]&mask)>>5|
												  	(allCardNums[l]&mask)>>1|
												  	(allCardNums[m]&mask)<<3|
												  	(allCardNums[n]&mask)<<7|
												  	(allCardNums[o]&mask)<<11
										  );
								  System.out.println(res + "\t" + bin32(res));
								  
							  }
	*/
	

}

public static void createsomethingsomethingidk(int[] cards){
	// how the hell can we test the evaluator to 100% make sure every result is actually correct
	// currently we are able to produce every 5 card combination, and it seems after running all through the evaluator
	//	that we have the correct counts of every hand, but can we be sure?
	// if we can create a way to produce every single type of hand, then run them all through the evaluator and make sure they all get the right results.
	
	// so how do we create every possible pair for example?
	// or what if we just create a way to verify if a hand is a pair, and a hand is a trips etc...that might be better.
	// then we can just create every hand (which we already can do) and somehow verify
	// 1. create every hand
	// 2. as we create every hand, run that hand through evaluator, if it claims to comes back a pair, 
		// run that same hand immediately through this method to validate it really is a pair and nothing else...
	
	
	int[] allCards = createAllFiveCardHands();
	int totalCounter= allCards.length;
	
	
}


	public static void testEval5AndNotes() {






		//copy of the deck to make this method more portable

		double allHandPossibilities = 0.0;
		int totalHands = 2598960;
		 /*int[] allCards = new int[totalHands*5];
		  int totalCounter=0;

		  //this is how we create every possible 5 card hand
		  for(int i=0;i<allCardNums.length-1;i++)
			  for(int j=i+1;j<allCardNums.length;j++)
				  for(int k=j+1;k<allCardNums.length;k++)
					  for(int l=k+1;l<allCardNums.length;l++)
						  for(int m=l+1;m<allCardNums.length;m++){
							  allCards[totalCounter*5]	=allCardNums[i];
							  allCards[totalCounter*5+1]=allCardNums[j];
							  allCards[totalCounter*5+2]=allCardNums[k];
							  allCards[totalCounter*5+3]=allCardNums[l];
							  allCards[totalCounter*5+4]=allCardNums[m];
							  totalCounter++;
						  }*/
		int[] allCards = createAllFiveCardHands();
		int totalCounter= allCards.length/5;

		//get start time
		long startT = System.nanoTime();
		//go through every hand, 5 cards at a time
		for(int i=0;i<allCards.length;i+=5){
			DeadHorse.eval5(allCards[i],allCards[i+1],allCards[i+2],allCards[i+3],allCards[i+4]);
			//int res1 = DeadHorse.eval5(allCards[i],allCards[i+1],allCards[i+2],allCards[i+3],allCards[i+4]);
			//int res2 = DeadHorse.eval5original(allCards[i],allCards[i+1],allCards[i+2],allCards[i+3],allCards[i+4]);
			//if(res1 != res2){
			//	System.out.println("ERROR: " + res1 + " : " + res2);
			//}
		}

		//get end time
		long endT = System.nanoTime();

		// Time = (end time - start time ) divided by a billion : because it is in nano seconds
		double time = (double) (endT - startT)/1000000000;
		System.out.println("Did all " + totalHands + " hands in " + time +" seconds");
		//hands per second in millions
		Double currentTotal = totalHands/time/1000000;
		//allHandPossibilities += currentTotal;
		System.out.println(currentTotal + " million hands a second\n");
		//System.out.println("Running Total : " + allHandPossibilities + " percent complete");
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

			int res = DeadHorse.eval5(allCards[i],allCards[i+1],
					allCards[i+2],allCards[i+3],allCards[i+4])>>26;
			handCounter[res]++;

		}
		//this is to go through and compare the number of each type of hand we created
		//to the number of each type of hand we expect
		for(int j=0;j<handCounter.length;j++){
			//check if expected == actual
			boolean checked = handCounter[j]== handFrequency[j];
			String res = checked
					//if all hands accounted for, good news
					?"All "+ handFrequency[j]+" "+handNames[j]+" hands are accounted for"

					//if the counts dont match, show how many failed
					: (handCounter[j]- handFrequency[j])+" of "+ handFrequency[j]
					+" "+handNames[j]+" hands failed!";

			System.out.println(res+" " + ((double)handCounter[j]/totalCounter*100) + "%");
		}

		System.out.println("Total Count : " + totalCounter);

	}
}
