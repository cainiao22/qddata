package com.qding.bigdata.ds.model;

public class DIMLiveClassify {
    private Long id;

    private String name;

    private Long articleNum;

    private Long realRevertNum;

    private Long revertNum;

    private Long realCollectNum;

    private Long collectNum;

    private Long realReadNum;

    private Long readNum;

    private Long realLikeNum;

    private Long likeNum;

    private Long realCommentNum;

    private Long commentNum;

    private Long creatAt;

    private String creatBy;

    private Long updateAt;

    private String updateBy;

    private Boolean isDel;

    private Long subscribeNum;

    private Long realSubscribeNum;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Long getArticleNum() {
        return articleNum;
    }

    public void setArticleNum(Long articleNum) {
        this.articleNum = articleNum;
    }

    public Long getRealRevertNum() {
        return realRevertNum;
    }

    public void setRealRevertNum(Long realRevertNum) {
        this.realRevertNum = realRevertNum;
    }

    public Long getRevertNum() {
        return revertNum;
    }

    public void setRevertNum(Long revertNum) {
        this.revertNum = revertNum;
    }

    public Long getRealCollectNum() {
        return realCollectNum;
    }

    public void setRealCollectNum(Long realCollectNum) {
        this.realCollectNum = realCollectNum;
    }

    public Long getCollectNum() {
        return collectNum;
    }

    public void setCollectNum(Long collectNum) {
        this.collectNum = collectNum;
    }

    public Long getRealReadNum() {
        return realReadNum;
    }

    public void setRealReadNum(Long realReadNum) {
        this.realReadNum = realReadNum;
    }

    public Long getReadNum() {
        return readNum;
    }

    public void setReadNum(Long readNum) {
        this.readNum = readNum;
    }

    public Long getRealLikeNum() {
        return realLikeNum;
    }

    public void setRealLikeNum(Long realLikeNum) {
        this.realLikeNum = realLikeNum;
    }

    public Long getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(Long likeNum) {
        this.likeNum = likeNum;
    }

    public Long getRealCommentNum() {
        return realCommentNum;
    }

    public void setRealCommentNum(Long realCommentNum) {
        this.realCommentNum = realCommentNum;
    }

    public Long getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(Long commentNum) {
        this.commentNum = commentNum;
    }

    public Long getCreatAt() {
        return creatAt;
    }

    public void setCreatAt(Long creatAt) {
        this.creatAt = creatAt;
    }

    public String getCreatBy() {
        return creatBy;
    }

    public void setCreatBy(String creatBy) {
        this.creatBy = creatBy == null ? null : creatBy.trim();
    }

    public Long getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Long updateAt) {
        this.updateAt = updateAt;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy == null ? null : updateBy.trim();
    }

    public Boolean getIsDel() {
        return isDel;
    }

    public void setIsDel(Boolean isDel) {
        this.isDel = isDel;
    }

    public Long getSubscribeNum() {
        return subscribeNum;
    }

    public void setSubscribeNum(Long subscribeNum) {
        this.subscribeNum = subscribeNum;
    }

    public Long getRealSubscribeNum() {
        return realSubscribeNum;
    }

    public void setRealSubscribeNum(Long realSubscribeNum) {
        this.realSubscribeNum = realSubscribeNum;
    }
}