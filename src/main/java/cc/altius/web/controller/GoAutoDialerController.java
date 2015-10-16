/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.web.controller;

import cc.altius.common.utils.CommonUtils;
import cc.altius.model.Group;
import cc.altius.model.ReportHeader;
import cc.altius.model.ReportType;
import cc.altius.model.Service;
import cc.altius.service.AdminService;
import cc.altius.service.GoAutoDialerService;
import cc.altius.utils.DateUtils;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author shrutika
 */
@Controller
public class GoAutoDialerController {
    
    @Autowired
    GoAutoDialerService goAutoDialerService;
    
    @Autowired
    AdminService adminService;
    
    private static int TIME_IST = 0;
    private static int TIME_PST = 1;
    private static int TIME_EST = 2;
    private static String YMDHM = "yyyy-MM-dd HH:mm";

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }
    
    @RequestMapping(value = "goAutoDialerReport.htm", method = RequestMethod.GET)
    public String showPageGet(HttpServletRequest request, ModelMap modelMap) 
    {
        int id = ServletRequestUtils.getIntParameter(request, "id", 0);
        modelMap.addAttribute("id", id);
        List<ReportType> reportTypeList = this.adminService.getReportTypeListByserverTypeId(id);
        modelMap.addAttribute("reportTypeList", reportTypeList);
        
        ReportHeader reportHeader=new ReportHeader();
        reportHeader.setEndDate(DateUtils.getCurrentDateObject(DateUtils.IST));
        reportHeader.setStartDate(DateUtils.getOffsetFromCurrentDateObject(DateUtils.IST, -1));
        modelMap.addAttribute("reportHeader", reportHeader);
        
        return "goAutoDialerReport";
    }
    
    @RequestMapping(value = "goAutoDialerReport.htm", method = RequestMethod.POST)
    public String postPage(HttpServletRequest request,
                          @ModelAttribute("reportHeader") ReportHeader reportHeader, 
                           ModelMap modelMap) throws ParseException {

        int id = ServletRequestUtils.getIntParameter(request, "id", 0);
        modelMap.addAttribute("id", id);
        List<ReportType> reportTypeList = this.adminService.getReportTypeListByserverTypeId(id);
        modelMap.addAttribute("reportTypeList", reportTypeList);

        int zoneId = reportHeader.getZone().getZoneId();
        modelMap.addAttribute("zoneId", zoneId);

        Date startDateObj = reportHeader.getStartDate();
        modelMap.addAttribute("startDate", startDateObj);
        String startDate = DateUtils.convertDateToString(startDateObj, DateUtils.YMDHM);
        
        Date endDateObj = reportHeader.getEndDate();
        modelMap.addAttribute("endDate", endDateObj);
        String endDate = DateUtils.convertDateToString(endDateObj, YMDHM);
        
        int reportTypeId = reportHeader.getReportType().getReportTypeId();
        modelMap.addAttribute("reportTypeId", reportTypeId);

        String selectedServiceIds[] = ServletRequestUtils.getStringParameters(request, "selectedServiceIds");

        List<Service> selectedServiceList = new LinkedList<Service>();
        
        for (String serviceId : selectedServiceIds) {
            Service s = new Service();
            s.setServerServiceId(serviceId);
            selectedServiceList.add(s);
        }

        modelMap.addAttribute("selectedServiceIds", selectedServiceIds);
        
        reportHeader.setService(selectedServiceList);
        
        List<Group> groupList = this.adminService.getMappedGroupListByReportType(reportTypeId, id);
        modelMap.addAttribute("groupList", groupList);
        
        modelMap.addAttribute("groupId", reportHeader.getGroup().getGroupId());

        int groupId = reportHeader.getGroup().getGroupId();
        List<Service> serviceList = this.adminService.getMappedServiceListByGroup(groupId,id);
        modelMap.addAttribute("serviceList", serviceList);

        if (zoneId == TIME_IST) {
            String startDateIST_To_EST = CommonUtils.dateConverte_IST_To_EST(startDate);
            String endDateIST_To_EST = CommonUtils.dateConverte_IST_To_EST(endDate);
            List<Map<String, Object>> list = null;

            if (reportTypeId == 1 || reportTypeId == 6) {
                list = this.goAutoDialerService.goAutoDialerInboundReport(startDateIST_To_EST, endDateIST_To_EST, selectedServiceIds, id);
            } else if (reportTypeId == 2|| reportTypeId == 7) {
                list = this.goAutoDialerService.getGoAutoDialerInboundList(startDateIST_To_EST, endDateIST_To_EST, selectedServiceIds, id);
            } else if (reportTypeId == 3|| reportTypeId == 4 || reportTypeId == 8 || reportTypeId == 9) {
                list = this.goAutoDialerService.goAutoDialerOutboundReport(startDateIST_To_EST, endDateIST_To_EST, selectedServiceIds, reportTypeId, id);
            } else{
                list = this.goAutoDialerService.getAgentPerformanceReport(startDateIST_To_EST, endDateIST_To_EST, selectedServiceIds, id);
            }
                        
            for (Map<String, Object> m : list) {
                Date t = (Date) m.get("call_date");
                Date convertedDate = CommonUtils.dateConverte_EST_To_IST(t);
                m.put("convertedCallStartDt", convertedDate);
            }
            modelMap.addAttribute("list", list);

            if (reportTypeId == 1 || reportTypeId == 6) {
                return "goAutoDialerInboundReport";
            } else if (reportTypeId == 2|| reportTypeId == 7) {
                return "goAutoDialerInboundReportList";
            } else if (reportTypeId == 3|| reportTypeId == 4 || reportTypeId == 8 || reportTypeId == 9) {
                return "goAutoDialerOutboundReport";
            } else {
                return "getAgentPerformanceReport";
            }
        } else if (zoneId == TIME_EST) {

            List<Map<String, Object>> list = null;
            
            if (reportTypeId == 1 || reportTypeId == 6) {
                list = this.goAutoDialerService.goAutoDialerInboundReport(startDate, endDate, selectedServiceIds, id);
            } else if (reportTypeId == 2|| reportTypeId == 7) {
                list = this.goAutoDialerService.getGoAutoDialerInboundList(startDate, endDate, selectedServiceIds, id);    
            } else if (reportTypeId == 3|| reportTypeId == 4 || reportTypeId == 8 || reportTypeId == 9) {
                list = this.goAutoDialerService.goAutoDialerOutboundReport(startDate, endDate, selectedServiceIds, reportTypeId, id);    
            } else {
                list = this.goAutoDialerService.getAgentPerformanceReport(startDate, endDate, selectedServiceIds, id);
            }
            
            for (Map<String, Object> m : list) {
                Date t = (Date) m.get("call_date");
                m.put("convertedCallStartDt", t);
            }
            modelMap.addAttribute("list", list);

            if (reportTypeId == 1 || reportTypeId == 6) {
                return "goAutoDialerInboundReport";
            } else if (reportTypeId == 2|| reportTypeId == 7) {
                return "goAutoDialerInboundReportList";
            } else if (reportTypeId == 3|| reportTypeId == 4 || reportTypeId == 8 || reportTypeId == 9) {
                return "goAutoDialerOutboundReport";
            } else {
                return "getAgentPerformanceReport";
            }
        } else {
            String startDatePST_To_EST = CommonUtils.dateConverte_PST_To_EST(startDate);
            String endDatePST_To_EST = CommonUtils.dateConverte_PST_To_EST(endDate);

            List<Map<String, Object>> list = null;
            
            if (reportTypeId == 1 || reportTypeId == 6) {
                list = this.goAutoDialerService.goAutoDialerInboundReport(startDatePST_To_EST, endDatePST_To_EST, selectedServiceIds, id);
            } else if (reportTypeId == 2|| reportTypeId == 7) {
                list = this.goAutoDialerService.getGoAutoDialerInboundList(startDatePST_To_EST, endDatePST_To_EST, selectedServiceIds, id);    
            } else if (reportTypeId == 3 || reportTypeId ==4 || reportTypeId == 8 || reportTypeId == 9) {
                list = this.goAutoDialerService.goAutoDialerOutboundReport(startDatePST_To_EST, endDatePST_To_EST, selectedServiceIds, reportTypeId, id);
            } else {
                list = this.goAutoDialerService.getAgentPerformanceReport(startDatePST_To_EST, endDatePST_To_EST, selectedServiceIds, id);
            }
            
            for (Map<String, Object> m : list) {
                Date t = (Date) m.get("call_date");
                Date convertedDate = CommonUtils.dateConverte_EST_To_PST(t);
                m.put("convertedCallStartDt", convertedDate);
            }
            modelMap.addAttribute("list", list);
            
            if (reportTypeId == 1 || reportTypeId == 6) {
                return "goAutoDialerInboundReport";
            } else if (reportTypeId == 2|| reportTypeId == 7) {
                return "goAutoDialerInboundReportList";
            } else if (reportTypeId == 3|| reportTypeId == 4 || reportTypeId == 8 || reportTypeId == 9) {
                return "goAutoDialerOutboundReport";
            } else {
                return "getAgentPerformanceReport";
            }
        }

    }
}