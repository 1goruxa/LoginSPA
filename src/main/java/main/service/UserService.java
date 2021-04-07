package main.service;

import main.api.request.AddUserRequest;
import main.api.request.LogoutRequest;
import main.api.request.UpdateRequest;
import main.api.response.*;
import main.config.Roles;
import main.model.User;
import main.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import org.apache.commons.validator.routines.EmailValidator;

@Service
public class UserService {
    @Autowired
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //Получаем список всех пользователей
    public UsersResponse getAll(String status){
        UsersResponse usersResponse = new UsersResponse();
        usersResponse.setResult(false);
        if (status.equals(Roles.ADMIN.getRole())){
            usersResponse.setResult(true);
            List<User> userList = userRepository.findAll();
            usersResponse.setUsers(userList);
        }
        return usersResponse;
    }

    //Получаем пользователя для редактирования данных
    public UserResponse getUser(String name){
        UserResponse userResponse = new UserResponse();
        Optional<User> optionalUser = userRepository.findByName(name);
        if (optionalUser.isPresent()){
            User user = optionalUser.get();
            userResponse = mapUserToResponse(user);
        }
        return userResponse;
    }

    //Добавляем нового пользователя с проверкой возможно ли это вообще
    public NewUserResponse addNew(AddUserRequest newUser){
        NewUserResponse newUserResponse = new NewUserResponse();
        newUserResponse.setResult(false);
        if (newUser.getStatus().equals(Roles.ADMIN.getRole())){
            //Введенные реквизиты пользователя уникальны
            if(!userRepository.findByName(newUser.getName()).isPresent()
                        && !userRepository.findByEmail(newUser.getEmail()).isPresent()){
                //Проверка пароля
                if (newUser.getPassword().length() > 3 && newUser.getName().length() != 0
                                                        && newUser.getEmail().length() != 0) {
                    if (isValidEmailAddress(newUser.getEmail())) {
                        //Создаем пользователя
                        newUserResponse.setResult(true);
                        User user = new User();
                        user.setName(newUser.getName());
                        user.setBirthday(newUser.getBirthday());
                        user.setEmail(newUser.getEmail());
                        user.setPassword(newUser.getPassword());
                        user.setIsOnline(0);
                        user.setIsAdmin(0);
                        userRepository.save(user);
                    }
                    else{
                        newUserResponse.setError("Email is not valid");
                    }
                }
                else{
                    newUserResponse.setError("Password too short or name/email are empty");
                }
            }
            else{
                newUserResponse.setError("Name or Email are non-unique");
            }
        }
        else{
            newUserResponse.setError("Not enough rights to add user");
        }
        return newUserResponse;
    }

    public UpdateResponse update(UpdateRequest updateRequest){
        UpdateResponse updateResponse = new UpdateResponse();
        updateResponse.setResult(false);
        Optional<User> optionalUser = userRepository.findByName(updateRequest.getOldName());
        if(optionalUser.isPresent()){
            User user = optionalUser.get();
            updateResponse.setResult(true);
            user.setName(updateRequest.getName());
            user.setEmail(updateRequest.getEmail());
            user.setBirthday(updateRequest.getBirthday());
            userRepository.save(user);
        }
        return updateResponse;
    }

    public LoginResponse login(String name, String password){
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setResult(false);
        Optional<User> optionalUser = userRepository.findByName(name);
        if (optionalUser.isPresent()){
            User user = optionalUser.get();
            //Работаем с паролем в открытом виде
            if (password.equals(user.getPassword())){
                //Пользователь залогонен
                user.setIsOnline(1);
                userRepository.save(user);
                //Готовим данные для передачи на фронт
                loginResponse.setUserResponse(mapUserToResponse(user));
                loginResponse.setResult(true);
            }
        }
        if (!loginResponse.isResult()){
            loginResponse.setError("Username or password is incorrect");
        }
        return loginResponse;
    }

    public LogoutResponse logout(LogoutRequest userName){
        LogoutResponse logoutResponse = new LogoutResponse();
        logoutResponse.setResult(false);
        Optional<User> optionalUser = userRepository.findByName(userName.getName());
        if (optionalUser.isPresent()){
            User user = optionalUser.get();
            user.setIsOnline(0);
            userRepository.save(user);
            logoutResponse.setResult(true);
        }
        return logoutResponse;
    }

    private UserResponse mapUserToResponse(User user){
        UserResponse userResponse = new UserResponse();
        userResponse.setName(user.getName());
        userResponse.setEmail(user.getEmail());
        userResponse.setBirthday(user.getBirthday());
        if (user.getIsAdmin() == 1){
            userResponse.setStatus(Roles.ADMIN.getRole());
        }
        else{
            userResponse.setStatus(Roles.USER.getRole());
        }
        return userResponse;
    }

    public static boolean isValidEmailAddress(String email) {
        if (email == null || email.equals("")) {
            return false;
        }
        email = email.trim();
        if (!email.contains("@")) {
            return false;
        }
        if (email.indexOf(" ") > 0) {
            return false;
        }
        EmailValidator validator = EmailValidator.getInstance();
        return validator.isValid(email);
    }
}
