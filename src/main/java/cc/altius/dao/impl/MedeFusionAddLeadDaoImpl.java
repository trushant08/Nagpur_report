/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.dao.impl;

import cc.altius.dao.MedeFusionAddLeadDao;
import cc.altius.model.LivonLead;
import cc.altius.model.MaricoLeadUpload;
import cc.altius.model.mapper.LivonLeadRowMapper;
import cc.altius.utils.DateUtils;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author soft1
 */
@Repository
public class MedeFusionAddLeadDaoImpl implements MedeFusionAddLeadDao {

    private DataSource maricoDataSource;
    private JdbcTemplate maricoJdbcTemplate;
    private static final Logger maricoLogger = Logger.getLogger("maricoLogging");

    @Autowired
    @Qualifier("maricoDataSource")
    public void setMaricoDataSource(DataSource maricoDataSource) {
        this.maricoDataSource = maricoDataSource;
        this.maricoJdbcTemplate = new JdbcTemplate(maricoDataSource);
    }

    @Override
    @Transactional
    public List<MaricoLeadUpload> loadDataLocally(String path) {
        String sql = "TRUNCATE TABLE `maricodsr`.`temp_table`; ";
        this.maricoJdbcTemplate.update(sql);

        sql = "LOAD DATA LOCAL INFILE '" + path + "' INTO TABLE `maricodsr`.`temp_table` CHARACTER SET 'latin1' FIELDS TERMINATED BY ',' OPTIONALLY ENCLOSED BY '\"' LINES TERMINATED BY '\n' IGNORE 1 LINES (`RETAILER_NAME`,`PHONE_NO`, `UNIQUE_ID`,`BEAT_DESC`, `DISTRIBUTOR_NAME`, `DISTRIBUTOR_CODE`, `LIST_ID`, `DSR_STATUS`)";
        this.maricoJdbcTemplate.execute(sql);

        //make default status true
        sql = "UPDATE temp_table tt SET tt.`STATUS`=1";
        this.maricoJdbcTemplate.update(sql);

        //check list_id value
        sql = "UPDATE temp_table tt SET tt.`STATUS`=0,tt.`REASON`=CONCAT(tt.`REASON`,'list_id is invalid.') WHERE tt.`LIST_ID` IS NULL OR tt.`LIST_ID`!='656'";
        this.maricoJdbcTemplate.update(sql);
        
        //check list_id value
        sql = "UPDATE temp_table tt SET tt.`STATUS`=0 AND tt.`REASON`=CONCAT(tt.`REASON`,'Unique Id is blank.') WHERE tt.`UNIQUE_ID` IS NULL OR tt.`UNIQUE_ID`=''";
        this.maricoJdbcTemplate.update(sql);

        //check MPN name required
        sql = "UPDATE temp_table tt SET tt.`RETAILER_NAME`='' WHERE tt.`RETAILER_NAME` IS NULL AND tt.`STATUS`=0";
        this.maricoJdbcTemplate.update(sql);
        
        //check MPN name required
        sql = "UPDATE temp_table tt SET tt.`PHONE_NO`='' WHERE tt.`PHONE_NO` IS NULL AND tt.`STATUS`=0";
        this.maricoJdbcTemplate.update(sql);

        //check price name required
        sql = "UPDATE temp_table tt SET tt.`DISTRIBUTOR_CODE`='' WHERE tt.`DISTRIBUTOR_CODE` IS NULL AND tt.`STATUS`=0";
        this.maricoJdbcTemplate.update(sql);

        //check quantity name required
        sql = "UPDATE temp_table tt SET tt.`BEAT_DESC`='' WHERE tt.`BEAT_DESC` IS NULL AND tt.`STATUS`=0";
        this.maricoJdbcTemplate.update(sql);

        //update status if product is new product
        sql = "UPDATE temp_table tt SET tt.`DISTRIBUTOR_NAME`='' WHERE tt.`DISTRIBUTOR_NAME` IS NULL AND tt.`STATUS`=0";
        this.maricoJdbcTemplate.update(sql);

        sql = "UPDATE temp_table tt SET tt.`DSR_STATUS`='' WHERE tt.`DSR_STATUS` IS NULL AND tt.`STATUS`=0";
        this.maricoJdbcTemplate.update(sql);

        sql = "UPDATE temp_table tt "
                + " LEFT JOIN dsr_table dt ON dt.`DSR_CODE`=tt.`DISTRIBUTOR_CODE` "
                + " SET tt.`LIST_ID`=COALESCE(dt.`LIST_ID`,'656') WHERE tt.`STATUS`=0";
        this.maricoJdbcTemplate.update(sql);

        sql = "UPDATE temp_table tt "
                + " LEFT JOIN dsr_table dt ON dt.`DSR_CODE`=tt.`DISTRIBUTOR_CODE` "
                + " SET tt.`PHONE_NO`=IF(LENGTH(tt.`PHONE_NO`)<10,CONCAT(dt.`STD_CODE`,tt.`PHONE_NO`),tt.`PHONE_NO`)"
                + " WHERE tt.`STATUS`=0";
        this.maricoJdbcTemplate.update(sql);

        sql = "SELECT * FROM temp_table";
        return this.maricoJdbcTemplate.query(sql, new LivonLeadRowMapper());
    }

    @Override
    public int addMaricoLeadsByCsv() {
        try {
            String sql = "INSERT INTO marico_lead SELECT NULL,`UNIQUE_ID`,RETAILER_NAME,BEAT_DESC,DISTRIBUTOR_NAME,DISTRIBUTOR_CODE,PHONE_NO,DSR_STATUS,"
                    + " LIST_ID,NOW(),NOW(),NULL,0 FROM temp_table tt WHERE tt.`STATUS`=1";
            maricoLogger.info("Inserted into marico_lead by csv");
            return this.maricoJdbcTemplate.update(sql);
        } catch (Exception e) {
            maricoLogger.info("Exception while inserting into marico_lead :" + e);
            return -1;
        }
    }

    @Override
//    @Transactional()
    public int addLivonLead(LivonLead ll) {
        maricoLogger.info("IN - " + ll.toString());
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.IST);
        String selectSql = " SELECT dsr_table.`STD_CODE`,dsr_table.`LIST_ID` FROM dsr_table WHERE dsr_table.`DSR_CODE`=?";
        Map<String, Object> codeObj = null;
        try {
            codeObj = this.maricoJdbcTemplate.queryForMap(selectSql, Integer.parseInt(ll.getDistributor_code()));//selectSql, new Object[]{Integer.parseInt(ll.getDistributor_code())}, String.class);
        } catch (Exception e) {
            codeObj = null;
        }
        if (codeObj != null) {
            if (ll.getPhone_No().length() < 10) {
                maricoLogger.info("Phone no is not 10 digits so trying to get Code");

                maricoLogger.info("Found STD code " + codeObj);
                ll.setPhone_No(codeObj.get("STD_CODE").toString().concat(ll.getPhone_No()));

            }


            ll.setList_id(codeObj.get("LIST_ID").toString());
        } else {

            ll.setList_id("656");
        }
        maricoLogger.info("Inserting into marico_lead");
        String insertSql = "INSERT INTO `maricodsr`.`marico_lead`(`MARICO_LEAD_ID`,`UNIQUE_ID`,`RETAILER_NAME`,`BEAT_DISCRIPTION`, `DISTRIBUTOR_NAME`,`DISTRIBUTOR_CODE`,`PHONE_NO`,`DSR_STATUS`,`LIST_ID`,`INSERTED_IN_DIALER_DATE`, `MODIFIED_DATE`,`CREATED_DATE`,`LEAD_STATUS`)VALUES (NULL,?,?,?,?,?,?,?,?,?,?,?,0);";
        int result = this.maricoJdbcTemplate.update(insertSql, ll.getUnique_ID(), ll.getRetailer_Name(), ll.getBeatDescription(), ll.getDistributor_name(), ll.getDistributor_code(), ll.getPhone_No(), ll.getDsr_status(), Integer.parseInt(ll.getList_id()), null, curDate, curDate);
//        if (result != 0 && !ll.getDsr_status().equalsIgnoreCase("A")) {
//            maricoLogger.info("DSR status =" + ll.getDsr_status());
//            if(ll.getRetailer_Name().length()>30){
//                ll.setRetailer_Name(ll.getRetailer_Name().substring(0, 30));
//            }
//            String sql = "";
//            NamedParameterJdbcTemplate jdbc = new NamedParameterJdbcTemplate(dataSourceDialer);
//            MapSqlParameterSource paramSource = new MapSqlParameterSource();
//            sql = "INSERT INTO asterisk.vicidial_list("
//                    + " lead_id ,entry_date,modify_date ,status ,user ,vendor_lead_code ,source_id ,list_id ,gmt_offset_now ,called_since_last_reset ,"
//                    + "phone_code ,phone_number ,title ,first_name ,middle_initial ,last_name ,address1 ,address2 ,address3 ,city ,state ,"
//                    + "province ,postal_code ,country_code ,gender ,date_of_birth ,alt_phone ,email ,security_phrase ,comments ,called_count ,"
//                    + "last_local_call_time ,rank ,owner ,entry_list_id)VALUES ("
//                    + ":lead_id ,:entry_date ,:modify_date ,:status ,:user ,:vendor_lead_code ,:source_id ,:list_id ,:gmt_offset_now ,:called_since_last_reset ,:phone_code ,:phone_number ,:title ,:first_name ,:middle_initial ,:last_name ,:address1 ,:address2 ,:address3 ,:city ,:state ,:province ,:postal_code ,:country_code ,:gender ,:date_of_birth ,:alt_phone ,:email ,:security_phrase ,:comments ,:called_count ,:last_local_call_time ,:rank ,:owner ,:entry_list_id)";
//            paramSource.addValue("lead_id", null);
//            paramSource.addValue("entry_date", curDate);
//            paramSource.addValue("modify_date", curDate);
//            paramSource.addValue("status", "NEW");
//            paramSource.addValue("user", null);
//            paramSource.addValue("vendor_lead_code", null);
//            paramSource.addValue("source_id", ll.getUnique_ID());
//            paramSource.addValue("list_id", ll.getList_id());
//            paramSource.addValue("gmt_offset_now", "0.00");
//            paramSource.addValue("called_since_last_reset", "N");
//            paramSource.addValue("phone_code", "91");
//            paramSource.addValue("phone_number", ll.getPhone_No());
//            paramSource.addValue("title", null);
//            paramSource.addValue("first_name", ll.getRetailer_Name());
//            paramSource.addValue("middle_initial", null);
//            paramSource.addValue("last_name", ll.getBeatDescription());
//            paramSource.addValue("address1", ll.getDistributor_name());
//            paramSource.addValue("address2", ll.getDistributor_code());
//            paramSource.addValue("address3", null);
//            paramSource.addValue("city", null);
//            paramSource.addValue("state", null);
//            paramSource.addValue("province", null);
//            paramSource.addValue("postal_code", null);
//            paramSource.addValue("country_code", null);
//            paramSource.addValue("gender", "U");
//            paramSource.addValue("date_of_birth", null);
//            paramSource.addValue("alt_phone", null);
//            paramSource.addValue("email", null);
//            paramSource.addValue("security_phrase", null);
//            paramSource.addValue("comments", null);
//            paramSource.addValue("called_count", 0);
//            paramSource.addValue("last_local_call_time", null);
//            paramSource.addValue("rank", "0");
//            paramSource.addValue("owner", null);
//            paramSource.addValue("entry_list_id", "0");
//            return jdbc.update(sql, paramSource);
//        } else {
//            return 1;
//        }
        return result;
    }
}
