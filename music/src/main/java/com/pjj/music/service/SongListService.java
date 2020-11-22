package com.pjj.music.service;

import com.pjj.music.pojo.SongList;

import java.util.List;

public interface SongListService  {
    //查询所有歌曲
    List<SongList> selectSongList();
    //id查询歌曲
    public SongList selectByID(Integer id);
    //歌曲标题模糊查询
    List<SongList> SongListTitle(String title);
    //歌曲风格模糊查询
    List<SongList> SongListStyle(String style);
    //增加
    public  boolean insert(SongList songList);
    //删除
    public boolean delete(Integer id);
    //修改
    public boolean update(SongList songList);
}
