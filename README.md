# DeadHorseEval

So I decided to beat a dead horse and make another 5 card evaluator. I did not like the idea of lugging around a >100MB lookup table file. I figured this eval would go into an app someday and who in their right minds would attach a 100MB file to a 10MB game just to eval the hand? But I also loved the speed of the lookup table evals, so I wanted both.
So I wanted to make a fast and small eval, yes like so many others. I also wrote it in Java, partly because most people dont, and partly so I can throw it into an android app later maybe...Either way it can quickly be recoded for other languages. This is all very new.
Before you read into the explanation, please note that this evaluator has been tested for accuracy against all 2,598,960 5card hands that you can make with a 52 card deck (Which it currently burns through in .038 seconds or so).

And as for the speed? Well, again, I want speed, like lookup table speed. I wrote this code less than 1 week ago...So there is optimization to be made. I recently added the unique number result functionality to compare hands which added a few more checks and brought the speed down, but the current speed from my amd desktop is ~70 million hands a second. It averages 0.28 seconds for 20,000,000 hands. There is only one way to go from here, and that is up.

I simply wanted to try my hand at the ol' poker hand evaluator. It seemed like a fun project.

The idea behind the eval is simple, set the 52 cards in such a way, where it would be easy to tell what hand you have when any 5 of those cards are passed to an evaluator.
You see, with the lookup table, all the preprocessing has been done already, which is why they are so damn fast. I wanted to do preprocessing as well, just without the added space of a file.

So my preprocessing is setting up 52 specific numbers (cards):

## I The Cards
```
 static int[] fiftyTwo = new int[]
	  {
		  65537,65538,65540,65544,65552,65568,65600,65664,65792,66048,66560,67584,69632,
		  32769,32770,32772,32776,32784,32800,32832,32896,33024,33280,33792,34816,36864,
		  16385,16386,16388,16392,16400,16416,16448,16512,16640,16896,17408,18432,20480,
		  8193,8194,8196,8200,8208,8224,8256,8320,8448,8704,9216,10240,12288
	  };
```


You can imaginge these numbers like this :
```
  static String[] allHumanCards = new String[]
	  {
		  "2s","3s","4s","5s","6s","7s","8s","9s","10s","11s","12s","13s","14s",
		  "2h","3h","4h","5h","6h","7h","8h","9h","10h","11h","12h","13h","14h",
		  "2c","3c","4c","5c","6c","7c","8c","9c","10c","11c","12c","13c","14c",
		  "2d","3d","4d","5d","6d","7d","8d","9d","10d","11d","12d","13d","14d"
	  };
```

There is a method to this number madness

####Card Structure

These numbers correspond to a nicely arranged set of bits. 
I setup the cards so each one only need 17 bits. 13 bits for the numbers 2-A, and 4 bits for the suits Spades Hearts Clubs Diamonds.

The Card setup follows this bit structure:

```
S H C D A K Q J 10 9 8 7 6 5 4 3 2
```
So an Ace of Spades would look like this in binary:
```
Ace of Spades :
1 0 0 0 1 0 0 0 0 0 0 0 0 0 0 0 0 
```

And a 5 of clubs would look like this :
```
Five of clubs:
0 0 1 0 0 0 0 0 0 0 0 0 0 1 0 0 0
```

You get the picture.

## II The 3 Hands

Now, setting the cards up like this gave me some advantages. Because in poker there are really 3 types of hands:
* Straights	(where the numbers matter) 
* Flushes 	(where the suits matter)  
* Duplicates 	(where the quantities matter)

This bit structure came about with this in mind.

So how would I handle each of these 3 types of hands ???

###II.a Straights:
Straights are 5 cards in a row : 4 5 6 7 8, or 9 10 J Q K, or even A 2 3 4 5.
I wanted a quick way to find these types of hands. 
First, using our bits, we would take the bitwise OR of all the cards as so:
```
00100000001000000 : Eight of clubs, 16448 in decimal
01000000000010000 : Six of Hearts, 32784 in decimal
00010000000001000 : Five of Diamonds, 8200 in decmal
10000000010000000 : Nine of Spades, 65664 in decimal
01000000000100000 : Seven of Hearts, 32800 in decimal
| (OR them all together)
11110000011111000 : Our results after Or'ing, 123128 in decimal
```

Since we do not need our Suits for a straight check we will use a Suit Mask to only leave our numbers:
```
Suit Mask:
00001111111111111 : 8191 in decimal

so...

11110000011111000 : Our result from Or'ing above, 123128 in decimal
&
00001111111111111 : Our Suit Mask, 8191 in decimal
=
00000000011111000 : Our Result, 248 in decimal
```
So now what are we looking at ? 
We have our five cards, all in a row, whose decimal equivalent is a number unique to straights. When I say unique to straights, I mean that when you OR all 5 cards together, you will never get the same number that a straight gives you, with any other type of hand. So a pair, full house, flush etc. will never OR into the same number a straight would. This allows us to isolate the straights and find them with unified way. 

The way we find if this masked OR number is a straight, is by modulo. The highes straight you can have, after being masked comes out to :
```
A K Q J 10
00001111100000000 : After OR'ing the cards, 7936 in decimal
```
Here is a cool thing about these straights. This highest straight number (7936) will give a 0 result when modulo'd against any other straight number. For example:
```
(AKQJ10 % 87654 = 0)
7936 % 124 = 0

(AKQJ10 % Q J 10 9 8 = 0)
7936 % 1984 = 0
```
The issue is, of course, that darn low Ace straight (A2345)
So to get around this we get the Least Common Multiple of 4111 (A2345) and 7936 (AKQJ10) which comes out to 32624896

Now this number, 32624896, when modulo'd against any of our straights, including a low Ace straight will give 0 !!

######Finally for Straights.....
we find out straights by 
* OR'ing all 5 cards
* Mask the suit bits
* if (32624896 % ourNumber == 0) {Straight !}

###II.b Flushes:

~~We find our flushes in a similar way to the straights above. First we OR all the cards together, except instead of masking the suits away, we mask the card numbers away and only leave the suits, since that is all we are interested in for a flush. So...~~

Thank you u/DrBreakalot for the idea and u/compiling for the comfirmation. Thanks to reddit, The flush check has been changed. previously we would OR the cards together, mask the numbers, leaving only the suit bits, then perform a modulo calculation that would return 0 only for flushes.

It has been brought to my attention that there is a simpler way. All we have to do is AND all the cards together, if the result is non-zero we have a flush ! It is intersting, as I was going through these hand checks, I thought about using AND'ing, but I could not find a use for it...I missed the unique flush check it provides. Pretty Sweet.

Lets check that out:
```
A Flush Hand:
00100000000001000 : Five of clubs
00100000010000000 : Nine of clubs
00100001000000000 : Jack of clubs
00100000000000001 : Two of clubs
00101000000000000 : Ace of clubs
&
00100000000000000 : our results from AND'ing, 16384 in decimal

```
Note that any hand that is not a flush would be zero'd out :
```
Non-Flush Hand
00100000000001000 : Five of clubs
00100000010000000 : Nine of clubs
00100001000000000 : Jack of clubs
00100000000000001 : Two of clubs
10001000000000000 : Ace of spades
&
00000000000000000 : our results from AND'ing, 0 in decimal

The spade at the end killed the 1, non-flushes end up as zero from AND'ing
```
The beautiful thing here is with AND'ing, the only bits that would make it through would be a single suit bit if the hand is actually a flush. AND'ing compares bits to eachother and only results in a 1 set bit if BOTH the two numbers have a 1 bit set in that position, if a 1 ever gets AND'ed with a 0, the 1 dies and the result is a 0. This means that a chain of five cards would need a bit set in the same place for all five cards, which would only happen for flushes.

Finally for Flushes :
* AND all five cards together
* if (result!=0) {Flush!}

So simple, I love it.


###II.c Duplicates (Pairs, Trips, etc.)
The last type of hand we will come across are the five hands that include duplicated cards:
* Pair
* Two Pair
* 3 of a Kind
* Full House
* 4 of a Kind

With straights and flushes, we found magic numbers to modulo against our OR'd cards, and it was easy peezy. Not for duplicates. What we can do with duplicates though is find a unique way to tell these five hands apart. The way we can do that is this:
* OR the cards together
* XOR the cards together
* Count the bits in both the OR and XOR results

Lets see an example:
```
Pair:
00100000000001000 : Five of clubs
00100000010000000 : Nine of clubs
01000000000001000 : Five of Hearts
00100000000000001 : Two of clubs
00101000000000000 : Ace of clubs

OR all five cards and Mask the Suits :
= 00001000010001001 (4 bits set)

XOR all five cards and Mask the Suits
= 00001000010000001 (3 bits set)

Total Bits set (4+3) = 7

---------------------------------------
Two Pair:
00100000000001000 : Five of clubs
01001000000000000 : Ace of Diamonds
01000000000001000 : Five of Hearts
00100000000000001 : Two of clubs
00101000000000000 : Ace of clubs

OR all five cards and Mask the Suits :
= 00001000000001001 (3 bits set)

XOR all five cards and Mask the Suits
= 00000000000000001 (1 bit set)

Total Bits set (3+1) = 4

---------------------------------------
Three of a kind:
00100000000001000 : Five of clubs
00011000000000000 : Ace of Diamonds
01001000000000000 : Ace of Hearts
00100000000000001 : Two of clubs
00101000000000000 : Ace of clubs

OR all five cards and Mask the Suits :
= 00001000000001001 (3 bits set)

XOR all five cards and Mask the Suits
= 00001000000001001 (3 bits set)

Total Bits set (3+3) = 6

---------------------------------------
Full House:
00100000000001000 : Five of clubs
00011000000000000 : Ace of Diamonds
01000000000001000 : Five of Hearts
01001000000000000 : Ace of Hearts
00101000000000000 : Ace of clubs

OR all five cards and Mask the Suits :
= 00001000000001000 (2 bits set)

XOR all five cards and Mask the Suits
= 00001000000000000 (1 bits set)

Total Bits set (2+1) = 3
---------------------------------------

4 of a Kind:
10001000000000000 : Ace of Spades
00011000000000000 : Ace of Diamonds
01000000000001000 : Five of Hearts
01001000000000000 : Ace of Hearts
00101000000000000 : Ace of clubs

OR all five cards and Mask the Suits :
= 00001000000001000 (2 bits set)

XOR all five cards and Mask the Suits
= 00000000000001000 (1 bits set)

Total Bits set (2+1) = 3
---------------------------------------

```
So after all these bits, what do we have left? We now know that if you OR the five cards, add up the amount of bits in the result, then XOR the five cards, add up the amount of bits in the result, you get unique numbers for pairs, two pairs, and 3 of a kind. Full house and 4 of a kind end up with the same number (3) which means we will have to do a separate check for them.

So to find pairs, two pair, 3 of a kind, Full house, 4 of a kind:
* OR all five cards, count the bits in the result
* XOR all five cards, count the bits in the result
* add those 2 counts together
* 
* if (count==7){Pair !}
* if (count==4){Two Pair!}
* if (count==6){3 of a kind!}
* if (count==3){Full House or 4 of a Kind!}

That damn Full House and 4 of a kind, why did they have to be the same? Everything was going sooo good until then. Aww well, We have done good so far. What we will do is if the count comes back as 3, we will check to see if it is a 4 of a kind or full house. This is how we will do that:

In 4 of a kind : the OR result from all five cards has both the quadruple card and the single card. So in the example:
```
10001000000000000 : Ace of Spades
00011000000000000 : Ace of Diamonds
01000000000001000 : Five of Hearts
01001000000000000 : Ace of Hearts
00101000000000000 : Ace of clubs

OR all five cards and Mask the Suits :
00001000000001000 

XOR all five cards and Mask the Suits
00000000000001000 

```
We can see that both the Ace bit (The Quadruple card) and the Five bit (the single card) are in the OR result.
We can also see that only the Five bit (the single card) shows up in the XOR result. This means that XOR'ing the OR and XOR together will give us back our quadruple card:
```
OR all five cards and Mask the Suits :
00001000000001000

XOR all five cards and Mask the Suits
00000000000001000 


XOR the OR and XOR (OR ^ XOR)
00001000000001000 : has both the Ace (Quad card) and Five (single card)
^
00000000000001000 : has just the Five (single card)

=
00001000000000000 : we get only our Ace back (our quadruple card)
```
This probably seems like a lot of stupid bit twiddling for something so simple. You are probably right, but what ends up happening is we now have isolated our Quad card (Ace) and Single Card(Five). Now we can do some math. If we mask the suit bits of all our cards and add the numbers together (Yes actually add, like arithmetic "+") we would get the same thing as our quad card times 4 plus our single card.

Example:
```
10001000000000000 : Ace of Spades
00011000000000000 : Ace of Diamonds
01000000000001000 : Five of Hearts
01001000000000000 : Ace of Hearts
00101000000000000 : Ace of clubs


Masked the suits :

00001000000000000 : Ace 
00001000000000000 : Ace
00000000000001000 : Five
00001000000000000 : Ace
00001000000000000 : Ace

add the numbers together:
4096 + 4096 + 8 + 4096 + 4096 = 16392

===============================

Our quad card (Ace) times 4
4096 * 4 = 16384
plus our single card
16384 + 8 = 16392 

16392 == 16392
!!!!
```

##We can now tell what kind of hand you have

After all that we have a way to find pairs, two pairs, 3 of a kinds, straights, flushes, full houses, 4 of a kinds, and straight flushes (if it is a straight and a flush)

I will explain the actual code later, but in short here it is:

Pass any five of these:
```
 static int[] fiftyTwo = new int[]
	  {
		  65537,65538,65540,65544,65552,65568,65600,65664,65792,66048,66560,67584,69632, //2-A spades
		  32769,32770,32772,32776,32784,32800,32832,32896,33024,33280,33792,34816,36864, //2-A hearts
		  16385,16386,16388,16392,16400,16416,16448,16512,16640,16896,17408,18432,20480, //2-A clubs
		  8193,8194,8196,8200,8208,8224,8256,8320,8448,8704,9216,10240,12288		 //2-A diamonds
	  };
```
Into this:
```
  public static int eval(int a, int b, int c, int d, int e){
		int sMask=0x1FFF;
		int xorMasked=(a^b^c^d^e)&sMask;
		int or=(a|b|c|d|e);
		int orMasked = or&sMask;
		int cntO=orMasked;
		int cntX=xorMasked;
		int v=(cntO&=cntO-1)!=0?(cntO&=cntO-1)!=0?(cntO&=cntO-1)!=0?5:4:3:2;
		if((cntX&=cntX-1)!=0)v++;
		if((cntX&=cntX-1)!=0)v++;
		if((cntX&=cntX-1)!=0)v++;
		boolean strt=0x1F1D100%orMasked==0;
		boolean flsh=(a&b&c&d&e)!=0;
		boolean quad=(a+b+c+d+e-xorMasked&sMask)==(sMask&(orMasked^xorMasked)<<2);
		return v==7?1:v==4?2:v==6?3:flsh&&strt?8:flsh?5:strt?4:v==3?quad?7:6:0;
	  }
```
And you will receive a number 0-8, corresponding to this:
* 0 High Card
* 1 Pair
* 2 Two Pair
* 3 Three of a kind
* 4 Straight
* 5 Flush
* 6 Full House
* 7 Four of a kind
* 8 Straight Flush


##But Who Wins ??
The next stage in this evaluator is to be able to determine a victor when presented with more than one hand. I mean it is great that we have simple code to tell what type of hand you have, but what happen when 2 people have the same type of hand ? Player A has a pair of 5's, and so does Player B...so who wins ???

Now this section of the code is the newest. It works but it is certainly not optimized. There is room for improvement.. So this will get more streamlined in the near future (hopefully), but for now we do have a way to give each hand a unique value to compare against other hands.

To tackle that challenge we need to be able to give the hand a final unique number. Let's revisit our bits...

We have been using integers for our numbers and bits this whole time, so I would like to stay within the realm of 32 bits. So we have 32 bits to work with (31 for our signed numbers), but we should not need all 32, I think we can get it done in 30, and maybe even less in the future.

Here is the current layout for our unique hand values:
```
Bit #
32 31 30 29 28 27 26 25 24 23 22 21 20 19 18 17 16 15 14 13 12 11 10 9 8 7 6 5 4 3 2 1
NA NA |Hand-Type| |----------Important Cards-----------| |--------Kicker Cards-------|
```

What is this non-sense?

Well bit 32 and 31 are not going to be needed...so lets leave those aside..

The next 4 bits (30-27) give us 16 different numbers to mess around with, and since we only have 9 different hand ranks (pair, flush, 4 of a kind etc..) these 4 bits can hold our hand rank. Lets see that:
```
Bit #		30 29 28 27 .........
High Card	0  0  0  0 (0 in decimal)
Pair		0  0  0  1 (67108864 in decimal)
Two Pair	0  0  1  0 (134217728 '')
3 of a kind	0  0  1  1 (201326592 '')
Straight	0  1  0  0 (268435456)
Flush		0  1  0  1 (335544320)
Full House	0  1  1  0 (402653184)
4 of a kind	0  1  1  1 (469762048)
Straight Flush	1  0  0  0 (536870912)
```

The great thing about putting the hand rank in these high bits, is no matter what bits are set in the smaller positions, a straight flush (no matter how low or high) will ALWAYS beat a 4 of a kind, and a 4 of a kind (whether its four 2's or 4 Aces) will ALWAYS beat a fullhouse, a full house will always beat a flush and so on and so on...So placing our ranks in these bit positions will guarentee the type of hand will beat a lower type of hand

The real problem is what happens when there is a sort of tie...?  What happens when two people have a 3 of a kind, or when two people have a full house. Not only that but what if two people both have a pair of 5's ??? Well that is where the next 2 sections of the bits come in:
* Important Cards (13 bits for the 13 different cards : 2 - Ace)
* Kicker Cards    (13 bits for the 13 different cards : 2 - Ace)

The 'Important cards' will hold the following:
* The card that is paired in a pair hand
* Both cards that are paired in a two pair hand
* The triple card in a 3 of a kind
* The triple card in a full house
* The quadruple card in a 4 of a kind

The 'Kicker Cards' will hold the following:
* All 5 single cards in a high card hand
* The 4 single cards in a paired hand
* The 1 single card in a two pair hand
* The 3 single cards in a 3 of a kind
* All 5 cards in a straight (save for low ace straight)
* All 5 cards in a flush
* The paired card in a full house
* The 1 single card in a 4 of a kind
* All 5 cards in a straight flush

You might be asking why High Card hands, Straights, Flushes, and Straight Flushes do not have any 'Important Cards'? Well that is because in those hands all 5 cards are taken at face value (save for low ace straight). This means that simply OR'ing the cards together will produce a higher number for a higher hand. The OR'd result of a 56789 straight will be smaller than the OR'd result of a 678910 straight. The same goes for a Flush, and High Card hand. 

The reason the duplicate type hands (pair, two pair, 3 of a kind, full house, 4 of a kind) need special treatment is because the cards do not take on face value. What I mean by this is with a pair of 4's, the number 4 is actually higher in a sense than the Ace kicker that it might have. So we need to elevate the status of that 4, and leave the kicker cards behind. This is what the 'Important Card' section is for. So how do we get and set these important cards you ask? well that is where our OR and XOR results come back into play.

If you remember, OR'ing and XOR'ing would isolate our paired cards, triple cards, quadruple cards etc..(in most cases) so we can use those numbers that we already have, shift them into the 'Important' position, and we would now have a way to show that even though 2 hands are the same, Player A wins because his 'Important' card is a 9, while Player B's 'Important' card is only a 6...

Lets find our important cards:
```
Pair
OR'ing - gets all 4 unique cards
XOR'ing - isolates our 3 single cards
OR ^ XOR - isolates our 1 paired card


Two Pair
OR - all 3 unique cards
XOR - our 1 single card
OR ^ XOR - our 2 paired cards

3 of a kind
OR - all 3 unique cards
XOR - all 3 cards (Oh no !)
OR ^ XOR - Nothing left (Oh no!)
*This 3 of a kind will be a problem...

Full House
OR - our 2 unique cards
XOR - our 1 triple card
OR ^ XOR - our 1 paired card

4 of a kind
OR - our 2 unique cards
XOR - our 1 single card
OR ^ XOR - our 1 quarduple card
```

Sweet, now we can tell which is the important cards simply by a little OR'ing and XOR'ing. Once we realize what the important card(s) are, we will shift them over a few spots ( card << 13 ) to put them in there important position. We will then take the leftover cards (single kicker cards) and leave them in there spots in the lower bit 'Kicker' section to give the hand a higher value if need be.

This is what two players who have the same type of hand would look like:

```
Player A: Two Pair
00100000000001000 : Five of clubs
01001000000000000 : Ace of Diamonds
01000000000001000 : Five of Hearts
00100000000000001 : Two of clubs
00101000000000000 : Ace of clubs

OR'ing and masking suits:
00001000000001001 : all 3 unique cards (Ace, Five, Two)

XOR'ing and masking suits:
00000000000000001 : our 1 single kicker card (Two)

XOR'ing the OR and XOR from above:
00001000000001000 : our 2 paired cards (Ace and Five)



Player B: Two Pair
10000000010000000 : Nine of Spades
01000000010000000 : Nine of Hearts
01000000001000000 : Eight of Hearts
00100010000000000 : Jack of clubs
00010000001000000 : Eight of Diamonds

OR'ing and masking suits:
00000001011000000 : all 3 unique cards (Jack, Nine, and Eight)

XOR'ing and masking suits
00000001000000000 : Our single kicker card (Jack)

XOR'ing the OR and XOR:
00000000011000000 : The two paired cards (Nine, Eight)

```

So Now we see that we have isolated our two 'Important' Paired cards, and our kicker card as well. it is now a matter of putting these bits in the right position to create the Entire hand in a single unique number:

First lets label the hand as a whole as a Two Pair Hand. Remember from above, our Two Pair hand will start off as the number 134217728. So we will start with that number and add the important bits, and kicker bits onto that guy:
```
Bit #
32 31 30 29 28 27 26 25 24 23 22 21 20 19 18 17 16 15 14 13 12 11 10 9 8 7 6 5 4 3 2 1
NA NA |Hand-Type| |----------Important Cards-----------| |--------Kicker Cards-------|

Player A : Two Pair (134217728)
0  0  0  0  1  0  0  0  0  0  0  0  0  0  0  0  0  0  0  0  0  0  0  0 0 0 0 0 0 0 0 0


Player B : Two Pair (134217728)
0  0  0  0  1  0  0  0  0  0  0  0  0  0  0  0  0  0  0  0  0  0  0  0 0 0 0 0 0 0 0 0
```
Now we remember that we can see our two paired cards by using the OR^XOR trick in a Two Pair hand. Since those two paired cards are important to knowing which Two Pair hand wins, lets shift those over into the 'Important' section:

```
Player A:
XOR'ing OR and XOR:
1000000001000 : our 2 paired cards (Ace and Five)
|-----------|

       |
       ------------------------------
                                    |
                                    
NA NA |Hand-Type| |----------Important Cards-----------| |--------Kicker Cards-------|


And we now end up with :
Player A : Two Pair (167837696)
0  0  0  0  1  0  1  0  0  0  0  0  0  0  0  1  0  0  0  0  0  0  0  0 0 0 0 0 0 0 0 0
                  A                          5

Lets do the same to Player B:

Player B:
XOR'ing the OR and XOR:
0000011000000 : The two paired cards (Nine, Eight)
|-----------|

       |
       ------------------------------
                                    |
                                    
NA NA |Hand-Type| |----------Important Cards-----------| |--------Kicker Cards-------|


And we now end up with :
Player B : Two Pair (135790592)
0  0  0  0  1  0  0  0  0  0  0  1  1  0  0  0  0  0  0  0  0  0  0  0 0 0 0 0 0 0 0 0
                                 9  8         
```
Totally sweet, we have our important bits set. For good measure lets throw in the last single kicker card that both of these players have in their Two Pair hand. Just in case both players had the two pairs the same, they would need the kicker card, so lets throw it in there:

```





Player A:
XOR'ing and masking suits:
0000000000001 : our 1 single kicker card (Two)
|-----------|

      |
      -------------------------------------------------------------------
                                                                        |
                                                                        
NA NA |Hand-Type| |----------Important Cards-----------| |--------Kicker Cards-------|

So Now Player A is:

Player A : Two Pair (167837697)
0  0  0  0  1  0  1  0  0  0  0  0  0  0  0  1  0  0  0  0  0  0  0  0 0 0 0 0 0 0 0 1
                  A                          5                                       2

```
Lets do the same for Player B:
```

Player B
XOR'ing and masking suits
0001000000000 : Our single kicker card (Jack)
|-----------|

      |
      -------------------------------------------------------------------
                                                                        |
                                                                        
NA NA |Hand-Type| |----------Important Cards-----------| |--------Kicker Cards-------|


Player B : Two Pair (135791104)
0  0  0  0  1  0  0  0  0  0  0  1  1  0  0  0  0  0  0  0  0  0  1  0 0 0 0 0 0 0 0 0
                                 9  8                             J
```

Now we have the finaly numbers for our two Players:
* Player A = Two Pair, Aces and Fives with a Two kicker = 167837697
* Player B = Two Pair, Nines and Eights, with a Jack kicker = 135791104

And who wins ?????

Well since 167837697 > 135791104

Player A wins with his Aces and Fives !

That is a lot of bits.....please no more bits...

I also wanted to add here that the resulting number (167837697) contains the necessary information to say what the hand is, not just what hand beats what. What I am saying is the number 167837697 in binary:
```
0  0  0  0  1  0  1  0  0  0  0  0  0  0  0  1  0  0  0  0  0  0  0  0 0 0 0 0 0 0 0 1
```
Can be seen with :
```
NA NA |Hand-Type| |----------Important Cards-----------| |--------Kicker Cards-------|
```
This gives one the ability to take the number 167837697 and tell that it is a Two Pair hand, Aces and Fives, with a Two kicker, not just that this number beat the other lower number. I think this can be valuable later. 
The suit information does get lost, but again suits do not matter when comparing values anyway, so I am ok with that.
###To Do
I still need to explain what we did with the 3 of a kind issue, and how the low ace straight gets handled, but I will have to add that shortly.

But we can see the overall mechanism to determine hand values that can be used to compare hands to other hands. With this strategy, no 2 hands will have the same value (unless you compare the same exact hand to itself), so we can find the winner. Now it is time to optimize this code to speed it up....

Here is the eval - with the added functionality of return unique values for hand comparison:
The snippet here is ugly, and beautiful in its own way. It is small and compact so you can keep it in your back pocket and whip it out when its needed..
```
	public static int eval(int a,int b,int c,int d,int e){
		int x=(a^b^c^d^e)&0x1FFF,y=(a|b|c|d|e)&0x1FFF,z=(y^x),v=y&y-1;
		boolean strt=0x1F1D100%y==0,flsh=(a&b&c&d&e)!=0;
		return strt&&flsh?0x20000000|(x==0x100F?15:x):strt
			?0x10000000|(x==0x100F?15:x):flsh?0x14000000|x
			:(v=(v&=v-1)==0?2:(v&=v-1)==0?3:(v&=v-1)==0?4:5)==3?z==0
			?0xC000000|(v=((a&b)==(a&0x1FFF)?a:(c&d)==(c&0x1FFF)?c:e)&0x1FFF^y)|v<<13
			:0x8000000|x|z<<13:v==4?0x4000000|x|z<<13
			:v==2?(a+b+c+d+e-x&0x1FFF)==(0x1FFF&(y^x)<<2)
			?0x1C000000|x|z<<13:0x18000000|z|x<<13:x;
	}
```


