**Patience Game in Java**

This project is an implementation of the Patience game in Java. The game allows users to play Patience directly from the console.

**Rules and Gameplay**

Read the rules of Patience at [Card Games/Patience.](https://simple.wikibooks.org/wiki/Card_Games/Patience)

**Features**

Displays the cards on the console and takes input from the keyboard (i.e. no GUI).

Displays the score and number of moves so far.

**Scoring:**

10 points per card moving from the uncovered pile to one of the suits.

20 points per card moving from one of the lanes to one of the suits.

5 points per card moving between lanes.

The uncovered pile is labelled “P”, the card “lanes” are numbered 1-7, and suit piles are labelled D (diamonds), H (hearts), C (clubs), and S (spades).

Prompt the user to enter a command.

The user can enter the following commands:
Q = quit
D = uncover a new card from the draw pile

<label1><label2> = move one card from <label1> to <label2>. E.g. “P2” or “2D”

<label1><label2><number> = move <number> cards from <label1> to <label2>. E.g. “413”.

Allow uppercase and lowercase commands.

Display an error message and ask for a new command if an invalid command is entered.

Display an error message and ask for a new command if a play cannot be made.

Display a game over message if all cards are in their correct suites.
