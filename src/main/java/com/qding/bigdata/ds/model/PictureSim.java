package com.qding.bigdata.ds.model;

public class PictureSim implements Comparable<PictureSim> {

  
  public PictureSim(String spu, Double sim) {
    super();
    this.spu = spu;
    this.sim = sim;
  }
  private String spu;
  private Double sim;
  public String getSpu() {
    return spu;
  }
  public void setSpu(String spu) {
    this.spu = spu;
  }
  public Double getSim() {
    return sim;
  }
  public void setSim(Double sim) {
    this.sim = sim;
  }
  @Override
  public int compareTo(PictureSim o1) {
    return o1.getSim().compareTo(this.sim);
  }
  
}
