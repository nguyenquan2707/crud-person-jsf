# crud-person
Study Project: Spring Boot, JSF, API REST

Link to the JSF interface:
> http://34.75.238.108/

PS: The deployment was performed on Google Cloud on a VM f1-micro (1 vCPU, 0.6 GB of memory). So don't expect system performance :)

## Spring REST API

Documentation is an essential part of building REST APIs.  SpringDoc is a tool that simplifies the generation and maintenance of API docs based on the OpenAPI 3 specification for Spring Boot 1.x and 2.x applications.

We can access the documents in JSON format at:
> http://34.75.238.108/api-docs/

The OpenAPI definitions are in JSON format by default. For yaml format, we can obtain the definitions at:
> http://34.75.238.108/api-docs.yaml

To view and interact with the API’s resources without having any of the implementation logic in place, try Swagger UI:
> http://34.75.238.108/swagger.html

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
