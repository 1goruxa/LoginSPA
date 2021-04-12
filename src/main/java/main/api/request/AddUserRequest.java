package main.api.request;

import java.util.Date;

public class AddUserRequest {
    private String name;
    private String email;
    private Date birthday;
    private String status;
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "AddUserRequest{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", birthday=" + birthday +
                ", status='" + status + '\'' +
                '}';
    }
}
