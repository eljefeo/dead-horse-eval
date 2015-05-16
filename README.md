# DeadHorseEval

So I decided to beat a dead horse and make another 5 card evaluator. I did not like the idea of lugging around a >100MB lookup table file. I figured this eval would go into an app someday and who in their right minds would attach a 100MB file to a 10MB game just to eval the hand?
So I wanted to make a fast and small eval, yes like so many others. 

I simply wanted to try my hand at the ol' poker hand evaluator. It seemed like a fun project.

The idea behind the eval is simple, set the 52 cards in such a way, where it would be easy to tell what hand you have when any 5 of those cards are passed to an evaluator.
You see, with the lookup table, all the preprocessing has been done already, which is why they are so damn fast. I wanted to do preprocessing as well, just without the added space of a file.

So my preprocessing is setting up 52 specific numbers (cards):
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

