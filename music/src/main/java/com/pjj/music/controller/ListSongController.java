package com.pjj.music.controller;

import com.alibaba.fastjson.JSONObject;
import com.pjj.music.pojo.ListSong;
import com.pjj.music.service.ListSongService;
import com.pjj.music.util.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import javax.servlet.http.HttpServletRequest;


//歌单歌曲管理
@RestController
@RequestMapping("/ListSong")
public class ListSongController {
    @Autowired
    ListSongService listSongService;
    //给歌单添加歌曲
    /**
     * 添加歌曲
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public Object addSongList(HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();
        //获取前端传来的参数
        String songId = request.getParameter("songId").trim();  //所属歌曲id
        String songListId = request.getParameter("songListId").trim(); //歌单id
        ListSong listSong=new ListSong();
        listSong.setSongId(Integer.parseInt(songId));
        listSong.setSongListId(Integer.parseInt(songListId));
        System.out.println(listSong);
        boolean flag = listSongService.add(listSong);
        if(flag){
            jsonObject.put(Const.CODE,1);
            jsonObject.put(Const.MSG,"保存成功");
            return jsonObject;
        }
        jsonObject.put(Const.CODE,0);
        jsonObject.put(Const.MSG,"保存失败");
        return jsonObject;
    }

    /**
     * 根据歌单id查询歌曲
     */
    @RequestMapping(value = "/detail",method = RequestMethod.GET)
    public Object detail(HttpServletRequest request){
        String songListId = request.getParameter("songListId");
        return listSongService.selectBySongID(Integer.parseInt(songListId));
    }

    /**
     * 修改歌曲
     */
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public Object updateSong(HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();
        String songId = request.getParameter("songId").trim();  //所属歌曲id
        String songListId = request.getParameter("songListId").trim(); //歌单id
        ListSong listSong=new ListSong();
        listSong.setSongId(Integer.parseInt(songId));
        listSong.setSongListId(Integer.parseInt(songListId));
        boolean flag = listSongService.update(listSong);
        if(flag){   //保存成功
            jsonObject.put(Const.CODE,1);
            jsonObject.put(Const.MSG,"修改成功");
            return jsonObject;
        }
        jsonObject.put(Const.CODE,0);
        jsonObject.put(Const.MSG,"修改失败");
        return jsonObject;
    }

    /**
     * 删除歌单歌曲
     */
    @RequestMapping(value = "/delete",method = RequestMethod.GET)
    public Object deleteSinger(HttpServletRequest request){
        //-TODO 先查询到数据库中对应的文件地址，删除掉它再进行下面的代码
        String songId = request.getParameter("songId").trim();          //主键
        String songListId = request.getParameter("songListId").trim();
        boolean flag = listSongService.deleteBysongIdSongListId(Integer.parseInt(songId),Integer.parseInt(songListId));
        return flag;
    }

    /**
     * 查询所有歌曲
     */
    @RequestMapping(value = "/allSong",method = RequestMethod.GET)
    public Object allSong(HttpServletRequest request){
        return listSongService.selectListSong();
    }
}
