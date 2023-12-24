package com.partior.swapiassignment.service;

import com.partior.swapiassignment.dto.People;
import com.partior.swapiassignment.dto.Planet;
import com.partior.swapiassignment.dto.Starship;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class InformationService {
    private final PeopleService peopleService;
    private final StarshipService starshipService;
    private final PlanetService planetService;

    public InformationService(PeopleService peopleService, StarshipService starshipService, PlanetService planetService) {
        this.peopleService = peopleService;
        this.starshipService = starshipService;
        this.planetService = planetService;
    }

    public Starship getFirstStarshipPilotedBy(String peopleName) {
        People people = peopleService.searchExact(peopleName);

        if (people == null
                || people.starships() == null
                || people.starships().length == 0) {
            return null;
        }

        return starshipService.getFromURI(people.starships()[0]);
    }

    public int getStarshipCrew(String starshipName) {
        Starship starship = starshipService.searchExact(starshipName);
        if (starship == null) {
            return -1;
        } else if (starship.crew() == null || starship.crew().isBlank()) {
            return 0;
        } else {
            return Integer.parseInt(starship.crew().replaceAll(",", ""));
        }
    }

    public boolean isPeopleOnPlanet(String peopleName, String planetName) {
        People people = peopleService.searchExact(peopleName);
        Planet planet = planetService.searchExact(planetName);

        if (people == null || planet == null || planet.residents() == null) {
            return false;
        }

        return Arrays.stream(planet.residents())
                .anyMatch(residentURL -> residentURL.equals(people.url()));
    }
}
