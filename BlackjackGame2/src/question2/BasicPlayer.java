package question2;

import java.io.Serializable;
import java.util.List;

/**
 *Makes the rules for the basic players, more other players no matter what 
 * level they are will follow these basic rules.
 * @author conor - nnf15evu - 100137486
 */
public class BasicPlayer implements Player, Serializable{
    static final long serialVersionUID = 177;
    
    protected Hand hand;
    private int balance;
    protected int betAmount = 10;
    public String playerID;
    Card dealerCard;
    int count;
    
    protected int tempBalance;
    protected static int diffrence;
    
    
    public BasicPlayer() {
        this.balance = 200;
        this.tempBalance = this.balance;
        this.betAmount = 10;
        hand = new Hand();
    }
    
    
    public void setID(String id){
        this.playerID = id;
    }
    public String getID(){
        return this.playerID;
    }
    
    public int getDiffrence(){
        return this.diffrence;
    }
    
    
/**
* newHand: this method should clear the previous hand ready for new hands and 
 return the old hand
**/
    public Hand newHand(){
        Hand toReturn = this.hand; // stores current hand in toReturn
        hand = new Hand(); //makes new hand
        return this.hand;
    }
    
/**
 * makeBet: This method determines what bet the player will make. It should be 
 *called prior to any hands being dealt. 
 */
    public int makeBet(){
        return betAmount;
    }
    
/**
 * getBet: 
 * @return the bet for the current game. This must not exceed the 
 * players balance
 */    
    public int getBet(){
        return betAmount;
    }

/**
 * getBalance: 
 * @return the players current total pot. 
 */
    public int getBalance(){
        return this.balance;
    }
    
/**
 * hit: this method should determine whether the player wants to take a card. 
 * @return true if a card is required, false otherwise. 
**/
    public boolean hit(){
        // get sum of current hand if total is 17 + stick else hit.
        return !(getHandTotal() >= 17 || isBust());
    }
    
/**
 * takeCard: If a card is requested by hit() it should be added to the players 
 * hand with this method
 * @param c 
 */    
    public void takeCard(Card c){
        //System.out.println(c + " Added to hand");
        hand.AddCard(c);
    }

 /**
 * settleBet: The value passed is positive if the player won, negative otherwise. 
 * @return true if the player has funds remaining, false otherwise. 
 */
    public boolean settleBet(int p){
        
        this.balance = this.balance + p;
        
        if (this.balance > 0 + this.betAmount) {
            return true;
        }else{
            return false;
        }
        
    }
    
/**
 * 
 * getHandTotal: @return the hand total score. If the payer has one or more aces
 * , this should return the highest HARD total that is less than 21. 
 * So for example
 * ACE, THREE   should return 14.
 * ACE, THREE, TEN should return 14.
 * ACE, ACE, TWO, THREE should return 17.
 * ACE, ACE, TEN should return 12.
 * 
 * this isnt to calculate who won, this is for the player to know what they have
 * and how to bet / hit / Stick
 * 
 */
    public int getHandTotal(){
        int[] sum = hand.CardsValue();
        for (int i = sum.length - 1; i >= 0; i--) {
            if (sum[i] <= 21) {
                //System.out.println(sum[i]);
                return sum[i];
            }else{
                isBust();
            }
            
        }
        return sum[0];
    }
    
/**
 * 
 * blackjack: @return true if the current hand is a black jack (ACE, TEN or 
 * PICTURE CARD)
 */    
    public boolean blackjack(){
        //get first 2 cards and run isBlackjack command to see if it is a 
        //blackjack or not
        Card card1 = this.hand.hands.get(0);
        Card card2 = this.hand.hands.get(1);

        if (Card.isBlackjack(card1, card2)) {
            return true;
        }else{
            return false;
        }
    }
    
/**
 * 
 * isBust: @return true if the current hand is bust
 */    
    public boolean isBust(){
        if(this.hand.isOver(22)){
            //System.out.println("BUST");
            return true;
        }else{
            return false;
        }     
    }
    
/**
 * 
 * getHand: @return the current hand
 */    
    public Hand getHand(){
        return hand;
    }
    
/**
 * viewDealerCard. This method allows the dealer to show the player their card. 
 * @param c Dealers first card
 */
    public void viewDealerCard(Card c){
        dealerCard = c;
    }

 /**
 * viewCards: This method allows the dealer to show all the hands that were 
 played after a hand is finished. If the player is card counting, they will 
 * need this info
 */    
    public void viewCards(List<Card> cards){
        for(Card c: cards){
            //System.out.println(c);
        }
    }
    
/**
 * newDeck. This method is called by the dealer to tell them the deck has been 
 * re-shuffled
 */    
    @Override
    public void newDeck(){
        //new deck was made by dealer
        //reset cound for card counting
        //System.out.println("NEW DECK!");
        count = 0;
        
        //get balance,
        diffrence  = this.balance - this.tempBalance;
        //System.out.println("diffrence: " + diffrence);
        //find diffrence between old and new balance
        //store in static?? to keep track of all players
        //restore new balance
        this.tempBalance = this.balance;
        
    }
}
