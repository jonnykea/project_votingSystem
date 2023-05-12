Technical requirement to
===============================
Graduation project [стажировки TopJava](https://javaops.ru/view/topjava)

Design and implement a REST API using Spring-Boot/Spring Data JPA **without frontend**.

The task is:

Build a voting system for deciding where to have lunch.

* 2 types of users: admin and regular users
* Admin can input a restaurant and one's lunch menu of the day (2-5 items usually, just a dish name and price)
* Menu changes each day (admins do the updates)
* Users can vote for a restaurant they want to have lunch at today
* Only one vote counted per user
* If user votes again the same day:
    - If it is before 11:00 we assume that he changed his mind.
    - If it is after 11:00 then it is too late, vote can't be changed

Each restaurant provides a new menu each day.

-------------------------------------------------------------
## Description of the voting system for the restaurants

How it works:
-	every day restaurants provide a new menu with dishes (from 2 to 5);
-	admin creates with yours controller's method:
     - a restaurant;
     - creates dishes for the one;
     - creates a menu with restaurant’s dishes.
 
How voting system works:

-	first you should authorize to take part in voting. You can do it yourself through "profile-controller/post" 
or ask admin to register you, also you can update your profile with "profile-controller/put";
-	next via the "restaurant-controller/get" any user can display the necessary information, 
and get any information in details via "menu-controller/get";
-	the user can do the vote for the restaurant in which he is interested in via the "vote-controller/post" 
or update the one to 11:00 via "vote-controller/put";

This system finds out which restaurant has a more rating.

All history of voting, restaurants, menu and dishes will be saved in the data.

-------------------------------------------------------------

- Stack: [JDK 17](http://jdk.java.net/17/), Spring Boot 3.0, Maven, Spring Security, Spring Data JPA(Hibernate), REST(Jackson), Lombok, H2, Caffeine Cache, Swagger/OpenAPI 3.0
- 
- Run: `mvn spring-boot:run` in root directory.

-----------------------------------------------------
[REST API documentation](http://localhost:8080/)  
Креденшелы:

```
Admin: admin@gmail.com / admin
User:  user@yandex.ru / password
Guest: guest@gmail.com / guest
```