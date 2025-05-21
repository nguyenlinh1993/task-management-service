# TASK MANAGEMENT SERVICE


## I. Overall
### 1. Information

- This project implement the task-management-service. It includes 2 entities: User + Task
- API document and swagger at [here](./api_document/swagger.yaml)
- To test API with plugin HTTP of IntelliJ IDEA at [here](./api_http)

### 2. Framework + Language + plugin + tool
- Java: corretto 21 (21.0.3)
- Springboot: 3.4.5
- Gradle: 8.13
- Check coverage + high quality: checkstyle, spotbugs, pmd, jacoco
- Testing: JUnit, Mockito, MockMVC, Testcontainers
- Deployment: Docker, Docker compose
- Manage version Database changing: Flyway. Flyway in Spring Boot automatically manages database schema versioning and migration. It runs on application startup and checks for SQL migration scripts (e.g., V1__init.sql, V2__add_table.sql) located in the db/migration folder (by default). It applies any new migrations in order, records them in a flyway_schema_history table, and ensures that each migration is executed only once. This helps keep database schema changes consistent across environments.

### 3. What have I done?
- All API (CRUD) of 2 entities (User + Task)
- Filtering & Searching feature with Task entity
- Managing version ddl script with *Flyway* at [here](src%2Fmain%2Fresources%2Fdb%2Fmigration)
- Error Handling with *@ControllerAdvice* and implementing multi-language. It also has clear format
````json
{
  "hasError": true,
  "errors": [
    {
      "errorCode": "30010001",
      "errorMessage": "Username existed",
      "errorType": "INTERNAL"
    }
  ],
  "debugInfo": null,
  "timestamp": 1747644924605
}
````
- Write UT for some class combine with Mockito. I also implement Integration test [UserControllerIntegrationTest.java](src%2Ftest%2Fjava%2Fcom%2Flinhnt%2Ftaskmanagementservice%2Fintegration%2FUserControllerIntegrationTest.java). It used real postgres to run test cases (spring-boot-testcontainers)
- I have finished the document to guide other one can deploy this project at their local PC (Docker + Docker compose)

## II. Deployment with docker container
### 1. Require for environment
- Docker
- Docker compose
### 2. Deployment
#### 2.1. Stop and remove all service in docker compose (optional)
```shell
docker-compose down -v
```

#### 2.2. Build image of the application (require)
```shell
./bin/build.sh
```

#### 2.3. Start all service (include postgres + application) (require)
```shell
docker compose up -d
```

#### 2.4. Check an application is working or not
````shell
curl http://localhost:8080/health
````
Or check [this url](http://localhost:8080/health) on the browser

If you see a response: "OK". It means the application is working now.

#### 2.5. Test API
Now you can test with all API. I've already defined it like the **.http** file at [here](./api_http)

#### 2.6. Check api swagger
You can check swagger by click at [here](http://localhost:8081/)

## III. Development and Coding
### 1. Preparing for coding
- Java: corretto 21 (21.0.3)
- Gradle: 8.13
- IDE: IntelliJ

### 2. ENV
- Please check file [.env_example](.env_example). This is all ENV that need to setup into IDE. 
- To setting please follow [here](https://www.baeldung.com/intellij-idea-environment-variables).

### 3. Start project
- Now you can start a project with IntelliJ IDEA

### 4. Some useful command line when you're working with the project
- Because I add some plugin to improve quality coding like: Check style, Spotbugs, PMD, Jacoco. So you can use the command bellow to check quality of your code:
````shell
gradle checkstyleMain spotbugsMain pmdMain jacocoAppCoverageVerification
````

- Run UT + Integration test
````shell
gradle test
````

You can see report at [here](./build/reports/tests/test/index.html)|[Image](./images/report_test.png)

- Check coverage
````shell
gradle jacocoAppReport
````

You can see report at [here](./build/reports/jacoco/jacocoAppReport/html/index.html)|[Image](./images/report_coverage.png)