
package solitaire;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.util.Stack;

import static solitaire.Features.*;
import static solitaire.Moves.*;

// initialising variables for creating pile of cards and other variables required 
public class Solitaire {

    private static final Scanner sc = new Scanner(System.in); // to get input
    public static final String[] suites = {"diamonds", "hearts", "clubs", "spades"};
    public static final String[] numbers = {"ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "jack", "queen", "king"};
    public static final Random randomize = new Random();   // to shuffle pack
    public static Card[] deck; // Total 52 cards
    public static Stack<Card> hand;
    public static Stack<Card> pile; // Pile is hand while revealing card, pile is waste when
    public static Stack<Stack<Card>> tabs;
    public static Stack<Stack<Card>> appear; // Creating lane
    public static ArrayList<int[]> possibleMoves;// possible moves
    public static boolean winGame;
    private static boolean lostGame; //to check if game won or lost

    public static int moves = 0;// count moves
    static int point = 0;// count points

    public static int flag = 0; // to add the points for correct moves
    public static int flagp = 0; // to add the points for correct moves


    public static void main(String[] args) {
        boolean restart;
        do {
            //Initliase the card variables 
            deck = new Card[52];
            appear = new Stack<>();
            tabs = new Stack<>();
            hand = new Stack<>();
            pile = new Stack<>();


            possibleMoves = new ArrayList<>();
            winGame = false;
            lostGame = false;
            restart = false;

            //Fill the pack with cards
            fillDeck();
//            printPack();

            //Shuffle the pack
            shuffleDeck();
//            printPack();

            //Place the cards onto the lane
            CreateLanes();

            int possibleMoves1;

            //Run main game loop        
            while (true) {

                possibleMoves1 = 0;
                checkPossibleMoves();

                displayLane();

                //Query the player
                System.out.println("Note:- B=Black, R=Red");
                System.out.println("Enter any below command to play:");
                System.out.println("Enter 'draw' to draw card from pile");
                System.out.println("Enter 'move 1 2 3' *1 - lane to move from, *2 - number of cards to move, *3 - lane to move the cards to or Enter 'move *1 *2' *1 - lane to move from, *2 - lane to move the cards to if you only want to move one card");
                System.out.println("(Use P for pile and 'D' or 'H' or 'C' or 'S' for suite piles)");
                //System.out.println("Enter 'hint' to receive a hint");
                System.out.println("Enter 'Q' to Quit");
                //System.out.println("Enter 'restart' to start a new game");

                System.out.println("User Moves " + moves);
                System.out.println("User Points " + point);

                boolean check, exitGame;

                do {
                    exitGame = false;
                    check = false;
                    String[] input = sc.nextLine().split(" ");// split input by space
                    switch (input[0].toLowerCase()) {
                        case "draw": // to reveal card in pile
                            turnHand();
                            moves = moves + 1;
                            break;
                        case "move":
                            if (input.length == 3) {
                                int[] inputNum = new int[3];// empty array for input
                                for (int i = 0; i < 2; i++) {
                                    System.out.println(input[i + 1]);
                                    if (input[i + 1].equalsIgnoreCase("P")) {
                                        flagp = 1;
                                        inputNum[i] = 8;
                                    } else if (input[i + 1].equalsIgnoreCase("D")) {
                                        inputNum[i] = 9;
                                    } else if (input[i + 1].equalsIgnoreCase("H")) {
                                        inputNum[i] = 10;
                                    } else if (input[i + 1].equalsIgnoreCase("C")) {
                                        inputNum[i] = 11;
                                    } else if (input[i + 1].equalsIgnoreCase("S")) {
                                        inputNum[i] = 12;
                                    } else {
                                        inputNum[i] = Integer.parseInt(input[i + 1]);
                                    }
                                }
//                                System.out.println(Arrays.toString(inputNum));
                                check = moveCardStack(inputNum[0], 1, inputNum[1]);// to move card for 2 inputs
                            } else if (input.length != 4) {
                                System.out.println("You haven't entered the correct number of arguments. Please enter correct arguments");
                                check = true;
                            } else {
                                int[] inputNum = new int[3];
                                for (int i = 0; i < 3; i++) {
//                                    System.out.println(input[i + 1]);
                                    if (input[i + 1].equalsIgnoreCase("P")) {
                                        flagp = 1;
                                        inputNum[i] = 8;
                                    } else if (input[i + 1].equalsIgnoreCase("D")) {
                                        inputNum[i] = 9;
                                    } else if (input[i + 1].equalsIgnoreCase("H")) {
                                        inputNum[i] = 10;
                                    } else if (input[i + 1].equalsIgnoreCase("C")) {
                                        inputNum[i] = 11;
                                    } else if (input[i + 1].equalsIgnoreCase("S")) {
                                        inputNum[i] = 12;
                                    } else {
                                        inputNum[i] = Integer.parseInt(input[i + 1]);
                                    }
                                }
//                                System.out.println(Arrays.toString(inputNum));
                                check = moveCardStack(inputNum[0], inputNum[1], inputNum[2]);
                            }
                            break;
                        case "hint":
                            if (possibleMoves.isEmpty()) {
                                System.out.println("turn");
                            } else {
                                int[] move = possibleMoves.get(possibleMoves1);
                                System.out.println("move " + move[0] + " " + move[1] + " " + move[2]);
                                possibleMoves1++;
                                if (possibleMoves1 == possibleMoves.size()) {
                                    possibleMoves1 = 0;
                                }
                            }
                            check = true;
                        case "q":// to quit game
                            exitGame = true;
                            break;
                        case "restart":
                            restart = true;
                            exitGame = true;
                            break;
                        default:
                            System.out.println("Wrong input entered. Please enter correct input");
                            check = true;
                    }
                } while (check);

                checkWinGame();
                checkLostGame();

                if (exitGame) {
                    break;
                }
            }

            if (winGame) {
                System.out.println("You won the game");
            } else if (lostGame) {
                System.out.println("You ran out of moves.");
            } else if (restart) {
                System.out.println("Restarting game");
            } else {
                System.out.println("Exiting game");
            }
        } while (restart);
    }

}