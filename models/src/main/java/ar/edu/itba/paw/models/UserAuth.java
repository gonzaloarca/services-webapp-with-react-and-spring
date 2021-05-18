package ar.edu.itba.paw.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
public class UserAuth {

    @Column(length = 100,nullable = false,unique = true,name = "user_email")
    private final String email;

    @Column(length = 100, nullable = false)
    private final String password;

    //Podemos poner EAGER dado a que este modelo se usa una sola vez en el login y register, y siempre necesitamos los roles
    @OneToMany(fetch = FetchType.EAGER)
    private final List<Role> roles;

    private final boolean verified;


    public UserAuth(String email, String password, List<Role> roles, boolean verified) {
        this.email = email;
        this.password = password;
        this.roles = roles;
        this.verified = verified;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public enum Role{
        CLIENT("ROLE_CLIENT"),

        PROFESSIONAL("ROLE_PROFESSIONAL");

        private final String name;

        Role(String name){
            this.name=name;
        }

        public String getName() {
            return name;
        }
    }

    public boolean isVerified() {
        return verified;
    }
}
