/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.model;

/**
 *
 * @author citius
 */
public class MaricoLeads {

    private int maricoLeadId;
    private String uniqueId;
    private String retailerName;
    private String beatDiscription;
    private String distributorName;
    private String distributorCode;
    private String phoneNo;
    private String dsrStatus;
    private String listId;
    private String modifiedDate;
    private String insertedInDialerDate;
    private String createdDate;
    private String leadStatus;

    public MaricoLeads() {
    }

    public int getMaricoLeadId() {
        return maricoLeadId;
    }

    public void setMaricoLeadId(int maricoLeadId) {
        this.maricoLeadId = maricoLeadId;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getRetailerName() {
        return retailerName;
    }

    public void setRetailerName(String retailerName) {
        this.retailerName = retailerName;
    }

    public String getBeatDiscription() {
        return beatDiscription;
    }

    public void setBeatDiscription(String beatDiscription) {
        this.beatDiscription = beatDiscription;
    }

    public String getDistributorName() {
        return distributorName;
    }

    public void setDistributorName(String distributorName) {
        this.distributorName = distributorName;
    }

    public String getDistributorCode() {
        return distributorCode;
    }

    public void setDistributorCode(String distributorCode) {
        this.distributorCode = distributorCode;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getDsrStatus() {
        return dsrStatus;
    }

    public void setDsrStatus(String dsrStatus) {
        this.dsrStatus = dsrStatus;
    }

    public String getListId() {
        return listId;
    }

    public void setListId(String listId) {
        this.listId = listId;
    }

    public String getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getInsertedInDialerDate() {
        return insertedInDialerDate;
    }

    public void setInsertedInDialerDate(String insertedInDialerDate) {
        this.insertedInDialerDate = insertedInDialerDate;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getLeadStatus() {
        return leadStatus;
    }

    public void setLeadStatus(String leadStatus) {
        this.leadStatus = leadStatus;
    }

    @Override
    public String toString() {
        return "MaricoLeads{" + "maricoLeadId=" + maricoLeadId + ", uniqueId=" + uniqueId + ", retailerName=" + retailerName + ", beatDiscription=" + beatDiscription + ", distributorName=" + distributorName + ", distributorCode=" + distributorCode + ", phoneNo=" + phoneNo + ", dsrStatus=" + dsrStatus + ", listId=" + listId + ", modifiedDate=" + modifiedDate + ", insertedInDialerDate=" + insertedInDialerDate + ", createdDate=" + createdDate + ", leadStatus=" + leadStatus + '}';
    }
}
