package View;
import Model.GameStructure.CardColor;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;

/**
 * The choose color GUI class
 */
public class ChooseColorGUI extends GUI{

    private JButton confirmBtn = new JButton("Confirm");
    private JPanel selectCardBoxColor;

    /**
     * The constructor of the class
     */
    public ChooseColorGUI() {
        super();
        JPanel panel = getCardDisplayJPanel();
        GridBagConstraints c1 = getGridBagConstraints();
        addMsgToPanel(panel, c1,"Please choose a Color");
        GridBagConstraints c2 = getGridBagConstraints();

        JPanel colors = new JPanel();
        colors.setLayout(new GridLayout(2,0));
        JPanel cardContainer = new JPanel();
        cardContainer.setLayout(new GridLayout());
        selectCardBoxColor = new JPanel();
        selectCardBoxColor.setLayout(new GridLayout());
        CardColor[] cardColors = CardColor.values();
        for (int i = 0; i < 4; i++) {
            JPanel curColor = getDisplayCard(getColorAWT(cardColors[i]),cardColors[i].toString(),Color.BLACK);
            JCheckBox box = new JCheckBox();
            cardContainer.add(curColor);
            selectCardBoxColor.add(box);
            cardContainer.setAlignmentY(Component.CENTER_ALIGNMENT);
        }
        colors.add(cardContainer);
        colors.add(selectCardBoxColor);

        c1.gridy = 1;
        panel.add(colors,c1);

        c1.gridy = 2;
        panel.add(confirmBtn,c1);

        this.add(panel);

    }


    /**
     * Add a select color listener to the check box
     * @param listenerForSelectColor the listener
     */
    public void addSelectColorListener(ItemListener listenerForSelectColor) {
        for (Component container : selectCardBoxColor.getComponents()) {
            if (container instanceof JCheckBox) {
                ((JCheckBox) container).addItemListener(listenerForSelectColor);
            }
        }
    }

    /**
     * Get the Jpanel of the select Card Box color
     * @return a JPanel
     */
    public  JPanel getSelectCardBoxColor() {
        return this.selectCardBoxColor;
    }

    /**
     * Add listener for confirm button
     * @param listenerForConfirm the listener
     */
    public void addConfirmListener(ActionListener listenerForConfirm) {
        confirmBtn.addActionListener(listenerForConfirm);
    }

    /**
     * Main function for testing static view
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        ChooseColorGUI gui = new ChooseColorGUI();
        gui.setVisible(true);
    }
}
