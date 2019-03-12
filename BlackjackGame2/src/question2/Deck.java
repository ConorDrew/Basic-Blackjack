package question2;

import question2.Card;
import question2.Card.Rank;
import question2.Card.Suit;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * The hand class makes the deck.
 * @author conor - nnf15evu - 100137486
 */
public class Deck implements Serializable {
    
    static final long serialVersionUID = 112;
    
    private ArrayList<Card> deck;

    /**
     * constructor to make a a new arraylist full of deck also to make a 
 full deck.
     */
    public Deck(){
        this.deck = new ArrayList<Card>();
        createFullDeck();
    }
    
    /**
     * creates a full deck, making sure each suit and rank has been made.
     */
    public void createFullDeck(){
        //generate Cards here
        for(Suit cardSuit : Suit.values()){
            for(Rank cardRank : Rank.values()){
                //add a new card to the deck
                this.deck.add(new Card(cardRank, cardSuit));
            }
        }
    }
    
    /**
     * A shuffle function takes the current size of the deck and shuffles them
     * randomly.
     */
    public void shuffle(){
        ArrayList<Card> tempDeck = new ArrayList<Card>();
        Random random = new Random();
        int randomIndex = 0;
        int deckSize = this.deck.size();
        for (int i = 0; i < deckSize; i++) {
            //finds random index 1 smaller than array size
            randomIndex = random.nextInt((this.deck.size()-1 - 0) + 1) + 0;
            //adds to temp deck
            tempDeck.add(this.deck.get(randomIndex));
            
            this.deck.remove(randomIndex);
        }
        this.deck = tempDeck;
    }
    
    /**
     * remove a card from the deck as position i
     * @param i
     */
    public void removeCard(int i){
        this.deck.remove(i);
    }
    
    /**
     * Gets the card from the position passed in the argument.
     * @param i
     * @return deck
     */
    public Card getCard(int i){
        return this.deck.get(i);
    }
    
    public void addCard(Card addCard){
        this.deck.add(addCard);
    }
    
    /**
     * draws the top card from the deck, and removes it from the deck.
     * @return card
     */
    public Card draw(){
        Card card = this.deck.get(0);
        //this.deck.add(deck.getCard(0));
        removeCard(0);//remove card from top of deck
        
        return card;
    }
    
    /**
     * Gets the size of the deck.
     * @return
     */
    public int size(){
        //System.out.println(this.deck.size() + " Cards left in deck");
        return this.deck.size();
    }
    
    /**
     * clears all the deck from the deck.
     */
    public void clearDeck(){
        //removes all deck from list.
        this.deck.removeAll(deck);
    }
    
    /**
     * makes a new deck, first it gets rid of all the deck in the deck
 makes a new deck and then shuffles the deck in current deck.
     */
    public final void newDeck(){
        clearDeck(); //removes all deck from old deck
        createFullDeck(); //re-intilizes a deck
        shuffle(); // shuffles deck ready to play
    }

    @Override
    public String toString() {
        String output = "";
        for(Card aCard : this.deck){
            output += "\n" + aCard.toString();
        }
        return output;
    }
    
    /**
     * Creates an iterator of the deck/ 
     * @param <Card>
     */
    public class SecoundCardIterator<Card> implements Iterator<Card>{
        
        private int index;
        
        /**
         * sets the index to 0
         */
        public SecoundCardIterator(){
            index = 0;
        }

        @Override
        public boolean hasNext() {
            return index < deck.size();
        }

        @Override
        public Card next() {
            if (index % 2 == 0) {
                return (Card)deck.get(index++);
            }else{
                index++;
                return null;
            }
            
        } 

        @Override
        public void remove() {
            
        }
    }
    
    public Iterator<Class> iterator(){
            return new SecoundCardIterator();
        }
    
    public static void main(String[] args) {
        /* ------------------ DECK TESTS ------------ */

        Deck playingDeck = new Deck();
        
        System.out.println(playingDeck);
        playingDeck.size();
        
        playingDeck.shuffle();
        
        System.out.println(playingDeck);
        playingDeck.size();
        
        playingDeck.draw();
        playingDeck.draw();
        playingDeck.draw();
        playingDeck.draw();
        System.out.println(playingDeck);
        playingDeck.size();
        
        playingDeck.newDeck();
        //playingDeck.clearDeck();
        System.out.println(playingDeck);
        playingDeck.size();
        
        System.out.println("------------------------------------");
        Iterator<Class> it = playingDeck.iterator();
        while(it.hasNext()){
            System.out.println("\t" + it.next());
        }
    }
}
