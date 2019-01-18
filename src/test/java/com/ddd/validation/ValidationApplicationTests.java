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

	private static final String STRONG_PASSWORD = "STRONG_PASSWORD";

	@Autowired
	private TestRestTemplate template;

	@Test
	public void registerUserByEmail() {
		// given:
		UserCreationRequest userCreationRequest = new UserCreationRequest("email4@email.com", STRONG_PASSWORD);

		// when:
		ResponseEntity<Void> response = template.postForEntity("/users/", userCreationRequest, Void.class);

		// then:
		assertThat(response.getStatusCode()).isEqualTo(CREATED);
	}

	@Test
	public void failUserRegistrationForInvalidEmail() {
		// given:
		UserCreationRequest userCreationRequest = new UserCreationRequest("email.with.two.ats@@email.com", STRONG_PASSWORD);

		// when:
		ResponseEntity<Void> response = template.postForEntity("/users/", userCreationRequest, Void.class);

		// then:
		assertThat(response.getStatusCode()).isEqualTo(BAD_REQUEST);
	}

	@Test
	public void retrievesUserEmailByUserId() {

		// given:
		String userId = createUserFor("email2@email.com");

		// when:
		ResponseEntity<String> response = template.getForEntity("/users/" + userId, String.class);

		// then:
		assertThat(response.getStatusCode()).isEqualTo(OK);
		assertThat(response.getBody()).isEqualTo("email2@email.com");
	}

	@Test
	public void failsOnAttemptToCreateUserForAlreadyTakenEmail() {

		// given:
		createUserFor("email3@email.com");

		// when:
		UserCreationRequest userCreationRequest = new UserCreationRequest("email3@email.com", STRONG_PASSWORD);
		ResponseEntity<String> response = template.postForEntity("/users/", userCreationRequest, String.class);

		// then:
		assertThat(response.getStatusCode()).isEqualTo(BAD_REQUEST);
	}

	@Test
	public void failsOnAttemptToCreateUserWithTooWeakPassword() {
		// when:
		UserCreationRequest userCreationRequest = new UserCreationRequest("email4@email.com", "WEAK");

		ResponseEntity<String> response = template.postForEntity("/users/", userCreationRequest, String.class);

		// then:
		assertThat(response.getStatusCode()).isEqualTo(BAD_REQUEST);
	}

	private String createUserFor(String email) {
		UserCreationRequest userCreationRequest = new UserCreationRequest(email, STRONG_PASSWORD);
		ResponseEntity<String> response = template.postForEntity("/users/", userCreationRequest, String.class);
		assertThat(response.getStatusCode()).isEqualTo(CREATED);
		return response.getBody();
	}

	public static class UserCreationRequest {

		public final String email;
		public final String password;

		public UserCreationRequest(String email, String password) {
			this.email = email;
			this.password = password;
		}
	}
}

