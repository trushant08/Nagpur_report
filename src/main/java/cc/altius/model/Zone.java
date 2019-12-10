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
public class Zone implements Serializable {

    public static final int IST = 0;
    public static final int PST = 1;
    public static final int EST = 2;
    private int zoneId;
    private String zoneDesc;

    public String getZoneDesc() {
        return zoneDesc;
    }

    public int getZoneId() {
        return zoneId;
    }

    public void setZoneId(int zoneId) {
        this.zoneId = zoneId;
        switch (zoneId) {
            case 0:
                this.zoneDesc = "IST";
                break;
            case 1:
                this.zoneDesc = "PST";
                break;
            case 2:
                this.zoneDesc = "EST";
                break;
        }
    }
}
