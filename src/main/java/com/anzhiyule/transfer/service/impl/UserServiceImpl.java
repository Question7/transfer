package com.anzhiyule.transfer.service.impl;

import com.anzhiyule.transfer.mapper.UserMapper;
import com.anzhiyule.transfer.model.User;
import com.anzhiyule.transfer.service.IUserService;
import com.anzhiyule.transfer.tools.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImpl implements IUserService, UserDetailsService {

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User getUserByUsername(String username) {
        return userMapper.selectUserByUsername(username);
    }

    @Override
    public User getUserById(String id) {
        return userMapper.selectUserById(id);
    }

    @Override
    public User getUserByFolderAndId(String folderId, String userId) {
        return userMapper.selectUserByFolderIdAndUserId(folderId, userId);
    }

    @Override
    public User[] listUsers() {
        return userMapper.selectUsers();
    }

    @Override
    public User[] listUsersByFolderId(String folderId) {
        return userMapper.selectUserByFolderId(folderId);
    }

    @Override
    public String addUser(User user) {
        String id = UUID.randomUUID().toString();
        String password = passwordEncoder.encode(user.getPassword());
        user.setId(id);
        user.setPassword(password);
        user.setRole("user");
        userMapper.insertUser(user);
        return id;
    }

    @Override
    public String updatePassword(String opd, String npd) {
        String id = CurrentUser.getUser();
        User user = getUserById(id);
        if (user == null) {
            return "fail";
        }
        String local = user.getPassword();
        if (passwordEncoder.matches(opd, local)) {
            userMapper.updateUserPassword(passwordEncoder.encode(npd), id);
            return "success";
        } else {
            return "fail";
        }
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return getUserByUsername(s);
    }
}
