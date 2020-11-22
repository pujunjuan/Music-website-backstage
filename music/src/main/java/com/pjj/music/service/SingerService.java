package com.pjj.music.service;

import com.pjj.music.pojo.Singer;

import java.util.List;

//歌手Service接口
public interface SingerService {
    //查询所有歌手
    List<Singer> selectSinger();
    //id查询歌手
    public Singer selectByID(Integer id);
    //歌手名字模糊查询
    List<Singer> SingerName(String name);
    //根据性别查询歌手
    List<Singer> SingerSex(Integer sex);
    //增加
    public  boolean insert(Singer singer);
    //删除
    public boolean delete(Integer id);
    //修改
    public boolean update(Singer singer);
}
