/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.dao.impl;

import cc.altius.dao.LogDao;
import cc.altius.utils.DateUtils;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

/**
 *
 * @author shrutika
 */
@Repository("logDao")
public class LogDaoImpl implements LogDao {

    private JdbcTemplate jdbcTemplate;
    private DataSource dataSource;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /**
     * Method used to get the access log
     *
     * @param ip ip used to get ip address
     * @param username username used to get access log
     * @param userId
     * @param success success used to get the status succeed/failed
     * @param outcome outcome used to get the reason behind success/failure
     */
    @Override
    public void accessLog(String ip, String username, Integer userId, boolean success, String outcome) {
        SimpleJdbcInsert si = new SimpleJdbcInsert(dataSource).withTableName("access_log");
        Map<String, Object> map = new HashMap<String, Object>();
        Date curDt = DateUtils.getCurrentDateObject(DateUtils.IST);
        map.put("ACCESS_DATE", curDt);
        map.put("IP", ip);
        map.put("USERNAME", username);
        map.put("USER_ID", userId);
        map.put("SUCCESS", success);
        map.put("OUTCOME", outcome);
        si.execute(map);
    }
}
