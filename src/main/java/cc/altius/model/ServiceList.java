/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.model;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author shrutika
 */
public class ServiceList implements Serializable {

    private List<Service> assignedList;
    private List<Service> nonAssignedList;

    public List<Service> getAssignedList() {
        return assignedList;
    }

    public void setAssignedList(List<Service> assignedList) {
        this.assignedList = assignedList;
    }

    public List<Service> getNonAssignedList() {
        return nonAssignedList;
    }

    public void setNonAssignedList(List<Service> nonAssignedList) {
        this.nonAssignedList = nonAssignedList;
    }
}