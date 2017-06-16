package integration;

import com.Main;
import com.dto.UserModel;
import org.apache.http.HttpStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT, classes = Main.class)
public class MainControllerIntegrationTest {

    @Autowired
    private TestRestTemplate template;

    @Test
    public void successfullyFetchUsers() {
        ResponseEntity<UserModel[]> response = template.getForEntity("/user", UserModel[].class);

        assertThat(response.getStatusCode().value(), is(HttpStatus.SC_OK));
        assertThat(response.getHeaders().getContentType(), is(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    public void successfullySaveUser() {
        UserModel userModel = new UserModel(1L, "testUser");

        ResponseEntity<Long> response = template.postForEntity("/user", userModel, Long.class);

        assertThat(response.getStatusCode().value(), is(HttpStatus.SC_OK));
        assertThat(response.getBody(), is(1L));

        ResponseEntity<UserModel[]> fetchResult = template.getForEntity("/user", UserModel[].class);

        assertThat(fetchResult.getStatusCode().value(), is(HttpStatus.SC_OK));
        assertThat(fetchResult.getHeaders().getContentType(), is(MediaType.APPLICATION_JSON_UTF8));
        assertThat(fetchResult.getBody().length, is(2));
    }

    @Test
    public void failWith404WhenUserNotFound() {

        ResponseEntity<String> response = template.getForEntity("/user/44", String.class);

        assertThat(response.getStatusCode().value(), is(HttpStatus.SC_NOT_FOUND));
        assertThat(response.getBody(), containsString("not found"));
        System.out.println(response.getBody());
    }

}
