package guru.springframework.sfg_mvc_demo.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.sfg_mvc_demo.domain.Customer;
import guru.springframework.sfg_mvc_demo.exception.GlobalExceptionHandler;
import guru.springframework.sfg_mvc_demo.services.CustomerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.NoSuchElementException;

import static guru.springframework.sfg_mvc_demo.domain.CustomerUtil.buildCustomer;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 'Controller slice' Spring MVC Test
 * Created by sergei on 19/05/2025
 */
@WebMvcTest(CustomerController.class)
@Import(GlobalExceptionHandler.class) // если надо
class CustomerControllerWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateCustomer() throws Exception {
        Customer createdCustomer = buildCustomer(1, "Sam", "Axe");

        when(customerService.createCustomer(createdCustomer))
                .thenReturn(createdCustomer);

        mockMvc.perform(post(CustomerController.BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(objectMapper.writeValueAsString(createdCustomer))
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.firstname").value("Sam"))
                .andExpect(jsonPath("$.lastname").value("Axe"));
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
                .andExpect(jsonPath("$.id").value(7L))
                .andExpect(jsonPath("$.firstname").value("Agent"))
                .andExpect(jsonPath("$.lastname").value("Pearce"));
    }

    @Test
    void whenGetCustomerById42_thenReturn404() throws Exception {

        when(customerService.findCustomerById(42L))
                .thenThrow(new NoSuchElementException("Customer with Id 42 not found"));

        mockMvc.perform(get(CustomerController.BASE_URL + '/' + 42))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", containsString("not found")));
    }

}
