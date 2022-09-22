# alexa-review-service
 alexa-review-service

# Initial Setup
 create database review_service;
 
 Run the jar
 load attached alexa.sql
 
 # APIs
1. For 3rd party browser application shall be able to access the service, using JWT
 POST http://localhost:8094/authenticate
Header=> Content-Type:application/json
Body=>
{
	"username" : "root",
	"password" : "root123"
}
Response:
jwt:
eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJyb290IiwiZXhwIjoxNjYzOTAxNDUxLCJpYXQiOjE2NjM4NjU0NTF9.0dQZ-EktOV5k9Qbst-hszsz3qTLleAjye5KUVlch63g

2. For all other apis 
Header=>
Content-Type:application/json
Authorization:Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJyb290IiwiZXhwIjoxNjYzOTAxNDUxLCJpYXQiOjE2NjM4NjU0NTF9.0dQZ-EktOV5k9Qbst-hszsz3qTLleAjye5KUVlch63g

3.Accepts reviews and stores reviews 
PUT http://localhost:8094/api/v1/alexa/reviews
Header => Content-Type:application/json
Body=>
{"review":"review1","author":"author1","reviewSource":"iTunes1","rating":1,"title":"Excelente","productName":"Amazon Alexa","reviewedDate": "2022-01-12T02:27:03.000Z"}

4. Allows to fetches reviews, with optional filters
 GET http://localhost:8094/api/v1/alexa/reviews?filter="start_date={}&end_date={}"
Header => Content-Type:application/json
 filtered by date, review_source or rating
 eg:
http://localhost:8094/api/v1/alexa/reviews?filter="start_date:2022-01-13"
http://localhost:8094/api/v1/alexa/reviews?filter="start_date:2022-01-01,end_date:2022-02-02,review_source:iTunes,rating:5"

5. Allows to get average monthly ratings per store(review_source)
GET http://localhost:8094/api/v1/alexa/reviews/ratings/monthly_avg/ 
o/p:
09-2022, 1.5*
08-2022, 2*
07-2022, 3*
GET http://localhost:8094/api/v1/alexa/reviews/review_sources  => returns distinct review_sources
GET http://localhost:8094/api/v1/alexa/reviews/ratings/monthly_avg/{review_source}
GET http://localhost:8094/api/v1/alexa/reviews/ratings/monthly_avg/iTunes

6. Allows to get total ratings for each category(rating). Meaning, how many 5*, 4*, 3* and so on 
  5 => 100 , 4=>12 ,3 =>12 ,2 =>100, 1 =>123
GET http://localhost:8094/api/v1/alexa/reviews/{rating}/total_count
GET http://localhost:8094/api/v1/alexa/reviews/5/total_count
100
GET http://localhost:8094/api/v1/alexa/reviews/ratings/total_count
    5 => 100 , 4=>12 ,3 =>12 ,2 =>100, 1 =>123

