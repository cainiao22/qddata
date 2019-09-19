package com.qding.bigdata.ds.enums;

/**
 * Created by yanpf on 2017/8/7.
 */
public enum RedisDataTypeEnum {
    KV,HASH;

    public static RedisDataTypeEnum getByName(String name){
        for (RedisDataTypeEnum typeEnum : RedisDataTypeEnum.values()) {
            if(typeEnum.name().equals(name.toUpperCase())){
                return typeEnum;
            }
        }

        return null;
    }
}
