package com.pjj.music.controller;

import com.alibaba.fastjson.JSONObject;
import com.pjj.music.pojo.Singer;
import com.pjj.music.service.SingerService;
import com.pjj.music.util.Const;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.*;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/Singer")
public class SingerController {
    @Autowired
    SingerService singerService;
    //添加歌手
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public Object addSinger(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        String name = request.getParameter("name").trim();
        String sex = request.getParameter("sex").trim();
        String pic = request.getParameter("pic").trim();
        String birth = request.getParameter("birth").trim();
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
        System.out.println(birthdate);
        Singer singer = new Singer();
        singer.setName(name);
        singer.setSex(new Byte(sex));
        singer.setPic(pic);
        singer.setBirth(birthdate);
        singer.setLocation(location);
        singer.setIntroduction(introduction);
        Boolean flag=singerService.insert(singer);
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
    //修改歌手
    @RequestMapping(value ="/update",method = RequestMethod.POST)
    public Object updateSinger(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        String id=request.getParameter("id").trim();
        String name = request.getParameter("name").trim();
        String sex = request.getParameter("sex").trim();
    /*    String pic = request.getParameter("pic").trim();*/
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
        Singer singer = new Singer();
        singer.setId(Integer.parseInt(id));
        singer.setName(name);
        singer.setSex(new Byte(sex));
      /*  singer.setPic(pic);*/
        singer.setBirth(birthdate);
        singer.setLocation(location);
        singer.setIntroduction(introduction);
        Boolean flag=singerService.update(singer);
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
    //删除歌手
    @RequestMapping(value ="/delete",method = RequestMethod.GET)
    public Object deleteSinger(HttpServletRequest request){
        JSONObject jsonObject=new JSONObject();
        String id=request.getParameter("id");
        Boolean flag=singerService.delete(Integer.parseInt(id));
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
    //根据主键ID查询歌手
    @RequestMapping(value ="/selectByID",method = RequestMethod.GET)
    public Object selectByIDSinger(HttpServletRequest request){
        String id=request.getParameter("id").trim();
        Singer flag=singerService.selectByID(Integer.parseInt(id));
        return flag;
    }
    //根据全部询歌手
    @RequestMapping(value ="/selectAll",method = RequestMethod.GET)
    public Object selectAllSinger(HttpServletRequest request){
        return singerService.selectSinger();
    }
    //根据歌手名字模糊查询
    @RequestMapping(value ="/singerName",method = RequestMethod.GET)
    public Object selectsingerName(HttpServletRequest request){
        String name=request.getParameter("name").trim();
        return singerService.SingerName("%"+name+"%");
    }
    //根据歌手名字模糊查询
    @RequestMapping(value ="/singerSex",method = RequestMethod.GET)
    public Object selectsingerSex(HttpServletRequest request){
        String sex=request.getParameter("sex").trim();
        return singerService.SingerSex(Integer.parseInt(sex));
    }

    //更新歌手图片
    //修改歌手
    @RequestMapping(value ="/updateSingerImg",method = RequestMethod.POST)
    public Object supdateSingerImg(@RequestParam("file")MultipartFile afile, @RequestParam("id")Integer id){
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
               +System.getProperty("file.separator")+"singerPic";
       //如果文件不存在，新建该路径
       File file=new File(filePath);
       if(file.exists()){
           file.mkdirs();
       }
       //实际文件路径
        File dest=new File(filePath+System.getProperty("file.separator")+filename);
       String strorPath="/img/singerPic/"+filename;
       try {
           afile.transferTo(dest);
           Singer singer=new Singer();
           singer.setId(id);
           singer.setPic(strorPath);
           boolean flag=singerService.update(singer);
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
