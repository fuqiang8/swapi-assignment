package com.partior.swapiassignment.service;

import com.partior.swapiassignment.dto.People;
import com.partior.swapiassignment.dto.Planet;
import com.partior.swapiassignment.dto.Starship;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InformationServiceTest {
    @InjectMocks
    InformationService informationService;
    @Mock
    PeopleService peopleService;
    @Mock
    StarshipService starshipService;
    @Mock
    PlanetService planetService;

    @Test
    void getFirstStarshipPilotedBy_shouldReturnStarship_givenPeopleHasStarship() {
        when(peopleService.searchExact(anyString()))
                .thenReturn(new People("people", new String[] {"starshipURI"}, ""));
        when(starshipService.getFromURI(anyString()))
                .thenReturn(new Starship("starship", "", "", ""));

        Starship starship = informationService.getFirstStarshipPilotedBy("people");
        assertEquals("starship", starship.name());
    }
    @Test
    void getFirstStarshipPilotedBy_shouldReturnStarship_givenPeopleHasNoStarship() {
        when(peopleService.searchExact(anyString()))
                .thenReturn(new People("people", new String[] {}, ""));

        Starship starship = informationService.getFirstStarshipPilotedBy("people");
        assertNull(starship);
    }

    @Test
    void getFirstStarshipPilotedBy_shouldReturnNull_givenPeopleStarshipIsNull() {
        when(peopleService.searchExact(anyString()))
                .thenReturn(new People("people", null, ""));

        Starship starship = informationService.getFirstStarshipPilotedBy("people");
        assertNull(starship);
    }

    @Test
    void getFirstStarshipPilotedBy_shouldReturnNull_givenPeopleIsNotFound() {
        when(peopleService.searchExact(anyString()))
                .thenReturn(null);

        Starship starship = informationService.getFirstStarshipPilotedBy("people");
        assertNull(starship);
    }

    @Test
    void getStarshipCrew_shouldReturnCrewAsNumber_givenStarshipCrewDoesNotContainsComma() {
        when(starshipService.searchExact(anyString()))
                .thenReturn(new Starship("starship", "", "", "123"));

        int crew = informationService.getStarshipCrew("starship");
        assertEquals(123, crew);
    }

    @Test
    void getStarshipCrew_shouldReturnCrewAsNumber_givenStarshipCrewContainsComma() {
        when(starshipService.searchExact(anyString()))
                .thenReturn(new Starship("starship","", "", "123,456"));

        int crew = informationService.getStarshipCrew("starship");
        assertEquals(123456, crew);
    }

    @Test
    void getStarshipCrew_shouldReturnCrewAsZero_givenStarshipCrewIsEmpty() {
        when(starshipService.searchExact(anyString()))
                .thenReturn(new Starship("starship","", "", ""));

        int crew = informationService.getStarshipCrew("starship");
        assertEquals(0, crew);
    }

    @Test
    void getStarshipCrew_shouldReturnCrewAsZero_givenStarshipCrewIsNull() {
        when(starshipService.searchExact(anyString()))
                .thenReturn(new Starship("starship","", "", null));

        int crew = informationService.getStarshipCrew("starship");
        assertEquals(0, crew);
    }

    @Test
    void getStarshipCrew_shouldReturnCrewAsZero_givenStarshipIsNotFound() {
        when(starshipService.searchExact(anyString()))
                .thenReturn(null);

        int crew = informationService.getStarshipCrew("starship");
        assertEquals(0, crew);
    }

    @Test
    void isPeopleOnPlanet_shouldReturnTrue_givenPeopleIsOnPlanet() {
        when(peopleService.searchExact(anyString()))
                .thenReturn(new People("people", null, "residentURL"));
        when(planetService.searchExact(anyString()))
                .thenReturn(new Planet("planet", new String[] {"residentURL"}));

        assertTrue(informationService.isPeopleOnPlanet("people", "planet"));
    }

    @Test
    void isPeopleOnPlanet_shouldReturnFalse_givenPeopleIsNotOnPlanet() {
        when(peopleService.searchExact(anyString()))
                .thenReturn(new People("people", null, "DoNotResideThere"));
        when(planetService.searchExact(anyString()))
                .thenReturn(new Planet("planet", new String[] {"residentURL"}));

        assertFalse(informationService.isPeopleOnPlanet("people", "planet"));
    }

    @Test
    void isPeopleOnPlanet_shouldReturnFalse_givenPeopleURLIsNull() {
        when(peopleService.searchExact(anyString()))
                .thenReturn(new People("people", null, null));
        when(planetService.searchExact(anyString()))
                .thenReturn(new Planet("planet", new String[] {"residentURL"}));

        assertFalse(informationService.isPeopleOnPlanet("people", "planet"));
    }

    @Test
    void isPeopleOnPlanet_shouldReturnFalse_givenPlanetHasNoResident() {
        when(peopleService.searchExact(anyString()))
                .thenReturn(new People("people", null, "residentURL"));
        when(planetService.searchExact(anyString()))
                .thenReturn(new Planet("planet", new String[] {}));

        assertFalse(informationService.isPeopleOnPlanet("people", "planet"));
    }

    @Test
    void isPeopleOnPlanet_shouldReturnFalse_givenPlanetResidentIsNull() {
        when(peopleService.searchExact(anyString()))
                .thenReturn(new People("people", null, "residentURL"));
        when(planetService.searchExact(anyString()))
                .thenReturn(new Planet("planet", null));

        assertFalse(informationService.isPeopleOnPlanet("people", "planet"));
    }

    @Test
    void isPeopleOnPlanet_shouldReturnFalse_givenPlanetIsNotFound() {
        when(peopleService.searchExact(anyString()))
                .thenReturn(new People("people", null, "residentURL"));
        when(planetService.searchExact(anyString()))
                .thenReturn(null);

        assertFalse(informationService.isPeopleOnPlanet("people", "planet"));
    }

    @Test
    void isPeopleOnPlanet_shouldReturnFalse_givenPeopleIsNotFound() {
        when(peopleService.searchExact(anyString()))
                .thenReturn(null);
        when(planetService.searchExact(anyString()))
                .thenReturn(new Planet("planet", new String[] {"residentURL"}));

        assertFalse(informationService.isPeopleOnPlanet("people", "planet"));
    }
}