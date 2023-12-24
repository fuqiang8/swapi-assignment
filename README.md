# swapi-project
project for Partior

<!-- TOC -->
* [swapi-project](#swapi-project)
  * [Overview](#overview)
  * [Setting up and running](#setting-up-and-running)
  * [How it works](#how-it-works)
  * [Validations and business logic](#validations-and-business-logic)
  * [Assumptions made](#assumptions-made)
<!-- TOC -->

## Overview
> Greetings, Jedi Master! <br/> 
> According to our sources, `Darth Vader` is planning an attack on the planet `Alderaan` with the `Death Star`. <br/>
> We'd need your help to figure out which `starships` he's using and how many `crew` are aboard the `Death Star`. <br/>
> Please double-check if Princess Leia `Leia Organa` is still on the planet`Alderaan`.

The Jedi Master has to gather the requested information and inform the Jedi High Council.

This project sets out to do just that. It gathers the following information:
- `Darth Vader`'s starship
- Number of crew on the `Death Star`
- Is princess `Leia Organa` on planet `Alderaan`

## Setting up and running
This project is built using Spring boot, Java 17 & Maven. The setup steps for Java and Maven can be found:
- For Java setup
  - [Download link](https://www.oracle.com/java/technologies/downloads/)
  - [Setting JAVA_HOME](https://www.baeldung.com/java-home-on-windows-7-8-10-mac-os-x-linux)
- For Maven setup
  - [Download link](https://maven.apache.org/download.cgi)
  - [Setting up Maven](https://www.baeldung.com/install-maven-on-windows-linux-mac)

To run, download this project as a zip and extract it. Alternatively, clone this project. Head to the folder where it resides and run the follow:
> mvn spring-boot:run 

This command should start a web server and provide the requested information for the Jedi High Council. 

The web server would run on port `8080`, providing the information on `/information`. To access it from the machine it is running in, enter the following in a web browser.
> http://localhost:8080/information

## How it works
This project queries [The Star Wars API](https://swapi.dev/) for its data. Here are the queries made to get the information.

- `Darth Vader`'s starship
  - People API would return URLs to the starships they have piloted. A separate call to the starship API is required to get the required information.
  - Queries made: 
    - Search for Darth Vader: `https://swapi.dev/api/people/?search=Darth Vader`
    - Direct starship URL: `https://swapi.dev/api/starships/1/`
- Number of crew on the `Death Star`
  - Information is available in the starship response
  - Query made: 
    - Search for Death Star: `https://swapi.dev/api/starships/?search=Death Star`
- Is princess `Leia Organa` on planet `Alderaan`
  - Planet API would return URLs to the residents who reside there. Checking if it contains the URL of the people we are after would determine if they are there.
  - Queries made: 
    - Search for Alderaan: `https://swapi.dev/api/planets/?search=Alderaan`
    - Search for Leia Organa: `https://swapi.dev/api/people/?search=Leia Organa`

## Validations and business logic
There are certain validations and business logic stated in the assignment. The project serves a single URL with no way to change any input. 
As such. it is not possible to alter inputs and test the validations and business logic.

The validations and business logic are tested against within the project tests. Here are the following tests which caters to them.

- If no starships were found, set the value for starship as an empty object {}.
  - Test class: `InformationControllerTest`
  - Test method: `getInformation_shouldReturnStarshipValueAsEmptyJsonObject_givenPeopleHasNoStarship()`
  - Note: `null` is used to represent empty in the test method. To understand this behavior, look into `InformationServiceTest` test class for methods with `getFirstStarshipPilotedBy_shouldReturnNull` prefix.
- If there is no crew on board the death star, set the crew value to 0.
  - Test class: `InformationServiceTest`
  - Test methods: methods with `getStarshipCrew_shouldReturnCrewAsZero` prefix 
- If Princess Leia is on the planet, then set "true" else set "false".
  - Test class: `InformationServiceTest`
  - Test method: methods with `isPeopleOnPlanet` prefix

## Assumptions made
Here are the assumptions made, since not all cases are stated in the assignments.

- A person would always use the 1st starship they have piloted within the array of starships.
- When searching, only a single result with the exact search terms would be considered.
  - This is to address situations where searching returns an array of results
  - E.g 'https://swapi.dev/api/people/?search=Darth' would return `Darth Vader` and `Darth Maul`. Both would be rejected since it is ambiguous which is the person we are looking for.
- If any required data to make a decision is not found, return its default failure value. The default failure value is taken from [Validations and business logic](#validations-and-business-logic)
  - E.g. When finding `Darth Vader`'s starship, the starship URL ends up as a dead link. The default failure value would be returned.
  - For "`Darth Vader`'s starship"
    - return empty object {}
  - For "Number of crew on the `Death Star`"
    - return 0
  - For "Is princess `Leia Organa` on planet `Alderaan`"
    - return false