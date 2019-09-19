package com.qding.bigdata.ds.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "GCTFunnel", description = "漏斗分析")
public class GCTFunnel {
    @ApiModelProperty(value = "ID", example = "")
    private Integer id;
    @ApiModelProperty(value = "名称", example = "")
    private String name;
    @ApiModelProperty(value = "页面codes", example = "Opendoor,homePage")
    private String codes;
    @ApiModelProperty(value = "业态ID", example = "0 千丁APP | 1丁管家 | 2 丁老板 | 3 千丁云 PC 端")
    private Long productId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getCodes() {
        return codes;
    }

    public void setCodes(String codes) {
        this.codes = codes == null ? null : codes.trim();
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}