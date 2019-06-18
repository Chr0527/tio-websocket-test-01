package com.chryl.server.po;

import java.io.Serializable;

/**
 * Created By Chr on 2019/6/18.
 */
public class SbInfo implements Serializable {

    private static final long serialVersionUID = -1633817450360411887L;
    private String sbId;
    private String sbDescription;
    private String sbState;


    public String getSbId() {
        return sbId;
    }

    public void setSbId(String sbId) {
        this.sbId = sbId;
    }

    public String getSbDescription() {
        return sbDescription;
    }

    public void setSbDescription(String sbDescription) {
        this.sbDescription = sbDescription;
    }

    public String getSbState() {
        return sbState;
    }

    public void setSbState(String sbState) {
        this.sbState = sbState;
    }

    @Override
    public String toString() {
        return "SbInfo{" +
                "sbId='" + sbId + '\'' +
                ", sbDescription='" + sbDescription + '\'' +
                ", sbState='" + sbState + '\'' +
                '}';
    }
}
