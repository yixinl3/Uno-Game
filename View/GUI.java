package View;

import Model.GameStructure.Card;
import Model.GameStructure.CardColor;
import Model.GameStructure.Game;
import Model.GameStructure.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import java.util.ArrayList;
/**
 * A <em>View.GUI</em> represents the graphical interface of a Uno game.
 *
 */
public class GUI extends JFrame{
    protected static final Dimension cardDimension = new Dimension(130,140);
    protected static final Dimension screenDimension = new Dimension(1200,800);
    protected static final Dimension gameStateDimension = new Dimension(1000,200);
    protected static final Dimension basicInfoDimension = new Dimension(600,300);
    protected static final Dimension validCardInfoDimension = new Dimension(600,300);
    protected static final Dimension playCardsBlockDimension = new Dimension(1200,400);


    /**
     * THe constructor of the class
     */
    public GUI() {
        this.setPreferredSize(screenDimension);
        this.setMaximumSize(screenDimension);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Uno Game");
        this.pack();
    }


    /**
     * Display a card
     * @param color given color
     * @param name given name of the card
     * @param fontcolor given color of the font
     * @return a JPanel contains the above info
     */
    public static JPanel getDisplayCard(Color color, String name, Color fontcolor) {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        panel.setPreferredSize(cardDimension);
        panel.setMaximumSize(cardDimension);
        panel.setBackground(color);
        JLabel label = new JLabel(name);
        label.setForeground(fontcolor);
        panel.add(label, BorderLayout.CENTER);
        panel.setLayout(new GridBagLayout());

        return panel;

    }


    /**
     * Get Color in AWT by given Model.GameStructure.CardColor
     * @param color a Model.GameStructure.CardColor
     * @return a color in awt
     */
    public static Color getColorAWT(CardColor color) {
        if (color == CardColor.WILD) {
            return Color.lightGray;
        } else if (color == CardColor.YELLOW) {
            return Color.YELLOW;
        }
        else if (color == CardColor.RED) {
            return Color.RED;
        }
        else if (color == CardColor.GREEN) {
            return Color.GREEN;
        }
        else {
            return Color.cyan;
        }
    }



    /**
     * Add a message(Label) to a Panel
     * @param panel the panel to be add
     * @param c1 the contraint
     */
    protected static void addMsgToPanel(JPanel panel, GridBagConstraints c1, String message) {
        JLabel msg = new JLabel(message);
        msg.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        c1.gridx = 0;
        c1.gridy = 0;
        panel.add(msg, c1);
    }

    /**
     * Create a gridBag constraints
     * @return the constraints
     */
    protected static GridBagConstraints getGridBagConstraints() {
        GridBagConstraints c1 = new GridBagConstraints();
        c1.fill = GridBagConstraints.NONE;
        return c1;
    }





    /**
     * create a default jPanel
     * @param BorderX The top and button of the border
     * @param BorderY the left and right of the order
     * @return a JPanel contains the above info
     */
    protected static JPanel createDefaultJPanel(int BorderX, int BorderY) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 0));
        panel.setBorder(BorderFactory.createEmptyBorder(BorderX, BorderY, BorderX, BorderY));
        return panel;
    }

    /**
     * Get the JPanel of display a block card
     * @return a JPanel contains the above info
     */
    protected static JPanel getCardDisplayJPanel() {
        JPanel playerCardsBlock = new JPanel();
        playerCardsBlock.setLayout(new GridBagLayout());
        playerCardsBlock.setPreferredSize(playCardsBlockDimension);
        playerCardsBlock.setMaximumSize(playCardsBlockDimension);
        return playerCardsBlock;
    }

    /**
     * Display the error message if some for invalid move
     * @param errorMsg the error message
     */
    public void displayErrorMsg(String errorMsg) {
        JOptionPane.showMessageDialog(this,errorMsg);
    }


}
