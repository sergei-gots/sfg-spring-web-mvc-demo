package guru.springframework.sfg_mvc_demo.repositories;

import guru.springframework.sfg_mvc_demo.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
