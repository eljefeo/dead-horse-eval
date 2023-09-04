package main;

import java.util.ArrayList;

import static main.util.handNames;

public class util7 extends util {

    static int total7CardHandCount = 133784560;
    static int[] handFrequency7=
            {
                    23294460,58627800,31433400,6461620,6180020,4047644,3473184,224848,41584
            };

    // cards are all 2 through Ace, diamonds clubs then hearts then spades (we may
    // change the order, Im already mixing up orders everywhere of cards and suits)
    static final long[] all52Cards7 = new long[] { 549755813889L, 549755813896L, 549755813952L, 549755814400L,
            549755817984L, 549755846656L, 549756076032L, 549757911040L, 549772591104L, 549890031616L, 550829555712L,
            558345748480L, 618475290624L, 4398046511105L, 4398046511112L, 4398046511168L, 4398046511616L,
            4398046515200L, 4398046543872L, 4398046773248L, 4398048608256L, 4398063288320L, 4398180728832L,
            4399120252928L, 4406636445696L, 4466765987840L, 35184372088833L, 35184372088840L, 35184372088896L,
            35184372089344L, 35184372092928L, 35184372121600L, 35184372350976L, 35184374185984L, 35184388866048L,
            35184506306560L, 35185445830656L, 35192962023424L, 35253091565568L, 281474976710657L, 281474976710664L,
            281474976710720L, 281474976711168L, 281474976714752L, 281474976743424L, 281474976972800L, 281474978807808L,
            281474993487872L, 281475110928384L, 281476050452480L, 281483566645248L, 281543696187392L };
    // static final long[] all52Cards2 = makeAll52Cards7Decimal();

    // These are the bits and what they mean, everything gets 3 bits.
    // Starting from the rightmost bits, 222 means the 2 cards go there
    // if we are adding up how many of each of these cards there are, like if
    // someone has 4-of-a-kind of 2(s)
    // then we need to count to 4, to do that we need 3 bits (100 - this is 4 in
    // binary)
    // same thing for the suits, if there are 7 cards total and all are 1 suit, then
    // we need to count to 7 (111 - this is 7 in binary)
    // so 3 bits each is enough to count whatever we want.
    static final String charPlacement = "SSSHHHCCCDDDAAAKKKQQQJJJTTT999888777666555444333222";





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
        for(int i=0;i<all52Cards7.length-1;i++)
            for(int j=i+1;j<all52Cards7.length;j++)
                for(int k=j+1;k<all52Cards7.length;k++)
                    for(int l=k+1;l<all52Cards7.length;l++)
                        for(int m=l+1;m<all52Cards7.length;m++)
                            for(int n=m+1;n<all52Cards7.length;n++)
                                for(int o=n+1;o<all52Cards7.length;o++){

                                    int res = DeadHorse7.eval7
                                            (
                                                    all52Cards7[i],
                                                    all52Cards7[j],
                                                    all52Cards7[k],
                                                    all52Cards7[l],
                                                    all52Cards7[m],
                                                    all52Cards7[n],
                                                    all52Cards7[o]
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
        //Since there is a known number of each type of 7 card hand with 52 cards
        //High Card 		23294460
        //Pair 			58627800
        //Two Pair 		31433400
        //Trips 			6461620
        //Straight 		6180020
        //Flush 			4047644
        //Full House 		3473184
        //Quads 			224848
        //Straight Flush	41584
        //Total			133,784,560

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

}
