package com.partior.swapiassignment.service;

import com.partior.swapiassignment.dto.People;
import org.springframework.stereotype.Service;

@Service
public class PeopleService {
    private final SWAPIWebClientService webClientService;

    public PeopleService(SWAPIWebClientService webClientService) {
        this.webClientService = webClientService;
    }

    public People searchExact(String query) {
        return webClientService.searchPeople(query)
                .stream()
                .filter(people -> people.name().equals(query))
                .findFirst()
                .orElse(null);
    }
}
