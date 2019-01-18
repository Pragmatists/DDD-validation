package com.ddd.validation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpStatus.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class ValidationApplicationTests {

	@Autowired
	private TestRestTemplate template;

	@Test
	public void registerUserByEmail() {

		// given:

		// when:
		ResponseEntity<Void> response = template.postForEntity("/users/", "email@email.com", Void.class);

		// then:
		assertThat(response.getStatusCode()).isEqualTo(CREATED);
	}

	@Test
	public void failUserRegistrationForInvalidEmail() {

		// given:

		// when:
		ResponseEntity<Void> response = template.postForEntity("/users/", "email.with.two.ats@@email.com", Void.class);

		// then:
		assertThat(response.getStatusCode()).isEqualTo(BAD_REQUEST);
	}

	@Test
	public void retrievesRegisteredUserByEmail() {

		// given:
		String userId = createUserFor("email@email.com");

		// when:
		ResponseEntity<String> response = template.getForEntity("/users/" + userId, String.class);

		// then:
		assertThat(response.getStatusCode()).isEqualTo(OK);
		assertThat(response.getBody()).isEqualTo("email@email.com");
	}

	private String createUserFor(String email) {
		return template.postForEntity("/users/", email, String.class).getBody();
	}
}

