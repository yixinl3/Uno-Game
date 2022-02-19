package Model.GameStructure;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * A <em>Model.GameStructure.Game</em> represents the game state in a Uno game.
 *
 */
public class Game {
    /**
     * Holds the valid color of each turn in the game.
     */
    private CardColor validCardColor;
    /**
     * Holds the valid value of each turn in the game.
     */
    private  Value validValue;
    /**
     * Holds the list of player in the game.
     */
    private ArrayList<Player> players;
    /**
     * Holds the direction of the game. Ture for clockwise, False for counter clockwise.
     */
    private boolean direction;
    /**
     * Holds the drawPile deck of Cards in the game.
     */
    private Deck drawPile;
    /**
     * Holds the discardPile deck of Cards in the game.
     */
    private Deck discardPile;
    /**
     * Holds the current Model.GameStructure.Player in the game.
     */
    private Player curPlayer;
    /**
     * Holds the state of game. True for game started, False for game ended
     */
    private Boolean gameStarted;
    /**
     * Holds the number of cards in the draw card stack (penalty stack)
     */
    private int numCardStack;
    /**
     * Hold the Id of the winner
     */
    private int winnerId = -1;
    /**
     * Hold the declaredColor
     */
    private CardColor declaredColor;
    /**
     * List of cardColors
     */
    private final List<CardColor> cardColors = Arrays.asList(CardColor.values());
    /**
     * List of values
     */
    private final List<Value> values =  Arrays.asList(Value.values());
    /**
     * Construct a <em>Model.GameStructure.Game</em> with given player number
     */
    public Game(int numOfHumanPlayers, int numOfBaselineAI, int numOfStrategicAI ) throws Exception {
        if (numOfHumanPlayers + numOfBaselineAI + numOfStrategicAI > 10) {
            throw new Exception("Invalid number of player");
        } else if (numOfHumanPlayers + numOfBaselineAI + numOfStrategicAI < 2) {
            throw new Exception("Invalid number of player");
        }
        // Initialize drawPile
        this.drawPile = new Deck();
        this.drawPile.initializeCardDeck();
        this.drawPile.shuffleDeck();
        // Initialize discardPile
        this.discardPile = new Deck();
        // Initialize Model.GameStructure.Player List, assign each player with 7 cards from drawPile

        this.players = new ArrayList<>();
        for (int i = 0; i < numOfHumanPlayers; i++) {
            ArrayList<Card> playersCards = this.drawPile.drawCards(7);
            this.players.add(new Player(i,playersCards,0));
        }
        for (int i = 0; i < numOfBaselineAI; i++) {
            ArrayList<Card> playersCards = this.drawPile.drawCards(7);
            this.players.add(new Player(10+i,playersCards,1));
        }
        for (int i = 0; i < numOfStrategicAI; i++) {
            ArrayList<Card> playersCards = this.drawPile.drawCards(7);
            this.players.add(new Player(20+i,playersCards,2));
        }
        //shuffle player list
        Collections.shuffle(this.players);
        this.curPlayer = this.players.get(0);

        // Initialize game direction to true:clockwise.
        this.direction = true;
        this.gameStarted = false;
    }
    /**
     * Draw one card from the draw pile
     * @return the top card of draw pile
     */
   public Card drawCardFromDraw() throws Exception {

       swapDiscardAndDraw();
       return this.drawPile.drawOneCard();
   }

    /**
     * If all cards are drawn (draw pile is empty),
     * while no player has played all his or her cards,
     * the top card in the discard pile is set aside
     * and the rest of the discard pile is shuffled to create a new deck
     * @throws Exception if exception occurs
     */
    private void swapDiscardAndDraw() throws Exception {
        if (this.drawPile.isEmpty()) {
            Card topDiscardCard = this.discardPile.drawOneCard();
            this.drawPile =this.discardPile;
            this.drawPile.shuffleDeck();
            this.discardPile = new Deck();
            this.discardPile.addOneCardToDeck(topDiscardCard);
        }
    }


    /**
     * Draw multiple cards from the draw pile
     * @param n the number of cards to draw
     * @return the top cards of draw pile
     */
    public ArrayList<Card> drawCardsFromDraw(int n) throws Exception {
        swapDiscardAndDraw();
        return this.drawPile.drawCards(n);
    }

    /**
     * Check if list of given cards is valid for play
     * @param cards the given cards
     * @return true if card is valid, false if not
     */
    public boolean isValidMove(ArrayList<Card> cards) {
        // If input two cards, handle addition and subtraction rules
        if (cards.size() == 2) {
            Card card1 = cards.get(0);
            Card card2 = cards.get(1);
            //Both of them should be numeric card
            if (card1.isNumeric() && card2.isNumeric() && card1.getColor() == card2.getColor()) {
                int val1 = values.indexOf(card1.getValue());
                int val2 = values.indexOf(card2.getValue());
                int validVal = values.indexOf(this.validValue);
                //Addition rules and subtraction rules
                if (validVal == val1 + val2 || validVal == Math.abs(val1 -val2)) {
                    return true;
                }

            }
            return false;

        } else {
            Card card = cards.get(0);
            if (this.validValue == Value.WILD_DRAW_FOUR && this.numCardStack != 0 && card.getValue() != Value.WILD_DRAW_FOUR) {
                return false;
            }
            if (this.validValue == Value.DRAW_TWO && this.numCardStack != 0 && card.getValue() != Value.DRAW_TWO && card.getValue() != Value.WILD_DRAW_FOUR) {
                return false;
            }
            if (card.getColor() == CardColor.WILD) {
                return true;
            }
            else if (card.getColor() == this.validCardColor) {
                return true;
            }
            else return card.getValue() == this.validValue;
        }


    }

    /**
     * Start a game by drawing one card from the draw pile and set valid color and valid value
     */
    public void gameStart() throws Exception {
        Card curCard = this.drawPile.getFirstNumericCard();
        CardColor curCardColor = curCard.getColor();
        this.validValue = curCard.getValue();
        this.validCardColor = curCardColor;
        this.gameStarted = true;
        this.discardPile.addOneCardToDeck(curCard);
        this.checkAIPlayerAndPlay();

    }
    /**
     * After playing a card, update the game state
     * @param cards the given card to be played
     * @param declaredCardColor  the possible declaredColor by the player, if using wild card
     */
    public void playerPlayCard(ArrayList<Card> cards, CardColor declaredCardColor) throws Exception{
        // If the move is valid
        if (this.isValidMove(cards)) {
            boolean hasSpecialRules = false;
            //Get the first card in cards as curCard
            Card card = cards.get(0);
            //If there are two cards in cards, remove the second card
            if (cards.size() == 2) {
                hasSpecialRules = true;
                Card temp = cards.get(1);
                this.curPlayer.removeGivenCardFromHand(temp);
            }
            //remove the cur card
            this.curPlayer.removeGivenCardFromHand(card);
            CardColor curCardColor = card.getColor();
            Value curValue = card.getValue();
            if (curCardColor == CardColor.WILD) {
                if (curValue == Value.WILD) {
                    this.validCardColor = declaredCardColor;
                } else if (curValue == Value.WILD_DRAW_FOUR) {
                    this.numCardStack += 4;
                    this.validCardColor = declaredCardColor;
                }
            } else {
                if (curValue == Value.REVERSE) {
                    this.direction = !this.direction;
                } else if (curValue == Value.SKIP) {
                    this.setNextPlayer(false);
                } else if (curValue == Value.DRAW_TWO) {
                    this.numCardStack += 2;
                }
                this.validCardColor = curCardColor;
            }
            // If wo do not have special rules, change valid value to curValue
            if (!hasSpecialRules) {
                this.validValue = curValue;
            }

            this.setNextPlayer(true);
            this.discardPile.addOneCardToDeck(card);

            this.checkGameEnd();

        } else {

            throw new Exception("invalid cards");
        }



    }

    /**
     * User can choose a specific color
     * @param idx the idx is the color. 0:Red,1:Blue,2:Green,3:Yellow
     * @return the declared card color
     */
    public CardColor getDeclaredColor(int idx) {
        if (idx == 0) {
            return CardColor.BLUE;
        } else if (idx == 1) {
            return  CardColor.GREEN;
        } else if (idx == 2) {
            return CardColor.RED;
        } else {
            return CardColor.YELLOW;
        }
    }
    /**
     * Model.GameStructure.Player select a specific card by its index, if index not accept means player has no card to play
     * Then check numCardStack, if is not 0, set penalty and move to next player
     * else draw one card from drawPile
     * @param idxes the list of index of the card the player wants to play
     * @return the Model.GameStructure.Card choose by the player
     */
    public ArrayList<Card> playerChooseCard(ArrayList<Integer> idxes) throws Exception {
        ArrayList<Card> ret = new ArrayList<>();
        if (idxes.size() == 2) {

            int idx1 = idxes.get(0);
            int idx2 = idxes.get(1);
            ret.add(this.curPlayer.getCardsOnHand().get(idx1));
            ret.add(this.curPlayer.getCardsOnHand().get(idx2));
            return ret;


        } else {
            int idx = idxes.get(0);
            ret.add(this.curPlayer.getCardsOnHand().get(idx));
            return ret;

        }

    }

    /**
     * This method would be called when player has no card to play out.
     * First check if numCardStack is 0. If not must accept penalty and move to next player.
     * Otherwise, draw one card from drawPile.
     * @throws Exception
     */
    public void noCardToPlay() throws Exception {
        if (numCardStack != 0) {
            ArrayList<Card> penaltyCards = this.drawCardsFromDraw(numCardStack);
            this.curPlayer.addCardsToHand(penaltyCards);
            this.numCardStack = 0;
            this.setNextPlayer(true);
            this.checkAIPlayerAndPlay();
        } else {
            Card retCard = drawCardFromDraw();
            this.curPlayer.addCardToHand(retCard);
        }


    }

    /**
     * Check if the cur player is AI, if true. Use the given type to play the game
     * @throws Exception
     */
    public void checkAIPlayerAndPlay() throws Exception {

        if (this.curPlayer.getType() != 0) {

            ArrayList<Card> CardsToPlay = new ArrayList<>();
            //If current player has valid cards and numCardStack not equal to 0
            if (this.curPlayer.hasValidCards(this)) {
                if (this.curPlayer.getType() == 1) {
                    CardsToPlay.add(this.curPlayer.getValidCards(this).get(0));
                } else {
                    CardsToPlay.add(this.curPlayer.getCardsWithColorMax(this.curPlayer.getValidCards(this)).get(0));
                }
                if (!CardsToPlay.isEmpty() ) {
                    if (CardsToPlay.get(0).getColor() == CardColor.WILD) {
                        this.declaredColor = this.curPlayer.getColorWithMaxNum();
                    }

                }

                playerPlayCard(CardsToPlay,this.declaredColor);

            }
            //Need to accept penalty
            else if (numCardStack != 0){
                this.noCardToPlay();
            }
            //Need to draw a card and force to skip
            else {
                this.noCardToPlay();
                this.setNextPlayer(true);
            }

        }

    }

    /**
     * Check whether the game has ended.
     */
    public void checkGameEnd() {
        for (Player player: players) {
            if (player.cardsIsEmpty()) {
                this.gameStarted = false;
                this.winnerId = player.getId();
                return;
            }
        }
    }




    /**
     * Set the next player to curPlayer with given direction
     */
    public void setNextPlayer(boolean ai) throws Exception {
        int curPlayeridx = players.indexOf(this.curPlayer);
        if (this.direction) {
            int nextPlayeridx = (curPlayeridx + 1) % players.size();
            this.curPlayer = players.get(nextPlayeridx);

        } else {
            int nextPlayeridx = curPlayeridx - 1;
            if (nextPlayeridx == -1) {
                nextPlayeridx = players.size()-1;
            }
            this.curPlayer = players.get(nextPlayeridx);
        }
        if (ai) {
            this.checkAIPlayerAndPlay();
        }

    }

    /**
     * Get the id of next player in a given game state
     * @return the id of next player
     */
    public int getNextPlayer() {
        int curPlayeridx = players.indexOf(this.curPlayer);
        if (this.direction) {
            int nextPlayeridx = (curPlayeridx + 1) % players.size();
            return players.get(nextPlayeridx).getId();

        } else {
            int nextPlayeridx = curPlayeridx - 1;
            if (nextPlayeridx == -1) {
                nextPlayeridx = players.size()-1;
            }
            return players.get(nextPlayeridx).getId();
        }
    }
    /**
     * Getter of drawPile
     * @return drawPile
     */
    public Deck getDrawPile() {
        return drawPile;
    }

    /**
     * Getter of discardPile
     * @return discardPile
     */
    public Deck getDiscardPile() {
        return discardPile;
    }

    /**
     * Getter of curPlayer
     * @return curPlayer
     */
    public Player getCurPlayer() {
        return curPlayer;
    }

    /**
     * Getter of players
     * @return players
     */
    public ArrayList<Player> getPlayers() {
        return players;
    }

    /**
     * Getter of direction
     * @return direction
     */
    public boolean getDirection() {
        return direction;
    }

    /**
     * Getter of gameStarted
     * @return gameStarted
     */
    public Boolean getGameStarted() {
        return gameStarted;
    }

    /**
     * Getter of Valid color
     * @return valid color
     */
    public CardColor getValidColor() {
        return validCardColor;
    }

    /**
     * Getter of valid value
     * @return valid value
     */
    public Value getValidValue() {
        return validValue;
    }

    /**
     * Getter of numCardStack
     * @return numCardStack
     */
    public int getNumCardStack() {
        return numCardStack;
    }

    /**
     * Set valid color
     * @param validCardColor the given valid color
     */
    public void setValidColor(CardColor validCardColor) {
        this.validCardColor = validCardColor;
    }

    /**
     * Set valid value
     * @param validValue the given valid value
     */
    public void setValidValue(Value validValue) {
        this.validValue = validValue;
    }

    /**
     * set numcardstack
     * @param numCardStack the given numCardStack
     */
    public void setNumCardStack(int numCardStack) {
        this.numCardStack = numCardStack;
    }

    /**
     * Set direction
     * @param direction the given direction
     */
    public void setDirection(boolean direction) {
        this.direction = direction;
    }



    /**
     * set discard pile
     * @param discardPile the given discard pile
     */
    public void setDiscardPile(Deck discardPile) {
        this.discardPile = discardPile;
    }

    /**
     * set draw pile
     * @param drawPile the given drawPile
     */
    public void setDrawPile(Deck drawPile) {
        this.drawPile = drawPile;
    }

    /**
     * Get the winner
     * @return the winner id
     */
    public int getWinnerId() {
        return winnerId;
    }

    /**
     * Getter of declared color
     * @return declared color
     */
    public CardColor getDeclaredColor() {
        return declaredColor;
    }

    /**
     * Setter of declared color
     * @param declaredColor the color to be set
     */
    public void setDeclaredColor(CardColor declaredColor) {
        this.declaredColor = declaredColor;
    }

    /**
     * Setter of curplayer
     * @param curPlayer a player
     */
    public void setCurPlayer(Player curPlayer) {
        this.curPlayer = curPlayer;
    }
}
