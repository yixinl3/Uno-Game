package View;

import Model.GameStructure.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Class for game end GUI
 */
public class GameEndGUI extends GUI{
    private JButton startBtn = new JButton("Start New Game");

    /**
     * Constructor for the class
     * @param game
     */
    public GameEndGUI(Game game) {
        super();
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = getGridBagConstraints();
        JLabel msg = new JLabel("Game Ends");
        JPanel displayWinner = new JPanel();
        displayWinner.setLayout(new GridBagLayout());
        JLabel winnerLabel = new JLabel("The winner has ID: ");
        JLabel winnerVal = new JLabel(String.valueOf(game.getWinnerId()));
        displayWinner.add(winnerLabel);
        displayWinner.add(winnerVal);
        c.gridx = 0;
        c.gridy = 0;
        panel.add(msg,c);
        c.gridy = 1;
        panel.add(displayWinner,c);
        c.gridy = 2;
        panel.add(startBtn,c);
        this.add(panel);
    }


    /**
     * Add listener for back to Start button
     * @param ListenerForBackToStart the listener
     */
    public void addBackToStartListener(ActionListener ListenerForBackToStart) {

        startBtn.addActionListener(ListenerForBackToStart);
    }

    /**
     * Main function for testing static view
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        Game game = new Game(3,0,0);
        game.gameStart();
        GameEndGUI gui = new GameEndGUI(game);
        gui.setVisible(true);
    }
}
