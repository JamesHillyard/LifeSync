# Brief Technical Details of LifeSync
## Building
```shell
mvn clean install
```

## Running in Production
```shell
docker compose up -d --build
```

---

## Testing
### Running Tests

- `-DskipTests` skips both unit and integration tests
- `-DskipUnitTests` skips unit tests but executes integration tests
- `-DskipIntegrationTests` skips integration tests but executes unit tests
- Add nothing to run unit tests and integration tests

> [!IMPORTANT]
Integration tests require a pre-configured environment to run correctly. You must run `docker compose -f docker-compose-integrationtest.yml up -d --build` before running the tests


**Unit Tests Only**
```shell
mvn clean install -DskipIntegrationTests
```
**Integration Tests Only**
```shell
docker compose down; docker compose -f .\docker-compose-integrationtest.yml up -d --build; mvn clean install -DskipUnitTests
```
**Entire Test Suite**
```shell
docker compose down; docker compose -f .\docker-compose-integrationtest.yml up -d --build; mvn clean install
```

---

## Test Extras
### Setup to Run UI Tests
```shell
mvn exec:java -e -D exec.mainClass=com.microsoft.playwright.CLI -D exec.args="install --with-deps chromium"
```

### Generating Code Coverage Report
```shell
mvn jacoco:report
```

---

## API Usage
```text
Food API     : Calorie Ninjas  https://calorieninjas.com/api
Exercise API : Nutritionix     https://www.nutritionix.com/natural-demo/exercise
```