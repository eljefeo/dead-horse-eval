package main;

public class EvalTestPlayground {
	
	static String[] handNames = 
		{
			"High Card", "Pair", "Two Pair", "3 of a kind", 
			"Straight", "Flush", "Full House", "4 of a kind", "Straight Flush"
		};
	
	
	static int[] handFrequency=
		{
			1302540,1098240,123552,54912,10200,5108,3744,624,40
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
	
	

	  //there is a speed test method below
	  //but in case you want the good ol fashion 'create every single possible hand'
	  //here you go
	  public static void testEveryHand(){
		  
		  //copy of the deck to make this method more portable

		  
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
			
			  int res = DeadHorseEval.eval(allCards[i],allCards[i+1],
					  allCards[i+2],allCards[i+3],allCards[i+4])>>26;
			  handCounter[res]++;
		  
		  }
		  //this is to go through and compare the number of each type of hand we created
		  //to the number of each type of hand we expect
		   for(int j=0;j<handCounter.length;j++){
			   //check if expected == actual
			   boolean checked = handCounter[j]==handFrequency[j];
			   String res = checked
					   //if all hands accounted for, good news
					   ?"All "+handFrequency[j]+" "+handNames[j]+" hands are accounted for" 
							   
						//if the counts dont match, show how many failed
					   : (handCounter[j]-handFrequency[j])+" of "+handFrequency[j]
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
			 res>>=26;
			  System.out.println("\n"+handNames[res]);
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
			  DeadHorseEval.eval(allCards[i],allCards[i+1],allCards[i+2],allCards[i+3],allCards[i+4]);
		  
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
		  
		  long startTf = System.nanoTime();
		  
		  //these dang for loops take a while
		  for(int v=0;v<allCards.length/7;v++)
			  for (int i = 0; i < 7 - 1; i++) 
				  for (int j = i + 1; j < 7; j++) 
					  for (int k = j + 1; k < 7; k++) 
						  for (int l = k + 1; l < 7; l++) 
							  for (int m = l + 1; m < 7; m++) {
								  i++;i++;i--;i--;
								}
		  //get end time
		  long endTf = System.nanoTime();
		  
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
		  
		  // Time is (end time - start time  ) divided by a billion : because it is in nano seconds
		  double time = (double) (endT - startT -(endTf - startTf))/1000000000;
		  
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

	return DeadHorseEval.eval(a, b, c, d, e);
}

//give it human readable string cards, it will spit back a human readable hand type(pair, full house etc..)
public static String humanDecodeEval(String as, String bs, String cs, String ds, String es){
	int res= EvalTestPlayground.humanEncodeEval(as,bs,cs,ds,es);
	return "unique hand value = " +res+ "\n"+ as + ", " + bs + ", " +cs+", " +ds+", "+es+"\n= "+ handNames[res>>26];
}




public static String bin(int i){
	return String.format("%17s", Integer.toBinaryString(i)).replace(' ', '0');
}
	  
}
