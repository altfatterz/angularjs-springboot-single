package demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.HttpCookie;
import java.net.URI;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = UiApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
public class UiApplicationTests {

    @Value("${local.server.port}")
    private int port;

    private RestTemplate template = new TestRestTemplate();

    @Test
    public void homePageLoads() {
        ResponseEntity<String> response = template.getForEntity("http://localhost:"
                + port + "/", String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void userEndpointProtected() {
        ResponseEntity<String> response = template.getForEntity("http://localhost:"
                + port + "/user", String.class);
        assertEquals(HttpStatus.FOUND, response.getStatusCode());
    }

    @Test
    public void resourceEndpointProtected() {
        ResponseEntity<String> response = template.getForEntity("http://localhost:"
                + port + "/resource", String.class);
        assertEquals(HttpStatus.FOUND, response.getStatusCode());
    }

    @Test
    public void loginSucceeds() {
        ResponseEntity<String> response = template.getForEntity("http://localhost:"
                + port + "/resource", String.class);
        String csrf = getCsrf(response.getHeaders());
        MultiValueMap<String, String> form = new LinkedMultiValueMap<String, String>();
        form.set("username", "altfatterz@gmail.com");
        form.set("password", "secret");
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-XSRF-TOKEN", csrf);
        headers.put("COOKIE", response.getHeaders().get("Set-Cookie"));
        RequestEntity<MultiValueMap<String, String>> request = new RequestEntity<MultiValueMap<String, String>>(
                form, headers, HttpMethod.POST, URI.create("http://localhost:" + port
                + "/login"));
        ResponseEntity<Void> location = template.exchange(request, Void.class);
        assertEquals("http://localhost:" + port + "/",
                location.getHeaders().getFirst("Location"));
    }

    @Test
    public void rememberMeLogin() {
        ResponseEntity<String> response = template.getForEntity("http://localhost:"
                + port + "/resource", String.class);
        String csrf = getCsrf(response.getHeaders());

        MultiValueMap<String, String> form = new LinkedMultiValueMap<String, String>();
        form.set("username", "zoltan");
        form.set("password", "secret");
        form.set("remember-me", "true");
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-XSRF-TOKEN", csrf);
        headers.put("COOKIE", response.getHeaders().get("Set-Cookie"));
        RequestEntity<MultiValueMap<String, String>> request = new RequestEntity<MultiValueMap<String, String>>(
                form, headers, HttpMethod.POST, URI.create("http://localhost:" + port
                + "/login"));

        ResponseEntity<Void> loginResponse = template.exchange(request, Void.class);

        assertNotNull(getRememberMe(loginResponse.getHeaders()));
    }

    private String getCsrf(HttpHeaders headers) {
        for (String header : headers.get("Set-Cookie")) {
            List<HttpCookie> cookies = HttpCookie.parse(header);
            for (HttpCookie cookie : cookies) {
                if ("XSRF-TOKEN".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    private String getRememberMe(HttpHeaders headers) {
        for (String header : headers.get("Set-Cookie")) {
            List<HttpCookie> cookies = HttpCookie.parse(header);
            for (HttpCookie cookie : cookies) {
                if ("remember-me".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

}