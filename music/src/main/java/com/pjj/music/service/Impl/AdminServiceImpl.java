package com.pjj.music.service.Impl;

import com.pjj.music.dao.AdminDao;
import com.pjj.music.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    AdminDao adminDao;


    @Override
    public boolean checkAdmin(String username, String password) {
        return adminDao.checkAdmin(username,password)>0;
    }
}
