package ar.edu.itba.paw.models;

public class UserOriginal {
    private long id;
    private String name;
    private String password;

    public UserOriginal() {
    }

    public UserOriginal(String name, long id) {
        this.name = name;
        this.id = id;
    }

    public UserOriginal(long id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return name;
    }
}
