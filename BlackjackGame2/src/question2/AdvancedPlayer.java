package question2;

import java.util.List;

/**
 * Advanced player will play the same as the Intermediate player, but will count
 * cards and adjust their bed accordingly 
 * @author conor - nnf15evu - 100137486
 */
public class AdvancedPlayer extends BasicPlayer {
    
    
    public AdvancedPlayer(){
        super();
    }
    
    
    /**
    * makeBet: This method determines what bet the player will make. It should be 
    * called prior to any hands being dealt. 
    */
    public int makeBet(){
        betAmount = 10;
        //if count is positive betAmount * count
        if (count > 0) {
            //if balance is smaller than bet Amount, go all in
            if (getBalance() < betAmount) {
                betAmount = getBalance();
                return betAmount;
            }else{
                //bet amount * count.
                betAmount = betAmount * count;
            } 
        }
        System.out.println("Betting: £"+betAmount);
        return betAmount;
    }
    /**
    * hit: this method should determine whether the player wants to take a card. 
    * @return true if a card is required, false otherwise. 
    **/
    
    public boolean hit(){
        int[] sum = this.hand.CardsValue();
        
        //if hand has Aces, work off soft totals 
        if (sum.length > 1) {
            //if soft card is 9 or 10 stick
            if (sum[0] == 9 || sum[0] == 10) {
                return false;
            //if soft total is less than 8, Hit
            }else if(sum[0] <= 8){
                return true;
            }
            
        }//Doesnt Have an ace.
        if (dealerCard.rank.getValue() >= 7) {
            return !(getHandTotal() >= 17 || isBust());    
        }else{
            return !(getHandTotal() >= 12 || isBust());
        }
    }
    
    /**
    * viewCards: This method allows the dealer to show all the hands that were 
    * played after a hand is finished. If the player is card counting, they will 
    * need this info
    **/
    public void viewCards(List<Card> cards){
        //System.out.println(" count before: " + count);
        
        //loops though all the cards passed as an argument
        for(Card c: cards){
            //gets the value of the current card,
            int value = c.rank.getValue();
            //if value is 2..6 count + 1 , if value is a 10 or 11 count -1
            switch(value){
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:{
                    System.out.print("1,");
                    count++;
                    break;
                }
                case 10:
                case 11:
                    System.out.print("-1,");
                    count--;
                    break;
            }
        }
        //System.out.println(" count now: " + count);
    }
    
}
