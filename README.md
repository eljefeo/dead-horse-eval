# DeadHorseEval

So I decided to beat a dead horse and make another 5 card evaluator. I did not like the idea of lugging around a >100MB lookup table file. I figured this eval would go into an app someday and who in their right minds would attach a 100MB file to a 10MB game just to eval the hand?
So I wanted to make a fast and small eval, yes like so many others. 

I simply wanted to try my hand at the ol' poker hand evaluator. It seemed like a fun project.

The idea behind the eval is simple, set the 52 cards in such a way, where it would be easy to tell what hand you have when any 5 of those cards are passed to an evaluator.
You see, with the lookup table, all the preprocessing has been done already, which is why they are so damn fast. I wanted to do preprocessing as well, just without the added space of a file.

So my preprocessing is setting up 52 specific numbers (cards):

##1. The Cards
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

##2. The 3 Hands

Now, setting the cards up like this gave me some advantages. Because in poker there are really 3 types of hands:
* Straights	(where the numbers matter) 
* Flushes 	(where the suits matter)  
* Duplicates 	(where the quantities matter)

This bit structure came about with this in mind.

So how would I handle each of these 3 types of hands ???

#####Straights:
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
So to get around this we get the Least Common Multiple of 4111 (A234)5 and 7936 (AKQJ10) which comes out to 32624896

Now this number, 32624896, when modulo'd against any of our straights, including a low Ace straight will give 0 !!

######Finally for Straights.....
we find out straights by 
* OR'ing all 5 cards
* Mask the suit bits
* If (32624896 % ourNumber) == 0 {Straight !}

#####Flushes:
We find our flushes in a similar way to the straights above. First we OR all the cards together, except instead of masking the suits away, we mask the card numbers away and only leave the suits, since that is all we are interested in for a flush. So...
```
00100000000001000 : Five of clubs
00100000010000000 : Nine of clubs
00100001000000000 : Jack of clubs
00100000000000001 : Two of clubs
00101000000000000 : Ace of clubs
|
00101001010001001 : our results from Or'ing, 21129 in decimal
```
And our mask in this case would be the opposite of our Suit Mask used for finding straights, so can use the bitwise NOT (~) of our Suit Maske to get our Number Mask:
```
Suit Mask = 8191
00000000000000000001111111111111

Number Mask = ~8191
11111111111111111110000000000000
```

Using that Numer mask we can isolate our suits in the five cards:
```
00000000000000000101001010001001 : our results from Or'ing our five flush cards above, 21129 in decimal
&
11111111111111111110000000000000
=
00000000000000000100000000000000, 
```
