package ar.edu.itba.paw.models;

import jdk.nashorn.internal.ir.annotations.Immutable;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Immutable
@Table(name = "users")
public class UserAuth {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_user_id_seq")
    @SequenceGenerator(sequenceName = "users_user_id_seq", name = "users_user_id_seq", allocationSize = 1)
    @Column(name = "user_id")
    private long user_id;

    @Column(length = 100, nullable = false, unique = true, name = "user_email")
    private String email;

    @Column(length = 100, nullable = false, name = "user_password")
    private String password;

    @Column(nullable = false, name = "user_is_verified")
    private boolean isVerified;


    @ElementCollection(targetClass = Role.class)
    @Enumerated(EnumType.ORDINAL)
    @CollectionTable(name = "user_role",joinColumns = {@JoinColumn(name = "user_id")},uniqueConstraints = {@UniqueConstraint(columnNames = {"role_id","user_id"},name = "user_role_pkey")})
    @Column(name="role_id")
    private List<Role> roles;

    /*default*/ UserAuth() {
    }

    public UserAuth(long user_id, String email, boolean isVerified, List<Role> roles) {
        this.user_id = user_id;
        this.email = email;
        this.isVerified = isVerified;
        this.roles = roles;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public boolean isVerified() {
        return isVerified;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserAuth userAuth = (UserAuth) o;
        return user_id == userAuth.user_id && email.equals(userAuth.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user_id, email);
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
