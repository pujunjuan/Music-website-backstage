package com.pjj.music.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Song implements Serializable {
    private Integer id;//歌曲ID
    private Integer singerId;//歌手ID
    private String name;//歌曲名字
    private Date createTime;//歌曲创建时间
    private String pic ;//歌曲图像
    private Date updateTime;//歌曲跟新时间
    private String introduction;//歌曲简介
    private String lyric;//歌词
    private String url;//歌曲地址
}
