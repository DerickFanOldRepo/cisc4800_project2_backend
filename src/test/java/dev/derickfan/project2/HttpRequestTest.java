package dev.derickfan.project2;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HttpRequestTest {

    @LocalServerPort
    private int port;



    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void test() throws Exception {
        String url = "http://localhost:" + port;
        assertThat(this.restTemplate.getForObject(url + "/test",
                String.class)).contains("Hello world!");
    }

    @Test
    public void testGetAllUsers() throws Exception {
        String url = "http://localhost:" + port;
        assertThat(this.restTemplate.getForObject(url + "/getAllUsers",
                String.class));
    }

    @Test
    public void testAddUser() throws Exception {
        String url = "http://localhost:" + port;
        assertThat(this.restTemplate.getForObject(url +"/addUser?username=derickfan&email=derickfan@gmail.com&password=password",
                String.class));
    }


}
