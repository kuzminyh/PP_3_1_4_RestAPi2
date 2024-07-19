package ru.kata.spring.boot_security.demo.DAO;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collections;
import java.util.List;


@Repository
public class UserDAOImpl implements UserDAO{

    @PersistenceContext
    private EntityManager em;

    private final BCryptPasswordEncoder encoder;

    public UserDAOImpl(BCryptPasswordEncoder encoder) {
        this.encoder = encoder;
    }


    @Override
    public List<User> getUsers() {
        return em.createQuery("from User", User.class).getResultList();
    }

    @Override
    public void saveUser(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        if(user.getRoles() == null) {
            user.setRoles(Collections.singleton(new Role(1L, "ROLE_USER")));
        }
        em.persist(user);
    }

    @Override
    public void updateUser(User user) {
        User currentUser = em.find(User.class, user.getId());
        if (!encoder.matches(user.getPassword(), currentUser.getPassword())) {
            user.setPassword(encoder.encode(user.getPassword()));
        }
        em.merge(user);
    }

    @Override
    public User findUserById(long id) {
        return em.find(User.class, id);
    }

    @Override
    public void deleteUser(long id) {
        em.remove(em.find(User.class, id));
    }

    @Override
    public User findByUsername(String username) {
        return em.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
                .setParameter("username", username)
                .getSingleResult();
    }

    @Override
    public List<User> usergtList(Long idMin) {
        return em.createQuery("SELECT u FROM User u WHERE u.id > :paramId", User.class)
                .setParameter("paramId", idMin).getResultList();
    }
}
