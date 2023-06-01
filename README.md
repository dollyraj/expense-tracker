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

|Method|Url|Description|Sample|
|----|---|-----------|-------|
|POST|/v1/expenseTracker/signup/{type}|Add/signup user|[JSON](https://github.com/dollyraj/expense-tracker#create-user--v1expensetrackersignuptype)|
|GET|/v1/expenseTracker/getAllUsers|Get all user list|
|PUT|/v1/expenseTracker/updateuserProfile|update user profile|[JSON](https://github.com/dollyraj/expense-tracker#update-user-profile--v1expensetrackerupdateuserprofile)|


### Expense Type API

|Method|Url|Description|Sample|
|----|---|-----------|-------|
|POST|/v1/expenseTracker/addExpenseType|Add expense type|[JSON](https://github.com/dollyraj/expense-tracker#create-expense-type--v1expensetrackeraddexpensetype)|
|GET|/v1/expenseTracker/getAllexpenseTypes|Get all expense types list|

### Txn(transaction) API

|Method|Url|Description|Sample|
|----|---|-----------|--------|
|POST|/v1/expenseTracker/addUserTxn|Add txn made by a user|[JSON](https://github.com/dollyraj/expense-tracker#create-user-transaction--v1expensetrackeraddusertxn)|
|GET|/v1/expenseTracker/fetchTxn|Get txn list based on filters provided(expense date,expenditure amount,expense type)|
|GET|/v1/expenseTracker/fetchCalculatedResults|Get total expense done by a user|


### Test them using postman or any other rest client.

#### Create User ->/v1/expenseTracker/signup/{type}

```http
{
    "username":"user2",
    "userEmail":"user2@gmail.com",
    "contact":"91234567828",
    "password":"pass123"
}
```

#### Update User Profile ->/v1/expenseTracker/updateUserProfile
```http
{
    "name":"admin2",
    "contact":8790562345
}
```
### Create Expense Type ->/v1/expenseTracker/addExpenseType

```http
{
    "expenseType":"TRAVEL",
    "expenditureCost":200,
    "expenseDate":"2023-05-30",
    "expenseNote":"travel expense"
}
```

### Create User Transaction ->/v1/expenseTracker/addUserTxn

```http
{
    "expenseType":"TRAVEL",
    "expenditureCost":200,
    "expenseDate":"2023-05-30",
    "expenseNote":"travel expense"
}
```
