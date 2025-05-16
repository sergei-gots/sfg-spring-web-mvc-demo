package guru.springframework.sfg_mvc_demo.services;

import guru.springframework.sfg_mvc_demo.domain.Customer;
import guru.springframework.sfg_mvc_demo.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by sergei on 16/05/2025
 */
@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public List<Customer> findAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Customer findCustomerById(Long id) {

        return customerRepository.findById(id).orElseThrow();
    }
}
