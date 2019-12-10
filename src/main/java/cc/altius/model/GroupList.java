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
public class GroupList implements Serializable {

    private List<Group> assignedList;
    private List<Group> nonAssignedList;

    public List<Group> getAssignedList() {
        return assignedList;
    }

    public void setAssignedList(List<Group> assignedList) {
        this.assignedList = assignedList;
    }

    public List<Group> getNonAssignedList() {
        return nonAssignedList;
    }

    public void setNonAssignedList(List<Group> nonAssignedList) {
        this.nonAssignedList = nonAssignedList;
    }
}
