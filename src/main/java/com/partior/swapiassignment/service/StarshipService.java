package com.partior.swapiassignment.service;

import com.partior.swapiassignment.dto.Starship;
import org.springframework.stereotype.Service;

@Service
public class StarshipService {
    private final SWAPIWebClientService webClientService;

    public StarshipService(SWAPIWebClientService webClientService) {
        this.webClientService = webClientService;
    }

    public Starship searchExact(String starshipName) {
        return webClientService.searchStarships(starshipName)
                .stream()
                .filter(starship -> starship.name().equals(starshipName))
                .findFirst()
                .orElse(null);
    }
}
