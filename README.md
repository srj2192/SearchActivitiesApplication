# Search Activities Application

## Overview

This project is a full-stack web application designed to display a list of activities. Users can search for activities by title, and view details such as the activity's title, price (with currency), rating, whether it has a special offer, and the supplier's name and location.

The application consists of two primary components:

## Backend API: 
Rest API build using Java17 on Springboot.

Endpoints:

* `/activities` - activities endpoint can be used to fetch all activities

    use query param `title` to filter activities by title name

## Frontend Client
React with TypeScript: For building the user interface.

Material-UI (MUI): For UI components and theming(used defaut theme).

Axios: For making HTTP requests to the backend API.

## UI Preview

![preview](/resources/preview.png)

## Steps to run

* Both backend and frontend apps are consolidated under `docker-compose` file. 
* To run them simply execute `docker-compose up --build`
* Frontend client can be accessed on URL `http://localhost:8081`
* Activities can be filtered based on title on the frontend app.

## Architectural Pattern, Approach & Improvement

* Looking at the project requirement and datasource setup, I have decided to use `Service Layer Respoitory` Pattern. Its is quite simple to use with less complexities and can be scaled as the scalability needs.

* Another important advantage behind using this pattern is seperation of concerns. All the data access can be abstracted behind data repositories. And core business logic can be separeted out in service layers.

* For loading data from resource JSON files in backend app is done in the Repository itself using `PostConstruct` , as I thought the data sources will be replaced by DB clients for production use. For more elegant approaches we can consider using AppListeners for startup events to do startup sideloading tasks.

* Unit tests for Backend REST app is written using JUNIT and mockito in their respective test packages.

### Improvement scenarios:

With more time, there was scope for few improvments for the appilications on both front end and backend. I have idetified and listed here:

* Pagintation support on REST API endpoint and also implementation of the same in frontend app as well.

* Test cases(JEST and cypress) for frontend app.

* Using a data store like Redis for loading data on STARTUP instead of keeping in app memory.

* More test scenarios in Backend unit test maybe possible.

