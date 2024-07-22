package ru.kata.spring.boot_security.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kata.spring.boot_security.demo.Model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
