package com.example.mongoAdvance.entity;

import org.springframework.data.mongodb.core.mapping.Document;

@Document ( collection = "Posts")
public class Posts {

    private String description;
    private String profile;
    private int totalExper;
    private String[] tech;


    public Posts() {
    }

    public Posts(String description, String profile, int totlalExper , String[] tech) {
        this.description = description;
        this.profile = profile;
        this.totalExper = totlalExper;
        this.tech = tech;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public int getTotalExper() {
        return totalExper;
    }

    public void setTotalExper(int totalExper) {
        this.totalExper = totalExper;
    }

    public String[] getTech() {
        return tech;
    }

    public void setTech(String[] tech) {
        this.tech = tech;
    }
}
