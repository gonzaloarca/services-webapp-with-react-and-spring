package ar.edu.itba.paw.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "user_role", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "role_id"})})
public class UserRole implements Serializable {
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "user_role_user_id_fkey"))
    private UserAuth userAuth;

    @Id
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "role_id")
    private Role role;

    /* default */UserRole() {
    }

    public UserRole(Role role) {
        this.role = role;
    }

    public UserAuth getUserAuth() {
        return userAuth;
    }

    public Role getRole() {
        return role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRole userRole = (UserRole) o;
        return role == userRole.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(role);
    }

    public enum Role {
        CLIENT("ROLE_CLIENT"),
        PROFESSIONAL("ROLE_PROFESSIONAL");

        private final String name;

        Role(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
