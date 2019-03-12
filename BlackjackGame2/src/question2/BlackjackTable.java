package question2;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * BlackJack Table makes the table and sets the rules for the table, also sets
 * up the dealer to interact with the player and play the various type of 
 * game modes
 * @author conor - nnf15evu - 100137486
 */
public class BlackjackTable implements Serializable {
    static final long serialVersionUID  = 155;
    int maxPlayers = 8;
    int MinBet = 1;
    int maxBet = 500;
    
    BlackjackDealer dealer;
    List<Player> players;

    /**
     *  constructs the table, adding the players where needed.
     * @param dealer
     * @param players
     */
    public BlackjackTable(BlackjackDealer dealer, List<Player> players) {
        //assign players to dealer
        this.dealer = dealer;
        this.players = players;
        //dealer.assignPlayers(players);  
    }
    
    public void getProfitDiffrence(){
        int dif = dealer.getDiffrence();
        System.out.println("Diffrence: "+dif);
    }


    /**
     * assign players though dealer 
     */
    public void assignPlayer(){
        dealer.assignPlayers(players);
    }
    
    /**
     *  loop though players and get their balance
     */
    public void getPlayersBalance(){
        for(Player p : dealer.players){
            System.out.println("Player " + p.getID() + "'s balance £" + p.getBalance());
        }
    }

    /**
     * checks if table is empty
     * @return
     */
    public boolean tableEmpty(){
        if (dealer.players.isEmpty()) {
            return true;
        }else{
            return false;
        }
    }
    
    /**
     * calls takes bets
     */
    public void placeBets(){
        dealer.takeBets();
    }
    
    /**
     * calls start in dealer
     */
    public void start(){
        //System.out.println("There are " + dealer.players.size()
        //                                            + " in the game");
        dealer.dealFirstCards();
        System.out.println("First Cards Dealt");
    }
    
    /**
     * loop though each player and call play from dealer
     */
    public void playEachHand(){
        //loops though all players and let them play their hand
        for(Player p : dealer.players){
            dealer.play(p);
        }
        //players dealer
        dealer.playDealer();
    }

    /**
     *  calls settle bets
     */
    public void settleBets(){
        dealer.settleBets();
        clearHands();
    }

    /**
     *  loops though players and clear their hands
     */
    public void clearHands(){
        //remove all cards from all players so its a fresh start
        for(Player p : dealer.players){
            p.newHand();
        }
    }

    /**
     *  Adds all calls into one simple play function, can add or remove if 
     *  more or less detail is wanted.
     */
    public void play(){
        getPlayersBalance();
        placeBets();
        start();
        dealerCard();
        playerCards();
        playEachHand();
        playerCards();
        dealerCard();
        settleBets();
        getPlayersBalance();
        System.out.println(" --------------------------");
    }
    
    /**
     *  show the dealers card
     */
    public void dealerCard(){
        System.out.println("Dealer hand: ");
        System.out.println(dealer.hand.toString());
        System.out.println("Dealer Total: "+dealer.scoreHand(dealer.hand)+ "\n");
    }

    /**
     *  loops though the players and show their cards and totals
     */
    public void playerCards(){
        for(Player p : dealer.players){
            System.out.println("Player " + p.getID() + "'s hand");
            System.out.println(p.getHand());
            System.out.println("Player " + p.getID() + "'s Total: "
                    +dealer.scoreHand(p.getHand())+ "\n");
        }
    }
    
    /**
     * Show what cards the deck has left
     */
    public void showDeck(){
        System.out.println(dealer.mainDeck);
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        //Add switch here to see what game is wanted to run
        Scanner scan = new Scanner(System.in);
        boolean running = true;
        
        //basic menu system.
        while(running){
            System.out.println("What game would you like to play?: ");
            System.out.print("basic \\ human \\ advanced \\ quit :  ");

            String input = scan.nextLine();
            switch(input.toLowerCase()){
                case "basic":
                case "basic game":
                    basicGame();
                    break;
                case "human":
                case "human game":
                    humanGame();
                    break;
                case "advanced":
                case "advanced game":
                    advancedGame();
                    break;
                case "quit":
                case "exit":
                    System.out.println("Quitting");
                    System.exit(0);
                default:
                    System.out.println("Not a valid entry");
                    break;
            }
        }

    }
    
    /**
     * Has a mix of basic, Intermediate and Advance player
     */
    static public void advancedGame(){
        //initilize all classes and vars used for menu system
        boolean playing = true;
        Scanner scan = new Scanner(System.in);
        int amount = 0;
        
        BlackjackDealer dealer = new BlackjackDealer();
        
        List<Player> players = new ArrayList<>();
        players.add(new BasicPlayer());
        players.add(new IntermediatePlayer());
        players.add(new AdvancedPlayer());
        
        BlackjackTable table = new BlackjackTable(dealer, players);
        
        table.assignPlayer();
        
        //delete diffrence file so its a clean slate
        try
        {
            Files.deleteIfExists(Paths.get("diffrence.txt"));
        }
        catch(NoSuchFileException e)
        {
            System.out.println("No such file/directory exists");
        }
        catch(IOException e)
        {
            System.out.println("Invalid permissions.");
        }
        
        //basic menu to see how many rounds it wants.
        while(playing){
                        
            //makes sure it doesnt throw an error in game
            //if string or char is entered, it will re-ask
            //and flush the string
            boolean goodInput = false;
            while(!goodInput){
                System.out.print("How many times would you "
                        + "like to run? \nEnter 0 to"
                        + " go back to menu: ");

                try{
                    amount = scan.nextInt();
                    goodInput = true;
                }
                catch (InputMismatchException  ex )
                {
                    System.out.println("You entered a wrong input, "
                            + "please only use whole numbers." );
                    System.out.println("Please try again." );
                    String flush = scan.next();
                }
            }
            //calls the playTable loop, passing the amount of
            //runs is wanted, it also returns a boolean
            //this decides if the player needs to quit.
            playing = playTable(amount, table);

        }
        
        //after game has quit, it will print the profit diffrence file.
        System.out.println("Deck Profit Diffrence:");
        try (BufferedReader br = new BufferedReader
                                (new FileReader("diffrence.txt"))) {
            String line = null;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (FileNotFoundException ex) {
            System.out.println("File Not Found");
        } catch (IOException ex) {
            System.out.println("Bad Input");
        }
    }
    
    /**
     * Adds a human player against a single basic player. 
     */
    static public void humanGame(){
        BlackjackDealer dealer = new BlackjackDealer();
        
        List<Player> players = new ArrayList<>();
        players.add(new HumanPlayer());
        players.add(new BasicPlayer());
        
        BlackjackTable table = new BlackjackTable(dealer, players);
        
        table.assignPlayer();
        int count = 0;
        while(!table.tableEmpty()){
            count++;
            table.play();
            System.out.println("Round: "+ count);
        }
    }
    
    /**
     * Basic Game will run and ask if the user wants to continue and if so 
     * how many rounds to do. 
     * save and load the game.
     */
    
    static public void basicGame(){
        //sets up booleans to and scanners
        boolean running = true;
        boolean playing = true;
        Scanner scan = new Scanner(System.in);
        int amount = 0;
        BlackjackTable table = null;
        
        while(running){
            //asks what command is wanted.
            System.out.print("Would you like to load\\save or play game? ");
            String input = scan.nextLine();
            switch(input.toLowerCase()){
                case "load":
                    //sets playing as true, to stop a infinant loop of 
                    //not being able to play game.
                    playing = true;
                    System.out.println("Load Game");
                    table = load();
                    if(table == null){
                        break;
                    }
                    
                    // playing loop checks if the user hasnt wanted to quit
                    while(playing){
                        
                        //makes sure it doesnt throw an error in game
                        //if string or char is entered, it will re-ask
                        //and flush the string
                        boolean goodInput = false;
                        while(!goodInput){
                            System.out.print("How many times would you "
                                    + "like to run? \nEnter 0 to"
                                    + " go back to menu: ");
                            
                            try{
                                amount = scan.nextInt();
                                goodInput = true;
                            }
                            catch (InputMismatchException  ex )
                            {
                                System.out.println("You entered a wrong input, "
                                        + "please only use whole numbers." );
                                System.out.println("Please try again." );
                                String flush = scan.next();
                            }
                        }
                        //calls the playTable loop, passing the amount of
                        //runs is wanted, it also returns a boolean
                        //this decides if the player needs to quit.
                        playing = playTable(amount, table);
                        
                    }
                    break;
                case "play":
                    //sets playing as true, to stop a infinant loop of 
                    //not being able to play game.
                    playing = true;
                    System.out.println("Play Game");
                    //initilize all the main componments to make it play a game
                    BlackjackDealer dealer = new BlackjackDealer();
                    //Makes the player classes
                    List<Player> players = new ArrayList<>();
                    players.add(new BasicPlayer());
                    players.add(new BasicPlayer());
                    players.add(new BasicPlayer());
                    players.add(new BasicPlayer());
                    
                    table = new BlackjackTable(dealer, players);
                    
                    table.assignPlayer();
                    // playing loop checks if the user hasnt wanted to quit
                    while(playing){
                        //makes sure it doesnt throw an error in game
                        //if string or char is entered, it will re-ask
                        //and flush the string
                        boolean goodInput = false;
                        while(!goodInput){
                            System.out.print("How many times would you "
                                    + "like to run? \nEnter 0 to"
                                    + " go back to menu ");
                            
                            try{
                                amount = scan.nextInt();
                                goodInput = true;
                            }
                            catch (InputMismatchException  ex )
                            {
                                System.out.println("You entered a wrong input, "
                                        + "please only use whole numbers." );
                                System.out.println("Please try again." );
                                String flush = scan.next();
                            }
                        }
                        //calls the playTable loop, passing the amount of
                        //runs is wanted, it also returns a boolean
                        //this decides if the player needs to quit.
                        playing = playTable(amount, table);
                        
                    }
                    break;
                case "save":
                    System.out.println("Saving");
                    save(table);
                    break;
                case "quit":
                case "exit":
                    System.out.println("Quitting");
                    System.exit(0);
                    break;
                case "menu":
                    System.out.println("Going to menu");
                    running =  false;
                    break;
                case "help":
                case "?":
                    System.out.print("Options:\n"
                            + "Load: loads saved game\n"
                            + "Save: Saves current game\n"
                            + "Play: Makes new game\n"
                            + "Quit: Quits whole program\n"
                            + "Menu: Goes back to menu\n"
                            + "Help: Well, you know that one because you "
                            + "are currently here!\n");
                default:
                    System.out.println("Not a valid entry");
                    break;
            }
        }
    }
    static public boolean playTable(int runs, BlackjackTable table){
        if (runs == 0) {
            return false;
        }
        for (int i = 0; i < runs; i++) {
            if (table.tableEmpty()) {
                System.out.println("NO PLAYERS LEFT");
                System.out.println("to play new game, press play on main menu"
                        + "this will make a new game");
                return false;
            }else{
                table.play();
                System.out.println("round " + (i+1) + " of " + runs);
                
            }
        }  
        return true;
    }
    
    static public void save(BlackjackTable table){
        try {
            FileOutputStream fileOut =
                    new FileOutputStream("BlackjackGame.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(table);
            out.close();
            fileOut.close();
            System.out.printf("Serialized data is saved "
                                        + "in BlackjackGame.ser\n");
        } catch (IOException i) {
            i.printStackTrace();
        }
        System.out.println("");
    }
    
    static public BlackjackTable load (){
        System.out.println("");
        BlackjackTable table = null;
        boolean loaded = false;
        
        while(!loaded){
            try {
                FileInputStream fileIn = 
                        new FileInputStream("BlackjackGame.ser");
                ObjectInputStream in = new ObjectInputStream(fileIn);
                table = (BlackjackTable) in.readObject();
                in.close();
                fileIn.close();
                loaded = true;
            } catch (IOException i) {
                System.out.println("File not found");
                break;
            } catch (ClassNotFoundException c) {
                System.out.println("BlackjackGame class not found");
                break;
            }
            
        }
        return table;
    }
    
}
