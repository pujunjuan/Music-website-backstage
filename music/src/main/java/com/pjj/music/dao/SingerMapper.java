package com.pjj.music.dao;

import com.pjj.music.pojo.Singer;
import com.pjj.music.pojo.Singer;
import com.pjj.music.pojo.Singer;
import org.springframework.stereotype.Repository;

import java.util.List;
//歌手Dao
@Repository
public interface SingerMapper {
    //查询所有歌手
    List<Singer> selectSinger();
    //id查询歌手
    public Singer selectByID(Integer id);
    //歌手名字模糊查询
    List<Singer> SingerName(String name);
    //根据性别查询歌手
    List<Singer> Singer(Integer sex);
    //增加
    public  int insert(Singer Singer);
    //删除
    public int delete(Integer id);
    //修改
    public int update(Singer Singer);
}
