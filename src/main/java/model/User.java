package model;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 100)
    private String email;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String roles;

    protected User() {}

    public User(String email, String name, String password, String roles) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.roles = roles;
    }

    public User(String email, String name, String roles) {
        this.email = email;
        this.name = name;
        this.roles = roles;
    }

    public int getId() { return id; }
    public String getEmail() { return email; }
    public String getName() { return name; }
    public String getPassword() { return password; }
    public String getRoles() { return roles; }
    public boolean isAdmin() {
        return roles.toUpperCase().contains("ADMIN");
    }
}
