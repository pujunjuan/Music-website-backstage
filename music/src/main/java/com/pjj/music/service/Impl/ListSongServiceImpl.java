package com.pjj.music.service.Impl;

import com.pjj.music.dao.ListSongMapper;
import com.pjj.music.pojo.ListSong;
import com.pjj.music.service.ListSongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ListSongServiceImpl implements ListSongService {
    @Autowired
    ListSongMapper listSongMapper;

    @Override
    public boolean add(ListSong listSong) {
        return listSongMapper.add(listSong)>0;
    }

    @Override
    public boolean delete(Integer id) {
        return listSongMapper.delete(id)>0;
    }

    @Override
    public boolean deleteBysongIdSongListId(Integer songId, Integer songListId) {
        return listSongMapper.deleteBysongIdSongListId(songId,songListId)>0;
    }

    @Override
    public boolean update(ListSong listSong) {
        return listSongMapper.update(listSong)>0;
    }

    @Override
    public List<ListSong> selectListSong() {
        return listSongMapper.selectListSong();
    }

    @Override
    public ListSong selectById(Integer id) {
        return listSongMapper.selectById(id);
    }

    @Override
    public List<ListSong> selectBySongID(Integer songListId) {
        return listSongMapper.selectBySongID(songListId);
    }
}
