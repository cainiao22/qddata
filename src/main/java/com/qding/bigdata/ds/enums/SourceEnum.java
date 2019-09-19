package com.qding.bigdata.ds.enums;

/**
 * Created by lzs on 2018/7/6.
 */
public enum SourceEnum {
    JSC("jiashicang","驾驶舱"),
    LP("luopan","罗盘"),
    RB("ribao","日报"),
    YB("yuebao","月报"),
    SHKJ("shujukuijia","数据盔甲");

    private String source;
    private String sourceCN;

    SourceEnum(String source, String sourceCN) {
        this.source = source;
        this.sourceCN = sourceCN;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSourceCN() {
        return sourceCN;
    }

    public void setSourceCN(String sourceCN) {
        this.sourceCN = sourceCN;
    }

    public static String getSource(String source){
        for (SourceEnum sourceEnum : SourceEnum.values()) {
            if(sourceEnum.getSource().equals(source)){
                return sourceEnum.getSourceCN();
            }
        }
        return null;
    }
}
