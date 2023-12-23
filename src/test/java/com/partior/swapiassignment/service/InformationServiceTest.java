package com.partior.swapiassignment.service;

import com.partior.swapiassignment.dto.Starship;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InformationServiceTest {
    @InjectMocks
    InformationService informationService;
    @Mock
    StarshipService starshipService;

    @Test
    void getStarshipCrew_shouldReturnCrewAsNumber_whenStarshipCrewDoesNotContainsComma() {
        when(starshipService.searchExact(anyString()))
                .thenReturn(new Starship("starship", "123"));

        int crew = informationService.getStarshipCrew("starship");
        assertEquals(123, crew);
    }

    @Test
    void getStarshipCrew_shouldReturnCrewAsNumber_whenStarshipCrewContainsComma() {
        when(starshipService.searchExact(anyString()))
                .thenReturn(new Starship("starship", "123,456"));

        int crew = informationService.getStarshipCrew("starship");
        assertEquals(123456, crew);
    }

    @Test
    void getStarshipCrew_shouldReturnCrewAsNumber_whenStarshipCrewIsNull() {
        when(starshipService.searchExact(anyString()))
                .thenReturn(new Starship("starship", null));

        int crew = informationService.getStarshipCrew("starship");
        assertEquals(0, crew);
    }

    @Test
    void getStarshipCrew_shouldReturnCrewAsNegativeOne_whenStarshipIsNull() {
        when(starshipService.searchExact(anyString()))
                .thenReturn(null);

        int crew = informationService.getStarshipCrew("starship");
        assertEquals(-1, crew);
    }
}