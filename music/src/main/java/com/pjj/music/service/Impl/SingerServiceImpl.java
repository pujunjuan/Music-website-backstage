package com.pjj.music.service.Impl;

import com.pjj.music.dao.SingerMapper;
import com.pjj.music.pojo.Singer;
import com.pjj.music.service.SingerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SingerServiceImpl implements SingerService {
    @Autowired
    SingerMapper singerMapper;

    @Override
    public List<Singer> selectSinger() {
        return singerMapper.selectSinger();
    }

    @Override
    public Singer selectByID(Integer id) {
        return singerMapper.selectByID(id);
    }

    @Override
    public List<Singer> SingerName(String name) {
        return singerMapper.SingerName(name);
    }

    @Override
    public List<Singer> SingerSex(Integer sex) {
        return singerMapper.Singer(sex);
    }

    @Override
    public boolean insert(Singer singer) {
        return singerMapper.insert(singer)>0;
    }

    @Override
    public boolean delete(Integer id) {
        return singerMapper.delete(id)>0;
    }

    @Override
    public boolean update(Singer singer) {
        return singerMapper.update(singer)>0;
    }
}
