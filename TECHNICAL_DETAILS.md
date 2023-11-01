# Brief Technical Details of LifeSync
## Building
```shell
mvn clean install
```

## Testing
### Running Tests
```shell
mvn clean test
```

### Generating Code Coverage Report
```shell
mvn jacoco:report
```

## Running in Production
```shell
docker compose up -d --build
```

## API Usage
```text
Food API     : Calorie Ninjas  https://calorieninjas.com/api
Exercise API : Nutritionix     https://www.nutritionix.com/natural-demo/exercise
```