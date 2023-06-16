package Brian;

public class CoinConvertHelper {
    
      // FUNCTION: COINSCONVERT
      // SIMULATES THE ACTION OF A COINSTAR MACHINE THAT TURNS COINS INTO CASH
      // CONVERTS QUARTERS, DIMES, NICKELS, AND PENNIES TO A DOLLAR
      // PROVIDE A VALUE OF 10% FEE REDUCTION FROM CONVERTING COINS INTO CASH
	
    public static void coinsConvert() {
        // PROVIDE THE VALUE TO IT VARIABLE
        int quarters = 200, dimes = 10, nickels = 20, pennies = 100;

        // CALCULATING THE COIN WITH THE VALUE IT WAS ASSIGN TO
        double quartersValue = quarters * 25.0;
        double dimesValue = dimes * 10.0;
        double nickelsValue = nickels * 5.0;
        double penniesValue = pennies * 1.0;
        double totalCents = quartersValue + dimesValue + nickelsValue + penniesValue;
        // double totalCents = (200 * 25.0) + (10 * 10.0) + (20 * 5.0) + (100 * 1.0); 

        // PRINTING THE VALUE OF THE COINS AND WHAT IT'S EQUAL TO
        System.out.printf("%d quarters, %d dimes, %d nickels, %d pennies, is equal to %.1f cents.\n", 
                        quarters, dimes, nickels, pennies, totalCents);

        // GETTING THE FEE PRICE, AND DISPLAYING IN DOLLAR AMOUNT || CALCULATE 10% OF TOTAL VALUE
        double feesTotal = Math.round(totalCents * .1);

        // GETTING FEE VALUES
        int newVal = (int) feesTotal;
        int pennyFees = (newVal % 100);
        int dollarFees = (newVal / 100);

        // CALCULATING THE 10% FEE
        System.out.printf("The fee you will pay is: %d dollars and %d cents.\n", dollarFees, pennyFees);        

        // SUBTRACTING THE FEE COST FROM THE TOTAL
        int returnTotal = (int) (totalCents - feesTotal);

        // CALCULATING THE TOTAL AMOUNT AFTER FEES 
        // CONVERTING CENTS TO DOLLARS AND PENNIES IF NECESSARY
        int dollarsReturned = returnTotal / 100;
        int penniesReturned = returnTotal % 100;
        System.out.printf("You should get %d$ and %d cents.", dollarsReturned, penniesReturned);
    }
}
