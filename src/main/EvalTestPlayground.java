package main;

import java.awt.List;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

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
	
	static String[] handNames = 
		{
			"High Card", "Pair", "Two Pair", "3 of a kind", 
			"Straight", "Flush", "Full House", "4 of a kind", "Straight Flush"
		};
	
	
	static int[] handFrequency5=
		{
			1302540,1098240,123552,54912,10200,5108,3744,624,40
		};
	
	static int[] handFrequency7=
		{
			23294460,58627800,31433400,6461620,6180020,4047644,3473184,224848,41584
		};
	
	static String[] allCardNames = new String[] { 
		"2s","3s","4s","5s","6s","7s","8s","9s","10s","Js","Qs","Ks","As",
		"2h","3h","4h","5h","6h","7h","8h","9h","10h","Jh","Qh","Kh","Ah",
		"2c","3c","4c","5c","6c","7c","8c","9c","10c","Jc","Qc","Kc","Ac",
		"2d","3d","4d","5d","6d","7d","8d","9d","10d","Jd","Qd","Kd","Ad"
		};
	
	  static int[] allCardNums = new int[]{
		  65537,65538,65540,65544,65552,65568,65600,65664,65792,66048,66560,67584,69632,
		  32769,32770,32772,32776,32784,32800,32832,32896,33024,33280,33792,34816,36864,
		  16385,16386,16388,16392,16400,16416,16448,16512,16640,16896,17408,18432,20480,
		  8193,8194,8196,8200,8208,8224,8256,8320,8448,8704,9216,10240,12288
	  };
	 
	  static int[] allCardNums7 = new int[]{
			  4194305,4194306,4194308,4194312,4194320,4194336,4194368,4194432,4194560,4194816,4195328,4196352,4198400,
			  524289,524290,524292,524296,524304,524320,524352,524416,524544,524800,525312,526336,528384,
			  65537,65538,65540,65544,65552,65568,65600,65664,65792,66048,66560,67584,69632,
			  8193,8194,8196,8200,8208,8224,8256,8320,8448,8704,9216,10240,12288
		  };
	
	  public static void makeNewnums(){
		  for(int i : allCardNums){
			  int ne = 0;
			  int suit = i & 122880;
			  if(suit == 65536)ne  = (suit << 6) | (i&8191);
			  else if (suit == 32768)ne = (suit << 4 )| (i&8191);
			  else if (suit == 16384) ne = (suit << 2) | (i&8191);
			  else ne = i;
			  System.out.println(ne);
		  }
	  }
	

	  //there is a speed test method below
	  //but in case you want the good ol fashion 'create every single possible hand'
	  //here you go
	  public static void testEveryHand5(){
		  
		  //copy of the deck to make this method more portable

		  double allHandPossibilities = 0.0;
		  int totalHands = 2598960;
		  int[] allCards = new int[totalHands*5];
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
						  }
		  
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
		  allHandPossibilities += currentTotal;
		  System.out.println(currentTotal + " million hands a second\n");
		  System.out.println("Running Total : " + allHandPossibilities + " percent complete");
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
			   boolean checked = handCounter[j]==handFrequency5[j];
			   String res = checked
					   //if all hands accounted for, good news
					   ?"All "+handFrequency5[j]+" "+handNames[j]+" hands are accounted for" 
							   
						//if the counts dont match, show how many failed
					   : (handCounter[j]-handFrequency5[j])+" of "+handFrequency5[j]
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
		  for(int i=0;i<allCardNums.length-1;i++)
			  for(int j=i+1;j<allCardNums.length;j++)
				  for(int k=j+1;k<allCardNums.length;k++)
					  for(int l=k+1;l<allCardNums.length;l++)
						  for(int m=l+1;m<allCardNums.length;m++)
							  for(int n=m+1;n<allCardNums.length;n++)
								  for(int o=n+1;o<allCardNums.length;o++){
									  
									  int res = DeadHorse.eval7
											  (
											  	allCardNums[i],
											  	allCardNums[j],
											  	allCardNums[k],
											  	allCardNums[l],
											  	allCardNums[m],
											  	allCardNums[n],
											  	allCardNums[o]
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
			   boolean checked = handCounter[j]==handFrequency7[j];
			   String res = checked
					   //if all hands accounted for, good news
					   ?"PASS - All "+handFrequency7[j]+" "+handNames[j]+" hands are accounted for" 
							   
						//if the counts dont match, show how many failed
					   : "FAIL - " +handCounter[j]+" out of "+handFrequency7[j]
							   +" "+handNames[j]+" hands received!"; 
			   
			   System.out.println(res+" " + ((double)handCounter[j]/totalCounter*100) + "%");
		   }
			 
		  System.out.println("Total Count : " + totalCounter);
		  
	  }
	  
	  

	  
public static void testEveryHand7n(){
		  
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
		  for(int i=0;i<allCardNums7.length-1;i++)
			  for(int j=i+1;j<allCardNums7.length;j++)
				  for(int k=j+1;k<allCardNums7.length;k++)
					  for(int l=k+1;l<allCardNums7.length;l++)
						  for(int m=l+1;m<allCardNums7.length;m++)
							  for(int n=m+1;n<allCardNums7.length;n++)
								  for(int o=n+1;o<allCardNums7.length;o++){
									  
									  int res = DeadHorse.eval7n
											  (
											  	allCardNums7[i],
											  	allCardNums7[j],
											  	allCardNums7[k],
											  	allCardNums7[l],
											  	allCardNums7[m],
											  	allCardNums7[n],
											  	allCardNums7[o]
											  )>>26;
											  	
									  
									
													  
								 /* String hand=	allCardNums7[i]+","+
										  		allCardNums7[j]+","+
												allCardNums7[k]+","+
												allCardNums7[l]+","+
												allCardNums7[m]+","+
												allCardNums7[n]+","+
												allCardNums7[o];
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
			   boolean checked = handCounter[j]==handFrequency7[j];
			   String res = checked
					   //if all hands accounted for, good news
					   ?"PASS - All "+handFrequency7[j]+" "+handNames[j]+" hands are accounted for" 
							   
						//if the counts dont match, show how many failed
					   : "FAIL - " +handCounter[j]+" out of "+handFrequency7[j]
							   +" "+handNames[j]+" hands received!"; 
			   
			   System.out.println(res+" " + ((double)handCounter[j]/totalCounter*100) + "%");
		   }
			 
		  System.out.println("Total Count : " + totalCounter);
		  
	  }
	  
	  
	  
	public static void randomizerSpeedTest5CardDiagnostics(int howMany){
		  
		  int[] allCards = HandMaker.makeLotsOfRandom5CardHands(howMany);
		  
		  for(int i=0;i<allCards.length;i+=5){
			int res = DeadHorse.eval5(allCards[i],allCards[i+1],allCards[i+2],allCards[i+3],allCards[i+4]);
			res>>=26;
		  	System.out.println("\n"+res+", "+handNames[res]);
		  }
	  }
	
	
	public static void randomizerSpeedTest5Card(int howMany){
		  
		  //number of hands to burn through
		  //pick a nice huge round number to let this sucker get warmed up
		  // int howMany = 10000000;

		  int[] allCards = HandMaker.makeLotsOfRandom5CardHands(howMany);
		  
		 // long startTp = System.nanoTime();
		  //let er rip, go through every hand, 5 cards at a time
		/*  for(int i=0;i<allCards.length;i++)
			  {i++;i++;i++;i++;}
		*/  
		  //get end time
		//  long endTp = System.nanoTime();
		  
		  
		  //get start time
		  long startT = System.nanoTime();
		  
		  //let er rip, go through every hand, 5 cards at a time
		  for(int i=0;i<allCards.length;i+=5)
			  DeadHorse.eval5(allCards[i],allCards[i+1],allCards[i+2],allCards[i+3],allCards[i+4]);
		  
		  //get end time
		  long endT = System.nanoTime();
		  
		  
		    
		  // Time is (end time - start time ) divided by a billion : because it is in nano seconds
		  double time = (double) (endT - startT )/1000000000;
		  
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
			  handCounter[DeadHorse.eval5(allCards[i],allCards[i+1],allCards[i+2],allCards[i+3],allCards[i+4])]++;
		
		  //this will show the frequence and percentages of each type of hand 
		  //these frequencies can be compared to the actual frequency percentages 
		  //of known poker hands. This just shows the accuracy of the speed test
		  //ths shows the speed test is testing random hands at the same handType/Frequency
		  //you would expect if you tested all possible hands
		  for(int j=0;j<handCounter.length;j++)
				 System.out.println(handNames[j] +" : " + handCounter[j] + " : " + ((double)handCounter[j]/howMany*100) + "%");

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
	}
	else{
		if(highCard[0] != 0)
			System.out.println("High card failed " + getName(highCard[0]) + " " + getName(highCard[1]) + " " + getName(highCard[2]) + " " + getName(highCard[3]) + " " + getName(highCard[4]) );
	}
	if(p<tp && p<t && p<s && p<f && p<fh && p<q && p<sf){
		//System.out.println("Pair pass");
	}
	else{
		System.out.println("Pair failed " + getName(pair[0]) + " " + getName(pair[1]) + " " + getName(pair[2]) + " " + getName(pair[3]) + " " + getName(pair[4]) );
	}
	if(tp<t && tp<s && tp<f && tp<fh && tp<q && tp<sf){
		//System.out.println("TwoPair pass");
	}
	else{
		System.out.println("TwoPair failed " + getName(twoPair[0]) + " " + getName(twoPair[1]) + " " + getName(twoPair[2]) + " " + getName(twoPair[3]) + " " + getName(twoPair[4]) );
	}
	if(t<s && t<f && t<fh && t<q && t<sf){
		//System.out.println("Trips pass");
	}
	else{
		System.out.println("Trips failed " + getName(trips[0]) + " " + getName(trips[1]) + " " + getName(trips[2]) + " " + getName(trips[3]) + " " + getName(trips[4]) );
	}
	if(s<f && s<fh && s<q && s<sf){
		//System.out.println("Straight pass");
	}
	else{
		System.out.println("Straight failed " + getName(straight[0]) + " " + getName(straight[1]) + " " + getName(straight[2]) + " " + getName(straight[3]) + " " + getName(straight[4]) );
	}
	if(f<fh && f<q && f<sf){
		//System.out.println("Flush pass");
	}
	else{
		System.out.println("Flush failed " + getName(flush[0]) + " " + getName(flush[1]) + " " + getName(flush[2]) + " " + getName(flush[3]) + " " + getName(flush[4]) );
	}
	if(fh<q && fh<sf){
		//System.out.println("FullHouse pass");
	}
	else{
		System.out.println("FullHouse failed " + getName(fullhouse[0]) + " " + getName(fullhouse[1]) + " " + getName(fullhouse[2]) + " " + getName(fullhouse[3]) + " " + getName(fullhouse[4]) );
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
//capital letter for face cards and lower case letter for suit 
public  static int humanEncodeEval(String as, String bs, String cs, String ds, String es){

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
}

//give it human readable string cards, it will spit back a human readable hand type(pair, full house etc..)
public static String humanEncodeFullHandEval(String as, String bs, String cs, String ds, String es){
	int res= EvalTestPlayground.humanEncodeEval(as,bs,cs,ds,es);
	return "unique hand value = " +res+ "\n"+ as + ", " + bs + ", " +cs+", " +ds+", "+es+"\n= "+ handNames[res>>26];
}

public static void randomizerSpeedTest7Card(int howMany){
	  
	  //number of hands to burn through
	  //pick a nice huge round number to let this sucker get warmed up
	  // int howMany = 10000000;
	
	

	  int[] ac = HandMaker.makeLotsOfRandom7CardHands(howMany);
	  //get start time
	  long startTf = System.nanoTime();
	  
	  //these dang for loops take a while
	  for(int v=0;v<ac.length/7;) v++;
	  //get end time
	  long endTf = System.nanoTime();
	  long startT = System.nanoTime();
	  
	  for(int v=0;v<ac.length/7;v++)
		  DeadHorse.eval7(ac[(v*7)],ac[(v*7)+1],ac[(v*7)+2],ac[(v*7)+3],ac[(v*7)+4],ac[(v*7)+5],ac[(v*7)+6]);


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
	
	

	  int[] ac = HandMaker.makeLotsOfRandom7CardHands(howMany);
	  //get start time
	  long startTf = System.nanoTime();
	  
	  //these dang for loops take a while
	  for(int v=0;v<ac.length/7;) v++;
	  //get end time
	  long endTf = System.nanoTime();
	  long startT = System.nanoTime();
	  
	  for(int v=0;v<ac.length/7;v++)
		  DeadHorse.eval7n(ac[(v*7)],ac[(v*7)+1],ac[(v*7)+2],ac[(v*7)+3],ac[(v*7)+4],ac[(v*7)+5],ac[(v*7)+6]);


	  //get end time
	  long endT = System.nanoTime();
	  
	  // Time is (end time - start time  ) divided by a billion : because it is in nano seconds
	  double time = (double) (endT - startT -(endTf - startTf))/1000000000;
	  
	  System.out.println("Did " + howMany + " hands in " + time +" seconds");
	  //hands per second in millions 
	  System.out.println((int)(howMany/time/1000000) + " million hands a second"); 
}

public static void test7(){
	
	int[] lowAce = {69632,32769,32770,16388,8200,32896,65792};
	
	int[] threepair = {66048,32896,16392,8196,8320,8200,33280};
	
	int[] trips2pair = {32832,65600,16448,32800,8224,34816,67584};
	
	int[] tripstrips = {65537,32769,16385,16640,65792,8448,8196};
	
	int[] quads = {65664,32896,16512,8320,36864,8704,65544};
	
	int[] quadspair = {36864,20480,12288,69632,65552,8208,16640};
	
	int[] quadstrips = {36864,20480,12288,69632,65552,8208,16400};
	
	int[] sevenCardFlush = {65538,65540,65552,65600,65664,66048,67584};
	
	int[] sf7 = {allCardNums[21],allCardNums[16],allCardNums[20],allCardNums[18],allCardNums[19],allCardNums[17],allCardNums[22]};
	int[] sf61 = {allCardNums[21],allCardNums[1],allCardNums[20],allCardNums[18],allCardNums[19],allCardNums[17],allCardNums[22]};
	int[] sf62 = {allCardNums[21],allCardNums[16],allCardNums[20],allCardNums[18],allCardNums[19],allCardNums[49],allCardNums[22]};
	int[] sf51 = {allCardNums[21],allCardNums[3],allCardNums[20],allCardNums[18],allCardNums[19],allCardNums[49],allCardNums[22]};

	 int[][] allTestHands = {
				{allCardNums[1],allCardNums[17],allCardNums[15],allCardNums[24],allCardNums[35],allCardNums[45],allCardNums[51]},
				{allCardNums[1],allCardNums[17],allCardNums[15],allCardNums[25],allCardNums[35],allCardNums[45],allCardNums[51]}, //aces
				{allCardNums[1],allCardNums[16],allCardNums[0],allCardNums[25],allCardNums[35],allCardNums[13],allCardNums[51]},//aces
				{allCardNums[12],allCardNums[17],allCardNums[15],allCardNums[25],allCardNums[35],allCardNums[45],allCardNums[51]},//aces
				{allCardNums[1],allCardNums[2],allCardNums[17],allCardNums[3],allCardNums[18],allCardNums[48],allCardNums[51]},// 34567
				{allCardNums[1],allCardNums[44],allCardNums[9],allCardNums[6],allCardNums[3],allCardNums[8],allCardNums[45]},//spades
				{allCardNums[4],allCardNums[32],allCardNums[46],allCardNums[48],allCardNums[35],allCardNums[17],allCardNums[30]},// 6 9 9 11 11 6 6 //6s full of 11s or 9s
				//{allCardNums[6],allCardNums[33],allCardNums[19],allCardNums[32],allCardNums[50],allCardNums[17],allCardNums[45]},//4x8s
				{allCardNums[12],allCardNums[25],allCardNums[19],allCardNums[32],allCardNums[38],allCardNums[17],allCardNums[51]},//4x8s
				{allCardNums[21],allCardNums[16],allCardNums[20],allCardNums[18],allCardNums[19],allCardNums[17],allCardNums[22]}//78910J
			  };
/*	 int tpo = (threepair[0]&8191)|(threepair[1]&8191)|(threepair[2]&8191)|(threepair[3]&8191)|(threepair[4]&8191)|(threepair[5]&8191)|(threepair[6]&8191);
	 int tpx = (threepair[0]&8191)^(threepair[1]&8191)^(threepair[2]&8191)^(threepair[3]&8191)^(threepair[4]&8191)^(threepair[5]&8191)^(threepair[6]&8191);
	 int tpz = tpo^tpx;
	 int k= tpx > (tpz^(tpz&tpz-1)) ? tpx :(tpz^(tpz&tpz-1));
	 int t2p=tpz&tpz-1;
	 System.out.println(bin(tpo) + "\n"+bin(tpx) + "\n"+bin(tpz)+"\n"+bin(k));
	 int out = */
	 
		/*int test = 4191;
		System.out.println(bin(test));
		int t1 = test&test-1;t1&=t1-1;
		System.out.println(bin(t1));
		int t2=test&(~test>>1)^test;
		System.out.println(bin(t2));
		
		System.out.println(bin(test&(test-1^(~test>>1))));
	*/
	//int[] f7 = allTestHands[2];
		int[] f7 =   sevenCardFlush;
	
	System.out.println(f7[0]+" "+getName(f7[0])+", "+f7[1]+" "+getName(f7[1])+", "+f7[2]+" "+getName(f7[2])+", "
			+f7[3]+" "+getName(f7[3])+", "+f7[4]+" "+getName(f7[4])+", "+f7[5]+" "+getName(f7[5])+", "+f7[6]+" "+getName(f7[6]));
	
	
	//int res = DeadHorseEval.eval7(f7);
	int res = DeadHorse.eval7(f7[0],f7[1],f7[2],f7[3],f7[4],f7[5],f7[6]);
	
	System.out.println("Outcome " + res + ", " + handNames[(res>>26)] + "\n"+bin32(res));
	
/*DeadHorseEval.sort7(f7);
	System.out.println(f7[0]+" "+getName(f7[0])+", "+f7[1]+" "+getName(f7[1])+", "+f7[2]+" "+getName(f7[2])+", "
	+f7[3]+" "+getName(f7[3])+", "+f7[4]+" "+getName(f7[4])+", "+f7[5]+" "+getName(f7[5])+", "+f7[6]+" "+getName(f7[6]));*/
}



public static String bin(int i){
	return String.format("%17s", Integer.toBinaryString(i)).replace(' ', '0');
}

public static String bin32(int i){
	return String.format("%32s", Integer.toBinaryString(i)).replace(' ', '0');
}
	  
public static String getName(int j){
	for(int i=0;i<allCardNums.length;i++){
		if(allCardNums[i]==j){
			return allCardNames[i];
		}
	}
	return "error getting name " + j;
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


}
