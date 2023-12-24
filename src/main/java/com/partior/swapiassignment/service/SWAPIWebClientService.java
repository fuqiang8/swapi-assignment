package com.partior.swapiassignment.service;

import com.partior.swapiassignment.dto.People;
import com.partior.swapiassignment.dto.SWAPIOutput;
import com.partior.swapiassignment.dto.Starship;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;

import java.util.List;

@Service
public class SWAPIWebClientService {
    private final Logger logger = LoggerFactory.getLogger(SWAPIWebClientService.class);
    private final String BASE = "https://swapi.dev/api/";
    private final String SEARCH_PREFIX = "?search=";
    private final WebClient webClient = WebClient.create();

    public <T> T getFromURI(String queryURI, Class<T> returnType) {
        try {
            return webClient
                    .get()
                    .uri(queryURI)
                    .retrieve()
                    .bodyToMono(returnType)
                    .block();
        } catch (WebClientException e) {
            logger.warn("Invalid query: {}", queryURI);
            return null;
        }
    }

    public List<People> searchPeople(String peopleName) {
        String queryURI = BASE + "people/" + SEARCH_PREFIX + peopleName;
        var returnType = new ParameterizedTypeReference<SWAPIOutput<People>>() {};
        SWAPIOutput<People> out = fetchResult(queryURI, returnType);
        return (out == null) ? List.of() : out.results();
    }

    public List<Starship> searchStarships(String starshipName) {
        String queryURI = BASE + "starships/" + SEARCH_PREFIX + starshipName;
        var returnType = new ParameterizedTypeReference<SWAPIOutput<Starship>>() {};
        SWAPIOutput<Starship> out = fetchResult(queryURI, returnType);
        return (out == null) ? List.of() : out.results();
    }

    private <T> SWAPIOutput<T> fetchResult(String queryURI, ParameterizedTypeReference<SWAPIOutput<T>> returnType) {
        try {
            return webClient
                    .get()
                    .uri(queryURI)
                    .retrieve()
                    .bodyToMono(returnType)
                    .block();
        } catch (WebClientException e) {
            logger.warn("Invalid query: {}", queryURI);
            return null;
        }
    }
}
