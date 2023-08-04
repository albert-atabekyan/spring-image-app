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
        if(isUserIsNotInDb(username))
            throw new UsernameNotFoundException("User not found");

        return userDao.findByUsername(username);
    }

    public boolean saveUser(User user) {
        if(isUserInDb(user.getUsername()))
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

    public boolean isUserIsNotInDb(String username) {
        return !isUserInDb(username);
    }

    public boolean deleteUser(Long userId) {
        if(userDao.findById(userId).isPresent()) {
            userDao.deleteById(userId);
            return true;
        }

        return false;
    }

    public void addImage(Long userId, String url) {
        Optional<User> userFromDB  = userDao.findById(userId);

        if(userFromDB.isPresent()) {
            User user = userFromDB.get();

            Image image = new Image(1L, url);
            user.getImages().add(image);

            userDao.save(user);
        }

    }

    public String deleteImage(Long userId, Long image_id) throws Exception {
        Optional<User> userFromDB = userDao.findById(userId);

        if (userFromDB.isPresent()) {
            User user = userFromDB.get();
            for (Image image : user.getImages()) {
                if(Objects.equals(image.getId(), image_id)) {
                    user.getImages().remove(image);
                    userDao.save(user);
                    return image.getUrl();
                }
            }
        }

        throw new Exception("Пользователь не существует");
    }
}
