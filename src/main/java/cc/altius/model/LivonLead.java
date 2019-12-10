/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.model;

import java.io.Serializable;

/**
 *
 * @author citius
 */
public class LivonLead implements Serializable {

    private static final long serialVersionUID = 1L;
    private String Retailer_Name;
    private String Phone_No;
    private String Unique_ID;
    private String BeatDescription;
    private String Distributor_name;
    private String Distributor_code;
    private String dsr_status;
    private String list_id;

    public LivonLead(String Retailer_Name, String Phone_No, String Unique_ID, String BeatDescription, String Distributor_name, String Distributor_code, String dsr_status, String list_id) {
        this.Retailer_Name = Retailer_Name;
        this.Phone_No = Phone_No;
        this.Unique_ID = Unique_ID;
        this.BeatDescription = BeatDescription;
        this.Distributor_name = Distributor_name;
        this.Distributor_code = Distributor_code;
        this.dsr_status = dsr_status;
        this.list_id = list_id;
    }

    public LivonLead() {
    }

    public String getRetailer_Name() {
        return Retailer_Name;
    }

    public void setRetailer_Name(String Retailer_Name) {
        this.Retailer_Name = Retailer_Name;
    }

    public String getPhone_No() {
        return Phone_No;
    }

    public void setPhone_No(String Phone_No) {
        this.Phone_No = Phone_No;
    }

    public String getUnique_ID() {
        return Unique_ID;
    }

    public void setUnique_ID(String Unique_ID) {
        this.Unique_ID = Unique_ID;
    }

    public String getBeatDescription() {
        return BeatDescription;
    }

    public void setBeatDescription(String BeatDescription) {
        this.BeatDescription = BeatDescription;
    }

    public String getDistributor_name() {
        return Distributor_name;
    }

    public void setDistributor_name(String Distributor_name) {
        this.Distributor_name = Distributor_name;
    }

    public String getDistributor_code() {
        return Distributor_code;
    }

    public void setDistributor_code(String Distributor_code) {
        this.Distributor_code = Distributor_code;
    }

    public String getDsr_status() {
        return dsr_status;
    }

    public void setDsr_status(String dsr_status) {
        this.dsr_status = dsr_status;
    }

    public String getList_id() {
        return list_id;
    }

    public void setList_id(String list_id) {
        this.list_id = list_id;
    }

    @Override
    public String toString() {
        return "LivonLead{" + "Retailer_Name=" + Retailer_Name + ", Phone_No=" + Phone_No + ", Unique_ID=" + Unique_ID + ", BeatDescription=" + BeatDescription + ", Distributor_name=" + Distributor_name + ", Distributor_code=" + Distributor_code + ", dsr_status=" + dsr_status + ", list_id=" + list_id + '}';
    }
}
