package forms;

import domain.User;

import java.util.Collection;

public class TestForm {


    private String name;
    private String password;
    private Collection<User> users;


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


}
