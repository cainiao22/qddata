/**
 * 
 */
package com.qding.bigdata.ds.model;

/**
 * @author wangjunping
 *
 */
public class QueryField  extends AbstractField{

  private Integer id;
  private String displayName;

  /**
   * 0:普通数值 1:日期类型 2:维度关联
   */
  private Integer type;

  private Dimension dimension;
  private String column;

 

  private String segment;



  public String getSegment() {
    return segment;
  }

  public void setSegment(String segment) {
    this.segment = segment;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  public Integer getType() {
    return type;
  }

  public void setType(Integer type) {
    this.type = type;
  }

  public String getColumn() {
    return column;
  }

  public void setColumn(String column) {
    this.column = column;
  }


  public Dimension getDimension() {
    return dimension;
  }

  public void setDimension(Dimension dimension) {
    this.dimension = dimension;
  }


}
