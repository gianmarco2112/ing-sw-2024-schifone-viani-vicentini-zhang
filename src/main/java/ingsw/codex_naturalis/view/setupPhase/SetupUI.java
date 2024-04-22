package ingsw.codex_naturalis.view.setupPhase;


import ingsw.codex_naturalis.model.cards.initialResourceGold.PlayableCard;

import java.util.List;

public abstract class SetupUI extends SetupObservable implements Runnable {

    public abstract void stop();

    public abstract void updateSetup1(PlayableCard.Immutable initialCard, List<PlayableCard.Immutable> resourceCards, List<PlayableCard.Immutable> goldCards);
}
