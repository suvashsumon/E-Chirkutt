package com.suvash.chirkutt.CommandLineRunner;

import com.suvash.chirkutt.Model.Role;
import com.suvash.chirkutt.Repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RoleInitializer {

    @Bean
    CommandLineRunner initRoles(RoleRepository roleRepository) {
        return args -> {
            // Check if roles already exist
            if (roleRepository.count() == 0) {
                // Insert default roles
                roleRepository.save(new Role("ROLE_USER"));
                roleRepository.save(new Role( "ROLE_ADMIN"));
                System.out.println("Default roles inserted into the database!");
            } else {
                System.out.println("Roles already exist. Skipping initialization.");
            }
        };
    }
}
