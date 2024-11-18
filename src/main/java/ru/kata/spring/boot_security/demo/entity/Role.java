package ru.kata.spring.boot_security.demo.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.Objects;

@Entity
@Data
@Table(name = "roles")
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "role")
    private String roleName;

    public Role() { }

    public Role(int id) { this.id = id; }

    public Role(int id, String roleName) { this.id = id; this.roleName = roleName; }


    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getRoleName() { return roleName; }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public String getAuthority() { return getRoleName(); }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Role role = (Role) o;

        if (id != role.id) return false;
        return Objects.equals(roleName, role.roleName);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (roleName != null ? roleName.hashCode() : 0);
        return result;
    }
}
