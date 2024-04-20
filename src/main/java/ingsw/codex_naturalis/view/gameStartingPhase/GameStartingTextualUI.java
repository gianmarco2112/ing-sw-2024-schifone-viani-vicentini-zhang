package ingsw.codex_naturalis.view.gameStartingPhase;

public class GameStartingTextualUI extends GameStartingUI{

    public GameStartingTextualUI(int gameID) {
        super(gameID);
    }

    @Override
    public void run() {

        while (isRunning()){
            System.out.println("\nGameID: " + getGameID());
            System.out.println("Waiting for players...");
            try {
                Thread.sleep(2500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
