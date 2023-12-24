package com.partior.swapiassignment.service;

import com.partior.swapiassignment.dto.Planet;
import org.springframework.stereotype.Service;

@Service
public class PlanetService {
    private final SWAPIWebClientService webClientService;

    public PlanetService(SWAPIWebClientService webClientService) {
        this.webClientService = webClientService;
    }

    public Planet searchExact(String planetName) {
        return webClientService.searchPlanets(planetName)
                .stream()
                .filter(planet -> planet.name().equals(planetName))
                .findFirst()
                .orElse(null);
    }
}
