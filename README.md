# Hotel Management System Using SpringBoot Microservices

Welcome to the Hotel Management System project !!!

This repository contains application that is designed to manage various aspects of a hotel, including user registration, user authentication and authorization, booking rooms, adding hotels, and handling user ratings etc.

## Prerequisites

- Java 17
- Maven
- MySQL
- Spring Boot
- Spring Data JPA
- Spring Security
- Spring Cloud
- Spring Cloud Gateway
- Spring Cloud Config
- JWT
- Netflix Eureka
- Docker

## Microservice Patterns Used 

- #### Saga Pattern: 

   Basically used during the payment/refund process to ensure that a series of local transactions are all executed or compensated. Each step is an individual local transaction and has a compensating transaction.
  - Example: If Payment is unsuccessful then blocked room is released. Also If the refund is not processed then the reservation is not cancelled.

- #### API Gateway Pattern: 

  All client requests go through an API Gateway (Spring Cloud Gateway), which routes requests to appropriate services.

## Features

This application contains 3 Roles **(Guest, User, Useradmin)**, these roles define which URL can be accessed with the JWT token. 
It also provides below services, their **GET/POST** Request, Roles needed to access URL and their features:

 #### Eureka-Service:
   Register and discover microservices.

 #### Config-Service:
   Centralized configuration management for microservices.

 #### Auth-Gateway-Service:
   Unified access point for microservices with JWT user authentication and authorization.

   - **Guest Role**
       > **POST:** Register User.
       > 
       > **POST:** Get JWT Token.

   - **Admin Role**
       > **GET:** Get User Credentials.
       > 
       > **GET:** Get User Roles.

 #### User-Service:

   - **Admin Role**
       > **GET:** Get User details.

   - **UserAdmin Role**
       > **GET:** Get User by UserId.
       > 
       > **GET:** Get User by UserName.
       > 
       > **GET:** Get Wallet Details by UserId.

   - **User Role**
       > **GET:** Get Wallet Balance by UserId.
       > 
       > **POST:** Update Wallet Balance by UserId.

 #### Hotel-Service:

   - **Guest Role**
       > **GET:** Get Hotel details.
       > 
       > **GET:** Get Hotel details by HotelId.
       > 
       > **GET:** Get Hotel details by Location.
       > 
       > **GET:** Get Hotel details by Name.
       > 
       > **GET:** Get Room details by HotelId.
       > 
       > **GET:** Get Room details by RoomId.
       > 
       > **GET:** Get Available Room details by HotelId.
       > 
       > **GET:** Get Hotel with Room details.

   - **Admin Role**
       > **POST:** Add Hotel.
       > 
       > **DELETE:** Delete Hotel by HotelId.
       > 
       > **GET:** Get Booked Room details by HotelId.
       > 
       > **POST:** Update Room status by HotelId.
       > 
       > **POST:** Add Room by HotelId.
       > 
       > **DELETE:** Delete Room by RoomId.

 #### Rating-Service:

   - **Guest Role**
       > **GET:** Get All Ratings.

   - **UserAdmin Role**
       > **GET:** Get Rating by UserId.
       > 
       > **GET:** Get Rating by HotelId.

   - **User Role**
       > **POST:** Add Rating.

 #### Payment-Service:

   - **Admin Role**
       > **GET:** Get Payment Details.

   - **UserAdmin Role**
       > **GET:** Get Payment Details by Id.
       > 
       > **POST:** Refund Payment by PaymentId.

   - **User Role**
       > **POST:** Make Payment by UserId.

 #### Booking-Service:
   - **User Role**
       > **POST:** Create Booking.

   - **UserAdmin Role**
       > **GET:** Get Booking by Id.
       > 
       > **POST:** Cancel Booking by Id.

   - **Admin Role**
       > **GET:** Get All Booking.

## Installation

1. Clone the repository.

       git clone https://github.com/KarthikeyanNagarajan/Hotel-Management-System-Microservice.git

2. Navigate to the project directory.
      
       cd Hotel-Management-System-Microservice

3. Configure the properties of each microservice according to your requirements.

4. Run the Application in below order.

  - ##### Start Eureka-Service
        cd Eureka-Service
        mvn spring-boot:run

  - ##### Start Config-Service
        cd Config-Service
        mvn spring-boot:run

  - ##### Start Auth-Gateway-Service
        cd Auth-Gateway-Service
        mvn spring-boot:run

  - ##### Start User-Service
        cd User-Service
        mvn spring-boot:run

  - ##### Start Hotel-Service
        cd Hotel-Service
        mvn spring-boot:run

  - ##### Start Rating-Service
        cd Rating-Service
        mvn spring-boot:run

  - ##### Start Payment-Service
        cd Payment-Service
        mvn spring-boot:run

  - ##### Start Booking-Service
        cd Booking-Service
        mvn spring-boot:run

5. Check Service Status in Eureka Dashboard. It will show which services are up and running and their corresponding instances.
  - ##### Eureka Dashboard: 
        http://localhost:8761

6. Import Postman Collection which contains all the URL routes for all services.
           
       Hotel-Management-System-Microservice.postman_collection.json

## Contributing

Contributions are welcome! Please fork the repository and create a pull request.
