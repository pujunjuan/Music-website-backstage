package com.pjj.music.dao;

import com.pjj.music.pojo.ListSong;
import org.springframework.stereotype.Repository;

import java.util.List;
//歌单歌曲
@Repository
public interface ListSongMapper {
    //增加歌单歌曲
    public int add(ListSong listSong);
    //删除歌单歌曲
    public int delete(Integer id);

    public int deleteBysongIdSongListId(Integer songId,Integer songListId);
    //修改歌单歌曲
    public int update(ListSong listSong);
    //查询全部歌单歌曲
    public List<ListSong> selectListSong();
    //id查询
    public ListSong selectById(Integer id);
    //歌单id查询
    public List<ListSong> selectBySongID(Integer songListId);

}
