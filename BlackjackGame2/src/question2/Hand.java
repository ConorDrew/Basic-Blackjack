package question2;

import java.util.ArrayList;
import question2.Card;
import question2.Card.Rank;
import java.io.Serializable;
import java.util.Collections;
import java.util.Iterator;
import question2.Card.Suit;


/**
 * The hand class contains a collection of hands 
 * @author conor - nnf15evu - 100137486
 */
public class Hand implements Serializable {
    static final long serialVersionUID = 102;
    public ArrayList<Card> hands;
    
    /**
     * Makes an empty hand
     */
    public Hand(){
        this.hands = new ArrayList<Card>();
    }
    
    /**
     * Makes new hand from an old hand
     * @param oldHand   The old hand you want copied
     */
    public Hand(Hand oldHand){
        this.hands = new ArrayList<Card>();
        this.hands.addAll(oldHand.hands);
        
    }
    
    /**
     * Adds a card object
     * @param addCard card object from Card class
     */
    public void AddCard(Card addCard){
        this.hands.add(addCard);
        size();
    }
    
    /**
     * Takes collection typed to Card and adds to current hand
     * @param cards
     */
    public void AddCards(ArrayList<Card> cards){
        this.hands.addAll(cards);
    }

    /**
     *  Adds an existing hand to current hand
     * @param addHand Takes Hand object
     */
    public void AddHand(Hand addHand){ 
        System.out.println("Hand Added!");
        this.hands.addAll(addHand.hands);
        //System.out.println(this.hands);
    }
    
    /**
     * removes single card from hand, card passed as argument
     * @param card from Card class
     * @return true if card removed, false not found
     */
    public boolean removeSingle(Card card){
        //loops though current hand looking for card to remove
        for (int i = 0; i < hands.size(); i++) {
            //if rank and suit match, remove
            if(this.hands.get(i).getRank() == card.getRank() &&
                    this.hands.get(i).getSuit() == card.getSuit()){
                this.hands.remove(i);
                return true;
                //System.out.println("Card removed");
            }
        }
        return false;
    }
    
    /**
     * Passes a hand class as an argument, removes all hands that match, returns
 true 
     * @param hand
     * @return
     */
    public boolean removeCards(Hand hand){
        boolean removed = false;
        //loops though this.hand
        for (int i = 0; i < hands.size(); i++) {
            //loops though argument hand, 
            for (int j = 0; j < hand.size(); j++) {
                //if suit and rank matches, remove
                if (this.hands.get(i).getRank() == hand.hands.get(j).getRank() &&
                      this.hands.get(i).getSuit() == hand.hands.get(j).getSuit()) {
                    this.hands.remove(i);
                    removed = true;
                }
            }
        }
        if (removed) {
            return true;
        }else{
            return false;
        }
    }
    
    /**
     * Removes the card in position that is passed as an argument
     * @param pos the position of the card you want removed starting from 1
     * @return card returns card if found.
     */
    //WORKS, ADD EXCEPTION FOR POS BIGGER THAN VALUE
    public Card removeCard(int pos){
        //checks if pos it within the bounds of index
        if (pos < this.hands.size() || pos > this.hands.size()) {
            //System.out.println("Card Removed");
            Card card = this.hands.get(pos);
            //System.out.println(card);
            this.hands.remove(pos-1); //-1 to deal with arrays starting at 0
            return card;
            
        }else{
            System.out.println("No card in that position");
            return null;
        }      
    }
    
    /**
     * Removes first card from list
     * @return returns card that was removed
     */
    public Card removeCard(){
        Card card = this.hands.remove(0); 
        
        return card;
    }
    
    
    /**
     * Gets the size of the current hand and returns the value
     * @return the size of the hand
     */
    public int size(){
        //System.out.println(this.hands.size() + " Cards left in deck");
        return this.hands.size();
    }

    /**
     * takes string input and counts how many hands of that Rank the hand has
     * @param s a string input 
     * @return the number of ranks in hand
     */
    public int CountRank(String s){
        int count = 0;
        s = s.toUpperCase();
        System.out.println("Value searched: " + s);
        
        for(Card aCard : this.hands){
            if (s.equals(aCard.getRank().name())) {
                count++;
            }
        }
        
        System.out.println("Found: "+ s + " " + count + " times");
        return count;
    }
    
    /**
     * Takes string input and counts how many hands of that suits the hand has
     * @param s a string input 
     * @return returns the number of suits in hand
     */
    public int CountSuit(String s){
        int count = 0;
        s = s.toUpperCase();
        System.out.println("Value searched: " + s);
        
        for(Card aCard : this.hands){
            if (s.equals(aCard.getSuit().name())) {
                count++;
            }
        }
        
        System.out.println("Found: "+ s + " " + count + " times");
        return count;
    }
    
    /**
     * Takes Int input, IF input is smaller than smallest possible value
 of all hands in hand return false
     * @param x Int as input 
     * @return returns boolean
     */
    public boolean isOver(int x){
        boolean isOver = false;
        int[] values = CardsValue();
        //gets smallest(soft) total to compare against
        if (x <= values[0]) {
            isOver = true;
        }

        return isOver;
    }
    
    /**
     * Sorts hands into Rank descending order 
     */
    public void sortDescending(){
        Collections.sort(this.hands);
    }

    /**
     * sorts hands in Suit order
     */
    public void sortAscending(){
        Collections.sort(hands, new Card.CompareSuit());
    }

    /**
     * Reverses the hand order and returns a new hand
     * @return return Hand object
     */
    public Hand reverseHand(){
        Hand reversed = new Hand();
        ArrayList<Card> temp = new ArrayList<>(this.hands);
        int size = temp.size();
        for (int i = 0; i < size; i++) {
            reversed.AddCard(temp.remove(temp.size()-1));
        }
        
        return reversed;
    }
    
    /**
     * Counts total value(s) of all hands in hand, Aces counted as high and low
 output will return all possible card values
     * @return return Array full of card values
     */
    public int[] CardsValue(){
        int count = 0;
        int aces = 0;
        int value[];
        //loop though hands in hand, if card is == to ACE, ACE++
        for(Card aCard : this.hands){
            if (aCard.getRank().compareTo(Rank.ACE) == 0) {
                aces++;
            }else{
                count = count + aCard.rank.getValue();
            }
        }
        
        //if there are aces, make an array that length + 1
        //loop though working out the soft and hard totals, 
        //find soft total, then add 10 to get hard total
        if (aces > 0) {
            value = new int[aces+1];
            count = count + aces;
            value[0] = count;
            for (int i = 1; i < aces+1; i++) {
                count = count + 10;
                value[i] = count;
            }
            
        }else{
            value = new int[1];
            value[0] = count;
        }
        
        return value;
    }
    

    @Override
    public String toString() {
        String output = "";
        if (this.hands.isEmpty()) {
            System.out.println("Hand Empty");
        }else{
            for(Card aCard : this.hands){
                
                output += "\n" + aCard.toString() ;
            }
        }
        
        return output;
    }
    
    
    public class orderAdded<Card> implements Iterator<Card>{
        
        private int index;

        @Override
        public boolean hasNext() {
            return index < hands.size();
        }

        @Override
        public Card next() {
            return (Card)hands.get(index++);
        }
        
    }
    
    public Iterator<Class> iterator(){
            return new orderAdded();
        }
    
    public static void main(String[] args) {
        /* ------------------ HAND TESTS ------------ */
        
        Deck playingDeck = new Deck();
        
        System.out.println(playingDeck);
        playingDeck.size();


        Hand player = new Hand();
        
        System.out.println(player);
        player.size();
        player.AddCard(new Card(Rank.ACE, Suit.SPADES));
        player.AddCard(new Card(Rank.EIGHT, Suit.CLUBS));
        player.AddCard(playingDeck.draw());
        player.AddCard(playingDeck.draw());
        player.AddCard(playingDeck.draw());
        player.AddCard(playingDeck.draw());
        player.AddCard(playingDeck.getCard(8));
        player.AddCard(playingDeck.getCard(21));
        System.out.println(player);
        player.size();
        
        int[] values = player.CardsValue();
        System.out.print("values = ");
        for (int i = 0; i < values.length; i++) {
            System.out.print(values[i]);
            if (values.length-1 == i) {
                System.out.println("");
            }else{
                System.out.print(", ");
            }
        }
        //System.out.println("value of cards = " + player.CardsValue());
        
        System.out.println("------------------------------");
        
        Hand test = new Hand();
        test.AddCard(playingDeck.draw());
        test.AddCard(playingDeck.draw());
        test.AddCard(playingDeck.draw());
        
        System.out.println(test);
        
        //Add player hand to this hand
        test.AddHand(player);
        //print new hand
        System.out.println(test);
        test.size();
        Card card = test.removeCard(1);
        System.out.println(card + " Removed");
        System.out.println(test);
        
        Card ace = new Card(Rank.ACE, Suit.DIAMONDS);
        System.out.println(test.removeSingle(ace));
        
        test.CountRank("ace"); //HAND Q10
        test.CountSuit("DIAMONDS"); //HAND Q11
        
        //HAND Q13
        System.out.println(player.isOver(17));
        System.out.println(player.isOver(15));
        
        //HAND Q14
        Hand reverse = test.reverseHand();
        System.out.println(reverse);

        //HAND Q8 & 9
        test.sortDescending();
        test.sortAscending();
        System.out.println(test);
        
        //HAND Q1
        Hand moreHands = new Hand(player);
        System.out.println("player Hand");
        System.out.println(player);
        //moreHands.AddCard(playingDeck.draw());
        System.out.println("moreHands Hand");
        System.out.println(moreHands);
        
        //Q4
        //make collection
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card(Rank.ACE, Suit.DIAMONDS));
        cards.add(new Card(Rank.QUEEN, Suit.DIAMONDS));
        cards.add(new Card(Rank.TEN, Suit.DIAMONDS));
        cards.add(new Card(Rank.TEN, Suit.SPADES));
        cards.add(new Card(Rank.TWO, Suit.CLUBS));
        cards.add(new Card(Rank.SIX, Suit.HEARTS));
        cards.add(new Card(Rank.KING, Suit.DIAMONDS));
        cards.add(new Card(Rank.JACK, Suit.CLUBS));
        cards.add(new Card(Rank.TEN, Suit.DIAMONDS));
        cards.add(new Card(Rank.JACK, Suit.CLUBS));
        
        System.out.println("");
        System.out.println("Cards collection");
        printList(cards);
        System.out.println("player Hand");
        System.out.println(player);
        
        player.AddCards(cards);
        
        System.out.println("Added \nnew hand");
        System.out.println(player);

        //moreHands.removeCard();
        System.out.println(moreHands.removeCard() + " has been removed");
        System.out.println(moreHands);
        System.out.println(moreHands.removeCard(1) + " has been removed");
        System.out.println(moreHands);
        //moreHands.removeSingle(new Card(Rank.ACE, Suit.DIAMONDS));
        //System.out.println(moreHands);
        
        System.out.println("");
        System.out.println("Remove Single Card");
        Card toRemove = new Card(Rank.ACE, Suit.DIAMONDS);
        System.out.println(moreHands.removeSingle(toRemove));
        System.out.println("");
        System.out.println(moreHands);
        System.out.println(player);
        
        System.out.println("NEW REMOVE");
        player.removeCards(moreHands);
        System.out.println(player);
        
        System.out.println("------------------------------------");
        
        System.out.println(moreHands);
        Hand reversed = new Hand();
        reversed = moreHands.reverseHand();
        
        reversed.sortAscending();
        reversed.sortDescending();
        
        
        Iterator<Class> it = reversed.iterator();
        while(it.hasNext()){
            System.out.println(it.next());
        }
    }
    public static void printList(ArrayList<Card> cards){
        for(Card e:cards){
            System.out.println(e.toString());
        }
        System.out.println();
    }  
}
