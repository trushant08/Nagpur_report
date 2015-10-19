/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.web.controller;

import cc.altius.model.Group;
import cc.altius.model.ReportType;
import cc.altius.model.ServerType;
import cc.altius.model.Service;
import cc.altius.service.AdminService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author shrutika
 */
@Controller
public class AdminController {

    @Autowired
    AdminService adminService;

    //Report:
    @RequestMapping(value = "reportTypeAdd.htm", method = RequestMethod.GET)
    public String showReportTypeAddPage(ModelMap modelMap) {
        List<ServerType> serverTypeList = this.adminService.getServerTypeList();
        modelMap.addAttribute("serverTypeList", serverTypeList);
        ReportType reportType = new ReportType();
        modelMap.addAttribute("reportType", reportType);
        reportType.setActive(true);
        return "reportTypeAdd";
    }

    @RequestMapping(value = "reportTypeAdd.htm", method = RequestMethod.POST)
    public String onReportTypeSubmit(@ModelAttribute("reportType") ReportType reportType) {
        this.adminService.addReportType(reportType);
        return "redirect:reportTypeList.htm";
    }

    @RequestMapping(value = "reportTypeList.htm", method = RequestMethod.GET)
    public String showReportTypeList(ModelMap modelMap) {
        List<ReportType> reportTypeList = this.adminService.getReportTypeList();
        modelMap.addAttribute("reportTypeList", reportTypeList);
        return "reportTypeList";
    }

    @RequestMapping(value = "reportTypeEdit.htm", method = RequestMethod.GET)
    public String onEditReportTypeGet(HttpServletRequest request, ModelMap modelMap) {
        int reportTypeId = ServletRequestUtils.getIntParameter(request, "reportTypeId", 0);

        ReportType reportType = this.adminService.getReportTypeObjByReportTypeId(reportTypeId);
        modelMap.addAttribute("reportType", reportType);
        List<ServerType> serverTypeList = this.adminService.getServerTypeList();
        modelMap.addAttribute("serverTypeList", serverTypeList);

        reportType.setActive(reportType.isActive());
        return "reportTypeEdit";
    }

    @RequestMapping(value = "reportTypeEdit.htm", method = RequestMethod.POST)
    public String onEditReportTypePost(ModelMap modelMap, @ModelAttribute("reportType") ReportType reportType) {
        this.adminService.updateReportType(reportType);

        return "redirect:reportTypeList.htm";
    }

    //Group:
    @RequestMapping(value = "groupAdd.htm", method = RequestMethod.GET)
    public String showGroupAddPage(ModelMap modelMap) {
        List<ServerType> serverTypeList = this.adminService.getServerTypeList();
        modelMap.addAttribute("serverTypeList", serverTypeList);
        Group group = new Group();
        modelMap.addAttribute("group", group);
        group.setActive(true);
        return "groupAdd";
    }

    @RequestMapping(value = "groupAdd.htm", method = RequestMethod.POST)
    public String onGroupSubmit(@ModelAttribute("group") Group group) {
        this.adminService.addGroup(group);
        return "redirect:groupList.htm";
    }

    @RequestMapping(value = "groupList.htm", method = RequestMethod.GET)
    public String showGroupList(ModelMap modelMap) {
        List<Group> groupList = this.adminService.getGroupList();
        modelMap.addAttribute("groupList", groupList);
        return "groupList";
    }

    @RequestMapping(value = "groupEdit.htm", method = RequestMethod.GET)
    public String onEditGroupGet(HttpServletRequest request, ModelMap modelMap) {
        int groupId = ServletRequestUtils.getIntParameter(request, "groupId", 0);

        Group group = this.adminService.getGroupObjByGroupId(groupId);
        modelMap.addAttribute("group", group);
        List<ServerType> serverTypeList = this.adminService.getServerTypeList();
        modelMap.addAttribute("serverTypeList", serverTypeList);

        group.setActive(group.isActive());
        return "groupEdit";
    }

    @RequestMapping(value = "groupEdit.htm", method = RequestMethod.POST)
    public String onEditGroupPost(ModelMap modelMap, @ModelAttribute("group") Group group) {
        this.adminService.updateGroup(group);
        return "redirect:groupList.htm";
    }

    //Service:
    @RequestMapping(value = "serviceAdd.htm", method = RequestMethod.GET)
    public String showServiceAddPage(ModelMap modelMap) {
        List<ServerType> serverTypeList = this.adminService.getServerTypeList();
        modelMap.addAttribute("serverTypeList", serverTypeList);
        Service service = new Service();
        modelMap.addAttribute("service", service);
        service.setActive(true);
        return "serviceAdd";
    }

    @RequestMapping(value = "serviceAdd.htm", method = RequestMethod.POST)
    public String onServiceSubmit(@ModelAttribute("service") Service service) {
        this.adminService.addService(service);
        return "redirect:serviceList.htm";
    }

    @RequestMapping(value = "serviceList.htm", method = RequestMethod.GET)
    public String showServiceList(ModelMap modelMap) {
        List<Service> serviceList = this.adminService.getServiceList();
        modelMap.addAttribute("serviceList", serviceList);
        return "serviceList";
    }

    @RequestMapping(value = "serviceEdit.htm", method = RequestMethod.GET)
    public String onEditServiceGet(HttpServletRequest request, ModelMap modelMap) {
        int serviceId = ServletRequestUtils.getIntParameter(request, "serviceId", 0);
        Service service = this.adminService.getServiceObjByServiceId(serviceId);
        modelMap.addAttribute("service", service);
        List<ServerType> serverTypeList = this.adminService.getServerTypeList();
        modelMap.addAttribute("serverTypeList", serverTypeList);
        service.setActive(service.isActive());
        return "serviceEdit";
    }

    @RequestMapping(value = "serviceEdit.htm", method = RequestMethod.POST)
    public String onEditServicePost(ModelMap modelMap, @ModelAttribute("service") Service service) {
        this.adminService.updateService(service);
        return "redirect:serviceList.htm";
    }

    //Mapping:
    @RequestMapping(value = "mapReportTypeGroup.htm", method = RequestMethod.GET)
    public String mappingGroup(ModelMap modelMap) {

        List<ServerType> serverTypeList = this.adminService.getServerTypeList();
        modelMap.addAttribute("serverTypeList", serverTypeList);
        return "mapReportTypeGroup";
    }

    @RequestMapping(value = "mapReportTypeGroup.htm", method = RequestMethod.POST)
    public String mappingGroupPost(HttpServletRequest request) {
        int reportTypeId = ServletRequestUtils.getIntParameter(request, "reportTypeId", 0);
        int serverTypeId = ServletRequestUtils.getIntParameter(request, "serverTypeId", 0);
        String[] assignGroups = ServletRequestUtils.getStringParameter(request, "finalAssignGroups", "").split(",");
        int result = this.adminService.insertReportTypeGroupMapping(reportTypeId, assignGroups, serverTypeId);
        if (result == 0) {
            return "mapReportTypeGroup";
        } else {
            return "redirect:/index.htm?&msg=msg.successfullyAddedData";
        }
    }

    @RequestMapping(value = "mapGroupService.htm", method = RequestMethod.GET)
    public String mappingService(ModelMap modelMap) {
        List<ServerType> serverTypeList = this.adminService.getServerTypeList();
        modelMap.addAttribute("serverTypeList", serverTypeList);
        return "mapGroupService";
    }

    @RequestMapping(value = "mapGroupService.htm", method = RequestMethod.POST)
    public String mappingServicePost(HttpServletRequest request) {
        int groupId = ServletRequestUtils.getIntParameter(request, "groupId", 0);
        int serverTypeId = ServletRequestUtils.getIntParameter(request, "serverTypeId", 0);
        String[] assignServices = ServletRequestUtils.getStringParameter(request, "finalAssignServices", "").split(",");
        int result = this.adminService.insertGroupServiceMapping(groupId, assignServices, serverTypeId);
        if (result == 0) {
            return "mapGroupService";
        } else {
            return "redirect:/index.htm?&msg=msg.successfullyAddedData";
        }
    }
}
