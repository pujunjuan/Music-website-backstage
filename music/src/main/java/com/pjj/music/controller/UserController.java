package com.pjj.music.controller;

import com.alibaba.fastjson.JSONObject;
import com.pjj.music.pojo.User;
import com.pjj.music.service.UserService;
import com.pjj.music.util.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/User")
public class UserController {
    @Autowired
    UserService userService;
    //添加用户
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public Object addUser(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        String username = request.getParameter("username").trim();
        String password = request.getParameter("password").trim();
        String sex = request.getParameter("sex").trim();
        String phone = request.getParameter("phone").trim();
        String email = request.getParameter("email").trim();
        String birth = request.getParameter("birth").trim();
        String pic = "/img/UserPic/user.png";
        String location = request.getParameter("location").trim();
        String introduction = request.getParameter("introduction").trim();
        //生日转Date
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date birthdate = new Date();
        try {
            birthdate = dateFormat.parse(birth);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setSex(new Byte(sex));
        user.setPhoneNum(phone);
        user.setEmail(email);
        user.setAvator(pic);
        user.setBirth(birthdate);
        user.setLocation(location);
        user.setIntroduction(introduction);
        System.out.println(user+"cururuu-");
        Boolean flag=userService.addUser(user);
        if(flag){
            jsonObject.put(Const.CODE,1);
            jsonObject.put(Const.MSG,"添加成功");
            System.out.println(jsonObject);
            return jsonObject;
        }
        jsonObject.put(Const.CODE,0);
        jsonObject.put(Const.MSG,"添加失败");
        System.out.println(jsonObject);
        return jsonObject;
    }
    //修改用户
    @RequestMapping(value ="/update",method = RequestMethod.POST)
    public Object updateUser(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        String id = request.getParameter("id").trim();
        String username = request.getParameter("username").trim();
        String sex = request.getParameter("sex").trim();
        String phone = request.getParameter("phone").trim();
        String email = request.getParameter("email").trim();
        String birth = request.getParameter("birth").trim();
        String location = request.getParameter("location").trim();
        String introduction = request.getParameter("introduction").trim();
        //生日转Date
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
        Date birthdate = new Date();
        try {
            birthdate = dateFormat.parse(birth);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        User user = new User();
        user.setId(Integer.parseInt(id));
        user.setUsername(username);
        user.setSex(new Byte(sex));
        user.setPhoneNum(phone);
        user.setEmail(email);
        user.setBirth(birthdate);
        user.setLocation(location);
        user.setIntroduction(introduction);
        System.out.println(user);
        boolean flag=userService.updateUser(user);
        if(flag){
            jsonObject.put(Const.CODE,1);
            jsonObject.put(Const.MSG,"修改成功");
            System.out.println(jsonObject);
            return jsonObject;
        }
        jsonObject.put(Const.CODE,0);
        jsonObject.put(Const.MSG,"修改失败");
        System.out.println(jsonObject);
        return jsonObject;
    }
    //删除用户
    @RequestMapping(value ="/delete",method = RequestMethod.GET)
    public Object deleteUser(HttpServletRequest request){
        JSONObject jsonObject=new JSONObject();
        String id=request.getParameter("id");
        Boolean flag=userService.deleteUser(Integer.parseInt(id));
        if(flag){
            jsonObject.put(Const.CODE,1);
            jsonObject.put(Const.MSG,"删除成功");
            System.out.println(jsonObject);
            return jsonObject;
        }
        jsonObject.put(Const.CODE,0);
        jsonObject.put(Const.MSG,"删除失败");
        System.out.println(jsonObject);
        return jsonObject;
    }
    //根据主键ID查询用户
    @RequestMapping(value ="/selectByID",method = RequestMethod.GET)
    public Object selectByIDUser(HttpServletRequest request){
        String id=request.getParameter("id").trim();
        User flag=userService.UserById(Integer.parseInt(id));
        return flag;
    }
    //根据全部询用户
    @RequestMapping(value ="/selectAll",method = RequestMethod.GET)
    public Object selectAllUser(HttpServletRequest request){
        System.out.println( userService.UserAll()  );
        return userService.UserAll();
    }

    //根据用户名字模糊查询
    @RequestMapping(value ="/UserName",method = RequestMethod.GET)
    public Object selectUserName(HttpServletRequest request){
        String username=request.getParameter("username").trim();
        return userService.UserName(username);
    }

    //验证密码
    @RequestMapping(value ="/CheckPassword",method = RequestMethod.GET)
    public Object CheckPassword(HttpServletRequest request){
        String username=request.getParameter("username").trim();
        String password=request.getParameter("password").trim();
        return userService.Checkpassword(username,password);
    }
    //修改密码
    @RequestMapping(value ="/UpdatePassword",method = RequestMethod.POST)
    public Object UpdatePassword(HttpServletRequest request){
        String username=request.getParameter("username").trim();
        String password=request.getParameter("password").trim();
        return userService.updatePassword(username,password);
    }

    //更新用户图片
    @RequestMapping(value ="/updateUserImg",method = RequestMethod.POST)
    public Object supdateUserImg(@RequestParam("file") MultipartFile afile, @RequestParam("id")Integer id){
        JSONObject jsonObject=new JSONObject();
        System.out.println("进入图片上传");
        if(afile.isEmpty()){
            jsonObject.put(Const.CODE,0);
            jsonObject.put(Const.MSG,"修改失败");
            System.out.println(jsonObject);
            return jsonObject;
        }
        //  文件名
        String filename=System.currentTimeMillis()+afile.getOriginalFilename();
        //文件路径
        String filePath=System.getProperty("user.dir")+System.getProperty("file.separator")+"img"
                +System.getProperty("file.separator")+"UserPic";
        //如果文件不存在，新建该路径
        File file=new File(filePath);
        if(file.exists()){
            file.mkdirs();
        }
        //实际文件路径
        File dest=new File(filePath+System.getProperty("file.separator")+filename);
        String strorPath="/img/UserPic/"+filename;
        try {
            afile.transferTo(dest);
            User user=new User();
            user.setId(id);
            user.setAvator(strorPath);
            boolean flag=userService.updateUser(user);
            if(flag){
                jsonObject.put(Const.CODE,1);
                jsonObject.put(Const.MSG,"上传成功");
                jsonObject.put("pic",strorPath);
                return jsonObject;
            } jsonObject.put(Const.CODE,0);
            jsonObject.put(Const.MSG,"上传失败");
            return jsonObject;
        } catch (IOException e) {
            e.printStackTrace();
            jsonObject.put(Const.CODE,0);
            jsonObject.put(Const.MSG,"上传失败");
        }finally {
            return jsonObject;
        }
    }
}
