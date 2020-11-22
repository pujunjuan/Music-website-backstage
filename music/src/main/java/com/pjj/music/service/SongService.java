package com.pjj.music.service;

import com.pjj.music.pojo.Song;

import java.util.List;

public interface SongService {
    //查询所有歌曲
    List<Song> selectSong();
    //id查询歌曲
    public Song selectByID(Integer id);
    //歌曲名字模糊查询
    List<Song> songName(String name);
    //根据歌手ID查询歌曲
    List<Song> selectBySongID(Integer singerId);
    //根据歌曲ID查询歌曲
    List<Song> selectBySongListID(Integer songId);
    //增加
    public  boolean insert(Song song);
    //删除
    public boolean delete(Integer id);
    //修改
    public boolean update(Song song);
}
