package ru.kata.spring.boot_security.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.validator.constraints.Range;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

      @Column(name = "surname")
    private String surname;
      @Column(name = "age")
    private int age;

    @NotEmpty(message = "Username should not be empty.")
    @Column(name = "username", unique = true)
    private String username;

    @NotEmpty(message = "Password should not be empty.")
    @Column(name = "password")
    private String password;

    @ManyToMany(fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<ru.kata.spring.boot_security.demo.entity.Role> roles;

    public User() { }


    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSurname() { return surname; }
    public void setSurname(String surname) { this.surname = surname; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public Set<ru.kata.spring.boot_security.demo.entity.Role> getRoles() { return roles; }
    public void setRoles(Set<ru.kata.spring.boot_security.demo.entity.Role> roles) { this.roles = roles; }

    @Override
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    @Override
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { return getRoles(); }

}
