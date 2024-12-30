package org.maxim.weatherapp.services;

import org.maxim.weatherapp.dao.UserDao;
import org.maxim.weatherapp.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserService {
    private final UserDao userDao;

    public UserService() {

        this.userDao = new UserDao();
    }

    public void registerUser(User user) {
        if(userDao.getByUserLogin(user.getLogin())) {
            throw new IllegalArgumentException("User already exists");
        }
        userDao.add(user);
    }

    public String loginUser(User user) {
        return "123";
    }
}
