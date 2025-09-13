package uk.vinch.redirectsvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(
        scanBasePackages = {"uk.vinch.redirectsvc", "uk.vinch.shortenersvc"}
)
@EnableJpaRepositories(basePackages = "uk.vinch.shortenersvc.repo")
@EntityScan(basePackages = "uk.vinch.shortenersvc.model")
public class RedirectSvcApplication {
    public static void main(String[] args) {
        SpringApplication.run(RedirectSvcApplication.class, args);
    }
}

