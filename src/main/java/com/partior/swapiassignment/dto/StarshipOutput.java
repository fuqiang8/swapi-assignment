package com.partior.swapiassignment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StarshipOutput {
    private String name;
    @JsonProperty("class")
    private String starshipClass;
    private String model;

    public StarshipOutput() {
    }

    public StarshipOutput(String name, String starshipClass, String model) {
        this.name = name;
        this.starshipClass = starshipClass;
        this.model = model;
    }

    public String getName() {
        return name;
    }

    public String getStarshipClass() {
        return starshipClass;
    }

    public String getModel() {
        return model;
    }
}
