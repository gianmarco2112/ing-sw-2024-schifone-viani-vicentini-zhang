package ingsw.codex_naturalis.enumerations;
import ingsw.codex_naturalis.model.DefaultValue;
/**
 * Symbols' enumeration
 * This enumeration represents all the different type of Symbols (Resources or Objects)
 * that a card could have.
 * "NULL" is an additional symbol we would use to indicate that a corner doesn't contain
 * any resource or object.
 * */
public enum Symbol {
    PLANT(DefaultValue.PlantColor + "P" + DefaultValue.ANSI_RESET, DefaultValue.PlantColor),
    ANIMAL(DefaultValue.AnimalColor + "A" + DefaultValue.ANSI_RESET, DefaultValue.AnimalColor),
    FUNGI(DefaultValue.FungiColor + "F" + DefaultValue.ANSI_RESET, DefaultValue.FungiColor),
    INSECT(DefaultValue.InsectColor + "I" + DefaultValue.ANSI_RESET, DefaultValue.InsectColor),
    QUILL("Q", ""),
    INKWELL("I", ""),
    MANUSCRIPT("M", ""),
    EMPTY(" ", "");

    private final String coloredChar;
    private final String color;

    Symbol(String coloredChar, String color){
        this.coloredChar = coloredChar;
        this.color = color;
    }

    public String getColoredChar(){
        return coloredChar;
    }

    public String getColor() {
        return color;
    }
}


