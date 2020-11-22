package com.pjj.music.service.Impl;

import com.pjj.music.dao.UserMapper;
import com.pjj.music.pojo.User;
import com.pjj.music.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;
    @Override
    public boolean addUser(User user) {
        return userMapper.addUser(user)>0;
    }

    @Override
    public boolean deleteUser(Integer id) {
        return userMapper.deleteUser(id)>0;
    }

    @Override
    public boolean updateUser(User user) {
        return userMapper.updateUser(user)>0;
    }

    @Override
    public List<User> UserAll() {
        return userMapper.UserAll();
    }

    @Override
    public User UserById(Integer id) {
        return userMapper.UserById(id);
    }

    @Override
    public boolean Checkpassword(String username, String password) {
        return userMapper.Checkpassword(username,password)>0;
    }

    @Override
    public boolean updatePassword(String username, String password) {
        return userMapper.updatePassword(username,password)>0;
    }

    @Override
    public User UserName(String username) {
        return userMapper.UserName(username);
    }
}
