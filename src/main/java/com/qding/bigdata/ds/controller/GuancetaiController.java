package com.qding.bigdata.ds.controller;

import com.qding.bigdata.ds.model.GuancetaiQuotaTrendItem;

import java.util.List;

/**
 * @author yanpf
 * @date 2018/12/27 18:04
 * @description
 */
public class GuancetaiController {

    protected void mergeResult(List<GuancetaiQuotaTrendItem> allItemList, List<GuancetaiQuotaTrendItem> otherList, List<GuancetaiQuotaTrendItem> result) {
        int i = 0, j =0;
        while (i < allItemList.size() && j < otherList.size()){
            GuancetaiQuotaTrendItem onlineItem = allItemList.get(i);
            GuancetaiQuotaTrendItem timeItem = otherList.get(j);
            GuancetaiQuotaTrendItem item = new GuancetaiQuotaTrendItem();
            if(onlineItem.getKey().compareTo(timeItem.getKey()) < 0){
                item.setValue(0.0);
                item.setKey(onlineItem.getKey());
                i ++;
            }else if(onlineItem.getKey().compareTo(timeItem.getKey()) == 0){
                item.setValue(Math.round(onlineItem.getValue() / timeItem.getValue() * 100) / 100.0);
                item.setKey(onlineItem.getKey());
                i ++;
                j ++;
            }else {
                item.setValue(0.0);
                item.setKey(timeItem.getKey());
                i ++;
            }

            result.add(item);
        }
    }
}
