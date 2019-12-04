/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.web.controller;

import cc.altius.common.utils.CommonUtils;
import cc.altius.service.GoAutoDialerService;
import cc.altius.utils.DateUtils;
import cc.altius.utils.LogUtils;
import cc.altius.utils.POI.POICell;
import cc.altius.utils.POI.POIRow;
import cc.altius.utils.POI.POIWorkSheet;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author aakash
 */
@Controller
public class ExcelController {

    @Autowired
    private GoAutoDialerService goAutoDialerService;
    private static int TIME_IST = 0;
    private static int TIME_PST = 1;
    private static int TIME_EST = 2;

    @RequestMapping(value = "reportExcel.htm")
    public void getExcelReport(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws IOException {

        try {
            int serverId = ServletRequestUtils.getIntParameter(request, "id", 0);
            int zoneId = ServletRequestUtils.getIntParameter(request, "zoneId", 0);
            String startDate = ServletRequestUtils.getStringParameter(request, "startDate", DateUtils.getCurrentDateString(DateUtils.IST, DateUtils.YMDHM));
            String endDate = ServletRequestUtils.getStringParameter(request, "stopDate", DateUtils.getCurrentDateString(DateUtils.IST, DateUtils.YMDHM));

            //These dates are for Excel File Name.......(Used in Excel File Name)
            String startDateExcel = startDate.substring(0, 10);
            String endDateExcel = endDate.substring(0, 10);

            int reportTypeId = ServletRequestUtils.getIntParameter(request, "reportId", 0);
            String selectedServiceIds[] = ServletRequestUtils.getStringParameters(request, "selectedServiceIds");

            int groupId = ServletRequestUtils.getIntParameter(request, "groupId", 0);

//            String startDateIST_To_EST = CommonUtils.dateConverte_IST_To_EST(startDate);
//            String endDateIST_To_EST = CommonUtils.dateConverte_IST_To_EST(endDate);
            String startDatePST_To_EST = CommonUtils.dateConverte_PST_To_EST(startDate);
            String endDatePST_To_EST = CommonUtils.dateConverte_PST_To_EST(endDate);

            //List<Map<String, Object>> leadData = null;
            List<Map<String, Object>> list = null;

            if (reportTypeId == 1 || reportTypeId == 6 || reportTypeId == 11 || reportTypeId == 12 || reportTypeId == 16 || reportTypeId == 20 || reportTypeId == 24 || reportTypeId == 28) {
                if (zoneId == TIME_IST) {
                    if (reportTypeId == 1 || reportTypeId == 6) {
                        list = this.goAutoDialerService.goAutoDialerInboundReport(startDate, endDate, selectedServiceIds, serverId);
                    } else {
                        list = this.goAutoDialerService.getInboundReportNew(startDate, endDate, selectedServiceIds, serverId);
                    }

                    OutputStream out = response.getOutputStream();
                    response.setHeader("Content-Disposition", "attachment;filename=InboundReport(IST)-" + startDateExcel + "_to_" + endDateExcel + ".xls");
                    response.setContentType("application/vnd.ms-excel");
                    POIWorkSheet mySheet = new POIWorkSheet(out, "InboundReport(IST)");
                    mySheet.setPrintTitle(false);
                    POIRow headerRow = new POIRow(POIRow.HEADER_ROW);
                    headerRow.addCell("Call Date(IST)");
                    headerRow.addCell("Unique Id");
                    headerRow.addCell("Caller Code");
                    headerRow.addCell("Did");
                    headerRow.addCell("Campaign");
                    headerRow.addCell("Term Reason");
                    headerRow.addCell("status");
                    headerRow.addCell("Length In Sec");
                    headerRow.addCell("Queue sec");
                    headerRow.addCell("Hold Time");
                    headerRow.addCell("Wrap Time");
                    headerRow.addCell("User");
                    headerRow.addCell("Call Date(EST)");

                    mySheet.addRow(headerRow);

                    for (Map<String, Object> data : list) {

                        String call_dateString = "";
                        String call_dateFormatted = "";
                        Date t = (Date) data.get("call_date");

                        if (t != null) {
                            Date convertedDate = CommonUtils.dateConverte_IST_To_EST(t);

                            call_dateString = DateUtils.convertDateToString(convertedDate, "yyyy-MM-dd HH:mm");

                            call_dateFormatted = DateUtils.convertDateToString(t, "yyyy-MM-dd HH:mm");
                        }

                        POIRow dataRow = new POIRow();

                        dataRow.addCell(call_dateFormatted, POICell.TYPE_TEXT);
                        dataRow.addCell(data.get("uniqueid"), POICell.TYPE_TEXT);
                        dataRow.addCell(data.get("caller_id_number"), POICell.TYPE_TEXT);
                        dataRow.addCell(data.get("did_description"), POICell.TYPE_TEXT);
                        dataRow.addCell(data.get("campaign_description"), POICell.TYPE_TEXT);
                        dataRow.addCell(data.get("term_reason"), POICell.TYPE_TEXT);
                        dataRow.addCell(data.get("status"), POICell.TYPE_TEXT);
                        dataRow.addCell(data.get("length_in_sec"), POICell.TYPE_TEXT);
                        dataRow.addCell(data.get("queue_seconds"), POICell.TYPE_TEXT);
                        dataRow.addCell(data.get("HoldTime"), POICell.TYPE_TEXT);
                        dataRow.addCell(data.get("wraptime"), POICell.TYPE_TEXT);
                        dataRow.addCell(data.get("user"), POICell.TYPE_TEXT);
                        dataRow.addCell(call_dateString, POICell.TYPE_TEXT);
                        mySheet.addRow(dataRow);
                    }
                    mySheet.writeWorkBook();
                    out.close();
                    out.flush();

                } else if (zoneId == TIME_EST) {
                    if (reportTypeId == 1 || reportTypeId == 6) {
                        list = this.goAutoDialerService.goAutoDialerInboundReport(startDate, endDate, selectedServiceIds, serverId);
                    } else {
                        list = this.goAutoDialerService.getInboundReportNew(startDate, endDate, selectedServiceIds, serverId);
                    }

                    OutputStream out = response.getOutputStream();
                    response.setHeader("Content-Disposition", "attachment;filename=InboundReport(EST)-" + startDateExcel + "_to_" + endDateExcel + ".xls");
                    response.setContentType("application/vnd.ms-excel");
                    POIWorkSheet mySheet = new POIWorkSheet(out, "InboundReport(EST)");
                    mySheet.setPrintTitle(false);
                    POIRow headerRow = new POIRow(POIRow.HEADER_ROW);
                    headerRow.addCell("Call Date(IST)");
                    headerRow.addCell("Unique Id");
                    headerRow.addCell("Caller Code");
                    headerRow.addCell("Did");
                    headerRow.addCell("Campaign");
                    headerRow.addCell("Term Reason");
                    headerRow.addCell("status");
                    headerRow.addCell("Length In Sec");
                    headerRow.addCell("Queue sec");
                    headerRow.addCell("Hold Time");
                    headerRow.addCell("Wrap Time");
                    headerRow.addCell("User");
                    headerRow.addCell("Call Date(EST)");

                    mySheet.addRow(headerRow);

                    for (Map<String, Object> data : list) {

                        String call_dateFormatted = "";
                        Date t = (Date) data.get("call_date");
                        if (t != null) {
                            //Date convertedDate = CommonUtils.dateConverte_EST_To_IST(t);

                            call_dateFormatted = DateUtils.convertDateToString(t, "yyyy-MM-dd HH:mm");
                        }

                        POIRow dataRow = new POIRow();

                        dataRow.addCell(call_dateFormatted, POICell.TYPE_TEXT);
                        dataRow.addCell(data.get("uniqueid"), POICell.TYPE_TEXT);
                        dataRow.addCell(data.get("caller_id_number"), POICell.TYPE_TEXT);
                        dataRow.addCell(data.get("did_description"), POICell.TYPE_TEXT);
                        dataRow.addCell(data.get("campaign_description"), POICell.TYPE_TEXT);
                        dataRow.addCell(data.get("term_reason"), POICell.TYPE_TEXT);
                        dataRow.addCell(data.get("status"), POICell.TYPE_TEXT);
                        dataRow.addCell(data.get("length_in_sec"), POICell.TYPE_TEXT);
                        dataRow.addCell(data.get("queue_seconds"), POICell.TYPE_TEXT);
                        dataRow.addCell(data.get("HoldTime"), POICell.TYPE_TEXT);
                        dataRow.addCell(data.get("wraptime"), POICell.TYPE_TEXT);
                        dataRow.addCell(data.get("user"), POICell.TYPE_TEXT);
                        dataRow.addCell(call_dateFormatted, POICell.TYPE_TEXT);
                        mySheet.addRow(dataRow);
                    }
                    mySheet.writeWorkBook();
                    out.close();
                    out.flush();

                } else if (zoneId == TIME_PST) {
                    if (reportTypeId == 1 || reportTypeId == 6) {
                        list = this.goAutoDialerService.goAutoDialerInboundReport(startDatePST_To_EST, endDatePST_To_EST, selectedServiceIds, serverId);
                    } else {
                        list = this.goAutoDialerService.getInboundReportNew(startDate, endDate, selectedServiceIds, serverId);
                    }

                    OutputStream out = response.getOutputStream();
                    response.setHeader("Content-Disposition", "attachment;filename=InboundReport(PST)-" + startDateExcel + "_to_" + endDateExcel + ".xls");
                    response.setContentType("application/vnd.ms-excel");
                    POIWorkSheet mySheet = new POIWorkSheet(out, "InboundReport(PST)");
                    mySheet.setPrintTitle(false);
                    POIRow headerRow = new POIRow(POIRow.HEADER_ROW);
                    headerRow.addCell("Call Date(IST)");
                    headerRow.addCell("Unique Id");
                    headerRow.addCell("Caller Code");
                    headerRow.addCell("Did");
                    headerRow.addCell("Campaign");
                    headerRow.addCell("Term Reason");
                    headerRow.addCell("status");
                    headerRow.addCell("Length In Sec");
                    headerRow.addCell("Queue sec");
                    headerRow.addCell("Hold Time");
                    headerRow.addCell("Wrap Time");
                    headerRow.addCell("User");
                    headerRow.addCell("Call Date(EST)");

                    mySheet.addRow(headerRow);

                    for (Map<String, Object> data : list) {

                        String call_dateFormatted = "";
                        String call_dateString = "";
                        Date t = (Date) data.get("call_date");
                        if (t != null) {
                            Date convertedDate = CommonUtils.dateConverte_EST_To_PST(t);
                            call_dateString = DateUtils.convertDateToString(convertedDate, "yyyy-MM-dd HH:mm");
                            call_dateFormatted = DateUtils.convertDateToString(t, "yyyy-MM-dd HH:mm");
                        }
                        POIRow dataRow = new POIRow();

                        dataRow.addCell(call_dateString, POICell.TYPE_TEXT);
                        dataRow.addCell(data.get("uniqueid"), POICell.TYPE_TEXT);
                        dataRow.addCell(data.get("caller_id_number"), POICell.TYPE_TEXT);
                        dataRow.addCell(data.get("did_description"), POICell.TYPE_TEXT);
                        dataRow.addCell(data.get("campaign_description"), POICell.TYPE_TEXT);
                        dataRow.addCell(data.get("term_reason"), POICell.TYPE_TEXT);
                        dataRow.addCell(data.get("status"), POICell.TYPE_TEXT);
                        dataRow.addCell(data.get("length_in_sec"), POICell.TYPE_TEXT);
                        dataRow.addCell(data.get("queue_seconds"), POICell.TYPE_TEXT);
                        dataRow.addCell(data.get("HoldTime"), POICell.TYPE_TEXT);
                        dataRow.addCell(data.get("wraptime"), POICell.TYPE_TEXT);
                        dataRow.addCell(data.get("user"), POICell.TYPE_TEXT);
                        dataRow.addCell(call_dateFormatted, POICell.TYPE_TEXT);
                        mySheet.addRow(dataRow);
                    }
                    mySheet.writeWorkBook();
                    out.close();
                    out.flush();
                }
            } else if (reportTypeId == 2 || reportTypeId == 7) {
                if (zoneId == TIME_IST) {

                    list = this.goAutoDialerService.getGoAutoDialerInboundList(startDate, endDate, selectedServiceIds, serverId);

                    OutputStream out = response.getOutputStream();
                    response.setHeader("Content-Disposition", "attachment;filename=InboundReport(IST)-" + startDateExcel + "_to_" + endDateExcel + ".xls");
                    response.setContentType("application/vnd.ms-excel");
                    POIWorkSheet mySheet = new POIWorkSheet(out, "InboundReport(IST)");
                    mySheet.setPrintTitle(false);
                    POIRow headerRow = new POIRow(POIRow.HEADER_ROW);
                    headerRow.addCell("Call Date(IST)");
                    headerRow.addCell("Unique Id");
                    headerRow.addCell("Caller Code");
                    headerRow.addCell("Did");
                    headerRow.addCell("Campaign");
                    headerRow.addCell("Term Reason");
                    headerRow.addCell("status");
                    headerRow.addCell("Length In Sec");
                    headerRow.addCell("Queue sec");
                    headerRow.addCell("User");
                    headerRow.addCell("Call Date(EST)");

                    mySheet.addRow(headerRow);

                    for (Map<String, Object> data : list) {

                        String call_dateString = "";
                        String call_dateFormatted = "";
                        Date t = (Date) data.get("call_date");

                        if (t != null) {
                            Date convertedDate = CommonUtils.dateConverte_IST_To_EST(t);

                            call_dateString = DateUtils.convertDateToString(convertedDate, "yyyy-MM-dd HH:mm");

                            call_dateFormatted = DateUtils.convertDateToString(t, "yyyy-MM-dd HH:mm");
                        }

                        POIRow dataRow = new POIRow();

                        dataRow.addCell(call_dateFormatted, POICell.TYPE_TEXT);
                        dataRow.addCell(data.get("uniqueid"), POICell.TYPE_TEXT);
                        dataRow.addCell(data.get("caller_id_number"), POICell.TYPE_TEXT);
                        dataRow.addCell(data.get("did_description"), POICell.TYPE_TEXT);
                        dataRow.addCell(data.get("campaign_description"), POICell.TYPE_TEXT);
                        dataRow.addCell(data.get("term_reason"), POICell.TYPE_TEXT);
                        dataRow.addCell(data.get("status"), POICell.TYPE_TEXT);
                        dataRow.addCell(data.get("length_in_sec"), POICell.TYPE_TEXT);
                        dataRow.addCell(data.get("queue_seconds"), POICell.TYPE_TEXT);
                        dataRow.addCell(data.get("user"), POICell.TYPE_TEXT);
                        dataRow.addCell(call_dateString, POICell.TYPE_TEXT);
                        mySheet.addRow(dataRow);
                    }
                    mySheet.writeWorkBook();
                    out.close();
                    out.flush();

                } else if (zoneId == TIME_EST) {

                    list = this.goAutoDialerService.getGoAutoDialerInboundList(startDate, endDate, selectedServiceIds, serverId);

                    OutputStream out = response.getOutputStream();
                    response.setHeader("Content-Disposition", "attachment;filename=InboundReport(EST)-" + startDateExcel + "_to_" + endDateExcel + ".xls");
                    response.setContentType("application/vnd.ms-excel");
                    POIWorkSheet mySheet = new POIWorkSheet(out, "InboundReport(EST)");
                    mySheet.setPrintTitle(false);
                    POIRow headerRow = new POIRow(POIRow.HEADER_ROW);
                    headerRow.addCell("Call Date(IST)");
                    headerRow.addCell("Unique Id");
                    headerRow.addCell("Caller Code");
                    headerRow.addCell("Did");
                    headerRow.addCell("Campaign");
                    headerRow.addCell("Term Reason");
                    headerRow.addCell("status");
                    headerRow.addCell("Length In Sec");
                    headerRow.addCell("Queue sec");
                    headerRow.addCell("User");
                    headerRow.addCell("Call Date(EST)");

                    mySheet.addRow(headerRow);

                    for (Map<String, Object> data : list) {

                        String call_dateFormatted = "";
                        Date t = (Date) data.get("call_date");
                        if (t != null) {
                            //Date convertedDate = CommonUtils.dateConverte_EST_To_IST(t);

                            call_dateFormatted = DateUtils.convertDateToString(t, "yyyy-MM-dd HH:mm");
                        }

                        POIRow dataRow = new POIRow();

                        dataRow.addCell(call_dateFormatted, POICell.TYPE_TEXT);
                        dataRow.addCell(data.get("uniqueid"), POICell.TYPE_TEXT);
                        dataRow.addCell(data.get("caller_id_number"), POICell.TYPE_TEXT);
                        dataRow.addCell(data.get("did_description"), POICell.TYPE_TEXT);
                        dataRow.addCell(data.get("campaign_description"), POICell.TYPE_TEXT);
                        dataRow.addCell(data.get("term_reason"), POICell.TYPE_TEXT);
                        dataRow.addCell(data.get("status"), POICell.TYPE_TEXT);
                        dataRow.addCell(data.get("length_in_sec"), POICell.TYPE_TEXT);
                        dataRow.addCell(data.get("queue_seconds"), POICell.TYPE_TEXT);
                        dataRow.addCell(data.get("user"), POICell.TYPE_TEXT);
                        dataRow.addCell(call_dateFormatted, POICell.TYPE_TEXT);
                        mySheet.addRow(dataRow);
                    }
                    mySheet.writeWorkBook();
                    out.close();
                    out.flush();

                } else if (zoneId == TIME_PST) {
                    list = this.goAutoDialerService.getGoAutoDialerInboundList(startDatePST_To_EST, endDatePST_To_EST, selectedServiceIds, serverId);

                    OutputStream out = response.getOutputStream();
                    response.setHeader("Content-Disposition", "attachment;filename=InboundReport(PST)-" + startDateExcel + "_to_" + endDateExcel + ".xls");
                    response.setContentType("application/vnd.ms-excel");
                    POIWorkSheet mySheet = new POIWorkSheet(out, "InboundReport(PST)");
                    mySheet.setPrintTitle(false);
                    POIRow headerRow = new POIRow(POIRow.HEADER_ROW);
                    headerRow.addCell("Call Date(IST)");
                    headerRow.addCell("Unique Id");
                    headerRow.addCell("Caller Code");
                    headerRow.addCell("Did");
                    headerRow.addCell("Campaign");
                    headerRow.addCell("Term Reason");
                    headerRow.addCell("status");
                    headerRow.addCell("Length In Sec");
                    headerRow.addCell("Queue sec");
                    headerRow.addCell("User");
                    headerRow.addCell("Call Date(EST)");

                    mySheet.addRow(headerRow);

                    for (Map<String, Object> data : list) {

                        String call_dateFormatted = "";
                        String call_dateString = "";
                        Date t = (Date) data.get("call_date");
                        if (t != null) {
                            Date convertedDate = CommonUtils.dateConverte_EST_To_PST(t);
                            call_dateString = DateUtils.convertDateToString(convertedDate, "yyyy-MM-dd HH:mm");
                            call_dateFormatted = DateUtils.convertDateToString(t, "yyyy-MM-dd HH:mm");
                        }
                        POIRow dataRow = new POIRow();

                        dataRow.addCell(call_dateString, POICell.TYPE_TEXT);
                        dataRow.addCell(data.get("uniqueid"), POICell.TYPE_TEXT);
                        dataRow.addCell(data.get("caller_id_number"), POICell.TYPE_TEXT);
                        dataRow.addCell(data.get("did_description"), POICell.TYPE_TEXT);
                        dataRow.addCell(data.get("campaign_description"), POICell.TYPE_TEXT);
                        dataRow.addCell(data.get("term_reason"), POICell.TYPE_TEXT);
                        dataRow.addCell(data.get("status"), POICell.TYPE_TEXT);
                        dataRow.addCell(data.get("length_in_sec"), POICell.TYPE_TEXT);
                        dataRow.addCell(data.get("queue_seconds"), POICell.TYPE_TEXT);
                        dataRow.addCell(data.get("user"), POICell.TYPE_TEXT);
                        dataRow.addCell(call_dateFormatted, POICell.TYPE_TEXT);
                        mySheet.addRow(dataRow);
                    }
                    mySheet.writeWorkBook();
                    out.close();
                    out.flush();
                }
            } else if (reportTypeId == 3 || reportTypeId == 4 || reportTypeId == 8 || reportTypeId == 9 || reportTypeId == 13 || reportTypeId == 14 || reportTypeId == 17 || reportTypeId == 18 || reportTypeId == 21 || reportTypeId == 22 || reportTypeId == 25 || reportTypeId == 26) {

                if (zoneId == TIME_IST) {

                    list = this.goAutoDialerService.goAutoDialerOutboundReport(startDate, endDate, selectedServiceIds, reportTypeId, serverId);

                    OutputStream out = response.getOutputStream();
                    response.setHeader("Content-Disposition", "attachment;filename=OutBoundReport(IST)-" + startDateExcel + "_to_" + endDateExcel + ".xls");
                    response.setContentType("application/vnd.ms-excel");
                    POIWorkSheet mySheet = new POIWorkSheet(out, "OutBoundReport(IST)");
                    mySheet.setPrintTitle(false);
                    POIRow headerRow = new POIRow(POIRow.HEADER_ROW);
                    headerRow.addCell("Call Date(IST)");
                    headerRow.addCell("Phone Number");
                    headerRow.addCell("Length In sec");
                    headerRow.addCell("Status");
                    headerRow.addCell("Term reason");
                    headerRow.addCell("User Id");
                    headerRow.addCell("Comments");
                    headerRow.addCell("Call Date(EST)");

                    mySheet.addRow(headerRow);

                    for (Map<String, Object> data : list) {

                        Date t = (Date) data.get("call_date");
                        Date convertedDate = CommonUtils.dateConverte_IST_To_EST(t);

                        String call_date = DateUtils.convertDateToString(convertedDate, "yyyy-MM-dd HH:mm");

                        POIRow dataRow = new POIRow();

                        dataRow.addCell(DateUtils.convertDateToString(t, "yyyy-MM-dd HH:mm"), POICell.TYPE_TEXT);
                        dataRow.addCell(data.get("phone_number"), POICell.TYPE_TEXT);
                        dataRow.addCell(data.get("length_in_sec"), POICell.TYPE_TEXT);
                        dataRow.addCell(data.get("status"), POICell.TYPE_TEXT);
                        dataRow.addCell(data.get("term_reason"), POICell.TYPE_TEXT);
                        dataRow.addCell(data.get("user"), POICell.TYPE_TEXT);
                        dataRow.addCell(data.get("comments"), POICell.TYPE_TEXT);
                        dataRow.addCell(call_date, POICell.TYPE_TEXT);
                        mySheet.addRow(dataRow);
                    }
                    mySheet.writeWorkBook();
                    out.close();
                    out.flush();
                } else if (zoneId == TIME_EST) {
                    list = this.goAutoDialerService.goAutoDialerOutboundReport(startDate, endDate, selectedServiceIds, reportTypeId, serverId);

                    OutputStream out = response.getOutputStream();
                    response.setHeader("Content-Disposition", "attachment;filename=OutBoundReport(EST)-" + startDateExcel + "_to_" + endDateExcel + ".xls");
                    response.setContentType("application/vnd.ms-excel");
                    POIWorkSheet mySheet = new POIWorkSheet(out, "OutBoundReport(EST)");
                    mySheet.setPrintTitle(false);
                    POIRow headerRow = new POIRow(POIRow.HEADER_ROW);
                    headerRow.addCell("Call Date(EST)");
                    headerRow.addCell("Phone Number");
                    headerRow.addCell("Length In sec");
                    headerRow.addCell("Status");
                    headerRow.addCell("Term reason");
                    headerRow.addCell("User Id");
                    headerRow.addCell("Comments");
                    headerRow.addCell("Call Date(EST)");

                    mySheet.addRow(headerRow);

                    for (Map<String, Object> data : list) {

                        Date t = (Date) data.get("call_date");
                        //Date convertedDate = CommonUtils.dateConverte_EST_To_IST(t);

                        //String call_date=DateUtils.convertDateToString(convertedDate,"yyyy-MM-dd HH:mm");


                        POIRow dataRow = new POIRow();

                        dataRow.addCell(data.get("call_date"), POICell.TYPE_TEXT);
                        dataRow.addCell(data.get("phone_number"), POICell.TYPE_TEXT);
                        dataRow.addCell(data.get("length_in_sec"), POICell.TYPE_TEXT);
                        dataRow.addCell(data.get("status"), POICell.TYPE_TEXT);
                        dataRow.addCell(data.get("term_reason"), POICell.TYPE_TEXT);
                        dataRow.addCell(data.get("user"), POICell.TYPE_TEXT);
                        dataRow.addCell(data.get("comments"), POICell.TYPE_TEXT);
                        dataRow.addCell(DateUtils.convertDateToString(t, "yyyy-MM-dd HH:mm"), POICell.TYPE_TEXT);
                        mySheet.addRow(dataRow);
                    }
                    mySheet.writeWorkBook();
                    out.close();
                    out.flush();
                } else if (zoneId == TIME_PST) {

                    list = this.goAutoDialerService.goAutoDialerOutboundReport(startDatePST_To_EST, endDatePST_To_EST, selectedServiceIds, reportTypeId, serverId);

                    OutputStream out = response.getOutputStream();
                    response.setHeader("Content-Disposition", "attachment;filename=OutBoundReport(PST)-" + startDateExcel + "_to_" + endDateExcel + ".xls");
                    response.setContentType("application/vnd.ms-excel");
                    POIWorkSheet mySheet = new POIWorkSheet(out, "OutBoundReport(PST)");
                    mySheet.setPrintTitle(false);
                    POIRow headerRow = new POIRow(POIRow.HEADER_ROW);
                    headerRow.addCell("Call Date(PST)");
                    headerRow.addCell("Phone Number");
                    headerRow.addCell("Length In sec");
                    headerRow.addCell("Status");
                    headerRow.addCell("Term reason");
                    headerRow.addCell("User Id");
                    headerRow.addCell("Comments");
                    headerRow.addCell("Call Date(EST)");

                    mySheet.addRow(headerRow);

                    for (Map<String, Object> data : list) {

                        Date t = (Date) data.get("call_date");
                        Date convertedDate = CommonUtils.dateConverte_EST_To_PST(t);

                        String call_date = DateUtils.convertDateToString(convertedDate, "yyyy-MM-dd HH:mm");

                        POIRow dataRow = new POIRow();

                        dataRow.addCell(call_date, POICell.TYPE_TEXT);
                        dataRow.addCell(data.get("phone_number"), POICell.TYPE_TEXT);
                        dataRow.addCell(data.get("length_in_sec"), POICell.TYPE_TEXT);
                        dataRow.addCell(data.get("status"), POICell.TYPE_TEXT);
                        dataRow.addCell(data.get("term_reason"), POICell.TYPE_TEXT);
                        dataRow.addCell(data.get("user"), POICell.TYPE_TEXT);
                        dataRow.addCell(data.get("comments"), POICell.TYPE_TEXT);
                        dataRow.addCell(DateUtils.convertDateToString(t, "yyyy-MM-dd HH:mm"), POICell.TYPE_TEXT);
                        mySheet.addRow(dataRow);
                    }
                    mySheet.writeWorkBook();
                    out.close();
                    out.flush();
                }
            } else { //OTHER reports Type
                if (zoneId == TIME_IST) {
                    list = this.goAutoDialerService.getAgentPerformanceReport(startDate, endDate, selectedServiceIds, serverId);

                    OutputStream out = response.getOutputStream();
                    response.setHeader("Content-Disposition", "attachment;filename=AgentPerformance(IST)-" + startDateExcel + "_to_" + endDateExcel + ".xls");
                    response.setContentType("application/vnd.ms-excel");
                    POIWorkSheet mySheet = new POIWorkSheet(out, "AgentPerformanceReport(IST)");
                    mySheet.setPrintTitle(false);
                    POIRow headerRow = new POIRow(POIRow.HEADER_ROW);
                    headerRow.addCell("UserId");
                    headerRow.addCell("User Name");
                    headerRow.addCell("Active Time");
                    headerRow.addCell("Not Ready");
                    headerRow.addCell("Idle");
                    headerRow.addCell("Wrap Time");
                    headerRow.addCell("Total");
                    headerRow.addCell("Calls");
                    headerRow.addCell("L Break Time");
                    headerRow.addCell("T Break Time");
                    headerRow.addCell("Quality Feed Back");
                    headerRow.addCell("TDT Break Time");
                    headerRow.addCell("BRF Break Time");
                    headerRow.addCell("Manual Calls");
                    headerRow.addCell("Login");
                    headerRow.addCell("NXDIAL");
                    headerRow.addCell("Pause");
                    mySheet.addRow(headerRow);

                    for (Map<String, Object> data : list) {

                        POIRow dataRow = new POIRow();

                        dataRow.addCell(data.get("user"), POICell.TYPE_TEXT);
                        dataRow.addCell(data.get("FullName"), POICell.TYPE_TEXT);
                        dataRow.addCell(data.get("ActiveTime"), POICell.TYPE_TEXT);
                        dataRow.addCell(data.get("NotReady"), POICell.TYPE_TEXT);
                        dataRow.addCell(data.get("Idle"), POICell.TYPE_TEXT);
                        dataRow.addCell(data.get("wrapTime"), POICell.TYPE_TEXT);
                        dataRow.addCell(data.get("Total"), POICell.TYPE_TEXT);
                        dataRow.addCell(data.get("Calls"), POICell.TYPE_TEXT);
                        dataRow.addCell(data.get("LbBreakTime"), POICell.TYPE_TEXT);
                        dataRow.addCell(data.get("TbBreakTime"), POICell.TYPE_TEXT);
                        dataRow.addCell(data.get("QfbBreakTime"), POICell.TYPE_TEXT);
                        dataRow.addCell(data.get("TdtBreakTime"), POICell.TYPE_TEXT);
                        dataRow.addCell(data.get("BrfBreakTime"), POICell.TYPE_TEXT);
                        dataRow.addCell(data.get("ManualCalls"), POICell.TYPE_TEXT);
                        dataRow.addCell(data.get("Login"), POICell.TYPE_TEXT);
                        dataRow.addCell(data.get("NxDial"), POICell.TYPE_TEXT);
                        dataRow.addCell(data.get("Pause"), POICell.TYPE_TEXT);

                        mySheet.addRow(dataRow);
                    }
                    mySheet.writeWorkBook();
                    out.close();
                    out.flush();
                } else if (zoneId == TIME_EST) {
                    list = this.goAutoDialerService.getAgentPerformanceReport(startDate, endDate, selectedServiceIds, serverId);

                    OutputStream out = response.getOutputStream();
                    response.setHeader("Content-Disposition", "attachment;filename=AgentPerformance(EST)-" + startDateExcel + "_to_" + endDateExcel + ".xls");
                    response.setContentType("application/vnd.ms-excel");
                    POIWorkSheet mySheet = new POIWorkSheet(out, "AgentPerformanceReport(EST)");
                    mySheet.setPrintTitle(false);
                    POIRow headerRow = new POIRow(POIRow.HEADER_ROW);
                    headerRow.addCell("UserId");
                    headerRow.addCell("User Name");
                    headerRow.addCell("Active Time");
                    headerRow.addCell("Not Ready");
                    headerRow.addCell("Idle");
                    headerRow.addCell("Wrap Time");
                    headerRow.addCell("Total");
                    headerRow.addCell("Calls");
                    headerRow.addCell("L Break Time");
                    headerRow.addCell("T Break Time");
                    headerRow.addCell("Quality Feed Back");
                    headerRow.addCell("TDT Break Time");
                    headerRow.addCell("BRF Break Time");
                    headerRow.addCell("Manual Calls");
                    headerRow.addCell("Login");
                    headerRow.addCell("NXDIAL");
                    headerRow.addCell("Pause");

                    mySheet.addRow(headerRow);

                    for (Map<String, Object> data : list) {

                        POIRow dataRow = new POIRow();

                        dataRow.addCell(data.get("user"), POICell.TYPE_TEXT);
                        dataRow.addCell(data.get("FullName"), POICell.TYPE_TEXT);
                        dataRow.addCell(data.get("ActiveTime"), POICell.TYPE_TEXT);
                        dataRow.addCell(data.get("NotReady"), POICell.TYPE_TEXT);
                        dataRow.addCell(data.get("Idle"), POICell.TYPE_TEXT);
                        dataRow.addCell(data.get("wrapTime"), POICell.TYPE_TEXT);
                        dataRow.addCell(data.get("Total"), POICell.TYPE_TEXT);
                        dataRow.addCell(data.get("Calls"), POICell.TYPE_TEXT);
                        dataRow.addCell(data.get("LbBreakTime"), POICell.TYPE_TEXT);
                        dataRow.addCell(data.get("TbBreakTime"), POICell.TYPE_TEXT);
                        dataRow.addCell(data.get("QfbBreakTime"), POICell.TYPE_TEXT);
                        dataRow.addCell(data.get("TdtBreakTime"), POICell.TYPE_TEXT);
                        dataRow.addCell(data.get("BrfBreakTime"), POICell.TYPE_TEXT);
                        dataRow.addCell(data.get("ManualCalls"), POICell.TYPE_TEXT);
                        dataRow.addCell(data.get("Login"), POICell.TYPE_TEXT);
                        dataRow.addCell(data.get("NxDial"), POICell.TYPE_TEXT);
                        dataRow.addCell(data.get("Pause"), POICell.TYPE_TEXT);

                        mySheet.addRow(dataRow);
                    }
                    mySheet.writeWorkBook();
                    out.close();
                    out.flush();
                } else {
                    list = this.goAutoDialerService.getAgentPerformanceReport(startDatePST_To_EST, endDatePST_To_EST, selectedServiceIds, serverId);

                    OutputStream out = response.getOutputStream();
                    response.setHeader("Content-Disposition", "attachment;filename=AgentPerformance(PST)-" + startDateExcel + "_to_" + endDateExcel + ".xls");
                    response.setContentType("application/vnd.ms-excel");
                    POIWorkSheet mySheet = new POIWorkSheet(out, "AgentPerformanceReport(ST)");
                    mySheet.setPrintTitle(false);
                    POIRow headerRow = new POIRow(POIRow.HEADER_ROW);
                    headerRow.addCell("UserId");
                    headerRow.addCell("User Name");
                    headerRow.addCell("Active Time");
                    headerRow.addCell("Not Ready");
                    headerRow.addCell("Idle");
                    headerRow.addCell("Wrap Time");
                    headerRow.addCell("Total");
                    headerRow.addCell("Calls");
                    headerRow.addCell("L Break Time");
                    headerRow.addCell("T Break Time");
                    headerRow.addCell("Quality Feed Back");
                    headerRow.addCell("TDT Break Time");
                    headerRow.addCell("BRF Break Time");
                    headerRow.addCell("Manual Calls");
                    headerRow.addCell("Login");
                    headerRow.addCell("NXDIAL");
                    headerRow.addCell("Pause");

                    mySheet.addRow(headerRow);

                    for (Map<String, Object> data : list) {

                        POIRow dataRow = new POIRow();

                        dataRow.addCell(data.get("user"), POICell.TYPE_TEXT);
                        dataRow.addCell(data.get("FullName"), POICell.TYPE_TEXT);
                        dataRow.addCell(data.get("ActiveTime"), POICell.TYPE_TEXT);
                        dataRow.addCell(data.get("NotReady"), POICell.TYPE_TEXT);
                        dataRow.addCell(data.get("Idle"), POICell.TYPE_TEXT);
                        dataRow.addCell(data.get("wrapTime"), POICell.TYPE_TEXT);
                        dataRow.addCell(data.get("Total"), POICell.TYPE_TEXT);
                        dataRow.addCell(data.get("Calls"), POICell.TYPE_TEXT);
                        dataRow.addCell(data.get("LbBreakTime"), POICell.TYPE_TEXT);
                        dataRow.addCell(data.get("TbBreakTime"), POICell.TYPE_TEXT);
                        dataRow.addCell(data.get("QfbBreakTime"), POICell.TYPE_TEXT);
                        dataRow.addCell(data.get("TdtBreakTime"), POICell.TYPE_TEXT);
                        dataRow.addCell(data.get("BrfBreakTime"), POICell.TYPE_TEXT);
                        dataRow.addCell(data.get("ManualCalls"), POICell.TYPE_TEXT);
                        dataRow.addCell(data.get("Login"), POICell.TYPE_TEXT);
                        dataRow.addCell(data.get("NxDial"), POICell.TYPE_TEXT);
                        dataRow.addCell(data.get("Pause"), POICell.TYPE_TEXT);

                        mySheet.addRow(dataRow);
                    }
                    mySheet.writeWorkBook();
                    out.close();
                    out.flush();
                }

                //modelMap.addAttribute("list", list);
            }
        } catch (IOException io) {
            LogUtils.systemLogger.warn(LogUtils.buildStringForSystemLog(io));
        } catch (Exception e) {
            LogUtils.systemLogger.error(LogUtils.buildStringForSystemLog(e));
        }
    }
}