package question2;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * The hand class makes a card 
 * @author conor - nnf15evu - 100137486
 */
public class Card implements Comparable<Card>, Serializable{
    
    static final long serialVersionUID = 111;

    public Rank rank;
    public Suit suit;

    /**
     * Enter the card details
     * @param rank Enter the card rank e.g TWO, THREE, QUEEN, ACE
     * @param suit Enter what suit the card is
     */
    public Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
    }

    /**
     * Gets the rank of the card
     * @return rank of card
     */
    public Rank getRank() {
        return rank;
    }

    /**
     * Gets the suit of the card
     * @return Suit of card
     */
    public Suit getSuit() {
        return suit;
    }

    @Override
    public String toString() {
        return rank + " of " + suit;
    }
    
    /**
     * Gets the sum of two cards
     * @param card1
     * @param card2
     */
    public static void sum(Card card1, Card card2){
        int sum = card1.rank.value + card2.rank.value;
        if(sum == 21){
            System.out.println("BlackJack");
        }
        System.out.println(card1.rank.value + card2.rank.value);
    }

    /**
     * prints out the message.
     */
    public static boolean isBlackjack(Card card1, Card card2){
        //if card 1 or card 2 value is 11 or 13  set blackjack to true
        return card1.rank.getValue() == 11 && card2.rank.getValue()  == 10 || 
                card2.rank.getValue()  == 11 && card1.rank.getValue()  == 10;
        
    }

    @Override
    public int compareTo(Card o) {
        //sorts ascending, if ranks are the same, compare names, if names match
        //sort by suit, keeping a uniform look
        int comparison = this.rank.value - o.rank.value;
        int rankCompare = this.rank.compareTo(o.rank);
        if (comparison != 0) {
            return comparison;
        }
        if (rankCompare != 0) {
            return this.rank.compareTo(o.rank);
        }
        
        return this.suit.compareTo(o.suit);
        
        
    }
    
    /**
     * compares the suits of cars
     */
    public static class CompareSuit implements Comparator<Card>{
        @Override
        public int compare(Card o1, Card o2) {
            
            return o1.suit.compareTo(o2.suit);
        }
    }
    
    /**
     * Enum to hold the ranks and values of the cards, 
     */
    public enum Rank {TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7),
            EIGHT(8), NINE(9), TEN(10),JACK(10), QUEEN(10), KING(10), ACE(11);
        
        int value; 
        private static Rank[] vals = values();
        Rank(int x){
            value = x;
        }
        
        /**
         * gets the value of the rank
         * @return value of rank
         */
        public int getValue(){
            return value;
        }
        
        /**
         * gets the next card in the enum 
         * @return values
         */
        public Rank Next(){
            //get index pos mod length 
            return vals[(this.ordinal()+1) % vals.length];
        }
        
        /**
         * gets the previous card in the enum
         * @return
         */
        public Rank Previous(){
            if (this.ordinal()-1 == -1) {
                return vals[vals.length-1];
            }else{
                return vals[this.ordinal()-1];
            }
        }        
    }
    
    /**
     * holds the suit of the card.
     */
    public enum Suit {CLUBS, DIAMONDS, HEARTS, SPADES}
    
    
    public static void main(String[] args) {
        /* ------------------ CARD TESTS ------------ */
        
        for(Rank r : Rank.values()){
            System.out.println(r + " = " + r.getValue());
        }
        for(Rank r : Rank.values()){
            System.out.print(r + " POSITION:  " + r.ordinal() + " | ");
            //System.out.println(r.Next());
            System.out.print("next = " + r.Next() + " | ");
            System.out.println("prev = " + r.Previous());
        }

        Card ace = new Card(Rank.ACE, Suit.DIAMONDS);
        Card queen = new Card(Rank.QUEEN, Suit.DIAMONDS);
        
        System.out.println(ace.toString());
        System.out.println(ace.rank.getValue());
        
        Card.sum(ace, queen);
        
        ArrayList<Card> cards = new ArrayList<Card>();
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

        cards.add(new Card(Rank.TEN, Suit.DIAMONDS));
        cards.add(new Card(Rank.TEN, Suit.SPADES));
        cards.add(new Card(Rank.TWO, Suit.CLUBS));
        cards.add(new Card(Rank.SIX, Suit.HEARTS));
        
        System.out.println
                ("---------------List before Comparison---------------");
        printList(cards);
        
        Collections.sort(cards);
        
        System.out.println
                ("------------List after comparison ---------------");
        printList(cards);
        
        Collections.sort(cards, new CompareSuit());
        System.out.println
                ("------------List after comparison ---------------");
        printList(cards);
    }
    
    public static void printList(ArrayList<Card> cards){
        for(Card e:cards){
            System.out.println(e.toString());
        }
        System.out.println();
    }    
    
}
