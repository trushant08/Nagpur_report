/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.service.impl;

import cc.altius.dao.AdminDao;
import cc.altius.model.Group;
import cc.altius.model.ReportType;
import cc.altius.model.ServerType;
import cc.altius.model.Service;
import cc.altius.service.AdminService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author shrutika
 */
@org.springframework.stereotype.Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    AdminDao adminDao;

    //Report:
    @Override
    public int addReportType(ReportType reportType) {
        return this.adminDao.addReportType(reportType);
    }

    @Override
    public List<ServerType> getServerTypeList() {
        return this.adminDao.getServerTypeList();
    }

    @Override
    public List<ReportType> getReportTypeList() {
        return this.adminDao.getReportTypeList();
    }

    @Override
    public void updateReportType(ReportType reportType) {
        this.adminDao.updateReportType(reportType);
    }

    @Override
    public ReportType getReportTypeObjByReportTypeId(int reportTypeId) {
        return this.adminDao.getReportTypeObjByReportTypeId(reportTypeId);
    }

    //Group:
    @Override
    public int addGroup(Group group) {
        return this.adminDao.addGroup(group);
    }

    @Override
    public List<Group> getGroupList() {
        return this.adminDao.getGroupList();
    }

    @Override
    public void updateGroup(Group group) {
        this.adminDao.updateGroup(group);
    }

    @Override
    public Group getGroupObjByGroupId(int groupId) {
        return this.adminDao.getGroupObjByGroupId(groupId);
    }

    //Service:
    @Override
    public int addService(Service service) {
        return this.adminDao.addService(service);
    }

    @Override
    public List<Service> getServiceList() {
        return this.adminDao.getServiceList();
    }

    @Override
    public void updateService(Service service) {
        this.adminDao.updateService(service);
    }

    @Override
    public Service getServiceObjByServiceId(int serviceId) {
        return this.adminDao.getServiceObjByServiceId(serviceId);
    }

    @Override
    public List<ReportType> getReportTypeListByserverTypeId(int serverTypeId) {
        return this.adminDao.getReportTypeListByserverTypeId(serverTypeId);
    }

    @Override
    public List<Group> getMappedGroupListByReportType(int reportTypeId, int serverTypeId) {
        return this.adminDao.getMappedGroupListByReportType(reportTypeId, serverTypeId);
    }

    @Override
    public List<Group> getGroupListNotMapped(int reportTypeId, int serverTypeId) {
        return this.adminDao.getGroupListNotMapped(reportTypeId, serverTypeId);
    }

    @Override
    public int insertReportTypeGroupMapping(int reportTypeId, String[] assignGroups, int serverTypeId) {
        return this.adminDao.insertReportTypeGroupMapping(reportTypeId, assignGroups, serverTypeId);
    }

    @Override
    public List<Group> getGroupListByserverTypeId(int serverTypeId) {
        return this.adminDao.getGroupListByserverTypeId(serverTypeId);
    }

    @Override
    public List<Service> getMappedServiceListByGroup(int groupId, int serverTypeId) {
        return this.adminDao.getMappedServiceListByGroup(groupId, serverTypeId);
    }

    @Override
    public List<Service> getServiceListNotMapped(int groupId, int serverTypeId) {
        return this.adminDao.getServiceListNotMapped(groupId, serverTypeId);
    }

    @Override
    public int insertGroupServiceMapping(int groupId, String[] assignServices, int serverTypeId) {
        return this.adminDao.insertGroupServiceMapping(groupId, assignServices, serverTypeId);
    }
}
