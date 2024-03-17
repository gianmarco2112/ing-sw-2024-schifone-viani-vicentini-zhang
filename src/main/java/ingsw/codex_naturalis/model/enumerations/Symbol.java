package ingsw.codex_naturalis.model.enumerations;
/**
 * Symbols' enumeration
 * This enumeration represents all the different type of Symbols (Resources or Objects)
 * that a card could have.
 * "NULL" is an additional symbol we would use to indicate that a corner doesn't contain
 * any resource or object.
 * */
public enum Symbol {
    PLANT,
    ANIMAL,
    FUNGI,
    INSECT,
    QUILL,
    INKWELL,
    MANUSCRIPT,
    EMPTY
}
