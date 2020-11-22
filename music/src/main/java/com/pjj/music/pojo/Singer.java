package com.pjj.music.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Singer implements Serializable {
    private Integer id;//歌手ID
    private String name;//歌手姓名
    private Byte sex;//歌手性别
    private Date birth;//歌手生日
    private String pic ;//歌手图像
    private String location;//歌手地区
    private String introduction;//歌手简介
}
