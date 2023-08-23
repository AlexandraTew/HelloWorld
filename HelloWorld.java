import java.util.Random;

public class HelloWorld {

    public static void main(String[] args){
        
        System.out.println("Hello World!");

        // Simulate a dice roll
        int diceValue = rollDice();
        System.out.println("You rolled a " + diceValue);
    }
         // Function to simulate a dice roll
    public static int rollDice() {
        Random random = new Random();
        return random.nextInt(6) + 1; // Generates a random number from 1 to 6
    }

}
