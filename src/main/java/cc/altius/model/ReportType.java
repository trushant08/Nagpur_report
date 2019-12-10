/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.model;

import java.io.Serializable;

/**
 *
 * @author sagar
 */
public class ReportType implements Serializable {

    private int reportTypeId;
    private String reportTypeDesc;
    private ServerType serverType;
    private boolean active;

    public String getReportTypeDesc() {
        return reportTypeDesc;
    }

    public void setReportTypeDesc(String reportTypeDesc) {
        this.reportTypeDesc = reportTypeDesc;
    }

    public int getReportTypeId() {
        return reportTypeId;
    }

    public void setReportTypeId(int reportTypeId) {
        this.reportTypeId = reportTypeId;
    }

    public ServerType getServerType() {
        return serverType;
    }

    public void setServerType(ServerType serverType) {
        this.serverType = serverType;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
