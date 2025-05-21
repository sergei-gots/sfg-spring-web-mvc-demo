package guru.springframework.sfg_mvc_demo.controllers;

import guru.springframework.sfg_mvc_demo.domain.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by sergei on 16/05/2025
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomerControllerIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testCreateCustomer() throws  Exception {

        Customer customerToSave = Customer.builder()
                .firstname("Jesse")
                .lastname("Porter")
                .build();

        ResponseEntity<Customer> responseEntity = restTemplate.postForEntity(
                CustomerController.BASE_URL,
                customerToSave,
                Customer.class);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());

        Customer savedCustomer = responseEntity.getBody();
        assertNotNull(savedCustomer.getId());
        assertEquals(customerToSave.getFirstname(), savedCustomer.getFirstname());
        assertEquals(customerToSave.getLastname(), savedCustomer.getLastname());

    }

    @Test
    void testGetAllCustomers() throws  Exception {

        ResponseEntity<Customer[]> responseEntity = restTemplate.getForEntity(CustomerController.BASE_URL, Customer[].class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(3, responseEntity.getBody().length);

    }

    @Test
    void testGetCustomer() throws  Exception {

        ResponseEntity<Customer> responseEntity =
                restTemplate.getForEntity(CustomerController.BASE_URL + "/1", Customer.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());

        Customer actualCustomer = responseEntity.getBody();
        assertEquals(1L, actualCustomer.getId());
        assertEquals("Michael", actualCustomer.getFirstname());
        assertEquals("Westen", actualCustomer.getLastname());

    }

    @Test
    void whenGetCustomer42_thenReturn404() throws  Exception {

        ResponseEntity<String> responseEntity =
                restTemplate.getForEntity(CustomerController.BASE_URL + "/42", String.class);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertTrue(responseEntity.getBody().contains("not found"));

    }

}
