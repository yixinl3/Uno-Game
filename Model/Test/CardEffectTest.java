package Model.Test;

import Model.GameStructure.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * A class for testing the effect of each card
 */
public class CardEffectTest {
    private Game game;
    private Card redDraw2 = new Card(CardColor.RED, Value.DRAW_TWO);
    private Card redSkip = new Card(CardColor.RED,Value.SKIP);
    private Card wildDraw4 = new Card(CardColor.WILD, Value.WILD_DRAW_FOUR);
    private Card wildWild = new Card(CardColor.WILD,Value.WILD);
    private Card blue0 = new Card(CardColor.BLUE,Value.ZERO);
    private Card redReverse = new Card(CardColor.RED,Value.REVERSE);

    /**
     * Set up some shared fields
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        game = new Game(3,0,0);
        game.gameStart();
        game.setValidColor(CardColor.RED);
        game.setValidValue(Value.ZERO);
    }
    /**
     * Test Model.GameStructure.Player play a normal card
     * @throws Exception
     */
    @Test
    public void testPlayerPlayCardNormal() throws Exception {
        addAndPlayCard(blue0);
        assertEquals("Success, curPlayer should move to 1",1,game.getPlayers().indexOf(game.getCurPlayer()));
        assertEquals("Success, old player should have 7 cards",7,game.getPlayers().get(0).getCardsNumber());
        assertEquals("Success, valid color should be Blue", CardColor.BLUE,game.getValidColor());
        assertEquals("Success, valid Model.GameStructure.Value should be Zero",Value.ZERO,game.getValidValue());
        assertEquals("Success, dicardPile should have Model.GameStructure.Card with Blue Zero Color", CardColor.BLUE,game.getDiscardPile().getTopCard().getColor() );
        assertEquals("Success, dicardPile should have Model.GameStructure.Card with Blue Zero Model.GameStructure.Value",Value.ZERO,game.getDiscardPile().getTopCard().getValue() );
    }



    /**
     * Test Model.GameStructure.Player play a draw two card
     * @throws Exception
     */
    @Test
    public void testPlayerPlayCardDRAW_TWO() throws Exception {
        // Set curPlayer has RED DRAW_TWO and Play it
        addAndPlayCard(redDraw2);
        assertEquals("Success, curPlayer should move to 1",1,game.getPlayers().indexOf(game.getCurPlayer()));
        assertEquals("Success, old player should have 7 cards",7,game.getPlayers().get(0).getCardsNumber());
        assertEquals("Success, valid color should be RED", CardColor.RED,game.getValidColor());
        assertEquals("Success, valid Model.GameStructure.Value should be DRAW_TWO",Value.DRAW_TWO,game.getValidValue());
        assertEquals("penalty card stack number should be 2",2,game.getNumCardStack());
        assertEquals("Success, dicardPile should have Model.GameStructure.Card with RED DRAW_TWO Color", CardColor.RED,game.getDiscardPile().getTopCard().getColor() );
        assertEquals("Success, dicardPile should have Model.GameStructure.Card with RED DRAW_TWO Model.GameStructure.Value",Value.DRAW_TWO,game.getDiscardPile().getTopCard().getValue() );
    }

    /**
     * Test player play a skip card
     * @throws Exception
     */
    @Test
    public void testPlayerPlayCardSKIP() throws Exception {
        addAndPlayCard(redSkip);
        assertEquals("Success, old player shoud have 7 cards",7,game.getPlayers().get(0).getCardsNumber());
        assertEquals("Success, valid color should be RED", CardColor.RED,game.getValidColor());
        assertEquals("Success, valid Model.GameStructure.Value should be SKIP",Value.SKIP,game.getValidValue());
        assertEquals("Success, curPlayer should move to 2",2,game.getPlayers().indexOf(game.getCurPlayer()));
        assertEquals("penalty card stack number should be 0",0,game.getNumCardStack());
        assertEquals("Success, dicardPile should have Model.GameStructure.Card with RED SKIP Color", CardColor.RED,game.getDiscardPile().getTopCard().getColor() );
        assertEquals("Success, dicardPile should have Model.GameStructure.Card with RED SKIP Model.GameStructure.Value",Value.SKIP,game.getDiscardPile().getTopCard().getValue() );
    }

    /**
     * Test player play a reverse card
     * @throws Exception
     */
    @Test
    public void testPlayerPlayCardREVERSE() throws Exception {
        // Set curPlayer has RED REVERSE and Play it
        addAndPlayCard(redReverse);
        assertEquals("Success, old player shoud have 7 cards",7,game.getPlayers().get(0).getCardsNumber());
        assertEquals("Success, valid color should be RED", CardColor.RED,game.getValidColor());
        assertEquals("Success, valid Model.GameStructure.Value should be REVERSE",Value.REVERSE,game.getValidValue());
        assertEquals("Success, curPlayer should move to 2",2,game.getPlayers().indexOf(game.getCurPlayer()));
        assertEquals("penalty card stack number should be 0",0,game.getNumCardStack());
        assertEquals("Success, dicardPile should have Model.GameStructure.Card with RED REVERSE Color", CardColor.RED,game.getDiscardPile().getTopCard().getColor() );
        assertEquals("Success, dicardPile should have Model.GameStructure.Card with RED REVERSE Model.GameStructure.Value",Value.REVERSE,game.getDiscardPile().getTopCard().getValue() );
    }

    /**
     * Test player play a wild card
     * @throws Exception
     */
    @Test
    public void testPlayerPlayCardWILD() throws Exception {
        // Set curPlayer has WILD WILD and Play it
        addAndPlayCard(wildWild);
        assertEquals("Success, old player should have 7 cards",7,game.getPlayers().get(0).getCardsNumber());
        assertEquals("Success, valid color should be BLUE", CardColor.BLUE,game.getValidColor());
        assertEquals("Success, valid Model.GameStructure.Value should be WILD",Value.WILD,game.getValidValue());
        assertEquals("Success, curPlayer should move to 1",1,game.getPlayers().indexOf(game.getCurPlayer()));
        assertEquals("penalty card stack number should be 0",0,game.getNumCardStack());
        assertEquals("Success, dicardPile should have Model.GameStructure.Card with WILD WILD Color", CardColor.WILD,game.getDiscardPile().getTopCard().getColor() );
        assertEquals("Success, dicardPile should have Model.GameStructure.Card with WILD WILD Model.GameStructure.Value",Value.WILD,game.getDiscardPile().getTopCard().getValue() );
    }

    /**
     * Test player play a wild draw four card
     * @throws Exception
     */
    @Test
    public void testPlayerPlayCardWILD_DRAW_FOUR() throws Exception {
        // Set curPlayer has WILD WILD_DRAW_FOUR and Play it
        addAndPlayCard(wildDraw4);
        assertEquals("Success, old player should have 7 cards",7,game.getPlayers().get(0).getCardsNumber());
        assertEquals("Success, valid color should be BLUE", CardColor.BLUE,game.getValidColor());
        assertEquals("Success, valid Model.GameStructure.Value should be WILD_DRAW_FOUR",Value.WILD_DRAW_FOUR,game.getValidValue());
        assertEquals("Success, curPlayer should move to 1",1,game.getPlayers().indexOf(game.getCurPlayer()));
        assertEquals("penalty card stack number should be 4",4,game.getNumCardStack());
        assertEquals("Success, dicardPile should have Model.GameStructure.Card with WILD WILD_DRAW_FOUR Color", CardColor.WILD,game.getDiscardPile().getTopCard().getColor() );
        assertEquals("Success, dicardPile should have Model.GameStructure.Card with WILD WILD_DRAW_FOUR Model.GameStructure.Value",Value.WILD_DRAW_FOUR,game.getDiscardPile().getTopCard().getValue() );
    }

    /**
     * Add a card to player's hand and play it
     * @param card
     * @throws Exception
     */
    private void addAndPlayCard(Card card) throws Exception {
        game.getCurPlayer().addCardToHand(card);
        ArrayList<Card> cardsToPlay = new ArrayList<>();
        cardsToPlay.add(game.getCurPlayer().getCardsOnHand().get(7));
        game.playerPlayCard(cardsToPlay, CardColor.BLUE);

    }

}
