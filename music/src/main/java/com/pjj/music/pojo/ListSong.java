package com.pjj.music.pojo;

import lombok.Data;

import java.io.Serializable;
@Data
public class ListSong implements Serializable {
    private Integer id;
    private Integer songId;//歌曲id
    private Integer songListId;//歌单id
}
