package com.alexa.review;

import com.alexa.review.entities.Review;
import jdk.jfr.Enabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ReviewApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;
	/*@Test
	public void testRatting() throws Exception{
		//arrange

		//act
		//restTemplate.getForEntity("/reviews", Review.class);
		//restTemplate.getForEntity("/", Review.class, )

	}*/

}
