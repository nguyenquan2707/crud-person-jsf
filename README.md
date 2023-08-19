
# crud-person
**Study Project: Spring Boot, JSF, API REST**

Link to the JSF interface:
> http://200.19.33.250:9001/

PS: The deployment was performed on Google Cloud on a VM f1-micro (1 vCPU, 0.6 GB of memory). So don't expect system performance :)

## Spring REST API

Documentation is an essential part of building REST APIs.

We can access the documents in JSON format at:
> http://200.19.33.250:9001/api-docs/

The OpenAPI definitions are in JSON format by default. For yaml format, we can obtain the definitions at:
> http://200.19.33.250:9001/api-docs.yaml

To view and interact with the API’s resources without having any of the implementation logic in place, try Swagger UI:
> http://200.19.33.250:9001/swagger.html

## Remains to do
* Create the address book in the JSF interface
* Validate domain objects in Spring Boot using Hibernate Validator
* Develop automated Spring Boot tests

## Project properties and requirements: 
* Java 11
* Spring Boot 2.5.0
* Apache MyFaces 2.2.12
* PrimeFaces 10.0.0
* Project lombok 1.12.20
* HSQLDB (HyperSQL DataBase) 2.6.0
* Flyway 7.8.2
