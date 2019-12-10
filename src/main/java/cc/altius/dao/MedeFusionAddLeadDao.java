/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.dao;

import cc.altius.model.LivonLead;
import cc.altius.model.MaricoLeadUpload;
import java.util.List;

/**
 *
 * @author soft1
 */
public interface MedeFusionAddLeadDao {

    public int addLivonLead(LivonLead ll);

    public List<MaricoLeadUpload> loadDataLocally(String path);

    public int addMaricoLeadsByCsv();
}
