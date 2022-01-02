# HireNet - Services Web App
## Single page application created with React and Spring + Jersey

## Authors
- [Francisco Quesada](https://github.com/fquesada00)
- [Manuel Félix Parma](https://github.com/manuelfparma)
- [Manuel Joaquín Rodríguez](https://github.com/rodriguezmanueljoaquin)
- [Gonzalo Arca](https://github.com/gonzaloarca)

## About the project
This project was carried out for the authors' university (Instituto Tecnológico de Buenos Aires) in 2021 for the Web Applications Project course. 
It was first developed using purely Spring MVC and Bootstrap for styling, and later migrated to a single page application using React, Jersey and Material UI for styling.
Its purpose is to connect professionals who wish to offer their services online and regular customers who wish to hire these services. 

## Dependencies
- Node.js (tested with v14.13.1)
- NVM (tested with v0.38.0, used to guarantee same Node version across all developers)
- Maven (tested with v3.8.2)
- Java 8
- PostgreSQL (tested with v14.0)

## How to run the project in a development environment
First, make sure you're running PostgreSQL. Database name, password and service port must be manually introduced in `WebConfig.java` in `webapp/src/main/java/ar/edu/itba/paw/webapp/config`

To start the REST API service, from the root folder of the project run:
```bash
mvn jetty:run
```

Once the Jetty server has started, start the front end service
First, move into the `frontend` folder and run the following command:
```bash
nvm use
```
This guarantees that the same Node version used for the development of the project is used

Next, install the project dependencies
```bash
npm install
```

Finally, start the React development server from the same folder:
```bash
npm start
```

