package ingsw.codex_naturalis.view.gameStartingPhase;


import java.io.IOException;

public class GameStartingTextualUI extends GameStartingUI{

    private enum State {
        RUNNING,
        WAITING_FOR_UPDATE,
        STOPPING_THE_VIEW
    }

    private GameStartingTextualUI.State state = State.WAITING_FOR_UPDATE;

    private final Object lock = new Object();

    private int gameID;



    private GameStartingTextualUI.State getState() {
        synchronized (lock) {
            return state;
        }
    }

    private void setState(GameStartingTextualUI.State state) {
        synchronized (lock) {
            this.state = state;
            lock.notifyAll();
        }
    }

    @Override
    public void run() {
        while (true) {
            while (getState() == GameStartingTextualUI.State.WAITING_FOR_UPDATE) {
                synchronized (lock) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        System.err.println("Error while waiting for server update");
                    }
                }
            }

            if (getState() == GameStartingTextualUI.State.STOPPING_THE_VIEW) {
                return;
            }

            System.out.println("\nGameID: " + gameID);
            System.out.println("Waiting for players...");
            try {
                Thread.sleep(2500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }

    @Override
    public void updateGameID(int gameID) {
        this.gameID = gameID;
        setState(State.RUNNING);
    }

    @Override
    public void stop() {
        setState(State.STOPPING_THE_VIEW);
    }
}
