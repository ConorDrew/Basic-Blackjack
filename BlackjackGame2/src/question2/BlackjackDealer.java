package question2;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Implements the Dealer from other code/
 * Fills out the blueprints and give the dealer their rules, and links up
 * this players 
 * @author conor - nnf15evu - 100137486
 */
public class BlackjackDealer implements Dealer, Serializable{
    static final long serialVersionUID  = 166;
    Deck mainDeck;
    Hand hand; 
    int diffrenceCount = 0 ;
    int count = 1;
    
    List<Player> players = new ArrayList<>();
    int[] bets = new int[8];
    
    /**
     *  when a new dealer is made, they make a new deck and shuffle t
     */
    public BlackjackDealer() {
        mainDeck = new Deck();
        mainDeck.shuffle();
    }
    public int getDiffrence(){
        return this.diffrenceCount;
    }
    
    //writes the diffrences to a file. 
    public void writeToFile() throws IOException{
        //what will be wrote to file
        String str = "Deck " + count + ": £" + getDiffrence();
        //the file it is saving it in
        BufferedWriter writer = new BufferedWriter
                                (new FileWriter("diffrence.txt", true));
        
        writer.append(str);
        writer.newLine();

        writer.close();
        count++;
    }
    
    /**
     * gets all the players that are at the table and assign them all
     * @param p
     */
    public void assignPlayers(List<Player> p){
        //adds all players to list
        this.players.removeAll(players);
        this.players.addAll(p);
        //loops though and gives them a id
        for (int i = 0; i < this.players.size(); i++) {
            //if the players name is "null" give it an ID so its easily
            //reconisable 
            if (players.get(i).getID() == null) {
                players.get(i).setID(Integer.toString(i+1));
            }
        }
    }
/**
 * takeBets: Take the bets for all the assigned players
*/
    public void takeBets(){
        
        int playersBet;
        
        //iterates though the players 
        //checks if they have enough to make a bet, if not,
        //security will be called and they will be 
        //forcibly removed from the table
        Iterator it = players.iterator();
        while(it.hasNext()){
            Player p = (Player) it.next();
            if (p.getBalance() >= p.getBet()) {
                playersBet = p.makeBet();
            } else{
                it.remove();
            }
        }
        
    }    

/**
  * dealFirstCards: Deal first two hands to each player, and one card to the dealer           
**/
    public void dealFirstCards(){
        this.hand = new Hand(); //clears dealers hand 
        //check if deck is less than 15
        
        
        
        //Checks if new deck is needed
        if (this.mainDeck.size() <= 17) {
            diffrenceCount = 0;
            for(Player p : players){
            this.mainDeck = new Deck();
            mainDeck.shuffle(); 
            p.newDeck();
            diffrenceCount = diffrenceCount +  p.getDiffrence();
            }
            //try save to file, if no file exists, error will be caught.
            try {
                    writeToFile();
                } catch (IOException ex) {
                    System.out.println("Cant write to file");
                }
            
        }
        
        //go round each player in list dealing two hands to each then one card
        //to the dealer
        for(Player p : players){
            p.takeCard(mainDeck.draw());
            p.takeCard(mainDeck.draw());
            
        }

        //give dealer 1 card
        this.hand.AddCard(mainDeck.draw());
        
        
    }

/**
 * play: play the hand of player p. 
 * Keep asking if the player wants a card until they stick or they bust
 * @return final score of the hand
 **/
   public int play(Player p){
       p.viewDealerCard(this.hand.hands.get(0));
       //makes sure the player wants to hits, if so they will draw a card.
       while (p.hit()) {
           p.takeCard(mainDeck.draw());
       }
       
       return scoreHand(p.getHand());
       
   }

/** playDealer: Play the dealer hand
 The dealer must take hands until their total is 17 or higher. 
 * @return dealer score
 */   
   public int playDealer(){
        while(scoreHand(this.hand) < 17){
            this.hand.AddCard(mainDeck.draw());
        }
        return scoreHand(this.hand);
   }
   
/**
 * scoreHand: The dealer should score the player hands, not rely on the player 
 * to do it.
 * @param h
 * @return score. Note if there are aces, this should be the highest possible 
 * value that is less than 21
 * ACE, THREE   should return 14.
 * ACE, THREE, TEN should return 14.
 * ACE, ACE, TWO, THREE should return 17.
 * ACE, ACE, TEN should return 12.
 */    
    public int scoreHand(Hand h){
        //gets the array from cardsValue
        int[] sum = h.CardsValue();
        for (int i = sum.length - 1; i >= 0; i--) {
            //loops though the array and find the lowest value that is below 21
            if (sum[i] <= 21) {
                //System.out.println(sum[i]);
                return sum[i];
            }
        }
        return sum[0];
    }
    
/** settleBets: This should settle all the bets at the end of the hand.
 * 
 */    
    public void settleBets(){
        //stores dealers first 2 hands to calc isBlackjack
        Card card1 = this.hand.hands.get(0);
        Card card2 = this.hand.hands.get(1);
        //stores the cards used in the last hand for counting cards.
        List<Card> roundCards = new ArrayList<>();
        
        //loops though each player
        //checking if they are going to win or loose. 
        //certain win factors have to come first to make it fair.
        for(Player p : players){
            int bet = p.getBet();
            int playerHand = scoreHand(p.getHand());
            if (p.isBust()) {
                //worst case, if player is bust they loose
                System.out.println("Player " + p.getID() 
                        + " is BUST, player looses");
                p.settleBet(-bet);
            }else if(Card.isBlackjack(card1, card2) && p.blackjack()){
                //check if player AND dealer got blackjack
                //if so return bet
                System.out.println("dealer AND player "+ p.getID() 
                        +" got blackJack player gets bet back");
                p.settleBet(0);
            }else if(Card.isBlackjack(card1, card2)){
                //check is dealer got blackjack, if yes player looses 
                System.out.println("dealer got blackjack, player Looses");
                p.settleBet(-bet);
            }else if(p.blackjack()){
                //check if player got blackjack
                //important if dealer is bust, player will still get correct
                //stake back
                System.out.println("Player "+ p.getID() +" got blackjack");
                p.settleBet(bet*2);
            }else if (this.hand.isOver(22) && !p.isBust()) {
                //check is dealer is bust, must come after blackjack tests
                //to guarantee correct stakes returned
                System.out.println("Dealer is BUST, Player " + p.getID() 
                        + " player wins");
                p.settleBet(bet);
            }else if (playerHand > scoreHand(this.hand)) {
                //player hand is bigger than dealers hand
                System.out.println("Player " + p.getID() 
                        + " score is higher, player wins");
                //player wins 
                p.settleBet(bet);
            }else if (playerHand < scoreHand(this.hand)) {
                //player hand is smaller than dealers hand
                System.out.println("Player " + p.getID() 
                        + " score is Lower, player Looses");
                //player looses
                p.settleBet(-bet);
            }else if (playerHand == scoreHand(this.hand)) {
                //player hand is same as dealer had
                System.out.println("Player " + p.getID() 
                        + " score is the same, player gets bet back");
                //player gets stake back
                p.settleBet(0);
            }
            
            //end of round pass hands from each player so they can count them
            for (int i = 0; i < p.getHand().hands.size(); i++) {
                roundCards.add(p.getHand().hands.get(i));
            }
        }
        //add dealer hands to the list
        roundCards.addAll(this.hand.hands);
        //after all hands have been added to collection, 
        //loop though all players
        //and show them the hands. 
        for(Player p : players){
            p.viewCards(roundCards);
        }
        
    }   
    
}
