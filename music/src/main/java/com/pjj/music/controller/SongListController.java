package com.pjj.music.controller;

import com.alibaba.fastjson.JSONObject;
import com.pjj.music.pojo.SongList;
import com.pjj.music.service.SongListService;
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


@RestController
@RequestMapping("SongList")
public class SongListController {
    @Autowired
    SongListService songListService;

    //查询全部歌单
    @RequestMapping(value = "/selectSongList", method = RequestMethod.GET)
    public Object selectSongList(HttpServletRequest request) {
        return songListService.selectSongList();
    }

    //ID查询歌单
    @RequestMapping(value = "/selectByID", method = RequestMethod.GET)
    public Object selectByID(HttpServletRequest request) {
        String id = request.getParameter("id");
        return songListService.selectByID(Integer.parseInt(id));
    }

    //标题模糊查询查询歌单
    @RequestMapping(value = "/SongListTitle", method = RequestMethod.GET)
    public Object SongListTitle(HttpServletRequest request) {
        String title = request.getParameter("title");
        return songListService.SongListTitle("%"+title+"%");
    }

    //风格模糊查询歌单
    @RequestMapping(value = "/SongListStyle", method = RequestMethod.GET)
    public Object SongListStyle(HttpServletRequest request) {
        String style = request.getParameter("style");
        return songListService.SongListStyle(style);
    }

    //插入歌单
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public Object insert(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        //前端获取的插入的值
        String title = request.getParameter("title").trim();//标题
        String pic = "/img/songPic/tubiao.jpg";//默认图片
        String introduction = request.getParameter("introduction").trim();//简介
        String style = request.getParameter("style").trim();
        SongList songList=new SongList();
        songList.setTitle(title);
        songList.setPic(pic);
        songList.setStyle(style);
        songList.setIntroduction(introduction);
        boolean flag=songListService.insert(songList);
        System.out.println(flag);
        System.out.println(songList);
        if(flag){
            jsonObject.put(Const.CODE, 1);
            jsonObject.put(Const.MSG, "添加成功");
            System.out.println(jsonObject);
            return jsonObject;
        }
        jsonObject.put(Const.CODE, 0);
        jsonObject.put(Const.MSG, "添加失败");
        return jsonObject;
    }

    //id删除数据
    @RequestMapping(value = "/detele", method = RequestMethod.GET)
    public Object detele(HttpServletRequest request){
        String id=request.getParameter("id");
        return songListService.delete(Integer.parseInt(id));
    }

    //修改歌单信息
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Object update(HttpServletRequest request){
        JSONObject jsonObject=new JSONObject();
        String id=request.getParameter("id");
        String title=request.getParameter("title");
        String introduction=request.getParameter("introduction");
        String style=request.getParameter("style");
        SongList songList=new SongList();
        songList.setId(Integer.parseInt(id));
        songList.setIntroduction(introduction);
        songList.setTitle(title);
        songList.setStyle(style);
        boolean flag=songListService.update(songList);
        if(flag){
            jsonObject.put(Const.CODE, 1);
            jsonObject.put(Const.MSG, "添加成功");
            System.out.println(jsonObject);
            return jsonObject;

        }
        jsonObject.put(Const.CODE, 0);
        jsonObject.put(Const.MSG, "添加失败");
        return jsonObject;
    }

    //修改歌单图片
    @RequestMapping(value = "/updateImg", method = RequestMethod.POST)
    public Object updateImg( @RequestParam("file") MultipartFile mpFile,@RequestParam("id")int id) {
        JSONObject jsonObject = new JSONObject();
        //上传歌单图片
        if (mpFile.isEmpty()) {
            jsonObject.put(Const.CODE, 0);
            jsonObject.put(Const.MSG, "图片上传失败");
            return jsonObject;
        }
        //文件名=当前毫秒数+原来文件名
        String fileName = System.currentTimeMillis() + mpFile.getOriginalFilename();
        //文件路劲System.getProperty("user.dir")//当前项目名
        // System.getProperty("file.separator")+"img"
        // System.getProperty("file.separator")+"singerPic";
        String filePath = System.getProperty("user.dir") + System.getProperty("file.separator") + "img"
                + System.getProperty("file.separator") + "songListPic";
        System.out.println(filePath);
        //如果文件不存在就重新建立
        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        //实际的文件地址
        File dest = new File(filePath+System.getProperty("file.separator")+fileName);
        //存储到数据库里的相对文件地址
        String storeUrlPath = "/img/songListPic/"+fileName;
        try {
            mpFile.transferTo(dest);

        } catch (IOException e) {
            e.printStackTrace();
        }

        SongList songList=new SongList();
        songList.setId(id);
        songList.setPic(storeUrlPath);
        System.out.println(songList);
        boolean flag=songListService.update(songList);
        if(flag){
            jsonObject.put(Const.CODE, 1);
            jsonObject.put(Const.MSG, "修改图片成功");
            return jsonObject;
        }
        jsonObject.put(Const.CODE, 0);
        jsonObject.put(Const.MSG, "修改图片失败");
        return jsonObject;
    }
}
