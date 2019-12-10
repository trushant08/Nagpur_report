/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.service;

import cc.altius.model.CsvFileUpload;
import cc.altius.model.LivonLead;
import cc.altius.model.MaricoLeadUpload;
import java.util.List;

/**
 *
 * @author soft1
 */
public interface MedeFusionAddLeadService {

    public int addLivonLead(LivonLead ll);
    
    public List<MaricoLeadUpload> uploadMaricoLeads(CsvFileUpload fileUpload);
    
    public int addMaricoLeadsByCsv();
}
