/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.model;

import java.io.Serializable;

/**
 *
 * @author shrutika
 */
public class ServerType implements Serializable {

    private int serverTypeId;
    private String serverTypeDesc;

    public int getServerTypeId() {
        return serverTypeId;
    }

    public void setServerTypeId(int serverTypeId) {
        this.serverTypeId = serverTypeId;
    }

    public String getServerTypeDesc() {
        return serverTypeDesc;
    }

    public void setServerTypeDesc(String serverTypeDesc) {
        this.serverTypeDesc = serverTypeDesc;
    }
}
