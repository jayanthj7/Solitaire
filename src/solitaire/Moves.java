package solitaire;

import java.util.Stack;

import static solitaire.Solitaire.*;

public class Moves {

    private static void printDeck() {
        for (int i = 0; i < 52; i++) {
//            System.out.println("Printing symbol of card at index " + i);
            System.out.println(deck[i].getSign());
        }
        System.out.println();
    }

    static void fillDeck() {// generate 52 cards
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 13; j++) {
//                System.out.println("Instantiating card with: suit = " + suits[i] + " value = " + values[j]);
                deck[i * 13 + j] = new Card(suites[i], numbers[j], false);
            }
        }
    }

    static void shuffleDeck() {// shuffle 52 cards
        for (int i = 0; i < 52; i++) {
            int index = randomize.nextInt(52 - i);
            Card temp = deck[index];
            deck[index] = deck[51 - i];
            deck[51 - i] = temp;
        }
    }

    static void CreateLanes() {// print lane
        int deckCount = 0;
        //Lay out the lane
        for (int i = 0; i < 7; i++) {
            //Initialise the stack in each column
            tabs.push(new Stack<Card>());
            for (int j = 0; j < i + 1; j++) {
                tabs.get(i).push(deck[deckCount]);
                if (j != i) {
                    tabs.get(i).peek().setHide(true);
                }
                deckCount++;
            }
        }

        //Place the rest of the cards in the hand
        while (deckCount < 52) {
            hand.push(deck[deckCount]);
            hand.peek().setHide(true);
            deckCount++;
        }

        //Initliase the suite pile
        for (int i = 0; i < 4; i++) {
            appear.push(new Stack<Card>());
        }
    }

    static void displayLane() {
        //Print the hand and suite pile indexes
        System.out.println("    P    D  H  C  S ");

        //Print the hand
        if (hand.empty()) {
            System.out.print("[] ");
        } else {
            System.out.print(hand.peek().getSign() + " ");
        }

        //Print the pile
        if (pile.empty()) {
            System.out.print("[]    ");
        } else {
            System.out.print(pile.peek().getSign() + "    ");
        }

        //Print the suite pile
        for (int i = 0; i < 4; i++) {
            if (appear.get(i).empty()) {
                System.out.print("[] ");
            } else {
                System.out.print(appear.get(i).peek().getSign() + " ");
            }
        }
        System.out.println();

        //Print the column indexes
        System.out.println("01 02 03 04 05 06 07");

        //Display lane
        for (int i = 0; i < 19; i++) {
            if (isRowEmpty(i)) {
                break;
            }
            for (int j = 0; j < 7; j++) {
                if (i >= tabs.get(j).size()) {
                    System.out.print("   ");
                } else {
                    System.out.print(tabs.get(j).get(i).getSign() + " ");
                }
            }
            System.out.println();
        }
    }

    private static boolean isRowEmpty(int index) {
        for (int i = 0; i < 7; i++) {
            if (index < tabs.get(i).size()) {
                return false;
            }
        }
        return true;
    }

    static void turnHand() {
        if (hand.empty()) {
            resetHand();
        } else {
            pile.push(hand.pop());
            pile.peek().setHide(false);
        }
    }

    private static void resetHand() {
        while (!pile.empty()) {
            hand.push(pile.pop());
            hand.peek().setHide(true);
        }
        pile.clear();
    }

}
