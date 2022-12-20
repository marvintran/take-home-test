# Proxy/Facade of Open Library
This is a basic Spring-Boot application where we create a service that will act as a proxy/facade of another service

Two endpoints are `search` and `get`
* http://localhost:8080/api/v1/search?text=lord+of+the+rings
    * that will call http://openlibrary.org/search.json?q=the+lord+of+the+rings
* http://localhost:8080/api/v1/get?id=OL9158246M
    * that will call https://openlibrary.org/books/OL9158246M.json

## Build
Do a maven clean install.
```
mvn clean install
```

Start the application in a few ways

1. Spring boot
```
mvn spring-boot:run
```

2. Packaging and executing
```
mvn clean package
java -jar target/basic-transaction-api-v1-0.0.1-SNAPSHOT.jar
```

3. Docker
```
todo
```

Example of existing endpoints are below
```
http://localhost:8080/api/v1/search?text=lord+of+the+rings
http://localhost:8080/api/v1/get?id=OL9158246M
```

## Tests
A few tests are created to test a valid search, invalid search, valid book id, invalid book id

## Documentation
Besides this README, Swagger documentation can be found at http://localhost:8080/swagger-ui/index.html
![image](https://user-images.githubusercontent.com/43839159/208357296-68d41197-50e9-42b1-a5a1-a4286ee4bedf.png)

## Monitoring
For monitoring, Spring Boot Actuator has been added as a dependency which can be used for health and monitoring metrics and other operational information such as
* http://localhost:8080/health
* ![image](https://user-images.githubusercontent.com/43839159/208358083-7a3d5bcb-b35a-4cec-a9d7-d1ca8ded0a88.png)
* http://localhost:8080/actuator/metrics/process.cpu.usage
* ![image](https://user-images.githubusercontent.com/43839159/208359397-b22f0b9f-c07b-4403-a122-6a3103cef312.png)

## Packaging
* This is something that can be taken as a future enhancement
* Unfortunately I had some errors running the executable jar file and in the interest of time, opted to skip this
* Exception in thread "main" java.lang.UnsupportedClassVersionError: com/example/takehometest/TakeHomeTestApplication has been compiled by a more recent version of the Java Runtime (class file version 61.0), this version of the Java Runtime only recognizes class file versions up to 53.0

## Future enhancements
* I would have liked to create my own custom exception handling. Right now I create my own custom string, but I would have liked to create something such as a BookNotFoundException
* Setting up Docker Compose is something I would have liked to do as well
* Initially I had used @Value and pulled it from application.properties, but when running tests, the values were null. In the end I used final Strings, but I would have liked to use application.properties
