# Zai Weather Service

# Controller
Host: http://localhost:8080

/v1/weather

## GET

### Params
- city = the city to pull the weather

## Extending the application

### Adding support for new weather service

Implement RemoteWeatherApi interface as a new weather service client. No need to catch http errors as the circuit breaker will handle it. Client response should return an instance of WeatherRemoteApiResponse.

# How to run

## With local java 24 installed

Run the included gradle wrapper:

> ./gradlew bootRun --args='--api.config.weatherstack.key=mykey --api.config.openweather.key=mykey'

Note: Keys are not pushed to repo. Use your own.
You can also add to weather-config.properties

Unit tests

> ./gradlew test -Dapi.config.weatherstack.key=yourkey -Dapi.config.openweather.key=yourkey

## Docker

> ./gradlew bootJar

> docker build -t hellozai-weather:latest .

> docker run -p 8080:8080 --rm  -e "API_CONFIG_WEATHERSTACK_KEY=yourkey" -e "API_CONFIG_OPENWEATHER_KEY=yourkey" hellozai-weather

# Trade offs

- disabled service discovery registration to use Consul or something similar. Would prefer to put this in a service directory for HA and load balancing.
- map out a complete POJO for the Openweather and Weatherstack. Currently it would be too time onsuming to map out.
- metric & imperial conversions
- spring profiles. i.e. dev/prod
- add ability to have more than one fallback and iterate over them.
- Handle potential for more than one result?
- Skip or choose integration tests
- API security
- API metrics
- externally pulled secrets