package ingsw.codex_naturalis.view.setupPhase;

import ingsw.codex_naturalis.enumerations.PlayersConnectedStatus;
import ingsw.codex_naturalis.model.Game;
import ingsw.codex_naturalis.model.observerObservable.Event;

import java.io.IOException;
import java.util.Scanner;

public class SetupTextualUI extends SetupUI {

    private PlayersConnectedStatus playersConnectedStatus;

    private boolean running;

    private final Scanner s = new Scanner(System.in);

    private String nickname;


    public SetupTextualUI(PlayersConnectedStatus playersConnectedStatus){
        running = true;
        this.playersConnectedStatus = playersConnectedStatus;
        this.nickname = nickname;
    }

    @Override
    public void setPlayersConnectedStatus(PlayersConnectedStatus playersConnectedStatus) {
        this.playersConnectedStatus = playersConnectedStatus;
    }





    @Override
    public void run() {

        while (playersConnectedStatus == PlayersConnectedStatus.WAIT) {
            try {
                System.out.println("Waiting for players to join...");
                Thread.sleep(3000);
            } catch (InterruptedException e) { }
        }

        System.out.println();
        System.out.println("Press enter if you're ready to play!");
        try {
            System.in.read();
        } catch (IOException e) { }
        notifyReady();

    }


    @Override
    public void stop() {
        running = false;
    }


    @Override
    public void update(Game.Immutable o, Event arg, String nickname, String playerWhoUpdated) {

    }

}
