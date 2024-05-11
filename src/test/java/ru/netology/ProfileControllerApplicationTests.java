package ru.netology;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProfileControllerApplicationTests {
    @Autowired
    TestRestTemplate restTemplate;
    private static final GenericContainer<?> devapp = new GenericContainer<>("devapp").withExposedPorts(8080);
    private static final GenericContainer<?> prodapp = new GenericContainer<>("prodapp").withExposedPorts(8081);

    @BeforeAll
    public static void setUp() {
        devapp.start();
        prodapp.start();

    }

    @Test
    void contextLoads() {
        String expectedDevapp = new DevProfile().getProfile();
        String expectedProdapp = new ProductionProfile().getProfile();
        ResponseEntity<String> forEntityDevapp = restTemplate.getForEntity("http://localhost:" + devapp.getMappedPort(8080) + "/profile", String.class);
        ResponseEntity<String> forEntityProdapp = restTemplate.getForEntity("http://localhost:" + prodapp.getMappedPort(8081) + "/profile", String.class);
        assertEquals(expectedDevapp, forEntityDevapp.getBody());
        assertEquals(expectedProdapp, forEntityProdapp.getBody());
    }

}