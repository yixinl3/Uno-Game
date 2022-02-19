package Model.Test;

import Model.GameStructure.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * A class for testing card and deck
 */
public class CardTest {
    private Deck deck;
    /**
     * Set up some shared fields
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        deck = new Deck();
        deck.initializeCardDeck();
    }
    /**
     * Test the initial of a Model.GameStructure.Deck
     */
    @Test
    public void testDeckInit() {

        // Test deck size
        assertEquals("deck size",108,deck.getNumCards());
        // Test first card
        assertEquals("last card color", CardColor.BLUE,deck.getCards().get(107).getColor());
        assertEquals("last card value", Value.ZERO,deck.getCards().get(107).getValue());
        // Test last card
        assertEquals("first card color", CardColor.WILD,deck.getCards().get(0).getColor());
        assertEquals("first card value",Value.WILD_DRAW_FOUR,deck.getCards().get(0).getValue());

    }


    /**
     * Test drawCards method
     * @throws Exception
     */
    @Test
    public void testDrawCards() throws Exception {

        ArrayList<Card> retCards = deck.drawCards(5);
        assertEquals("ret cards length",5,retCards.size());
        assertEquals("remain cards length",103,deck.getNumCards());
        for (int i = 0; i < 103; i++) {
            Card temp = deck.drawOneCard();
        }
        assertEquals("remain cards is empty after 103 draws",true,deck.isEmpty());
    }

}
