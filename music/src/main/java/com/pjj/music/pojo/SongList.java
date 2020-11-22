package com.pjj.music.pojo;

import lombok.Data;

import java.io.Serializable;
//歌曲管理
@Data
public class SongList implements Serializable {
    private Integer id;
    private String title;//标题
    private String pic;//图片
    private String introduction;//简介
    private String style;//风格
}
