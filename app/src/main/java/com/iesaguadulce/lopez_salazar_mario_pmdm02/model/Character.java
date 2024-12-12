package com.iesaguadulce.lopez_salazar_mario_pmdm02.model;

/**
 * Class representing characters from Super Mario Bros.
 *
 * @author Mario López Salazar
 * @version 1.1
 */
public class Character {

    /**
     * Variables to encapsulate the character's attributes.
     */
    private final String name;
    private final String picture;
    private final String description;
    private final String detail;
    private final String skills;


    /**
     * Constructs a new Character.
     *
     * @param name        The name of the character.
     * @param picture     The picture URL of the character.
     * @param description A brief description of the character.
     * @param detail      Detailed information about the character.
     * @param skills      The skills of the character.
     */
    public Character(String name, String picture, String description, String detail, String skills) {
        this.name = name;
        this.picture = picture;
        this.description = description;
        this.detail = detail;
        this.skills = skills;
    }


    /**
     * Returns the picture URL of the character.
     *
     * @return The picture URL.
     */
    public String getPicture() {
        return picture;
    }


    /**
     * Returns the name of the character.
     *
     * @return The name.
     */
    public String getName() {
        return name;
    }


    /**
     * Returns the brief description of the character.
     *
     * @return The brief description.
     */
    public String getDescription() {
        return description;
    }


    /**
     * Returns the detailed information of the character.
     *
     * @return The detailed information.
     */
    public String getDetail() {
        return detail;
    }


    /**
     * Returns the skills of the character.
     *
     * @return The skills.
     */
    public String getSkills() {
        return skills;
    }
}