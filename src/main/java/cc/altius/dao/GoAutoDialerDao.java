/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.dao;

import cc.altius.model.Group;
import cc.altius.model.ReportType;
import cc.altius.model.Service;
import java.util.List;
import java.util.Map;

/**
 *
 * @author vipul
 */
public interface GoAutoDialerDao {

   
    //Report
    public List<Map<String, Object>> goAutoDialerInboundReport(String startDate, String endDate, String[] selectedServiceIds, int id);
    
    public List<Map<String, Object>> goAutoDialerOutboundReport(String startDate, String endDate, String[] selectedServiceIds, int id);
    
    public List<Map<String, Object>> getAgentPerformanceReport(String startDate, String endDate, String[] selectedServiceIds, int id);
}
