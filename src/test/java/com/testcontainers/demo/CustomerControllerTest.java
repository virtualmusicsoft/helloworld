package com.testcontainers.demo;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasSize;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.OracleContainer;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(
        classes = SpringBootRestApiTestingGuideApplication.class,
        webEnvironment = WebEnvironment.RANDOM_PORT
)
@ActiveProfiles("test")

public class CustomerControllerTest {
  @LocalServerPort
  private Integer port;

  static OracleContainer db = new OracleContainer("gvenzl/oracle-xe:21-slim-faststart")
          .withDatabaseName("testDB")
          .withUsername("testUser")
          .withPassword("testPassword");

  @BeforeAll
  static void beforeAll() {
    db.start();
  }

  @AfterAll
  static void afterAll() {
    db.stop();
  }

  @DynamicPropertySource
  static void configureProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", db::getJdbcUrl);
    registry.add("spring.datasource.username", db::getUsername);
    registry.add("spring.datasource.password", db::getPassword);
  }

  @Autowired
  CustomerRepository customerRepository;

  @BeforeEach
  void setUp() {
    RestAssured.baseURI = "http://localhost:" + port;
  }

  @Test
  void shouldGetAllCustomers() {
    List<Customer> customers = List.of(
            new Customer(null, "John", "john@mail.com"),
            new Customer(null, "Dennis", "dennis@mail.com")
    );
    customerRepository.saveAll(customers);

    given()
            .contentType(ContentType.JSON)
            .when()
            .get("/api/customers")
            .then()
            .statusCode(200)
            .body(".", hasSize(4));
  }

}

