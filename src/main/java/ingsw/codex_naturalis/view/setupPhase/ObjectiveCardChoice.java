package ingsw.codex_naturalis.view.setupPhase;

public enum ObjectiveCardChoice {

    CHOICE_1("Objective card 1"),
    CHOICE_2("Objective card 2");

    private final String description;

    ObjectiveCardChoice(String description){
        this.description = description;
    }

    public String getDescription(){
        return description;
    }

}
