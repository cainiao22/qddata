package com.qding.bigdata.ds.model;

public class PortraitDictionaries extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3349394179933753380L;
	private String example_id;
	private String example_name;
	private String source;
	private Integer sortno;


    public Integer getSortno() {
        return sortno;
    }

    public void setSortno(Integer sortno) {
        this.sortno = sortno;
    }

    public String getExample_name() {
        return example_name;
    }

    public void setExample_name(String example_name) {
        this.example_name = example_name;
    }

    public String getExample_id() {
        return example_id;
    }

    public void setExample_id(String example_id) {
        this.example_id = example_id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
