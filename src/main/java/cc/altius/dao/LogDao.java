/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.dao;

/**
 *
 * @author shrutika
 */
public interface LogDao {

    public void accessLog(String ip, String username, Integer userId, boolean success, String outcome);
}
