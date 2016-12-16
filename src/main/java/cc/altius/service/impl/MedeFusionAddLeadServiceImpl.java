/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.service.impl;

import cc.altius.dao.MedeFusionAddLeadDao;
import cc.altius.model.CsvFileUpload;
import cc.altius.model.LivonLead;
import cc.altius.model.MaricoLeadUpload;
import cc.altius.service.MedeFusionAddLeadService;
import cc.altius.utils.DateUtils;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author soft1
 */
@Service
public class MedeFusionAddLeadServiceImpl implements MedeFusionAddLeadService {
    
    public static final String IMG_FILE_PATH = "/home/crm/leads/";
    private static final org.apache.log4j.Logger maricoLogger = org.apache.log4j.Logger.getLogger("maricoLogging");
    @Autowired
    MedeFusionAddLeadDao addLeadDao;
    
    @Override
    public int addLivonLead(LivonLead ll) {
        return this.addLeadDao.addLivonLead(ll);
    }
    
    @Override
    public List<MaricoLeadUpload> uploadMaricoLeads(CsvFileUpload fileUpload) {
        maricoLogger.info("IN upload marico lead");
        List<MaricoLeadUpload> feedList = new LinkedList<MaricoLeadUpload>();
        if (!fileUpload.getMultipartFile().isEmpty()) {
            String originaFileName = fileUpload.getMultipartFile().getOriginalFilename();
            Date curDate = DateUtils.getCurrentDateObject(DateUtils.IST);
            byte[] imgBytes = null;
            try {
                imgBytes = fileUpload.getMultipartFile().getBytes();
            } catch (IOException ex) {
                Logger.getLogger(MedeFusionAddLeadServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            String path = IMG_FILE_PATH + sdf.format(curDate) + "/" + originaFileName;
            File folderFile = new File(IMG_FILE_PATH + sdf.format(curDate));
            try {
                folderFile.mkdirs();
            } catch (Exception e) {
                Logger.getLogger(MedeFusionAddLeadServiceImpl.class.getName()).log(Level.SEVERE, null, e);
            }
            File someFile = new File(path);
            FileOutputStream fos;
            try {
                fos = new FileOutputStream(someFile);
                fos.write(imgBytes);
                fos.flush();
                fos.close();
                
                feedList = this.addLeadDao.loadDataLocally(path);
                maricoLogger.info("file " + originaFileName + ":data inserted in temp table");
                
            } catch (FileNotFoundException ex) {
                maricoLogger.info("FileNotFoundException :" + ex);
                feedList = null;
            } catch (Exception e) {
                maricoLogger.info("Exception :" + e);
                feedList = null;
            }
        }
        return feedList;
    }
    
    @Override
    public int addMaricoLeadsByCsv() {
        return this.addLeadDao.addMaricoLeadsByCsv();
    }
}
