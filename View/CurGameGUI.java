package View;

import Model.GameStructure.Card;
import Model.GameStructure.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;

/**
 * A class for cur Game
 */
public class CurGameGUI extends GUI{

    private JButton revealHideBtn = new JButton("Reveal/Hide");
    private JButton drawBtn = new JButton("Draw");
    private JButton playBtn = new JButton("Play");
    private JButton skipBtn = new JButton("Skip");

    private JPanel playerCards;
    private JPanel selectCardBox;

    private boolean reveal;

    /**
     * The constructor of the curGame GUI
     * @param game
     */
    public CurGameGUI(Game game) {
        super();
        refreshGUI(game);
    }


    /**
     * Display the cur game state info including cur player, direction, next player, draw pile, valid card
     * @param game a Uno game
     * @return a JPanel contains the above info
     */
    public static JPanel getGameStateInfo(Game game) {
        JPanel gameStateInfo = new JPanel();
        gameStateInfo.setLayout(new GridLayout(0,3));
        gameStateInfo.setPreferredSize(gameStateDimension);
        gameStateInfo.setMaximumSize(gameStateDimension);
        JPanel basicStateInfo = getBasicStateInfo(game);
        JPanel drawPileInfo = getDrawPileInfo();
        JPanel validCardInfo = getValidCardInfo(game);
        gameStateInfo.add(basicStateInfo);
        gameStateInfo.add(drawPileInfo);
        gameStateInfo.add(validCardInfo);
        gameStateInfo.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        return gameStateInfo;

    }

    /**
     * Display the basic info of cur game state: cur player, next player, direction
     * @param game a Uno game
     * @return a JPanel contains the above info
     */
    public static JPanel getBasicStateInfo(Game game) {
        JPanel basicStateInfo = new JPanel();
        basicStateInfo.setLayout(new GridLayout(0,2));
        basicStateInfo.setPreferredSize(validCardInfoDimension);
        basicStateInfo.setMaximumSize(validCardInfoDimension);

        JLabel curPlayer = new JLabel("Current Player: ");
        JLabel curPlayerVal = new JLabel(String.valueOf(game.getCurPlayer().getId()));
        basicStateInfo.add(curPlayer);
        basicStateInfo.add(curPlayerVal);

        JLabel nextPlayer = new JLabel("Next Player: ");
        JLabel nextPlayerVal = new JLabel(String.valueOf(game.getNextPlayer()));
        basicStateInfo.add(nextPlayer);
        basicStateInfo.add(nextPlayerVal);

        JLabel stackCardNum = new JLabel("StackCardNumber: ");
        JLabel stackCardNumVal = new JLabel(String.valueOf(game.getNumCardStack()));
        basicStateInfo.add(stackCardNum);
        basicStateInfo.add(stackCardNumVal);

        JLabel direction = new JLabel("Direction: ");
        JLabel directionVal = new JLabel("Clockwise");
        if (!game.getDirection()) {
            directionVal = new JLabel("Counter Clockwise");
        }
        basicStateInfo.add(direction);
        basicStateInfo.add(directionVal);
        return basicStateInfo;
    }

    /**
     * Display a drawPile
     * @return  a JPanel contains the above info
     */
    public static JPanel getDrawPileInfo() {
        JPanel drawPileInfo = new JPanel();
        drawPileInfo.setLayout(new GridBagLayout());
        GridBagConstraints c = getGridBagConstraints();
        drawPileInfo.setPreferredSize(validCardInfoDimension);
        drawPileInfo.setMaximumSize(validCardInfoDimension);

        JLabel CardInfo = new JLabel("Draw Pile:");
        CardInfo.setBorder(BorderFactory.createEmptyBorder(10,0,10,0));
        JPanel drawPileCard = getDisplayCard(Color.BLACK,"Uno Cards",Color.white);
        return getCardInfo(drawPileInfo, c, CardInfo, drawPileCard);
    }

    /**
     * Display the info of the valid card
     * @param game a Uno Model.GameStructure.Game
     * @return a JPanel contains the above info
     */
    public static JPanel getValidCardInfo(Game game) {
        JPanel validCardInfo = new JPanel();
        validCardInfo.setLayout(new GridBagLayout());
        GridBagConstraints c = getGridBagConstraints();

        validCardInfo.setPreferredSize(basicInfoDimension);
        validCardInfo.setMaximumSize(basicInfoDimension);

        JLabel CardInfo = new JLabel("A card to be played should match: ");
        CardInfo.setBorder(BorderFactory.createEmptyBorder(10,0,10,0));
        JPanel validCard = getDisplayCard(getColorAWT(game.getValidColor()),game.getValidValue().toString(),Color.BLACK);
        return getCardInfo(validCardInfo, c, CardInfo, validCard);
    }



    /**
     * Display the cards on cur player's hand
     * @param game a Uno game
     * @return a JPanel contains the above info
     */
    public JPanel getCardsOnCurHand(Game game) {
        JPanel playerCardsBlock = new JPanel();
        playerCardsBlock.setLayout(new GridLayout(2,0));

        JLabel msg = new JLabel("Cards on current player's hand:");
        playerCardsBlock.add(msg);

        playerCards = new JPanel();
        playerCards.setLayout(new GridLayout(2,0));
        JPanel cardContainer = new JPanel();
        cardContainer.setLayout(new GridLayout());

        selectCardBox = new JPanel();
        selectCardBox.setLayout(new GridLayout());

        for (Card card:game.getCurPlayer().getCardsOnHand()) {
            JPanel pCard = getDisplayCard(getColorAWT(card.getColor()),card.getValueString(),Color.BLACK);
            if (!reveal) {
                pCard = getDisplayCard(Color.BLACK,"Uno Card",Color.WHITE);
            }

            JCheckBox box = new JCheckBox();

            cardContainer.add(pCard);
            selectCardBox.add(box);
            cardContainer.setAlignmentY(Component.CENTER_ALIGNMENT);

        }
        playerCards.add(cardContainer);
        playerCards.add(selectCardBox);
        playerCardsBlock.add(playerCards);

        return playerCardsBlock;
    }




    /**
     * Get the info of a Model.GameStructure.Card
     * @param cardPanel the panel contains the card
     * @param c the contrains of layout
     * @param cardInfo the label
     * @param card the card
     * @return a JPanel contains the above info
     */
    protected static JPanel getCardInfo(JPanel cardPanel, GridBagConstraints c, JLabel cardInfo, JPanel card) {
        c.gridx = 0;
        c.gridy = 0;
        cardPanel.add(cardInfo,c);
        c.gridy = 1;
        cardPanel.add(card,c);
        cardPanel. setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        return cardPanel;
    }


    /**
     * Display a list of buttons include: hide/reveal, draw, play, skip
     * @return a JPanel contains the above info
     */
    public  JPanel getButtonList() {
        JPanel buttonList = new JPanel();
        buttonList.setLayout(new GridBagLayout());
        GridBagConstraints c = getGridBagConstraints();



        buttonList.add(revealHideBtn,c);
        buttonList.add(drawBtn,c);
        buttonList.add(playBtn,c);
        buttonList.add(skipBtn,c);
        return buttonList;
    }

    /**
     * Refresh the GUI of the cur Game state
     * @param game the given game
     */
    public void  refreshGUI(Game game) {

        this.getContentPane().removeAll();

        JPanel panel = createDefaultJPanel(10, 10);

        JPanel gameStateInfo = getGameStateInfo(game);
        panel.add(gameStateInfo);

        JPanel playerCards = getCardsOnCurHand(game);
        panel.add(playerCards);

        JPanel buttonList = getButtonList();
        panel.add(buttonList);
        this.add(panel);
        this.revalidate();
        this.repaint();
    }

    /**
     * Add listener for reveal hide button
     * @param listenerForRevealHide the listener
     */
    public void addRevealHideListener(ActionListener listenerForRevealHide) {
        revealHideBtn.addActionListener(listenerForRevealHide);
    }

    /**
     * Change value reveal to its opposite side
     */
    public void changeRevealHide() {
        this.reveal = !this.reveal;
    }

    /**
     * Set reveal to false
     */
    public void hidePlayerCard() {
        this.reveal = false;
    }

    public void addSkipListener(ActionListener listenerForSkip) {
        skipBtn.addActionListener(listenerForSkip);
    }

    /**
     * Add listener for skip button
     * @param listenerForDraw the listener
     */
    public void addDrawListener(ActionListener listenerForDraw) {
        drawBtn.addActionListener(listenerForDraw);
    }

    /**
     * Add listener for select the card
     * @param listenerForSelect the listener
     */
    public void addSelectListener(ItemListener listenerForSelect) {
        for (Component container : selectCardBox.getComponents()) {
            if (container instanceof JCheckBox) {

                ((JCheckBox) container).addItemListener(listenerForSelect);

            }
        }
    }

    /**
     * Get the select Card box Jpanel
     * @return the JPanel
     */
    public JPanel getSelectCardBox() {
        return this.selectCardBox;
    }

    /**
     * Add listener for play button
     * @param listenerForPlay the listener
     */
    public void addPlayListener(ActionListener listenerForPlay) {
        playBtn.addActionListener(listenerForPlay);
    }

    /**
     * Main function for testing static view
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        Game game = new Game(3,0,0);
        game.gameStart();
        CurGameGUI gui = new CurGameGUI(game);
        gui.setVisible(true);
    }

}
