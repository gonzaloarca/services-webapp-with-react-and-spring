package ar.edu.itba.paw.models;

import java.util.List;

public class UserAuth {

    private String email;

    private String password;

    private List<Role> roles;

    public UserAuth() {
    }

    public UserAuth(String email, String password, List<Role> roles) {
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
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

}
