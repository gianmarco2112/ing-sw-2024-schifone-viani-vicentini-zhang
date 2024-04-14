package ingsw.codex_naturalis.controller.setupPhase;

import ingsw.codex_naturalis.distributed.Client;
import ingsw.codex_naturalis.model.Game;

import java.util.ArrayList;
import java.util.List;

public class SetupController {

    private final Game model;
    private final List<Client> views = new ArrayList<>();

    //--------------------------------------------------------------------------------------
    public SetupController(Game model, Client view) {
        this.model = model;
        this.views.add(view);
    }


    public Game getModel() {
        return model;
    }

    public List<Client> getViews() {
        return views;
    }

    public void addView(Client view){
        this.views.add(view);
    }

    public void removeView(Client view) {
        this.views.remove(view);
    }

}
