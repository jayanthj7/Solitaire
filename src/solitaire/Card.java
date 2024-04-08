
package solitaire;

public class Card {
    
	private boolean hide;
    private final String suite, number, colours;
    private final String suiteValue, Numbervalue;
    private static final String ColorChange = "",
                                CardBlack = "B",
                                CardRed = "R",
                                hideCard = "**";
    
    public Card(String suite, String number, boolean hide) {
        this.suite = suite;
        this.number = number;
        this.hide = hide;
        
        Numbervalue = createNumberValue();
        suiteValue = createSuiteValue();
        colours = createColour();
    }

    private String createNumberValue() {
        switch (number) {
            case "ace":
                return "A";
            case "2":
            case "3":
            case "4":
            case "5":
            case "6":
            case "7":
            case "8":
            case "9":
                return String.valueOf(number.charAt(0));
            case "10":
                return "10" ;
            case "jack":
                return "J";
            case "queen":
                return "Q";
            case "king":
                return "K";
            default:
                System.out.println("ERROR: Value not recognised");                  
                return "?";
        }
    }
    
    private String createSuiteValue() {
        switch (suite) {
        case "diamonds":
            return "D";
        case "hearts":
            return "H";
        case "clubs":
            return "C";
            case "spades":
                return "S";
            default:
                System.out.println("Invalid suite entered. Please enter correct command");                
                return "?";
        }
    }

   
    
    private String createColour() {
        switch (suite) {
        
            case "diamonds":
            case "hearts":
            return "red";
            case "clubs":
            case "spades":
                return "black";
            default:
                System.out.println("Please enter valid Suite");
                return "?";
        }
    }
    public String getNumber() {
        return number;
    }
    public String getSuite() {
        return suite;
    }
    
    
    
    public String getColour() {
        return colours;
    }
    
    public String getSign() {
        if(hide) {
            return hideCard;
        } else {
            if (colours.equals("black")) {
                return CardBlack + Numbervalue + suiteValue + ColorChange;
            } else {
                return CardRed + Numbervalue + suiteValue + ColorChange;
            }
        }
    }
    
    public void setHide(boolean condition) {
        hide = condition;
    }
    
    public boolean getHide() {
        return hide;
    }
}
