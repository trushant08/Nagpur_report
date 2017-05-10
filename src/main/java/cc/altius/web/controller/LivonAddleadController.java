/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.web.controller;

import cc.altius.model.CsvFileUpload;
import cc.altius.model.LivonLead;
import cc.altius.model.MaricoLeadUpload;
import cc.altius.service.MedeFusionAddLeadService;
import cc.altius.utils.LogUtils;
import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author citius
 */
@Controller
public class LivonAddleadController {

    @Autowired
    private MedeFusionAddLeadService medeFusionAddLeadService;
    private static final Logger maricoLogger = Logger.getLogger("maricoLogging");

    @InitBinder
    public void initBinder(WebDataBinder binder, HttpServletRequest request) {

        binder.registerCustomEditor(Date.class, "date", new PropertyEditorSupport() {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                Date dt = new Date();
                try {
                    dt = sdf.parse(text);
                } catch (ParseException pe) {
                    LogUtils.systemLogger.error(LogUtils.buildStringForSystemLog(pe));
                }
                super.setValue(dt);
            }
        });
    }

    @RequestMapping(value = "/livon/addLead.htm")
    public @ResponseBody
    String showaddLivonLeadController(LivonLead ll, HttpServletRequest request) {
        if (ll.getList_id() == null || !ll.getList_id().equals("656")) {
            return " Failed |list_id is invalid";
        }
        try {
            ll.setPhone_No(ll.getPhone_No() == null ? "" : ll.getPhone_No());
            ll.setRetailer_Name(ll.getRetailer_Name() == null ? "" : ll.getRetailer_Name());
            ll.setDistributor_code(ll.getDistributor_code() == null ? "" : ll.getDistributor_code());
            ll.setBeatDescription(ll.getBeatDescription() == null ? "" : ll.getBeatDescription());
            ll.setDistributor_name(ll.getDistributor_name() == null ? "" : ll.getDistributor_name());
            ll.setDsr_status(ll.getDsr_status() == null ? "" : ll.getDsr_status());
            String message = "";
            int result = this.medeFusionAddLeadService.addLivonLead(ll);
            if (result != 0) {
                message += "Success|" + ll.getUnique_ID();
            } else {
                message += "Failed |" + ll.getUnique_ID() + "|Could not insert into db";
            }
            maricoLogger.info(message);
            return message;
        } catch (Exception e) {
            maricoLogger.info("Failed|" + ll.getUnique_ID() + "|Failed to insert into the db - " + e.getMessage());
            maricoLogger.info(LogUtils.buildStringForSystemLog(e));
            return ("Failed|" + ll.getUnique_ID() + "|Failed to insert into the db - " + e.getMessage());
        }
    }

    @RequestMapping(value = "/uploadMaricoLeads.htm", method = RequestMethod.GET)
    public String showUploadLeadPage(ModelMap modelMap) {
        CsvFileUpload fileUpload = new CsvFileUpload();
        modelMap.addAttribute("fileUpload", fileUpload);
        return "/uploadMaricoLeads";
    }

    @RequestMapping(value = "/uploadMaricoLeads", method = RequestMethod.POST)
    public String showUploadPagePost(@ModelAttribute("fileUpload") CsvFileUpload fileUpload, ModelMap modelMap) {
        List<MaricoLeadUpload> lst = this.medeFusionAddLeadService.uploadMaricoLeads(fileUpload);
        modelMap.addAttribute("lst", lst);
        return "/tempUploadResult";
    }

    @RequestMapping(value = "saveFeed.htm", method = RequestMethod.POST)
    public String finalUploadFeed(HttpServletRequest request, ModelMap modelMap) {
        String cancel = ServletRequestUtils.getStringParameter(request, "_cancel", null);
        if (cancel != null) {
            return "redirect:../home/home.htm?msg=Action cancelled";
        } else {
            int result = this.medeFusionAddLeadService.addMaricoLeadsByCsv();

            if (result < 0) {
                String error = "Error in lead upload";
                modelMap.addAttribute("error", error);
                return "redirect:/uploadMaricoLeads.htm?error="+error;
            } else {
                String msg = result + " Leads uploaded successfully.";
                modelMap.addAttribute("msg", msg);
                return "redirect:/uploadMaricoLeads.htm?msg="+msg;
            }
        }
    }
}
