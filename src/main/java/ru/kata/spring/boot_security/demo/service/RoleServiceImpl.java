package ru.kata.spring.boot_security.demo.service;

import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.DAO.RoleDAO;
import ru.kata.spring.boot_security.demo.model.Role;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
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
    public void saveRole(Role role) {
        roleDAO.saveRole(role);
    }

    @Override
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
}
