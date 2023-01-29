# TamuHack2023
OptimalWorkSpaceAllocation

This project is a web application that uses Spring Boot for the backend and React for the frontend solving floor and team allocation based on their preferred, tolerated, no way team ids.

Constraints:
    Fill atleast 25% of the each floor

Prerequisites

    Java 8 or higher
    Node.js and npm
    Maven

Installation

Clone the repository:

git clone https://github.com/snehith57624/tamuhack23.git

Install the dependencies for the backend

cd [repository]
mvn install

Install the dependencies for the frontend

cd [repository]/client
npm install

Start the backend

cd [repository]
mvn spring-boot:run

Start the frontend

cd [repository]/client
npm start

Open a browser and navigate to http://localhost:3000 to view the application.

Note

    If you are using different port for backend update package.json file with the correct url and port.
    If you are using different database other than mysql update application.properties file with correct driver and url.

Deployment

Build the frontend:

cd [repository]/client
npm run build

Build the backend:

cd [repository]
mvn package

Copy the generated jar file and the build folder from the frontend to the server.

Start the backend:

java -jar [generated-jar-file].jar

Team:
Sri Datta Raghavendra,
Snehith Bikumandla,
Sami Abouelnaaj,
Salem Saleh
