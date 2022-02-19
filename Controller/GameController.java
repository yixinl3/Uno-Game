package Controller;

import Model.GameStructure.Card;
import Model.GameStructure.CardColor;
import Model.GameStructure.Game;
import View.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;


/**
 * The Game controller class
 */
public class GameController {
    private Game game;
    private GameStartGUI gameStartGUI;
    private CurGameGUI curGameGUI;
    private ChooseColorGUI chooseColorGUI;
    private SelectListener selectListener;
    private GameEndGUI gameEndGUI;
    private ArrayList<Integer> selectedIdx = new ArrayList<>();
    private ArrayList<Integer> selectedColorIdx = new ArrayList<>();
    private ArrayList<Card> cardsToPlay;

    /**
     * Construct a game controller with game start GUI
     * @param gameStartGUI
     */
    public GameController( GameStartGUI gameStartGUI) {
        this.gameStartGUI = gameStartGUI;

        this.selectListener = new SelectListener();

        this.gameStartGUI.addStartGameListener(new StartGameListener());
    }

    /**
     * StartGameListener class
     * Click the start button should create and new game, and display the cur game state
     */
    class StartGameListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            try {

                int humanPlayerNum = gameStartGUI.getHumanPlayerNum();
                int baselinePlayerNum = gameStartGUI.getBaselinePlayerNum();
                int strategicPlayerNum = gameStartGUI.getStrategicPlayerNum();
                game = new Game(humanPlayerNum,baselinePlayerNum,strategicPlayerNum);
                game.gameStart();
                curGameGUI = new CurGameGUI(game);
                gameStartGUI.setVisible(false);
                curGameGUI.setVisible(true);
                curGameGUI.addRevealHideListener(new RevealHideListener());
                curGameGUI.addSkipListener(new SkipListener());
                curGameGUI.addDrawListener(new DrawListener());
                curGameGUI.addSelectListener(selectListener);
                curGameGUI.addPlayListener(new PlayListener());

            } catch (Exception exception) {
                exception.printStackTrace();
                gameStartGUI.displayErrorMsg("Invalid num of player. Total player number must be less than 10 and greater than 2.");
            }
        }
    }

    /**
     * RevealHideListener class
     * Click the reveal hide button to reveal or hide the cards on player's hand
     */
    class RevealHideListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                curGameGUI.changeRevealHide();

                curGameGUI.refreshGUI(game);
                curGameGUI.addSelectListener(selectListener);
                selectedIdx.clear();

            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    /**
     * SkipListener class
     * Click the skip button to move on to next player, use when player draw one card, and could not play this card
     */
    class SkipListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            try {

                game.setNextPlayer(true);
                curGameGUI.hidePlayerCard();
                curGameGUI.refreshGUI(game);
                selectedIdx.clear();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    /**
     * DrawListener class
     * Click the button could draw cards from draw pile, call no CardToPlay method
     * If numCardStack is not 0, draw number of penalty card and move to next player
     * else just draw one card and stay in the cur player
     */
    class DrawListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                game.noCardToPlay();
                refreshView();
                selectedIdx.clear();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    /**
     * SelectListener class
     * listener for select card on player hand to play, use check box if player want to play 2 cards
     * Store the card Idx in selectedIdx
     */
    class SelectListener implements ItemListener{

        @Override
        public void itemStateChanged(ItemEvent e) {
            try {
                JPanel selectCardBox = curGameGUI.getSelectCardBox();
                for(int i = 0; i < selectCardBox.getComponentCount();i++) {
                    Component c = selectCardBox.getComponent(i);
                    if (c instanceof JCheckBox & e.getSource() == c) {
                        selectedIdx.add(i);
                    }

                }


            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    /**
     * PlayListener class
     * Click the play button would call player play card
     * The cards to be play are from the result of selectedIdx
     * If play a single card and the card is a wild call, open  ChooseColorGUI view to choose color
     * else player play the card
     * If the move is invalid, an error message would show up
     * Finally, call check game end function to check if game ends
     * If true, open GameEndGUI view to show the winner
     *
     */
    class PlayListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                selectedIdx = removeEvenNumTimesElement(selectedIdx);
                System.out.println(selectedIdx);
                 cardsToPlay = game.playerChooseCard(selectedIdx);
                game.setDeclaredColor(cardsToPlay.get(0).getColor());
                if (cardsToPlay.size() == 2) {
                    game.playerPlayCard(cardsToPlay,game.getDeclaredColor());
                    refreshView();
                    selectedIdx.clear();
                } else {
                    Card cardToPlay = cardsToPlay.get(0);
                    if (cardToPlay.getColor() == CardColor.WILD) {
                        chooseColorGUI = new ChooseColorGUI();
                        chooseColorGUI.addSelectColorListener(new SelectColorListener());
                        chooseColorGUI.addConfirmListener(new ConfirmListener());
                        chooseColorGUI.setVisible(true);
                    } else {
                        game.playerPlayCard(cardsToPlay,game.getDeclaredColor());
                        refreshView();
                        selectedIdx.clear();
                    }


                }

                //Check Game End
                if (game.getWinnerId() != -1) {
                    curGameGUI.setVisible(false);
                    gameEndGUI = new GameEndGUI(game);
                    gameEndGUI.addBackToStartListener(new BackToStartListener());
                    gameEndGUI.setVisible(true);
                }

            } catch (Exception exception) {
                exception.printStackTrace();
                curGameGUI.displayErrorMsg("Invalid move. Please draw or choose another one!");
            }
        }
    }

    private void refreshView() {
        curGameGUI.hidePlayerCard();
        curGameGUI.refreshGUI(game);
        curGameGUI.addSelectListener(selectListener);
    }

    /**
     * SelectColorListener class
     * listener for select color for wild card
     * Store the card Idx in selectedColorIdx
     */
    class SelectColorListener implements ItemListener{

        @Override
        public void itemStateChanged(ItemEvent e) {
            try {
                JPanel selectCardBoxColor = chooseColorGUI.getSelectCardBoxColor();
                for(int i = 0; i < selectCardBoxColor.getComponentCount();i++) {
                    Component c = selectCardBoxColor.getComponent(i);
                    if (c instanceof JCheckBox & e.getSource() == c) {
                        selectedColorIdx.add(i);
                    }

                }


            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    /**
     * ConfirmListener class
     * Click confirm button to confirm the chosen color
     * After that, the player should play the wild card
     * And then check if game ends
     */
    class ConfirmListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                selectedColorIdx = removeEvenNumTimesElement(selectedColorIdx);
                CardColor declareColor = game.getDeclaredColor(selectedColorIdx.get(0));
                game.setDeclaredColor(declareColor);
                chooseColorGUI.setVisible(false);
                System.out.println(game.getCurPlayer().getId());
                game.playerPlayCard(cardsToPlay,game.getDeclaredColor());
                refreshView();
                selectedIdx.clear();
                selectedColorIdx.clear();

                //Check Game End
                if (game.getWinnerId() != -1) {
                    curGameGUI.setVisible(false);
                    gameEndGUI = new GameEndGUI(game);
                    gameEndGUI.addBackToStartListener(new BackToStartListener());
                    gameEndGUI.setVisible(true);
                }
            } catch (Exception exception) {
                System.out.println("here");
                exception.printStackTrace();
            }

        }
    }

    /**
     * BackToStartListener class
     * Click the start button to recreate a new game in GameEndGUI view
     */
    class BackToStartListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                System.out.println("back to start");
                GameStartGUI gui = new GameStartGUI();
                GameController controller = new GameController(gui);
                gui.setVisible(true);
                gameEndGUI.setVisible(false);


            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    /**
     * Use for removing the element in an arraylist that shows up even number of time
     * Use for checkbox selection
     * If the element shows up even number of time in an selected list, it mean that the player does not choose it at the end
     * @param arr the select idx array
     * @return a new array removing all the even num of time element in old arr
     */
    private ArrayList<Integer> removeEvenNumTimesElement(ArrayList<Integer> arr) {
        ArrayList<Integer> retTemp = new ArrayList<>();
        ArrayList<Integer> countList = new ArrayList<>();
        ArrayList<Integer> ret = new ArrayList<>();
        for (int i = 0; i < arr.size(); i++) {
            int cur = arr.get(i);
            if (retTemp.contains(cur)) {
                int idx = retTemp.indexOf(cur);
                int count = countList.get(idx) + 1;
                countList.set(idx,count);
            } else {
                retTemp.add(cur);
                countList.add(1);
            }
        }
        for (int i = 0; i < retTemp.size(); i++) {
            if (countList.get(i) % 2 != 0) {
                ret.add(retTemp.get(i));
            }
        }
        return ret;
    }

    /**
     * The main function to start the Uno Game
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        GameStartGUI gui = new GameStartGUI();
        GameController controller = new GameController(gui);
        gui.setVisible(true);
    }
}
