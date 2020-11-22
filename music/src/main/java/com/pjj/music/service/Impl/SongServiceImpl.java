package com.pjj.music.service.Impl;

import com.pjj.music.dao.SongMapper;
import com.pjj.music.pojo.Song;
import com.pjj.music.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SongServiceImpl implements SongService {
    @Autowired
    SongMapper songMapper;
    @Override
    public List<Song> selectSong() {
        return songMapper.selectSong();
    }

    @Override
    public Song selectByID(Integer id) {
        return songMapper.selectByID(id);
    }

    @Override
    public List<Song> songName(String name) {
        return songMapper.songName(name);
    }

    @Override
    public List<Song> selectBySongID(Integer singerId) {
        return songMapper.selectBySongID(singerId);
    }

    @Override
    public List<Song> selectBySongListID(Integer songId) {
        return songMapper.selectBySongListID(songId);
    }

    @Override
    public boolean insert(Song song) {
        return songMapper.insert(song)>0;
    }

    @Override
    public boolean delete(Integer id) {
        return songMapper.delete(id)>0;
    }

    @Override
    public boolean update(Song song) {
        return songMapper.update(song)>0;
    }
}
