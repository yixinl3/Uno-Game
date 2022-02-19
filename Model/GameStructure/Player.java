package Model.GameStructure;
import java.util.ArrayList;
import java.util.Collections;

/**
 * A <em>Model.GameStructure.Player</em> represents a player in a Uno game.
 *
 */
public class Player {
    /**
     * Holds the id of the Model.GameStructure.Player.
     */
    private final int id;
    /**
     * Holds the hand of the Model.GameStructure.Player.
     */
    private ArrayList<Card> cardsOnHand;
    /**
     * Holds the type of the player. 0 for human, 1 for Baseline AI, 2 for Strategic AI
     */
    private final int type;
    /**
     * Construct a <em>Model.GameStructure.Player</em> with given #id and cars.
     */
    public Player(int id, ArrayList<Card> cardsOnHand,int type){
        this.id = id;
        this.cardsOnHand = cardsOnHand;
        this.type = type;
    }
    /**
     * Return the cards on the player's hand.
     */
    public ArrayList<Card> getCardsOnHand() {
        return this.cardsOnHand;
    }
    /**
     * Return if the cards on the player's hand is empty.
     */
    public boolean cardsIsEmpty() {
        return this.cardsOnHand.isEmpty();
    }
    /**
     * Return number of the cards on the player's hand.
     */
    public int getCardsNumber() {
        return this.cardsOnHand.size();
    }
    /**
     * Add a given card to the cards on the player's hand.
     */
    public void addCardToHand(Card card) {
        this.cardsOnHand.add(card);
    }
    /**
     * Add an arraylist of cards to the cards on the player's hand.
     */
    public void addCardsToHand(ArrayList<Card> cards) {
        this.cardsOnHand.addAll(cards);
    }
    /**
     * Remove a given card from the cards on the player's hand.
     * @param card the given card to be removed
     * @return true if removal is successed, false otherwise
     */
    public boolean removeGivenCardFromHand(Card card) {
        for (Card curCard: cardsOnHand) {
            if (curCard.equals(card)) {
                cardsOnHand.remove(card);
                return true;
            }
        }
        return false;
    }

    /**
     * Getter of ID
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Set Cards on Hand
     * @param cardsOnHand the Model.GameStructure.Card List to set
     */
    public void setCardsOnHand(ArrayList<Card> cardsOnHand) {
        this.cardsOnHand = cardsOnHand;
    }

    /**
     * Getter of player's type
     * @return type of player
     */
    public int getType() {
        return type;
    }

    /**
     * Return the cards on player's hand that is valid to be played in a given turn of game
     * @param game the Uno game
     * @return a list of valid cards
     */
    public ArrayList<Card> getValidCards(Game game) {
        ArrayList<Card> validCardsRet = new ArrayList<>();
        for (Card card:this.cardsOnHand) {
            ArrayList<Card> cardToPlay = new ArrayList<>();
            cardToPlay.add(card);
            if (game.isValidMove(cardToPlay)) {
                validCardsRet.add(card);
            }
        }
        return validCardsRet;
    }

    /**
     * Check if player has valid card, if not call noCardToPlay
     * @param game the Uno Game
     * @return true if has valid cards, false if not
     */
    public Boolean hasValidCards(Game game) {
        if (getValidCards(game).isEmpty()) {
            return false;
        }
        return true;
    }

    /**
     * Return the cards on player's hand that with Color maximized
     * For example, player has 7  cards, 3 are Red, 2 are yellow, 1 is blue, and 1 is wild.
     * The method would return the 3 red cards.
     * If the color number is the same. randomly select one.
     * @param cards the cards as input
     * @return a list of  cards
     */
    public ArrayList<Card> getCardsWithColorMax(ArrayList<Card> cards) {
        ArrayList<Card> redCards = new ArrayList<>();
        ArrayList<Card> blueCards = new ArrayList<>();
        ArrayList<Card> yellowCards = new ArrayList<>();
        ArrayList<Card> greenCards = new ArrayList<>();
        ArrayList<Card> wildCards = new ArrayList<>();
        for(Card card:cards) {
            CardColor curColor = card.getColor();
            if (curColor == CardColor.RED) {
                redCards.add(card);
            } else if (curColor == CardColor.BLUE) {
                blueCards.add(card);
            } else if (curColor == CardColor.GREEN) {
                greenCards.add(card);
            } else if (curColor == CardColor.YELLOW) {
                yellowCards.add(card);
            } else {
                wildCards.add(card);
            }
        }
        ArrayList<Integer> size = new ArrayList<>();
        size.add(redCards.size());
        size.add(blueCards.size());
        size.add(greenCards.size());
        size.add(yellowCards.size());
        size.add(wildCards.size());
        int idx = size.indexOf(Collections.max(size));
        if (idx == 0) {
            return redCards;
        } else if (idx == 1){
            return  blueCards;
        } else if (idx == 2) {
            return greenCards;
        } else if (idx == 3) {
            return  yellowCards;
        } else {
            return wildCards;
        }

    }

    /**
     * Return the color with maximum number of cards in player's hand.
     * For example, player has 7 valid cards, 3 are Red, 2 are yellow, 1 is blue, and 1 is wild.
     * The method would return the RED.
     * If return color is WILD, return RED as default
     * @return CardColor
     */
    public CardColor getColorWithMaxNum() {
        ArrayList<Card> colorCards = getCardsWithColorMax(this.cardsOnHand);
        if (colorCards.get(0).getColor() == CardColor.WILD) {
            return CardColor.RED;
        } else {
            return colorCards.get(0).getColor();
        }

    }
}
