# Keepass-cloud

### How to start
By default, the app is using an embedded database (H2) to make it easier to run the app.

To start the app, you can use Maven ;

    mvn spring-boot:run
    
It will download dependencies, compile code and start a server. Then you can access the app
at [http://localhost:8080](http://localhost:8080).

### Running the test
To run the tests, use maven:

    mvn test