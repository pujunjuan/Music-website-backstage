package com.pjj.music.controller;
import com.alibaba.fastjson.JSONObject;
import com.pjj.music.service.AdminService;
import com.pjj.music.util.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
public class AdminController {
    @Autowired
    AdminService adminService;
    /*
    * 判断管理员是否登录正确
    */
    @RequestMapping(value = "/admin/login/status",method = RequestMethod.POST)
    public Object LoginStatus(HttpServletRequest request, HttpSession session){
        JSONObject jsonObject=new JSONObject();
        String name=request.getParameter("name");
        String password=request.getParameter("password");
        boolean flag=adminService.checkAdmin(name,password);
        System.out.println(flag);
        System.out.println(name+password);
        if(flag){
            jsonObject.put(Const.CODE,1);
            jsonObject.put(Const.MSG,"登陆成功");
            session.setAttribute(Const.NAME,name);
            System.out.println(jsonObject);
            return jsonObject;
        }
        jsonObject.put(Const.CODE,0);
        jsonObject.put(Const.MSG,"用户名或密码错误");
        return jsonObject;
    }
}
