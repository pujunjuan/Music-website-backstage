package com.pjj.music.service;

public interface AdminService {
    /*验证管理员密码是否正确*/
    boolean checkAdmin(String username,String password);
}
