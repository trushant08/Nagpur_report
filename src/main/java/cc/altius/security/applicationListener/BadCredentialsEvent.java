/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.security.applicationListener;

import cc.altius.service.LogService;
import cc.altius.service.UserService;
import cc.altius.utils.LogUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

/**
 *
 * @author shrutika
 */
public class BadCredentialsEvent implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {

    @Autowired
    private UserService userService;
    @Autowired
    private LogService logService;

    @Override
    public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent e) {
        String curUser = (String) e.getAuthentication().getPrincipal();
        this.userService.incrementFailedCountForUsername(curUser);
        this.logService.accessLog(((WebAuthenticationDetails) e.getAuthentication().getDetails()).getRemoteAddress(), curUser, null, false, "Incorrect Password");
        LogUtils.systemLogger.info(LogUtils.buildStringForSystemLog("User Not Found with username " + curUser));
        LogUtils.accessLogger.info(LogUtils.buildStringForAccessLog("User Not Found with username " + curUser));
    }
}
