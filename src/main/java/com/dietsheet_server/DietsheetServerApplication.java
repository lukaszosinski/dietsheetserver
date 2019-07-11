package com.dietsheet_server;




import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication(exclude = {
        org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class
})
public class DietsheetServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(DietsheetServerApplication.class, args);
    }

    @Bean
    public Module datatypeHibernateModule() {
        return new Hibernate5Module();
    }

}
