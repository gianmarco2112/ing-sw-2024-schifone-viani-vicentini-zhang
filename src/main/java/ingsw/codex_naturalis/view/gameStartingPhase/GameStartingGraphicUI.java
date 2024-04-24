package ingsw.codex_naturalis.view.gameStartingPhase;

import javax.swing.*;
import java.awt.*;

public class GameStartingGraphicUI extends GameStartingUI {
    private enum State {
        RUNNING,
        WAITING_FOR_UPDATE,
        STOPPING_THE_VIEW
    }

    private static class GameStartingFrame extends JFrame{
        private final JPanel mainPanel = new JPanel();

        public GameStartingFrame(int gameID){
            super("Game starting");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(600,600);

            mainPanel.setLayout(new BorderLayout());

            JPanel coverPanel = cover();

            mainPanel.add(coverPanel,BorderLayout.NORTH);

            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new FlowLayout());
            buttonPanel.add(new JLabel("GameID:" + gameID));
            JPanel jPanel = new JPanel(); //serve per andare a capo
            jPanel.setPreferredSize(new Dimension(10000,1));
            buttonPanel.add(jPanel);

            ImageIcon loader = new ImageIcon("src/main/resources/ingsw/codex_naturalis/resources/loader.gif");
            buttonPanel.add(new JLabel("Waiting for players...",loader,JLabel.CENTER));

            mainPanel.add(buttonPanel,BorderLayout.CENTER);

            add(mainPanel);
        }
        private JPanel cover(){
            JPanel coverPanel = new JPanel();
            ImageIcon coverImage = new ImageIcon("src/main/resources/ingsw/codex_naturalis/resources/title.png");
            Image scaledImage = coverImage.getImage().getScaledInstance(500,500,Image.SCALE_SMOOTH);
            ImageIcon scaled = new ImageIcon(scaledImage);
            JLabel coverLabel = new JLabel(scaled);
            coverPanel.add(coverLabel);
            return coverPanel;
        }
    }

    private GameStartingGraphicUI.State state = GameStartingGraphicUI.State.WAITING_FOR_UPDATE;

    private final Object lock = new Object();

    private GameStartingFrame gameStartingFrame;

    private GameStartingGraphicUI.State getState() {
        synchronized (lock) {
            return state;
        }
    }

    private void setState(GameStartingGraphicUI.State state) {
        synchronized (lock) {
            this.state = state;
            lock.notifyAll();
        }
    }

    @Override
    public void updateGameID(int gameID) {
        gameStartingFrame = new GameStartingFrame(gameID);
        setState(GameStartingGraphicUI.State.RUNNING);
    }

    @Override
    public void stop() {
        setState(GameStartingGraphicUI.State.STOPPING_THE_VIEW);
    }

    @Override
    public void run() {
        SwingUtilities.invokeLater(()->{
            gameStartingFrame.setVisible(true);
        });
    }

}
