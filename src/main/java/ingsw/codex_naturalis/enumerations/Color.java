package ingsw.codex_naturalis.enumerations;
/**
 * Color's enumeration
 * Each player is represented on the score track by a token of a specific color.
 * This enumeration represents the 4 possibles colors that a player could choose.
 */
public enum Color {
    RED("Red"),
    BLUE("Blue"),
    GREEN("Green"),
    YELLOW("Yellow");

    private final String description;

    Color(String description){
        this.description = description;
    }

    public String getDescription(){
        return description;
    }
}
