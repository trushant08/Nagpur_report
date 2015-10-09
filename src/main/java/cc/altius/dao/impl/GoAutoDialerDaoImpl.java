/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.dao.impl;

import cc.altius.dao.GoAutoDialerDao;
import cc.altius.model.Group;
import cc.altius.model.ReportType;
import cc.altius.model.Service;
import cc.altius.model.mapper.GroupMapper;
import cc.altius.model.mapper.ReportTypeMapper;
import cc.altius.model.mapper.ServiceMapper;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author vipul
 */
@Repository
public class GoAutoDialerDaoImpl implements GoAutoDialerDao {

    private JdbcTemplate jdbcTemplate;
    private JdbcTemplate nagpurJdbcTemplate;
    private DataSource dataSource;
    private DataSource nagpurDataSource;

    @Autowired
    @Qualifier("dataSource")
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    
    @Autowired
    @Qualifier("nagpurDataSource")
    public void setNagpurDataSource(DataSource nagpurDataSource) {
        this.nagpurDataSource = nagpurDataSource;
        this.nagpurJdbcTemplate = new JdbcTemplate(nagpurDataSource);
    }

    @Override
    public List<Map<String, Object>> goAutoDialerInboundReport(String startDate, String endDate, String[] selectedServiceIds, int id) {
        String sql = " SELECT "
                + " vicidial_did_log.`uniqueid`, "
                + " vicidial_did_log.`caller_id_number`, "
                + " vicidial_inbound_dids.`did_description`, "
                + " vicidial_campaigns.`campaign_description`, "
                + " COALESCE(vicidial_closer_log.`term_reason`,'IVR ABANDON') AS term_reason, "
                + " COALESCE(vicidial_closer_log.`status`,'IVR ABANDON') AS `status`, "
                + " COALESCE(vicidial_closer_log.`call_date`,vicidial_did_log.`call_date`) AS call_date, "
                + " COALESCE(IF ( vicidial_closer_log.`status` = 'NANQUE',  call_log.`length_in_sec`,  vicidial_closer_log.`length_in_sec`  ),call_log.`length_in_sec`) AS length_in_sec, "
                + " vicidial_closer_log.`queue_seconds` AS queue_seconds, "
                + " IFNULL(park_log.`parked_sec`,0) AS HoldTime,   "
                + " IFNULL(vicidial_agent_log.`dispo_sec`,0) AS wraptime, "
                + " vicidial_closer_log.`user` "
                + " FROM "
                + " vicidial_did_log "
                + " LEFT JOIN vicidial_inbound_dids "
                + " ON vicidial_inbound_dids.`did_id` = vicidial_did_log.`did_id` "
                + " LEFT JOIN vicidial_closer_log "
                + " ON vicidial_did_log.`uniqueid` = vicidial_closer_log.`uniqueid` "
                + " LEFT JOIN vicidial_campaigns "
                + " ON vicidial_inbound_dids.`campaign_id` = vicidial_campaigns.`campaign_id` "
                + " LEFT JOIN call_log "
                + " ON call_log.`uniqueid` = vicidial_did_log.`uniqueid` "
                + " LEFT JOIN park_log "
                + " ON park_log.`uniqueid` = vicidial_did_log.`uniqueid` "
                + " LEFT JOIN vicidial_agent_log "
                + " ON vicidial_agent_log.`uniqueid` = vicidial_did_log.`uniqueid` "
                + " WHERE vicidial_did_log.`call_date` BETWEEN ? "
                + " AND ?  AND vicidial_inbound_dids.`did_id` IN (";

        String strSelectedServiceIds = "";
        for (String i : selectedServiceIds) {
            strSelectedServiceIds += "'"+i + "',";
        }
        if (selectedServiceIds.length > 0) {
            strSelectedServiceIds = strSelectedServiceIds.substring(0, strSelectedServiceIds.length() - 1);
        }
        sql += strSelectedServiceIds + ")";

        List<Map<String, Object>> report = null;
        try {
            if (id == 1) {
            report = this.nagpurJdbcTemplate.queryForList(sql, startDate, endDate);
            } else {
                report = this.nagpurJdbcTemplate.queryForList(sql, startDate, endDate);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return report;
    }

    @Override
    public List<Map<String, Object>> goAutoDialerOutboundReport(String startDate, String endDate, String[] selectedServiceIds, int reportTypeId, int id) {

        String sql = "SELECT "
                + "vicidial_log.`call_date`,"
                + "vicidial_log.`phone_number`,"
                + "IF(call_log.`start_time`='0000-00-00 00:00:00','',call_log.`start_time`) AS `start_time`,"
                + "IF(call_log.`end_time`='0000-00-00 00:00:00','',call_log.`end_time`) AS `end_time`,"
                + "call_log.`length_in_sec`,"
                + "call_log.`caller_code`,"
                + "vicidial_log.`status`,"
                + "vicidial_log.`term_reason`,"
                + "vicidial_log.`user`,"
                + "vicidial_log.`comments` "
                + "FROM vicidial_log "
                + "LEFT JOIN call_log ON call_log.`uniqueid` = vicidial_log.`uniqueid` "
                + "WHERE vicidial_log.`call_date` BETWEEN ? AND ? ";


        sql += "AND vicidial_log.`campaign_id` IN (";

        String strSelectedServiceIds = "'";
        for (String i : selectedServiceIds) {
            strSelectedServiceIds += i + "','";
        }
        if (selectedServiceIds.length > 0) {
            strSelectedServiceIds = strSelectedServiceIds.substring(0, strSelectedServiceIds.length() - 2);
        }
        sql += strSelectedServiceIds + ")";

        List<Map<String, Object>> report = null;
        try {
             if (id == 1) {
            report = this.nagpurJdbcTemplate.queryForList(sql, startDate, endDate);
            } else {
                report = this.nagpurJdbcTemplate.queryForList(sql, startDate, endDate);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return report;
    }

    @Override
    public List<Map<String, Object>> getAgentPerformanceReport(String startDate, String endDate, String[] selectedServiceIds, int id) {

        String sql = "SELECT vicidial_users.user,full_name,"
                + " CAST(TIME_FORMAT(SEC_TO_TIME( SUM( talk_sec ) ) , '%H:%i:%S') AS CHAR) AS ActiveTime, "
                + " CAST(TIME_FORMAT(SEC_TO_TIME(SUM( pause_sec )) , '%H:%i:%S')  AS CHAR) AS NOTREADY ,"
                + " CAST(TIME_FORMAT(SEC_TO_TIME(SUM( wait_sec )) , '%H:%i:%S')  AS CHAR) AS IDLE ,"
                + " CAST(TIME_FORMAT(SEC_TO_TIME(SUM((dead_sec))+(SUM( dispo_sec )) ) , '%H:%i:%S')  AS CHAR) AS WrapTime,"
                + " CAST(TIME_FORMAT(SEC_TO_TIME(  (SUM( talk_sec ) + SUM( pause_sec ) + SUM( wait_sec ) + SUM( dispo_sec ) + SUM(dead_sec))) , '%H:%i:%S')  AS CHAR) AS TOTAL,"
                + " CAST(SUM(IF(vicidial_agent_log.status !='null',1,0))  AS CHAR) AS CALLS,"
                + " CAST(TIME_FORMAT(SEC_TO_TIME(SUM(IF(vicidial_agent_log.`sub_status`='OCW',vicidial_agent_log.`pause_sec`,0))) , '%H:%i:%S') AS CHAR) AS OCWTIME,"
                + " CAST(TIME_FORMAT(SEC_TO_TIME(SUM(IF(vicidial_agent_log.`sub_status`='LBREAK' OR vicidial_agent_log.`sub_status`='LB',vicidial_agent_log.`pause_sec`,0))),'%H:%i:%S') AS CHAR) AS LBREAKTIME,"
                + " CAST(TIME_FORMAT(SEC_TO_TIME(SUM(IF(vicidial_agent_log.`sub_status`='TBREAK' OR vicidial_agent_log.`sub_status`='TB',vicidial_agent_log.`pause_sec`,0))),'%H:%i:%S') AS CHAR) AS TBREAKTIME,"
                + " CAST(SEC_TO_TIME(SUM(IF(vicidial_agent_log.`sub_status`='QFB' OR vicidial_agent_log.`sub_status`='QFback',vicidial_agent_log.`pause_sec`,0))) AS CHAR) AS QUALITYFEEDBACK,"
                + " CAST(TIME_FORMAT(SEC_TO_TIME(SUM(IF(vicidial_agent_log.`sub_status`='TDT' OR vicidial_agent_log.`sub_status`='TDTime' OR vicidial_agent_log.`sub_status`='TDown',vicidial_agent_log.`pause_sec`,0)))  , '%H:%i:%S') AS CHAR) AS TDTBEAKTIME,"
                + " CAST(TIME_FORMAT(SEC_TO_TIME(SUM(IF(vicidial_agent_log.`sub_status`='BRF',vicidial_agent_log.`pause_sec`,0))) , '%H:%i:%S') AS CHAR) AS BRFBEAKTIME,"
                + " CAST(TIME_FORMAT(SEC_TO_TIME(SUM(IF(vicidial_agent_log.`sub_status`='MCALLS' OR vicidial_agent_log.`sub_status`='Mcall',vicidial_agent_log.`pause_sec`,0))) , '%H:%i:%S') AS CHAR) AS MANUALCALLS ,"
                + " CAST(TIME_FORMAT(SEC_TO_TIME(SUM(IF(vicidial_agent_log.`sub_status`='LOGIN',vicidial_agent_log.`pause_sec`,0))) , '%H:%i:%S') AS CHAR) AS LOGIN ,"
                + " CAST(SEC_TO_TIME(SUM(IF(vicidial_agent_log.`sub_status` IS NULL,vicidial_agent_log.`pause_sec`,0))) AS CHAR) AS Pause"
                + " FROM vicidial_users, vicidial_agent_log"
                + " WHERE vicidial_agent_log.`event_time` BETWEEN ? AND ?"
                + " AND vicidial_users.user = vicidial_agent_log.user "
                + " AND pause_sec <65000 "
                + " AND wait_sec <65000 "
                + " AND talk_sec <65000 "
                + " AND dispo_sec <65000 "
                + " AND vicidial_agent_log.campaign_id IN (";

        String strSelectedServiceIds = "'";
        for (String i : selectedServiceIds) {
            strSelectedServiceIds += i + "','";
        }
        if (selectedServiceIds.length > 0) {
            strSelectedServiceIds = strSelectedServiceIds.substring(0, strSelectedServiceIds.length() - 2);
        }
        sql += strSelectedServiceIds + ") GROUP BY USER, full_name";

        
        List<Map<String, Object>> report = null;
        try {
             if (id == 1) {
            report = this.nagpurJdbcTemplate.queryForList(sql, startDate, endDate);
            } else {
                report = this.nagpurJdbcTemplate.queryForList(sql, startDate, endDate);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return report;
    }
}