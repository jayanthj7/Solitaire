package solitaire;

import java.util.Arrays;
import java.util.Stack;

import static solitaire.Solitaire.*;

public class Features {

    public static boolean moveCardStack(int source, int num, int target) {
        Stack<Card> cardStack;
        //Read the card stack
        cardStack = readCardStack(source, num);
        String cardDesign=cardStack.peek().getSuite();


        if(target==9 && !cardDesign.equals("diamonds")) {
            System.out.println("wrong entry. Please enter correct command");
            return true;
        }else if(target==10 && !cardDesign.equals("hearts")) {
            System.out.println("wrong entry. Please enter correct command");
            return true;
        }else if(target ==11 && !cardDesign.equals("clubs")) {
            System.out.println("wrong entry. Please enter correct command");
            return true;
        }else if(target ==12 &&  !cardDesign.equals("spades") ) {
            System.out.println("wrong entry. Please enter correct command");
            return true;
        }

        //Was the card stack read successfully?
        if (cardStack != null) {
            //Attempt to write the card stack
            boolean writeFailure = writeCardStack(cardStack, target);
            if(!writeFailure && source==8 && flag==1){
                point=point+0;
            }
            else if(!writeFailure && source ==8) {
                point=point+(num*10);
                flagp=0;
            }
            else if(!writeFailure && flag==2){
                point=point+(num*20);
                flag=0;
            }
            else if(!writeFailure && flag==1){
                point=point+(num*5);
                flag=0;
            }

            //If writing failed write the card stack back to the source
            if (!writeFailure) {
                removeCardStack(source, num);
                displayCard(source);
                return false;
            }
        }
        return true;
    }

    private static Stack<Card> readCardStack(int loc, int no) {
        //Check that no is valid
        if (no < 1 || no > 13) {
            System.out.println("You can't move that many cards at a time. Please enter correct command");
            return null;
        }

        switch (loc) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
                return readCardStackFromLane(loc, no);
            case 8:
                if (no == 1) {
                    return readCardStackFromLane();
                } else {
                    System.out.println("You can only move one card at a time from the pile. Please enter corect command");
                    return null;
                }
            case 9:
            case 10:
            case 11:
            case 12:
                if (no == 1) {
                    return readCardStackFromSuitePile(loc);
                } else {
                    System.out.println("You can only move one card at a time from the suit pile. Please enter corect command");
                }
            default:
                System.out.println("Source lane not recognised. Please enter corect command");
                return null;
        }
    }

    private static Stack<Card> readCardStackFromLane(int loc, int n) {
        int index = loc - 1;
        if (n > tabs.get(index).size()) {
            System.out.println("There aren't enough cards in that column. Please enter corect command");
            return null;
        }
        Stack<Card> cardStack = new Stack<>();
        for (int i = 0; i < n; i++) {
            if (tabs.get(index).get(tabs.get(index).size() - 1 - i).getHide()) {
                System.out.println("There aren't enough visible cards in that column. Please enter corect command");
                return null;
            }
            cardStack.push(tabs.get(index).get(tabs.get(index).size() - 1 - i));
        }
        return cardStack;
    }

    private static Stack<Card> readCardStackFromLane() {
        if (pile.empty()) {
            System.out.println("The pile is empty. Please enter corect command");
            return null;
        }
        Stack<Card> cardStack = new Stack<>();
        cardStack.push(pile.peek());
        return cardStack;
    }

    private static Stack<Card> readCardStackFromSuitePile(int loc) {
        int index = loc - 9;
        if (appear.get(index).empty()) {
            System.out.println("The suite pile is empty. Please enter corect command");
            return null;
        }
        Stack<Card> cardStack = new Stack<>();
        cardStack.push(appear.get(index).peek());
        return cardStack;
    }

    private static boolean writeCardStack(Stack<Card> cardStack, int loc) {
        switch (String.valueOf(loc)) {
            case "7":
            case "6":
            case "5":
            case "4":
            case "3":
            case "2":
            case "1":
                return writeCardStackToLane(cardStack, (loc));
            case "8":
                System.out.println("You cannot place a card in the pile. Please enter corect command");
                return true;
            case "12":
            case "11":
            case "10":
            case "9":
                if (cardStack.size() == 1) {
                    return writeCardStackToSuitePile(cardStack, (loc));
                } else {
                    System.out.println("You can only move one card at a time to the suite pile. Please enter corect command");
                }
            default:
                System.out.println("Destination lane not recognised. Please enter corect command");
                return true;
        }
    }

    private static boolean writeCardStackToLane(Stack<Card> cardStack, int loc) {
        int index = loc - 1;
        if (isValidForLane(cardStack.peek(), index, false)) {
            while (!cardStack.empty()) {
                tabs.get(index).push(cardStack.pop());
            }
            moves=moves+1;
            flag=1;
            return false;
        }

        return true;
    }

    private static boolean writeCardStackToSuitePile(Stack<Card> cardStack, int loc) {
        int index = loc - 9;
        if (isValidForSuitePile(cardStack.peek(), index, false)) {
            while (!cardStack.empty()) {
                appear.get(index).push(cardStack.pop());
            }
            moves=moves+1;
            flag=2;
            return false;
        }
        // count point

        return true;
    }

    private static boolean isValidForLane(Card placee, int ind, boolean quite) {
        if (tabs.get(ind).empty()) {
            if (placee.getNumber().equals("king")) {
                return true;
            }
            if (!quite) {
                System.out.println("Only kings can be placed in empty lane. Please enter correct command ");
            }
            return false;
        }
        Card placed = tabs.get(ind).peek();
//        System.out.println("placed.getNumber() = " + placed.getNumber());
        int valIndex = Arrays.asList(numbers).indexOf(placed.getNumber()) - 1;
//        System.out.println("valIndex = " + valIndex);
        if (valIndex >= 0 && valIndex < 13) {
            if (!placed.getColour().equals(placee.getColour())) {
                if (numbers[valIndex].equals(placee.getNumber())) {
                    return true;
                }
                if (!quite) {
                    System.out.println("Cards can be arranged sequentially only. Please enter correct command");
                }
                return false;
            }
            if (!quite) {
                System.out.println("Only different will be arranged in lane. Please enter correct command ");
            }
            return false;
        }
        if (!quite) {
            System.out.println("That is not a correct move. Please enter correct command");
        }
        return false;
    }

    private static boolean isValidForSuitePile(Card placee, int ind, boolean quite) {
        if (appear.get(ind).empty()) {
            if (placee.getNumber().equals("ace")) {
                return true;
            }
            if (!quite) {
                System.out.println("Only Ace can be moved to empty suite pile. Please enter correct command");
            }
            return false;
        }
        Card placed = appear.get(ind).peek();
        int valIndex = Arrays.asList(numbers).indexOf(placed.getNumber()) + 1;
        if (valIndex >= 0 && valIndex < 13) {
            if (placed.getSuite().equals(placee.getSuite())) {
                if (numbers[valIndex].equals(placee.getNumber())) {
                    return true;
                }
                if (!quite) {
                    System.out.println("Cards can be arranged sequentially only. Please enter correct command");
                }
                return false;
            }
            if (!quite) {
                System.out.println("Only card of same suite can be arranged in suite pile");
            }
            return false;
        }
        if (!quite) {
            System.out.println("That is not a correct move. Please enter correct command");
        }
        return false;
    }

    private static void displayCard(int loc) {
        if (loc >= 1 && loc <= 7) {
            int index = loc - 1;
            if (!tabs.get(index).empty()) {
                tabs.get(index).peek().setHide(false);
            }
        }
    }

    private static void removeCardStack(int loc, int no) {
        switch (loc) {
            case 7:
            case 6:
            case 5:
            case 4:
            case 3:
            case 2:
            case 1:
                removeCardStackFromLane(loc, no);
                break;
            case 8:
                removeCardFromPile();
                break;
            case 12:
            case 11:
            case 10:
            case 9:
                removeCardFromSuitePile(loc);
                break;
            default:
                System.out.println("Location to remove card stack from not recognised. Please enter correct command");
        }
    }

    private static void removeCardStackFromLane(int loc, int n) {
        int index = loc - 1;
        for (int i = 0; i < n; i++) {
            tabs.get(index).pop();
        }
    }

    private static void removeCardFromPile() {
        pile.pop();
    }

    private static void removeCardFromSuitePile(int loc) {
        int index = loc - 9;
        appear.get(index).pop();
    }

    static void checkWinGame() {
        for (int i = 0; i < 4; i++) {
            if (appear.get(i).size() != 13) {
                return;
            }
        }
        winGame = true;
    }

    static void checkLostGame() {
        //TODO: Code game loss condition
    }

    public static void checkPossibleMoves() {
        possibleMoves.clear();
        //Check the card a the top of the pile
        if (!pile.empty()) {
            considerCard(pile.peek(), 7, 1);
        }
        //Check the cards in the lane
        for (Stack<Card> col : tabs) {
            for (Card card : col) {
                if (!card.getHide()) {
                    considerCard(card, tabs.indexOf(col), col.size() - col.indexOf(card));
                }
            }
        }
        //Check the cards at the tops of the suite pile
        for (Stack<Card> col : appear) {
            if (!col.empty()) {
                considerCard(col.peek(), appear.indexOf(col) + 8, 1);
            }
        }
    }

    private static void considerCard(Card card, int source, int n) {
        if (!(source > 7) && n == 1) {
            for (int i = 0; i < 4; i++) {
                if (isValidForSuitePile(card, i, true)) {
                    int[] move = {source + 1, n, i + 9};
                    possibleMoves.add(move);
                }
            }
        }
        for (int i = 0; i < 7; i++) {
            if (isValidForLane(card, i, true)) {
                int[] move = {source + 1, n, i + 1};
                possibleMoves.add(move);
            }
        }
    }
}
