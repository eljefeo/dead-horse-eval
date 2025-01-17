package com.jmedia.poker;

public class Tests {

	DeadHorse7 dh = new DeadHorse7();

	public static void main(String args[]) throws Exception {
		shortToLong();
		shortToDecimalToLong();
		check52GeneratedSameAsInline();
	}

	public static void shortToLong() throws Exception {
		String card = "AH";
		String expected = "Ace of Hearts";
		String name = Util.convertHumanShortNameToLongName(card);
		if (!name.equals(expected)) {
			throw new Exception(card + " did not convert to " + expected + " : " + name);
		}
		System.out.println("success: " + expected);
	}

	public static void shortToDecimalToLong() throws Exception {
		String card = "6S";
		long cardDecimal = Util7.shortCardNameToDecimal(card);
		String shortName = Util7.convertDecimalToShortCardName(cardDecimal);
		if (!shortName.equals(card)) {
			throw new Exception(
					"Error converting. card: " + card + ", cardDecimal: " + cardDecimal + ", shortName: " + shortName);
		} else {
			System.out
					.println("Converted card: " + card + ", cardDecimal: " + cardDecimal + ", shortName: " + shortName);
		}
		String expected = "Six of Spades";

		String longName1 = Util.convertHumanShortNameToLongName(card);
		String longName2 = Util7.convertDecimalToLongName(cardDecimal);
		if (!longName1.equals(longName2) || !longName1.equals(expected)) {
			throw new Exception(card + " did not convert..." + longName1 + " : " + longName2);
		} else {
			System.out.println("Converted card: " + card + ", longName1: " + longName1 + ", longName2: " + longName2);
		}
		System.out.println("success: " + expected) ;
	}

	public static void check52GeneratedSameAsInline() throws Exception {
		long[] generated = Util7.makeAll52CardsDecimal();
		long[] inline = Util7.all52CardsDecimal;
		if (generated.length != inline.length) {
			throw new Exception("Generated cards dont match length of inline, are there 52?");
		}
		for (int i = 0; i < generated.length; i++) {
			if (generated[i] != inline[i]) {
				throw new Exception(
						"Generated cards dont match. Generated: " + generated[i] + ", inline: " + inline[i]);
			}
		}

		System.out.println("Cool, 52 cards are still generated correctly....");
	}

}
