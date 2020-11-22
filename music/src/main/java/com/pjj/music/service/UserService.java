package com.pjj.music.service;

import com.pjj.music.pojo.User;

import java.util.List;

public interface UserService {
    //增加用户
    public boolean addUser(User user);
    //删除用户
    public boolean deleteUser(Integer id);
    //修改用户
    public boolean updateUser(User user);
    //查询全部用户
    public List<User> UserAll();
    //id查询用户
    public User UserById(Integer id);
    //验证密码
    public boolean Checkpassword(String username, String password);
    //修改密码
    public boolean updatePassword(String username,String password);
    //根据账号查询
    public User UserName(String username);
}
