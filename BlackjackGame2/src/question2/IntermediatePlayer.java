/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package question2;

/**
 *  makes an intermediate player, who takes the dealers card into consideration
 * and is a wear of their soft / hard total 
 * @author conor - nnf15evu - 100137486
 */
public class IntermediatePlayer extends BasicPlayer{
    
    public IntermediatePlayer(){
        super();
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
    
}
