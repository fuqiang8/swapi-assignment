package com.partior.swapiassignment.service;

import com.partior.swapiassignment.dto.Starship;
import org.springframework.stereotype.Service;

@Service
public class InformationService {
    private final StarshipService starshipService;

    public InformationService(StarshipService starshipService) {
        this.starshipService = starshipService;
    }

    public int getStarshipCrew(String starshipName) {
        Starship starship = starshipService.searchExact(starshipName);
        if (starship == null) {
            return -1;
        } else if (starship.crew() == null) {
            return 0;
        } else {
            return Integer.parseInt(starship.crew().replaceAll(",", ""));
        }
    }
}
