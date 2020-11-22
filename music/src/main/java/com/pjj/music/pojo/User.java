package com.pjj.music.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class User {
    private Integer id;
    private String username;//用户名
    private String password;//密码
    private Byte sex;//性别
    private String phoneNum;//电话号码
    private String email;//邮箱
    private Date  birth;//生日
    private String introduction;//签名
    private String location;//地区
    private String avator;//图像
    private  Date  createTime;//创建时间
    private Date updateTime;//更新时间

}
