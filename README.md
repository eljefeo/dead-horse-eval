# DeadHorseEval

So I decided to beat a dead horse and make another 5 card evaluator. I did not like the idea of lugging around a >100MB lookup table file. I figured this eval would go into an app someday and who in their right minds would attach a 100MB file to a 10MB game just to eval the hand?
So I wanted to make a fast and small eval, yes like so many others. 

I simply wanted to try my hand at the ol' poker hand evaluator. It seemed like a fun project.

The idea behind the eval is simple, set the 52 cards in such a way, where it would be easy to tell what hand you have when any 5 of those cards are passed to an evaluator.
You see, with the lookup table, all the preprocessing has been done already, which is why they are so damn fast. I wanted to do preprocessing as well, just without the added space of a file.

So my preprocessing is setting up the 52 cards:


