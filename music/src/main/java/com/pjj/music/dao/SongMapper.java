package com.pjj.music.dao;

import com.pjj.music.pojo.Song;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SongMapper {
    //查询所有歌手
    List<Song> selectSong();
    //id查询歌手
    public Song selectByID(Integer id);
    //歌手名字模糊查询
    List<Song> songName(String name);
    //根据性别查询歌手
    List<Song> selectBySongID(Integer singerId);
    //根据歌曲ID查询歌曲
    List<Song> selectBySongListID(Integer songId);
    //增加
    public  int insert(Song song);
    //删除
    public int delete(Integer id);
    //修改
    public int update(Song song);
}
