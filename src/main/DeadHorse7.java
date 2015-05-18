package main;

public class DeadHorse7 {
	
	  static int[] allc = new int[]
			  {
				  65537,65538,65540,65544,65552,65568,65600,65664,65792,66048,66560,67584,69632,
				  32769,32770,32772,32776,32784,32800,32832,32896,33024,33280,33792,34816,36864,
				  16385,16386,16388,16392,16400,16416,16448,16512,16640,16896,17408,18432,20480,
				  8193,8194,8196,8200,8208,8224,8256,8320,8448,8704,9216,10240,12288
			  };
	
	  static String[] handNames = 
			{
				"High Card", "Pair", "Two Pair", "3 of a kind", 
				"Straight", "Flush", "Full House", "4 of a kind", "Straight Flush"
			};
	  
	public static void eval(){
		
	}
	
	public static void testEvaluator() {
		
		

		int[][] allTestHands = {
				{ allc[1], allc[17], allc[15], allc[24], allc[35], allc[45],
						allc[51] },
				{ allc[1], allc[17], allc[15], allc[25], allc[35], allc[45],
						allc[51] }, // aces
				{ allc[1], allc[16], allc[0], allc[25], allc[35], allc[13],
						allc[51] },// aces
				{ allc[12], allc[17], allc[15], allc[25], allc[35], allc[45],
						allc[51] },// aces
				{ allc[1], allc[2], allc[17], allc[3], allc[18], allc[48],
						allc[51] },// 34567
				{ allc[1], allc[44], allc[9], allc[6], allc[3], allc[8],
						allc[45] },// spades
				{ allc[4], allc[33], allc[46], allc[48], allc[35], allc[17],
						allc[30] },// 6 9 9 11 11 6 6 //6s full of 11s or 9s
				// {allc[6],allc[33],allc[19],allc[32],allc[50],allc[17],allc[45]},//4x8s
				{ allc[12], allc[25], allc[19], allc[32], allc[38], allc[17],
						allc[51] },// 4x8s
				{ allc[21], allc[16], allc[20], allc[18], allc[19], allc[17],
						allc[22] } // 78910J
		};
		// int[] cnm = trips;
		int counter = 0;
		int[] finalWinner = { 0, 0 };
		for (int[] cnm : allTestHands) {

			int[] csm = new int[cnm.length];
			// sortHighDown(cnm);
			for (int i = 0; i < cnm.length; i++) {
				csm[i] = (cnm[i] & 8191);
			}

			finalWinner[0] = 0;
			finalWinner[1] = 0;
			for (int i = 0; i < csm.length - 1; i++) {
				for (int j = i + 1; j < csm.length; j++) {
					for (int k = j + 1; k < csm.length; k++) {
						for (int l = k + 1; l < csm.length; l++) {
							for (int m = l + 1; m < csm.length; m++) {
								/*
								 * System.out.println("\n\n" + getName(cnm[i]) +
								 * " i\t" + bin(cnm[i]) + "\t"+(csm[i]&8191)
								 * +"\n"+getName(cnm[j]) + " j\t" + bin(cnm[j])
								 * + "\t"+(csm[j]&8191) +"\n"+getName(cnm[k]) +
								 * " k\t" + bin(cnm[k]) + "\t"+(csm[k]&8191)
								 * +"\n"+getName(cnm[l]) + " l\t" + bin(cnm[l])
								 * + "\t"+(csm[l]&8191) +"\n"+getName(cnm[m]) +
								 * " m\t" + bin(cnm[m]) + "\t"+(csm[m]&8191) );
								 */
								int[] cu = { cnm[i], cnm[j], cnm[k], cnm[l], cnm[m] };
								int fsm = (csm[i] | csm[j] | csm[k] | csm[l] | csm[m]);
								int[] c = { csm[i], csm[j], csm[k], csm[l],
										csm[m] };

								int winningHand = DeadHorseEval.eval(cnm[i], cnm[j], cnm[k], cnm[l], cnm[m]);
								// System.out.println(handNames[winningHand]);
								if (winningHand > finalWinner[0]) {
									finalWinner[0] = winningHand;
									finalWinner[1] = fsm;
								} else if (winningHand == finalWinner[0]) {
									if (fsm > finalWinner[1]) {
										finalWinner[1] = fsm;
									}
								}
								// System.out.println(handNames[winningHand]);

							}
						}
					}
				}
			}

			System.out.println(counter + ", " + finalWinner[0] + ", "
					+ (finalWinner[0] == counter++));
		}

		System.out.println("\n\nFinal winner : " + handNames[finalWinner[0]]);
	}

}
