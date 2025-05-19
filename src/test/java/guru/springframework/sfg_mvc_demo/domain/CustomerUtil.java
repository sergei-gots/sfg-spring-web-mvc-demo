package guru.springframework.sfg_mvc_demo.domain;

/**
 * Created by sergei on 19/05/2025
 */
public abstract class CustomerUtil {

    public static Customer buildCustomer(long id, String firstname, String lastname) {
        return Customer.builder()
                .id(id)
                .firstname(firstname)
                .lastname(lastname)
                .build();
    }
}
