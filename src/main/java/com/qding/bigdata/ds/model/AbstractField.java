package com.qding.bigdata.ds.model;

public class AbstractField implements Comparable<AbstractField> {
  private Integer sort;

  public Integer getSort() {
    return sort;
  }

  public void setSort(Integer sort) {
    this.sort = sort;
  }

  @Override
  public int compareTo(AbstractField obj) {
    if (obj.getSort() > this.getSort())
      return -1;
    else
      return 1;
  }

}
