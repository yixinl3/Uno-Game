package Model.Test;

import Model.GameStructure.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class PlayerTest {
    private Game game;
    private Card red0 = new Card(CardColor.RED,Value.ZERO);
    private Card redSkip = new Card(CardColor.RED,Value.SKIP);
    private Card greenReverse= new Card(CardColor.GREEN,Value.REVERSE);
    private Card wildWild = new Card(CardColor.WILD,Value.WILD);
    private Card red7 = new Card(CardColor.RED,Value.SEVEN);
    private Card green7 = new Card(CardColor.GREEN,Value.SEVEN);
    private Card blue2 = new Card(CardColor.BLUE,Value.TWO);
    private Card yellow3 = new Card(CardColor.YELLOW,Value.THREE);
    private Player player;
    private ArrayList<Card> cardsOnHand;
    /**
     * Set up some shared fields
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        player = new Player(0,new ArrayList<Card>(),0);
        game = new Game(1,0,1);
        game.setValidColor(CardColor.RED);
        game.setValidValue(Value.THREE);
        cardsOnHand = new ArrayList<>();
        cardsOnHand.add(red7);
        cardsOnHand.add(redSkip);
        cardsOnHand.add(red0);
        cardsOnHand.add(green7);
        cardsOnHand.add(greenReverse);
        cardsOnHand.add(yellow3);
        cardsOnHand.add(blue2);
        cardsOnHand.add(wildWild);
    }

    /**
     * Test create a player
     */
    @Test
    public void testCreatePlayer() {

        assertEquals("players id 0",0,player.getId());
        assertTrue("player has empty hand",player.getCardsOnHand().isEmpty());
        assertEquals("player type is 0",0,player.getType());
    }

    /**
     * Test get valid cards
     * @throws Exception
     */
    @Test
    public void testGetValidCards() throws Exception {

        game.getCurPlayer().setCardsOnHand(cardsOnHand);
        ArrayList<Card> validCards = game.getCurPlayer().getValidCards(game);
        assertTrue("valid cards contain red7",validCards.contains(red7));
        assertTrue("valid cards contain red0",validCards.contains(red0));
        assertTrue("valid cards contain redSkip",validCards.contains(redSkip));
        assertTrue("valid cards contain yellow3",validCards.contains(yellow3));
        assertTrue("valid cards contain wildWild",validCards.contains(wildWild));
        assertEquals("valid cards has length 5",5,validCards.size());
    }

    /**
     * Test has valid cards
     * @throws Exception
     */
    @Test
    public void testHasValidCards() throws Exception {

        ArrayList<Card> cardsOnHand = new ArrayList<>();
        cardsOnHand.add(green7);
        cardsOnHand.add(greenReverse);
        cardsOnHand.add(blue2);
        game.getCurPlayer().setCardsOnHand(cardsOnHand);
        assertFalse("does not have valid card",game.getCurPlayer().hasValidCards(game));
    }

    /**
     * test get cards with color Max
     * @throws Exception
     */
    @Test
    public void testGetCardsWithColorMax() throws Exception {


        player.setCardsOnHand(cardsOnHand);
        ArrayList<Card> cardsColorMax = player.getCardsWithColorMax(player.getCardsOnHand());
        assertTrue("cardsColorMax cards contain red7",cardsColorMax.contains(red7));
        assertTrue("cardsColorMax cards contain redSkip",cardsColorMax.contains(redSkip));
        assertTrue("cardsColorMax cards contain red0",cardsColorMax.contains(red0));
        assertEquals("valid cards has length 3",3,cardsColorMax.size());


    }

    /**
     * test get color with max num
     * @throws Exception
     */
    @Test
    public void testGetColorWithMaxNum() throws Exception {

        player.setCardsOnHand(cardsOnHand);
        CardColor color = player.getColorWithMaxNum();
        assertEquals("color should be red",CardColor.RED,color);


    }

}
