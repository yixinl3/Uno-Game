package Model.GameStructure;

/**
 * A <em>Model.GameStructure.Card</em> represents a single Model.GameStructure.Card in a Uno game.
 * Several types of Cards exist.
 */
public class Card {
    /**
     * Holds the color of the Model.GameStructure.Card.
     */
    private final CardColor cardColor;
    /**
     * Holds the value of the Model.GameStructure.Card.
     */
    private final Value value;

    /**
     * Construct a <em>Model.GameStructure.Card</em> with given #color and #value.
     */
    public Card (CardColor cardColor, Value value) {
        this.cardColor = cardColor;
        this.value = value;
    }
    /**
     * Return the color of the card.
     */
    public CardColor getColor() {
        return this.cardColor;
    }

    /**
     * Get the value of a card in string
     * @return the value of a card
     */
    public String getValueString() {
        return this.value.toString();
    }


    /**
     * Return the value of the card.
     */
    public  Value getValue() {
        return  this.value;
    }


    /**
     * Check is two cards are the same
     * @param card the card to be compared
     * @return true if they have same color and value, false otherwise
     */
    public boolean equals(Card card) {
        return cardColor == card.cardColor && value == card.value;
    }

    /**
     * Check is a card is a numeric card
     * @return true if the card is numeric
     */
    public boolean isNumeric() {
        if (this.cardColor != CardColor.WILD
                && this.value != Value.DRAW_TWO
                && this.value != Value.REVERSE
                && this.value != Value.SKIP) {
            return true;
        }
        return false;
    }




}
