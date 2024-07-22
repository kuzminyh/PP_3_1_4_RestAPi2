package ru.kata.spring.boot_security.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kata.spring.boot_security.demo.Model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
