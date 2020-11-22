package com.pjj.music.dao;

import org.springframework.stereotype.Repository;

@Repository
public interface AdminDao {
    //验证管理员密码是否正确
    int checkAdmin(String username,String password);
}
