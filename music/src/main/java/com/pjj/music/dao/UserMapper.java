package com.pjj.music.dao;

import com.pjj.music.pojo.User;
import org.springframework.stereotype.Repository;

import java.util.List;

//用户
@Repository
public interface UserMapper {
    //增加用户
    public int addUser(User user);
    //删除用户
    public int deleteUser(Integer id);
    //修改用户
    public int updateUser(User user);
    //查询全部用户
    public List<User> UserAll();
    //id查询用户
    public User UserById(Integer id);
    //验证密码
    public int Checkpassword(String username, String password);
   //修改密码
    public int updatePassword(String username,String password);
    //根据账号查询
    public User UserName(String username);



}
