package Wolox.training.DAO;

import java.time.LocalDate;

public class UserDAO {

    private String username;
    private String name;
    private LocalDate birthday;
    private String password;


    public UserDAO(String username, String name, LocalDate birthday, String password) {
        this.username = username;
        this.name = name;
        this.birthday = birthday;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
