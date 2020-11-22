package com.pjj.music.dao;

import com.pjj.music.pojo.SongList;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SongListMapper {
    //查询所有歌曲
    List<SongList> selectSongList();
    //id查询歌曲
    public SongList selectByID(Integer id);
    //歌曲标题模糊查询
    List<SongList> SongListTitle(String title);
    //歌曲风格模糊查询
    List<SongList> SongListStyle(String style);
    //增加
    public  int insert(SongList songList);
    //删除
    public int delete(Integer id);
    //修改
    public int update(SongList songList);
}
