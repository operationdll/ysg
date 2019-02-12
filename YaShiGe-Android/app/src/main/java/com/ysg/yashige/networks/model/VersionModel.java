package com.ysg.yashige.networks.model;

/**
 * Created by apple on 2017/9/15.
 */

public class VersionModel {
    private boolean isMust;
    private String doneTitle;
    private String cancelTitle;
    private String newDesc;
    private String link;
    private String desc;
    private String version;

    public VersionModel() {
    }

    public VersionModel(boolean isMust, String doneTitle, String cancelTitle, String newDesc, String link, String desc, String version) {
        this.isMust = isMust;
        this.doneTitle = doneTitle;
        this.cancelTitle = cancelTitle;
        this.newDesc = newDesc;
        this.link = link;
        this.desc = desc;
        this.version = version;
    }

    public boolean isMust() {
        return isMust;
    }

    public void setMust(boolean must) {
        isMust = must;
    }

    public String getDoneTitle() {
        return doneTitle;
    }

    public void setDoneTitle(String doneTitle) {
        this.doneTitle = doneTitle;
    }

    public String getCancelTitle() {
        return cancelTitle;
    }

    public void setCancelTitle(String cancelTitle) {
        this.cancelTitle = cancelTitle;
    }

    public String getNewDesc() {
        return newDesc;
    }

    public void setNewDesc(String newDesc) {
        this.newDesc = newDesc;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
