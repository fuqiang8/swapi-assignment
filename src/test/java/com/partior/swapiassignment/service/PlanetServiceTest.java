package com.partior.swapiassignment.service;

import com.partior.swapiassignment.dto.Planet;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PlanetServiceTest {
    @InjectMocks
    PlanetService planetService;
    @Mock
    SWAPIWebClientService webClientService;

    @Test
    void searchExact_shouldReturnPlanet_givenExactMatchExists() {
        when(webClientService.searchPlanets(anyString()))
                .thenReturn(List.of(
                        new Planet("planet1", null),
                        new Planet("planet2", null)
                ));

        Planet planet = planetService.searchExact("planet2");
        assertEquals("planet2", planet.name());
    }

    @Test
    void searchExact_shouldReturnNull_givenNoExactMatchExists() {
        when(webClientService.searchPlanets(anyString()))
                .thenReturn(List.of(
                        new Planet("planet1", null),
                        new Planet("planet23", null)
                ));

        Planet planet = planetService.searchExact("planet2");
        assertNull(planet);
    }

    @Test
    void searchExact_shouldReturnNull_givenEmptyList() {
        when(webClientService.searchPlanets(anyString()))
                .thenReturn(List.of());

        Planet planet = planetService.searchExact("planet2");
        assertNull(planet);
    }
}