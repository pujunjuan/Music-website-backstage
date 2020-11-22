package com.pjj.music.pojo;

import lombok.Data;

import java.io.Serializable;

/*
管理员
 */
@Data
public class Admin implements Serializable {
private  Integer id;//主键
private String name;//管理员名称
private String password;//管理员密码
}
