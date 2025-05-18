package guru.springframework.sfg_mvc_demo.controllers;

import guru.springframework.sfg_mvc_demo.domain.Customer;
import guru.springframework.sfg_mvc_demo.exception.GlobalExceptionHandler;
import guru.springframework.sfg_mvc_demo.services.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.NoSuchElementException;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static  org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CustomerControllerUnitTest {

    private MockMvc mockMvc;

    @InjectMocks
    private CustomerController customerController;

    @Mock
    private CustomerService customerService;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(customerController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void testCreateCustomer() {

    }

    @Test
    void testGetAllCustomers() throws Exception {

        when(customerService.findAllCustomers())
                .thenReturn(List.of(
                        buildCustomer(1, "Sam", "Axe")
                ));

        mockMvc.perform(get(CustomerController.BASE_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].firstname").value("Sam"))
                .andExpect(jsonPath("$[0].lastname").value("Axe"));

    }

    @Test
    void testGetCustomerById() throws Exception {

        when(customerService.findCustomerById(7L))
                .thenReturn(
                        buildCustomer(7, "Agent", "Pearce")
                );

        mockMvc.perform(get(CustomerController.BASE_URL + '/' + 7))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].firstname").value("Agent"))
                .andExpect(jsonPath("$[0].lastname").value("Pearce"));
    }

    @Test
    void whenGetCustomerById42_thenReturn404() throws Exception {

        when(customerService.findCustomerById(42L))
                .thenThrow(new NoSuchElementException("Customer with Id 42 not found"));

        mockMvc.perform(get(CustomerController.BASE_URL + '/' + 42))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", containsString("not found")));
    }

    private static Customer buildCustomer(long id, String firstname, String lastname) {
        return Customer.builder()
                .id(id)
                .firstname(firstname)
                .lastname(lastname)
                .build();
    }
}