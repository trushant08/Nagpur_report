/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.dao.impl;

import cc.altius.dao.MaricoLeadDao;
import cc.altius.model.MaricoLeads;
import cc.altius.utils.DateUtils;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

/**
 *
 * @author citius
 */
@Repository
public class MaricoLeadDaoImpl implements MaricoLeadDao {

    private JdbcTemplate jdbcTemplate;
    private DataSource dataSource;
//    private DataSource dataSourceDialer;
    private DataSource dataSourceDialerNagpur;

    @Autowired
    @Qualifier("maricoDataSource")
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Autowired
    @Qualifier("dataSourceDialerNagpur")
    public void setDataSourceDialerNagpur(DataSource dataSourceDialer) {
        this.dataSourceDialerNagpur = dataSourceDialer;
    }

    @Override
    public List<MaricoLeads> getMaricoLeadList(String startDate, String endDate) {
        startDate += " 00:00:00";
        endDate += " 23:59:59";
        String sql = "SELECT  `MARICO_LEAD_ID`,`UNIQUE_ID`,`RETAILER_NAME`,`BEAT_DISCRIPTION`,`DISTRIBUTOR_NAME`,`DISTRIBUTOR_CODE`,`PHONE_NO`,`DSR_STATUS`,`LIST_ID`, `MODIFIED_DATE`,DATE_FORMAT(`INSERTED_IN_DIALER_DATE`,'%Y-%m-%d %T') INSERTED_IN_DIALER_DATE,DATE_FORMAT(`CREATED_DATE`,'%Y-%m-%d %T') CREATED_DATE,`LEAD_STATUS`  FROM `maricodsr`.`marico_lead` WHERE `CREATED_DATE` between ? and ? ";
        List<MaricoLeads> maricoLeadList = null;
        maricoLeadList = this.jdbcTemplate.query(sql, ParameterizedBeanPropertyRowMapper.newInstance(MaricoLeads.class), startDate, endDate);
        return maricoLeadList;
    }

    @Override
    public List<Map<String, Object>> getOpenLeadList() {

        String sql = "SELECT marico_lead.`CREATED_DATE`  createdDate,marico_lead.`BEAT_DISCRIPTION`  beatDiscription,marico_lead.`LIST_ID` listId,COUNT(*) COUNT FROM marico_lead WHERE  marico_lead.`LEAD_STATUS`=0  GROUP BY DATE(marico_lead.`CREATED_DATE`),  marico_lead.`BEAT_DISCRIPTION`;";
        List<Map<String, Object>> openLeadList = null;
        openLeadList = this.jdbcTemplate.queryForList(sql);

        return openLeadList;
    }

    @Override
    public List<MaricoLeads> getOpenLeadListForDial(String createdDate, String beatDesc) {
        String startDate = createdDate + " 00:00:00";
        String stopDate = createdDate + " 23:59:59";
        String sql = "SELECT  `MARICO_LEAD_ID`,`UNIQUE_ID`,`RETAILER_NAME`,`BEAT_DISCRIPTION`,`DISTRIBUTOR_NAME`,`DISTRIBUTOR_CODE`,`PHONE_NO`,`DSR_STATUS`,`LIST_ID`,`MODIFIED_DATE`,`INSERTED_IN_DIALER_DATE`,`CREATED_DATE`,`LEAD_STATUS`  FROM `maricodsr`.`marico_lead` WHERE `LEAD_STATUS` =0 and `CREATED_DATE` BETWEEN ? AND ? and BEAT_DISCRIPTION= ? ;";
        List<MaricoLeads> maricoLeadList = null;
        maricoLeadList = this.jdbcTemplate.query(sql, ParameterizedBeanPropertyRowMapper.newInstance(MaricoLeads.class), startDate, stopDate, beatDesc);
        return maricoLeadList;
    }

    @Override
    public MaricoLeads getMaricoLeadByLeadId(Integer leadId) {
        String selectSql = "SELECT  `MARICO_LEAD_ID`,`UNIQUE_ID`,`RETAILER_NAME`,`BEAT_DISCRIPTION`,`DISTRIBUTOR_NAME`,`DISTRIBUTOR_CODE`,`PHONE_NO`,`DSR_STATUS`,`LIST_ID`,`MODIFIED_DATE`,`INSERTED_IN_DIALER_DATE` FROM `maricodsr`.`marico_lead` WHERE `MARICO_LEAD_ID` =" + leadId + " ;";
        return this.jdbcTemplate.queryForObject(selectSql, ParameterizedBeanPropertyRowMapper.newInstance(MaricoLeads.class));
    }

    @Override
    public int insertLeadsIntoDailer(List<MaricoLeads> leadList, String location) {
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.IST);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String stringDate = sdf.format(curDate);

        String selectSql = " SELECT dsr_table.`NAGPUR_LIST_ID` FROM dsr_table WHERE dsr_table.`DSR_CODE`=?";

        NamedParameterJdbcTemplate jdbc = new NamedParameterJdbcTemplate(dataSourceDialerNagpur);

        MapSqlParameterSource paramSource = new MapSqlParameterSource();
        String sql = "INSERT INTO asterisk.vicidial_list("
                + " lead_id ,entry_date,modify_date ,status ,user ,vendor_lead_code ,source_id ,list_id ,gmt_offset_now ,called_since_last_reset ,"
                + "phone_code ,phone_number ,title ,first_name ,middle_initial ,last_name ,address1 ,address2 ,address3 ,city ,state ,"
                + "province ,postal_code ,country_code ,gender ,date_of_birth ,alt_phone ,email ,security_phrase ,comments ,called_count ,"
                + "last_local_call_time ,rank ,owner ,entry_list_id)VALUES ("
                + ":lead_id ,:entry_date ,:modify_date ,:status ,:user ,:vendor_lead_code ,:source_id ,:list_id ,:gmt_offset_now ,:called_since_last_reset ,:phone_code ,:phone_number ,:title ,:first_name ,:middle_initial ,:last_name ,:address1 ,:address2 ,:address3 ,:city ,:state ,:province ,:postal_code ,:country_code ,:gender ,:date_of_birth ,:alt_phone ,:email ,:security_phrase ,:comments ,:called_count ,:last_local_call_time ,:rank ,:owner ,:entry_list_id)";

        int i = leadList.size();
        for (MaricoLeads lead : leadList) {

            Integer listId = null;
            try {
                listId = this.jdbcTemplate.queryForObject(selectSql, Integer.class, Integer.parseInt(lead.getDistributorCode()));//selectSql, new Object[]{Integer.parseInt(ll.getDistributor_code())}, String.class);
                System.out.println("" + selectSql + lead.getDistributorCode());
            } catch (Exception e) {
                listId = 105;
                e.printStackTrace();
            }
            System.out.println("i----" + i);
            paramSource.addValue("lead_id", null);
            paramSource.addValue("entry_date", curDate);
            paramSource.addValue("modify_date", curDate);
            paramSource.addValue("status", "NEW");
            paramSource.addValue("user", null);
            paramSource.addValue("vendor_lead_code", null);
            paramSource.addValue("source_id", lead.getUniqueId());
            paramSource.addValue("list_id", listId);
            paramSource.addValue("gmt_offset_now", "0.00");
            paramSource.addValue("called_since_last_reset", "N");
            paramSource.addValue("phone_code", "91");
            paramSource.addValue("phone_number", lead.getPhoneNo());
            paramSource.addValue("title", null);
            paramSource.addValue("first_name", lead.getRetailerName().length() > 30 ? lead.getRetailerName().substring(0, 30) : lead.getRetailerName());
            paramSource.addValue("middle_initial", null);
            paramSource.addValue("last_name", lead.getBeatDiscription().length() > 30 ? lead.getBeatDiscription().substring(0, 30) : lead.getBeatDiscription());
            paramSource.addValue("address1", lead.getDistributorName());
            paramSource.addValue("address2", lead.getDistributorCode());
            paramSource.addValue("address3", null);
            paramSource.addValue("city", null);
            paramSource.addValue("state", null);
            paramSource.addValue("province", null);
            paramSource.addValue("postal_code", null);
            paramSource.addValue("country_code", null);
            paramSource.addValue("gender", "U");
            paramSource.addValue("date_of_birth", null);
            paramSource.addValue("alt_phone", null);
            paramSource.addValue("email", null);
            paramSource.addValue("security_phrase", null);
            paramSource.addValue("comments", null);
            paramSource.addValue("called_count", 0);
            paramSource.addValue("last_local_call_time", null);
            paramSource.addValue("rank", "0");
            paramSource.addValue("owner", null);
            paramSource.addValue("entry_list_id", "0");

            int dialerResult = jdbc.update(sql, paramSource);
            if (dialerResult > 0) {
                String updateSql = "UPDATE `maricodsr`.`marico_lead` SET `INSERTED_IN_DIALER_DATE` = '" + stringDate + "' ,  LEAD_STATUS =1 WHERE `MARICO_LEAD_ID` = " + lead.getMaricoLeadId() + ";";
                this.jdbcTemplate.update(updateSql);
            }
        }

        return 1;
    }

    @Override
    public MaricoLeads getMaricoLeadForNagpurByLeadId(Integer leadId) {
        MaricoLeads leads = null;
        String selectSql = "SELECT  `MARICO_LEAD_ID`,`UNIQUE_ID`,`RETAILER_NAME`,`BEAT_DISCRIPTION`,`DISTRIBUTOR_NAME`,`DISTRIBUTOR_CODE`,`PHONE_NO`,`DSR_STATUS`,`LIST_ID`,`MODIFIED_DATE`,`INSERTED_IN_DIALER_DATE` FROM `maricodsr`.`marico_lead` WHERE `MARICO_LEAD_ID` =" + leadId + " ;";
        leads = this.jdbcTemplate.queryForObject(selectSql, ParameterizedBeanPropertyRowMapper.newInstance(MaricoLeads.class));
        selectSql = " SELECT dsr_table.`NAGPUR_LIST_ID` FROM dsr_table WHERE dsr_table.`DSR_CODE`=?";
        Integer listId = 105;
        try {
            listId = this.jdbcTemplate.queryForObject(selectSql, Integer.class, Integer.parseInt(leads.getDistributorCode()));//selectSql, new Object[]{Integer.parseInt(ll.getDistributor_code())}, String.class);
            System.out.println("" + selectSql + leads.getDistributorCode());
        } catch (Exception e) {
            listId = 105;
            e.printStackTrace();
        }
        leads.setListId(Integer.toString(listId));
        return leads;
    }

    @Override
    public int leadsForNagpurMarkAsDialed(List<MaricoLeads> leadList) {
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.IST);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String stringDate = sdf.format(curDate);
        for (MaricoLeads lead : leadList) {
            String updateSql = "UPDATE `maricodsr`.`marico_lead` SET `MODIFIED_DATE` = '" + stringDate + "' ,  LEAD_STATUS =1 WHERE `MARICO_LEAD_ID` = " + lead.getMaricoLeadId() + ";";
            this.jdbcTemplate.update(updateSql);
        }
        return 1;
    }

    @Override
    public int rejectLeads(List<MaricoLeads> leadList) {
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.IST);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String stringDate = sdf.format(curDate);
        for (MaricoLeads lead : leadList) {
            String updateSql = "UPDATE `maricodsr`.`marico_lead` SET `MODIFIED_DATE` = '" + stringDate + "' ,  LEAD_STATUS =2 WHERE `MARICO_LEAD_ID` = " + lead.getMaricoLeadId() + ";";
            this.jdbcTemplate.update(updateSql);
        }
        return 1;
    }
}
