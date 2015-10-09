/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.service.impl;

import cc.altius.dao.LogDao;
import cc.altius.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author akil
 */
@Service("logService")
public class LogServiceImpl implements LogService {

    @Autowired
    private LogDao logDao;

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
        this.logDao.accessLog(ip, username, userId, success, outcome);
    }
}
