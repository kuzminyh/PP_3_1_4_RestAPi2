package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.Role;

import java.util.List;
import java.util.Set;

public interface RoleService {

    List<Role> getRoles();

    void saveRole(Role role);

    void updateRole(Role role);

    Role findRoleById(long id);

    void deleteRole(long id);

    Role findRoleByName(String roleName);

    Set<Role> findRolesByIds(Set<Long> roleIds);

}
