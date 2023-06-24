package Brian;

import java.util.Arrays;

/*
 * Name: Brian Hert
 * Date: 6/26/2020
 * Project:RecursiveProgram
 * Project Description: This program contains two methods. One accepts an integer
 * and returns the integer stripped of all odd digits. The second accepts an integer array
 * and returns true if it is a palindrome or false if it is not.
 * The output is put to the console. The requirements are the Arrays class from the java.util
 * library used for slicing and printing the integer arrays. 
 */

public class RecursiveProgram {

	// this method recursively removes odd digits from an integer and returns the result
	static int removeOdds(int num) {
		// base case
		if (num == 0) return 0;
		
		// recursive calls
		if (num % 2 != 0) return removeOdds(num/10);
		return removeOdds(num/10) * 10 + (num % 10);
	}

	// this method recursively compares the first and last digits of an integer array and returns true if it is a palindrome
	static boolean isPalindrome(int[] numArr) {
		
		// base case for true result
		if (numArr.length <= 1) return true;

		// lastDigit is the index of the final digit of the integer at any point during the recursive process
		int lastDigitIndex = numArr.length-1;
		
		// if the first and last digits are equal, calls method again passing in a smaller array each time
		if (numArr[0] == numArr[lastDigitIndex]) {
			return isPalindrome(Arrays.copyOfRange(numArr, 1, lastDigitIndex));
		}
		
		// base case for false result
		else {
			return false;
		}
	}
	
	// main method begin
	public static void main(String[] args) {
		
		// sample integers for testing removeOdds method
		int[] removeTest = {1894124,48020,340234856, 13579};
		
		// sample integer arrays for testing isPalindrome method
		int[] arr1 = {3,2,5,2,1};
		int[] arr2 = {1,2,8,2,1};
		int[] arr3 = {1};
		int[] arr4 = {7,8,9,10,10,9,8,7};
		
		int[][] palindromeTest = {arr1, arr2, arr3, arr4};

		// running tests on removeOdds method
		for (int i : removeTest) {
			System.out.printf("The integer %s with odds digits removed is: %s\n",i,removeOdds(i));
		}
		
		// running tests on isPalindrome method
		for (int[] i : palindromeTest) {
			if (isPalindrome(i)) {
				System.out.println(Arrays.toString(i) + " is a palindrome");
			} else {
				System.out.println(Arrays.toString(i) + " is not a palindrome");
			}
		}
	} // end main method
}
