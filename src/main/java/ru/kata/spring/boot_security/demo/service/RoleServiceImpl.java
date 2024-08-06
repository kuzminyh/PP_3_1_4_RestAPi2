package ru.kata.spring.boot_security.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.DAO.RoleDAO;
import ru.kata.spring.boot_security.demo.model.Role;

import java.util.List;
import java.util.Set;

@Service
@Transactional(readOnly = true)
public class RoleServiceImpl implements RoleService {


    private final RoleDAO roleDAO;

    public RoleServiceImpl(RoleDAO roleDAO) {
        this.roleDAO = roleDAO;
    }


    @Override
    public List<Role> getRoles() {
        return roleDAO.getRoles();
    }

    @Override
    @Transactional
    public void saveRole(Role role) {
        roleDAO.saveRole(role);
    }

    @Override
    @Transactional
    public void updateRole(Role role) {
        roleDAO.updateRole(role);
    }

    @Override
    public Role findRoleById(long id) {
        return roleDAO.findRoleById(id);
    }

    @Override
    public void deleteRole(long id) {
        roleDAO.deleteRole(id);
    }

    @Override
    public Role findRoleByName(String roleName) {
        return roleDAO.findRoleByName(roleName);
    }

    @Override
    public Set<Role> findRolesByIds(Set<Long> roleIds) {
        return roleDAO.findRolesByIds(roleIds);
    }
}
