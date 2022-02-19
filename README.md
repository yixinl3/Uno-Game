# Uno-Game
A Uno Game
build in Java.
The design follows the game rules described in [here](https://en.wikipedia.org/wiki/Uno_(card_game)).


## Requirement
The following should be installed in order to run the program.
- [Java](https://www.java.com/en/)

## Run the program
Using [IntelliJ IDEA](https://www.jetbrains.com/idea/download/#section=mac)
- Find ```src/Controller/GameController.java```, right-click the file and select "Run 'GUI.main()' "

## Model
### Game
#### Game Property
##### New Game
```
/* Create new game number of players*/
 public Game(int numOfHumanPlayers, int numOfBaselineAI, int numOfStrategicAI )
 /* Start the game*/
 game.gameStart()
```
##### DrawPile
```
Deck drawPile = game.getDrawPile();
```
##### DiscardPile
```
Deck discardPile = game.getDiscardPile();
```
##### Current Player
```
Player curPlayer = game.getCurPlayer();
```
##### Direction
```
Boolean direction = game.getDirection();
```
##### Winner ID
```
Int winerID = game.getWinnerId();
```
##### Players
```
ArrayList<Player> players = game.getPlayers();
```
##### Valid Color
```
CardColor validColor = game.getValidColor();
```
##### Valid Value
```
Value validValue = game.getValidValue();
```
### Player
#### Player Property
##### New Player
```
/* Create a human player with cards on player's hand and id 0*/
Player player = new Player(0,cards,0)
```
##### ID
```
Int id = player.getID();
```
##### Cards On Hand
```
ArrayList<Card> cardsOnHand = player.getCardsOnHand();
```
##### Type
```
/* Type 0 as Human. Type 1 as BaseLine AI. Type 2 as Strategic AI.*/
Int type = player.getType();
```
### Card
#### Card Property
##### New Card
```
/* Create new card with given cardColor and value.*/
Card card = new Card(cardColor, value);
```
##### CardColor
```
/* There are five CardColors in total, which are BLUE, GREEN, RED, YELLOW, WILD.*/
CardColor color = card.getCardColor();
```
##### Value
```
/* There are fifteen values in total, which are ZERO, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, SKIP, REVERSE, DRAW_TWO, WILD, WILD_DRAW_FOUR.*/
Value value = card.getValue();
```
### Deck
#### Deck Property
##### New Deck
```
Deck deck = new Deck();
```
##### Cards
```
ArrayList<Card> cards = deck.getCards();
```
##### Cards
```
int numCards = deck.getNumCards();
```
## View
### GUI
Super class of all other GUI classes. Stored some common property of JFrame.
### GameStartGUI
The interface of starting a new game.
### CurGameGUI
The interface of displaying the current game state information. Player can perform game actions in this view.
### ChooseColorGUI
The interface for the user to choose a color.
### GameEndGUI
The interface of showing the winner of the game.
## Controller
The controller to connect the Model and View to allow players interact with the GUI and play the game.


