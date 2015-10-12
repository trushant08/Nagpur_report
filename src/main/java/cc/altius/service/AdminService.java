/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.service;

import cc.altius.model.Group;
import cc.altius.model.ReportType;
import cc.altius.model.ServerType;
import cc.altius.model.Service;
import java.util.List;

/**
 *
 * @author shrutika
 */
public interface AdminService {

    //Report:
    public int addReportType(ReportType reportType);
    
    public List<ServerType> getServerTypeList();

    public List<ReportType> getReportTypeList();

    public void updateReportType(ReportType reportType);

    public ReportType getReportTypeObjByReportTypeId(int reportTypeId);

    //Group:
    public int addGroup(Group group);

    public List<Group> getGroupList();

    public void updateGroup(Group group);

    public Group getGroupObjByGroupId(int groupId);

    //Service:
    public int addService(Service service);

    public List<Service> getServiceList();

    public void updateService(Service service);

    public Service getServiceObjByServiceId(int serviceId);
    
    //Mapping:
    public List<ReportType> getReportTypeListByserverTypeId(int serverTypeId);
    
    public List<Group> getMappedGroupListByReportType(int reportTypeId, int serverTypeId);
    
    public List<Group> getGroupListNotMapped(int reportTypeId, int serverTypeId);
    
    public int insertReportTypeGroupMapping(int reportTypeId, String[] assignGroups, int serverTypeId);
    
    public List<Group> getGroupListByserverTypeId(int serverTypeId);
    
    public List<Service> getMappedServiceListByGroup(int groupId, int serverTypeId);
    
    public List<Service> getServiceListNotMapped(int groupId, int serverTypeId);
    
    public int insertGroupServiceMapping(int groupId, String[] assignServices, int serverTypeId);
}
