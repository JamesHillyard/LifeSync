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
```shell
mvn clean install
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