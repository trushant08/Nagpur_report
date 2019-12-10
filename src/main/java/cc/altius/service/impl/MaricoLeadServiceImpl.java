/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.service.impl;

import cc.altius.dao.MaricoLeadDao;
import cc.altius.model.MaricoLeads;
import cc.altius.service.MaricoLeadService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author citius
 */
@Service
public class MaricoLeadServiceImpl implements MaricoLeadService {

    @Autowired
    private MaricoLeadDao maricoLeadDao;

    @Override
    public List<MaricoLeads> getMaricoLeadList(String startDate, String endDate) {
        return this.maricoLeadDao.getMaricoLeadList(startDate, endDate);
    }

    @Override
    public List<Map<String, Object>> getOpenLeadList() {
        return this.maricoLeadDao.getOpenLeadList();
    }

    @Override
    public List<MaricoLeads> getOpenLeadListForDial(String createdDate, String beatDesc) {
        return this.maricoLeadDao.getOpenLeadListForDial(createdDate, beatDesc);
    }

    @Override
    public MaricoLeads getMaricoLeadByLeadId(Integer leadId) {
        return this.maricoLeadDao.getMaricoLeadByLeadId(leadId);
    }

    @Override
    public int insertLeadsIntoDailer(List<MaricoLeads> leadList, String location) {
        return this.maricoLeadDao.insertLeadsIntoDailer(leadList, location);
    }

    @Override
    public MaricoLeads getMaricoLeadForNagpurByLeadId(Integer leadId) {
        return this.maricoLeadDao.getMaricoLeadForNagpurByLeadId(leadId);
    }

    @Override
    public int leadsForNagpurMarkAsDialed(List<MaricoLeads> leadList) {
        return this.maricoLeadDao.leadsForNagpurMarkAsDialed(leadList);
    }

    @Override
    public int rejectLeads(List<MaricoLeads> leadList) {
        return this.maricoLeadDao.rejectLeads(leadList);
    }
}
