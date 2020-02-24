                                               # Target Interview

## __Case Study:  myRetail service__

myRetail is a rapidly growing company with HQ in Richmond, VA and over 200 stores across the east coast. myRetail wants to make its internal data available to any number of client devices, from retail.com to native mobile apps. 

The goal for this exercise is to create an end-to-end Proof-of-Concept for a products API, which will aggregate product data from multiple sources and return it as JSON to the caller. 
Your goal is to create a RESTful service that can retrieve product and price details by ID. The URL structure is up to you to define, but try to follow some sort of logical convention.
Build an application that performs the following actions: 
Responds to an HTTP GET request at /products/{id} and delivers product data as JSON (where {id} will be a number. 

Example product ID: 13860428
Example response: {"id":13860428,"name":"The Big Lebowski (Blu-ray) (Widescreen)","current_price":{"value": 13.49,"currency_code":"USD"}}

Performs an HTTP GET to retrieve the product name from an external API. (For this exercise the data will come from redsky.target.com, but let’s just pretend this is an internal resource hosted by myRetail) 

Example: http://redsky.target.com/v2/pdp/tcin/13860428?excludes=taxonomy,price,promotion,bulk_ship,rating_and_review_reviews,rating_and_review_statistics,question_answer_statistics

Reads pricing information from a NoSQL data store and combines it with the product id and name from the HTTP request into a single response. 

BONUS: Accepts an HTTP PUT request at the same path (/products/{id}), containing a JSON request body similar to the GET response, and updates the product’s price in the data store. 

*********************************************************************************************************************************
# __Solution:__

## __MyRetail API Solution provides the ability to:__

<ol>
  <li>Retrieve product and price information by Product Id.</li>
  <li>Update the price information in the database.</li>
  <li>Secure API with basic authentication.</li>
  <li>One rest end point is not recure.</li>	
  <li>Implement Swagger2 for API documentation</li>
</ol>
All the end points are totally secure in this application. I have implemented basic security and method level security as well. Update resource can be accessed by admin/admin user only.

                                   Method               Request                
                                     GET              /products/{id}             
                                     PUT              /products/{id}            
					                 GET              /products                   

###### __Technology Stack:__

1. Spring Boot : 
	https://start.spring.io/
	https://spring.io/guides/gs/serving-web-content/ 
2. Feign:
Declarative REST Client: Feign creates a dynamic implementation of an interface decorated with JAX-RS or Spring MVC annotations.
	https://cloud.spring.io/spring-cloud-netflix/ 
3. MongoDB:
	https://www.mongodb.com/what-is-mongodb 
4. Gradle:
	https://gradle.org/guides/
5. Mokito/Junit:
	http://site.mockito.org/ 
	
###### __Setup instructions:__

1. Java 1.8
2. Install Mongo DB: https://docs.mongodb.com/manual/tutorial/install-mongodb-on-os-x/
3. Github: 
a) Download as a ZIP file   OR
b) Clone the git project from git-bash or command prompt (You must have git setup)
4. Import the project into intellij –   File->import

Run mongo DB from the command prompt.  And test  ---  http://localhost:27017/  (default port)
Go to the project folder and trigger the command: ./gradlew run

###  Swagger2 documentation path
http://localhost:8080/swagger-ui.html

