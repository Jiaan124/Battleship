import java.io.IOException;
import java.util.*;
import java.io.PrintWriter;

/* Sarina Li, Vivien Cai, Jiaan Li
* Mon December 20
* ICS4U1
* Main Class
*/

public class Main {
    // attributes 
    private static boolean gamestate = true;
    protected static Scanner sc = new Scanner(System.in);
    protected static boolean AIFirst = false;
    public static int AIShot, AIHit, AIMiss, PlayerShot, PlayerMiss, PlayerHit=0;
    protected static Coordinate playerAttackBoard[][] = new Coordinate[11][11];
    protected static Coordinate AIPlacementBoard[][] = new Coordinate[11][11];
    protected static Coordinate AIAttackBoard[][] = new Coordinate[11][11];
   private static int fileCounter=0;
    //getting ship list of AI and player
    private static ArrayList<Ship> shipsAlive = Ship.getList();
    private static ArrayList<String> playerShipsAlive = Ship.getPlayerListOfShipsAlive();

    public static void main(String[] args) throws Exception {
        // instantiating Coordinate for boards
        initArrays();
        //intro message, coin flip, and instruction for input
        introPrompt();
        // prompt user to "press enter to continue"
        promptEnterKey();
        boolean saved=false;
        // while the player or AI still has ships alive
        while (gamestate) {
        	int counter=0;
            if (shipsAlive.size() == 0) {
                System.out.println("AI lost, player wins");
                break;
            }
            if (playerShipsAlive.size() == 0) {
                System.out.println("AI won, player lost");
                break;
            }

            // prints the ships still alive for AI and user
            printShipsAlive();

            if (AIFirst == true) {
                Game.AIMoves(shipsAlive, playerShipsAlive);
                Game.playerMoves(shipsAlive, playerShipsAlive);
                

                
            } else {
                Game.playerMoves(shipsAlive, playerShipsAlive);
                Game.AIMoves(shipsAlive, playerShipsAlive);
            }
            System.out.println("Number of AI shots/ Number of AI misses/ Number of player hits");
            System.out.println( AIShot +"/ "+ AIMiss+"/ "+AIHit);
            System.out.println("Number of player shots/ Number of player misses/ Number of player hits");
            System.out.println(PlayerShot +" "+ PlayerMiss+" "+PlayerHit);
            System.out.println("Round number "+counter+" is over. If you would like to stop playing and save, please enter SAVE");
            String temp=sc.nextLine();
            if (temp.equals("SAVE")) {      //sees if the user wants to save the game
            	saveGame();
            	saved=true;
            	break;
            }
        }
        
        if (saved==true) {
        	System.out.println("Thank you for playing our battleship game and we are sorry to see you go. Please come back soon");
        }
        
        
    }

    // Method that initilalzies our game boards so they do not have null values
    public static void initArrays() {
        // default constrctor for a ship denoting that there that coordinate is empty
        Ship emptyShip = new Ship();

        // instantiate every object in the 2d arrays
        for (int i = 0; i <= 10; i++) {
            for (int j = 0; j <= 10; j++) {
                // giving each key a ship value so when we reference E4 we can just check the hashmap for the key E4 and it will 
                // return the ship name
                char keyChar = Coordinate.columnIndex(i);
                String key = String.valueOf(keyChar) + String.valueOf(j);
                Game.AIMapOfCoor.put(key, emptyShip);
                // instantiating coordinate objects for each array
                playerAttackBoard[i][j] = new Coordinate(i, j);
                AIPlacementBoard[i][j] = new Coordinate(i, j);
                AIAttackBoard[i][j] = new Coordinate(i, j);
                Hunting.huntingProbability[i][j] = new Coordinate(i, j);
            }
        }
        //initializing arrays for coordinate that have been hit for each ship
        for (int i = 0; i < 7; i++) {
            Hunting.pointsHit[i] = new ArrayList<Coordinate>();
        }   
        
    }
    // copied from stack overflow LMAO
    public static void promptEnterKey() {
        System.out.println("Press \"ENTER\" to continue...");
        try {
            System.in.read(new byte[2]);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void introPrompt() throws Exception {
        System.out.println("Hello, welcome to Sarina, Vivien, and Jiaan's battleship game.");
        System.out.println("Would you like to start a new game or resume an old one? ");
        System.out.println("Please enter RESUME if you want to continue and anything else for a new game");
        System.out.println("______________________________________________________________");
        String input=sc.nextLine();

        if (input.equals("RESUME")) {
        	resumeGame();
        }
        else {
        	// Determining who goes first
            System.out.println("To determine who goes first, lets do a coin flip!");
            Game.coinFlip();
            Placing.place(AIPlacementBoard);

        }
       
        

        // generating random placement for AI PlacementBoard
        System.out.println("AI Placement Board: ");
        Game.printPlacementArray(AIPlacementBoard);

        System.out.println("When inputting things in the code, make sure to use all capitals. ");
        System.out.println("If the AI missed, type \"HIT\".");
        System.out.println("If one of your ships were hit, type \"HIT, [SHIPTYPE]\". EX. HIT, BATTLESHIP ");
        System.out.println("If one of your ships were sunk, type \"SUNK, [SHIPTYPE]\". EX. SUNK, BATTLESHIP ");
    }

    public static void resumeGame() throws Exception{
    	PrintWriter text = new PrintWriter("Battleship.txt");
    	

    }
    
    public static void saveGame() throws Exception{
    	System.out.println("Which save file would you like to save to? Please enter a number greater than 1");
    	int fileNumber=sc.nextInt();                                                              //ADD SMTG TO STOP CODE FROM CRASHING IF STRING
    	
    	
    	
    	PrintWriter text = new PrintWriter("info"+fileNumber+".txt");
    	
    	
    	
    	text.println(AIShot+" "+AIHit+" "+AIMiss);
    	text.println(PlayerShot+" "+PlayerHit+" "+PlayerMiss);    
    	
    	for (int i=0;i<shipsAlive.size();i++) {  
    		text.print(shipsAlive.get(i)+" ");
    	}
    	text.println();
    	for (int i=0;i<playerShipsAlive.size();i++) {
    		text.print(playerShipsAlive.get(i)+" ");
    	}
    	
    	
    	
    	
    	
    	text.close();
    	
    	FileHandling.saveBoards(fileNumber);
    	// Finish printing to battleship1
    	
    	
    	//ADD FILE PARINT PLACEMENT ARRAY METHOD KMSKMSKMSKMS
    	
    	
    	
    	
    }
    /*
     * Format for saving game
     * 1st line will have number of AI shot, hit and miss   EX. 10 1 9
     * 2nd line will have number of Player shot hit and miss
     * 3rd line will have types of ships alive for AI
     * 4th type of ship alive for player
     * 
     *            BELOW WILL HAVE A DIFFERENT TEXT FILE
     * 1st grid is AI home board
     * 2nd grid is Player Home board
     * 3rd grid is AI attack board
    
    
    
    
    things to save
    1. ship locations
    
    */
    
 public static void filePlacementArray(Coordinate array[][]) throws Exception{      //USER ATTACK BOARD
	 
 		PrintWriter text = new PrintWriter("Battleship.txt");

    	
        for (int i = 0; i <= 10; i++) {
            char ind = (char) ('A' + i - 1);
            if (i == 0) {
                for (int j = 0; j <= 10; j++) {
                    System.out.print(j + " ");
                }
            }
            if (i > 0) {
                System.out.print(ind + " ");
            }
            for (int j = 1; j <= 10; j++) {
                if (i == 0) {
                    continue;
                } else {
                    Coordinate cur = array[i][j];
                    boolean isShip = cur.getIsShip(), isHit = cur.getIsHit();
                    if (isHit && isShip) {
                        System.out.print("X ");
                    } else if (isHit && !isShip) {
                        System.out.print("M ");
                    } else if (!isHit && isShip) {
                        System.out.print("S ");
                    } else {
                        System.out.print("O ");
                    }
                }

            }
            System.out.println();
        }
    }
    
    
    public static void printShipsAlive() {
        System.out.println("________________________________");
        System.out.println("AI ships alive: ");
        for (Ship aliveShip : shipsAlive) {
            System.out.println(aliveShip);
        }
        System.out.println();
        System.out.println("Player ships alive: ");
        for (String aliveShip : playerShipsAlive) {
            System.out.println(aliveShip);
        }
    }
    
}
