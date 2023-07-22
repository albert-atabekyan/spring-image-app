package com.atabekyan.image.service.services;

import com.atabekyan.image.service.dao.UserDao;
import com.atabekyan.image.service.model.Image;
import com.atabekyan.image.service.model.Role;
import com.atabekyan.image.service.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class UserService implements UserDetailsService {
    private final UserDao userDao;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserDao userDao, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDao = userDao;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findByUsername(username);

        if(user == null)
            throw new UsernameNotFoundException("User not found");

        return user;
    }

    public User findUserById(Long userId) {
        Optional<User> userFromDB = userDao.findById(userId);

        return userFromDB.orElse(new User());
    }

    public boolean saveUser(User user) {
        User userFromDB = userDao.findByUsername(user.getUsername());

        if(userFromDB != null)
            return false;

        user.setRoles(Collections.singleton(new Role(1L, "ROLE_USER")));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        userDao.save(user);

        return true;
    }

    public boolean isUserInDb(String username) {
        User userFromDB = userDao.findByUsername(username);

        return userFromDB != null;
    }

    public boolean deleteUser(Long userId) {
        if(userDao.findById(userId).isPresent()) {
            userDao.deleteById(userId);
            return true;
        }

        return false;
    }

    public boolean addImage(Long userId, String url) {
        Optional<User> userFromDB  = userDao.findById(userId);

        if(userFromDB.isPresent()) {
            User user = userFromDB.get();
            Image image = new Image(1L, url);
            user.getImages().add(image);

            userDao.save(user);
            return true;
        }

        return false;
    }

    public boolean deleteImage(Long userId, String url) {
        Optional<User> userFromDB  = userDao.findById(userId);

        if(userFromDB.isPresent()) {
            User user = userFromDB.get();
            user.getImages().removeIf(tempImage -> tempImage.getUrl().equals(url));

            userDao.save(user);
            return true;
        }

        return false;
    }
}
