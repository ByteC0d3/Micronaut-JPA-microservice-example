package ru.kazbo.micronaut.jpa;

import io.micronaut.runtime.EmbeddedApplication;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import jakarta.inject.Inject;
import ru.kazbo.micronaut.jpa.model.User;

@MicronautTest
class MicronautDemoTest {

    @Inject
    EmbeddedApplication<?> application;
	
	@Inject
	@Client("/")
	HttpClient client; 

	/*
	 *	Test for running server
	 */
    @Test
    void testItWorks() {
        Assertions.assertTrue(application.isRunning());
    }
	
	/*
	 *	Test for adding user from JSON into database by REST
	 */
	@Test
	void testAddingToAndRemovingFrom() {
		String 	name = "Bred", 	newName = "Duke";
		int 	age = 23, 		newAge = 21;
		
		// post user into database
		User user = getUser(name, age);
		Assertions.assertEquals(name, user.getName());
		Assertions.assertEquals(age, user.getAge());
		
		// update posted user
		User updatingUser = updateUser(user.getId(), newName, newAge);
		Assertions.assertEquals(newAge, updatingUser.getAge());
		Assertions.assertEquals(newName, updatingUser.getName());
		
		// remove user and check status
		Assertions.assertEquals(200, removeUserAndGetStatusResponse(updatingUser.getId()));
	}

	/*
	 *	Add new user to database by rest-api
	 */
	private User getUser(String name, int age) {
		String jsonedUser = """
					{
						"name":"%s", 
						"age":"%d"
					}""".formatted(name, age);
		return client.toBlocking().retrieve(buildJsonTypedRequest("/users/add", jsonedUser), User.class);
	}
	
	private User updateUser(Long id, String newName, int newAge) {
		String jsonedUpdatingUser = """
				{
					"id":"%d",
					"name":"%s",
					"age":"%d"
				}""".formatted(id, newName, newAge);
		return client.toBlocking().retrieve(buildJsonTypedRequest("/users/update", jsonedUpdatingUser), User.class);
	}
	
	/*
	 *	remove user from database by rest-api and id
	 */
	private int removeUserAndGetStatusResponse(Long id) {
		String jsonedUserId = """
				{
					"id":"%d"
				}""".formatted(id);
		HttpRequest userFromDatabase = buildJsonTypedRequest("/users/remove", jsonedUserId);
		return client.toBlocking().exchange(userFromDatabase).getStatus().getCode();
		
	}
	
	/*
	 *	Create request for JSON-parameters
	 */
	private HttpRequest buildJsonTypedRequest(String url,String data) {
		HttpRequest jsonRequest = HttpRequest.POST(url, data)
				.header("Accept","application/json")
				.header("Content-Type","application/json");
		return jsonRequest;
	}
}
