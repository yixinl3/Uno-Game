package View;

import Model.GameStructure.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * The class for game start GUI interface
 */
public class GameStartGUI extends GUI{
    private JSpinner humanPlayerNumInput = new JSpinner(new SpinnerNumberModel(1,1,10,1));
    private JSpinner baslineAINumInput = new JSpinner(new SpinnerNumberModel(0,0,10,1));
    private JSpinner stategicAINumInput = new JSpinner(new SpinnerNumberModel(0,0,10,1));
    private JButton startBtn = new JButton("Start");

    /**
     * Display the start game scene with number of player input and start button
     */
    public GameStartGUI() {
        super();
        JPanel panel = new JPanel();//createDefaultJPanel(300, 100);
        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = getGridBagConstraints();
        JLabel humanPlayerNumLabel = new JLabel("Please enter the number of the Human players:");
        JLabel baselineAINumLabel = new JLabel("Please enter the number of the baseline AI players:");
        JLabel strategicAINumLabel = new JLabel("Please enter the number of the strategic AI players:");
        c.gridx = 0;
        c.gridy = 0;
        panel.add(humanPlayerNumLabel,c);
        c.gridy = 1;
        panel.add(this.humanPlayerNumInput,c);
        c.gridy = 2;
        panel.add(baselineAINumLabel,c);
        c.gridy = 3;
        panel.add(this.baslineAINumInput,c);
        c.gridy = 4;
        panel.add(strategicAINumLabel,c);
        c.gridy = 5;
        panel.add(this.stategicAINumInput,c);
        c.gridy = 6;
        panel.add(startBtn,c);
        this.add(panel);

    }

    /**
     * Add listener for game start button
     * @param listenerForStartGame the listener
     */
    public void addStartGameListener(ActionListener listenerForStartGame) {
        startBtn.addActionListener(listenerForStartGame);
    }

    /**
     * Get the human player num
     * @return the human player num
     */
    public int getHumanPlayerNum() {
        return (Integer) humanPlayerNumInput.getValue();
    }

    /**
     * Get the Baseline AI player num
     * @return the num of baseline AI
     */
    public int getBaselinePlayerNum() {
        return (Integer) baslineAINumInput.getValue();
    }

    /**
     * Get the Strategic AI player num
     * @return the num of strategic AI
     */
    public int getStrategicPlayerNum() {
        return (Integer) stategicAINumInput.getValue();
    }

    /**
     * Main function for testing static view
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        GameStartGUI gui = new GameStartGUI();
        gui.setVisible(true);
    }
}
