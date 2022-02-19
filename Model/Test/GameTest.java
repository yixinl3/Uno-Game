package Model.Test;

import Model.GameStructure.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * A class for testing the game rules(game state)
 */
public class GameTest {
    private Game game;
    /**
     * Set up some shared fields
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        game = new Game(3,0,0);
    }

    /**
     * Test Model.GameStructure.Game constructor
     * @throws Exception
     */
    @Test
    public void testCreatGame() throws Exception {

        // drawPile length should be 108 - 3 * 7 = 87
        assertEquals("drawPile length",87,game.getDrawPile().getNumCards());
        // discardPile length should be empty
        assertTrue("discardPile length",game.getDiscardPile().isEmpty());
        // players should have 3 players
        assertEquals("players length",3,game.getPlayers().size());
        // each players should have 7 cards
        for (int i = 0; i < 3; i++) {
            Player player = game.getPlayers().get(i);
            assertEquals("player id",i,game.getPlayers().indexOf(player));
            assertEquals("cards on hand length",7,player.getCardsNumber());
        }
        // curPlayer should have id 0
        assertEquals("curPlayer id",0,game.getPlayers().indexOf(game.getCurPlayer()));
        // direction should be true
        assertTrue("direction",game.getDirection());
        // gameStarted should be false
        assertFalse("gameStarted",game.getGameStarted());

    }

    /**
     * Test gameStart method
     * @throws Exception
     */
    @Test
    public void testGameStart() throws Exception {
        game.gameStart();
        // gameStarted should be true
        assertTrue("gameStarted",game.getGameStarted());
        // valid color is not WILD
        assertFalse("validColor not WILD",game.getValidColor().equals(CardColor.WILD));
        // valid value is not DRAW_TWO
        assertFalse("validValue not DRAW_TWO",game.getValidValue().equals(Value.DRAW_TWO));
        // valid value is not WILD
        assertFalse("validValue not WILD",game.getValidValue().equals(Value.WILD));
        // valid value is not WILD_DRAW_FOUR
        assertFalse("validValue not WILD_DRAW_FOUR",game.getValidValue().equals(Value.WILD_DRAW_FOUR));
        // valid value is not REVERSE
        assertFalse("validValue not REVERSE",game.getValidValue().equals(Value.REVERSE));
        // valid value is not SKIP
        assertFalse("validValue not SKIP",game.getValidValue().equals(Value.SKIP));

    }

    /**
     * Test player choose card method
     * @throws Exception
     */
    @Test
    public void testPlayerChooseCard() throws Exception {
        game.gameStart();
        ArrayList<Integer> idx = new ArrayList<>();
        idx.add(3);
        // the card chosen should be match (valid index)
        assertEquals("choose expected card from player's hand",game.getCurPlayer().getCardsOnHand().get(3),game.playerChooseCard(idx).get(0));
        game.setNumCardStack(0);
        // test input two valid idx
        idx.set(0,1);
        idx.add(2);
        ArrayList<Card> retCard3 = game.playerChooseCard(idx);
        assertEquals("The first card ret is the card with idx 1 in player's hand",game.getCurPlayer().getCardsOnHand().get(1),retCard3.get(0));
        assertEquals("The second card ret is the card with idx 2 in player's hand",game.getCurPlayer().getCardsOnHand().get(2),retCard3.get(1));

    }

    /**
     * Test if player has not card to play
     * @throws Exception
     */
    @Test
    public void testNoCardToPlay() throws Exception {
        game.gameStart();
        // if penaltyCard stack is empty, and idx is invalid draw one card from the top of drawPile and add to player's hand
        Card topCard = game.getDrawPile().getTopCard();
        game.noCardToPlay();
        assertEquals("top from drawPile", topCard, game.getCurPlayer().getCardsOnHand().get(7));
        assertEquals("cards num of curPlayer", 8, game.getCurPlayer().getCardsNumber());
        game.setNumCardStack(2);
        game.noCardToPlay();
        // if penaltyCard stack is not empty, draw numCardStack from drawPile, and move to next player
        assertEquals("move to next player",1,game.getPlayers().indexOf(game.getCurPlayer()));
        assertEquals("this player with penalty has two more cards in his hand",10,game.getPlayers().get(0).getCardsNumber());
    }

    /**
     * Test setNextPlayer method
     * @throws Exception
     */
    @Test
    public void testSetNextPlayer() throws Exception {
        game.gameStart();
        game.setNextPlayer(true);
        // when game first start, the curPlayer should move to id 1 direction is true
        assertEquals("curplayer id 1, true", 1, game.getPlayers().indexOf(game.getCurPlayer()));
        // Next should move to id 2
        game.setNextPlayer(true);
        assertEquals("curplayer id 2, true", 2, game.getPlayers().indexOf(game.getCurPlayer()));
        // Next should back to id 0
        game.setNextPlayer(true);
        assertEquals("curplayer id 0, true", 0, game.getPlayers().indexOf(game.getCurPlayer()));
        // Change direction to false, should move to id 2
        game.setDirection(false);
        game.setNextPlayer(true);
        assertEquals("curplayer id 2, false", 2, game.getPlayers().indexOf(game.getCurPlayer()));
        // Next should back to id 1
        game.setNextPlayer(true);
        assertEquals("curplayer id 1, false", 1, game.getPlayers().indexOf(game.getCurPlayer()));
        // Next should back to id 0
        game.setNextPlayer(true);
        assertEquals("curplayer id 0, false", 0, game.getPlayers().indexOf(game.getCurPlayer()));
    }


    /**
     * Test draw card from empty drawPile
     * @throws Exception
     */
    @Test
    public void testDrawFromEmptyDraw() throws Exception {
        // swap drawPile and discardPile, draw should be empty, discard should have 87 cards
        Deck tempDeck = game.getDrawPile();
        game.setDrawPile(game.getDiscardPile());
        game.setDiscardPile(tempDeck);
        assertEquals("length of discard should be 87",87,game.getDiscardPile().getNumCards());
        assertEquals("length of draw should be 0",0,game.getDrawPile().getNumCards());
        // after drawing one card, draw should have 85 cards, and discard should have 1 card
        Card temp = game.drawCardFromDraw();
        assertEquals("length of discard should be 1",1,game.getDiscardPile().getNumCards());
        assertEquals("length of draw should be 85",85,game.getDrawPile().getNumCards());
    }

    /**
     * Test checkGameEnd method
     * @throws Exception
     */
    @Test
    public void testCheckGameEnd() throws Exception {
        game.gameStart();
        this.testGameStart();
        // Assign player 2 has empty hand
        Player player2 = game.getPlayers().get(1);
        ArrayList<Card> player2Cards = new ArrayList<Card>();
        player2.setCardsOnHand(player2Cards);
        game.checkGameEnd();
        assertFalse("gameStarted should be false",game.getGameStarted());
        assertEquals("winner id should be player with index 1",game.getPlayers().get(1).getId(),game.getWinnerId());
    }

    /**
     * Test the effect of playing two cards using addition and substraction
     * @throws Exception
     */
    @Test
    public void testPlayTwoCards() throws Exception {
        game.gameStart();
        this.testGameStart();
        game.setValidColor(CardColor.RED);
        game.setValidValue(Value.TWO);
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card(CardColor.BLUE,Value.FOUR));
        cards.add(new Card(CardColor.BLUE,Value.SIX));
        game.playerPlayCard(cards,CardColor.BLUE);
        assertEquals("valid value should be Two",Value.TWO,game.getValidValue());
        assertEquals("valid color should be Blue", CardColor.BLUE,game.getValidColor());
    }
}
