package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.DAO.UserDAO;
import ru.kata.spring.boot_security.demo.model.User;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class UserService implements UserDetailsService {

    private final UserDAO userDAO;


    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userDAO.findByUsername(username);
    }

    public void saveUser(User user)  {
        userDAO.saveUser(user);
    }

    public User findUserById(Long userId) {
        return userDAO.findUserById(userId);
    }

        public List<User> allUsers() {
        return userDAO.getUsers();
    }

        public void deleteUser(Long userId) {
        userDAO.deleteUser(userId);
    }
    public List<User> usergtList(Long idMin) {
    return userDAO.usergtList(idMin);
}



}
//    public boolean saveUser(User user) {
//        if (user.getId() == null) {
//            if (userRepository.findByUsername(user.getUsername()) != null) {
//                return false;
//            }
//            user.setRoles(Collections.singleton(new Role(1L, "ROLE_USER")));
//            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
//            userRepository.save(user);
//        } else {
//            User existingUser = userRepository.findById(user.getId()).orElse(null);
//            if (existingUser != null) {
//                existingUser.setUsername(user.getUsername());
//                existingUser.setEmail(user.getEmail());
//                if (!user.getPassword().equals(existingUser.getPassword())) {
//                    existingUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
//                }
//                existingUser.setRoles(user.getRoles());
//                userRepository.save(existingUser);
//            } else {
//                return false;
//            }
//        }
//        return true;
//    }

//    public User findUserById(Long userId) {
//        Optional<User> userFromDb = userRepository.findById(userId);
//        return userFromDb.orElse(new User());
//    }

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user = userRepository.findByUsername(username);
//
//        if (user == null) {
//            throw new UsernameNotFoundException("User not found");
//        }
//
//        return user;
//    }

//    public List<User> allUsers() {
//        return userRepository.findAll();
//    }


//    public boolean deleteUser(Long userId) {
//        if (userRepository.findById(userId).isPresent()) {
//            userRepository.deleteById(userId);
//            return true;
//        }
//        return false;
//    }

//public List<User> usergtList(Long idMin) {
//    return em.createQuery("SELECT u FROM User u WHERE u.id > :paramId", User.class)
//            .setParameter("paramId", idMin).getResultList();
//}
