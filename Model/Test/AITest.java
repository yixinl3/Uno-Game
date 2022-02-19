package Model.Test;

import Model.GameStructure.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.Assert.*;

/**
 * A class for testing AI player
 */
public class AITest{
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
    private Card blue3 = new Card(CardColor.BLUE,Value.THREE);
    private Card blue6 = new Card(CardColor.BLUE,Value.SIX);
    private Card blue5 = new Card(CardColor.BLUE,Value.FIVE);
    private Card green6 = new Card(CardColor.GREEN,Value.SIX);
    private Card blueSkip = new Card(CardColor.BLUE,Value.SKIP);
    private Card red7 = new Card(CardColor.RED,Value.SEVEN);
    private Card green7 = new Card(CardColor.GREEN,Value.SEVEN);
    private Card blue2 = new Card(CardColor.BLUE,Value.TWO);
    private Card yellow3 = new Card(CardColor.YELLOW,Value.THREE);
    private ArrayList<Card> cards;
    private Player player;
    private ArrayList<Card> cardsOnHand;
    ArrayList<Value> validValues;

    /**
     * Set up some shared fields
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        game = new Game(3,0,0);
        game.setValidColor(CardColor.RED);
        game.setValidValue(Value.THREE);
        cardsOnHand = new ArrayList<>();
        cardsOnHand.add(red7);
        cardsOnHand.add(redSkip);
        cardsOnHand.add(red0);
        cardsOnHand.add(green7);
        cardsOnHand.add(greenReverse);
        cardsOnHand.add(yellow3);
        Collections.shuffle(cardsOnHand);
        validValues = new ArrayList<>();
        validValues.add(Value.SEVEN);
        validValues.add(Value.SKIP);
        validValues.add(Value.ZERO);
    }
    /**
     * Test create a game with AI player
     * @throws Exception
     */
    @Test
    public void testCreateAIGame() throws Exception {
        game = new Game(1,1,1);
        // drawPile length should be 108 - 3 * 7 = 87
        assertEquals("drawPile length",87,game.getDrawPile().getNumCards());
        // discardPile length should be empty
        assertTrue("discardPile length",game.getDiscardPile().isEmpty());
        // players should have 3 players
        assertEquals("players length",3,game.getPlayers().size());
        // each players should have 7 cards
        int humanCount = 0;
        int baseCount = 0;
        int strategicCount = 0;
        for (int i = 0; i < 3; i++) {
            Player player = game.getPlayers().get(i);
//            assertEquals("player id",i,game.getPlayers().indexOf(player));
            assertEquals("cards on hand length",7,player.getCardsNumber());
            if (player.getId() == 0) {
                humanCount++;
                assertEquals("human players type is 0",0,player.getType());
            }
            if (player.getId() == 10) {
                baseCount++;
                assertEquals("baseline AI players type is 1",1,player.getType());
            }
            if (player.getId() == 20) {
                strategicCount++;
                assertEquals("strategic AI players type is 2",2,player.getType());
            }
        }
        assertEquals("human players count",1,humanCount);
        assertEquals("baseline AI players count",1,baseCount);
        assertEquals("strategic AI players count",1,strategicCount);
        // curPlayer should have id 0
        assertEquals("curPlayer id",0,game.getPlayers().indexOf(game.getCurPlayer()));
        // direction should be true
        assertTrue("direction",game.getDirection());
        // gameStarted should be false
        assertFalse("gameStarted",game.getGameStarted());
    }

    /**
     * Test baseline has valid card to play
     * @throws Exception
     */
    @Test
    public void testBaseLineAI() throws Exception {

        //Create a player with type = 1: baseline AI
        player = new Player(11,cardsOnHand,1);
        ArrayList<Card> validCards = player.getValidCards(game);
        game.setCurPlayer(player);
        game.checkAIPlayerAndPlay();
        ArrayList<CardColor> validColors = new ArrayList<>();
        validColors.add(CardColor.RED);
        validColors.add(CardColor.YELLOW);
        validValues.add(Value.THREE);
        assertTrue("valid card color in valid color",validColors.contains(game.getValidColor()));
        assertTrue("valid card value in valid value",validValues.contains(game.getValidValue()));



    }

    /**
     * Test AI has no card to play, must draw
     * @throws Exception
     */
    @Test
    public void testAIDraw() throws Exception {
        game.setValidColor(CardColor.BLUE);
        game.setValidValue(Value.FIVE);
        //Create a player with type = 1: baseline AI
        player = new Player(11,cardsOnHand,1);
        game.setCurPlayer(player);
        game.checkAIPlayerAndPlay();
        assertEquals("player Should have 1 more card",7,player.getCardsNumber());
        assertEquals("curPlayer should move to 0",0,game.getPlayers().indexOf(game.getCurPlayer()));
   }

    /**
     * Test AI has no card to play, must accept penalty
     * @throws Exception
     */
    @Test
    public void testAIAcceptPenalty() throws Exception {
        game.setValidValue(Value.DRAW_TWO);
        game.setNumCardStack(2);
        //Create a player with type = 1: baseline AI
        player = new Player(11,cardsOnHand,1);
        game.setCurPlayer(player);
        game.checkAIPlayerAndPlay();
        assertEquals("player Should have 2 more card",8,player.getCardsNumber());
        assertEquals("curPlayer should move to 0",0,game.getPlayers().indexOf(game.getCurPlayer()));
    }

    /**
     * Test strategic AI has valid card to play, play the card with max color
     * @throws Exception
     */
    @Test
    public void testStrategicAI() throws Exception {
        //Create a player with type = 1: baseline AI
        player = new Player(21,cardsOnHand,2);
        ArrayList<Card> validCards = player.getValidCards(game);
        game.setCurPlayer(player);
        game.checkAIPlayerAndPlay();
        assertEquals("valid card color should be red",CardColor.RED,game.getValidColor());
        assertTrue("valid card value in valid value",validValues.contains(game.getValidValue()));
    }


}
