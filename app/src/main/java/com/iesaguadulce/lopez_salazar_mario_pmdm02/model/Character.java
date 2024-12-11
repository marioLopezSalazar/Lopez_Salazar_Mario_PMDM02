package com.iesaguadulce.lopez_salazar_mario_pmdm02.model;

public class Character {

    private final String name;
    private final String picture;
    private final String description;
    private final String detail;
    private final String skills;

    public Character(String name, String picture, String description, String detail, String skills) {
        this.name = name;
        this.picture = picture;
        this.description = description;
        this.detail = detail;
        this.skills = skills;
    }

    public String getPicture() {
        return picture;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getDetail() {
        return detail;
    }

    public String getSkills() {
        return skills;
    }
}
