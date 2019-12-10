/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.service;

/**
 *
 * @author akil
 */
public interface LogService {

    public void accessLog(String ip, String username, Integer userId, boolean success, String outcome);
}
