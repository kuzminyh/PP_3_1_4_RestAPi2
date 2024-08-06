package ru.kata.spring.boot_security.demo.DAO;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

@Repository
public interface UserDAO {

    List<User> getUsers();

    void saveUser(User user);

    void updateUser(User user);

    //@Query("SELECT u FROM User u JOIN FETCH u.roles WHERE u.id = :id")
    User findUserById(@Param("id") long id);

    void deleteUser(long id);

   // @Query("SELECT u FROM User u JOIN FETCH u.roles WHERE u.username = :username")
    User findByUsername(@Param("username") String username);

    List<User> usergtList(Long idMin);

}
