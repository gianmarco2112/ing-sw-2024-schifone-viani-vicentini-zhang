package ingsw.codex_naturalis.distributed;

import ingsw.codex_naturalis.controller.setupPhase.SetupController;
import ingsw.codex_naturalis.model.Game;

import java.util.ArrayList;
import java.util.List;

public class GameManagement <Controller>{

    private final Game model;
    private final Controller controller;
    private final List<Client> views = new ArrayList<>();

    public GameManagement(Game model, Controller controller, Client view){
        this.model = model;
        this.controller = controller;
        this.views.add(view);
    }


    public Game getModel() {
        return model;
    }

    public Controller getController() {
        return controller;
    }

    public List<Client> getViews() {
        return views;
    }

    public void addView(Client view){
        this.views.add(view);
    }
}
