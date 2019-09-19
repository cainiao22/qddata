package com.qding.bigdata.ds.model;

/**
 * Created by QDHL on 2017/8/10.
 */
public class SchemaFiled {

    private String column_name;
    private String data_type;
    private String column_comment;

    public String getColumn_name() {
        return column_name;
    }

    public void setColumn_name(String column_name) {
        this.column_name = column_name;
    }

    public String getData_type() {
        return data_type;
    }

    public void setData_type(String data_type) {
        this.data_type = data_type;
    }

    public String getColumn_comment() {
        return column_comment;
    }

    public void setColumn_comment(String column_comment) {
        this.column_comment = column_comment;
    }

    @Override
    public String toString() {
        return "SchemaFiled{" +
                "column_name='" + column_name + '\'' +
                ", data_type='" + data_type + '\'' +
                ", column_comment='" + column_comment + '\'' +
                '}';
    }
}
