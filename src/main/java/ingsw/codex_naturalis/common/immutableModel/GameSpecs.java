package ingsw.codex_naturalis.common.immutableModel;

/**
 * Not started game specs to show in lobby.
 * @param ID game id
 * @param currentNumOfPlayers current number of players
 * @param maxNumOfPlayers max number of players
 */
public record GameSpecs(int ID, int currentNumOfPlayers, int maxNumOfPlayers) {}
