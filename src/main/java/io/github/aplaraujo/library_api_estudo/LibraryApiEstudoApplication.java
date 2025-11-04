package io.github.aplaraujo.library_api_estudo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing // Auditoria do JPA
public class LibraryApiEstudoApplication {

	public static void main(String[] args) {

        SpringApplication.run(LibraryApiEstudoApplication.class, args);

	}


}
