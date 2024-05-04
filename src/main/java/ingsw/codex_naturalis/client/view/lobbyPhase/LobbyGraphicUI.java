package ingsw.codex_naturalis.client.view.lobbyPhase;

import ingsw.codex_naturalis.server.model.GameSpecs;
import ingsw.codex_naturalis.common.exceptions.NoExistingGamesAvailable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class LobbyGraphicUI extends LobbyUI{
    private enum State {
        RUNNING,
        ASKING_WHICH_GAME_TO_ACCESS,
        WAITING_FOR_UPDATE,
        STOPPING_THE_VIEW
    }
    private int numOfPlayers;
    private String enteredNickname;
    private int gameID;
    private static class LobbyFrame extends JFrame{
        private final JButton createGameButton = new JButton("Create new game");
        private final JButton joinGameButton = new JButton("Join existing game");
        private final JComboBox<Integer> numOfPlayers = new JComboBox<Integer>(new Integer[]{2,3,4});
        private final JTextField nickname = new JTextField(20);
        private final JPanel mainPanel = new JPanel();
        private final JPanel createGamePanel = new JPanel();
        private final JPanel joinGamePanel = new JPanel();
        private final JPanel nicknamePanel = new JPanel();
        private List<Integer> gameID = new ArrayList<>();
        private JList<String> gameList;
        public LobbyFrame(ActionListener createGameListener, ActionListener joinGameListener){
            super("Game lobby");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(600,600);

            mainPanel.setLayout(new BorderLayout());

            JPanel coverPanel = cover();

            mainPanel.add(coverPanel,BorderLayout.NORTH);

            JPanel buttonPanel = new JPanel();
            //buttonPanel.setLayout(new GridLayout(1,2));
            buttonPanel.setLayout(new FlowLayout());

            buttonPanel.add(createGameButton);
            createGameButton.addActionListener(createGameListener);

            buttonPanel.add(joinGameButton);
            joinGameButton.addActionListener(joinGameListener);

            mainPanel.add(buttonPanel,BorderLayout.CENTER);

            add(mainPanel);

        }
        private void showCreatePanel(ActionListener selectNumPlayersListener){
            mainPanel.setVisible(false);

            createGamePanel.setLayout(new BorderLayout());
            createGamePanel.setSize(600,600);

            JPanel coverPanel = cover();

            createGamePanel.add(coverPanel,BorderLayout.NORTH);

            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new FlowLayout());
            buttonPanel.add(new JLabel("Select number of players: "));
            buttonPanel.add(numOfPlayers);
            JButton confirmButton = new JButton("Confirm");
            buttonPanel.add(confirmButton);

            confirmButton.addActionListener(selectNumPlayersListener);

            numOfPlayers.setSelectedIndex(0);
            createGamePanel.add(buttonPanel,BorderLayout.CENTER);

            getContentPane().add(createGamePanel);
            createGamePanel.setVisible(true);
            buttonPanel.setVisible(true);
        }
        private void showJoinPanel(ActionListener selectGameListener,Map<Integer, GameSpecs> gameSpecsMap){
            mainPanel.setVisible(false);

            joinGamePanel.setLayout(new BorderLayout());

            String[] gameSpec = new String[gameSpecsMap.entrySet().size()];
            int i = 0;
            for (Map.Entry<Integer, GameSpecs> entry : gameSpecsMap.entrySet()) {
                gameSpec[i] = "Game ID: " + entry.getValue().ID()
                        + "     Current number of players connected: " + entry.getValue().currentNumOfPlayers()
                        + "    Max number of players: " + entry.getValue().maxNumOfPlayers();
                i++;

                gameID.add(entry.getValue().ID());
            }
            gameList = new JList<>(gameSpec);

            //TODO: consentire solo una selezione dalla lista

            //gameList.addListSelectionListener(selectGameListener);
            JButton confirmButton = new JButton("Confirm");
            joinGamePanel.add(confirmButton,BorderLayout.SOUTH);

            //TODO: disabilitare conferma fintanto non si è scelto una partita
            confirmButton.addActionListener(selectGameListener);

            JScrollPane scrollPane = new JScrollPane(gameList);
            joinGamePanel.add(scrollPane,BorderLayout.CENTER);

            joinGamePanel.add(new JLabel("Which game do you want to access?"),BorderLayout.NORTH);

            getContentPane().add(joinGamePanel);
            joinGamePanel.setVisible(true);
            scrollPane.setVisible(true);
            confirmButton.setVisible(true);
        }
        private void showNicknamePanelForList(ActionListener digitNicknameListener){
            createGamePanel.setVisible(false);
            joinGamePanel.setVisible(false);

            nicknamePanel.setLayout(new BorderLayout());
            nicknamePanel.setSize(600,600);

            JPanel coverPanel = cover();

            nicknamePanel.add(coverPanel,BorderLayout.NORTH);

            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new FlowLayout());
            buttonPanel.add(new JLabel("Choose your nickname: "));
            buttonPanel.add(nickname);

            JButton confirmButton = new JButton("Confirm");
            buttonPanel.add(confirmButton);
            confirmButton.addActionListener(digitNicknameListener);

            //numOfPlayers.setSelectedIndex(0); ????
            nicknamePanel.add(buttonPanel,BorderLayout.CENTER);

            getContentPane().add(nicknamePanel);
            nicknamePanel.setVisible(true);
            buttonPanel.setVisible(true);
        }
        private void showNicknamePanel(ActionListener digitNicknameListener){
            createGamePanel.setVisible(false);
            joinGamePanel.setVisible(false);

            nicknamePanel.setLayout(new BorderLayout());
            nicknamePanel.setSize(600,600);

            JPanel coverPanel = cover();

            nicknamePanel.add(coverPanel,BorderLayout.NORTH);

            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new FlowLayout());
            buttonPanel.add(new JLabel("Choose your nickname: "));
            buttonPanel.add(nickname);

            JButton confirmButton = new JButton("Confirm");
            buttonPanel.add(confirmButton);
            confirmButton.addActionListener(digitNicknameListener);

            //numOfPlayers.setSelectedIndex(0); ????
            nicknamePanel.add(buttonPanel,BorderLayout.CENTER);

            getContentPane().add(nicknamePanel);
            nicknamePanel.setVisible(true);
            buttonPanel.setVisible(true);
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
        private String getNickname(){
            return nickname.getText();
        }
    }
    private LobbyGraphicUI.State state = LobbyGraphicUI.State.RUNNING;

    private final Object lock = new Object();
    private Map<Integer, GameSpecs> gameSpecsMap = new LinkedHashMap<>();
    private final ActionListener createGameListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            lobbyFrame.showCreatePanel(selectNumPlayersListener);
        }
    };
    private final ActionListener joinGameListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            lobbyFrame.showJoinPanel(selectGameListener,gameSpecsMap);
        }
    };
    private final ActionListener selectNumPlayersListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            lobbyFrame.showNicknamePanel(digitNicknameListenerFromNewGame);
            if(lobbyFrame.numOfPlayers.getSelectedItem()!=null){
                Integer i = (Integer) lobbyFrame.numOfPlayers.getSelectedItem();
                numOfPlayers = i.intValue();
            }
        }
    };

    /*private final ListSelectionListener selectGameListener = new ListSelectionListener() {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            lobbyFrame.showNicknamePanelForList(digitNicknameListenerFromExistingGame);
        }
    };*/
    private final ActionListener selectGameListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            lobbyFrame.showNicknamePanelForList(digitNicknameListenerFromExistingGame);
        }
    };
    private final ActionListener digitNicknameListenerFromNewGame = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            enteredNickname = lobbyFrame.getNickname();

            notifyNewGame(numOfPlayers, enteredNickname);
            setState(LobbyGraphicUI.State.WAITING_FOR_UPDATE);

            lobbyFrame.setVisible(false);
        }
    };

    private final ActionListener digitNicknameListenerFromExistingGame = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            enteredNickname = lobbyFrame.getNickname();
            int index = lobbyFrame.gameList.getSelectedIndex();
            gameID = lobbyFrame.gameID.get(index);

            notifyGameToAccess(gameID, enteredNickname);
            setState(LobbyGraphicUI.State.WAITING_FOR_UPDATE);

            lobbyFrame.setVisible(false);
        }
    };

    private final LobbyFrame lobbyFrame = new LobbyFrame(createGameListener,joinGameListener);
    @Override
    public void updateGamesSpecs(List<GameSpecs> gamesSpecs) {
        for (int key = 0; key < gamesSpecs.size(); key++)
            gameSpecsMap.put(key+1, gamesSpecs.get(key));

        if (getState() == LobbyGraphicUI.State.ASKING_WHICH_GAME_TO_ACCESS) {
            gameSpecsMap.clear();
            for (int key = 0; key < gamesSpecs.size(); key++)
                gameSpecsMap.put(key+1, gamesSpecs.get(key));

            //askGameToAccess();//TO FIX
        }
    }
    private void askGameToAccess() throws NoExistingGamesAvailable {

        if (gameSpecsMap.isEmpty())
            //JOptionPane.showMessageDialog(this,"I'm sorry, there aren't any existing games yet");

        System.out.println("""
                
                
                ---------------------------------
                Which game do you want to access?
                
                (/) Back
                """);
        for (Map.Entry<Integer, GameSpecs> entry : gameSpecsMap.entrySet()) {
            System.out.println(entry.getKey() + " - "
                    + "Game ID: " + entry.getValue().ID()
                    + "    Current number of players connected: " + entry.getValue().currentNumOfPlayers()
                    + "    Max number of players: " + entry.getValue().maxNumOfPlayers());
        }
        System.out.println("---------------------------------\n\n");

    }

    @Override
    public void reportError(String error) {
        System.err.println(error);
        setState(LobbyGraphicUI.State.RUNNING);
    }

    @Override
    public void stop() {
        setState(LobbyGraphicUI.State.STOPPING_THE_VIEW);
    }

    @Override
    public void run() {
        SwingUtilities.invokeLater(()->{
            lobbyFrame.setVisible(true);
        });
    }

    private LobbyGraphicUI.State getState() {
        synchronized (lock) {
            return state;
        }
    }

    private void setState(LobbyGraphicUI.State state) {
        synchronized (lock) {
            this.state = state;
            lock.notifyAll();
        }
    }
}
