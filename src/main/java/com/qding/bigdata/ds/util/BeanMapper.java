package com.qding.bigdata.ds.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.BeanUtils;

/**
 * Created by yanpf on 2017/7/31.
 */
public class BeanMapper {

    public static <T> T map(Object source, Class<T> clazz) {
        if (source == null) {
            return null;
        }
        try {
            T destination = clazz.newInstance();
            BeanUtils.copyProperties(source, destination);
            return destination;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static <T> List<T> mapList(List sourceList, Class<T> clazz) {
        if (sourceList == null || sourceList.isEmpty()) {
            return Collections.emptyList();
        }
        try {
            List<T> target = new ArrayList<T>(sourceList.size());
            for (Object source : sourceList) {
                T instance = clazz.newInstance();
                BeanUtils.copyProperties(source, instance);
                target.add(instance);
            }
            return target;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }
}
