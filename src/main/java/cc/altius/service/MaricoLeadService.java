/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.service;

import cc.altius.model.MaricoLeads;
import java.util.List;
import java.util.Map;

/**
 *
 * @author citius
 */
public interface MaricoLeadService {

    public List<MaricoLeads> getMaricoLeadList(String startDate, String endDate);

    public List<Map<String, Object>> getOpenLeadList();

    public List<MaricoLeads> getOpenLeadListForDial(String createdDate, String beatDesc);

    public MaricoLeads getMaricoLeadByLeadId(Integer leadId);

    public int insertLeadsIntoDailer(List<MaricoLeads> leadList, String location);

    public MaricoLeads getMaricoLeadForNagpurByLeadId(Integer leadId);

    public int leadsForNagpurMarkAsDialed(List<MaricoLeads> leadList);

    public int rejectLeads(List<MaricoLeads> leadList);
}
