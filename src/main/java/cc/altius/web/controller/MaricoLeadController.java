/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.web.controller;

import cc.altius.model.MaricoLeads;
import cc.altius.service.MaricoLeadService;
import cc.altius.utils.DateUtils;
import cc.altius.utils.LogUtils;
import cc.altius.utils.POI.POICell;
import cc.altius.utils.POI.POIRow;
import cc.altius.utils.POI.POIWorkSheet;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author citius
 */
@Controller
public class MaricoLeadController {

    @Autowired
    private MaricoLeadService maricoLeadService;

    @RequestMapping(value = "maricoDialerReport.htm")
    public String showListofMaricoLead(HttpServletRequest request, ModelMap model) {
        String startDate = ServletRequestUtils.getStringParameter(request, "startDate", DateUtils.getCurrentDateString(DateUtils.IST, DateUtils.YMD));
        String endDate = ServletRequestUtils.getStringParameter(request, "endDate", DateUtils.getCurrentDateString(DateUtils.IST, DateUtils.YMD));
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        List<MaricoLeads> maricoLeadList = maricoLeadService.getMaricoLeadList(startDate, endDate);
        model.addAttribute("maricoLeadList", maricoLeadList);
        return "maricoDialerReport";
    }

    @RequestMapping(value = "maricoLeadReportExcel.htm")
    public void getAccessLogExcelReport(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
        try {
            String startDate = ServletRequestUtils.getStringParameter(request, "startDate", DateUtils.getCurrentDateString(DateUtils.IST, DateUtils.YMD));
            String endDate = ServletRequestUtils.getStringParameter(request, "endDate", DateUtils.getCurrentDateString(DateUtils.IST, DateUtils.YMD));

            List<MaricoLeads> maricoLeadList = maricoLeadService.getMaricoLeadList(startDate, endDate);

            OutputStream out = response.getOutputStream();
            response.setHeader("Content-Disposition", "attachment;filename=MaricoLeadReport-" + startDate + "_to_" + endDate + ".xls");
            response.setContentType("application/vnd.ms-excel");
            POIWorkSheet mySheet = new POIWorkSheet(out, "Marico Lead report");
            mySheet.setPrintTitle(false);
            POIRow headerRow = new POIRow(POIRow.HEADER_ROW);
            headerRow.addCell("Id");
            headerRow.addCell("Retailer Name");
            headerRow.addCell("Beat Description");
            headerRow.addCell("Distributor Name");
            headerRow.addCell("Distributor code");
            headerRow.addCell("DSR Status");
            headerRow.addCell("Phone No");
            headerRow.addCell("Created Date");
            headerRow.addCell("Pushed To Dialer Date");
            headerRow.addCell("Status");

            mySheet.addRow(headerRow);

            for (MaricoLeads data : maricoLeadList) {
                String status;
                String pushedToDialerDate = "";
                if (data.getLeadStatus().equals("0")) {
                    status = "New Lead";
                } else if (data.getLeadStatus().equals("1")) {
                    status = "Pushed to Dialer";
                    pushedToDialerDate = data.getInsertedInDialerDate();
                } else {
                    status = "Rejected";
                }
                POIRow dataRow = new POIRow();
                dataRow.addCell(data.getMaricoLeadId(), POICell.TYPE_INTEGER);
                dataRow.addCell(data.getRetailerName(), POICell.TYPE_TEXT);
                dataRow.addCell(data.getBeatDiscription(), POICell.TYPE_TEXT);
                dataRow.addCell(data.getDistributorName(), POICell.TYPE_TEXT);
                dataRow.addCell(data.getDistributorCode(), POICell.TYPE_TEXT);
                dataRow.addCell(data.getDsrStatus(), POICell.TYPE_TEXT);
                dataRow.addCell(data.getPhoneNo(), POICell.TYPE_TEXT);
                dataRow.addCell(data.getCreatedDate(), POICell.TYPE_TEXT);
                dataRow.addCell(pushedToDialerDate, POICell.TYPE_TEXT);
                dataRow.addCell(status, POICell.TYPE_TEXT);


                mySheet.addRow(dataRow);
            }
            mySheet.writeWorkBook();
            out.close();
            out.flush();
        } catch (IOException io) {
            io.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "openDSRLeadReport.htm")
    String showOpenDSRLeadReport(HttpServletRequest request, ModelMap model) {

        List< Map<String, Object>> leadList = maricoLeadService.getOpenLeadList();
        model.addAttribute("leadList", leadList);
        return "openDSRLeadReport";
    }

    @RequestMapping(value = "insertOpenLeadIntoDialer.htm", method = RequestMethod.GET)
    String showInsertOpenLeadIntoDialer(@RequestParam("id") int date, @RequestParam("desc") String beatDesc, HttpServletRequest request, ModelMap model) throws ParseException, Exception {
        String createdDateStr = null;
        try {
            SimpleDateFormat dFormat = new SimpleDateFormat("yyyyMMdd");
            SimpleDateFormat dFormatFinal = new SimpleDateFormat("yyyy-MM-dd");
            Date createdDate = dFormat.parse(Integer.toString(date));
            // String createdDate = ServletRequestUtils.getStringParameter(request, "createdDate", DateUtils.getCurrentDateString(DateUtils.IST, DateUtils.YMD));
            createdDateStr = dFormatFinal.format(createdDate);
        } catch (Exception e) {
            LogUtils.systemLogger.info(e);
            throw new Exception("Invalid Date!!!!", e);
        }
        List<MaricoLeads> openLeadList = this.maricoLeadService.getOpenLeadListForDial(createdDateStr, beatDesc);
        model.addAttribute("openLeadList", openLeadList);
        model.addAttribute("id", date);
        model.addAttribute("desc", beatDesc);

        return "insertOpenLeadIntoDialer";
    }

    @RequestMapping(value = "insertOpenLeadIntoDialer.htm", method = RequestMethod.POST)
    public String showInsertOpenLeadIntoDialer(@RequestParam("check") Integer[] check,
            HttpServletRequest request, ModelMap modelMap, HttpServletResponse response) throws ServletRequestBindingException {
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.IST);

        int date = ServletRequestUtils.getIntParameter(request, "id");
        String beatDesc = ServletRequestUtils.getStringParameter(request, "desc");

        List<MaricoLeads> leadList = new ArrayList<MaricoLeads>();
        String submit = ServletRequestUtils.getStringParameter(request, "submit");
        for (Integer leadId : check) {
            MaricoLeads lead = this.maricoLeadService.getMaricoLeadByLeadId(leadId);
            leadList.add(lead);
        }
        int id = 0;
        if (submit.equals("Push to Dialer")) {
            id = this.maricoLeadService.insertLeadsIntoDailer(leadList, "Nagpur");
        } else if (submit.equals("Export lead for Nagpur")) {
            leadList.clear();
            for (Integer leadId : check) {
                MaricoLeads lead = this.maricoLeadService.getMaricoLeadForNagpurByLeadId(leadId);
                leadList.add(lead);
            }
            try {
                OutputStream out = response.getOutputStream();
                response.setHeader("Content-Disposition", "attachment;filename=leadList.xls");
                response.setContentType("application/vnd.ms-excel");
                POIWorkSheet mySheet = new POIWorkSheet(out, "Lead List");
                mySheet.setPrintTitle(false);
                POIRow headerRow = new POIRow(POIRow.HEADER_ROW);
                headerRow.addCell("Queries");

                mySheet.addRow(headerRow);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                String stringDate = sdf.format(curDate);
                for (MaricoLeads data : leadList) {
                    String retailer = data.getRetailerName().length() > 30 ? data.getRetailerName().substring(0, 30) : data.getRetailerName();

                    String sql = "INSERT INTO asterisk.vicidial_list(lead_id ,entry_date,modify_date ,status ,user ,vendor_lead_code ,source_id ,list_id ,gmt_offset_now ,called_since_last_reset ,phone_code ,phone_number ,title ,first_name ,middle_initial ,last_name ,address1 ,address2 ,address3 ,city ,state ,province ,postal_code ,country_code ,gender ,date_of_birth ,alt_phone ,email ,security_phrase ,comments ,called_count ,last_local_call_time ,rank ,owner ,entry_list_id)VALUES (null ,'" + stringDate + "' ,'" + stringDate + "' ,'NEW' ,null ,null ,'" + data.getUniqueId() + "' ,'" + data.getListId() + "','0:00' ,'N' ,'91' ,'" + data.getPhoneNo() + "' ,null ,'" + retailer + "' ,null ,'" + data.getBeatDiscription() + "' ,'" + data.getDistributorName() + "' ,'" + data.getDistributorCode() + "' ,null ,null ,null ,null ,null ,null ,'U' ,null ,null ,null ,null ,null ,0,null ,'0' ,null ,'0');";

                    POIRow dataRow = new POIRow();
                    dataRow.addCell(sql, POICell.TYPE_TEXT);
                    mySheet.addRow(dataRow);
                }

                mySheet.writeWorkBook();

                out.close();

                out.flush();

            } catch (IOException i) {
                i.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (submit.equalsIgnoreCase("Dialed for Nagpur")) {
            id = this.maricoLeadService.leadsForNagpurMarkAsDialed(leadList);
            return "redirect:insertOpenLeadIntoDialer.htm?id=" + date + "&desc=" + beatDesc;
        } else {
            id = this.maricoLeadService.rejectLeads(leadList);
        }
        if (id == 0) {
            return "redirect:insertOpenLeadIntoDialer.htm?id=" + date + "&desc=" + beatDesc;
        } else {
            return "redirect:insertOpenLeadIntoDialer.htm?id=" + date + "&desc=" + beatDesc;
        }

    }

    @RequestMapping(value = "leadsforNagpurExcel.htm", method = RequestMethod.POST)
    public void getLeadsforNagpurExcel(@RequestParam("check") Integer[] check, ModelMap map, HttpServletRequest request, HttpServletResponse response) throws ServletRequestBindingException, IOException {
        int date = ServletRequestUtils.getIntParameter(request, "id");
        String beatDesc = ServletRequestUtils.getStringParameter(request, "desc");
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.IST);
        System.out.println("in leadsforNagpurExcel" + check[0]);
        System.out.println("check value = " + check.length);
        List<MaricoLeads> leadList = new ArrayList<MaricoLeads>();
        for (Integer leadId : check) {
            MaricoLeads lead = this.maricoLeadService.getMaricoLeadByLeadId(leadId);
            leadList.add(lead);
        }
        try {
            OutputStream out = response.getOutputStream();
            response.setHeader("Content-Disposition", "attachment;filename=leadList.xls");
            response.setContentType("application/vnd.ms-excel");
            POIWorkSheet mySheet = new POIWorkSheet(out, "Lead List");
            mySheet.setPrintTitle(false);
            POIRow headerRow = new POIRow(POIRow.HEADER_ROW);
            headerRow.addCell("lead_id");
            headerRow.addCell("entry_date");
            headerRow.addCell("modify_date");
            headerRow.addCell("status");
            headerRow.addCell("user");
            headerRow.addCell("vendor_lead_code");
            headerRow.addCell("source_id");
            headerRow.addCell("list_id");
            headerRow.addCell("gmt_offset_now");
            headerRow.addCell("called_since_last_reset");
            headerRow.addCell("phone_code");
            headerRow.addCell("phone_number");
            headerRow.addCell("title");
            headerRow.addCell("first_name");
            headerRow.addCell("middle_initial");
            headerRow.addCell("last_name");
            headerRow.addCell("address1");
            headerRow.addCell("address3");
            headerRow.addCell("city");
            headerRow.addCell("state");
            headerRow.addCell("province");
            headerRow.addCell("postal_code");
            headerRow.addCell("country_code");
            headerRow.addCell("gender");
            headerRow.addCell("date_of_birth");
            headerRow.addCell("alt_phone");
            headerRow.addCell("email");
            headerRow.addCell("security_phrase");
            headerRow.addCell("comments ");
            headerRow.addCell("called_count");
            headerRow.addCell("last_local_call_time");
            headerRow.addCell("rank");
            headerRow.addCell("owner");
            headerRow.addCell("entry_list_id");
            mySheet.addRow(headerRow);
            for (MaricoLeads data : leadList) {
                POIRow dataRow = new POIRow();
                dataRow.addCell(null, POICell.TYPE_TEXT);
                dataRow.addCell(curDate, POICell.TYPE_DATETIME);
                dataRow.addCell(curDate, POICell.TYPE_DATETIME);
                dataRow.addCell("NEW", POICell.TYPE_TEXT);
                dataRow.addCell(null, POICell.TYPE_TEXT);
                dataRow.addCell(null, POICell.TYPE_TEXT);
                dataRow.addCell(data.getUniqueId(), POICell.TYPE_TEXT);
                dataRow.addCell(data.getListId(), POICell.TYPE_TEXT);
                dataRow.addCell("0:00", POICell.TYPE_TEXT);
                dataRow.addCell("N", POICell.TYPE_TEXT);
                dataRow.addCell("91", POICell.TYPE_TEXT);
                dataRow.addCell(data.getPhoneNo(), POICell.TYPE_TEXT);
                dataRow.addCell(null, POICell.TYPE_TEXT);
                dataRow.addCell(data.getBeatDiscription(), POICell.TYPE_TEXT);
                dataRow.addCell(data.getDistributorName(), POICell.TYPE_TEXT);
                dataRow.addCell(data.getDistributorCode(), POICell.TYPE_TEXT);
                dataRow.addCell(null, POICell.TYPE_TEXT);
                dataRow.addCell(null, POICell.TYPE_TEXT);
                dataRow.addCell(null, POICell.TYPE_TEXT);
                dataRow.addCell(null, POICell.TYPE_TEXT);
                dataRow.addCell(null, POICell.TYPE_TEXT);
                dataRow.addCell(null, POICell.TYPE_TEXT);
                dataRow.addCell("U", POICell.TYPE_TEXT);
                dataRow.addCell(null, POICell.TYPE_TEXT);
                dataRow.addCell(null, POICell.TYPE_DATE);
                dataRow.addCell(null, POICell.TYPE_TEXT);
                dataRow.addCell(null, POICell.TYPE_TEXT);
                dataRow.addCell(null, POICell.TYPE_TEXT);
                dataRow.addCell("0", POICell.TYPE_TEXT);
                dataRow.addCell(null, POICell.TYPE_TEXT);
                dataRow.addCell("0", POICell.TYPE_TEXT);
                dataRow.addCell(null, POICell.TYPE_DATE);
                dataRow.addCell("0", POICell.TYPE_TEXT);
                mySheet.addRow(dataRow);
            }
            mySheet.writeWorkBook();
            out.close();
            out.flush();
        } catch (IOException i) {
            i.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
