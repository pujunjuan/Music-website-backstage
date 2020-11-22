package com.pjj.music.service.Impl;

import com.pjj.music.dao.SongListMapper;
import com.pjj.music.pojo.SongList;
import com.pjj.music.service.SongListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SongListServiceImpl implements SongListService {
    @Autowired
    SongListMapper songListMapper;
    @Override
    public List<SongList> selectSongList() {
        return songListMapper.selectSongList();
    }

    @Override
    public SongList selectByID(Integer id) {
        return songListMapper.selectByID(id);
    }

    @Override
    public List<SongList> SongListTitle(String title) {
        return songListMapper.SongListTitle(title);
    }

    @Override
    public List<SongList> SongListStyle(String style) {
        return songListMapper.SongListStyle(style);
    }


    @Override
    public boolean insert(SongList songList) {
        return songListMapper.insert(songList)>0;
    }

    @Override
    public boolean delete(Integer id) {
        return songListMapper.delete(id)>0;
    }

    @Override
    public boolean update(SongList songList) {
        return songListMapper.update(songList)>0;
    }
}
