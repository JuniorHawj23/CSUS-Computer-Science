package Brian;

// Name:  Brian Hert
// Class: CSC 15
// Date:  10/24/21
// Assignment: Programming Assignment 3

import java.util.Scanner;

public class EncryptionMachineMainHelper {
	
	// CONSTANTS
	public static final String ALPHABET = "abcdefghijklmnopqrstuv";
	public static final int SHIFT = 3;
	
	// Create a variable that stores the plain text of the key value
	// that will be entered; this is very likely not safe practice
	public static String keyValue = "";
	
	// main encryption method
	public static void caesarCipher() {
		
		// scanner objective
		Scanner scanner = new Scanner(System.in);
		
		// show a brief description of what the program does to the user
		programDescription();
		
		// ask the user for a key
		String keyString = inputKey(scanner);
		// set the public class attribute to the key string thats passed; not neccesary nor safe practice
		keyValue = keyString;
		
		// encrypt this plain word and display to the user
		String encryptedKeyString = encryptWord(keyString);
		// display the plain word and the encrypted word to the user
		displayBanner(keyString, encryptedKeyString);
		
		// prompt the user for how many words are in the message 
		int towards = inputNumberOfWords(scanner);
		
		// loop this through totalWords amount to store and encrypt a message
		for (int i=0; i < towards; i++) {
			// Retrieve the plain text word
			String plainWord = inputWord(scanner);
			
			// encrypt the plain text
			String encryptedWord = encryptWord(plainWord);
			
			// display a message to the user showing plain text word and encrypted word
			displayBanner(plainWord, encryptedWord);
			
		}
		System.out.println("Message fully encrypted. Happy secret messaging!");
	}
	
	// METHODS TO ENCRYPT
	// encrypts a single letter
	public static char encryptLetter(char letter) {
		// plaintext letter
		
		int originalCharIndex = ALPHABET.indexOf(letter);
		
		// use module to find index backwards
		int newCharIndex = (originalCharIndex + SHIFT) % ALPHABET.length();
		
		// return the shifted letter
		char newLetter = ALPHABET.charAt(newCharIndex);
		return newLetter;
	}
	
	// encrypts a single word utilizing the encryptLetter method above
	public static String encryptWord(String word) {
		// create a string variable that will store the new encrypted word
		String encryptedWord = "";
		
		// loop through the word and encrypt every letter
		for(int i = 0; i < word.length(); i++) {
			
			// figure what letter is at this specific index
			char letter = word.charAt(i);
			
			// once figure above letter, find its index in the original alphabet; returns int
			// call the encryptLetter method which returns a shifted alphabet letter
			char newLetter = encryptLetter(letter);
			encryptedWord += newLetter;
		}
		
		// return the encrypted word in assignment doc. 
		return encryptedWord;
	}
	
	// METHODS TO INTERACT WITH THE USER
	// method to print the plain word and encrypted word
	public static void displayBanner(String word, String encryptedWord) {
		// print the plain text word and the encrypted word
		System.out.printf( "\"%s\", has been encrypted to: %s\n", word, encryptedWord);
	}
	
	// method to get the key from the user
	public static String inputKey(Scanner scanner) {
		// prompt the user
		System.out.print("Enter key: ");
		
		// retrieve input from the user
		String keyString = scanner.next();
		return keyString;
	}
	
	// method to prompt the user for a # specifying the length of their "message"
	public static int inputNumberOfWords(Scanner scanner) {
		// prompt the user
		System.out.println("How many words is your message? ");
		
		// retrieve the input from the user
		int totalWords = scanner.nextInt();
		return totalWords;
	}
	
	// method to prompt the user to enter a word for their "message"
	public static String inputWord(Scanner scanner) {
		// prompt the user
		System.out.print("Next word: ");
		
		// retrieve the input from the user 
		String inputString = scanner.next();
		return inputString;
	}
	
	// MISC METHODS
	// method to store the description 
	public static void programDescription() {
		// give a brief description of what the program does to the user 
		System.out.println("Welcome to the CSC 15 Encryption Machine\nThe Program lets you encrypt a message");
		System.out.println("with a key for your recipient to decrypt!\n");
		
		System.out.println("Encrypted messages use a shared keyword to decrypt.");
	}
}
