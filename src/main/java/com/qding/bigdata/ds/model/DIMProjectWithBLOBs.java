package com.qding.bigdata.ds.model;

public class DIMProjectWithBLOBs extends DIMProject {
    private String firstRoomUploadDate;

    private String firstHouseNum;

    private String firstHasphoneHouseNum;

    private String kaOperationProject;

    private String pianquManager;

    private String pianquManagerDepart;

    private String pianquManagerCity;

    public String getFirstRoomUploadDate() {
        return firstRoomUploadDate;
    }

    public void setFirstRoomUploadDate(String firstRoomUploadDate) {
        this.firstRoomUploadDate = firstRoomUploadDate == null ? null : firstRoomUploadDate.trim();
    }

    public String getFirstHouseNum() {
        return firstHouseNum;
    }

    public void setFirstHouseNum(String firstHouseNum) {
        this.firstHouseNum = firstHouseNum == null ? null : firstHouseNum.trim();
    }

    public String getFirstHasphoneHouseNum() {
        return firstHasphoneHouseNum;
    }

    public void setFirstHasphoneHouseNum(String firstHasphoneHouseNum) {
        this.firstHasphoneHouseNum = firstHasphoneHouseNum == null ? null : firstHasphoneHouseNum.trim();
    }

    public String getKaOperationProject() {
        return kaOperationProject;
    }

    public void setKaOperationProject(String kaOperationProject) {
        this.kaOperationProject = kaOperationProject == null ? null : kaOperationProject.trim();
    }

    public String getPianquManager() {
        return pianquManager;
    }

    public void setPianquManager(String pianquManager) {
        this.pianquManager = pianquManager == null ? null : pianquManager.trim();
    }

    public String getPianquManagerDepart() {
        return pianquManagerDepart;
    }

    public void setPianquManagerDepart(String pianquManagerDepart) {
        this.pianquManagerDepart = pianquManagerDepart == null ? null : pianquManagerDepart.trim();
    }

    public String getPianquManagerCity() {
        return pianquManagerCity;
    }

    public void setPianquManagerCity(String pianquManagerCity) {
        this.pianquManagerCity = pianquManagerCity == null ? null : pianquManagerCity.trim();
    }
}