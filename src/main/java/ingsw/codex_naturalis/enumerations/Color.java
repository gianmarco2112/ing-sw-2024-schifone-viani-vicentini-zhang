package ingsw.codex_naturalis.enumerations;
/**
 * Color's enumeration
 * Each player is represented on the score track by a token of a specific color.
 * This enumeration represents the 4 possibles colors that a player could choose.
 */
public enum Color {
    RED("Red", "\u001B[31m"),
    BLUE("Blue", "\u001B[34m"),
    GREEN("Green", "\u001B[32m"),
    YELLOW("Yellow", "\u001B[33m");

    private final String description;
    private final String colorCode;

    Color(String description, String colorCode){
        this.description = description;
        this.colorCode = colorCode;
    }

    public String getDescription(){
        return description;
    }

    public String getColorCode() {
        return colorCode;
    }
}
