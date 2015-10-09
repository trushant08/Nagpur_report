/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.service.impl;

import cc.altius.dao.GoAutoDialerDao;
import cc.altius.model.Group;
import cc.altius.model.ReportType;
import cc.altius.service.GoAutoDialerService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author vipul
 */
@Service
public class GoAutoDialerServiceImpl implements GoAutoDialerService {

    @Autowired
    GoAutoDialerDao GoAutoDialerDao;
  
    @Override
    public List<Map<String, Object>> goAutoDialerInboundReport(String startDate, String endDate, String[] selectedServiceIds, int id) {
        return this.GoAutoDialerDao.goAutoDialerInboundReport(startDate, endDate, selectedServiceIds, id);
    }
    
    @Override
    public List<Map<String, Object>> goAutoDialerOutboundReport(String startDate, String endDate, String[] selectedServiceIds, int reportTypeId, int id) {
        return this.GoAutoDialerDao.goAutoDialerOutboundReport(startDate, endDate, selectedServiceIds, reportTypeId, id);
    }
    
    @Override
    public List<Map<String, Object>> getAgentPerformanceReport(String startDate, String endDate, String[] selectedServiceIds, int id) {
        return this.GoAutoDialerDao.getAgentPerformanceReport(startDate, endDate, selectedServiceIds, id);
    }
}
