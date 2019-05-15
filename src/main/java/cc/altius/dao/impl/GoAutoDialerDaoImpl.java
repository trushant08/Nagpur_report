/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.dao.impl;

import cc.altius.dao.GoAutoDialerDao;
import cc.altius.utils.LogUtils;
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
    private JdbcTemplate nagpurJdbcTemplate5;
    private JdbcTemplate nagpurJdbcTemplate14;
    private JdbcTemplate nagpurJdbcTemplate6;
    private JdbcTemplate nagpurJdbcTemplate15;
    private DataSource dataSource;
    private DataSource nagpurDataSource5;
    private DataSource nagpurDataSource14;
    private DataSource nagpurDataSource6;
    private DataSource nagpurDataSource15;

    @Autowired
    @Qualifier("dataSource")
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Autowired
    @Qualifier("nagpurDataSource5")
    public void setNagpurDataSource5(DataSource nagpurDataSource5) {
        this.nagpurDataSource5 = nagpurDataSource5;
        this.nagpurJdbcTemplate5 = new JdbcTemplate(nagpurDataSource5);
    }

    @Autowired
    @Qualifier("nagpurDataSource14")
    public void setNagpurDataSource14(DataSource nagpurDataSource14) {
        this.nagpurDataSource14 = nagpurDataSource14;
        this.nagpurJdbcTemplate14 = new JdbcTemplate(nagpurDataSource14);
    }

    @Autowired
    @Qualifier("nagpurDataSource6")
    public void setNagpurDataSource6(DataSource nagpurDataSource6) {
        this.nagpurDataSource6 = nagpurDataSource6;
        this.nagpurJdbcTemplate6 = new JdbcTemplate(nagpurDataSource6);
    }

    @Autowired
    @Qualifier("nagpurDataSource15")
    public void setNagpurDataSource15(DataSource nagpurDataSource15) {
        this.nagpurDataSource15 = nagpurDataSource15;
        this.nagpurJdbcTemplate15 = new JdbcTemplate(nagpurDataSource15);
    }

    //GoAuto Dialer Report with Hold Time
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
            strSelectedServiceIds += "'" + i + "',";
        }
        if (selectedServiceIds.length > 0) {
            strSelectedServiceIds = strSelectedServiceIds.substring(0, strSelectedServiceIds.length() - 1);
        }
        sql += strSelectedServiceIds + ")";
        LogUtils.systemLogger.info(LogUtils.buildStringForSystemLog(sql, new Object[]{startDate, endDate}));

        List<Map<String, Object>> report = null;
        try {
            if (id == 1) {
                report = this.nagpurJdbcTemplate5.queryForList(sql, startDate, endDate);
            } else if (id == 2) {
                report = this.nagpurJdbcTemplate14.queryForList(sql, startDate, endDate);
            } else if (id == 3) {
                report = this.nagpurJdbcTemplate6.queryForList(sql, startDate, endDate);
            } else if (id == 4) {
                report = this.nagpurJdbcTemplate15.queryForList(sql, startDate, endDate);
            }
        } catch (Exception e) {
            LogUtils.systemLogger.error(LogUtils.buildStringForSystemLog(e));
        }
        return report;
    }

    //GoAuto Dialer Inbound Report
    @Override
    public List<Map<String, Object>> getGoAutoDialerInboundList(String startDate, String endDate, String[] selectedServiceIds, int id) {
        String sql = "SELECT "
                + " vicidial_did_log.`uniqueid`, "
                + " vicidial_did_log.`caller_id_number`, "
                + " vicidial_inbound_dids.`did_description`, "
                + " vicidial_campaigns.`campaign_description`, "
                + " COALESCE(vicidial_closer_log.`term_reason`,'IVR ABANDON') AS term_reason, "
                + " COALESCE(vicidial_closer_log.`status`,'IVR ABANDON') AS `status`, "
                + " COALESCE(vicidial_closer_log.`call_date`,vicidial_did_log.`call_date`) AS call_date, "
                + " IF ( "
                + " vicidial_closer_log.`status` = 'NANQUE' , "
                + " call_log.`length_in_sec`, "
                + " vicidial_closer_log.`length_in_sec` "
                + " ) AS length_in_sec, "
                + " vicidial_closer_log.`queue_seconds`, "
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
                //                + "WHERE vicidial_did_log.`call_date` BETWEEN '2015-07-24 00:00' "
                //                + "  AND '2015-07-24 23:59' AND vicidial_inbound_dids.`did_id` IN (13,14,16,17,19);"
                + " where vicidial_did_log.`call_date` >=? and "
                + " vicidial_did_log.`call_date` <=? and "
                + " vicidial_inbound_dids.`did_id` IN (";

        String strSelectedServiceIds = "";
        for (String i : selectedServiceIds) {
            strSelectedServiceIds += i + ",";
        }
        if (selectedServiceIds.length > 0) {
            strSelectedServiceIds = strSelectedServiceIds.substring(0, strSelectedServiceIds.length() - 1);
        }
        sql += strSelectedServiceIds + ")";
        LogUtils.systemLogger.info(LogUtils.buildStringForSystemLog(sql, new Object[]{startDate, endDate}));

        List<Map<String, Object>> report = null;

        try {
            if (id == 1) {
                report = this.nagpurJdbcTemplate5.queryForList(sql, startDate, endDate);
            } else if (id == 2) {
                report = this.nagpurJdbcTemplate14.queryForList(sql, startDate, endDate);
            } else if (id == 3) {
                report = this.nagpurJdbcTemplate6.queryForList(sql, startDate, endDate);
            } else if (id == 4) {
                report = this.nagpurJdbcTemplate15.queryForList(sql, startDate, endDate);
            }
        } catch (Exception e) {
            LogUtils.systemLogger.error(LogUtils.buildStringForSystemLog(e));
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
        if (reportTypeId == 3 || reportTypeId == 8) {
            sql += "AND (vicidial_log.`comments` IS NULL OR vicidial_log.`comments` ='auto' ) ";
        }
        if (reportTypeId == 4 || reportTypeId == 9) {
            sql += "AND vicidial_log.`comments` ='manual' ";
        }

        sql += "AND vicidial_log.`campaign_id` IN (";

        String strSelectedServiceIds = "'";
        for (String i : selectedServiceIds) {
            strSelectedServiceIds += i + "','";
        }
        if (selectedServiceIds.length > 0) {
            strSelectedServiceIds = strSelectedServiceIds.substring(0, strSelectedServiceIds.length() - 2);
        }
        sql += strSelectedServiceIds + ")";
        LogUtils.systemLogger.info(LogUtils.buildStringForSystemLog(sql, new Object[]{startDate, endDate}));

        List<Map<String, Object>> report = null;
        try {
            if (id == 1) {
                report = this.nagpurJdbcTemplate5.queryForList(sql, startDate, endDate);
            } else if (id == 2) {
                report = this.nagpurJdbcTemplate14.queryForList(sql, startDate, endDate);
            } else if (id == 3) {
                report = this.nagpurJdbcTemplate6.queryForList(sql, startDate, endDate);
            } else if (id == 4) {
                report = this.nagpurJdbcTemplate15.queryForList(sql, startDate, endDate);
            }

        } catch (Exception e) {
            LogUtils.systemLogger.error(LogUtils.buildStringForSystemLog(e));
        }
        return report;
    }

    @Override
    public List<Map<String, Object>> getAgentPerformanceReport(String startDate, String endDate, String[] selectedServiceIds, int id) {

        String sql = "SELECT vicidial_users.user,full_name FullName,\n"
                + "CAST(TIME_FORMAT(SEC_TO_TIME( SUM( talk_sec ) - SUM((dead_sec)) ) , '%H:%i:%S') AS CHAR) AS ActiveTime,\n"
                + "CAST(TIME_FORMAT(SEC_TO_TIME(SUM( pause_sec )) , '%H:%i:%S')  AS CHAR) AS NotReady ,\n"
                + "CAST(TIME_FORMAT(SEC_TO_TIME(SUM( wait_sec )) , '%H:%i:%S')  AS CHAR) AS Idle ,\n"
                + "CAST(TIME_FORMAT(SEC_TO_TIME(SUM((dead_sec))+(SUM( dispo_sec )) ) , '%H:%i:%S')  AS CHAR) AS wrapTime,\n"
                + "CAST(TIME_FORMAT(SEC_TO_TIME(  (SUM( talk_sec ) + SUM( pause_sec ) + SUM( wait_sec ) + SUM( dispo_sec ) )) , '%H:%i:%S')  AS CHAR) AS Total,\n"
                + "CAST(SUM(IF(vicidial_agent_log.status !='null',1,0))  AS CHAR) AS Calls,\n"
                + "CAST(TIME_FORMAT(SEC_TO_TIME(SUM(IF(vicidial_agent_log.`sub_status`='OCW',vicidial_agent_log.`pause_sec`,0))) , '%H:%i:%S') AS CHAR) AS OCWTIME,\n"
                + "CAST(TIME_FORMAT(SEC_TO_TIME(SUM(IF(vicidial_agent_log.`sub_status`='LBREAK' OR vicidial_agent_log.`sub_status`='LB',vicidial_agent_log.`pause_sec`,0))),'%H:%i:%S') AS CHAR) AS LbBreakTime,\n"
                + "CAST(TIME_FORMAT(SEC_TO_TIME(SUM(IF(vicidial_agent_log.`sub_status`='TBREAK' OR vicidial_agent_log.`sub_status`='TB',vicidial_agent_log.`pause_sec`,0))),'%H:%i:%S') AS CHAR) AS TbBreakTime,\n"
                + "CAST(TIME_FORMAT(SEC_TO_TIME(SUM(IF(vicidial_agent_log.`sub_status`='BBREAK',vicidial_agent_log.`pause_sec`,0))) , '%H:%i:%S') AS CHAR) AS BioBreak,\n"
                + "CAST(SEC_TO_TIME(SUM(IF(vicidial_agent_log.`sub_status`='QFB' OR vicidial_agent_log.`sub_status`='QFback',vicidial_agent_log.`pause_sec`,0))) AS CHAR) AS QfbBreakTime,\n"
                + "CAST(TIME_FORMAT(SEC_TO_TIME(SUM(IF(vicidial_agent_log.`sub_status`='TDT' OR vicidial_agent_log.`sub_status`='TDTime' OR vicidial_agent_log.`sub_status`='TDown',vicidial_agent_log.`pause_sec`,0)))  , '%H:%i:%S') AS CHAR) AS TdtBreakTime,\n"
                + "CAST(TIME_FORMAT(SEC_TO_TIME(SUM(IF(vicidial_agent_log.`sub_status`='BRF',vicidial_agent_log.`pause_sec`,0))) , '%H:%i:%S') AS CHAR) AS BrfBreakTime,\n"
                + "CAST(TIME_FORMAT(SEC_TO_TIME(SUM(IF(vicidial_agent_log.`sub_status`='MCALLS' OR vicidial_agent_log.`sub_status`='Mcall',vicidial_agent_log.`pause_sec`,0))) , '%H:%i:%S') AS CHAR) AS ManualCalls ,\n"
                + "CAST(TIME_FORMAT(SEC_TO_TIME(SUM(IF(vicidial_agent_log.`sub_status`='LOGIN',vicidial_agent_log.`pause_sec`,0))) , '%H:%i:%S') AS CHAR) AS Login ,\n"
                + "CAST(TIME_FORMAT(SEC_TO_TIME(SUM(IF(vicidial_agent_log.`sub_status`='NXDIAL',vicidial_agent_log.`pause_sec`,0))) , '%H:%i:%S') AS CHAR) AS NxDial ,\n"
                + "CAST(SEC_TO_TIME(SUM(IF(vicidial_agent_log.`sub_status` IS NULL,vicidial_agent_log.`pause_sec`,0))) AS CHAR) AS Pause\n"
                + "FROM vicidial_users, vicidial_agent_log\n"
                + "WHERE vicidial_agent_log.`event_time` BETWEEN  ? AND ?\n"
                + "AND vicidial_users.user = vicidial_agent_log.user\n"
                + "AND pause_sec <65000\n"
                + "AND wait_sec <65000\n"
                + "AND talk_sec <65000\n"
                + "AND dispo_sec <65000\n"
                + "AND vicidial_agent_log.campaign_id IN (";

//        String sql = "SELECT `User`, FullName,SEC_TO_TIME(ActiveTime) AS ActiveTime,SEC_TO_TIME( IF(Minus>Total, NotReady,\n"
//                + "IF ((NotReady-(Total-Minus)<=0),NotReady,(NotReady-(Total-Minus)) ))) AS NotReady,SEC_TO_TIME(Idle) AS Idle,\n"
//                + "SEC_TO_TIME(Wraptime) AS wraptime,SEC_TO_TIME(IF(Minus>Total, Total, Minus)) Total,\n"
//                + "Calls,SEC_TO_TIME(LbBreakTime) AS LbBreakTime,SEC_TO_TIME(TbBreakTime) AS TbBreakTime,SEC_TO_TIME(QfbBreakTime) AS QfbBreakTime,\n"
//                + "SEC_TO_TIME(TdtBreakTime) AS TdtBreakTime,SEC_TO_TIME(BrfBreakTime) AS BrfBreakTime,\n"
//                + "SEC_TO_TIME(ManualCalls) AS ManualCalls,SEC_TO_TIME(Login) AS Login,\n"
//                //                + "-- sec_to_time(NxDial-(Total-Minus)) As NxDial,\n"
//                + "SEC_TO_TIME(IF((NxDial-(Total-Minus))>=0, (NxDial-(Total-Minus)),0 )) AS NxDial   FROM (\n"
//                + "SELECT vicidial_users.user `User`,full_name `FullName`,\n"
//                + "(SUM( talk_sec ) - SUM(dead_sec) + SUM(IF(vicidial_agent_log.`sub_status` IS NULL,vicidial_agent_log.`pause_sec`,0))) AS ActiveTime,\n"
//                + "(SUM( pause_sec ) -SUM(IF(vicidial_agent_log.`sub_status` IS NULL,vicidial_agent_log.`pause_sec`,0))) AS NotReady ,\n"
//                + "(SUM( wait_sec )) AS Idle ,\n"
//                + "(SUM((dead_sec))+(SUM( dispo_sec ))) AS WrapTime,\n"
//                + "SUM(IF(vicidial_agent_log.status !='null',1,0)) AS Calls,\n"
//                //                + "-- (SUM(IF(vicidial_agent_log.`sub_status`='OCW',vicidial_agent_log.`pause_sec`,0))) AS OcwTime,\n"
//                + "(SUM(IF(vicidial_agent_log.`sub_status`='LBREAK' OR vicidial_agent_log.`sub_status`='LB',vicidial_agent_log.`pause_sec`,0))) AS LbBreakTime,\n"
//                + "(SUM(IF(vicidial_agent_log.`sub_status`='TBREAK' OR vicidial_agent_log.`sub_status`='TB',vicidial_agent_log.`pause_sec`,0))) AS TbBreakTime,\n"
//                + "SUM(IF(vicidial_agent_log.`sub_status`='QFB' OR vicidial_agent_log.`sub_status`='QFback',vicidial_agent_log.`pause_sec`,0)) AS QfbBreakTime,\n"
//                + "(SUM(IF(vicidial_agent_log.`sub_status`='TDT' OR vicidial_agent_log.`sub_status`='TDTime' OR vicidial_agent_log.`sub_status`='TDown',vicidial_agent_log.`pause_sec`,0))) AS TdtBreakTime,\n"
//                + "(SUM(IF(vicidial_agent_log.`sub_status`='BRF',vicidial_agent_log.`pause_sec`,0))) AS BrfBreakTime,\n"
//                + "(SUM(IF(vicidial_agent_log.`sub_status`='MCALLS' OR vicidial_agent_log.`sub_status`='Mcall',vicidial_agent_log.`pause_sec`,0))) AS ManualCalls ,\n"
//                + "(SUM(IF(vicidial_agent_log.`sub_status`='LOGIN',vicidial_agent_log.`pause_sec`,0))) AS Login ,\n"
//                + "(SUM(IF(vicidial_agent_log.`sub_status`='NXDIAL',vicidial_agent_log.`pause_sec`,0))) AS NxDial ,\n"
//                //                + "-- (SUM(IF(vicidial_agent_log.`sub_status` IS NULL,vicidial_agent_log.`pause_sec`,0))) AS Pause,\n"
//                + "MIN(vicidial_agent_log.`event_time`) AS LoginTime,\n"
//                + "MAX(vicidial_agent_log.`event_time`) AS LogoutTime,\n"
//                + "SUM( talk_sec ) + SUM( pause_sec ) + SUM( wait_sec ) + SUM( dispo_sec ) AS Total,\n"
//                + "TIME_TO_SEC(TIMEDIFF(MAX(vicidial_agent_log.`event_time`),MIN(vicidial_agent_log.`event_time`))) AS Minus\n"
//                + "FROM vicidial_users, vicidial_agent_log\n"
//                + "WHERE vicidial_agent_log.`event_time` BETWEEN ? AND ?\n"
//                + "AND vicidial_users.user = vicidial_agent_log.user\n"
//                + "AND pause_sec <65000\n"
//                + "AND wait_sec <65000\n"
//                + "AND talk_sec <65000\n"
//                + "AND dispo_sec <65000\n"
//                + "AND vicidial_agent_log.`campaign_id` IN (";
        String strSelectedServiceIds = "'";
        for (String i : selectedServiceIds) {
            strSelectedServiceIds += i + "','";
        }
        if (selectedServiceIds.length > 0) {
            strSelectedServiceIds = strSelectedServiceIds.substring(0, strSelectedServiceIds.length() - 2);
        }
        sql += strSelectedServiceIds + ") GROUP BY USER, full_name";
//        sql += strSelectedServiceIds + ") GROUP BY USER, full_name) AS a1";
        LogUtils.systemLogger.info(LogUtils.buildStringForSystemLog(sql, new Object[]{startDate, endDate}));

        List<Map<String, Object>> report = null;
        try {
            if (id == 1) {
                report = this.nagpurJdbcTemplate5.queryForList(sql, startDate, endDate);
            } else if (id == 2) {
                report = this.nagpurJdbcTemplate14.queryForList(sql, startDate, endDate);
            } else if (id == 3) {
                report = this.nagpurJdbcTemplate6.queryForList(sql, startDate, endDate);
            } else if (id == 4) {
                report = this.nagpurJdbcTemplate15.queryForList(sql, startDate, endDate);
            }

        } catch (Exception e) {
            LogUtils.systemLogger.error(LogUtils.buildStringForSystemLog(e));
        }
        return report;
    }

    //Inbound Report With Hold Time New
    @Override
    public List<Map<String, Object>> getInboundReportNew(String startDate, String endDate, String[] selectedServiceIds, int id) {

        String sql = "SELECT"
                + " vicidial_did_log.`uniqueid`,"
                + " vicidial_did_log.`caller_id_number`,"
                + " vicidial_inbound_dids.`did_description`,"
                + " vicidial_campaigns.`campaign_description`,"
                + " COALESCE(vicidial_closer_log.`term_reason`,'IVR ABANDON') AS term_reason,"
                + " COALESCE(vicidial_closer_log.`status`,'IVR ABANDON') AS `status`,"
                + " COALESCE(vicidial_closer_log.`call_date`,vicidial_did_log.`call_date`) AS call_date,"
                + " COALESCE(IF ( vicidial_closer_log.`status` = 'NANQUE', call_log.`length_in_sec`, vicidial_closer_log.`length_in_sec` ),call_log.`length_in_sec`) AS length_in_sec,"
                + " vicidial_closer_log.`queue_seconds` AS queue_seconds,"
                + " IFNULL(park.`parked_sec`,0) AS HoldTime,"
                + " IFNULL(vicidial_agent_log.`dispo_sec`,0) AS wraptime,"
                + " vicidial_closer_log.`user`"
                + " FROM"
                + " vicidial_did_log"
                + " LEFT JOIN vicidial_inbound_dids ON vicidial_inbound_dids.`did_id` = vicidial_did_log.`did_id`"
                + " LEFT JOIN vicidial_closer_log ON vicidial_did_log.`uniqueid` = vicidial_closer_log.`uniqueid`"
                + " LEFT JOIN vicidial_campaigns ON vicidial_inbound_dids.`campaign_id` = vicidial_campaigns.`campaign_id`"
                + " LEFT JOIN call_log ON call_log.`uniqueid` = vicidial_did_log.`uniqueid`"
                + " LEFT JOIN (SELECT park_log.`uniqueid`,SUM(IFNULL(park_log.`parked_sec`,0)) `parked_sec`"
                + " FROM park_log"
                + " WHERE park_log.`parked_time` BETWEEN ? AND ?  "
                + " GROUP BY park_log.`uniqueid`) AS park"
                + " ON park.`uniqueid`=vicidial_did_log.`uniqueid`"
                + " LEFT JOIN vicidial_agent_log ON vicidial_agent_log.`uniqueid` = vicidial_did_log.`uniqueid`"
                + " AND vicidial_agent_log.`status` IS NOT NULL"
                + " WHERE vicidial_did_log.`call_date` BETWEEN ? AND ? "
                + " AND vicidial_inbound_dids.`did_id` IN (";

        String strSelectedServiceIds = "";
        for (String i : selectedServiceIds) {
            strSelectedServiceIds += i + ",";
        }
        if (selectedServiceIds.length > 0) {
            strSelectedServiceIds = strSelectedServiceIds.substring(0, strSelectedServiceIds.length() - 1);
        }
        sql += strSelectedServiceIds + ")";

        List<Map<String, Object>> report = null;
        try {
            if (id == 1) {
                report = this.nagpurJdbcTemplate5.queryForList(sql, startDate, endDate, startDate, endDate);
            } else if (id == 2) {
                report = this.nagpurJdbcTemplate14.queryForList(sql, startDate, endDate, startDate, endDate);
            } else if (id == 3) {
                report = this.nagpurJdbcTemplate6.queryForList(sql, startDate, endDate, startDate, endDate);
            } else if (id == 4) {
                report = this.nagpurJdbcTemplate15.queryForList(sql, startDate, endDate, startDate, endDate);
            }
        } catch (Exception e) {
            LogUtils.systemLogger.error(LogUtils.buildStringForSystemLog(e));
        }
        return report;
    }
}
