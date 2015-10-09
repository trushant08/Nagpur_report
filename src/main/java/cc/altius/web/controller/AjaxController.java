/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.web.controller;

import cc.altius.model.Group;
import cc.altius.model.GroupList;
import cc.altius.model.ReportType;
import cc.altius.model.Service;
import cc.altius.model.ServiceList;
import cc.altius.service.AdminService;
import cc.altius.service.GoAutoDialerService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author shrutika
 */
@Controller
public class AjaxController {

    @Autowired
    AdminService adminService;

    @RequestMapping(value = "ajaxGetGroupListForReportTypeId.htm", method = RequestMethod.GET)
    public @ResponseBody
    String doAjaxGetGroupListForReportTypeId(@RequestParam(value = "reportTypeId") int reportTypeId,
                                             @RequestParam(value = "id") int id) {

        String json;
        List<Group> groupList;
        groupList = this.adminService.getMappedGroupListByReportType(reportTypeId, id);

        Gson gson = new Gson();
        Type typeList = new TypeToken<List>() {
        }.getType();
        json = gson.toJson(groupList, typeList);
        return json;

    }

    @RequestMapping(value = "ajaxGetServiceListForGroupId.htm", method = RequestMethod.GET)
    public @ResponseBody
    String doAjaxGetServiceListForGroupId(@RequestParam(value = "groupId") int groupId,
                                          @RequestParam(value = "id") int id) {
        String json;
        List<Service> serviceList = this.adminService.getMappedServiceListByGroup(groupId,id);

        Gson gson = new Gson();
        Type typeList = new TypeToken<List>() {
        }.getType();
        json = gson.toJson(serviceList, typeList);
        return json;
    }

    @RequestMapping(value = "ajaxGetGroupListByReportTypeId.htm", method = RequestMethod.GET)
    public @ResponseBody
    String doAjaxGetGroupListByReportTypeId(@RequestParam(value = "reportTypeId") int reportTypeId, 
                                            @RequestParam(value = "serverTypeId")int serverTypeId) {
        String json;
        List<Group> assignedList = this.adminService.getMappedGroupListByReportType(reportTypeId,serverTypeId);
        List<Group> nonAssignedList = this.adminService.getGroupListNotMapped(reportTypeId,serverTypeId);
        GroupList groupList = new GroupList();
        groupList.setAssignedList(assignedList);
        groupList.setNonAssignedList(nonAssignedList);

        Gson gson = new Gson();
        Type typeList = new TypeToken<GroupList>() {
        }.getType();
        json = gson.toJson(groupList, typeList);
        System.out.println("json" +json);
        return json;

    }

    @RequestMapping(value = "ajaxGetServiceListByGroupId.htm", method = RequestMethod.GET)
    public @ResponseBody
    String doAjaxGetServiceListByGroupId(@RequestParam(value = "groupId") int groupId,
                                         @RequestParam(value = "serverTypeId")int serverTypeId) {

        String json;
        List<Service> assignedList = this.adminService.getMappedServiceListByGroup(groupId, serverTypeId);
        List<Service> nonAssignedList = this.adminService.getServiceListNotMapped(groupId, serverTypeId);
        ServiceList serviceList = new ServiceList();
        serviceList.setAssignedList(assignedList);
        serviceList.setNonAssignedList(nonAssignedList);

        Gson gson = new Gson();
        Type typeList = new TypeToken<ServiceList>() {
        }.getType();
        json = gson.toJson(serviceList, typeList);
        return json;

    }

    @RequestMapping(value = "ajaxGetReportTypeListForServerTypeId.htm", method = RequestMethod.GET)
    public @ResponseBody
    String doAjaxGetReportTypeListForServerTypeId(@RequestParam(value = "serverTypeId") int serverTypeId) {

        String json;
        List<ReportType> reportTypeList = this.adminService.getReportTypeListByserverTypeId(serverTypeId);

        Gson gson = new Gson();
        Type typeList = new TypeToken<List>() {
        }.getType();
        json = gson.toJson(reportTypeList, typeList);
        
        return json;

    }
    
    @RequestMapping(value = "ajaxGetGroupListForServerTypeId.htm", method = RequestMethod.GET)
    public @ResponseBody
    String doAjaxGetGroupListForServerTypeId(@RequestParam(value = "serverTypeId") int serverTypeId) {

        String json;
        List<Group> groupList = this.adminService.getGroupListByserverTypeId(serverTypeId);

        Gson gson = new Gson();
        Type typeList = new TypeToken<List>() {
        }.getType();
        json = gson.toJson(groupList, typeList);
        
        return json;

    }
}
