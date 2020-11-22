package com.pjj.music.service;

import com.pjj.music.pojo.ListSong;

import java.util.List;

public interface ListSongService {
    //增加歌单歌曲
    public boolean add(ListSong listSong);
    //删除歌单歌曲
    public boolean delete(Integer id);
    public boolean deleteBysongIdSongListId(Integer songId,Integer songListId);
    //修改歌单歌曲
    public boolean update(ListSong listSong);
    //查询全部歌单歌曲
    public List<ListSong> selectListSong();
    //id查询
    public ListSong selectById(Integer id);
    //歌单id查询
    public List<ListSong> selectBySongID(Integer songListId);
}
