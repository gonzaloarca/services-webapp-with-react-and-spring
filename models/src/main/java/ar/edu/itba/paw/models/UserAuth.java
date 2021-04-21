package ar.edu.itba.paw.models;

import java.util.List;

public class UserAuth {

    private final String email;

    private final String password;

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
