package question2;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * changes some functions from the Basic player so a human can play the game
 * main changes are Make Bet, and if the player wants to stick or Hit
 * @author conor - nnf15evu - 100137486
 */
public class HumanPlayer extends BasicPlayer{
    
    public HumanPlayer(){
        super();
        playerID = "HUMAN PLAYER";
    }
    
    
    /**
    * makeBet: This method determines what bet the player will make. It should be 
    * called prior to any hands being dealt. 
    */
    public int makeBet(){
        //makes a scanner so input can be read
        Scanner scan = new Scanner(System.in);
        int input = 0;
        boolean goodInput = false;
            
        while(!goodInput){
            System.out.print("Place your Bet: £");
            try
            {
                input = scan.nextInt();
                goodInput = true;
            }
            catch (InputMismatchException  ex )
            {
                System.out.println("You entered a wrong input, please "
                        + "only use whole numbers." );
                System.out.println("Please try again.\n" );
                String flush = scan.next();
            }
            if (getBalance() < input || input <= 0) {
                System.out.println("Not enough money, your balance is £" 
                                                            + getBalance());
                
                goodInput = false;
            }
        }
        
        //if the player is trying to bet too much, it will keep asking the
        //how much to bet, until a valid number is entered.
//        while(getBalance() < input || input <= 0){
//            System.out.println("Not enough money, your balance is £" + getBalance());
//            System.out.print("Place your Bet: £");
//            input = scan.nextInt();
//        }
        betAmount = input;
        
        return input;

    }
    
    /**
    * hit: this method should determine whether the player wants to take a card. 
    * @return true if a card is required, false otherwise. 
    **/
    
    public boolean hit(){
        boolean goodInput = false;
        boolean result = true;
        String hitVal = "";
        Scanner scan = new Scanner(System.in);
        
        //if player is bust or has blackjack, return false, as nothing can
        //be gain from playing
        if (isBust() || blackjack()) {
            return false;
        }
        System.out.println("Your Hand:");
        System.out.println(getHand());
        System.out.println("Your Hand Total: " + getHandTotal());
        
        //while loop to make sure a correct input is made, 
        //multiple selections, if default is called, it will ask again
        //and not move forwards in the game.
        while(!goodInput){
            System.out.print("Do you want to hit? ");
            hitVal = scan.nextLine();
            switch(hitVal.toLowerCase()){
                case "y":
                case "yes":
                case "h":
                case "hit":
                    System.out.println("HIT");
                    goodInput = true;
                    result = true;
                    break;
                case "n":
                case "no":
                case "s":
                case "stick":
                    System.out.println("STICK");
                    goodInput = true;
                    result = false;
                    break;
                case "quit":
                    System.out.println("Quitting");
                    System.exit(0);
                case "help":
                case "?":
                    System.out.println("\"quit\" : quits program");
                    System.out.println("\"stick \\ s \\ n \\ no \" "
                                                        + ": Stick hand");
                    System.out.println("\"hit \\ h \\ y \\ yes \" "
                                                        + ": Hit hand");
                    
                    goodInput = false;
                    break;
                default: System.out.println("Not a valid entry");
                    goodInput = false;
                    break;
            }
        }
        
        return result;
        
    }
    
    
}
