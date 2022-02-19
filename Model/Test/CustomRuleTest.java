package Model.Test;

import Model.GameStructure.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * A class for testing custom rules: Addition and Subtraction
 */
public class CustomRuleTest {
    private Game game;
    private Card wildWild = new Card(CardColor.WILD,Value.WILD);
    private Card blue3 = new Card(CardColor.BLUE,Value.THREE);
    private Card blue6 = new Card(CardColor.BLUE,Value.SIX);
    private Card blue5 = new Card(CardColor.BLUE,Value.FIVE);
    private Card green6 = new Card(CardColor.GREEN,Value.SIX);
    private Card blueSkip = new Card(CardColor.BLUE,Value.SKIP);
    private ArrayList<Card> cards;
    /**
     * Set up some shared fields
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        game = new Game(3,0,0);
        game.gameStart();
        cards = new ArrayList<>();
        cards.add(blue3);
        cards.add(blue6);
    }
    /**
     * Test addition rules
     * @throws Exception
     */
    @Test
    public void testAddition() throws Exception {


        game.setValidColor(CardColor.RED);
        game.setValidValue(Value.NINE);

        assertTrue("BLUE THREE + BLUE SIX is valid",game.isValidMove(cards));

        cards.set(1,blue5);
        assertFalse("BLUE THREE + BLUE FIVE is invalid",game.isValidMove(cards));

        cards.set(1,green6);
        assertFalse("BLUE THREE + GREEN SIX is invalid",game.isValidMove(cards));

        cards.set(1,blueSkip);
        assertFalse("BLUE THREE + BLUE SKIP is invalid",game.isValidMove(cards));

        cards.set(1,wildWild);
        assertFalse("BLUE THREE + WILD WILD is invalid",game.isValidMove(cards));

    }

    /**
     * Test subtraction rules
     * @throws Exception
     */
    @Test
    public void testSubtraction() throws Exception {

        game.setValidColor(CardColor.RED);
        game.setValidValue(Value.THREE);

        assertTrue("abs:BLUE THREE - BLUE SIX is valid",game.isValidMove(cards));

        cards.set(1,blue5);
        assertFalse("abs:BLUE THREE - BLUE FIVE is invalid",game.isValidMove(cards));

        cards.set(1,green6);
        assertFalse("abs:BLUE THREE - GREEN SIX is invalid",game.isValidMove(cards));

        cards.set(1,blueSkip);
        assertFalse("abs:BLUE THREE - BLUE SKIP is invalid",game.isValidMove(cards));

        cards.set(1,wildWild);
        assertFalse("abs:BLUE THREE - WILD WILD is invalid",game.isValidMove(cards));

    }

}
