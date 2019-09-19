package com.qding.bigdata.ds.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @Author yanpf
 * @Date 19-9-2 下午4:48
 * @Description
 **/

@Getter
@Setter
public class UserDetailParam {
    private String companyId;
    private String action;
    private boolean registered = false;
    private String registerDate;
    private String activeDate;
    private String userName;
    private String telphone;

}
