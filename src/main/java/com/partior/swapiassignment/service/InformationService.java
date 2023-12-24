package com.partior.swapiassignment.service;

import com.partior.swapiassignment.dto.People;
import com.partior.swapiassignment.dto.Starship;
import org.springframework.stereotype.Service;

@Service
public class InformationService {
    private final PeopleService peopleService;
    private final StarshipService starshipService;

    public InformationService(PeopleService peopleService, StarshipService starshipService) {
        this.peopleService = peopleService;
        this.starshipService = starshipService;
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
}
