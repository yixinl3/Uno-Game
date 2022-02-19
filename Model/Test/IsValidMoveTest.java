package Model.Test;

import Model.GameStructure.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * A class for testing isValid move method for different types of cards
 */
public class IsValidMoveTest {
    private Game game;
    private Card redDraw2 = new Card(CardColor.RED, Value.DRAW_TWO);
    private Card greenDraw2 = new Card(CardColor.GREEN,Value.DRAW_TWO);
    private Card red0 = new Card(CardColor.RED,Value.ZERO);
    private Card redSkip = new Card(CardColor.RED,Value.SKIP);
    private Card green0 = new Card(CardColor.GREEN,Value.ZERO);
    private Card greenReverse= new Card(CardColor.GREEN,Value.REVERSE);
    private Card green3 = new Card(CardColor.GREEN,Value.THREE);
    private Card wildDraw4 = new Card(CardColor.WILD, Value.WILD_DRAW_FOUR);
    private Card wildWild = new Card(CardColor.WILD,Value.WILD);
    private ArrayList<Card> cards;
    /**
     * Set up some shared fields
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        game = new Game(3,0,0);
        game.gameStart();
        game.setValidColor(CardColor.RED);
        cards = new ArrayList<>();
    }
    /**
     * Test isValidMove method if valid card is Normal(Numeric) card
     * @throws Exception
     */
    @Test
    public void testIsValidMoveNormal() throws Exception {

        game.setValidValue(Value.ZERO);


        cards.add(green0);
        assertTrue("GREEN Zero is valid",game.isValidMove(cards));

        cards.set(0,green3);
        assertFalse("GREEN THREE is invalid",game.isValidMove(cards));

        cards.set(0,redSkip);
        assertTrue("RED SKIP is valid",game.isValidMove(cards));

        cards.set(0,greenReverse);
        assertFalse("GREEN REVERSE is invalid",game.isValidMove(cards));

        cards.set(0,redDraw2);
        assertTrue("RED DRAW_TWO is valid",game.isValidMove(cards));

        cards.set(0,greenDraw2);
        assertFalse("GREEN DRAW_TWO is invalid",game.isValidMove(cards));

        cards.set(0,wildWild);
        assertTrue("WILD WILD is valid",game.isValidMove(cards));

        cards.set(0,wildDraw4);
        assertTrue("WILD WILD_DRAW_FOUR is valid",game.isValidMove(cards));


    }

    /**
     * Test isValidMove method if valid card is DrawTwo card
     * @throws Exception
     */
    @Test
    public void testIsValidMoveDrawTwo() throws Exception {

        game.setValidValue(Value.DRAW_TWO);

        game.setNumCardStack(2);
        cards.add(red0);
        assertFalse("RED ZERO is invalid",game.isValidMove(cards));

        cards.set(0,green3);
        assertFalse("GREEN THREE is invalid",game.isValidMove(cards));
        game.setNumCardStack(0);
        cards.set(0,redSkip);
        assertTrue("RED SKIP is valid",game.isValidMove(cards));

        cards.set(0,greenReverse);
        assertFalse("GREEN REVERSE is invalid",game.isValidMove(cards));
        game.setNumCardStack(2);
        cards.set(0,greenDraw2);
        assertTrue("GREEN DRAW_TWO is valid",game.isValidMove(cards));

        cards.set(0,wildWild);
        assertFalse("WILD WILD is invalid",game.isValidMove(cards));

        cards.set(0,wildDraw4);
        assertTrue("WILD WILD_DRAW_FOUR is valid",game.isValidMove(cards));


    }

    /**
     * Test isValidMove method if valid card is WILD card
     * @throws Exception
     */
    @Test
    public void testIsValidMoveWILD() throws Exception {

        game.setValidValue(Value.WILD);


        cards.add(red0);
        assertTrue("RED ZERO is valid",game.isValidMove(cards));

        cards.set(0,green3);
        assertFalse("GREEN THREE is invalid",game.isValidMove(cards));

        cards.set(0,redSkip);
        assertTrue("RED SKIP is valid",game.isValidMove(cards));

        cards.set(0,greenReverse);
        assertFalse("GREEN REVERSE is invalid",game.isValidMove(cards));

        cards.set(0,greenDraw2);
        assertFalse("GREEN DRAW_TWO is invalid",game.isValidMove(cards));

        cards.set(0,wildWild);
        assertTrue("WILD WILD is valid",game.isValidMove(cards));

        cards.set(0,wildDraw4);
        assertTrue("WILD WILD_DRAW_FOUR is valid",game.isValidMove(cards));


    }

    /**
     * Test isValidMove method if valid card is WildDrawFour card
     * @throws Exception
     */
    @Test
    public void testIsValidMoveWildDrawFour() throws Exception {

        game.setValidValue(Value.WILD_DRAW_FOUR);

        game.setNumCardStack(2);
        cards.add(red0);
        assertFalse("RED ZERO is invalid",game.isValidMove(cards));

        cards.set(0,green3);
        assertFalse("GREEN THREE is invalid",game.isValidMove(cards));
        game.setNumCardStack(0);
        cards.set(0,redSkip);
        assertTrue("RED SKIP is valid",game.isValidMove(cards));

        cards.set(0,greenReverse);
        assertFalse("GREEN REVERSE is invalid",game.isValidMove(cards));
        game.setNumCardStack(2);
        cards.set(0,redDraw2);
        assertFalse("RED DRAW_TWO is invalid",game.isValidMove(cards));

        cards.set(0,wildWild);
        assertFalse("WILD WILD is invalid",game.isValidMove(cards));

        cards.set(0,wildDraw4);
        assertTrue("WILD WILD_DRAW_FOUR is valid",game.isValidMove(cards));


    }

}
