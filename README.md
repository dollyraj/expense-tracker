# expense-tracker
Build a simple Restful CRUD API for expense tracker using Spring Boot, Mysql, and JPA. Each api is secured using spring security.

#### 1.  Clone the application
`https://github.com/dollyraj/expense-tracker.git`

#### 2.  Create MYSQL Database
    create database expensetrcaker

#### 3.  Change mysql username and password as per your installation
* Open   `src/main/resources/application.properties` 
* change `spring.datasource.username` and `spring.datasource.password` as per your mysql installation

#### 4. Build and run the app using maven
```
mvn package 
java -jar target/expense-tracker-0.0.1-SNAPSHOT.jar
```

Alternatively, you can run the app without packaging it using -
If you use Maven, run the following command in a terminal window (in the complete) directory:
`mvn spring-boot:run`

The app will start running at http://localhost:8082.
## Explore API
The application defines following CRUD APIs.

### User API

|Method|Url|Description|
|----|---|-----------|
|POST|/v1/expenseTracker/signup/{type}|Add/signup user|
|GET|/v1/expenseTracker/getAllUsers|Get all user list|
|PUT|/v1/expenseTracker/updateuserProfile|update user profile|


### Expense Type API

|Method|Url|Description|
|----|---|-----------|
|POST|/v1/expenseTracker/addExpenseType|Add expense type|
|GET|/v1/expenseTracker/getAllexpenseTypes|Get all expense types list|

### Txn(transaction) API

|Method|Url|Description|
|----|---|-----------|
|POST|/v1/expenseTracker/addUserTxn|Add txn made by a user|
|GET|/v1/expenseTracker/fetchTxn|Get txn list based on filters provided(expense date,expenditure amount,expense type)|
|GET|/v1/expenseTracker/fetchCalculatedResults|Get total expense done by a user|


