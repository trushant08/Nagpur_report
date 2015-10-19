/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.service.impl;

import cc.altius.dao.MedeFusionAddLeadDao;
import cc.altius.model.LivonLead;
import cc.altius.service.MedeFusionAddLeadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author soft1
 */
@Service
public class MedeFusionAddLeadServiceImpl implements MedeFusionAddLeadService {

    @Autowired
    MedeFusionAddLeadDao addLeadDao;

    @Override
    public int addLivonLead(LivonLead ll) {
        return this.addLeadDao.addLivonLead(ll);
    }
}
