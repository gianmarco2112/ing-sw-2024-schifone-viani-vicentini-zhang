package ingsw.codex_naturalis.view.setupPhase;

import ingsw.codex_naturalis.enumerations.Color;
import ingsw.codex_naturalis.events.setupPhase.InitialCardEvent;
import ingsw.codex_naturalis.model.Game;
import ingsw.codex_naturalis.model.cards.initialResourceGold.PlayableCard;
import ingsw.codex_naturalis.model.util.GameEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class SetupGraphicUI extends SetupUI {

    private enum State {
        RUNNING,
        WAITING_FOR_UPDATE,
        PLAYING_INITIAL_CARD,
        STOPPING_THE_VIEW
    }

    private SetupGraphicUI.State state = SetupGraphicUI.State.RUNNING;

    private final Object lock = new Object();

    private static class SetupFrame extends JFrame{
        private final JPanel waitPanel = new JPanel();
        private final JPanel mainPanel = new JPanel(); //tabellone, carte e deck al centro

        public SetupFrame(ActionListener pressEnterListener){
            super("Game setup");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(600,600);

            waitPanel.setLayout(new BorderLayout());

            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new FlowLayout());
            buttonPanel.add(new JLabel("Press ENTER if you're ready to play"));
            JPanel jPanel = new JPanel(); //serve per andare a capo
            jPanel.setPreferredSize(new Dimension(10000,1));
            buttonPanel.add(jPanel);
            JButton enterButton = new JButton("ENTER");
            buttonPanel.add(enterButton);
            enterButton.addActionListener(pressEnterListener);

            waitPanel.add(buttonPanel,BorderLayout.CENTER);

            add(waitPanel);

        }
        private void showMainPanel(){
            waitPanel.setVisible(false);

            mainPanel.setLayout(new BorderLayout());
            mainPanel.setSize(600,600);

            ImageIcon loader = new ImageIcon("src/main/resources/ingsw/codex_naturalis/resources/board.png");
            mainPanel.add(new JLabel("Waiting for players...",loader,JLabel.CENTER));


        }
    }

    private final ActionListener pressEnterListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            setupFrame.showMainPanel();
        }
    };

    private final SetupFrame setupFrame = new SetupFrame(pressEnterListener);
    private SetupGraphicUI.State getState() {
        synchronized (lock) {
            return state;
        }
    }

    private void setState(SetupGraphicUI.State state) {
        synchronized (lock) {
            this.state = state;
            lock.notifyAll();
        }
    }



    @Override
    public void stop() {
        setState(SetupGraphicUI.State.STOPPING_THE_VIEW);
    }

    @Override
    public void updateInitialCard(Game.Immutable game, InitialCardEvent initialCardEvent) {

    }

    @Override
    public void updateColor(Color color) {

    }

    @Override
    public void reportError(String message) {

    }

    @Override
    public void update(Game.Immutable immGame, GameEvent gameEvent) {

    }

    @Override
    public void updateObjectiveCardChoice(Game.Immutable immGame) {

    }

    /*@Override
    public void updateSetup1(PlayableCard.Immutable initialCard, List<PlayableCard.Immutable> resourceCards, List<PlayableCard.Immutable> goldCards) {

        setState(SetupGraphicUI.State.RUNNING);
    }*/

    @Override
    public void run() {
        ready();
        waitForUpdate();
    }

    private void ready() {
        SwingUtilities.invokeLater(()->{
            setupFrame.setVisible(true);
        });
        notifyReady();
        setState(SetupGraphicUI.State.WAITING_FOR_UPDATE);
    }

    private void waitForUpdate() {
        while (getState() == SetupGraphicUI.State.WAITING_FOR_UPDATE) {
            synchronized (lock) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    System.err.println("Error while waiting for server update");
                }
            }
        }
    }
}
