/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.security.applicationListener;

import cc.altius.model.CustomUserDetails;
import cc.altius.service.LogService;
import cc.altius.service.UserService;
import cc.altius.utils.LogUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

/**
 *
 * @author shrutika
 */
public class SuccessEvent implements ApplicationListener<AuthenticationSuccessEvent> {

    @Autowired
    private UserService userService;
    @Autowired
    private LogService logService;

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent e) {
        CustomUserDetails curUser = (CustomUserDetails) e.getAuthentication().getPrincipal();
        this.userService.loginSuccessUpdateForUserId(curUser.getUserId());
        this.logService.accessLog(((WebAuthenticationDetails) e.getAuthentication().getDetails()).getRemoteAddress(), curUser.getUsername(), curUser.getUserId(), true, "Success");
        LogUtils.systemLogger.info(LogUtils.buildStringForSystemLog("User Found with username " + curUser.getUsername()));
        LogUtils.accessLogger.info(LogUtils.buildStringForAccessLog("User Found with username " + curUser.getUsername()));
    }
}
