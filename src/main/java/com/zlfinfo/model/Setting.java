package com.zlfinfo.model;

import java.io.Serializable;

public class Setting implements Serializable {
    private String username;

    private String isUpdate;

    private String isShareloc;

    private String isShowimg;

    private String reserve1;

    private String reserve2;

    private String reserve3;

    private static final long serialVersionUID = 1L;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getIsUpdate() {
        return isUpdate;
    }

    public void setIsUpdate(String isUpdate) {
        this.isUpdate = isUpdate == null ? null : isUpdate.trim();
    }

    public String getIsShareloc() {
        return isShareloc;
    }

    public void setIsShareloc(String isShareloc) {
        this.isShareloc = isShareloc == null ? null : isShareloc.trim();
    }

    public String getIsShowimg() {
        return isShowimg;
    }

    public void setIsShowimg(String isShowimg) {
        this.isShowimg = isShowimg == null ? null : isShowimg.trim();
    }

    public String getReserve1() {
        return reserve1;
    }

    public void setReserve1(String reserve1) {
        this.reserve1 = reserve1 == null ? null : reserve1.trim();
    }

    public String getReserve2() {
        return reserve2;
    }

    public void setReserve2(String reserve2) {
        this.reserve2 = reserve2 == null ? null : reserve2.trim();
    }

    public String getReserve3() {
        return reserve3;
    }

    public void setReserve3(String reserve3) {
        this.reserve3 = reserve3 == null ? null : reserve3.trim();
    }
}