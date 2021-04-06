package main.api.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import main.model.User;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UsersResponse {
    private Boolean result;
    private List<User> users;
    private String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
