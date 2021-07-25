import java.util.*;

public class example {
    public static void main(String[] args) {
        Scanner console = new Scanner(System.in); // Scanner class that we will be working with
        Random generator = new Random(); // Random number generator that will be used later on
        long startTime = System.currentTimeMillis(); // Saves the starting time
        System.out.print("Please enter a number: "); // Notice how its print instead of println... it's important
        String input = console.nextLine(); // Reads user input
        long endTime = System.currentTimeMillis(); // Gets the ending time
        long deltaTime = endTime - startTime; // Gets the difference in time... Use to calculate how long it takes
        System.out.println("It took you " + deltaTime + " milliseconds to enter a number"); // Gives us an update
        try {
            int number = Integer.parseInt(input); // Parses the input and gets an actual number
            int random = generator.nextInt(number); // Generates a random number
            System.out.println("Random number between 0 and " + number + " is " + random); // Updates us
        } catch (Exception e) {
            System.out.println("Parse error... you entered invalid number"); // This happens when user enters invalid
        }
        console.close();
    }
}
