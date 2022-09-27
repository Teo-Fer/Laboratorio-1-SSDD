package com.banco.lab01;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.http.ResponseEntity;

import org.springframework.http.HttpMethod;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class Test3 {


    TestRestTemplate restTemplate = new TestRestTemplate();

    HttpHeaders headers = new HttpHeaders();

    @Test
    @Order(1)
    public void testBalance1() throws Exception {

        final String uri = "http://localhost:8081";
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> deposit = restTemplate.exchange(
                uri + "/movements/deposit?amount=1000",
                HttpMethod.POST, entity, String.class);

        ResponseEntity<String> response = restTemplate.exchange(
                uri + "/movements/balance",
                HttpMethod.GET, entity, String.class);

        String expected = "{\"balance\":1000.0}";

        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    @Test
    @Order(2)
    public void testBalance2() throws Exception {

        final String uri = "http://localhost:8082";
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> deposit = restTemplate.exchange(
                uri + "/movements/deposit?amount=2000",
                HttpMethod.POST, entity, String.class);

        ResponseEntity<String> response = restTemplate.exchange(
                uri + "/movements/balance",
                HttpMethod.GET, entity, String.class);

        String expected = "{\"balance\":2000.0}";

        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    @Test
    @Order(3)
    public void testBalance3() throws Exception {

        final String uri = "http://localhost:8083";
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> deposit = restTemplate.exchange(
                uri + "/movements/deposit?amount=3000",
                HttpMethod.POST, entity, String.class);

        ResponseEntity<String> response = restTemplate.exchange(
                uri + "/movements/balance",
                HttpMethod.GET, entity, String.class);

        String expected = "{\"balance\":3000.0}";

        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    @AfterAll
    public void cleanDatabase() throws Exception {
        final String uri1 = "http://localhost:8081";
        final String uri2 = "http://localhost:8082";
        final String uri3 = "http://localhost:8083";

        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        restTemplate.exchange(
                uri1 + "/movements",
                HttpMethod.DELETE, entity, String.class);

        restTemplate.exchange(
                uri2 + "/movements",
                HttpMethod.DELETE, entity, String.class);

        restTemplate.exchange(
                uri3 + "/movements",
                HttpMethod.DELETE, entity, String.class);
    }

}
