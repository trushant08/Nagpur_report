/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.service.impl;

import cc.altius.dao.GoAutoDialerDao;
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
    GoAutoDialerDao goAutoDialerDao;

    @Override
    public List<Map<String, Object>> goAutoDialerInboundReport(String startDate, String endDate, String[] selectedServiceIds, int id) {
        return this.goAutoDialerDao.goAutoDialerInboundReport(startDate, endDate, selectedServiceIds, id);
    }

    @Override
    public List<Map<String, Object>> getGoAutoDialerInboundList(String startDate, String endDate, String[] selectedServiceIds, int id) {
        return this.goAutoDialerDao.getGoAutoDialerInboundList(startDate, endDate, selectedServiceIds, id);
    }

    @Override
    public List<Map<String, Object>> goAutoDialerOutboundReport(String startDate, String endDate, String[] selectedServiceIds, int reportTypeId, int id) {
        return this.goAutoDialerDao.goAutoDialerOutboundReport(startDate, endDate, selectedServiceIds, reportTypeId, id);
    }

    @Override
    public List<Map<String, Object>> getAgentPerformanceReport(String startDate, String endDate, String[] selectedServiceIds, int id) {
        return this.goAutoDialerDao.getAgentPerformanceReport(startDate, endDate, selectedServiceIds, id);
    }

    @Override
    public List<Map<String, Object>> getInboundReportNew(String startDate, String endDate, String[] selectedServiceIds, int id) {
        return this.goAutoDialerDao.getInboundReportNew(startDate, endDate, selectedServiceIds, id);
    }
}
