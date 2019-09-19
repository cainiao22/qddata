package com.qding.bigdata.ds.model;

import java.util.Date;

public class DIMSKU {
    private Long id;

    private Long fskuId;

    private Long wareId;

    private String wareName;

    private Long providerId;

    private String shopId;

    private String special1;

    private String special2;

    private String special3;

    private String special4;

    private String special5;

    private Long isMain;

    private Long isDel;

    private Date createAt;

    private Date updateAt;

    private String skuCode;

    private Long propId;

    private String propCode;

    private Integer version;

    private String mainProductNo;

    private String attach;

    private String thirdSkuBn;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFskuId() {
        return fskuId;
    }

    public void setFskuId(Long fskuId) {
        this.fskuId = fskuId;
    }

    public Long getWareId() {
        return wareId;
    }

    public void setWareId(Long wareId) {
        this.wareId = wareId;
    }

    public String getWareName() {
        return wareName;
    }

    public void setWareName(String wareName) {
        this.wareName = wareName == null ? null : wareName.trim();
    }

    public Long getProviderId() {
        return providerId;
    }

    public void setProviderId(Long providerId) {
        this.providerId = providerId;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId == null ? null : shopId.trim();
    }

    public String getSpecial1() {
        return special1;
    }

    public void setSpecial1(String special1) {
        this.special1 = special1 == null ? null : special1.trim();
    }

    public String getSpecial2() {
        return special2;
    }

    public void setSpecial2(String special2) {
        this.special2 = special2 == null ? null : special2.trim();
    }

    public String getSpecial3() {
        return special3;
    }

    public void setSpecial3(String special3) {
        this.special3 = special3 == null ? null : special3.trim();
    }

    public String getSpecial4() {
        return special4;
    }

    public void setSpecial4(String special4) {
        this.special4 = special4 == null ? null : special4.trim();
    }

    public String getSpecial5() {
        return special5;
    }

    public void setSpecial5(String special5) {
        this.special5 = special5 == null ? null : special5.trim();
    }

    public Long getIsMain() {
        return isMain;
    }

    public void setIsMain(Long isMain) {
        this.isMain = isMain;
    }

    public Long getIsDel() {
        return isDel;
    }

    public void setIsDel(Long isDel) {
        this.isDel = isDel;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode == null ? null : skuCode.trim();
    }

    public Long getPropId() {
        return propId;
    }

    public void setPropId(Long propId) {
        this.propId = propId;
    }

    public String getPropCode() {
        return propCode;
    }

    public void setPropCode(String propCode) {
        this.propCode = propCode == null ? null : propCode.trim();
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getMainProductNo() {
        return mainProductNo;
    }

    public void setMainProductNo(String mainProductNo) {
        this.mainProductNo = mainProductNo == null ? null : mainProductNo.trim();
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach == null ? null : attach.trim();
    }

    public String getThirdSkuBn() {
        return thirdSkuBn;
    }

    public void setThirdSkuBn(String thirdSkuBn) {
        this.thirdSkuBn = thirdSkuBn == null ? null : thirdSkuBn.trim();
    }
}