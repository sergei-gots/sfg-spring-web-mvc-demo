package guru.springframework.sfg_mvc_demo.services;

import guru.springframework.sfg_mvc_demo.domain.Customer;

import java.util.List;

/**
 * Created by sergei on 16/05/2025
 */
public interface CustomerService {

    Customer createCustomer(Customer customer);

    List<Customer> findAllCustomers();

    Customer findCustomerById(Long id);
}
