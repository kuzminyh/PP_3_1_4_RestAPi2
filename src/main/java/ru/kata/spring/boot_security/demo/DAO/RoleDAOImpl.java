package ru.kata.spring.boot_security.demo.DAO;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class RoleDAOImpl implements RoleDAO {

    @PersistenceContext
    private EntityManager em;


    @Override
    public List<Role> getRoles() {
        return em.createQuery("from Role", Role.class).getResultList();
    }

    @Override
    public void saveRole(Role role) {
        em.persist(role);
    }

    @Override
    public void updateRole(Role role) {
        em.merge(role);
    }

    @Override
    public Role findRoleById(long id) {
        return em.find(Role.class, id);
    }

    @Override
    public void deleteRole(long id) {
        em.remove(em.find(Role.class, id));
    }

    @Override
    public Role findRoleByName(String roleName) {
        return em.createQuery("SELECT r FROM Role r WHERE r.name = :roleName", Role.class)
                .setParameter("roleName", roleName)
                .getSingleResult();
    }

}
