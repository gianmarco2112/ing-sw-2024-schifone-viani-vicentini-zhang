package ingsw.codex_naturalis.model.cards.objective;

import ingsw.codex_naturalis.model.PlayerArea;

public class PatternConcreteStrategy implements PatternStrategy{
    PatternObjectiveCard patternObjectiveCard;
    PlayerArea playerArea;
    public PatternConcreteStrategy(PatternObjectiveCard c, PlayerArea area){
        patternObjectiveCard=c;
        playerArea=area;
    }
    //da implementare ancora
    @Override
    public int run() {
        return 0;
    }
}
