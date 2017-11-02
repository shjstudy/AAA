package com.invengo.xcrf.view;

import invengo.javaapi.core.GBMemoryBank;
import invengo.javaapi.core.MemoryBank;

import java.util.Random;

public class DemoUtil {

	private static Random randGen = null;
	private static char[] numbersAndLetters = null;

	public static final String getRandomHexString(int length) {
		if (length < 1) {
			return null;
		}
		if (randGen == null) {
			randGen = new Random();
			numbersAndLetters = ("0123456789abcdef").toCharArray();
		}
		char[] randBuffer = new char[length * 2];
		for (int i = 0; i < randBuffer.length; i++) {
			randBuffer[i] = numbersAndLetters[randGen.nextInt(15)];
		}
		return new String(randBuffer);
	}
	
	public static GBMemoryBank getUserBank(int userBank) {
		GBMemoryBank bank = GBMemoryBank.GBEPCMemory;
		
		if(userBank == 0){
			bank = GBMemoryBank.GBUser1Memory;
		}else if(userBank == 1){
			bank = GBMemoryBank.GBUser2Memory;
		}else if(userBank == 2){
			bank = GBMemoryBank.GBUser3Memory;
		}else if(userBank == 3){
			bank = GBMemoryBank.GBUser4Memory;
		}else if(userBank == 4){
			bank = GBMemoryBank.GBUser5Memory;
		}else if(userBank == 5){
			bank = GBMemoryBank.GBUser6Memory;
		}else if(userBank == 6){
			bank = GBMemoryBank.GBUser7Memory;
		}else if(userBank == 7){
			bank = GBMemoryBank.GBUser8Memory;
		}else if(userBank == 8){
			bank = GBMemoryBank.GBUser9Memory;
		}else if(userBank == 9){
			bank = GBMemoryBank.GBUser10Memory;
		}else if(userBank == 10){
			bank = GBMemoryBank.GBUser11Memory;
		}else if(userBank == 11){
			bank = GBMemoryBank.GBUser12Memory;
		}else if(userBank == 12){
			bank = GBMemoryBank.GBUser13Memory;
		}else if(userBank == 13){
			bank = GBMemoryBank.GBUser14Memory;
		}else if(userBank == 14){
			bank = GBMemoryBank.GBUser15Memory;
		}else if(userBank == 15){
			bank = GBMemoryBank.GBUser16Memory;
		}
		return bank;
	}

	
}
