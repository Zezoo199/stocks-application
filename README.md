## Payconiq - Stocks
A Spring boot microservice that allow viewing,creation and update of Stocks prices.
## Getting started
### Running tests
mvn clean install
### Start the backend
Start as Spring boot web application  main=StocksApplication.java
### Start The Angular  Frontend
Navigate to src/main/frontend and run  npm install and ng build then ,start using npm start and go to localhost:4200, you can also check another readme inside frontend,note the proxy-conf file redirect to the backend.
## Swagger
http://localhost:8080/swagger-ui.html
## FrontEnd
http://localhost:4200
## Implementation assumptions and notes
For consistency the stock name must be unique , i.e you cannot create two stocks with same name.
It is ok during POST of a stock to pass the lastUpdate as well.
Note that front end is submodule inside this git repository.
I noticed that it might make sense to sort the list of stocks by lastUpdate but i just added it as commented code since is not required.
## Built with
Spring boot 2.1.3,Maven,Angular 7,REST,Maven code formatting.
## Design patterns
Singleton for repository,Observer for the frontend.
## Test coverage 
Test coverage for used lines is 100%
## Example Endpoint CURLs
curl -X GET "http://localhost:8080/api/stocks" -H "accept: */*"

curl -X GET "http://localhost:8080/api/stocks/1" -H "accept: */*"

curl -X POST "http://localhost:8080/api/stocks" -H "accept: */*" -H "Content-Type: application/json" -d "{ \"currentPrice\": { \"amount\": 10, \"currency\": \"EUR\" }, \"id\": 0, \"lastUpdate\": \"2019-06-25T23:23:15.464Z\", \"name\": \"FACEBOOK\"}"

curl -X PUT "http://localhost:8080/api/stocks/1" -H "accept: */*" -H "Content-Type: application/json" -d "{ \"amount\": 99, \"currency\": \"EUR\" }"