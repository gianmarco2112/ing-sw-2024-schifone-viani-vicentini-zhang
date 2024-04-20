package ingsw.codex_naturalis.view.gameStartingPhase;


public abstract class GameStartingUI implements Runnable {

    private boolean running = true;

    private final int gameID;



    public GameStartingUI(int gameID) {
        this.gameID = gameID;
    }



    public boolean isRunning(){
        return running;
    }

    public int getGameID() {
        return gameID;
    }



    public void stop(){
        running = false;
    }

}
