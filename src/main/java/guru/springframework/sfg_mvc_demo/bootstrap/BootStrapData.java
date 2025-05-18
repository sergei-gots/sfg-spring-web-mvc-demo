package guru.springframework.sfg_mvc_demo.bootstrap;

import guru.springframework.sfg_mvc_demo.domain.Customer;
import guru.springframework.sfg_mvc_demo.repositories.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Created by sergei on 16/05/2025
 */
@Component
@Slf4j
public class BootStrapData implements CommandLineRunner {

    private final CustomerRepository customerRepository;

    public BootStrapData(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        log.info("Loading customers");

        customerRepository.save(createCustomer("Michael", "Westen"));
        customerRepository.save(createCustomer("Fiona", "Glennane"));
        customerRepository.save(createCustomer("Sam", "Axe"));

        log.info("{} customers have been successfully created and saved to the database", customerRepository.count());

    }

    private Customer createCustomer(String firstName, String lastName) {

        return Customer.builder()
                .firstname(firstName)
                .lastname(lastName)
                .build();
    }

}
