package Model.GameStructure;

import java.util.ArrayList;
import java.util.Collections;

/**
 * A <em>Model.GameStructure.Deck</em> represents the deck of cards in a Uno game.
 *
 */
public class Deck {
    /**
     * Holds an array of the card in Model.GameStructure.Deck.
     */
    private ArrayList<Card> cards;
    /**
     * Holds an the number of the card in Model.GameStructure.Deck.
     */
    private int numCards;
    /**
     * Construct a <em>Model.GameStructure.Deck</em> with 108 cards.
     */
    public Deck() {
        this.cards = new ArrayList<Card>();

    }
    /**
     * Initialize the card deck with 108 different cards.
     */
    public void initializeCardDeck() {
        this.numCards = 0;
        CardColor[] cardColors = CardColor.values();
        Value[] values = Value.values();
        //Create card for BLUE, GREEN, RED, YELLOW color
        for(int i = 0; i < cardColors.length-1; i++) {
            //Create one ZERO card for each color(except WILD)
            CardColor curCardColor = cardColors[i];
            Value curValue = values[0];
            Card curCard = new Card(curCardColor,curValue);
            addOneCardToDeck(curCard);
            //Create two cards for each color (except WILD) with value ONE to NINE and SKIP, REVERSE, DRAW_TWO (12 types of values in total)
            for(int j = 1; j < 13; j++) {
                curValue = values[j];
                curCard = new Card(curCardColor,curValue);
                addOneCardToDeck(curCard);
                addOneCardToDeck(curCard);
            }
        }
        //Create card for WILD color
        CardColor curCardColor = cardColors[4];
        for (int k = 13; k < 15; k++) {
            Value curValue = values[k];
            Card curCard = new Card(curCardColor,curValue);
            addOneCardToDeck(curCard);
            addOneCardToDeck(curCard);
            addOneCardToDeck(curCard);
            addOneCardToDeck(curCard);
        }
    }
    /**
     * Add one card to Model.GameStructure.Deck with given #color and #value.
     */
    public void addOneCardToDeck(Card card) {
        this.cards.add(0,card);
        this.numCards++;
    }
    /**
     * Remove one card from the top of the Model.GameStructure.Deck.
     * @return the removed card
     */
    public Card RemoveOneCardFromDeck() {
        this.numCards--;
        Card topCard = this.cards.remove(0);
        return topCard;
    }
    /**
     * Shuffle the cards in the Model.GameStructure.Deck
     */
    public void shuffleDeck() {
        Collections.shuffle(this.cards);
    }
    /**
     * Check if the Model.GameStructure.Deck is empty.
     * @return true if there are no cards in the Model.GameStructure.Deck.
     */
    public Boolean isEmpty() {
        if (this.numCards == 0) {
            return true;
        } else {
            return false;
        }
    }
    /**
     * Draw cards from the deck.
     * @param n number of cards to draw
     * @return a Model.GameStructure.Card array from the top of the Model.GameStructure.Deck.
     */
    public ArrayList<Card> drawCards(int n) throws Exception{
        // First check if the deck is empty
        if (isEmpty()) {
            // Throw error message
            throw new Exception("The deck is empty cannot draw cards");

        } else if (n <= 0){
            // Throw error message
            throw new Exception("The num of cards is invalid, cannot draw cards");
        } else if (n > this.numCards) {
            // Throw error message
            throw new Exception("The num of cards is invalid, cannot draw cards");
        }  else {
            ArrayList<Card> topCards = new ArrayList<Card>();
            for (int i = 0; i < n; i++) {
                Card topCard = RemoveOneCardFromDeck();
                topCards.add(topCard);
            }
            return topCards;
        }
    }
    /**
     * Draw one card from the deck.
     * @return a Model.GameStructure.Card from the top of the Model.GameStructure.Deck.
     */
    public Card drawOneCard()  throws Exception{
        // First check if the deck is empty
        if (isEmpty()) {
            throw new Exception("The deck is empty cannot draw card");
        } else {
            return RemoveOneCardFromDeck();
        }
    }
    /**
     * Getter of numCards
     */
    public int getNumCards() {
        return numCards;
    }
    /**
     * Getter of cards
     */
    public ArrayList<Card> getCards() {
        return cards;
    }

    /**
     * get topcard
     * @return the top card of the deck
     */
    public Card getTopCard() {

        Card ret = this.cards.get(0);
        return ret;
    }

    /**
     * Get first numericCard for startGame method
     * @return
     */
    public Card getFirstNumericCard() {
        for (int i = 0; i < this.cards.size(); i++) {
            Card curCard = this.cards.get(i);
            if (curCard.isNumeric()) {
                this.cards.remove(i);
                return curCard;
            }
        }
        return null;
    }
}










