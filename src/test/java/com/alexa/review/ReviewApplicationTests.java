package com.alexa.review;

import com.alexa.review.entities.Review;
import com.alexa.review.models.AuthenticationRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ReviewApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    private String jwt;

    @Test
    public void givenAuthRequestIntegrationTest_shouldSucceedWith200() throws Exception {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        AuthenticationRequest auth = new AuthenticationRequest("root", "root123");
        HttpEntity<AuthenticationRequest> request = new HttpEntity<AuthenticationRequest>(auth, httpHeaders);
        ResponseEntity<String> result = restTemplate.postForEntity("/authenticate", request, String.class);
        //ResponseEntity.ok(new AuthenticationResponse(jwt))
        //ObjectMapper mapper = new ObjectMapper();
        assertEquals(HttpStatus.OK, result.getStatusCode());
        //AuthenticationResponse authenticationResponse = mapper.readValue(result.getBody(), AuthenticationResponse.class);
        jwt = result.getBody().replaceAll("\"", "").split(":")[1].replace("}", "");
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(jwt);

        //Accept review
        HttpHeaders httpHeaders1 = new HttpHeaders();
        httpHeaders1.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders1.setBearerAuth(jwt);
        Review review = new Review(100L, "Cannot fix connection glitches without this", "omgzero", "iTunes", 5, "Cant log in", "Amazon Alexa", new Date());
        HttpEntity<Review> request1 = new HttpEntity<Review>(review, httpHeaders1);
        ResponseEntity<String> result1 = restTemplate.exchange("/api/v1/alexa/reviews", HttpMethod.PUT, request1, String.class);
        assertEquals(HttpStatus.OK, result1.getStatusCode());
        assertTrue(result1.getBody().contains("omgzero"));

        //fetch review
        HttpEntity<Review> request2 = new HttpEntity<Review>(httpHeaders1);
        ResponseEntity<String> result2 = restTemplate.exchange("/api/v1/alexa/reviews", HttpMethod.GET, request2, String.class);
        assertEquals(HttpStatus.OK, result2.getStatusCode());
        assertTrue(result2.getBody().contains("omgzero"));

        //fetch reviews by filter works, try it with start_date
        HttpEntity<Review> request20 = new HttpEntity<Review>(httpHeaders1);
        ResponseEntity<String> result20 = restTemplate.exchange("/api/v1/alexa/reviews?filter=\"start_date:2022-01-13\"", HttpMethod.GET, request20, String.class);
        assertEquals(HttpStatus.OK, result20.getStatusCode());
        assertTrue(result20.getBody().contains("omgzero"));

        //with all the filters
        HttpEntity<Review> request21 = new HttpEntity<Review>(httpHeaders1);
        ResponseEntity<String> result21 = restTemplate.exchange("/api/v1/alexa/reviews?filter=\"start_date:2022-01-13,end_date:2023-09-24,review_source:iTunes,rating:5\"", HttpMethod.GET, request21, String.class);
        assertEquals(HttpStatus.OK, result21.getStatusCode());
        assertTrue(result21.getBody().contains("omgzero"));


        //get monthly avg rating per store
        HttpEntity<Review> request3 = new HttpEntity<Review>(httpHeaders1);
        ResponseEntity<String> result3 = restTemplate.exchange("/api/v1/alexa/reviews/ratings/monthly_avg", HttpMethod.GET, request3, String.class);
        assertEquals(HttpStatus.OK, result3.getStatusCode());
        assertTrue(result3.getBody().contains("iTunes"));

        HttpEntity<Review> request4 = new HttpEntity<Review>(httpHeaders1);
        ResponseEntity<String> result4 = restTemplate.exchange("/api/v1/alexa/reviews/ratings/monthly_avg/iTunes", HttpMethod.GET, request4, String.class);
        assertEquals(HttpStatus.OK, result4.getStatusCode());
        assertTrue(result4.getBody().contains("2022-09"));

        //total rating
        HttpEntity<Review> request5 = new HttpEntity<Review>(httpHeaders1);
        ResponseEntity<String> result5 = restTemplate.exchange("/api/v1/alexa/reviews/ratings/total_count", HttpMethod.GET, request5, String.class);
        assertEquals(HttpStatus.OK, result5.getStatusCode());
        assertTrue(result5.getBody().contains("5"));

        HttpEntity<Review> request6 = new HttpEntity<Review>(httpHeaders1);
        ResponseEntity<String> result6 = restTemplate.exchange("/api/v1/alexa/reviews/5/total_count", HttpMethod.GET, request6, String.class);
        assertEquals(HttpStatus.OK, result6.getStatusCode());
    }

    @Test
    public void contextLoads() {
    }

}
